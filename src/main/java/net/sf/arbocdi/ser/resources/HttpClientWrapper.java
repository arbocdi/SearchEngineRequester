package net.sf.arbocdi.ser.resources;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import javax.enterprise.inject.Alternative;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.arbocdi.ser.resources.Configuration;
import net.sf.arbocdi.ser.Utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

@Root
@Slf4j
@Alternative
@ToString
public class HttpClientWrapper implements AutoCloseable, Runnable {

    private Thread worker;

    //config===========
    /**
     * Timeout in seconds
     */
    @Element
    @Setter
    @Getter
    private int idleTimeout = 60;
    @Element
    @Setter
    @Getter
    private int maxConnections = 100;
    @Element
    @Setter
    @Getter
    private int maxConnectionsPerRoute = 20;
    //work=============
    @Getter
    private PoolingHttpClientConnectionManager cm;
    @Getter
    private CloseableHttpClient httpClient;
    private volatile boolean stopped=false;

    public void start() {
        log.info(String.format("Starting %s", this.getClass().getName()));
        this.stopped=false;

//        PoolingHttpClientConnectionManager is a more complex implementation that 
//        manages a pool of client connections and is able to service connection requests 
//        from multiple execution threads. Connections are pooled on a per route basis. 
//        A request for a route for which the manager already has a persistent connection 
//        available in the pool will be serviced by leasing a connection from the pool 
//        rather than creating a brand new connection.
        cm = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 200
        cm.setMaxTotal(this.maxConnections);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(this.maxConnectionsPerRoute);
//        HttpClient implementations are expected to be thread safe. 
//        It is recommended that the same instance of this class is reused 
//        for multiple request executions.
        httpClient = HttpClients.custom()
                .setConnectionManager(cm).setRetryHandler(new SafeRetryHandler())
                .build();
        this.worker = new Thread(this);
        this.worker.start();
    }

    public Object makeRequest(ResponseHandler handler, HttpUriRequest request) throws IOException {
        return this.httpClient.execute(request, handler);
    }

    public String makeStringRequest(HttpUriRequest request) throws IOException {
        return this.httpClient.execute(request, new StringResponseHandler());
    }

    /**
     * When an HttpClient instance is no longer needed and is about to go out of
     * scope it is important to shut down its connection manager to ensure that
     * all connections kept alive by the manager get closed and system resources
     * allocated by those connections are released. A connection can be closed
     * gracefully (an attempt to flush the output buffer prior to closing is
     * made), or forcefully, by calling the shutdown method (the output buffer
     * is not flushed). To properly close connections we need to do all of the
     * following:
     *
     * consume and close the response (if closeable)
     *
     * close the client
     *
     * close and shut down the connection manager
     *
     */
    @Override
    public void close() throws Exception {
        log.info(String.format("Stopping %s", this.getClass().getName()));
        this.stopped = true;
        Utils.close(this.httpClient);
        Utils.close(this.cm);
        this.cm.shutdown();
        this.worker.interrupt();
        this.worker.join();
    }

    /**
     * Connections check:This is a general limitation of the blocking I/O in
     * Java. There is simply no way of finding out whether or not the opposite
     * endpoint has closed connection other than by attempting to read from the
     * socket. Apache HttpClient works this problem around by employing the so
     * stale connection check which is essentially a very brief read operation.
     */
    @Override
    public void run() {
        while (!this.stopped) {
            try {
                Thread.sleep(10000);
                // Close expired connections
                cm.closeExpiredConnections();
                // Optionally, close connections
                // that have been idle longer than 30 sec
                cm.closeIdleConnections(idleTimeout, TimeUnit.SECONDS);
            } catch (Exception ex) {
                return;
            }
        }
    }

    public static class StringResponseHandler implements ResponseHandler<String> {

        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(
                        statusLine.getStatusCode(),
                        statusLine.getReasonPhrase());
            }
            if (entity == null) {
                throw new ClientProtocolException("Response contains no content");
            }
            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();
            return EntityUtils.toString(entity, charset);
        }

    }

    public static class SafeRetryHandler implements HttpRequestRetryHandler {

        @Override
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            return false;
        }

    }

    public static void main(String[] args) throws Exception {
        HttpClientWrapper hcw = new HttpClientWrapper();
        Serializer persister = new Persister();
        persister.write(hcw, new File(Configuration.HTTP_CLIENT_WRAPPER_XML));
    }

}

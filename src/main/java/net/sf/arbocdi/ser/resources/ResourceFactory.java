package net.sf.arbocdi.ser.resources;

import java.io.File;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author arbocdi
 */
@ApplicationScoped
public class ResourceFactory {

    @Produces
    @ApplicationScoped
    public HttpClientWrapper makeHttpClientWrapper() throws Exception {
        Serializer persister = new Persister();
        HttpClientWrapper httpClientWrapper = persister.read(HttpClientWrapper.class, new File(Configuration.HTTP_CLIENT_WRAPPER_XML));
        try {
            httpClientWrapper.start();
            return httpClientWrapper;
        } catch (Exception ex) {
            httpClientWrapper.close();
            throw ex;
        }

    }

    public void closeHttpClientWrapper(@Disposes HttpClientWrapper httpClientWrapper) throws Exception {
        httpClientWrapper.close();
    }
}

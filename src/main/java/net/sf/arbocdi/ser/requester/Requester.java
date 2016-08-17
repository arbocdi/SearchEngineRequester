package net.sf.arbocdi.ser.requester;

/**
 *
 * @author arbocdi
 */
public interface Requester {

    String makeRequest(String request) throws Exception;
}

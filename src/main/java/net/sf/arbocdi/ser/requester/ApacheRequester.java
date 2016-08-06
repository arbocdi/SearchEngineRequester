/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ser.requester;

import javax.annotation.PostConstruct;
import net.sf.arbocdi.ser.resources.HttpClientWrapper;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.Data;
import net.sf.arbocdi.ser.requester.RequesterI.RequesterQualifier;
import net.sf.arbocdi.ser.requester.RequesterI.RequesterType;
import org.apache.http.client.methods.HttpGet;

/**
 *
 * @author arbocdi
 */
@ApplicationScoped
@Data
@RequesterQualifier(type = RequesterType.APACHE)
public class ApacheRequester implements RequesterI {

    @Inject
    private HttpClientWrapper httpClientWrapper;

    @Override
    public String makeRequest(String request) throws Exception {
        HttpGet getRequest = new HttpGet(request);
        return this.httpClientWrapper.makeStringRequest(getRequest);
    }

}

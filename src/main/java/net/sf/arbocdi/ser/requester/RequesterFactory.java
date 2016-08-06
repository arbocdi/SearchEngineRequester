/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ser.requester;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import lombok.Data;
import net.sf.arbocdi.ser.requester.RequesterI.RequesterType;

/**
 *
 * @author arbocdi
 */
@ApplicationScoped
@Data
public class RequesterFactory {

    private RequesterType requesterType;

    @Produces
    @Default
    @Dependent
    public RequesterI createRequester(@RequesterI.RequesterQualifier(type = RequesterType.APACHE)ApacheRequester ar) {
        switch (requesterType) {
            case APACHE:
                return ar;
            default:
                throw new IllegalStateException(String.format("Undefined requesterType %s ", requesterType));
        }
    }
}

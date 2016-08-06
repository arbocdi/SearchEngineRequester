/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ser;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 *
 * @author arbocdi
 */
public class WeldHelper {

    public static final WeldContainer container;

    static {
        final Weld weld = new Weld();
        container = weld.initialize();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    container.close();
                    weld.shutdown();
                } catch (Exception ex) {
                }
            }
        });
    }
}

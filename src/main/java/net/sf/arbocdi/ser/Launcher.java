/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ser;

import net.sf.arbocdi.ser.searcher.GoogleSearcher;
import java.io.Console;
import lombok.extern.slf4j.Slf4j;
import net.sf.arbocdi.ser.parser.ParserI.ParserResult;
import net.sf.arbocdi.ser.requester.RequesterFactory;
import net.sf.arbocdi.ser.requester.RequesterI;
import net.sf.arbocdi.ser.resources.AppServices;

/**
 *
 * @author arbocdi
 */
@Slf4j
public class Launcher {

    public static void main(String[] args) {
        AppServices appServices = new AppServices();
        try {
            appServices.start();
            appServices.getCDIBean(RequesterFactory.class).setRequesterType(RequesterI.RequesterType.APACHE);
            Console console = System.console();
            while (true) {
                console.format("#Enter search text or type #quit to stop%n");
                String text = console.readLine();
                if (text.equals("#quit")) {
                    return;
                }
                ParserResult result = appServices.getCDIBean(GoogleSearcher.class).search(text);
                console.format("1st result title is: %s%n", result.getTitle());
                console.format("1st result url is: %s%n", result.getUrl());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Utils.close(appServices);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ser.apache;

import net.sf.arbocdi.ser.requester.ApacheRequester;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import net.sf.arbocdi.ser.Utils;
import net.sf.arbocdi.ser.WeldHelper;
import net.sf.arbocdi.ser.requester.RequesterQualifier;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author arbocdi
 */
public class ApacheRequesterTest {

    ApacheRequester requester;

    public ApacheRequesterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        requester = WeldHelper.container.select(ApacheRequester.class,ApacheRequester.class.getAnnotation(RequesterQualifier.class)).get();
    }

    @After
    public void tearDown() {
    }

    @Test
    @Ignore
    public void testMakeGoogleRequest() throws Exception {
        String response = this.requester.makeRequest("https://www.google.com/search?ie=utf-8&oe=utf-8&q="+URLEncoder.encode("Привет", "UTF-8"));
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream("data/google.html"));
            out.write(response.getBytes("UTF-8"));
        } finally {
            Utils.close(out);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ser;

import net.sf.arbocdi.ser.searcher.GoogleSearcher;
import java.net.URISyntaxException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arbocdi
 */
public class GoogleSearcherTest {

    GoogleSearcher gs;

    public GoogleSearcherTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testMakeURIString() throws Exception {
        gs = new GoogleSearcher();
        String uri = gs.makeURIString("wП +&");
        System.out.println(uri);
        Assert.assertEquals("https://www.google.com/search?ie=utf-8&oe=utf-8&q=w%D0%9F+%2B%26", uri);
    }

}

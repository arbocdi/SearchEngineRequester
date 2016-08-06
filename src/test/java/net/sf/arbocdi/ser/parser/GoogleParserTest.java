package net.sf.arbocdi.ser.parser;

import java.io.File;
import junit.framework.Assert;
import net.sf.arbocdi.ser.WeldHelper;
import net.sf.arbocdi.ser.parser.ParserI.ParserQualifier;
import net.sf.arbocdi.ser.parser.ParserI.ParserResult;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author arbocdi
 */
public class GoogleParserTest {

    GoogleParser parser;

    public GoogleParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        parser = WeldHelper.container.select(GoogleParser.class, GoogleParser.class.getAnnotation(ParserQualifier.class)).get();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testParseHello() throws Exception {
        String html = FileUtils.readFileToString(new File("data/helloGoogleExample.html"), "UTF-8");
        ParserResult result = this.parser.parse(html);
        System.out.println(result);
        Assert.assertEquals("Adele - Hello - YouTube", result.getTitle());
        Assert.assertEquals("https://www.youtube.com/watch?v=YQHsXMglC9A", result.getUrl());
        
    }
    @Test
    public void testParse123() throws Exception {
        String html = FileUtils.readFileToString(new File("data/google123Example.html"), "UTF-8");
        ParserResult result = this.parser.parse(html);
        System.out.println(result);
        Assert.assertEquals("123movies.to: Watch Movies Online Free", result.getTitle());
        Assert.assertEquals("http://123movies.to/", result.getUrl());
        
    }

}

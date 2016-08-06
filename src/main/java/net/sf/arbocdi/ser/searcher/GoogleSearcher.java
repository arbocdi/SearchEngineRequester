/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ser.searcher;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import net.sf.arbocdi.ser.parser.ParserI;
import net.sf.arbocdi.ser.parser.ParserI.ParserQualifier;
import net.sf.arbocdi.ser.parser.ParserI.ParserResult;
import net.sf.arbocdi.ser.parser.ParserI.ParserType;
import net.sf.arbocdi.ser.requester.RequesterI;
import net.sf.arbocdi.ser.requester.RequesterI.RequesterQualifier;
import net.sf.arbocdi.ser.requester.RequesterI.RequesterType;

import org.apache.commons.io.FileUtils;

/**
 *
 * @author arbocdi
 */
@Dependent
public class GoogleSearcher implements Searcher {

    @Inject
    @Default
    private RequesterI requester;
    @Inject
    @ParserQualifier(type = ParserType.GOOGLE)
    private ParserI pasrer;

    @Override
    public ParserResult search(String searchText) throws Exception {
        String uri = this.makeURIString(searchText);
        String response = this.requester.makeRequest(uri);
        FileUtils.write(new File("work/google.html"), response, "UTF-8");
        return this.pasrer.parse(response);
    }

    public String makeURIString(String searchText) throws URISyntaxException, UnsupportedEncodingException {
        searchText = URLEncoder.encode(searchText, "UTF-8");
        return String.format("https://www.google.com/search?ie=utf-8&oe=utf-8&q=%s", searchText);
    }

}

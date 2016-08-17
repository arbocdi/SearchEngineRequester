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
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import net.sf.arbocdi.ser.parser.Parser;
import net.sf.arbocdi.ser.parser.ParserQualifier;
import net.sf.arbocdi.ser.parser.ParserResult;
import net.sf.arbocdi.ser.parser.ParserType;
import net.sf.arbocdi.ser.requester.Requester;

import org.apache.commons.io.FileUtils;

/**
 *
 * @author arbocdi
 */
@SearcherQualifier(type = SearcherType.GOOGLE)
public class GoogleSearcher implements Searcher {

    @Inject
    @Default
    private Requester requester;
    @Inject
    @ParserQualifier(type = ParserType.GOOGLE)
    private Parser pasrer;

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

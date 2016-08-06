/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ser.parser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import net.sf.arbocdi.ser.Utils;
import net.sf.arbocdi.ser.parser.ParserI.ParserQualifier;
import net.sf.arbocdi.ser.parser.ParserI.ParserType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author arbocdi
 */
@ParserQualifier(type = ParserType.GOOGLE)
public class GoogleParser implements ParserI {

    @Override
    public ParserResult parse(String str) throws Exception {
        ParserResult result = new ParserResult();
        Document doc = Jsoup.parse(str);
        Element a = doc.select("ol div.g h3 a").first();
        if (a == null) {
            return result;
        }
        result.setTitle(a.text());
        String q = Utils.getQueryMap(a.attr("href")).get("/url?q");
        result.setUrl(URLDecoder.decode(q, "UTF-8"));
        return result;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ser.searcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;
import net.sf.arbocdi.ser.parser.ParserI.ParserResult;

/**
 *
 * @author arbocdi
 */
public interface Searcher {

    ParserResult search(String searchText) throws Exception;

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER, ElementType.METHOD})
    public @interface SearcherrQualifier {

        SearcherType type();
    }

    public enum SearcherType {

        GOOGLE;
    }

}

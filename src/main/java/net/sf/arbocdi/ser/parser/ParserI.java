/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ser.parser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;
import lombok.Data;

/**
 *
 * @author arbocdi
 */
public interface ParserI {

    ParserResult parse(String str) throws Exception;

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER, ElementType.METHOD})
    public @interface ParserQualifier {

        ParserType type();
    }

    @Data
    public class ParserResult {

        private String title;
        private String url;
    }

    public enum ParserType {

        GOOGLE;
    }
}

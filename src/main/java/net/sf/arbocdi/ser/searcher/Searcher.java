/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ser.searcher;

import net.sf.arbocdi.ser.parser.ParserResult;

/**
 *
 * @author arbocdi
 */
public interface Searcher {

    ParserResult search(String searchText) throws Exception;

}

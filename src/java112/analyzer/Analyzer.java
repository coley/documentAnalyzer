package java112.analyzer;

import java.util.*;

/**
 *  Analyzer interface. Contains methods to process tokens and write output.
 *
 *@author     Nicole LaBonte
 */

public interface Analyzer {

    /**
     *  Method processes tokens.
     *
     *@param  token  String token
     */
    void processToken(String token);


    /**
     *  Method writes output
     *
     *@param  inputFilePath   input file path
     */
    void writeOutputFile(String inputFilePath);

}


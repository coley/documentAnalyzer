package java112.analyzer;

import java.util.*;
import java.io.*;

/**
 *  UniqueTokenCountAnalyzer class: This Analyzer will generate a file of 
 *  unique tokens along with a count of times the token appeared.  
 *  The output file will contain a row for each unique token. 
 *  Each row will have the token, a tab character, and the number of 
 *  times that token occurred in the file. 
 *
 *@author     Nicole LaBonte
 */

public class TokenCountAnalyzer implements Analyzer {

    private Properties properties;
    private Map<String, Integer> tokenCounts;
    //private Map tokenCounts;


    /**
     *  Constructor for the UniqueTokenCountAnalyzer object
     */
    public TokenCountAnalyzer() {
        tokenCounts = new TreeMap<String, Integer>();
    }


    /**
     *  Constructor for the UniqueTokenCountAnalyzer object
     *
     *@param  properties  Properties object
     */
    public TokenCountAnalyzer(Properties properties) {
        this();
        this.properties = properties;
    }


    /**
     *  Gets the properties attribute of the UniqueTokenCountAnalyzer object
     *
     *@return    The properties value
     */
    public Properties getProperties() {
        return properties;
    }


    /**
     *  Method returns the tokenCounts map.
     *
     *@return    Map of tokenCounts
     */
    public Map<String, Integer> tokenCounts() {
    //public Map tokenCounts() {

        return tokenCounts;
    }


    /**
     *  Gets the tokenCounts attribute of the UniqueTokenCountAnalyzer object
     *
     *@return    The tokenCounts value
     */
    public Map getTokenCounts() {
        return tokenCounts;
    }


    /**
     *  Method determines if the token is currently in the tokenCounts map.
     *  If the token exists in the map, then the current value for the
     *  key is incremented by 1.  If the token is not in the map, then
     *  it is added as a key with a value of 1.
     *
     *@param  token  token
     */
    public void processToken(String token) {

        int tokenCount = 1;

        if (tokenCounts.containsKey(token)) {

            tokenCount = tokenCounts.get(token);
            tokenCount++;
            tokenCounts.put(token, tokenCount);

        } else {
            tokenCounts.put(token, tokenCount);
        }
    }


    /**
     *  Method calls a method to write the token information to the file. It
     *  handles exceptions for writing output.
     *
     *@param  inputFilePath  input file path
     */
    public void writeOutputFile(String inputFilePath) {

        PrintWriter out = null;

        String outputFilePath = properties.getProperty("output.dir")
                 + properties.getProperty("output.file.token.count");

        try {

            out = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputFilePath)));

            writeTokens(out);

        } catch (IOException ioException) {

            ioException.printStackTrace();

        } catch (Exception exception) {

            exception.printStackTrace();

        } finally {

            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception exception) {

                exception.printStackTrace();
            }

        }

    }


    /**
     *  Method writes all the unique tokens with their counts into a file.
     *
     *@param  out  PrintWriter object
     */
    private void writeTokens(PrintWriter out) {

        String delimiter = "\t";

        for (Map.Entry<String, Integer> entry : tokenCounts.entrySet()) {
        //for (int i = 0; i < 2; i++) {
            out.println(entry.getKey() + delimiter + entry.getValue());
        }
    }

}


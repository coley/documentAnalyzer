package java112.analyzer;

import java.util.*;
import java.io.*;

/**
 *  UniqueTokenAnalyzer class: This class will generate a file containing all
 *  the unique tokens in the input file. The file will have one token on each
 *  line. There will not be any duplicates in the file. This class implements
 *  the Analyzer interface.
 *
 *@author     Nicole LaBonte
 */

public class UniqueTokenAnalyzer implements Analyzer {

    private Set uniqueTokensList;
    private Properties properties;


    /**
     *  Basic constructor for UniqueTokenAnalyzer
     */
    public UniqueTokenAnalyzer() {
        uniqueTokensList = new TreeSet();
    }


    /**
     *  Constructor for the UniqueTokenAnalyzer object
     *
     *@param  properties  Description of the Parameter
     */
    public UniqueTokenAnalyzer(Properties properties) {
        this();
        this.properties = properties;
    }


    /**
     *  Gets the uniqueTokensList attribute of the UniqueTokenAnalyzer object
     *
     *@return    The uniqueTokensList value
     */
    public Set getUniqueTokensList() {
        return uniqueTokensList;
    }


    /**
     *  Method adds each unique token to the uniqueTokensList.
     *
     *@param  token  String token
     */
    public void processToken(String token) {
        uniqueTokensList.add(token);
    }


    /**
     *  Method calls a method to write the token information to the file. It
     *  handles exceptions for writing output.
     *
     *@param  inputFilePath   input file path
     */
    public void writeOutputFile(String inputFilePath) {

        PrintWriter out = null;

        String outputFilePath = properties.getProperty("output.dir")
                + properties.getProperty("output.file.unique");

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
     *  Method writes all the unique tokens into a file.
     *
     *@param  out  PrintWriter
     */
    private void writeTokens(PrintWriter out) {

        for (Iterator tokenIterator = uniqueTokensList.iterator();
                tokenIterator.hasNext(); ) {
            out.println(tokenIterator.next());
        }

    }

}


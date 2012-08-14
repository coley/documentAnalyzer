package java112.analyzer;

import java.util.*;
import java.io.*;

/**
 *  BigWordAnalyzer class: This Analyzer will generate a file of unique tokens 
 *  that are greater than or equal to a specified number, and this number is
 *  determined by the properties object. The file will have one token on each
 *  line. There will not be any duplicates in the file. This class implements
 *  the Analyzer interface.
 *
 *@author     Nicole LaBonte
 */

public class BigWordAnalyzer implements Analyzer {

    private Properties properties;
    //private Set bigWords;
    private Set<String> bigWords;
    private int minimumWordLength;


    /**
     *  Constructor for the BigWordAnalyzer object
     */
    public BigWordAnalyzer() {
        bigWords = new TreeSet<String>();
    }


    /**
     *  Constructor for the BigWordAnalyzer object
     *
     *@param  properties  properties object
     */
    public BigWordAnalyzer(Properties properties) {

        this();
        this.properties = properties;

        minimumWordLength =
                Integer.parseInt(properties.getProperty(
                "bigwords.minimum.length"));
    }


    /**
     *  Returns the value of properties.
     *
     *@return    The properties value
     */
    public Properties getProperties() {
        return properties;
    }


    /**
     *  Returns the value of minimumWordLength.
     *
     *@return    The minimumWordLength value
     */
    public int getMinimumWordLength() {
        return minimumWordLength;
    }


    /**
     *  Gets the bigWords attribute of the BigWordAnalyzer object
     *
     *@return    The bigWords value
     */
    //public Set getBigWords() {
    public Set<String> getBigWords() {
        return bigWords;
    }


    /**
     *  Method adds each unique token to bigWords if the lenght of the token
     *  is greater than or equal to the minimumWordLength.
     *
     *@param  token  String token
     */
    public void processToken(String token) {

        if (token.length() >= minimumWordLength) {
            bigWords.add(token);
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
                 + properties.getProperty("output.file.bigwords");

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

        for (String token : bigWords) {
            out.println(token);
        }

    }

}


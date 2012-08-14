package java112.analyzer;

import java.util.*;
import java.io.*;

/**
 *  TokenSizeAnalyzer class: This analyzer will determine the size
 *  distribution of the tokens in the input file.  The first part of the
 *  output will be a listing of sizes of tokens and the number of tokens
 *  that are that size. The second part of the output will
 *  display a histogram of the results. The display will be adjusted
 *  for each file. The value of each asterisk will be changed so that 
 *  the histogram doesn’t exceed the max length in the properties file and
 *  it will minimally display at least one “*”.
 *
 *@author     Nicole LaBonte
 */

public class TokenSizeAnalyzer implements Analyzer {

    private Map<Integer, Counter> tokenSizes;
    private Properties properties;
    private int maximumSize;


    /**
     *  Constructor for the TokenSizeAnalyzer object
     */
    public TokenSizeAnalyzer() { 
        tokenSizes = new TreeMap<Integer, Counter>();
    }

    /**
     *  Constructor for the TokenSizeAnalyzer object
     *
     *@param  properties  Properties object
     */
    public TokenSizeAnalyzer(Properties properties) {
        this();
        this.properties = properties;

        /*maximumSize =
                Integer.parseInt(properties.getProperty(
                "maximum.token.size"));*/

        maximumSize = 75;
    }


    /**
     *  Gets the tokenSizes attribute of the TokenSizeAnalyzer object
     *
     *@return    The tokenSizes value
     */
    //public Map getTokenSizes() {
    public Map<Integer, Counter> getTokenSizes() {
        return tokenSizes;
    }


    /**
     *  Gets the maximumSize attribute of the TokenSizeAnalyzer object
     *
     *@return    The maximumSize value
     */
    public int getMaximumSize() {
        return maximumSize;
    }


    /**
     *  Method processes the token input.
     *
     *@param  token  String token
     */
    public void processToken(String token) {

        addToken(token);
    }


    /**
     *  Method creates the histogram row.
     *
     *@param  numberOfIcons  number of icons
     *@return                String of the histogram row
     */
    private String determineHistogramRow(double numberOfIcons) {

        String histogramIcon = "*";
        String histogramRow = "";

        for (int index = 0; index < numberOfIcons; index++) {
            histogramRow += histogramIcon;
        }

        if (histogramRow.length() <= 0) {
            histogramRow += histogramIcon;
        }

        return histogramRow;
    }


    /**
     *  Method determines the scaling factor for the histogram.
     *
     *@return    scaling factor as a double
     */
    private double determineScalingFactor() {

        double maximumHistogramSize
                 = Double.parseDouble(properties.getProperty("maximum.token.size"));

        double maxTokenSize
                 = (double) determineMaxTokenFrequency();

        double scalingFactor = maximumHistogramSize / maxTokenSize;

        return scalingFactor;
    }


    /**
     *  Method determines the max token frequency
     *
     *@return    max token frequency as an int
     */
    public int determineMaxTokenFrequency() {

        int maxToken = 0;

        for (Map.Entry<Integer, Counter> entry : tokenSizes.entrySet()) {
        //for (int i = 0; i < 2; i++) {

            if (entry.getValue().getTokenCount() > maxToken) {
                maxToken = entry.getValue().getTokenCount();
            }
        }

        return maxToken;
    }


    /**
     *  Adds token lengths to the tokenSizes map.
     *
     *@param  token  token
     */
    private void addToken(String token) {

        int size = token.length();
        Counter tokenCount = new Counter();

        if (tokenSizes.containsKey(size)) {

            tokenSizes.get(size).increment();

        } else {

            tokenSizes.put(size, tokenCount);
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
                 + properties.getProperty("output.file.token.size");

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
     *  Writes sizes of tokens to the file
     *
     *@param  out  PrintWriter
     */
    private void writeTokenSize(PrintWriter out) {

        String delimiter = "\t";

        for (Map.Entry<Integer, Counter> entry : tokenSizes.entrySet()) {
        //for (int i = 0; i < 2; i++) {

            out.println(entry.getKey() + delimiter
                     + entry.getValue().getTokenCount());
        }
    }


    /**
     *  Writes histogram to the file
     *
     *@param  out  PrintWriter
     */
    public void writeHistogram(PrintWriter out) {

        double numberOfIcons = 0;
        double scalingFactor = determineScalingFactor();
        String delimiter = "\t";

        for (Map.Entry<Integer, Counter> entry : tokenSizes.entrySet()) {
        //for (int i = 0; i < 2; i++) {

            numberOfIcons = (double) entry.getValue().getTokenCount() 
                    * scalingFactor;

            out.println(entry.getKey() + delimiter
                     + determineHistogramRow(numberOfIcons));
        }

    }


    /**
     *  Writes output to the file
     *
     *@param  out  PrintWriter
     */
    private void writeTokens(PrintWriter out) {

        writeTokenSize(out);

        out.println();

        writeHistogram(out);

    }

}


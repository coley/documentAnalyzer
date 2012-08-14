package java112.analyzer;

import java.util.*;
import java.io.*;

/**
 *  SummaryReport class: This class generates the summary report for the
 *  Analyzer application. It will count all the tokens being analyzed and it
 *  will include this information along with a header into the report. This
 *  class implements the Analyzer interface.
 *
 *@author     Nicole LaBonte
 */

public class SummaryReport implements Analyzer {

    private int totalTokensCount;
    private Properties properties;


    /**
     *  Constructor for the SummaryReport object
     */
    public SummaryReport() { }


    /**
     *  Constructor for the SummaryReport object
     *
     *@param  properties  properties object
     */
    public SummaryReport(Properties properties) {
        this();
        this.properties = properties;
    }


    /**
     *  Gets the totalTokensCount attribute of the SummaryReport object
     *
     *@return    The totalTokensCount value
     */
    public int getTotalTokensCount() {
        return totalTokensCount;
    }


    /**
     *  Method counts all the tokens processed.
     *
     *@param  token  String token
     */
    public void processToken(String token) {

        totalTokensCount++;
    }


    /**
     *  Method calls a method to write the summary information to the file. It
     *  handles exceptions for writing output.
     *
     *@param  inputFilePath  input file path
     */
    public void writeOutputFile(String inputFilePath) {

        PrintWriter out = null;

        String outputFilePath = properties.getProperty("output.dir")
                 + properties.getProperty("output.file.summary");

        try {

            out = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputFilePath)));

            writeReportHeader(inputFilePath, out);

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
     *  Method writes the summary information to the file.
     *
     *@param  inputFilePath  input file path
     *@param  out            PrintWriter
     */
    private void writeReportHeader(String inputFilePath, PrintWriter out) {

        out.println("Application: "
                 + properties.getProperty("application.name"));

        out.println("Author: "
                 + properties.getProperty("author"));

        out.println("Email: "
                 + properties.getProperty("author.email.address"));

        out.println("Input File: " + inputFilePath);

        out.println("Analysis Timestamp: " + new Date());

        out.println("Total Token Count: " + totalTokensCount);

    }

}


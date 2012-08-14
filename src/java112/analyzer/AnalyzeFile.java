package java112.analyzer;

import java.io.*;
import java.util.*;

/**
 *  AnalyzeFile class: The main controlling class. It has an instance variable
 *  that holds the path to the input file named inputFilePath. It has an
 *  instance variable named propertiesFilePath that holds the path to the
 *  properties file. It has an instance variable for each Analyzer class named
 *  summaryReport, uniqueTokenAnalyzer, bigWordAnalyzer, and tokenCountAnalyzer.
 *  This class will run the analysis on the input file and call a method to
 *  write the output files that process the tokens from the input file.
 *
 *@author     Nicole LaBonte
 */

public class AnalyzeFile {

    private final static int VALID_ARGUMENT_COUNT = 2;

    private String inputFilePath;
    private Properties properties;
    private ArrayList<Analyzer> analyzers;

    /**
     *  The method will first test if 2 arguments have been entered by the user
     *  when running the application. If the correct number is not entered then
     *  the application must output a message to the command line asking for the
     *  right input and then terminate the program. Next, the method will assign
     *  the entered command line argument to the input path instance variable.
     *  Next, the method will create an instance of each Analyzer class and
     *  assign them to their instance variables. Next, the method will call
     *  methods to open the input file, loop through all the lines of the input
     *  file, and generate individual tokens.
     *
     *@param  arguments  command line arguments
     */
    public void runAnalysis(String[] arguments) {

        if (arguments.length != VALID_ARGUMENT_COUNT) {
            System.out.println("Enter the input file path and "
                     + "the properties configuration file path.");
            return;
        }

        inputFilePath = arguments[0];

        String propertiesFilePath = arguments[1];

        loadProperties(propertiesFilePath);

        initializeReports();

        readInputFile();

        writeAllOutputFiles();
    }


    /**
     *  Method instantiates the following reports: summaryReport,
     *  UniqueTokenAnalyzer, BigWordAnalyzer, and TokenCountAnalyzer.
     *
     */
    private void initializeReports() {

        analyzers = new ArrayList<Analyzer>();

        analyzers.add(new SummaryReport(properties));
        analyzers.add(new UniqueTokenAnalyzer(properties));
        analyzers.add(new BigWordAnalyzer(properties));
        analyzers.add(new TokenCountAnalyzer(properties));
        analyzers.add(new TokenSizeAnalyzer(properties));
        analyzers.add(new KeywordAnalyzer(properties));
    }


    /**
     *  Method loads the properties from the properties file.
     *
     *@param  propertiesFilePath  Properties file path
     */
    private void loadProperties(String propertiesFilePath) {

        //String filepath = "/analyzer.properties";

        properties = new Properties();

        try {
            properties.load(
                    this.getClass().getResourceAsStream(propertiesFilePath));
        } catch (IOException ioe) {
            System.out.println("Can't load the properties file");
            ioe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Problem: " + e);
            e.printStackTrace();
        }

    }


    /**
     *  Method calls a method to read the input file. It handles the exceptions
     *  from reading the input file.
     *
     */
    private void readInputFile() {

        BufferedReader input = null;

        try {
            input = new BufferedReader(new FileReader(inputFilePath));

            readContents(input);

        } catch (FileNotFoundException fileNotFound) {

            fileNotFound.printStackTrace();

        } catch (IOException ioException) {

            ioException.printStackTrace();

        } catch (Exception exception) {

            exception.printStackTrace();

        } finally {

            try {

                if (input != null) {

                    input.close();
                }
            } catch (Exception exception) {

                exception.printStackTrace();
            }
        }

    }


    /**
     *  Method loops through all the lines of the input file, splits up each
     *  line by non-word characters and stores in an array. Then, calls a method
     *  to perform the processing on the tokens from the file.
     *
     *@param  input            Buffered Reader
     *@exception  IOException  IO Exception
     */
    private void readContents(BufferedReader input) throws IOException {

        String inputLine = "";
        String[] tokenArray = null;

        while (input.ready()) {

            inputLine = input.readLine();
            tokenArray = inputLine.split("\\W");

            processAllTokens(tokenArray);
        }

    }


    /**
     *  Method calls methods to perform processing on the tokens in order to
     *  prepare various Analyzer reports. Tokens are only processed if they have
     *  a length greater than 0 (i.e. are not blank tokens)
     *
     *@param  tokenArray  array of all tokens
     */
    private void processAllTokens(String[] tokenArray) {

        for (String token : tokenArray) {
        //for (int index = 0; index < tokenArray.length - 1; index++) {

            if (token.length() > 0) {
                analyzerProcessTokens(token);
            }
        }
    }


    /**
     *  Method loops through all of the Analyzers in the analyzers ArrayList and
     *  calls the processToken method for each analyzer to process tokens.
     *
     *@param  token  token
     */
    private void analyzerProcessTokens(String token) {

        for (Analyzer anAnalyzer : analyzers) {
        //for (int i = 0; i < 2; i++) {
            anAnalyzer.processToken(token);
        }
    }


    /**
     *  Method calls other methods to write out the various Analyzer reports.
     *
     */
    private void writeAllOutputFiles() {

        for (Analyzer anAnalyzer : analyzers) {
        //for (int i = 0; i < 2; i++) {
            anAnalyzer.writeOutputFile(inputFilePath);
        }

        System.out.println("Files generated.");
    }

}



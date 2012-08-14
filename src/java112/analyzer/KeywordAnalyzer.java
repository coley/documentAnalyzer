package java112.analyzer;

import java.util.*;
import java.io.*;

/**
 *  KeywordAnalyzer Class: an analyzer class that will determine
 *  where keywords are in the input file.  The class will load a
 *  file of keywords, parse the file and store all the keywords
 *  in a Map that will use each keyword as the key to an element
 *  and an empty ArrayList as the element’s value. The location of the
 *  keyword file must be delivered to this class through a property in
 *  the analyzer.properties file. The file will be loaded before
 *  processing of the input file starts. For each token in the input file,
 *  this analyzer will check to see if the token is one of the keywords
 *  that were preloaded.  If the token is a keyword then the number
 *  position of the token in the input file will be added to the List
 *  associated with the keyword in the Map. If the token is not a keyword
 *  then no processing will take place. The report that this analyzer
 *  generates will be a listing of all the keywords and their locations
 *  in the input file. The output file will consist of a listing of the
 *  keywords and where each keyword occurs in the file.
 *
 *@author     Nicole LaBonte
 */

public class KeywordAnalyzer implements Analyzer {

    private Map<String, List<Integer>> keywordMap;
    private Properties properties;
    private int tokenOccurence;


    /**
     *  Constructor for the KeywordAnalyzer object
     */
    public KeywordAnalyzer() {
        keywordMap = new TreeMap<String, List<Integer>>();
        tokenOccurence = 0;
    }

    /**
     *  Constructor for the KeywordAnalyzer object
     *
     *@param  properties  Properties object
     */
    public KeywordAnalyzer(Properties properties) {

        this();
        this.properties = properties;

        readKeywordFile();
    }


    /**
     *  Gets the keywordMap attribute of the KeywordAnalyzer object
     *
     *@return    The keywordMap value
     */
    public Map<String, List<Integer>> getKeywordMap() {
        return keywordMap;
    }


    /**
     *  Method reads the input file and adds the keyword to the keywordMap
     *  with an empty list if the input line has a length greater than zero.
     *
     *@param  input           file input
     *@exception  IOException  IO Exception
     */
    private void readContents(BufferedReader input) throws IOException {

        String inputLine = "";
        List<Integer> aList;

        while (input.ready()) {

            inputLine = input.readLine();
            aList = new ArrayList<Integer>();

            if (inputLine.length() > 0) {
                keywordMap.put(inputLine, aList);
            }
        }
    }


    /**
     *  Method reads the input file from the properties file.  It calls
     *  the readContents method to parse the input.
     */
    private void readKeywordFile() {

        BufferedReader input = null;
        String inputFilePath = properties.getProperty("file.path.keywords");

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
     *  Method processes the token input.
     *
     *@param  token  String token
     */
    public void processToken(String token) {

        tokenOccurence++;

        addNumberPositions(token);

    }


    /**
     *  Method adds the token occurence frequency for the keywords in the
     *  keywordMap.
     *
     *@param  token  token String
     */
    private void addNumberPositions(String token) {

        if (keywordMap.containsKey(token)) {
            keywordMap.get(token).add(tokenOccurence);
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
                 + properties.getProperty("output.file.keyword");

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
     *  Method generates the string output for the occurence of tokens
     *  for the given keyword.
     *
     *@return    string of token occurrence
     */
    private String generateTokenCountList(List<Integer> tokenFrequencyList) {
    //private String generateTokenCountList() {

        String tokenFrequencyString = "[";
        int maximumDisplayWidth = 8;
        int currentWidth = 0;
        int index = 0;
        String lineSeparator = System.getProperty("line.separator");
        String commaString = "";

        for (int frequency : tokenFrequencyList) {
        //for (int i = 0; i < 2; i++) {

            if (index < tokenFrequencyList.size() - 1) {
                commaString = ", ";
            } else {
                commaString = "";
            }

            if (currentWidth < maximumDisplayWidth) {
                currentWidth++;
                tokenFrequencyString += frequency;
                tokenFrequencyString += commaString;

            } else {
                currentWidth = 0;
                tokenFrequencyString += lineSeparator;
                tokenFrequencyString += frequency;
                tokenFrequencyString += commaString;
            }

            index++;
        }

        tokenFrequencyString += "]";

        return tokenFrequencyString;
    }


    /**
     *  Method writes token information to the file.
     *
     *@param  out  PrintWriter
     */
    private void writeTokens(PrintWriter out) {

        String tokenFrequencyString;

        for (Map.Entry<String, List<Integer>> entry : keywordMap.entrySet()) {
        //for (int i = 0; i < 2; i++) {

            tokenFrequencyString
                     = generateTokenCountList(entry.getValue());

            out.println(entry.getKey() + " =");
            out.println(tokenFrequencyString);
            out.println();
        }
    }

}


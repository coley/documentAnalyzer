package java112.analyzer;

/**
 *  AnalyzerDriver class: The class will instantiate an instance of the
 *  Analyzer's main processing class. The class will call the main processing
 *  method of the main class passing the command line arguments array to the
 *  method.
 *
 *@author     Nicole LaBonte
 */

public class AnalyzerDriver {

    /**
     *  The main program for the AnalyzerDriver class
     *
     *@param  arguments  The command line arguments
     */
    public static void main(String[] arguments) {

        AnalyzeFile analyzer = new AnalyzeFile();
        analyzer.runAnalysis(arguments);
    }

}


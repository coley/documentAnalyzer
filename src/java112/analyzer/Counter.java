package java112.analyzer;

/**
 *  Counter class:  Class counts tokens.
 *
 *@author     Nicole LaBonte
 */

public class Counter {

    private int tokenCount;


    /**
     *  Basic constructor for Count
     */
    public Counter() {
        tokenCount = 1;
    }


    /**
     *  Returns the value of tokenCount.
     *
     *@return    The tokenCount value
     */
    public int getTokenCount() {
        return tokenCount;
    }


    /**
     *  Method increments the token count by 1.
     */
    public void increment() {
        tokenCount++;
    }


    /**
     *  Method returns a string of the token count.
     *
     *@return    token count string
     */
    public String toString() {

        return "token count: " + tokenCount;
    }

}


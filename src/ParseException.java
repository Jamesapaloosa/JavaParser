/**
 * <h1>ParseException</h1>
 * This class handles methods used in ParseException
 * Assignment 2 CPSC449
 * @author  Daniel Dastoor, James Gilders, Carlin Liu, Teresa Van, Thomas Vu
 * @version 1.0
 * @since   2017-03-17
 */
public class ParseException extends RuntimeException {

    private int index;
    private String input;

    public ParseException() { super(); }

    /**
     * This constructor sets the variables
     * @param msg Message to be printed
     * @param index Index used for error arrow
     * @param input Input used
     */
    public ParseException(String msg, int index, String input) {
        super(msg);
        this.index = index;
        this.input = input;
    }

    /**
     * This constructor sets variables using Node
     * @param expression Expression that contains error
     * @param n Node used for offset
     * @param input Input used
     */
    public ParseException(String expression, ParseTree.Node n, String input) {
        super("Matching function for '" + expression + "' not found at offset " + n.start);
        this.index = n.start;
        this.input = input;
    }

    /**
     * Prints out a "---^" error arrow based on the index.
     */
    public void printErrorMessage(){
        System.out.println(getMessage());
        System.out.println(input);
        int d = 0;
        while(d < index){
            System.out.print("-");
            d++;
        }
        System.out.println("^");
    }

}

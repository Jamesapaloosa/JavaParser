import java.util.regex.MatchResult;

public class ParseException extends RuntimeException {

    private int index;
    private String input;

    public ParseException() { super(); }

    public ParseException(String msg, String input) {
        super(msg);
        this.index = input.length();
        this.input = input;
    }

    public ParseException(String expression, ParseTree.Node n, String input) {
        super("Matching function for '" + expression + "' not found at offset " + n.start);
        this.index = n.start;
        this.input = input;
    }

    public void printErrorMessage(){
        System.out.println(getMessage());
        System.out.println(input);
        int d = 0;
        while(d < index){
            System.out.print("-");
            d++;
        }
        System.out.println("^");
//		if(verbose == true){
//				e.printStackTrace();
//		}
    }

}

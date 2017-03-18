import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
/**
 * <h1>ParseTree</h1>
 * This class handles methods used in create and validate the parseTree
 * Assignment 2 CPSC449
 * @author  Daniel Dastoor, James Gilders, Carlin Liu, Teresa Van, Thomas Vu
 * @version 1.0
 * @since   2017-03-17
 */
public class ParseTree {

    public String input;
    public Node root;
    private ArrayList<MatchResult> matches;
    private JarExecutor jarExec;

    /**
     * This constructor builds and tokenizes the parseTree. It will then validate
     * @param input The string input of the expression
     * @param jarExec The jarExecutor that is used
     * @throws ParseException Thrown when the expression cannot be parsed
     */
    public ParseTree(String input, JarExecutor jarExec) throws ParseException {
        this.input = input;
    	this.jarExec = jarExec;
        if (input.charAt(0) == ')') throw new ParseException("Unexpected character encountered at offset " + 0, 0, input);
        Pattern p = Pattern.compile("^([^\"]*\"[^\"]*[^\"]*\"[^\"]*)*[^\"]*(?<error>\"[^\"]*)$");
        Matcher m = p.matcher(input);
        if (m.find()) {
            throw new ParseException("Encountered end-of-input while reading string beginning at offset " + m.start("error") + " at offset " + input.length(), input.length(), input);
        }
        tokenize();
        constructTree();
        validate(root);
    }

    /**
     * Evaluate the root and print out the result
     */
    public void getEvaluation() throws ParseException { System.out.println(evaluate(root)); }

    /**
     * This method will evaluate a given node. It will check if the node is empty and will execute the method at the location
     * @param n Node to be evaluated
     * @return The object result of the execution
     * @throws ParseException Throw when the method cannot be parsed
     */
    private Object evaluate(Node n) throws ParseException {
        try {
            if (n.children.isEmpty()) {
                return n.isValue ? n.value : jarExec.executeMethod((String) n.value, (ArrayList<Object>) n.children.stream().map(c -> c.value).collect(Collectors.toList()));
            } else {
                return jarExec.executeMethod((String) n.value, (ArrayList<Object>) n.children.stream().map(this::evaluate).collect(Collectors.toList()));
            }
        } catch (NoSuchMethodException | IllegalArgumentException e) {
            throw new ParseException(e.getMessage(), n, input);
        }
    }

    /**
     * This method will tokenize the  input
     */
    private void tokenize() {
        Pattern p = Pattern.compile("\"[^\"]*\"|([\\S&&[^()]]+)|([()])");
        Matcher m = p.matcher(input);
        matches = new ArrayList<>();
        while (m.find()) {
            matches.add(m.toMatchResult());
        }
    }

    /**
     * This method constructs the tree
     * @throws ParseException Throw when it cannot be parsed
     */
    private void constructTree() throws ParseException {
        try {
            Stack<Node> stack = new Stack<>();
            Node t, t1, t2;
            for (MatchResult m : matches) {
                if (m.group().equals(")")) {
                    if (stack.peek().value.equals("(")) {
                        throw new ParseException("Missing identifier in funcall at offset " + m.start(), m.start(), input);
                    }
                    ArrayList<Node> children = new ArrayList<>();
                    t1 = stack.pop();
                    t2 = stack.peek();
                    while (!t2.value.equals("(")) {
                        children.add(t1);
                        t1 = stack.pop();
                        t2 = stack.peek();
                    }
                    Collections.reverse(children);
                    t1.children = children;
                    stack.pop();
                    stack.push(t1);
                } else {
                    t = new Node(m);
                    stack.push(t);
                }
            }
            t = stack.peek();
            stack.pop();
            root = t;
            if (!stack.isEmpty()) {
                throw new ParseException("Encountered end-of-input while reading expression beginning at offset 0 at offset " + input.length(), input.length(), input);
            }
            parse(root);
        } catch (EmptyStackException e) {
            throw new ParseException("Encountered end-of-input while reading expression beginning at offset 0 at offset " + input.length(), input.length(), input);
        }
    }

    /**
     * Check if the node has children, if empty parse. Else loop through children and parse
     * @param n Node to be parsed
     */
    private void parse(Node n) {
        if (n.children.isEmpty()) {
            n.parse();
        } else {
            for (Node c : n.children) {
                parse(c);
            }
        }
    }

    /**
     * Validate a node and its children
     * @param n Node to be validated
     */
    private void validate(Node n) {
        n.validate();
        for (Node c : n.children) {
            validate(c);
        }
    }

    /**
     * Class type node used to represent node objects inside parse tree
     */
    public class Node {

        private Object value;
        public int start;
        private boolean isValue = false;
        private ArrayList<Node> children;

        private Node(MatchResult match) {
            value = match.group();
            start = match.start();
            children = new ArrayList<>();
        }

        private void parse() {
            String str = value.toString();
            if (str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"') {
                value = str.substring(1, str.length() - 1);
                isValue = true;
            } else {
                try {
                    value = Integer.parseInt(str);
                    isValue = true;
                } catch (NumberFormatException e1) {
                    try {
                        value = Float.parseFloat(str);
                        isValue = true;
                    } catch (NumberFormatException e2) {
                        isValue = false;
                    }
                }
            }

        }

        private void validate() {
            if (!isValue && ((start - 1 < 0 || input.charAt(start - 1) != '(') || ((String) value).matches("(\\W)|(\\b[0-9].*\\b)"))) {
                throw new ParseException("Unexpected character encountered at offset " + start, start, input);
            }
        }

    }

}

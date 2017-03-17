import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParseTree {

    public String input;
    public Node root;
    private ArrayList<MatchResult> matches;
    private JarExecutor jarExec;

    //TODO: Error on "()"
    //TODO: Add constructor check for <value> <anything else> error case
    //TODO: Add constructor check for <expression> <anything else> error case

    public ParseTree(String input, JarExecutor jarExec) throws ParseException {
        this.input = input;
    	this.jarExec = jarExec;
        Pattern p = Pattern.compile("^([^\"]*\"[^\"]*[^\"]*\"[^\"]*)*[^\"]*(?<error>\"[^\"]*)$");
        Matcher m = p.matcher(input);
        if (m.find()) {
            throw new ParseException("Encountered end-of-input while reading string beginning at offset " + m.start("error") + " at offset " + input.length(), input.length(), input);
        }
        tokenize();
        constructTree();
        validate(root);
    }

    public void getEvaluation() {
        try {
            Object result = evaluate(root);
            System.out.println(result);
        } catch (ParseException e) {
            e.printErrorMessage();
        }
    }

    private Object evaluate(Node n) throws ParseException {
        try {
            if (n.children.isEmpty()) {
                if (n.isValue) {
                    return n.value;
                } else {
                    return jarExec.executeMethod((String) n.value, (ArrayList<Object>) n.children.stream().map(c -> c.value).collect(Collectors.toList()));
                }
            } else {
                return jarExec.executeMethod((String) n.value, (ArrayList<Object>) n.children.stream().map(this::evaluate).collect(Collectors.toList()));
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
            throw new ParseException(e.getMessage(), n, input);
        }
    }
	
    private void tokenize() {
        Pattern p = Pattern.compile("\"[^\"]*\"|([\\S&&[^()]]+)|([()])");
        Matcher m = p.matcher(input);
        matches = new ArrayList<>();
        while (m.find()) {
            matches.add(m.toMatchResult());
        }
    }

    private void constructTree() throws ParseException {
        Stack<Node> stack = new Stack<>();
        Node t, t1, t2;
        for (MatchResult m : matches) {
            if (m.group().equals(")")) {
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
    }

    private void parse(Node n) {
        if (n.children.isEmpty()) {
            n.parse();
        } else {
            for (Node c : n.children) {
                parse(c);
            }
        }
    }

    private void validate(Node n) {
        n.validate();
        for (Node c : n.children) {
            validate(c);
        }
    }

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
            if (!isValue && (start - 1 < 0 || input.charAt(start - 1) != '(')) {
                throw new ParseException("Unexpected character encountered at offset " + start, start, input);
            } else if (!isValue && ((String) value).matches("(\\W)|(\\b[0-9].*\\b)")) {
                throw new ParseException("Unexpected character encountered at offset " + start, start, input);
            }
        }

    }

}

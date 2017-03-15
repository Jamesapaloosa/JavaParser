import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;
import java.util.stream.Collectors;

public class ParseTree {

    public String input;
    public Node root;
    private ArrayList<MatchResult> matches;

    public ParseTree(String input) {
        this.input = input;
        tokenize();
        constructTree();
        validate(root);
    }

    public Object getEvaluation() {
        return evaluate(root);
    }

//    private String evaluate(Node n) {
//        if (n.children.isEmpty()) {
//            return "(" + n.value + ")";
//        } else {
//            String result = "(" + n.value + " ";
//            for (Node c : n.children) {
//                result += evaluate(c);
//            }
//            result += ")";
//            return result;
//        }
//    }

    private Object evaluate(Node n) {
        if (n.children.isEmpty()) {
            if (n.isValue) {
                return n.value;
            } else {
                return executeMethod((String) n.value, (ArrayList<Object>) n.children.stream().map(c -> c.value).collect(Collectors.toList()));
            }
        } else {
            return executeMethod((String) n.value, (ArrayList<Object>) n.children.stream().map(this::evaluate).collect(Collectors.toList()));
        }
    }

    //This function should not be in this file, and is only for temporary testing!
    private Object executeMethod(String function, ArrayList<Object> parameters) {
        String result = "("+function+(parameters.isEmpty() ? "" : "ExecutedWithArgs");
        for (Object p : parameters) {
            result += p.toString();
        }
        result += ")";
        return result;
    }

    private void tokenize() {
        Pattern p = Pattern.compile("\".*\"|([\\S&&[^()]]+)|([()])");
        Matcher m = p.matcher(input);
        matches = new ArrayList<>();
        while (m.find()) {
            matches.add(m.toMatchResult());
        }
    }

    private void constructTree() {
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
            System.out.println("Bracket mismatch error at: " + (input.length() - 1));
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

        public Object value;
        public int start;
        public boolean isValue = false;
        private ArrayList<Node> children;

        public Node(MatchResult match) {
            value = match.group();
            start = match.start();
            children = new ArrayList<>();
        }

        public void parse() {
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

        public void validate() {
            if (!isValue && (start - 1 < 0 || input.charAt(start - 1) != '(')) {
                System.out.println("Error at: " + start);
            } else if (!isValue && ((String) value).matches("(\\W)|(\\b[0-9].*\\b)")) {
                System.out.println("Error at: " + start);
            }
        }

    }

    public static void main(String[] args) {
        //ParseTree t = new ParseTree("(add 100 (div 9 3 2) 5 (mul 4 5))");
        //ParseTree t = new ParseTree("(add 100 3.6 (mul 10 (len \"Hello world!\")))");
        ParseTree t = new ParseTree("(add 5 (test 6 9 2.8 (_8most_)))");
        //ParseTree t = new ParseTree("\"asdf\" 4 5 2");
        // TODO: THE ABOVE NEEDS TO FAIL!
        // TODO: MISMATCHED QUOTE CHARACTERS NEED TO FAIL!
        String test = t.getEvaluation().toString();
    }

}

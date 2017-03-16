
public class ReflectiveParser {

    public static void main(String[] args) {
        // do crap here, Carlin

        JarExecutor jarExec = new JarExecutor("commands.jar", "Commands");
        try {
            ParseTree p = new ParseTree("(add 100 (mul 10 (len \"Hello world!\"))", jarExec);
            p.getEvaluation();
        } catch (ParseException e) {
            e.printErrorMessage();
        }
    }

}

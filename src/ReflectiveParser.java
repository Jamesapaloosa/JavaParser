/**
* <h1>ReflectiveParser</h1>
* This class implements the main method and handles the mainloop logic and how to parse the command line arguments
* Assignment 2 CPSC449
* @author  Daniel Dastoor, James Gilders, Carlin Liu, Teresa Van, Thomas Vu
* @version 1.0
* @since   2017-03-17
*/
public class ReflectiveParser {

    public static Boolean Verbose = false;
    public static Boolean Run = true;
    public static String MenuC;
    public static Interface MainI = new Interface();
    public static JarExecutor jarExec;

    public static void main(String[] args) {

        ConsoleParse cp = new ConsoleParse();

        String jarString = "";
        String functionClassName = "";
		Interface MainI = new Interface();
		
        if (args.length == 0) {

            Verbose = cp.ParseProgramInput0(args, Verbose);
            System.exit(0);

        } else if (args.length == 1) {

            Verbose = cp.ParseProgramInput1(args, Verbose);
            System.exit(0);

        } else if (args.length == 2) {

            if (args[0].charAt(0) == '-' && args[1].endsWith(".jar")) {

                Verbose = cp.ParseProgramInput(args, Verbose);
                    jarString = args[1];
                    functionClassName = "Commands";
                    //jarExec = new Parser(jarString, functionClassName, Verbose);
                    jarExec = new JarExecutor(jarString, functionClassName);
					MainI.PrintMenu();
            } else {
                    jarString = args[0];
                    functionClassName = args[1];
                    jarExec = new JarExecutor(jarString, functionClassName);
					MainI.PrintMenu();
            }

            mainLoop();

        } else if (args.length == 3) {

            Verbose = cp.ParseProgramInput(args, Verbose);
                if (args[1].endsWith(".jar")) {
                    jarString = args[1];
                } else {
                    System.out.println("This program requires a jar file as the first command line argument (after any qualifiers)");
                    System.exit(-3);
                }
                functionClassName = args[2];
                jarExec = new JarExecutor(jarString, functionClassName);
				MainI.PrintMenu();

            mainLoop();
        } else {

            System.out.println("This program takes at most two command line arguments");
            System.exit(-2);

        }

    }

    public static void mainLoop() {
        while (Run == true) {
            System.out.print("> ");
            MenuC = MainI.GetInput().trim();
            switch (MenuC) {
                case "q":
                    Run = false;
                    MainI.PrintBye();
                    break;

                case "v":
                    if (Verbose == false) {
                        Verbose = true;
                        MainI.PrintVerboseOn();
                        //jarExec.SetVerbose(true);
                    } else {
                        MainI.PrintVerboseOff();
                        Verbose = false;
                        //jarExec.SetVerbose(false);
                    }
                    break;

                case "f":
                    //jarExec.PrintFunctions();
                    break;

                case "?":
                    MainI.PrintMenu();
                    break;

                case "":
                    break;

                default:
                    try {
                        ParseTree p = new ParseTree(MenuC, jarExec);
                        p.getEvaluation();
                    } catch (ParseException e) {
                        e.printErrorMessage();
                    }
            }
        }
    }
}

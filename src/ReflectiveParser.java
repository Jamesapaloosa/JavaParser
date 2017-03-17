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
	/**
	* Main method that handles the different lengths of input from the command line. The method will individually handle console
	* arguments of length 0, 1, 2,3 and >3. For length 0 and 1 it will call the proper ParseProgramInput function and will exit the
	* program after. For length 2 it will check if the first character of the first string is '-' and ends with ".jar". If it does
	* it will defualt the class name to be "Commands" and will call the jarexecutor will the default. If it is not, it will assume 
	* the console input is in the form <Jar file name> <class name> and will call the jarexecutor accordingly. It will then call the
	* mainloop. If the length is 3, It will check if the 2nd argument has a jar file. If it does not it will print a error message.
	* It will assume the form of the command line arguments to be <verbose> <Jar file name> <class name> and will parse the input 
	* accordingly to call jarexecutor. It will then call mainloop. If it is greater than 3 it will give an error message.
	* @param args an array of strings containing the command line input
	* @return none
	*/
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
		//jarExec = new Parser(jarString, functionClassName, Verbose);
                jarExec = new JarExecutor(jarString, functionClassName);
				MainI.PrintMenu();

            mainLoop();
        } else {

            System.out.println("This program takes at most two command line arguments");
            System.exit(-2);

        }

    }
	/**
	* The mainLoop method is used to handle the menu logic. The loop will recieve input from the interface and will compare it with
	* the menu options of q, v, f and ?. It will call the corresponding functions and will end only when case 'q' is called.
	* @param None
	* @return void  
	*/	
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

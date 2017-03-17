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
//                try {
                    jarString = args[1];
                    functionClassName = "Commands";
                    //jarExec = new Parser(jarString, functionClassName, Verbose);
                    jarExec = new JarExecutor(jarString, functionClassName);
					MainI.PrintMenu();
//                } catch (ClassNotFoundException e) {
//                    System.out.println("Could not find class: " + functionClassName);
//                    System.exit(0);
//                } catch (Exception f) {
//
//                }

            } else {

//                try {
                    jarString = args[0];
                    functionClassName = args[1];
                    jarExec = new JarExecutor(jarString, functionClassName);
					MainI.PrintMenu();
//                } catch (ClassNotFoundException e) {
//                    System.out.println("Could not find class: " + functionClassName);
//                    jarExec = new Parser();
//                    System.exit(0);
//                } catch (Exception f) {

                //}

            }

            mainLoop();

        } else if (args.length == 3) {

            Verbose = cp.ParseProgramInput(args, Verbose);

//            try {
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
//            } catch (Exception e) {
//                System.out.println("asdasdasd");
//                jarExec = new Parser();
//                System.exit(0);
//            }

            mainLoop();
        } else {

            System.out.println("This program takes at most two command line arguments");
            System.exit(-2);

        }


//        try {
//            ParseTree p = new ParseTree("(add 100 (mul 10 (len \"Hello world!\"))", jarExec);
//            p.getEvaluation();
//        } catch (ParseException e) {
//            e.printErrorMessage();
//        }
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

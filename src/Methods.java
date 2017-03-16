	//Assignment 2 CPSC449
	//James Gilders 10062731
	//Carlin Liu 10123584
	//Version March 1.14.55


import java.lang.reflect.*;
public class Methods
{
	public static Boolean Verbose = false;
	public static Boolean Run = true;
	public static String MenuC;
	public static Interface MainI = new Interface();
	public static Parser P;

	//Main Starts here
	
	public static void main(String[] args)
	{
		//Main variables

		ConsoleParse cp = new ConsoleParse();
		
		String jarString = "";
		String functionClassName = "";
		
		//Parse over input
		if(args.length == 0)
		{
			Verbose = cp.ParseProgramInput0(args, Verbose);
		}
		else if(args.length == 1)
		{
			Verbose = cp.ParseProgramInput1(args, Verbose);
		}
		else if(args.length == 2)
		{
			try
			{
				jarString = args[0];
				functionClassName = args[1];
				P = new Parser(jarString, functionClassName);
			}
			catch(ClassNotFoundException e)
			{
				System.out.println("Could not find class: " + functionClassName);
				P = new Parser();
				System.exit(0);
			}
			catch(Exception f)
			{
				// System.out.println("Could not find jar: " + jarString);
				// System.exit(0);
			}

			mainLoop();
		}
		else
		{
			System.out.println("else");
			Verbose = cp.ParseProgramInput(args, Verbose);
			
			try
			{
				jarString = args[1];
				functionClassName = args[2];
				P = new Parser(jarString, functionClassName);
			}
			catch(Exception e)
			{
				P = new Parser();
				System.exit(0);
			}
			
			mainLoop();
			
		}
			
	}
	
	public static void mainLoop()
	{
		while(Run == true)
		{
			System.out.print("> ");
			MenuC = MainI.GetInput();
			switch(MenuC)
			{
				case "q":
					Run = false;
					MainI.PrintBye();
					break;

				case "v":
					if(Verbose == false)
					{
						Verbose = true;
						MainI.PrintVerboseOn();
						P.SetVerbose(true);
					}
					else
					{
						MainI.PrintVerboseOff();
						Verbose = false;
						P.SetVerbose(false);
					}
					break;

				case "f":
					P.PrintFunctions();
					break;

				case "?":
					MainI.PrintMenu();
					break;

				default:
					P.ParseExpression(MenuC);
			}
		}
	}
	
}

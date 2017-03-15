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
		
		String functionClassName = "";
		
		//Parse over input
		if(args.length == 0)
		{
			System.out.println("len 0");
			Verbose = cp.ParseProgramInput0(args, Verbose);
		}
		else if(args.length == 1)
		{
			System.out.println("len 1");
			Verbose = cp.ParseProgramInput1(args, Verbose);
		}
		else
		{
			System.out.println("else");
			Verbose = cp.ParseProgramInput1(args, Verbose);
			
			try
			{
				String[] jarString = args[1].split("\\.");
				//set jar name here. if not found, catch
			}
			catch(Exception e)
			{
				System.out.println("Could not find class: " + args[1]);
			}
			try
			{
				functionClassName = args[2];
				P = new Parser(functionClassName);
			}
			catch(Exception e)
			{
				System.out.println("Could not find class: " + functionClassName);
				P = new Parser();
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
					}
					else
					{
						MainI.PrintVerboseOff();
						Verbose = false;
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

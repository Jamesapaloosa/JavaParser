//Assignment 2 CPSC449
//Daniel Dastoor, James Gilders, Carlin Liu, Teresa Van, Thomas Vu

import java.lang.reflect.*;
import java.util.*;

public class ConsoleParse
{
	/**
	*Method that parses console input that has length 0. It will create a new interface and will print the synopsis to console.
	* @param input An array containing the console inputs
	* @param Verbose The current boolean value of Verbose
	* @return boolean Containing the updated status of verbose 
	*/
	public boolean ParseProgramInput0(String[] input, boolean Verbose)
	{
		Interface MainI = new Interface();
		MainI.PrintSyn2();
		
		return(Verbose);
	}
	/**
	*Method that parses console input that has a length of 1. The function will first create a new interface for all the printing
	*and will then set default variables. It will then parse the first character of the first string and check if it contains '-'.
	*It will then check the same character on the next position. It it falls under '--' it will check for the qualifiers of help
	*and verbose. If it doesnt match it will exit or check the input if it is '-'. The method will then loop through the entire 
	*string and will compare to the available parameters of 'h', '?' and 'v'. If these match it will set the corresponding boolean.
	*If it doesn't match it will print out an error message. At the end of the method the booleans are checked to see which prints 
	*need to be made.
	* @param input An array containing the console inputs
	* @param Verbose The current boolean value of Verbose
	* @return boolean Containing the updated status of verbose  
	*/
	public boolean ParseProgramInput1(String[] input, boolean Verbose) 
	{
		Interface MainI = new Interface();
		int i1 = 1;
		int i2 = 1;
		int j1 = input[0].length();
		int j2 = input[0].length();
		boolean noError = true;	
		boolean runH = false;
				
		if(input[0].charAt(0) == '-')
		{
			if(input[0].charAt(1) == '-')
			{
				if(input[0].compareToIgnoreCase("--help") == 0)
				{
					MainI.PrintMenu();
				}
				else if(input[0].compareToIgnoreCase("--verbose") == 0)
				{
					System.out.println("This program requires a jar file as the first command line argument (after any qualifiers)");
					System.exit(-3);
				}
				else
				{
					System.out.println("Unrecognized qualifier " + input[0] +".");
					MainI.PrintSyn2();
					System.exit(-1);
				}
			}
			else
			{
				while(i1 < j1)
				{
					char x = input[0].charAt(i1);
					
					if(x == 'h')
					{
						runH = true;
					}
					else if(x == '?') 
					{
						runH = true;
					}
					else if(x == 'v')
					{
						System.out.println("This program requires a jar file as the first command line argument (after any qualifiers)");
						System.exit(-3);
					}
					else 
					{
						noError = false;
						System.out.println("Unrecognized qualifier '" + input[0].charAt(i1) + "' in '"+ input[0] + "'.");
						MainI.PrintSyn2();
						System.exit(-1);
					}
					i1++;
				}
				
				
			}
			if(noError == true)
			{
				if(runH == true)
				{
					MainI.PrintSyn();
				}	
			}

		}
		else
		{
			System.out.println("Unrecognized qualifier '" + input[0] +"'.");
			MainI.PrintSyn2();
			System.exit(-1);
		}
		return(Verbose);
	}	
	/**
	*Method that parses console input that has a length of 2 or more. The function will first create a new interface for all the printing
	*and will then set default variables. It will then parse the first character of the first string and check if it contains '-'.
	*It will then check the same character on the next position. It it falls under '--' it will check for the qualifiers of help
	*and verbose. If it doesnt match it will exit or check the input if it is '-'. The method will then loop through the entire 
	*string and will compare to the available parameters of 'h', '?' and 'v'. If these match it will set the corresponding boolean.
	*or an error message. At the end of the method the booleans are checked to see which prints need to be made.
	* @param input An array containing the console inputs
	* @param Verbose The current boolean value of Verbose
	* @return boolean Containing the updated status of verbose  
	*/
	public boolean ParseProgramInput(String[] input, boolean Verbose)
	{
		Interface MainI = new Interface();
		int i1 = 1;
		int i2 = 1;
		int j1 = input[0].length();
		int j2 = input[0].length();
		boolean noError = true;	
		boolean runV = false;
				
		if(input[0].charAt(0) == '-')
		{
			if(input[0].charAt(1) == '-')
			{
				if(input[0].compareToIgnoreCase("--help") == 0)
				{
					System.out.println("Qualifier --help (-h, -?) should not appear with any command-line arguments.");
					MainI.PrintSyn2();
					System.exit(-4);
				}
				else if(input[0].compareToIgnoreCase("--verbose") == 0)
				{
					if(Verbose == false)
					{
						Verbose = true;
						//MainI.PrintVerboseOn();
					}
					else
					{
						//MainI.PrintVerboseOff();
						Verbose = false;
					}
				}
				else
				{
					System.out.println("Unrecognized qualifier " + input[0] +".");
					MainI.PrintSyn2();
					System.exit(-1);
				}
			}
			else
			{
				while(i1 < j1)
				{
					char x = input[0].charAt(i1);
					if(x == 'h')
					{
						System.out.println("Qualifier --help (-h, -?) should not appear with any command-line arguments.");
						MainI.PrintSyn2();
						System.exit(-4);
					}
					else if(x == '?') 
					{
						System.out.println("Qualifier --help (-h, -?) should not appear with any command-line arguments.");
						MainI.PrintSyn2();
						System.exit(-4);
					}
					else if(x == 'v')
					{
						runV = true;
					}
					else 
					{
						noError = false;
						System.out.println("Unrecognized qualifier '" + input[0].charAt(i1) + "' in '"+ input[0] +"'.");
						MainI.PrintSyn2();
						System.exit(-1);
					}
					i1++;
				}	
			}
			if(noError == true)
			{
				if(runV == true)
				{
					if(Verbose == false)
					{
						Verbose = true;
						//MainI.PrintVerboseOn();
					}
					else
					{
						//MainI.PrintVerboseOff();
						Verbose = false;
					}
				}
			}
		}
		else
		{
			System.out.println("Unrecognized qualifier '" + input[0] +"'.");
			MainI.PrintSyn2();
			System.exit(-1);
		}
		return(Verbose);
	}
}

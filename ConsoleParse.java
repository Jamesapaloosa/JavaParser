import java.lang.reflect.*;
import java.util.*;

public class ConsoleParse
{
	public boolean ParseProgramInput0(String[] input, boolean Verbose)
	{
		Interface MainI = new Interface();
		MainI.PrintSyn2();
		
		return(Verbose);
	}
	
	public boolean ParseProgramInput1(String[] input, boolean Verbose) 
	{
		Interface MainI = new Interface();
		int i1 = 1;
		int i2 = 1;
		int j1 = input[0].length();
		int j2 = input[0].length();
		boolean noError = true;	
		boolean runH = false;
		boolean runV = false;
				
		if(input[0].charAt(0) == '-')
		{
			//System.out.println("1");
			if(input[0].charAt(1) == '-')
			{
				//System.out.println("2");
				if(input[0].compareToIgnoreCase("--help") == 0)
				{
					//System.out.println("2.1");
					MainI.PrintMenu();
					//break;
				}
				else if(input[0].compareToIgnoreCase("--verbose") == 0)
				{
					//System.out.println("2.2");
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
					//break;
				}
				else
				{
					System.out.println("Unrecognized " + input[0]);
					MainI.PrintSyn2();
				}
			}
			else
			{
				//check to see if all chars valid
				while(i1 < j1)
				{
					//use AND and print out char 0
					//System.out.println("index "+i1);
					char x = input[0].charAt(i1);
					
					if(x == 'h')
					{
						//System.out.println("h");
						runH = true;
						//continue;
					}
					else if(x == '?') 
					{
						//System.out.println("?");
						runH = true;
						//continue;
					}
					else if(x == 'v')
					{
						//System.out.println("v");
						runV = true;
						//continue;
					}
					else 
					{
						noError = false;
						System.out.println("Unrecognized qualifier '" + input[0].charAt(i1) + "' in '"+ input[0] + "' ");
						MainI.PrintSyn2();
						break;
					}
					i1++;
				}
				
				
			}
			if(noError == true)
			{
				if(runH == true)
				{
					//System.out.println("running help");
					MainI.PrintSyn();
				}	
				if(runV == true)
				{
					//System.out.println("running verbose");
					if(Verbose == false)
					{
						Verbose = true;
						MainI.PrintVerboseOn();
						//System.out.println("t");
					}
					else
					{
						MainI.PrintVerboseOff();
						Verbose = false;
						//System.out.println("f");
					}
					
				}
			}

		}
		return(Verbose);
	}
	
	
	public boolean ParseProgramInput(String[] input, boolean Verbose)
	{
		Interface MainI = new Interface();
		//System.out.println("Console Parser");
		int i1 = 1;
		int i2 = 1;
		int j1 = input[1].length();
		int j2 = input[1].length();
		boolean noError = true;	
		boolean runH = false;
		boolean runV = false;
				
		if(input[1].charAt(0) == '-')
		{
			//System.out.println("1");
			if(input[1].charAt(1) == '-')
			{
				//System.out.println("2");
				if(input[1].compareToIgnoreCase("--help") == 0)
				{
					//System.out.println("2.1");
					MainI.PrintMenu();
					//break;
				}
				else if(input[1].compareToIgnoreCase("--verbose") == 0)
				{
					//System.out.println("2.2");
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
					//break;
				}
				else
				{
					System.out.println("Unrecognized " + input[1]);
					MainI.PrintSyn2();
				}
			}
			else
			{
				//check to see if all chars valid
				while(i1 < j1)
				{
					//use AND and print out char 0
					//System.out.println("index "+i1);
					char x = input[1].charAt(i1);
					
					if(x == 'h')
					{
						//System.out.println("h");
						runH = true;
						//continue;
					}
					else if(x == '?') 
					{
						//System.out.println("?");
						runH = true;
						//continue;
					}
					else if(x == 'v')
					{
						//System.out.println("v");
						runV = true;
						//continue;
					}
					else 
					{
						noError = false;
						System.out.println("Unrecognized qualifier '" + input[1].charAt(i1) + "' in '"+ input[1] +"' ");
						MainI.PrintSyn2();
						break;
					}
					i1++;
				}
				
				
			}
			if(noError == true)
			{
				if(runH == true)
				{
					//System.out.println("running help");
					MainI.PrintSyn();
				}	
				if(runV == true)
				{
					//System.out.println("running verbose");
					if(Verbose == false)
					{
						Verbose = true;
						MainI.PrintVerboseOn();
						//System.out.println("t");
					}
					else
					{
						MainI.PrintVerboseOff();
						Verbose = false;
						//System.out.println("f");
					}
					
				}
			}

		}
		return(Verbose);
	}
}
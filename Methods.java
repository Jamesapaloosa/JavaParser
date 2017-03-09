	//Assignment 2 CPSC449
	//James Gilders 10062731
	//Carlin Liu 10123584
	//Version March 1.14.55


import java.lang.reflect.*;
public class Methods {

	//Main Starts here
	public static void main(String[]args){
		//Main variables
		Boolean Verbose = false;
		Boolean Run = true;
		Parser P;
		String MenuC;
		
		//PUT YOUR CODE FOR INPUT PARSER HERE CARILIN
		
		try{
			P = new Parser("TESTER");
			
		}
		catch(ClassNotFoundException e){
			Run = false;
			P = new Parser();
		}
		Interface MainI = new Interface();
		
		//Main Menu and menu selection decided here
		MainI.PrintMenu(); //This must be bypassed but isnt
		while(Run == true){
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
						Parser.verbose = true;
						MainI.PrintVerboseOn();
					}
					else
					{
						MainI.PrintVerboseOff();
						Verbose = false;
						Parser.verbose = false;
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
//					int [] temp = P.MethodLocations(MenuC);
//					int i = 0;
//					while (i < temp.length){
//						System.out.println(temp[i]);
//						i++;
//					}
		}
		
	}
	}
}

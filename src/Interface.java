
	//Assignment 2 CPSC449
	//James Gilders 10062731
	//Carlin Liu 10123584
	//Version March 1.14.55


import java.util.Scanner;
public class Interface {
	Scanner in;
	Interface(){
		in = new Scanner(System.in);
	}
	
	public String GetInput(){
		return(in.nextLine());
	}
	
	public void PrintMenu(){
		System.out.println("q           : Quit the program.\nv           : Toggle verbose mode (stack traces).\nf           : List all known functions.\n?           : Print this helpful text.\n<expression>: Evaluate the expression.\nExpressions can be integers, floats, strings (surrounded in double quotes) or function calls of the form '(identifier {expression}*)'.");
	}
	
	public void PrintSyn(){
		System.err.print("Synopsis:\n  methods\n  methods { -h | -? | --help }+\n  methods {-v --verbose}* <jar-file> [<class-name>]\nArguments:\n  <jar-file>:   The .jar file that contains the class to load (see next line).\n  <class-name>: The fully qualified class name containing public static command methods to call. [Default=\"Commands\"]\nQualifiers:\n  -v --verbose: Print out detailed errors, warning, and tracking.\n  -h -? --help: Print out a detailed help message.\nSingle-char qualifiers may be grouped; long qualifiers may be truncated to unique prefixes and are not case sensitive.\n\nThis program interprets commands of the format '(<method> {arg}*)' on the command line, finds corresponding\nmethods in <class-name>, and executes them, printing the result to sysout. Terminate with ^D or \"exit\".");
	}
	
	public void PrintSyn2(){
		System.err.print("Synopsis:\n  methods\n  methods { -h | -? | --help }+\n  methods {-v --verbose}* <jar-file> [<class-name>]\nArguments:\n  <jar-file>:   The .jar file that contains the class to load (see next line).\n  <class-name>: The fully qualified class name containing public static command methods to call. [Default=\"Commands\"]\nQualifiers:\n  -v --verbose: Print out detailed errors, warning, and tracking.\n  -h -? --help: Print out a detailed help message.\nSingle-char qualifiers may be grouped; long qualifiers may be truncated to unique prefixes and are not case sensitive.");
	}
	
	public void PrintVerboseOn(){
		System.out.println("Verbose on");
	}
	public void PrintVerboseOff(){
		System.out.println("Verbose off");
	}
	
	public void PrintBye(){
		System.out.println("bye.");
	}
	
	public void PrintStack(boolean V){
		if(V == true){
			Thread.dumpStack();
		}
	}
}


import java.util.Scanner;
/**
* <h1>Interface</h1>
* This class implements methods used to print out console synopsis and messages
* Assignment 2 CPSC449
* @author  Daniel Dastoor, James Gilders, Carlin Liu, Teresa Van, Thomas Vu
* @version 1.0
* @since   2017-03-17
*/
public class Interface {
	Scanner in;
	/**
	* Constructor for class that creates a new scanner
	*/
	Interface(){
		in = new Scanner(System.in);
	}
	/**
	* Method used for returning input from console
	* @return String containing Console Input
	*/
	public String GetInput(){
		return(in.nextLine());
	}
	/**
	* Method used to print out MainLoop's Menu
	*/	
	public void PrintMenu(){
		System.out.println("q           : Quit the program.\nv           : Toggle verbose mode (stack traces).\nf           : List all known functions.\n?           : Print this helpful text.\n<expression>: Evaluate the expression.\nExpressions can be integers, floats, strings (surrounded in double quotes) or function calls of the form '(identifier {expression}*)'.");
	}
	/**
	* Method that prints out synopsis that contains commands format line
	*/
	public void PrintSyn(){
		System.err.print("Synopsis:\n  methods\n  methods { -h | -? | --help }+\n  methods {-v --verbose}* <jar-file> [<class-name>]\nArguments:\n  <jar-file>:   The .jar file that contains the class to load (see next line).\n  <class-name>: The fully qualified class name containing public static command methods to call. [Default=\"Commands\"]\nQualifiers:\n  -v --verbose: Print out detailed errors, warning, and tracking.\n  -h -? --help: Print out a detailed help message.\nSingle-char qualifiers may be grouped; long qualifiers may be truncated to unique prefixes and are not case sensitive.\n\nThis program interprets commands of the format '(<method> {arg}*)' on the command line, finds corresponding\nmethods in <class-name>, and executes them, printing the result to sysout. Terminate with ^D or \"exit\".");
	}
	/**
	* Method that prints out synopsis
	*/
	public void PrintSyn2(){
		System.err.print("Synopsis:\n  methods\n  methods { -h | -? | --help }+\n  methods {-v --verbose}* <jar-file> [<class-name>]\nArguments:\n  <jar-file>:   The .jar file that contains the class to load (see next line).\n  <class-name>: The fully qualified class name containing public static command methods to call. [Default=\"Commands\"]\nQualifiers:\n  -v --verbose: Print out detailed errors, warning, and tracking.\n  -h -? --help: Print out a detailed help message.\nSingle-char qualifiers may be grouped; long qualifiers may be truncated to unique prefixes and are not case sensitive.");
	}
	/**
	* Method that prints out verbose on
	*/
	public void PrintVerboseOn(){
		System.out.println("Verbose on.");
	}
	/**
	* Method that prints out verbose off
	*/
	public void PrintVerboseOff(){
		System.out.println("Verbose off.");
	}
	/**
	* Method that prints out bye
	*/
	public void PrintBye(){
		System.out.print("bye.");
	}
}


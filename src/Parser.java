//Assignment 2 CPSC449
	//James Gilders 10062731
	//Carlin Liu 10123584
	//
	//
	//
	//Version March 8.12.49
	//

	// TO IMPLEMENT:
	// public [returnType: Object? I dunno] executeMethod(String function, ArrayList<Object> parameters) throws blah blah blah
	// parameters should be ArrayList<Object> and not ArrayList<Node>, since Object is working nicer

	//Current Bugs


	//-Does not recognize string inputs yet
	//-Does not yet deal with nested expressions 
	//-Inputs for actual method calls are hard coded
	//-Verbose not implemented
	//-May not yet be in parse tree from
	

	//Current Functionality

	//-Prints methods from desired function
	//-Can recognize an instantiated function that is fed in from main
	//-Knows all return types, constructors, parameterTypes, methods and fields
	//-Points to error points in expressions
	//-Handles a single expression of up to three inputs in length


import java.lang.reflect.*;
import java.util.*;
public class Parser {

	
	//Source is the 
	private String Source;
	private Class<?> ClassName;
	private Method[] methods;
	private Field[] fields;
	private Constructor [] constructors;
	private Class[][] parameterTypes;
	private Class[] returnType;
	private Object instance = null;
	public static boolean verbose = false;
	
//============================================================================
//////////////////////////////////////////////////////////////////////////////
//																			//
//								Constructor									//
//																			//
//////////////////////////////////////////////////////////////////////////////
//============================================================================
	//Constructor that initializes the array of all methods and all the constructors and all the fields
	//with parameter types and return types stored as well.
	public Parser(String ClassN)throws ClassNotFoundException{
		ClassName = Class.forName(ClassN);
		methods = ClassName.getMethods();
		fields = ClassName.getFields();
		constructors = ClassName.getConstructors();
		int k = methods.length;
		parameterTypes = new Class[k][];
		returnType = new Class[k];
		int i = 0;
		while (i < k){
			parameterTypes[i] = methods[i].getParameterTypes();
			i++;
		}
		i = 0;
		while (i < k){
			returnType[i] = methods[i].getReturnType();
			i++;
		}    
		Constructor<?> constructor = ClassName.getConstructors()[0];
		try{
		instance = constructor.newInstance();
		}catch(Throwable e){
	}
	}
	//Constructor that will not throw exceptions
	//Used in case of faulty input from command line
	public Parser(){}
	
	//Simple method that can print all the fields in a method
	public void PrintFields(){
		for(Field field : fields){
			System.out.println("Field = " + field.getName());
		}
	}
//============================================================================
//////////////////////////////////////////////////////////////////////////////
//																			//
//							PrintFunctions									//
//																			//
//////////////////////////////////////////////////////////////////////////////
//============================================================================
	//Method that prints the functions in the desired program
	public void PrintFunctions(){
		int i = 0;
		int j = 0;
		int k = methods.length;
		String InType;
		while(i < k){
			System.out.print("(" + methods[i].getName());
			j = 0;
			while(j < parameterTypes[i].length){
				InType = GetTypeString(parameterTypes[i][j].toString());
				System.out.print(" " + InType);
				j++;
			}
			InType = GetTypeString(returnType[i].toString());
			System.out.print(") : " + InType);
			i++;
		    System.out.println("");
		}
	}
	
public void ParseExpression(String string) {
    ParseTree t = new ParseTree(string, ClassName, instance);
    System.out.println(t.getEvaluation().toString());
}

//============================================================================	
//////////////////////////////////////////////////////////////////////////////
//																			//
//								CastObject									//
//																			//
//////////////////////////////////////////////////////////////////////////////
//============================================================================
//Takes in a string as input and the desired type and returns the correct format
//in an object
	private Object CastObject(String input, Class Type){
		Object output = null;
		if(Type == int.class){
			output = Integer.parseInt(input);
		}
		else if(Type == float.class){
			output = Float.parseFloat(input);
		}
		else
			output = input;
		return(output);
	}
	
//============================================================================	
//////////////////////////////////////////////////////////////////////////////
//																			//
//							MethodLocations									//
//																			//
//////////////////////////////////////////////////////////////////////////////
//============================================================================
//Method to find where and if a string is a method in a program
	private LinkedList MethodLocations(String input){
		int i = 0;
		int j = 0;
		int k = methods.length;
		LinkedList places = new LinkedList();
		String Name = "";
		while (i < k){
			Name = methods[i].getName();
			if(Name.equals(input)){
				places.add(i);
				j++;
			}
			i++;
		}
		return places;
	}
//============================================================================
//////////////////////////////////////////////////////////////////////////////
//																			//
//								ErrorArrows									//
//																			//
//////////////////////////////////////////////////////////////////////////////
//============================================================================
//Displays the arrows and needed error message to be printed
	private void ErrorArrows(int counter, int ErrorType){
		int i = 0;
		counter = counter + 2;
		while(i < counter){
			System.out.print("-");
			i++;
		}
		System.out.println("^");
		if(ErrorType == 0){
			System.out.println("Unexpected character here.");
		}
		else if(ErrorType == 1){
			System.out.println("No Such Method");
		}
		else if(ErrorType == 2){
			System.out.println("Bad input to this function");
		}
		else if(ErrorType == 3){
			System.out.println("Invalid parameters for this function");
		}
	}
}

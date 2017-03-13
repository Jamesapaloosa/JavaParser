//Assignment 2 CPSC449
	//James Gilders 10062731
	//Carlin Liu 10123584
	//
	//
	//
	//Version March 8.12.49
	//


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
//============================================================================
//////////////////////////////////////////////////////////////////////////////
//																			//
//							ParseExpression									//
//																			//
//////////////////////////////////////////////////////////////////////////////
//============================================================================
	//Takes an expression from input and then creates a stack to evaluate each sub expression
	//May have to be re-implemented if it does not conform to the parse tree style.
	public void ParseExpression(String input){
		int LocationCounter = 0;
		Stack MyStack = new Stack();
		Stack<Integer> CounterStack = new Stack<Integer>();
		LinkedList MNP = new LinkedList();
		Object Result = null;
		String Word = "";
		String temp = "";
		String output = "";
		int i = 0;
		int j = input.length();
		int counter = 0;
		char c;
		if(input.charAt(0) != '(' ){
			ErrorArrows(0, 0);
		}
		while(i < j){
			
			c = input.charAt(i);
			if(c == '('){
				temp = temp + c;
				MyStack.push(temp);
				temp = "";
				CounterStack.push(i);
			}
			
			//This section deals with one subexpression
			else if(c == ')'){
				MyStack.push(Word);
				Word = "";
				while(!MyStack.empty()){
					temp = (String) MyStack.pop();
					if(temp.equals("(")){
						break;
					}
					MNP.add(temp);
				}
				try{
					output = ImplementMethod(MNP);
					MyStack.push(output);
					try{
						CounterStack.pop();
					}catch(EmptyStackException r){
						ErrorArrows(counter, 0);
					}
				}catch(NoSuchMethodException e){
					counter = CounterStack.pop();
					ErrorArrows(counter, 1);
					break;
				}
				catch(InvocationTargetException f){
					ErrorArrows(counter, 2);
				}
				catch(IllegalArgumentException g){
					ErrorArrows(counter, 3);
				}
				catch(IllegalAccessException d){	
					}
				finally{
					MNP = new LinkedList();
				}
			}
			//End of subexpression handling
			else if((c == ' ')||(c == '\t')){
				MyStack.push(Word);
				Word = "";
			}
			else{
				Word = Word + c;
			}
			i++;
		}
		try{
			System.out.println(MyStack.pop());
		}
		catch(EmptyStackException e){
			//Fill
		}
	}
//============================================================================
//////////////////////////////////////////////////////////////////////////////
//																			//
//							Implement Method								//
//																			//
//////////////////////////////////////////////////////////////////////////////
//============================================================================
	//Implements the desired method with the provided parameters
	//Not finished yet
	
	public String ImplementMethod(LinkedList data) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalArgumentException{
		int Returned;
		String temp = "";
		String MethodName = "";
		String[] ListTypes = new String[data.size()];
		String[] DataArray = new String[data.size()];
		LinkedList temp1 = new LinkedList();
		int i;
		int j = data.size();
		while(0 < j){
			temp1.add(data.removeLast());
			j--;
		}
		data = temp1;
		
		for (i = 0; i < data.size(); i++) {
		   DataArray[i] = (String) data.get(i);
		}
		TreeMap<String, Class> TM = new TreeMap<String, Class>();
		i = 0;
		j = ListTypes.length;
		while(i <j){
			temp =  WordType(DataArray[i]);
			if(temp == "String.class"){
				DataArray[i] = (String) DataArray[i].replace("\"", "");
			}
			ListTypes[i] = temp;
			i++;
		}
		if(ListTypes[0] == "FUNCTION")
			MethodName = DataArray[0];
		//Creates a map to put the proper class into the getMethod in the try
		Class Alpha = int.class;
		Class Betta = String.class;
		Class Theta = float.class;
		Class TEMP;
		i = 1;
		j = ListTypes.length - 1;
		String MapIndex = "P";
		
		//Determine which class to place in each map location
		while(i <= j){
			temp = MapIndex + i;
			if(ListTypes[i] == "int.class"){
				TM.put(temp, Alpha);
			}
			else if(ListTypes[i] == "String.class"){
				TM.put(temp, Betta);
			}
			else if(ListTypes[i] == "float.class"){
				TM.put(temp,Theta);
			}
			i++;
		}
		LinkedList Parameters = ParameterLister(DataArray, TM);
		try{
			return Feeder(Parameters, TM, data.size(), DataArray, MethodName);		
		}
			finally{
				//Fill
			}
}
	
//============================================================================	
//////////////////////////////////////////////////////////////////////////////
//																			//
//							ParameterLister									//
//																			//
//////////////////////////////////////////////////////////////////////////////
//============================================================================
//Takes the parameters provided and their types then returns a linked list of
//objects which are the appropriate types.
	private LinkedList ParameterLister(String [] Parameters, TreeMap<String, Class> TM){
		LinkedList LL = new LinkedList();
		int i = 1;
		int j = Parameters.length - 1;
		String temp = "";
		String MapIndex = "P";
		while(i <= j){
			temp = MapIndex + i;
			if(TM.get(temp) == int.class){
				LL.add(Integer.parseInt(Parameters[i]));
			}
			else if(TM.get(temp) == float.class){
				LL.add(Float.parseFloat(Parameters[i]));
			}
			else 
				LL.add(Parameters[i]);
			i++;
		}
		return(LL);
	}
	
//============================================================================	
//////////////////////////////////////////////////////////////////////////////
//																			//
//									Feeder									//
//																			//
//////////////////////////////////////////////////////////////////////////////
//============================================================================
//This method takes in the number of arguments from the implementer and then
//calls the method with that many arguments.  Currently handles up to three 
//parameter inputs.
	private String Feeder(LinkedList Parameters, TreeMap<String, Class>  TM, int paramaterNum, String[] parameters, String MethodName)throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalArgumentException{
		Method method = null;
		Class RT = null;
		Object returnValue = null;
		if (paramaterNum == 1){
			method = ClassName.getMethod(MethodName);
			RT = method.getReturnType();
			returnValue = (method.invoke(instance));
		}
		else if(paramaterNum == 2){
			method = ClassName.getMethod(MethodName, TM.get("P1"));
			RT = method.getReturnType();
			returnValue = method.invoke(instance, Parameters.get(0));
		}
		else if(paramaterNum == 3){
			method = ClassName.getMethod(MethodName, TM.get("P1"), TM.get("P2"));
			RT = method.getReturnType();
			returnValue = (method.invoke(instance, Parameters.get(0), Parameters.get(1)));
		}
		else if(paramaterNum == 4){
			method = ClassName.getMethod(MethodName, TM.get("P1"), TM.get("P2"), TM.get("P3"));
			RT = method.getReturnType();
			returnValue = (method.invoke(instance, Parameters.get(0), Parameters.get(1), Parameters.get(2)));
		}
		
		//Determines the return type
		
		if(RT == int.class){
			return(Integer.toString((int)returnValue));
		}
		else if(RT == String.class){
			String temp = (String) returnValue;
			temp = '"' + temp + '"'; 
			return((String) returnValue);
		}
		else if (RT == float.class){
			return(Float.toString((float) returnValue));
		}
		else{
			return null;
		}
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
//							GetTypeString									//
//																			//
//////////////////////////////////////////////////////////////////////////////
//============================================================================
//Takes in a string and returns its type.
//takes in "java.lang.string" and returns "String"
	private String GetTypeString(String input){
		String [] temp = input.split(" ");
		String output = "";
		
		if(temp.length > 1){
			output = temp[1];
			temp = output.split("\\.");
			output = temp[temp.length - 1];
		}
		else{
			output = input;
		}
		return output;
	}
//============================================================================	
//////////////////////////////////////////////////////////////////////////////
//																			//
//								WordType									//
//																			//
//////////////////////////////////////////////////////////////////////////////
//============================================================================
	//Takes a string and determines what type it is
	//takes in "9.0" and returns "float.class"
	
	private String WordType(String input){
		String INT = "int.class";
		String STR = "String.class";
		String CHR = "Character.class";
		String FLT = "float.class";
		String FUN = "FUNCTION";
		String INV = "Invalid input here";
		
		int numDec = 0;
		char k;
		int i = 0;
		int j = input.length();
		LinkedList LL = new LinkedList();
		
		if(input != ""){
		//String
		if((input.charAt(0) == '"')&&(j >= 2)){
			if(input.charAt(j-1) == '"'){
				return(STR);
			}
			else{
				return(INV);
			}
		}
		//Digit or Float
		else{
			if(Character.isDigit(input.charAt(0))){
				while(i < j){
					k = input.charAt(i);
					if((k == '1')||(k == '2')||(k == '3')||(k == '4')||(k == '5')||(k == '6')||(k == '7')||(k == '8')||(k == '9')||(k == '0')||(k == '.')){
						if(k == '.'){
							numDec = numDec + 1;
						}
					}
					else{
						return(INV);
					}
					i++;
				}
				if(numDec == 0){
					return(INT);
				}
				else if(numDec == 1){
					return(FLT);
				}
				else{
					return(INV);
				}
			}
			//Check for function names
			else{
				LL = MethodLocations(input);
				if(LL.size() == 0){
					return (INV);
				}
				else{
					return(FUN);
				}
			}
		}
	}
		else{
			return null;
		}
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

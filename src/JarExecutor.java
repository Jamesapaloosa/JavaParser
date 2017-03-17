import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
/**
 * <h1>JarExecutor</h1>
 * This class implements the methods used to convert primitive types, execute desired methods and import the jar and class
 * Assignment 2 CPSC449
 * @author  Daniel Dastoor, James Gilders, Carlin Liu, Teresa Van, Thomas Vu
 * @version 1.0
 * @since   2017-03-17
 */
public class JarExecutor {

    private Class<?> className;
    private Method[] methods;
    private Field[] fields;
    private Constructor[] constructors;
    private Class[][] parameterTypes;
    private Class[] returnType;
    private Object instance = null;
    public Class cls;
	/**
	* Constructor for class that creates the path to the specified jar file. It will first check if the file already exists.
	* If the file does not exist it means File is not found and will catch and print out an error message. If the file exists 
	* it will try to load the class from the generated URL. If the class is not found it will catch with an error message. It 
	* will then get the methods, fields and constructors in the current class. The function will loop to find all the current
	* parameters of each method and will do the same for the return types.
	* @param jarName Is the string used for the jar name
	* @param className Is the string used for the class name
	*/
    public JarExecutor(String jarName, String className) {
	Interface MainI = new Interface();
        Path currentRelativePath = Paths.get("");
        String pathway = currentRelativePath.toAbsolutePath().toString();
        //*****Change this for Linux("/") or Windows("\\")
        pathway = pathway + "/"+jarName;
        //*****
        try {
            File file = new File(pathway);
            if(!file.exists())
            {
                throw new FileNotFoundException();
            }
            URL url = file.toURL();
            URL[] urls = new URL[] {url};

            ClassLoader cl = new URLClassLoader(urls);
            this.cls = cl.loadClass(className);
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find class: " + className);
	    MainI.PrintSyn2();
            System.exit(-6);
        } catch (FileNotFoundException | MalformedURLException q) {
            System.out.println("Could not load jar file: "+ jarName);
            System.exit(-5);
        }

        methods = cls.getMethods();
        fields = cls.getFields();
        constructors = cls.getConstructors();

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
        Constructor<?> constructor = cls.getConstructors()[0];
        try {
            instance = constructor.newInstance();
        } catch (Throwable e) {
            System.out.println("Could not load jar file: "+ jarName);
            System.exit(-5);
        }
    }

    /**
     * This method will execute the method by taking in the function name and arraylist of parameters. It will match the
     * java.lang.integer with a primitive type and will add it into an array. It will then invoke the method and return
     * the result.
     * @param function String that contains the function name
     * @param parameters An arraylist of objects containing the functions parameters
     * @return Object the result of the executed method
     * @throws NoSuchMethodException Throw when method is not found
     */
    public Object executeMethod(String function, ArrayList<Object> parameters) throws NoSuchMethodException {
        Class[] ptypes = (Class[]) parameters.stream().map(this::toPrimitiveClass).toArray(size -> new Class[size]);
        try {
            Method method = cls.getMethod(function, ptypes);
            return method.invoke(instance, parameters.toArray());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
            throw new NoSuchMethodException(expressionString(function, parameters));
        }
    }

    /**
     * This method will take in an object and will match and return the same primitive class
     * @param o Object to be converted
     * @return Class object after conversion will be returned
     */
    private Class toPrimitiveClass(Object o) {
        if (o instanceof Integer) {
            return int.class;
        } else if (o instanceof Float) {
            return float.class;
        } else {
            return o.getClass();
        }
    }

    /**
     * The method will go through all the parameters in the arraylist and will convert from java.lang.Integer to a string
     * parameter. It will then append the strings together and return the expression.
     * @param function Will take in the string containing the function
     * @param parameters An arraylist of objects that contain the parameters
     * @return String of the expression is returned
     */
    private String expressionString(String function, ArrayList<Object> parameters){
        String[] parameterTypes = new String[parameters.size()];
        int i = 0;
        Class<?> temp;
        int j = parameters.size();
        while (i < j){
            temp = parameters.get(i).getClass();
            if(temp.toString().equals("class java.lang.Integer")){
                parameterTypes[i] = "int";
            }
            else if(temp.toString().equals("class java.lang.Float")){
                parameterTypes[i] = "float";
            }
            else{
                parameterTypes[i] = "string";
            }
            i++;
        }
		String expression = "(" + function;
		for(String type : parameterTypes){
				expression += " " + type;
		}
        expression += ")";
        return expression;
	}

}

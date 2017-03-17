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

public class JarExecutor {

    private Class<?> className;
    private Method[] methods;
    private Field[] fields;
    private Constructor[] constructors;
    private Class[][] parameterTypes;
    private Class[] returnType;
    private Object instance = null;
    public Class cls;

    public JarExecutor(String jarName, String className) {
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

    public Object executeMethod(String function, ArrayList<Object> parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        Method method;
        Class[] parameterTypes = new Class[parameters.size()];
        int i = 0;
        Class<?> temp;
        int j = parameters.size();
        while (i < j){
            temp = parameters.get(i).getClass();
            if(temp.toString().equals("class java.lang.Integer")){
                parameterTypes[i] = int.class;
                parameters.set(i, ((int) parameters.get(i)));
                //System.out.println(((Integer) parameters.get(i)).intValue());
            }
            else if(temp.toString().equals("class java.lang.Float")){
                parameterTypes[i] = float.class;
                parameters.set(i, (float) parameters.get(i));
            }
            else{
                parameterTypes[i] = String.class;
                parameters.set(i, (String)parameters.get(i));
            }
            i++;
        }

//        for(Object o: parameters){
//            System.out.println(o.getClass());
//            System.out.println(o);
//        }
//
//        for(Class o: parameterTypes){
//            System.out.println(o);
//        }

        Class[] ptypes = (Class[]) parameters.stream().map(this::toPrimitiveClass).toArray(size -> new Class[size]);

//        for (int k = 0; k < ptypes.length; k++) {
//            System.out.println(ptypes[k]);
//        }

        try {
            method = cls.getMethod(function, ptypes);
            return method.invoke(instance, parameters.toArray());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
            throw new NoSuchMethodException(expressionString(function, parameters));
        }
    }

    private Class toPrimitiveClass(Object o) {
        if (o instanceof Integer) {
            return int.class;
        } else if (o instanceof Float) {
            return float.class;
        } else {
            return o.getClass();
        }
    }

    private String expressionString(String function, ArrayList<Object> parameters){
        Class[] parameterTypes = new Class[parameters.size()];
        int i = 0;
        Class<?> temp;
        int j = parameters.size();
        while (i < j){
            temp = parameters.get(i).getClass();
            if(temp.toString().equals("class java.lang.Integer")){
                parameterTypes[i] = int.class;
            }
            else if(temp.toString().equals("class java.lang.Float")){
                parameterTypes[i] = float.class;
            }
            else{
                parameterTypes[i] = String.class;
            }
            i++;
        }
		String expression = "(" + function + " ";
		for(Class C: parameterTypes){
				expression = expression + " " + C;
		}
        expression += ")";
        return expression;
//		System.out.println("Matching function for '" + expression + ")' not found at offset " + (counter));
//		System.out.println(input);
//		int d = 0;
//		while(d < counter){
//				System.out.print("-");
//				d++;
//		}
//		System.out.println("^");
//		if(verbose == true){
//				e.printStackTrace();
//		}
	}

}

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Tester {
    private Scanner userInput = new Scanner(System.in);
    private String className;
    private String methodName;
    private Class studentClass;
    private Method studentMethod;
    private Class[] inputClasses;
    private Class returnType;
    private Object [] inputArgs;
    private Object someInstance;
    private int grade;

    public Tester(Class returnType, Class [] inputClasses, Object[] inputArgs, Object someInstance) {
        this.returnType = returnType;
        this.inputClasses = inputClasses;
        this.inputArgs = inputArgs;
        this.someInstance = someInstance;
        Scanner userInput = new Scanner(System.in);
        // initializing student class
        askClassNameInput();
        ClassLoader classLoader = Tester.class.getClassLoader();
        try {
            this.studentClass = classLoader.loadClass(className);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // initializing student method
        askMethodNameInput();
        try {
            this.studentMethod = studentClass.getMethod(methodName, inputClasses);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("Enter expected output");
        String expected = userInput.nextLine();
        if(returnType == null) {
            testForVoid(expected);
        }
        else {
            testForReturnValue(expected);
        }
        System.out.println("finish testing");
        userInput.close();
    }

    private void testForVoid(String expected) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream consolePrints = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(consolePrints);
        try {
            studentMethod.invoke(someInstance, inputArgs);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.setOut(old);
        String actual = baos.toString();
        this.grade = calcGrade(expected, actual);
    }

    private void testForReturnValue(String expected) {
        if(this.returnType == studentMethod.getReturnType()) { //otherwise, grade=0
            String actual = null;
            try {
                actual = (studentMethod.invoke(someInstance, inputArgs)).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            this.grade = calcGrade(expected, actual);
        }
    }

    private int calcGrade(String expected, String actual) {
        int grade = 100;
        int length = expected.length();
        int scorePerChar = 100/(length);
        int max = Math.max(expected.length(), actual.length());
        for(int i = 0 ; i < max ; i++) {
            if(i < expected.length() && i < actual.length()) {
                if(expected.charAt(i) != actual.charAt(i)) {
                    grade = grade - scorePerChar;
                }
            }
            else {
                grade = grade - scorePerChar;
            }
        }
        if(grade < 0) {
            grade = 0;
        }
        return grade;
    }

    public int getGrade() {
        return this.grade;
    }

    private void askClassNameInput() {
        System.out.println("Enter class name");
        this.className = userInput.nextLine();
    }

    private void askMethodNameInput() {
        System.out.println("Enter method name");
        this.methodName = userInput.nextLine();
    }
}

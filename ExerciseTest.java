import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ExerciseTest {
    private Class<?> studentClass;
    private List<Integer> questionsGrades = new LinkedList<>();
    private int [] questionsScore;
    private int totalGrade;
    private boolean calculated = false;

    public ExerciseTest(String className, int [] questionsScore) {
        // initializing student class
        if(checkScore(questionsScore)) {
            this.questionsScore = questionsScore;
        }
        else {
            System.out.println("Error: invalid score entered");
        }
        ClassLoader classLoader = ExerciseTest.class.getClassLoader();
        try {
            this.studentClass = classLoader.loadClass(className);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void calcTotalGrade() {
        if(questionsGrades.size() != questionsScore.length) {
            System.out.println(questionsGrades.toString()+" "+Arrays.toString(questionsScore));
            System.out.println("Error: the number of questions examined does not match the number of questions requested");
        }
        else {
            for(int i=0 ; i < questionsScore.length ; i++) {
                this.totalGrade += ((double) questionsGrades.get(i) * ((double) questionsScore[i] / 100));
            }
        }
    }

    public int getTotalGrade() {
        if(! calculated) {
            calcTotalGrade();
            calculated = true;
        }
        return this.totalGrade;
    }

    private boolean checkScore(int[] scores) {
        int sum = 0;
        for(int score: scores) {
            sum += score;
        }
        return(sum == 100);
    }

    // INNER CLASS in ExerciseTest

    public class QuestionTest {
        private Method studentMethod;
        private Class<?> [] inputClasses;
        private List<Integer> testsGrades = new LinkedList<>();
        private int questionGrade;
        private boolean calculated = false;

        public QuestionTest(String methodName, Class<?> [] inputClasses) {
            this.inputClasses = inputClasses;
            // initializing student method
            try {
                this.studentMethod = studentClass.getMethod(methodName, this.inputClasses);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        private void calcQuestionGrade() {
            int cnt = 0;
            int result = 0;
            for(int testGrade: testsGrades) {
                result += testGrade;
                cnt += 1;
            }
            if(cnt != 0) { // otherwise, questionGrade=0
                this.questionGrade = result / cnt;
            }
            questionsGrades.add(this.questionGrade);
            calculated = true;
        }

        public int getQuestionGrade() {
            if(! this.calculated) {
                calcQuestionGrade();
            }
            return this.questionGrade;
        }


        // INNER CLASS in QuestionTest

        public class SingleTest {
            private Class<?> returnType;
            private Object[] inputArgs;
            private Object someInstance;
            private int singleTestGrade;

            public SingleTest(Class<?> returnType, Object[] inputArgs, Object someInstance, String expected, boolean moreTests) {
                this.returnType = returnType;
                this.inputArgs = inputArgs;
                this.someInstance = someInstance;
                if(returnType == null) {
                    testForVoid(expected);
                }
                else {
                    testForReturnValue(expected);
                }
                testsGrades.add(this.singleTestGrade);
                if(! moreTests) { // finished with all the tests for this question, ready to calculate total grade for this question
                    calcQuestionGrade();
                }
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
                this.singleTestGrade = calcSingleTestGrade(expected, actual);
            }

            private void testForReturnValue(String expected) {
                if(this.returnType == studentMethod.getReturnType()) { //otherwise, grade=0
                    String actual = null;
                    try {
                        if(returnType.isArray()) {
                            actual = Arrays.deepToString((Object[]) studentMethod.invoke(someInstance, inputArgs));
                        }
                        else {
                            actual = studentMethod.invoke(someInstance, inputArgs).toString();
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    this.singleTestGrade = calcSingleTestGrade(expected, actual);
                }
            }

            private int calcSingleTestGrade(String expected, String actual) {
                double correct = 0;
                double wrong = 0;
                int grade;
                if (expected.length() != actual.length()) {
                    wrong += Math.abs(expected.length() - actual.length());
                }
                for(int i=0 ; i < Math.min(expected.length(), actual.length()) ; i++) {
                    if(expected.charAt(i) == actual.charAt(i)) {
                        correct += 1;
                    }
                    else {
                        wrong += 1;
                    }
                }
                if(wrong == 0) {
                    grade = 100;
                }
                else {
                    grade = (int) ((correct / (correct + wrong)) * 100);
                }
                return grade;
            }

            public int getSingleTestGrade() {
                return this.singleTestGrade;
            }
        }
    }

}



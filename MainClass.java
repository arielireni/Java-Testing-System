
public class MainClass {

    public static void main(String[] args) {
        // test for a student
        int [] questionsScore = {30,30,40};
        ExerciseTest student1 = new ExerciseTest("StudentClass", questionsScore);

        // initializing questions for this student
        ExerciseTest.QuestionTest question1 = student1.new QuestionTest("example1", null);
        ExerciseTest.QuestionTest question2 = student1.new QuestionTest("example2", null);
        Class[] question3_classes = {int.class, int.class};
        ExerciseTest.QuestionTest question3 = student1.new QuestionTest("example3", question3_classes);

        // initializing single tests for each question
        StudentClass someInstance = new StudentClass();
        ExerciseTest.QuestionTest.SingleTest test11 = question1.new SingleTest(null, null, someInstance, "1", true);
        ExerciseTest.QuestionTest.SingleTest test12 = question1.new SingleTest(null, null, someInstance, "1", false);
        ExerciseTest.QuestionTest.SingleTest test21 = question2.new SingleTest(int.class, null, someInstance, "2", true);
        ExerciseTest.QuestionTest.SingleTest test22 = question2.new SingleTest(int.class, null, someInstance, "1", false);
        Object[] question3_inputArgs = {3,4};
        ExerciseTest.QuestionTest.SingleTest test31 = question3.new SingleTest(int.class, question3_inputArgs, someInstance, "7", true);
        ExerciseTest.QuestionTest.SingleTest test32 = question3.new SingleTest(int.class, question3_inputArgs, someInstance, "7", false);

        //System.out.println(test11.getSingleTestGrade());
        //System.out.println(question3.getQuestionGrade());
        System.out.println(student1.getTotalGrade());
    }
}


import il.ac.tau.cs.sw1.ex5.BigramModel;

import java.io.IOException;
import java.util.Arrays;

public class MainClass {

    public static void main(String[] args) throws IOException {
        // test for a student - for exercise 5
        int [] questionsScore = {15, 15, 10, 10, 5, 5, 10, 5, 10, 15};
        ExerciseTest studentTest = new ExerciseTest("il.ac.tau.cs.sw1.ex5.BigramModel", questionsScore);

        // initializing questions for this student
        Class [] op1_inputClasses = {String.class};
        Class [] op2_inputClasses = {String.class, String[].class};
        Class [] op3_inputClasses = {String.class, String.class};
        Class [] op4_inputClasses = {int[].class, int[].class};
        ExerciseTest.QuestionTest question1 = studentTest.new QuestionTest("buildVocabularyIndex", op1_inputClasses);
        ExerciseTest.QuestionTest question2 = studentTest.new QuestionTest("buildCountsArray", op2_inputClasses);
        ExerciseTest.QuestionTest question3 = studentTest.new QuestionTest("saveModel", op1_inputClasses);
        ExerciseTest.QuestionTest question4 = studentTest.new QuestionTest("loadModel", op1_inputClasses);
        ExerciseTest.QuestionTest question5 = studentTest.new QuestionTest("getWordIndex", op1_inputClasses);
        ExerciseTest.QuestionTest question6 = studentTest.new QuestionTest("getBigramCount", op3_inputClasses);
        ExerciseTest.QuestionTest question7 = studentTest.new QuestionTest("getMostFrequentProceeding", op1_inputClasses);
        ExerciseTest.QuestionTest question8 = studentTest.new QuestionTest("isLegalSentence", op1_inputClasses);
        ExerciseTest.QuestionTest question9 = studentTest.new QuestionTest("calcCosineSim", op4_inputClasses);
        ExerciseTest.QuestionTest question10 = studentTest.new QuestionTest("getClosestWord", op1_inputClasses);

        // initializing single tests for each question
        il.ac.tau.cs.sw1.ex5.BigramModel someInstance = new BigramModel();
        // Q1 //
        Object[] test11_inputs = {"resources/hw5/all_you_need.txt"};
        String [] test11_expected = {"love", "all", "you", "need", "is"};
        ExerciseTest.QuestionTest.SingleTest test11 = question1.new SingleTest(String[].class, test11_inputs, someInstance, Arrays.toString(test11_expected), false);
        // Q2 // PROBLEM WITH 2D ARRAY
        Object[] test21_inputs = {"resources/hw5/all_you_need.txt", new String[]{"love", "all", "you", "need", "is"}};
        String test21_expected = "[[3, 1, 0, 0, 1], [0, 0, 3, 0, 0], [0, 0, 0, 3, 0], [0, 0, 0, 0, 2], [2, 1, 0, 0, 0]]";
        ExerciseTest.QuestionTest.SingleTest test21 = question2.new SingleTest(int[][].class, test21_inputs, someInstance, test21_expected, false);
        // Q3 + Q4 // NEED TO COMPLETE
        Object[] test31_inputs = {"resources/hw5/all_you_need_model"};
        someInstance.initModel("resources/hw5/all_you_need.txt");
        ExerciseTest.QuestionTest.SingleTest test31 = question3.new SingleTest(null, test31_inputs, someInstance, "", false);
        ExerciseTest.QuestionTest.SingleTest test41 = question4.new SingleTest(null, test31_inputs, someInstance, "", false);
        // Q5
        Object[] test51_inputs = {"love"};
        ExerciseTest.QuestionTest.SingleTest test51 = question5.new SingleTest(int.class, test51_inputs, someInstance, "0", false);
        // Q6
        Object[] test61_inputs = {"love", "love"};
        ExerciseTest.QuestionTest.SingleTest test61 = question6.new SingleTest(int.class, test61_inputs, someInstance, "3", true);
        Object[] test62_inputs = {"all", "you"};
        ExerciseTest.QuestionTest.SingleTest test62 = question6.new SingleTest(int.class, test61_inputs, someInstance, "3", false);
        // Q7
        Object[] test71_inputs = {"is"};
        ExerciseTest.QuestionTest.SingleTest test71 = question7.new SingleTest(String.class, test71_inputs, someInstance, "love", false);
        // Q8
        Object[] test81_inputs = {"love is all"};
        ExerciseTest.QuestionTest.SingleTest test81 = question8.new SingleTest(boolean.class, test81_inputs, someInstance, "true", true);
        Object[] test82_inputs = {"love is is"};
        ExerciseTest.QuestionTest.SingleTest test82 = question8.new SingleTest(boolean.class, test82_inputs, someInstance, "false", false);
        // Q9
        Object[] test91_inputs = {new int[] {1,2,0,4, 2}, new int[] {5, 0, 3, 1, 1}};
        ExerciseTest.QuestionTest.SingleTest test91 = question9.new SingleTest(double.class, test91_inputs, someInstance, String.valueOf(11./30), false);
        // Q10
        someInstance.initModel("resources/hw5/emma.txt");
        Object[] test101_inputs = {"good"};
        ExerciseTest.QuestionTest.SingleTest test101 = question10.new SingleTest(String.class, test101_inputs, someInstance, "great", true);
        Object[] test102_inputs = {"emma"};
        ExerciseTest.QuestionTest.SingleTest test102 = question10.new SingleTest(String.class, test102_inputs, someInstance, "she", false);

        // FINISHED WITH ALL SINGLE TESTS

        System.out.println("Total grade = " + studentTest.getTotalGrade());
        System.out.println("grade for Q1 = " + question1.getQuestionGrade());
        System.out.println("grade for Q2 = " + question2.getQuestionGrade());
        System.out.println("grade for Q3 = " + question3.getQuestionGrade());
        System.out.println("grade for Q4 = " + question4.getQuestionGrade());
        System.out.println("grade for Q5 = " + question5.getQuestionGrade());
        System.out.println("grade for Q6 = " + question6.getQuestionGrade());
        System.out.println("grade for Q7 = " + question7.getQuestionGrade());
        System.out.println("grade for Q8 = " + question8.getQuestionGrade());
        System.out.println("grade for Q9 = " + question9.getQuestionGrade());
        System.out.println("grade for Q10 = " + question10.getQuestionGrade());
    }
}

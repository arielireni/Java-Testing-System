import il.ac.tau.cs.sw1.ex5.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class MainClass {

    public static void main(String[] args) throws IOException {
        // test for a student - for exercise 5
        int [] numOfTests = {1,1,1,1,1,2,1,2,1,2};
        DataCollector dataC = new DataCollector(numOfTests);
        int [] questionsScore = {15, 15, 10, 10, 5, 5, 10, 5, 10, 15};
        ExerciseTest studentTest = new ExerciseTest("il.ac.tau.cs.sw1.ex5.BigramModel1", questionsScore);

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
        il.ac.tau.cs.sw1.ex5.BigramModel1 someInstance = new BigramModel1();
        // Q1 //
        Object[] test11_inputs = {"resources/hw5/all_you_need.txt"};
        String [] test11_expected = {"love", "all", "you", "need", "is"};
        ExerciseTest.QuestionTest.SingleTest test11 = question1.new SingleTest(String[].class, test11_inputs, someInstance, Arrays.toString(test11_expected), false);
        dataC.addTestGrade(1,1, test11.getSingleTestGrade());
        // Q2 //
        Object[] test21_inputs = {"resources/hw5/all_you_need.txt", new String[]{"love", "all", "you", "need", "is"}};
        String test21_expected = "[[3, 1, 0, 0, 1], [0, 0, 3, 0, 0], [0, 0, 0, 3, 0], [0, 0, 0, 0, 2], [2, 1, 0, 0, 0]]";
        ExerciseTest.QuestionTest.SingleTest test21 = question2.new SingleTest(int[][].class, test21_inputs, someInstance, test21_expected, false);
        dataC.addTestGrade(2,1, test21.getSingleTestGrade());
        // Q3 + Q4 // NEED TO COMPLETE
        Object[] test31_inputs = {"resources/hw5/all_you_need_model"};
        someInstance.initModel("resources/hw5/all_you_need.txt");
        ExerciseTest.QuestionTest.SingleTest test31 = question3.new SingleTest(null, test31_inputs, someInstance, "", false);
        ExerciseTest.QuestionTest.SingleTest test41 = question4.new SingleTest(null, test31_inputs, someInstance, "", false);
        dataC.addTestGrade(3,1, test31.getSingleTestGrade());
        dataC.addTestGrade(4,1, test41.getSingleTestGrade());
        // Q5
        Object[] test51_inputs = {"love"};
        ExerciseTest.QuestionTest.SingleTest test51 = question5.new SingleTest(int.class, test51_inputs, someInstance, "0", false);
        dataC.addTestGrade(5,1, test51.getSingleTestGrade());
        // Q6
        Object[] test61_inputs = {"love", "love"};
        ExerciseTest.QuestionTest.SingleTest test61 = question6.new SingleTest(int.class, test61_inputs, someInstance, "3", true);
        dataC.addTestGrade(6,1, test61.getSingleTestGrade());
        Object[] test62_inputs = {"all", "you"};
        ExerciseTest.QuestionTest.SingleTest test62 = question6.new SingleTest(int.class, test61_inputs, someInstance, "4", false);
        dataC.addTestGrade(6,2, test62.getSingleTestGrade());
        // Q7
        Object[] test71_inputs = {"is"};
        ExerciseTest.QuestionTest.SingleTest test71 = question7.new SingleTest(String.class, test71_inputs, someInstance, "lov", false);
        dataC.addTestGrade(7,1, test71.getSingleTestGrade());
        // Q8
        Object[] test81_inputs = {"love is all"};
        ExerciseTest.QuestionTest.SingleTest test81 = question8.new SingleTest(boolean.class, test81_inputs, someInstance, "true", true);
        dataC.addTestGrade(8,1, test81.getSingleTestGrade());
        Object[] test82_inputs = {"love is is"};
        ExerciseTest.QuestionTest.SingleTest test82 = question8.new SingleTest(boolean.class, test82_inputs, someInstance, "false", false);
        dataC.addTestGrade(8,2, test82.getSingleTestGrade());
        // Q9
        Object[] test91_inputs = {new int[] {1,2,0,4, 2}, new int[] {5, 0, 3, 1, 1}};
        ExerciseTest.QuestionTest.SingleTest test91 = question9.new SingleTest(double.class, test91_inputs, someInstance, String.valueOf(11./30), false);
        dataC.addTestGrade(9,1, test91.getSingleTestGrade());
        // Q10
        someInstance.initModel("resources/hw5/emma.txt");
        Object[] test101_inputs = {"good"};
        ExerciseTest.QuestionTest.SingleTest test101 = question10.new SingleTest(String.class, test101_inputs, someInstance, "great", true);
        dataC.addTestGrade(10,1, test101.getSingleTestGrade());
        Object[] test102_inputs = {"emma"};
        ExerciseTest.QuestionTest.SingleTest test102 = question10.new SingleTest(String.class, test102_inputs, someInstance, "she", false);
        dataC.addTestGrade(10,2, test102.getSingleTestGrade());

        // FINISHED WITH ALL SINGLE TESTS

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
        System.out.println("Total grade = " + studentTest.getTotalGrade());

        // import data into excel file

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Student grades");

        Object[][] sourceData = new Object[][] {
                {"Question number", "Grade"},
                {"1", question1.getQuestionGrade()},
                {"2", question2.getQuestionGrade()},
                {"3", question3.getQuestionGrade()},
                {"4", question4.getQuestionGrade()},
                {"5", question5.getQuestionGrade()},
                {"6", question6.getQuestionGrade()},
                {"7", question7.getQuestionGrade()},
                {"8", question8.getQuestionGrade()},
                {"9", question9.getQuestionGrade()},
                {"10", question10.getQuestionGrade()},
                {"Total grade", studentTest.getTotalGrade()}
        };

        int rowCount = 0;

        for (Object[] data : sourceData) {
            Row row = sheet.createRow(++rowCount);

            int columnCount = 0;

            for (Object field : data) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }

        }

        try {
            FileOutputStream outputStream = new FileOutputStream("filePath");
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

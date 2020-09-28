package src;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;


public class DataCollector {
    private int numOfQuestions;
    private int[] numOfTests;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public DataCollector(int[] numOfTests) {

        this.numOfTests = numOfTests;
        this.numOfQuestions = numOfTests.length;
        File f = new File("C:\\Users\\Eli\\Desktop\\testGrades\\dataCollector.xlsx");
        if(! f.exists()) {
            this.workbook = new XSSFWorkbook();
            this.sheet = workbook.createSheet("Data");
            for(int i=0 ; i<numOfQuestions ; i++) {
                Row row = sheet.createRow(i);
                Cell cell = row.createCell(0);
                cell.setCellValue("Question " + (i+1));
            }
            try {
                FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Eli\\Desktop\\testGrades\\dataCollector.xlsx");
                workbook.write(outputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTestGrade(int questionNum, int testNum, int grade, int numOfStudents) {
        try {
            FileInputStream input = new FileInputStream(new File("C:\\Users\\Eli\\Desktop\\testGrades\\dataCollector.xlsx"));XSSFWorkbook workbook = new XSSFWorkbook(input);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(questionNum - 1);
            if(row.getCell(testNum) == null) { // insert grade of the first student
                Cell cell = row.createCell(testNum);
                cell.setCellValue(grade);
            }
            else {
                Cell cell = row.getCell(testNum);
                cell.setCellValue((cell.getNumericCellValue() * (numOfStudents - 1) + grade) / numOfStudents);
            }
            FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Eli\\Desktop\\testGrades\\dataCollector.xlsx");
            workbook.write(outputStream);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
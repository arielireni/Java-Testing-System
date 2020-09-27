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

        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet("Data");

        Row titleRow = sheet.createRow(1);
        int maxVal = 0;
        for(int i=0 ; i<numOfTests.length ; i++) {
            if(numOfTests[i] > maxVal) {
                maxVal = numOfTests[i];
            }
        }
        int columnCount = 0;
        for(int i=1 ; i<=(maxVal+1); i++) { // number of rows
            Row row = sheet.createRow(i);
            for(int j=1; j<=numOfQuestions*2 ; j=j+2) { // number of columns
                if(i == 1) {
                    Cell questionCell = row.createCell(++columnCount);
                    questionCell.setCellValue("Question "+(j/2 + 1));
                    sheet.addMergedRegion(new CellRangeAddress(1, 1, columnCount, ++columnCount));

                }
                else {
                    if(numOfTests[j/2] >= (i-1)) {
                        Cell cell = row.createCell(j);
                        cell.setCellValue("test " + (i-1));

                    }


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

    public void addTestGrade(int questionNum, int testNum, int grade) {
        try {
            FileInputStream input = new FileInputStream(new File("filePath"));
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(testNum + 1);
            Cell cell = row.createCell(2*questionNum);
            cell.setCellValue(grade);
            input.close();
            FileOutputStream output = new FileOutputStream("filePath);
            workbook.write(output);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

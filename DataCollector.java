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
        File f = new File("filepath");
        if(! f.exists()) {
            this.workbook = new XSSFWorkbook();
            this.sheet = workbook.createSheet("Data");
            for(int i=0 ; i<numOfQuestions ; i++) {
                Row row = sheet.createRow(i);
                Cell cell = row.createCell(0);
                cell.setCellValue("Question " + (i+1));
            }
            try {
                FileOutputStream outputStream = new FileOutputStream("filepath");
                workbook.write(outputStream);
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTestGrade(int questionNum, int testNum, int grade, int studentNum, String expected) {
        try {
            FileInputStream inputStream = new FileInputStream(new File("filepath"));
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(questionNum - 1);
            if(row.getCell(testNum * 2) == null) { // insert grade of the first student & expected value
                Cell cell = row.createCell(testNum * 2);
                cell.setCellValue(grade);
                Cell expectedCell = row.createCell(2 * testNum - 1);
                expectedCell.setCellValue(expected);
            }
            else {
                Cell cell = row.getCell(testNum * 2);
                cell.setCellValue((cell.getNumericCellValue() * (studentNum - 1) + grade) / studentNum);
            }
            FileOutputStream outputStream = new FileOutputStream("filepath");
            workbook.write(outputStream);
            inputStream.close();
            outputStream.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void commonMistakesSheet() {
        try {
            FileInputStream inputStream = new FileInputStream(new File("filepath"));
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFSheet mistakesSheet = workbook.getSheet("Common Mistakes");
            if(mistakesSheet == null) {
                mistakesSheet = workbook.createSheet("Common Mistakes");
            }
            int rowCount = 0;
            for(int i=0 ; i<numOfQuestions ; i++) {
                Row row = sheet.getRow(i);
                for(int j=1 ; j<=numOfTests[i] ; j++) {
                    Cell cell = row.getCell(j*2);
                    if(cell.getNumericCellValue() < 80) {
                        Row rowM = mistakesSheet.createRow(rowCount++);
                        Cell cellQ = rowM.createCell(0);
                        Cell cellT = rowM.createCell(1);
                        Cell cellE = rowM.createCell(2);
                        Cell cellG = rowM.createCell(3);
                        cellQ.setCellValue("Question " + (i+1));
                        cellT.setCellValue("Test " + j);
                        cellE.setCellValue(row.getCell(j*2 - 1).getStringCellValue());
                        cellG.setCellValue(row.getCell(2*j).getNumericCellValue());
                    }
                }
            }
            FileOutputStream outputStream = new FileOutputStream("filepath");
            workbook.write(outputStream);
            inputStream.close();
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

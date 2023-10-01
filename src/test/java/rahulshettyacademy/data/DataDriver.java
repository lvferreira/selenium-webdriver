package rahulshettyacademy.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDriver {

    public static ArrayList<String> getData(String testCase) throws IOException {
        // Define the path to the Excel file
		String excelData = System.getProperty("user.dir") 
        		+ "\\test-data\\excel_data.xlsx";

        // Initialize FileInputStream and XSSFWorkbook
        FileInputStream fileStream = new FileInputStream(excelData);
        XSSFWorkbook workbook = new XSSFWorkbook(fileStream);

        // Create an ArrayList to store the test data
        ArrayList<String> arrayList = new ArrayList<String>();

        // Get the number of sheets in the workbook
        int sheets = workbook.getNumberOfSheets();

        // Iterate through all sheets
        for (int i = 0; i < sheets; i++) {
            XSSFSheet sheet = workbook.getSheetAt(i);

            // Check if the sheet name matches "testdata"
            if (sheet.getSheetName().equalsIgnoreCase("testdata")) {

                // Find the column index for "TestCases" in the first row
                int columnIndex = findColumnIndex(sheet, "TestCases");

                if (columnIndex != -1) {
                    // Iterate through all rows in the sheet
                    Iterator<Row> rows = sheet.iterator();
                    while (rows.hasNext()) {
                        Row row = rows.next();

                        // Check if the value in the "TestCases" column matches the specified testCase
                        if (row.getCell(columnIndex).getStringCellValue().equalsIgnoreCase(testCase)) {
                            Iterator<Cell> cells = row.cellIterator();

                            // Iterate through all cells in the matching row
                            while (cells.hasNext()) {
                                Cell cell = cells.next();

                                // Convert cell value to String (handling both string and numeric types)
                                String value = cell.getCellType() == CellType.STRING ?
                                        cell.getStringCellValue() :
                                        NumberToTextConverter.toText(cell.getNumericCellValue());

                                arrayList.add(value);
                            }
                        }
                    }
                }
            }
        }

        // Close the workbook and file stream
        workbook.close();
        fileStream.close();

        return arrayList;
    }

    private static int findColumnIndex(XSSFSheet sheet, String columnName) {
        // Get the first row (header row) of the sheet
        Row firstRow = sheet.getRow(0);

        // Iterate through cells in the first row to find the matching column name
        for (int i = 0; i < firstRow.getLastCellNum(); i++) {
            Cell cell = firstRow.getCell(i);

            // Check if the cell value matches the specified columnName
            if (cell != null && cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                return i; // Return the column index if found
            }
        }

        return -1; // Return -1 if not found
    }
}

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
		// fileInputStream argument
		String excelData = System.getProperty("user.dir") + "\\test-data\\excel_data.xlsx";
		// System.out.println(excelData);
		FileInputStream fileStream = new FileInputStream(excelData);
		XSSFWorkbook workbook = new XSSFWorkbook(fileStream);

		ArrayList<String> arrayList = new ArrayList<String>();

		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase("testdata")) {
				// Identify TestCases column by scanning the entire 1st row
				XSSFSheet sheet = workbook.getSheetAt(i);
				// Get sheet rows
				Iterator<Row> rows = sheet.iterator(); // sheet is collection of rows
				Row firstRow = rows.next();
				// Get row cells
				Iterator<Cell> cells = firstRow.cellIterator(); // row is collection of cells
				// Get cell data
				int j = 0;
				int column = 0;
				while (cells.hasNext()) {
					Cell data = cells.next();
					if (data.getStringCellValue().equalsIgnoreCase("TestCases")) {
						// desired column
						column = j;
					}
					j++;
				}
				  // System.out.println(column);

				// Scan entire column to identify cell data value
				while (rows.hasNext()) {
					Row row = rows.next();
					if (row.getCell(column).getStringCellValue().equalsIgnoreCase(testCase)) {
						Iterator<Cell> cell = row.cellIterator();
						while (cell.hasNext()) {
							Cell c = cell.next();
							if(c.getCellType() == CellType.STRING) {
								String value = c.getStringCellValue();
								arrayList.add(value);
							} else {
								String value = NumberToTextConverter.toText(c.getNumericCellValue());
								arrayList.add(value);
							}
						}
					}
				}
			}
		}

		workbook.close();
		fileStream.close();

		return arrayList;
	}

}

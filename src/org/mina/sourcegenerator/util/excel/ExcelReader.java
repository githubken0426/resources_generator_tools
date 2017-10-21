package org.mina.sourcegenerator.util.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelReader {

	public static String readData(String src, int sheetIndex, int rowIndex,
			int cellIndex) throws IOException {
		String ret = "";
		InputStream inxls;
		inxls = new FileInputStream(src);

		HSSFWorkbook wb = new HSSFWorkbook(inxls);

		HSSFSheet sheet = wb.getSheetAt(sheetIndex);
		HSSFRow row = sheet.getRow(rowIndex);
		HSSFCell cell = row.getCell(cellIndex);

		ret = cell.getStringCellValue();
		return ret;
	}

	public static String readData(String src, int sheetIndex, String rc)
			throws IOException {
		String ret = "";
		InputStream inxls;
		inxls = new FileInputStream(src);

		HSSFWorkbook wb = new HSSFWorkbook(inxls);

		DataBean bean = new DataBean(sheetIndex, rc, "");

		HSSFSheet sheet = wb.getSheetAt(bean.getSheetIndex());
		HSSFRow row = sheet.getRow(bean.getRowIndex());
		HSSFCell cell = row.getCell(bean.getCellIndex());

		if (cell!=null) { 
			ret = cell.getStringCellValue();
		}
		return ret;
	}

}

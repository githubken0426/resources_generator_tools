package org.mina.sourcegenerator.util.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelWritter {

	public static void writeData(String src, String tar, List<DataBean> list)
			throws IOException {

		InputStream inxls;
		inxls = new FileInputStream(src);

		HSSFWorkbook wb = new HSSFWorkbook(inxls);
		for (int i = 0; i < list.size(); i++) {
			DataBean bean = list.get(i);

			HSSFSheet sheet = wb.getSheetAt(bean.getSheetIndex());
			HSSFRow row = sheet.getRow(bean.getRowIndex());
			HSSFCell cell = row.getCell(bean.getCellIndex());

			cell.setCellValue(bean.getValue());			
		}

		FileOutputStream fileOut = new FileOutputStream(tar);
		wb.write(fileOut);
		fileOut.close();
	}
}

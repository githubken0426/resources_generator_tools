package org.mina.sourcegenerator.util.excel;

public class DataBean {
	private int sheetIndex = 0;
	private int rowIndex = 0;
	private int cellIndex = 0;
	private String value = "";

	public DataBean(int sheetIndex, int rowIndex, int cellIndex, String value) {
		this.sheetIndex = sheetIndex;
		this.rowIndex = rowIndex;
		this.cellIndex = cellIndex;
		this.value = value;
	}

	public DataBean(int sheetIndex, String rc, String value) {
		this.sheetIndex = sheetIndex;
		this.setRowCell(rc);
		this.value = value;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getCellIndex() {
		return cellIndex;
	}

	public void setCellIndex(int cellIndex) {
		this.cellIndex = cellIndex;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setRowCell(String rc) {
		String s = rc;
		String r = "";
		String c = "";
		for (int i = 0; i < 10; i++) {
			s = s.replaceAll(i + "", "");
		}
		r = s;
		c = rc.replaceAll(r, "");
		this.rowIndex = Integer.parseInt(c) - 1;
		this.cellIndex = trans(r);
	}

	public void setRowCellValue(String rc, String value) {
		this.setRowCell(rc);
		this.setValue(value);
	}

	private int trans(String r) {
		int ret = 0;
		if (r == null || r.length() == 0) {
			return ret;
		}
		if (r.length() == 1) {
			ret = r.charAt(0) - 'A';
			return ret;
		}
		if (r.length() == 2) {
			ret = (r.charAt(0) - 'A' + 1) * 26 + (r.charAt(1) - 'A');
			return ret;
		}
		if (r.length() == 3) {
			ret = (r.charAt(0) - 'A' + 1) * 26 * 26 + (r.charAt(1) - 'A' + 1)
					* 26 + (r.charAt(2) - 'A');
			return ret;
		}
		return ret;
	}
}

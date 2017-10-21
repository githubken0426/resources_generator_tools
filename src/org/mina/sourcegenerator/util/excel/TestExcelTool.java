package org.mina.sourcegenerator.util.excel;


public class TestExcelTool {

	private static String SRC = "d:\\tmp\\充电桩位置总结_v0.1.xls";
	private static String TAR = "d:\\tmp\\充电桩位置总结_v0.1.xls";

	
	public static void main1(String[] args) {

		new TestExcelTool().test();
		System.out.println("SRC=" + SRC);
		System.out.println("TAR=" + TAR);
		System.out.println("CONVERT OVER");

		// System.out.println(new DataBean().trans(null));
		// System.out.println(new DataBean().trans(""));
		// System.out.println(new DataBean().trans("A"));
		// System.out.println(new DataBean().trans("B"));
		// System.out.println(new DataBean().trans("C"));
		// System.out.println(new DataBean().trans("M"));
		// System.out.println(new DataBean().trans("Z"));
		// System.out.println(new DataBean().trans("AA"));
		// System.out.println(new DataBean().trans("AB"));
		// System.out.println(new DataBean().trans("ZZ"));
		// System.out.println(new DataBean().trans("MN"));
		// System.out.println(new DataBean().trans("ZZZ"));
		// System.out.println(new DataBean().trans("AAA"));
		// System.out.println(new DataBean().trans("A"));
		// System.out.println(new DataBean().trans("A"));
		// System.out.println(new DataBean().trans("A"));

	}

	private void test() {

		try {

			System.out.println("Cell:A3 Value="
					+ ExcelReader.readData(SRC, 0, "A3"));
			System.out.println("Cell:A4 Value="
					+ ExcelReader.readData(SRC, 0, "A4"));
			System.out.println("Cell:B3 Value="
					+ ExcelReader.readData(SRC, 0, "B3"));
			System.out.println("Cell:B4 Value="
					+ ExcelReader.readData(SRC, 0, "B4"));

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}

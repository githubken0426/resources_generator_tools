package org.mina.sourcegenerator.main;

import java.io.File;

import org.mina.sourcegenerator.entity.Param;
import org.mina.sourcegenerator.util.GenTask;

import org.mina.sourcegenerator.util.excel.ExcelReader;
import org.mina.sourcegenerator.util.excel.FileUtil;

public class Gen5LeftJSP implements GenTask {

	@Override
	public String generate(Param p) throws Exception {
		// 根据 template\jsp\left.jsp，生成left.xml文件
		String src = p.base_dir + SourceGeneratorConst.TEMPLATE_SOURCE_JSP_PATH
				+ "left.jsp";

		String tar = p.generator_path //+ SourceGeneratorConst.JAVA_SRC_PATH
				+ "jsp" + File.separator + "left.jsp";

		String content = FileUtil.readFileByLines(src);

		content = content.replaceAll("##base_dir##", p.base_dir);
		content = content.replaceAll("##base_package##", p.base_package);
		content = content.replaceAll("##base_name##", p.base_name);
		content = content.replaceAll("##base_name2##", p.base_name2);
		content = content.replaceAll("##table_name##", p.table_name);
		content = content.replaceAll("##table_name_chinese##",
				p.table_name_chinese);
		content = content.replaceAll("##subsys_name##", p.subsys_name);

		String listData = "";

		// ##listData_input##

		listData = "\r\n";
		// listData = listData + "\r\n\t\t\t\t\t<!-- SrcGen生成菜单  -->";
		// listData = listData + "\r\n";

		int cnt = p.table_count;
		for (int i = 0; i < cnt; i++) {
			int idx = 2 + i;
			String base_name = (String) (ExcelReader.readData(p.excel, idx,
					"J6"));
			String table_name_chinese = (String) (ExcelReader.readData(p.excel,
					idx, "J8"));

			listData = listData + "\r\n\t\t\t\t\t" + "<li>";
			listData = listData + "\r\n\t\t\t\t\t\t"
					+ "<a href=\"#\" onclick=\"setMainContent('" + base_name
					+ "Action!listData.action');\">";
			listData = listData + "\r\n\t\t\t\t\t\t"
					+ "<i class=\"fa fa-angle-double-right\"></i>"
					+ table_name_chinese + "</a>";
			listData = listData + "\r\n\t\t\t\t\t" + "</li>";
			idx = idx + 1;
		}
		listData = listData + "\r\n";

		content = content.replace("##listData_input##", listData);

		String ret = FileUtil.writeFile(tar, content);
		return ret;
	}
}

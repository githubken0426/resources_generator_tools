package org.mina.sourcegenerator.main;

import java.io.File;

import org.mina.sourcegenerator.entity.Param;
import org.mina.sourcegenerator.util.GenTask;

import org.mina.sourcegenerator.util.excel.ExcelReader;
import org.mina.sourcegenerator.util.excel.FileUtil;

public class Gen6Struts implements GenTask {

	@Override
	public String generate(Param p) throws Exception {
		// 生成struts-config.xml文件
		String tar = p.generator_path //+ SourceGeneratorConst.JAVA_SRC_PATH
				+ "config" + File.separator + "struts-config.xml";
		String item = "";
		item = item + "\r\n\t<!-- ##table_name_chinese## -->";
		item = item
				+ "\r\n\t<include file=\"strutsxml/struts-##base_name##.xml\" />";
		String content = "";
		// ##listData_input##
		content = "\r\n";
		int cnt = p.table_count;
		for (int i = 0; i < cnt; i++) {
			String line = "";
			int idx = 2 + i;
			String base_name = (String) (ExcelReader.readData(p.excel, idx,
					"J6"));
			String table_name_chinese = (String) (ExcelReader.readData(p.excel,
					idx, "J8"));
			line = item.replaceAll("##base_name##", base_name);
			line = line
					.replaceAll("##table_name_chinese##", table_name_chinese);
			content = content + line;
		}
		String ret = FileUtil.writeFile(tar, content);
		return ret;
	}
}

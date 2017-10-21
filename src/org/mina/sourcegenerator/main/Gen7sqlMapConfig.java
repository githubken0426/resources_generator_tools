package org.mina.sourcegenerator.main;

import java.io.File;

import org.mina.sourcegenerator.entity.Param;
import org.mina.sourcegenerator.util.GenTask;
import org.mina.sourcegenerator.util.excel.ExcelReader;
import org.mina.sourcegenerator.util.excel.FileUtil;

public class Gen7sqlMapConfig implements GenTask {

	@Override
	public String generate(Param p) throws Exception {
		// 生成sqlMapConfig.xml文件
		String tar = p.generator_path //+ SourceGeneratorConst.JAVA_SRC_PATH
				+ "config" + File.separator + "sqlMapConfig.xml";
		
		String item = "";
		item = "\r\n\t<mapper resource=\"##base_package##/mapper/##base_name2##Mapper.xml\"/>";

		String content = "";
		content = "\r\n";
		int cnt = p.table_count;
		for (int i = 0; i < cnt; i++) {
			String line = "";
			int idx = 2 + i;
			String base_name = (String) (ExcelReader.readData(p.excel, idx,
					"J6"));
			String table_name_chinese = (String) (ExcelReader.readData(p.excel,
					idx, "J8"));
			String base_package = (String) (ExcelReader.readData(p.excel, idx,
					"J10"));
			String base_name2 = (String) (ExcelReader.readData(p.excel, idx,
					"Q6"));
			String subsys_name = (String) (ExcelReader.readData(p.excel, idx,
					"J11"));
			line = item.replaceAll("##base_name##", base_name);
			line = line
					.replaceAll("##table_name_chinese##", table_name_chinese);
			line = line.replaceAll("##base_name2##", base_name2);
			line = line.replaceAll("##subsys_name##", subsys_name);
			line = line.replaceAll("##base_package##", base_package);
			line = line
					.replaceAll(base_package, base_package.replace('.', '/'));
			content = content + line;
		}
		String ret = FileUtil.writeFile(tar, content);
		return ret;
	}
}

package org.mina.sourcegenerator.main;

import java.io.File;

import org.mina.sourcegenerator.entity.Field;
import org.mina.sourcegenerator.entity.Param;
import org.mina.sourcegenerator.util.GenTask;
import org.mina.sourcegenerator.util.StringUtil;

import org.mina.sourcegenerator.util.excel.FileUtil;

public class Gen36Action implements GenTask {

	@Override
	public String generate(Param p) throws Exception {
		// 根据 template\ActionTemplate.java中的内容，生成Action.java文件
		String src = p.base_dir
				+ SourceGeneratorConst.TEMPLATE_SOURCE_ROOT_PATH
				+ "ActionTemplate.java";
		
		String tar = p.generator_path //+ SourceGeneratorConst.JAVA_SRC_PATH
				+ p.base_package.replaceAll("[.]", "\\"+File.separator) +
				SourceGeneratorConst.generatorPath("action");

		FileUtil.createDir(tar);
		tar = tar + p.base_name2 + "Action.java";

		String content = FileUtil.readFileByLines(src);

		content = content.replaceAll("##base_dir##", p.base_dir);
		content = content.replaceAll("##base_package##", p.base_package);
		content = content.replaceAll("##base_name##", p.base_name);
		content = content.replaceAll("##base_name2##", p.base_name2);
		content = content.replaceAll("##table_name##", p.table_name);
		content = content.replaceAll("##table_name_chinese##",
				p.table_name_chinese);
		content = content.replaceAll("##subsys_name##", p.subsys_name);

		content = content.replaceAll("##pk_name_camel##", StringUtil
				.camel("set_" + p.pkname));
		content = content.replaceAll("##pk_name##", p.pkname);

		String listData = "";

		listData = "\r\n";
		listData = listData + "\r\n\t\t\t// 接收查询参数";
		listData = listData + "\r\n";
		// 检索条件区域
		for (Field f : p.list) {
			if (StringUtil.isEmpty(f.getQueryflag())) {
				continue;
			}
			listData = listData + "\r\n\t\t\t" + "String " + f.getCamelName()
					+ " = request.getParameter(\"" + f.getCamelName() + "\");";
			listData = listData + "\r\n\t\t\t" + "map.put(\""
					+ f.getCamelName() + "\", " + f.getCamelName() + ");";
			listData = listData + "\r\n";
		}

		content = content.replaceAll("##listData1##", listData);

		listData = "\r\n";
		listData = listData + "\r\n\t\t\t// 设置查询参数";
		listData = listData + "\r\n";
		// 检索条件区域
		for (Field f : p.list) {
			if (StringUtil.isEmpty(f.getQueryflag())) {
				continue;
			}
			listData = listData + "\r\n\t\t\t" + "context.put(\""
					+ f.getCamelName() + "\", " + f.getCamelName() + ");";
		}
		listData = listData + "\r\n";
		content = content.replaceAll("##listData2##", listData);
		String ret = FileUtil.writeFile(tar, content);
		return ret;
	}
}

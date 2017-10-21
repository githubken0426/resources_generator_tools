package org.mina.sourcegenerator.main;

import java.io.File;

import org.mina.sourcegenerator.entity.Param;
import org.mina.sourcegenerator.util.GenTask;

import org.mina.sourcegenerator.util.excel.FileUtil;

public class Gen34Service implements GenTask {

	@Override
	public String generate(Param p) throws Exception {
		// 根据 template\ServiceTemplate.java中的内容，生成Service.java文件

		String src = p.base_dir + SourceGeneratorConst.TEMPLATE_SOURCE_ROOT_PATH+"ServiceTemplate.java";
		String tar = p.generator_path //+ SourceGeneratorConst.JAVA_SRC_PATH
				+ p.base_package.replaceAll("[.]", "\\" + File.separator)
				+ SourceGeneratorConst.generatorPath("service");

		FileUtil.createDir(tar);
		tar = tar + p.base_name2 + "Service.java";

		String content = FileUtil.readFileByLines(src);

		content = content.replaceAll("##base_dir##", p.base_dir);
		content = content.replaceAll("##base_package##", p.base_package);
		content = content.replaceAll("##base_name##", p.base_name);
		content = content.replaceAll("##base_name2##", p.base_name2);
		content = content.replaceAll("##table_name##", p.table_name);
		content = content.replaceAll("##table_name_chinese##",
				p.table_name_chinese);
		content = content.replaceAll("##subsys_name##", p.subsys_name);

		String ret = FileUtil.writeFile(tar, content);
		return ret;
	}
}

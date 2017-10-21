package org.mina.sourcegenerator.main;

import java.io.File;

import org.mina.sourcegenerator.entity.Param;
import org.mina.sourcegenerator.util.GenTask;
import org.mina.sourcegenerator.util.excel.FileUtil;

public class Gen31MybatisEntity implements GenTask {

	@Override
	public String generate(Param p) throws Exception {
		String entity = SourceGeneratorConst.generatorPath("entity");
		String src = p.generator_path
				+ p.base_package.replaceAll("[.]", "\\" + File.separator) + entity;
		src = src + p.base_name2 + ".java";

		String tar = p.generator_path
				//+ SourceGeneratorConst.JAVA_SRC_PATH
				+ p.base_package.replaceAll("[.]", "\\" + File.separator) + entity;

		FileUtil.createDir(tar);
		tar = tar + p.base_name2 + ".java";
		String content = FileUtil.readFileByLines(src);

		/*
		 * content = content + "\r\n" + "\tpublic String getId() {"; content =
		 * content + "\r\n" + "\t\treturn id;"; content = content + "\r\n" +
		 * "\t}"; content = content + "\r\n" + "\t"; content = content + "\r\n"
		 * + "\tpublic void setId(String id) {"; content = content + "\r\n" +
		 * "\t\tthis.id = id;"; content = content + "\r\n" + "\t}"; content =
		 * content + "\r\n" + "}"; content = content + "\r\n" + "";
		 */
		String ret = FileUtil.writeFile(tar, content, false);
		return ret;
	}
}

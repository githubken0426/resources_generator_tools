package org.mina.sourcegenerator.main;

import java.io.File;

import org.mina.sourcegenerator.entity.Param;
import org.mina.sourcegenerator.util.GenTask;

import org.mina.sourcegenerator.util.excel.FileUtil;

public class Gen32MybatisDao implements GenTask {

	@Override
	public String generate(Param p) throws Exception {
		String daoPath = SourceGeneratorConst.generatorPath("dao");
		// 追加部分方法 Dao.java
		String s = "";
		String src = p.generator_path
				+ p.base_package.replaceAll("[.]", "\\" + File.separator)
				+ daoPath;
		src = src + p.base_name2 + "Mapper.java";
		
		String tar = p.generator_path //+ SourceGeneratorConst.JAVA_SRC_PATH
				+ p.base_package.replaceAll("[.]", "\\" + File.separator)
				+ daoPath;
		tar = tar + p.base_name2 + "Mapper.java";
		FileUtil.createDir(tar);

		String content = FileUtil.readFileByLines(src);

		// 要给dao加上@Repository注解， 以及要导入的包
		String t = "";
		t = t + "\r\n";
		t = t + "\r\nimport java.util.List;";
		t = t + "\r\nimport java.util.Map;";
		t = t + "\r\n";
		t = t + "\r\nimport org.springframework.stereotype.Repository;";
		t = t + "\r\n";
		t = t + "\r\n@Repository";
		t = t + "\r\npublic interface";

		content = content.replace("public interface", t);

		content = content.replaceAll("}", "");

		content = content + "\r\n";
		content = content + "\r\n";
		content = content + "\r\n\t// SrcGen 添加的方法";

		content = content + "\r\n";
		content = content + "\r\n\t// 查询";
		s = "public List<" + p.base_name2
				+ "> queryAllData(Map<String, Object> map);";
		content = content + "\r\n\t" + s;

		content = content + "\r\n";
		content = content + "\r\n\t// 统计数据总数（查询用）";
		s = "public int getTotalCount(Map<String, Object> map);";
		content = content + "\r\n\t" + s;

		content = content + "\r\n";
		content = content + "\r\n}";
		String ret = FileUtil.writeFile(tar, content, false);
		return ret;
	}
}

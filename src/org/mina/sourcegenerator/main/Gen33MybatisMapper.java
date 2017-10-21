package org.mina.sourcegenerator.main;

import java.io.File;

import org.mina.sourcegenerator.entity.Field;
import org.mina.sourcegenerator.entity.Param;
import org.mina.sourcegenerator.util.GenTask;
import org.mina.sourcegenerator.util.StringUtil;

import org.mina.sourcegenerator.util.excel.FileUtil;

public class Gen33MybatisMapper implements GenTask {

	@Override
	public String generate(Param p) throws Exception {
		String mapperPath = SourceGeneratorConst.generatorPath("mapper");
		// 追加部分方法mapper.xml
		String s = "";
		String src = p.generator_path
				+ p.base_package.replaceAll("[.]", "\\" + File.separator)
				+ mapperPath;
		src = src + p.base_name2 + "Mapper.xml";
		
		String tar = p.generator_path
				//+ SourceGeneratorConst.JAVA_SRC_PATH
				+ p.base_package.replaceAll("[.]", "\\" + File.separator)
				+ mapperPath;
		FileUtil.createDir(tar);
		tar = tar + p.base_name2 + "Mapper.xml";

		String content = FileUtil.readFileByLines(src);

		content = content.replaceAll("</mapper>", "");
		content = content + "\r\n";
		content = content + "\r\n";
		content = content + "\r\n\t\t<!-- SrcGen 添加的方法  -->";

		s = "";
		content = content + "\r\n";
		content = content + "\r\n\t\t<!-- 查询 -->";
		content = content + "\r\n\t" + s;
		s = s
				+ "<select id=\"queryAllData\" resultMap=\"BaseResultMap\" parameterType=\"java.util.Map\">";
		content = content + "\r\n\t\t" + s;
		content = content + "\r\n\t\t\t" + "select * from " + p.table_name;
		content = content + "\r\n\t\t\t" + "<where>";

		// 检索条件区域
		for (Field f : p.list) {
			if (StringUtil.isEmpty(f.getQueryflag())) {
				continue;
			}
			if (f.getDbtype().toUpperCase().contains("VARCHAR")) {
				content = content + "\r\n\t\t\t" + "<if test=\""
						+ f.getCamelName() + " !=null and " + f.getCamelName()
						+ " !=''\">";
				content = content + "\r\n\t\t\t" + "    and " + f.getName()
						+ " like concat(concat('%',#{" + f.getCamelName()
						+ ",jdbcType=VARCHAR}),'%')";
				content = content + "\r\n\t\t\t" + "</if>";
			} else if (f.getDbtype().toUpperCase().contains("CHAR")) {
				content = content + "\r\n\t\t\t" + "<if test=\""
						+ f.getCamelName() + " !=null and " + f.getCamelName()
						+ " !=''\">";
				content = content + "\r\n\t\t\t" + "    and " + f.getName()
						+ " like concat(concat('%',#{" + f.getCamelName()
						+ ",jdbcType=CHAR}),'%')";
				content = content + "\r\n\t\t\t" + "</if>";
			} else if (f.getDbtype().toUpperCase().contains("INT")) {
				content = content + "\r\n\t\t\t" + "<if test=\""
						+ f.getCamelName() + " !=null and " + f.getCamelName()
						+ " !=''\">";
				content = content + "\r\n\t\t\t" + "    and " + f.getName()
						+ " =#{" + f.getCamelName() + ",jdbcType=INTEGER}";
				content = content + "\r\n\t\t\t" + "</if>";
			} else if (f.getDbtype().toUpperCase().contains("DATE")) {
				content = content + "\r\n\t\t\t" + "<if test=\""
						+ f.getCamelName() + " !=null and " + f.getCamelName()
						+ " !=''\">";
				content = content + "\r\n\t\t\t" + "    and " + f.getName()
						+ " =#{" + f.getCamelName() + ",jdbcType=DATETIME}";
				content = content + "\r\n\t\t\t" + "</if>";
			} else if (f.getDbtype().toUpperCase().contains("FLOAT")) {
				content = content + "\r\n\t\t\t" + "<if test=\""
						+ f.getCamelName() + " !=null and " + f.getCamelName()
						+ " !=''\">";
				content = content + "\r\n\t\t\t" + "    and " + f.getName()
						+ " =#{" + f.getCamelName() + ",jdbcType=FLOAT}";
				content = content + "\r\n\t\t\t" + "</if>";
			} else if (f.getDbtype().toUpperCase().contains("DECIMAL")) {
				content = content + "\r\n\t\t\t" + "<if test=\""
						+ f.getCamelName() + " !=null and " + f.getCamelName()
						+ " !=''\">";
				content = content + "\r\n\t\t\t" + "    and " + f.getName()
						+ " =#{" + f.getCamelName() + ",jdbcType=DOUBLE}";
				content = content + "\r\n\t\t\t" + "</if>";
			} else if (f.getDbtype().toUpperCase().contains("TIMESTAMP")) {
				content = content + "\r\n\t\t\t" + "<if test=\""
						+ f.getCamelName() + " !=null and " + f.getCamelName()
						+ " !=''\">";
				content = content + "\r\n\t\t\t" + "    and " + f.getName()
						+ " =#{" + f.getCamelName() + ",jdbcType=TIMESTAMP}";
				content = content + "\r\n\t\t\t" + "</if>";
			} else {
				throw new Exception("不识别的数据类型：" + f.getDbtype());
			}
		}
		content = content + "\r\n\t\t\t" + "</where>";

		// content = content + "\r\n\t\t\t" + "order by insert_time asc";
		content = content + "\r\n\t\t\t" + "limit #{beginResult}, #{pageSize}";
		content = content + "\r\n\t\t" + "</select>";

		s = "";
		content = content + "\r\n";
		content = content + "\r\n\t\t<!-- 查询统计数据 -->";
		content = content + "\r\n\t" + s;
		s = s
				+ "<select id=\"getTotalCount\" resultType=\"java.lang.Integer\" parameterType=\"java.util.Map\">";
		content = content + "\r\n\t\t" + s;
		content = content + "\r\n\t\t\t" + "select count(1) from "
				+ p.table_name;
		content = content + "\r\n\t\t\t" + "<where>";

		// 检索条件区域
		for (Field f : p.list) {
			if (StringUtil.isEmpty(f.getQueryflag())) {
				continue;
			}
			if (f.getDbtype().toUpperCase().contains("VARCHAR")) {
				content = content + "\r\n\t\t\t" + "<if test=\""
						+ f.getCamelName() + " !=null and " + f.getCamelName()
						+ " !=''\">";
				content = content + "\r\n\t\t\t" + "    and " + f.getName()
						+ " like concat(concat('%',#{" + f.getCamelName()
						+ ",jdbcType=VARCHAR}),'%')";
				content = content + "\r\n\t\t\t" + "</if>";
			} else if (f.getDbtype().toUpperCase().contains("CHAR")) {
				content = content + "\r\n\t\t\t" + "<if test=\""
						+ f.getCamelName() + " !=null and " + f.getCamelName()
						+ " !=''\">";
				content = content + "\r\n\t\t\t" + "    and " + f.getName()
						+ " like concat(concat('%',#{" + f.getCamelName()
						+ ",jdbcType=CHAR}),'%')";
				content = content + "\r\n\t\t\t" + "</if>";
			} else if (f.getDbtype().toUpperCase().contains("INT")) {
				content = content + "\r\n\t\t\t" + "<if test=\""
						+ f.getCamelName() + " !=null and " + f.getCamelName()
						+ " !=''\">";
				content = content + "\r\n\t\t\t" + "    and " + f.getName()
						+ " =#{" + f.getCamelName() + ",jdbcType=INTEGER}";
				content = content + "\r\n\t\t\t" + "</if>";
			} else if (f.getDbtype().toUpperCase().contains("DATE")) {
				content = content + "\r\n\t\t\t" + "<if test=\""
						+ f.getCamelName() + " !=null and " + f.getCamelName()
						+ " !=''\">";
				content = content + "\r\n\t\t\t" + "    and " + f.getName()
						+ " =#{" + f.getCamelName() + ",jdbcType=DATETIME}";
				content = content + "\r\n\t\t\t" + "</if>";
			} else if (f.getDbtype().toUpperCase().contains("FLOAT")) {
				content = content + "\r\n\t\t\t" + "<if test=\""
						+ f.getCamelName() + " !=null and " + f.getCamelName()
						+ " !=''\">";
				content = content + "\r\n\t\t\t" + "    and " + f.getName()
						+ " =#{" + f.getCamelName() + ",jdbcType=FLOAT}";
				content = content + "\r\n\t\t\t" + "</if>";
			} else if (f.getDbtype().toUpperCase().contains("DECIMAL")) {
				content = content + "\r\n\t\t\t" + "<if test=\""
						+ f.getCamelName() + " !=null and " + f.getCamelName()
						+ " !=''\">";
				content = content + "\r\n\t\t\t" + "    and " + f.getName()
						+ " =#{" + f.getCamelName() + ",jdbcType=DOUBLE}";
				content = content + "\r\n\t\t\t" + "</if>";
			} else if (f.getDbtype().toUpperCase().contains("TIMESTAMP")) {
				content = content + "\r\n\t\t\t" + "<if test=\""
						+ f.getCamelName() + " !=null and " + f.getCamelName()
						+ " !=''\">";
				content = content + "\r\n\t\t\t" + "    and " + f.getName()
						+ " =#{" + f.getCamelName() + ",jdbcType=TIMESTAMP}";
				content = content + "\r\n\t\t\t" + "</if>";
			} else {
				throw new Exception("不识别的数据类型：" + f.getDbtype());
			}
		}
		content = content + "\r\n\t\t\t" + "</where>";
		content = content + "\r\n\t\t" + "</select>";
		content = content + "\r\n\t" + "</mapper>";

		String ret = FileUtil.writeFile(tar, content, false);

		return ret;
	}
}

package org.mina.sourcegenerator.main;

import java.io.File;

import org.mina.sourcegenerator.entity.Field;
import org.mina.sourcegenerator.entity.Param;
import org.mina.sourcegenerator.util.GenTask;
import org.mina.sourcegenerator.util.StringUtil;
import org.mina.sourcegenerator.util.excel.FileUtil;

public class Gen1DBSql implements GenTask {

	@Override
	public String generate(Param p) throws Exception {
		// 根据 p.list中的字段内容，生成p.dbsql文件
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n");
		sb.append("\r\n" + "# ");
		sb.append("\r\n" + "# " + p.table_name + " : " + p.table_name_chinese);
		sb.append("\r\n" + "# ");
		sb.append("\r\n");
		sb.append("\r\n" + "DROP TABLE IF EXISTS " + p.table_name + ";");
		sb.append("\r\n" + "CREATE TABLE " + p.table_name + "(");
		String pk = "";
		for (Field f : p.list) {
			sb.append("\r\n" + "\t" + f.getName() + " " + f.getDbtype());
			if (!StringUtil.isEmpty(f.getNotnull())) {
				sb.append(" " + f.getNotnull() + " ");
			}
			sb.append(", \t # " + f.getLabel());
			if (!StringUtil.isEmpty(f.getPk())) {
				if (!StringUtil.isEmpty(pk)) {
					pk = pk + ",";
				}
				pk = pk + f.getName();
			}
		}
		sb.append("\r\n" + "\tPRIMARY KEY (" + pk + " )");
		sb.append("\r\n" + ");");
		String fname = p.generator_path //+ SourceGeneratorConst.JAVA_SRC_PATH
				+ "db" + File.separator + p.table_name + ".sql";
		return FileUtil.writeFile(fname, sb.toString(), false);
	}
}

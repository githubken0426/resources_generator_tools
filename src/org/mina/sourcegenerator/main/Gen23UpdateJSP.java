package org.mina.sourcegenerator.main;

import java.io.File;

import org.mina.sourcegenerator.entity.Field;
import org.mina.sourcegenerator.entity.Param;
import org.mina.sourcegenerator.util.GenTask;
import org.mina.sourcegenerator.util.StringUtil;

import org.mina.sourcegenerator.util.excel.FileUtil;

public class Gen23UpdateJSP implements GenTask {

	@Override
	public String generate(Param p) throws Exception {
		// 根据 template\jsp\ update.jsp中的内容，生成update.jsp文件

		String src = p.base_dir + SourceGeneratorConst.TEMPLATE_SOURCE_JSP_PATH+"update.jsp";
		String tar = p.generator_path + SourceGeneratorConst.TARGET_JSP_PATH + p.base_name;

		FileUtil.createDir(tar);
		tar = tar + File.separator+"update.jsp";

		String content = FileUtil.readFileByLines(src);

		content = content.replaceAll("##base_dir##", p.base_dir);
		content = content.replaceAll("##base_package##", p.base_package);
		content = content.replaceAll("##base_name##", p.base_name);
		content = content.replaceAll("##base_name2##", p.base_name2);
		content = content.replaceAll("##table_name##", p.table_name);
		content = content.replaceAll("##table_name_chinese##",
				p.table_name_chinese);
		content = content.replaceAll("##subsys_name##", p.subsys_name);
		content = content.replaceAll("##pk_name##", p.pkname);

		String listData = "";

		// ##listData_update##
		// 数据修改
		listData = "\r\n";
		listData = listData + "\r\n\t\t\t\t\t\t<!-- 数据修改  -->";
		listData = listData + "\r\n";
		int idx = 1;
		for (Field f : p.list) {
			if (StringUtil.isEmpty(f.getUpdateflag())) {
				continue;
			}
			listData = listData + "\r\n\t\t\t\t\t\t" + "<tr>";

			listData = listData
					+ "\r\n\t\t\t\t\t\t\t"
					+ "<td width=\"200px\" align=\"right\" nowrap=\"nowrap\" bgcolor=\"#f1f1f1\" height=\"40px\">"
					+ f.getLabel();
			if (!StringUtil.isEmpty(f.getNotnull())) {
				listData = listData + "(*)";
			}
			listData = listData + "：</td>";

			listData = listData + "\r\n\t\t\t\t\t\t\t" + "<td>";
			listData = listData + "\r\n\t\t\t\t\t\t\t"
					+ "<input type=\"text\" id=\"" + f.getCamelName()
					+ "\" name=\"entity." + f.getCamelName() + "\" "
					+ " value=\"${entity." + f.getCamelName() + " }\" "
					+ " tabindex=\"" + idx
					+ "\" style=\"width:400px;margin-left:30px;\"/>";
			listData = listData + "\r\n\t\t\t\t\t\t\t" + "<span id=\""
					+ f.getCamelName()
					+ "Msg\" style=\"margin-left:15px;\"></span>";
			listData = listData + "\r\n\t\t\t\t\t\t\t" + "</td>";
			listData = listData + "\r\n\t\t\t\t\t\t" + "</tr>";
			idx = idx + 1;
		}
		content = content.replace("##listData_update##", listData);

		// ##input_check_var##
		// 定义js check变量区
		listData = "\r\n";
		listData = listData + "\r\n\t// 定义js check变量  ";
		listData = listData + "\r\n";

		for (Field f : p.list) {
			if (StringUtil.isEmpty(f.getUpdateflag())) {
				continue;
			}
			listData = listData + "\r\n\t" + "var " + f.getCamelName()
					+ "IsOK = true;";
		}
		listData = listData + "\r\n";
		content = content.replace("##input_check_var##", listData);

		// ##input_check_required_length##
		// 定义js 校验
		listData = "\r\n";
		listData = listData + "\r\n\t\t// 定义js check必录项和长度校验  ";
		listData = listData + "\r\n";

		for (Field f : p.list) {
			if (StringUtil.isEmpty(f.getUpdateflag())) {
				continue;
			}
			listData = listData + "\r\n\t\t" + "// " + f.getLabel();
			listData = listData + "\r\n\t\t" + "$(\"###name##\").bind({";
			listData = listData + "\r\n\t\t\t" + "focus:function(){";
			listData = listData + "\r\n\t\t\t\t"
					+ "$(\"###name##Msg\").html(\"\");";
			listData = listData + "\r\n\t\t\t" + "},blur:function(){";
			listData = listData + "\r\n\t\t\t\t"
					+ "var ##name## = $.trim($(\"###name##\").val());";

			// 必录较验
			if (!StringUtil.isEmpty(f.getNotnull())) {
				listData = listData + "\r\n\t\t\t\t"
						+ "if(##name##.length == 0){";
				listData = listData + "\r\n\t\t\t\t\t"
						+ "$(\"###name##Msg\").html(\"<font color='red'>"
						+ f.getLabel() + " 不能为空!</font>\");";
				listData = listData + "\r\n\t\t\t\t\t"
						+ "##name##IsOK = false;";
				listData = listData + "\r\n\t\t\t\t\t" + "return false;";
				listData = listData + "\r\n\t\t\t\t" + "}";
			}

			// 长度较验
			String dbtype = f.getDbtype().toUpperCase();
			dbtype = dbtype.replaceAll("VARCHAR", "");
			dbtype = dbtype.replaceAll("CHAR", "");
			dbtype = dbtype.replaceAll("INT", "");
			dbtype = dbtype.replaceAll("TIMESTAMP", "");
			dbtype = dbtype.replaceAll("DATETIME", "");
			dbtype = dbtype.replaceAll("DATE", "");
			dbtype = dbtype.replaceAll("DECIMAL", "");
			dbtype = dbtype.replaceAll("LONGTEXT", "");
			dbtype = dbtype.replace('(', ' ');
			dbtype = dbtype.replace(')', ' ');
			dbtype = dbtype.replace('.', ' ');
			dbtype = dbtype.replace(',', ' ');
			dbtype = dbtype.replaceAll(" ", "");

			if (!StringUtil.isEmpty(dbtype)
					&& f.getDbtype().toUpperCase().contains("CHAR")) {
				int len = Integer.parseInt(dbtype);

				listData = listData + "\r\n\t\t\t\t" + "if(##name##.length > "
						+ len + "){";
				listData = listData + "\r\n\t\t\t\t\t"
						+ "$(\"###name##Msg\").html(\"<font color='red'>"
						+ f.getLabel() + " 超长(" + len + "位)!</font>\");";
				listData = listData + "\r\n\t\t\t\t\t"
						+ "##name##IsOK = false;";
				listData = listData + "\r\n\t\t\t\t\t" + "return false;";
				listData = listData + "\r\n\t\t\t\t" + "}";
			}

			listData = listData + "\r\n\t\t\t\t" + "##name##IsOK = true;";
			listData = listData + "\r\n\t\t\t\t" + "return true;";
			listData = listData + "\r\n\t\t\t" + "}";
			listData = listData + "\r\n\t\t" + "});";
			listData = listData + "\r\n";
			listData = listData.replaceAll("##name##", f.getCamelName());
		}

		listData = listData + "\r\n";

		content = content.replace("##input_check_required_length##", listData);

		// ##input_check_submit##
		// 定义js 提交前check
		listData = "\r\n\t\t// 提交前校验";

		listData = listData + "\r\n\t\t" + "if ( 1==1";
		for (Field f : p.list) {
			if (StringUtil.isEmpty(f.getUpdateflag())) {
				continue;
			}
			listData = listData + " && " + f.getCamelName() + "IsOK";
		}
		listData = listData + "){";
		listData = listData + "\r\n\t\t\t" + "$(\"#updateForm\").submit();";
		listData = listData + "\r\n\t\t" + "} else {";
		listData = listData + "\r\n\t\t\t" + "alert(\"信息输入有误，无法保存！\");";
		listData = listData + "\r\n\t\t" + "}";

		content = content.replace("##input_check_submit##", listData);

		String ret = FileUtil.writeFile(tar, content);
		return ret;
	}
}

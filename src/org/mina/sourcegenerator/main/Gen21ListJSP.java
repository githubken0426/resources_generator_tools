package org.mina.sourcegenerator.main;

import java.io.File;

import org.mina.sourcegenerator.entity.Field;
import org.mina.sourcegenerator.entity.Param;
import org.mina.sourcegenerator.util.GenTask;
import org.mina.sourcegenerator.util.StringUtil;
import org.mina.sourcegenerator.util.excel.FileUtil;

public class Gen21ListJSP implements GenTask {

	@Override
	public String generate(Param p) throws Exception {
		// 根据 template\jsp\list.jsp中的内容，生成list.jsp文件
		String src = p.base_dir + SourceGeneratorConst.TEMPLATE_SOURCE_JSP_PATH+"list.jsp";
		String tar = p.generator_path + SourceGeneratorConst.TARGET_JSP_PATH + p.base_name;

		FileUtil.createDir(tar);
		tar = tar + File.separator+"list.jsp";
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
		// ##listData_query_clean##
		// 清空条件检索区的参数
		// $("#configid").attr("value","");
		// $("#appid").attr("value","");
		listData = "\r\n";
		listData = listData + "\r\n\t\t// 清空条件检索区的参数";
		listData = listData + "\r\n";
		for (Field f : p.list) {
			if (StringUtil.isEmpty(f.getQueryflag())) {
				continue;
			}
			listData = listData + "\r\n\t\t" + "$(\"#" + f.getCamelName()
					+ "\").attr(\"value\",\"\");";
		}
		content = content.replace("##listData_query_clean##", listData);
		// content = content.replaceAll("##listData_query_clean##", listData);
		// ##listData_query_input##
		// 条件检索区
		// <span class="margin-left-10" style="font-size: 15px;">
		// configid:
		// <input type="text" id="configid" name="configid"
		// value="${configid}"class="laydate-icon"
		// style="width:150px;padding:5px;" />
		// </span>
		listData = "\r\n";
		listData = listData + "\r\n\t\t\t\t\t<!--  条件检索区 -->";
		listData = listData + "\r\n";
		for (Field f : p.list) {
			if (StringUtil.isEmpty(f.getQueryflag())) {
				continue;
			}
			listData = listData
					+ "\r\n\t\t\t\t\t"
					+ "<span class=\"margin-left-10\" style=\"font-size: 15px;\">";
			listData = listData + "\r\n\t\t\t\t\t\t" + "" + f.getLabel() + "";
			listData = listData
					+ "\r\n\t\t\t\t\t\t"
					+ "<input type=\"text\" id=\""
					+ f.getCamelName()
					+ "\" name=\""
					+ f.getCamelName()
					+ "\" value=\"${"
					+ f.getCamelName()
					+ "}\"class=\"laydate-icon\" style=\"width:150px;padding:5px;\" />";
			listData = listData + "\r\n\t\t\t\t\t" + "</span>";
			listData = listData + "\r\n";
		}
		content = content.replace("##listData_query_input##", listData);
		// ##listData_table_title##
		// 检索结果表格题头
		// <td nowrap="nowrap" width="220px"><strong>orgid</strong></td>

		listData = "\r\n";
		listData = listData + "\r\n\t\t\t\t\t\t<!--  检索结果表格题头 -->";
		listData = listData + "\r\n";
		for (Field f : p.list) {
			if (StringUtil.isEmpty(f.getShowflag())) {
				continue;
			}
			listData = listData + "\r\n\t\t\t\t\t\t"
					+ "<td nowrap=\"nowrap\" width=\"220px\"><strong>"
					+ f.getLabel() + "</strong></td>";
		}
		listData = listData + "\r\n";
		content = content.replace("##listData_table_title##", listData);
		// ##listData_table_content##
		// 检索结果表格内容
		// <td>${o.orgid }</td>

		listData = "\r\n";
		listData = listData + "\r\n\t\t\t\t\t\t<!--  检索结果表格内容 -->";
		listData = listData + "\r\n";
		for (Field f : p.list) {
			if (StringUtil.isEmpty(f.getShowflag())) {
				continue;
			}
			if (f.getDbtype().toUpperCase().contains("DATE")) {
				listData = listData + "\r\n\t\t\t\t\t\t"
						+ "<td><fmt:formatDate value=\"${o." + f.getCamelName()
						+ "}\" pattern=\"yyyy-MM-dd HH:mm\"/></td>";
			} else if (f.getDbtype().toUpperCase().contains("CHAR")) {
				listData = listData + "\r\n\t\t\t\t\t\t" + "<td>${o."
						+ f.getCamelName() + " }</td>";
			} else {
				listData = listData + "\r\n\t\t\t\t\t\t" + "<td>${o."
						+ f.getCamelName() + " }</td>";
			}
		}
		listData = listData + "\r\n";
		content = content.replace("##listData_table_content##", listData);
		String ret = FileUtil.writeFile(tar, content);
		return ret;
	}
}

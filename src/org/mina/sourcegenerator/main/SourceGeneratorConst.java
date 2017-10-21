package org.mina.sourcegenerator.main;

import java.io.File;

public final class SourceGeneratorConst {
	//模板的根路径
	public static final String TEMPLATE_SOURCE_ROOT_PATH=File.separator+"template"+File.separator;
	//jsp模板路径
	public static final String TEMPLATE_SOURCE_JSP_PATH=TEMPLATE_SOURCE_ROOT_PATH+"jsp"+File.separator;
	//生成jsp文件存放路径
	public static final String TARGET_JSP_PATH=File.separator+"jsp"+File.separator;
	//生成模式(db或者java src)
	public static final int MODE_SQL = 0;
	public static final int MODE_SRC = 1;
	//压缩文件存放路径
	public static final String COMPRESS_JAVA_PATH="com"+File.separator;
	public static final String COMPRESS_CONFIG_PATH="config"+File.separator;
	public static final String COMPRESS_JSP_PATH="jsp"+File.separator;
	public static final String COMPRESS_DB_PATH="db"+File.separator;
	//测试用
	public static final String FNAME = "car_home_db_20161129.xls";
	public static final String EXCEL = "D:\\resource_work\\" + FNAME;
	public static final String OUTPUT = "D:\\resource_work";

	/**
	 * 生成路径(window linux通用)
	 * @param packageName 包名
	 * @return
	 * 2016-12-7 下午04:18:20
	 */
	public static String generatorPath(String packageName){
		return File.separator+packageName+File.separator;
	}
}

package org.mina.sourcegenerator.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mina.sourcegenerator.entity.Param;
import org.mina.sourcegenerator.main.Gen0ReadParam;
import org.mina.sourcegenerator.main.Gen1DBSql;
import org.mina.sourcegenerator.main.Gen21ListJSP;
import org.mina.sourcegenerator.main.Gen22AddJSP;
import org.mina.sourcegenerator.main.Gen23UpdateJSP;
import org.mina.sourcegenerator.main.Gen31MybatisEntity;
import org.mina.sourcegenerator.main.Gen32MybatisDao;
import org.mina.sourcegenerator.main.Gen33MybatisMapper;
import org.mina.sourcegenerator.main.Gen34Service;
import org.mina.sourcegenerator.main.Gen35ServiceImpl;
import org.mina.sourcegenerator.main.Gen36Action;
import org.mina.sourcegenerator.main.Gen41Struts;
import org.mina.sourcegenerator.main.Gen5LeftJSP;
import org.mina.sourcegenerator.main.Gen6Struts;
import org.mina.sourcegenerator.main.Gen7sqlMapConfig;
import org.mina.sourcegenerator.main.SourceGeneratorConst;

/**
 * java源码生成、sql建表语句生成
 * 
 * @author ken
 * 2016-12-1 下午02:22:39
 */
public final class WebSourceGeneratorUtil {
	public static void main(String[] args) {
		try {
			Param param = new Param();
			param.excel = SourceGeneratorConst.EXCEL;
			param.base_dir = SourceGeneratorConst.OUTPUT;
			HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(new File(SourceGeneratorConst.EXCEL)));
			execute(workbook.getNumberOfSheets(),1,param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行方法
	 * @param sheetCount excel sheet页数	
	 * @param mode 	0:sql,1：java src
	 * @param param 生成文件相关
	 * @throws Exception
	 * 2016-12-1 下午02:06:44
	 */
	public static void execute(int sheetCount,int mode,Param param) throws Exception {
		//数据库模板从第三页开始
		for (int i = 2; i < sheetCount; i++) {
			generator(i,mode,param);			
		}
		//菜单只生成一次
		if (mode == SourceGeneratorConst.MODE_SRC) {
			System.out.println("生成左侧菜单栏");
			(new Gen5LeftJSP()).generate(param);//5. left.jsp
			(new Gen6Struts()).generate(param);//6. struts-config.jsp
			(new Gen7sqlMapConfig()).generate(param);//7. sqlMapConfig.xml
		}
	}

	/**
	 * 不包含菜单栏
	 * @param sheet	excel sheet页
	 * @param mode 	0:sql,1：java src
	 * @param param 生成文件相关
	 * @throws Exception
	 * 2016-12-1 下午02:06:44
	 */
	public static void generator(int sheet,int mode,Param param) throws Exception {
		Gen0ReadParam gen0 = new Gen0ReadParam();
		gen0.setIdx(sheet);
		param = gen0.generate(param);
		System.out.println("Table[" + sheet + "] "+ param.table_name + ":" + param.table_name_chinese + "  has been started.");

		if (mode== SourceGeneratorConst.MODE_SQL) {
			(new Gen1DBSql()).generate(param);//1.DBsql
		} else {
			(new Gen21ListJSP()).generate(param);//2.1 list.jsp
			(new Gen22AddJSP()).generate(param); //2.2 add.jsp
			(new Gen23UpdateJSP()).generate(param);//2.3 update.jsp
//			(new Gen31MybatisEntity()).generate(param);//3.1 Entity
//			(new Gen32MybatisDao()).generate(param);//3.2 Dao
//			(new Gen33MybatisMapper()).generate(param);//3.3 Mapper
			(new Gen34Service()).generate(param); //3.4 Service
			(new Gen35ServiceImpl()).generate(param);//3.5 ServiceImpl 
			(new Gen36Action()).generate(param); //3.6 Action
			(new Gen41Struts()).generate(param); //4.1 struts-template.xml
		}
		System.out.println("Table[" + sheet + "] "+ param.table_name + ":" + param.table_name_chinese + "  has been finished.");
	}
}

package org.mina.sourcegenerator.main;

import org.mina.sourcegenerator.entity.Param;

public class SourceGeneratorMain {

	public static void main(String[] args) {
		try {
			new SourceGeneratorMain().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run() throws Exception {
		int tb_count = 1;
		for (int i = 0; i < tb_count; i++) {
			execute(i);			
		}
		
		if (1 == SourceGeneratorConst.MODE_SRC) {
			Param p = new Param();
			p.excel = SourceGeneratorConst.EXCEL;
			p.base_dir = SourceGeneratorConst.OUTPUT;
			
			String ret = (new Gen5LeftJSP()).generate(p);
			System.out.println("5.left.jsp has been executed.");
			
			ret = (new Gen6Struts()).generate(p);
			System.out.println("6.struts-config.jsp has been executed.");
			//System.out.println("ret = " + ret);
			
			ret = (new Gen7sqlMapConfig()).generate(p);
			System.out.println("7.sqlMapConfig.xml has been executed.");
			//System.out.println("ret = " + ret);

		}
	}

	private void execute(int idx) throws Exception {
		// 读取excel文件中的内容进入Param
		Param p = new Param();
		p.excel = SourceGeneratorConst.EXCEL;
		p.base_dir = SourceGeneratorConst.OUTPUT;

		Gen0ReadParam gen0 = new Gen0ReadParam();
		gen0.setIdx(idx + 2);
		p = gen0.generate(p);

		System.out.println("0.Param has been executed.");
		// System.out.println(p.toString());
		System.out.println("");
		System.out.println("Table[" + idx + "] "+ p.table_name + ":" + p.table_name_chinese + "  has been started.");

		String ret = "";
		if (SourceGeneratorConst.MODE_SQL == 0) {
			ret = (new Gen1DBSql()).generate(p);
			System.out.println("1.DBSql has been executed.");
			//System.out.println("ret = " + ret);
		} else {

			ret = (new Gen21ListJSP()).generate(p);
			System.out.println("2.1.list.jsp has been executed.");
			//System.out.println("ret = " + ret);

			ret = (new Gen22AddJSP()).generate(p);
			System.out.println("2.2.add.jsp has been executed.");
			//System.out.println("ret = " + ret);

			ret = (new Gen23UpdateJSP()).generate(p);
			System.out.println("2.3.update.jsp has been executed.");
			//System.out.println("ret = " + ret);

			ret = (new Gen31MybatisEntity()).generate(p);
			System.out.println("3.1.Entity has been executed.");
			//System.out.println("ret = " + ret);

			ret = (new Gen32MybatisDao()).generate(p);
			System.out.println("3.2.Dao has been executed.");
			//System.out.println("ret = " + ret);

			ret = (new Gen33MybatisMapper()).generate(p);
			System.out.println("3.3.Mapper has been executed.");
			//System.out.println("ret = " + ret);

			ret = (new Gen34Service()).generate(p);
			System.out.println("3.4.Service has been executed.");
			//System.out.println("ret = " + ret);

			ret = (new Gen35ServiceImpl()).generate(p);
			System.out.println("3.5.ServiceImpl has been executed.");
			//System.out.println("ret = " + ret);

			ret = (new Gen36Action()).generate(p);
			System.out.println("3.6.Action has been executed.");
			//System.out.println("ret = " + ret);

			ret = (new Gen41Struts()).generate(p);
			System.out.println("4.1.struts-template.xml has been executed.");
			//System.out.println("ret = " + ret);
		}
		System.out.println("Table[" + idx + "] "+ p.table_name + ":" + p.table_name_chinese + "  has been finished.");

	}

}

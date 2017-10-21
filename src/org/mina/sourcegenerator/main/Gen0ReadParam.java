package org.mina.sourcegenerator.main;

import java.util.ArrayList;

import org.mina.sourcegenerator.entity.Field;
import org.mina.sourcegenerator.entity.Param;
import org.mina.sourcegenerator.util.GenTask;
import org.mina.sourcegenerator.util.StringUtil;
import org.mina.sourcegenerator.util.excel.ExcelReader;

public class Gen0ReadParam implements GenTask {
	private int idx = 0;

	@Override
	public Param generate(Param p) throws Exception {
		// 业务名
		p.base_name = (String) (ExcelReader.readData(p.excel, idx, "J6"));
		p.base_name2 = (String) (ExcelReader.readData(p.excel, idx, "Q6"));
		// 表名
		p.table_name = (String) (ExcelReader.readData(p.excel, idx, "J7"));
		// 表名（汉字）
		p.table_name_chinese = (String) (ExcelReader.readData(p.excel, idx,
				"J8"));
		// 字段数量
		p.field_size = Integer.parseInt((String) (ExcelReader.readData(p.excel,
				idx, "J9")));
		// 基本包
		p.base_package = (String) (ExcelReader.readData(p.excel, idx, "J10"));
		// 基本包
		p.subsys_name = (String) (ExcelReader.readData(p.excel, idx, "J11"));

		p.list = new ArrayList<Field>();

		int line = 45;
		for (int i = 0; i < p.field_size; i++) {
			Field f = new Field();
			f.setName((StringUtil.trim((String) (ExcelReader.readData(p.excel,
					idx, "D" + (line + i))))));
			f.setLabel((StringUtil.trim((String) (ExcelReader.readData(p.excel,
					idx, "K" + (line + i))))));
			f.setDbtype((StringUtil.trim((String) (ExcelReader.readData(
					p.excel, idx, "R" + (line + i))))));
			f.setNotnull((StringUtil.trim((String) (ExcelReader.readData(
					p.excel, idx, "Y" + (line + i))))));
			f.setPk((StringUtil.trim((String) (ExcelReader.readData(p.excel,
					idx, "AD" + (line + i))))));
			f.setQueryflag((StringUtil.trim((String) (ExcelReader.readData(
					p.excel, idx, "AG" + (line + i))))));
			f.setShowflag((StringUtil.trim((String) (ExcelReader.readData(
					p.excel, idx, "AJ" + (line + i))))));
			f.setInsertflag((StringUtil.trim((String) (ExcelReader.readData(
					p.excel, idx, "AM" + (line + i))))));
			f.setUpdateflag((StringUtil.trim((String) (ExcelReader.readData(
					p.excel, idx, "AP" + (line + i))))));
			f.setCamelName(StringUtil.camel(f.getName()));
			if (!StringUtil.isEmpty(f.getPk())) {
				p.pkname = f.getCamelName();
			}
			p.list.add(f);
		}
		System.out.println("generator param");
		return p;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}
}

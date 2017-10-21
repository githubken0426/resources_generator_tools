package org.mina.sourcegenerator.entity;

import java.util.ArrayList;
import java.util.List;

public class Param {
	public Param(){}
	
	public Param(String excel,String generator_path,String base_dir,int table_count){
		this.excel=excel;
		this.base_dir=base_dir;
		this.generator_path=generator_path;
		this.table_count=table_count;
	}
	// EXCEL文件
	public String excel;
	// 工作路径
	public String base_dir;
	// 基本包名
	public String base_package;
	// 业务名
	public String base_name;
	// 业务名2,首字母大写
	public String base_name2;
	// 表名
	public String table_name;
	// 表名（汉字）
	public String table_name_chinese;
	// 字段数量
	public int field_size;
	// 子系统名
	public String subsys_name;
	// dbsql 文件名
	public String dbsql;
	// pk
	public String pkname;
	public String generator_path;//生成文件存放路径
	public int table_count;
	
	public List<Field> list = new ArrayList<Field>();

	@Override
	public String toString() {
		return "Param [excel=" + excel + ", base_dir=" + base_dir
				+ ", base_package=" + base_package + ", base_name=" + base_name
				+ ", base_name2=" + base_name2 + ", table_name=" + table_name
				+ ", table_name_chinese=" + table_name_chinese
				+ ", field_size=" + field_size + ", dbsql=" + dbsql + ", list="
				+ list + "]";
	}

}

package org.mina.sourcegenerator.entity;

public class Field {
	private String name;
	private String camelName;
	private String dbtype;
	private String notnull;
	private String pk;
	private String label;
	private String queryflag;
	private String showflag;
	private String insertflag;
	private String updateflag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCamelName() {
		return camelName;
	}

	public void setCamelName(String camelName) {
		this.camelName = camelName;
	}

	public String getDbtype() {
		return dbtype;
	}

	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}

	public String getNotnull() {
		return notnull;
	}

	public void setNotnull(String notnull) {
		this.notnull = notnull;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getQueryflag() {
		return queryflag;
	}

	public void setQueryflag(String queryflag) {
		this.queryflag = queryflag;
	}

	public String getShowflag() {
		return showflag;
	}

	public void setShowflag(String showflag) {
		this.showflag = showflag;
	}

	public String getInsertflag() {
		return insertflag;
	}

	public void setInsertflag(String insertflag) {
		this.insertflag = insertflag;
	}

	public String getUpdateflag() {
		return updateflag;
	}

	public void setUpdateflag(String updateflag) {
		this.updateflag = updateflag;
	}

	@Override
	public String toString() {
		return "Field [name=" + name + ", camelName=" + camelName + ", dbtype="
				+ dbtype + ", notnull=" + notnull + ", pk=" + pk + ", label="
				+ label + ", queryflag=" + queryflag + ", showflag=" + showflag
				+ ", insertflag=" + insertflag + ", updateflag=" + updateflag
				+ "]";
	}

	public Field(String name, String camelName, String dbtype, String notnull,
			String pk, String label, String queryflag, String showflag,
			String insertflag, String updateflag) {
		super();
		this.name = name;
		this.camelName = camelName;
		this.dbtype = dbtype;
		this.notnull = notnull;
		this.pk = pk;
		this.label = label;
		this.queryflag = queryflag;
		this.showflag = showflag;
		this.insertflag = insertflag;
		this.updateflag = updateflag;
	}

	public Field() {
		super();
	}

}

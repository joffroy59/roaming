package com.decathlon.offroy.sql;

import workbench.sql.formatter.SqlFormatter;

public class SqlQueryFormater {

	private String querySql;
	private String querySqlWithoutFirsComment;
	private String querySqlFormatted;
	private String queryName;

	public SqlQueryFormater(String queryName, String querySql) {
		super();
		this.queryName = queryName;
		this.querySql = querySql;
		formatSql();
	}

	public void formatSql() {
		querySqlWithoutFirsComment = removeFirstComment(querySql);

		SqlFormatter f = new SqlFormatter(querySqlWithoutFirsComment, "oracle");
		querySqlFormatted = f.getFormattedSql();
	}

	private static String removeFirstComment(String sqlQuery) {
		return sqlQuery.replaceFirst("/\\*\\+.*\\*/", "");
	}

	public void printFormatedSql() {
		System.out.println("Query clean:" + "[" + queryName + "]");
		System.out.println(querySqlFormatted);
	}

	public void printFormatedDetailSql() {
		System.out.println("Query Input" + "[" + queryName + "]");
		System.out.println(querySql);
		System.out.println("");
		System.out.println("Query Input (without comment)" + "[" + queryName + "]");
		System.out.println(querySqlWithoutFirsComment);
		System.out.println("");
		printFormatedSql();
	}

	public void print(boolean isDebug) {
		if (isDebug) {
			printFormatedDetailSql();
		} else {
			printFormatedSql();
		}

	}

	public void print() {
		print(false);
	}

}

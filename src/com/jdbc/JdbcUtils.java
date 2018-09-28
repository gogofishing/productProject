package com.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Driver;

public class JdbcUtils {
	
	

	private final String USERNAME = "root";
	private final String PASSWORD = "wjh123123123";
	private final String DRIVER = "com.mysql.jdbc.Driver";
	protected static String dbUrl2 = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
	
	private final String URL = "jdbc:mysql://localhost:3306/mydb";
	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet resultSet;
	private Statement stmt;
	
	public JdbcUtils() {
		try {
			Class.forName(DRIVER);
			System.out.println("��������");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		try {
			connection = DriverManager.getConnection(dbUrl2,USERNAME,PASSWORD);
			System.out.println("�������ݿ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("�������ݿ�ʧ��");
		}
		return connection;
	}

	public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException{
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		pstmt = connection.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = pstmt.getMetaData();
		int cols_len = metaData.getColumnCount();
		
		while (resultSet.next()) {
			for (int i = 0; i < cols_len; i++) {
				String col_name = metaData.getColumnName(i + 1);
				Object col_value = resultSet.getObject(col_name);
				if(col_value == null) {
					col_value = "";
				}
				map.put(col_name, col_value);
				
			}
		}
		return map;
	}
	
}
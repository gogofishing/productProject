package com.jdbc;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
		//print sql
		System.out.println(pstmt.toString());
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

	public void releaseConn() {
		// TODO Auto-generated method stub
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean updateByPreparedStatement(String sql, List<Object> params) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		int result = -1;
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params !=null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		result = pstmt.executeUpdate();
		flag = result > 0? true : false;
		return flag;
	}

	public List<Map<String, Object>> findMoreResult(String sql, List<Object> params) throws SQLException {
		// TODO Auto-generated method stub\
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		pstmt = connection.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		
		// print sql
		System.out.println("JdbcUtils line 147:" + pstmt.toString());
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			int cols_len = metaData.getColumnCount();
			for (int i = 0; i < cols_len; i++) {
				String col_name = metaData.getColumnName(i + 1);
				Object col_value = resultSet.getObject(col_name);
				if (col_value == null) {
					col_value = "";
				}
				map.put(col_name, col_value);
			}
			list.add(map);
		}
		return list;
	}
	
	
	/**
	 * ��ѯ���ص���JavaBean(ʹ��java�������)
	 * 
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> T findSimpleRefResult(String sql, List<Object> params,
			Class<T> cls) throws Exception {
		T resultObject = null;
		int index = 1; // ռλ��
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i)); // ���ռλ��
			}
		}
		resultSet = pstmt.executeQuery(); // ��ȡ��ѯ���
 
		ResultSetMetaData metaData = resultSet.getMetaData(); // ��ȡ�е���Ϣ
		int cols_len = metaData.getColumnCount(); // ��ȡ�ܵ�����
		while (resultSet.next()) {
			// ͨ��������ƴ���ʵ��
			resultObject = cls.newInstance(); // java�������
			for (int i = 0; i < cols_len; i++) {
				String col_name = metaData.getColumnName(i + 1); // ��ȡ��i�е�����
				Object col_value = resultSet.getObject(col_name); // ��ȡ��i�е�ֵ
				if (col_value == null) {
					col_value = "";
				}
				Field field = cls.getDeclaredField(col_name);
				field.setAccessible(true);// �� JavaBean�ķ��� privateȨ��
				field.set(resultObject, col_value);
			}
 
		}
 
		return resultObject;
	}
 
	/** ��ѯ���ض��JavaBean(ͨ��java�������)
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> findMoreRefResult(String sql, List<Object> params,
			Class<T> cls) throws Exception {
		List<T> list = new ArrayList<T>();
		int index = 1; //ռλ��
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery(); // ���ز�ѯ�������
 
		ResultSetMetaData metaData = resultSet.getMetaData(); // �����е���Ϣ
		int cols_len = metaData.getColumnCount(); // ��������ܵ�����
		while (resultSet.next()) {
			// ͨ��������ƴ���һ��javaʵ��
			T resultObject = cls.newInstance();
			for (int i = 0; i < cols_len; i++) {
				String col_name = metaData.getColumnName(i + 1); // ��õ�i�е�����
				Object col_value = resultSet.getObject(col_name); // ��õ�i�е�����
				if (col_value == null) {
					col_value = "";
				}
				Field field = cls.getDeclaredField(col_name);
				field.setAccessible(true); // ��JavaBean�ķ���privateȨ��
				field.set(resultObject, col_value);
			}
			list.add(resultObject);
 
		}
 
		return list;
	}

	public boolean deleteByBatch(String[] sql) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		stmt = connection.createStatement();
		if (sql != null) {
			for (int i = 0; i < sql.length; i++) {
				stmt.addBatch(sql[i]);
			}
			int[] count = stmt.executeBatch();
			if (count != null) {
				flag = true;
			}
		}
		return flag;
	}
	
}

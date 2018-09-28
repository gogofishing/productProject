package testJDBC;

import java.sql.*;

import javax.swing.JOptionPane;
public class JDBCtest {


	protected static String dbClassName = "com.mysql.cj.jdbc.Driver";
	protected static String dbUrl = "jdbc:mysql://localhost:3306/mydb";
	protected static String dbUrl2 = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
	
	protected static String dbUser = "root";
	protected static String dbPwd = "wjh123123123";
	
	protected static String second = null;//
	public static Connection conn = null;// MySQL数据库的连接对象

	public static void main(String[] args) {
		try {
			if (conn == null) {
				Class.forName(dbClassName).newInstance();
				System.out.println("数据库驱动加载成功");
				conn = DriverManager.getConnection(dbUrl2, dbUser, dbPwd);
				System.out.println("数据库连接成功");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "请将MySQL的JDBC驱动包复制到lib文件夹中。");// 捕获异常后，弹出提示框
			System.exit(-1);// 系统停止运行
		} catch (Exception e) {
			e.printStackTrace();
		}
		Statement statement;
		try {
			statement = conn.createStatement();
			String sql = "select * from userinfo";
			ResultSet rs = statement.executeQuery(sql);
			String name = null;
			int id = 0;
			String pas = null;
			while(rs.next()) {
				name = rs.getString("username");
				id = rs.getInt("id");
				pas = rs.getString("pswd");
				System.out.println("id:" + id + "\t" + "name:" + name + "\t" + "pass:" + pas);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}

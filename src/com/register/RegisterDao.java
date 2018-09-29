package com.register;
import java.util.List;

import com.jdbc.JdbcUtils;
public class RegisterDao implements RegisterService{

	private JdbcUtils jdbcUtils;
	public RegisterDao() {
		jdbcUtils = new JdbcUtils();
	}
	@Override
	public boolean registerUser(List<Object> params) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			jdbcUtils.getConnection();
			String sql = "insert into userinfo(username,pswd) values(?,?)";
			flag = jdbcUtils.updateByPreparedStatement(sql,params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}


}

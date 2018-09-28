package com.login;

import java.util.List;
import java.util.Map;

import com.jdbc.JdbcUtils;

public class LoginDao {
	private JdbcUtils jdbcUtils;
	
	public LoginDao() {
		jdbcUtils = new JdbcUtils();
	}

	public boolean login(List<Object> params) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			jdbcUtils.getConnection();
			String sql = "select * from userinfo where username = ? and pswd = ?";
			Map<String, Object> map = jdbcUtils.findSimpleResult(sql,params);
			flag = !map.isEmpty()?true:false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
}

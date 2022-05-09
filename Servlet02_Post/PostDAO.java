package com.kh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.dbcp2.BasicDataSource;

public class PostDAO {
	
	private BasicDataSource bds = new BasicDataSource();
	
	public PostDAO() {
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "kh";
		String password = "kh";
		
		bds.setUrl(url);
		bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		bds.setUsername(username);
		bds.setPassword(password);
		
		bds.setInitialSize(30);
	}
	public Connection getConnection() throws Exception{
		return bds.getConnection();
	}
	public int insert(String id, String post) throws Exception{
		String sql = "insert into tbl_post values(?, ?)";
		
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, id);
			pstmt.setString(2, post);
			
			int rs = pstmt.executeUpdate();
			return rs;
		}
	}

}

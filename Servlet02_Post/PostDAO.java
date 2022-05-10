package com.kh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.dbcp2.BasicDataSource;

import com.kh.dto.PostDTO;

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
	
	public int delete(String id) throws Exception{
		String sql = "delete from tbl_post where id = ?";
		
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, id);
			int rs = pstmt.executeUpdate();
			return rs;
		}
	}
	
	public int update(PostDTO dto) throws Exception{
		String sql = "update tbl_post set post = ? where id = ?";
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, dto.getPost());
			pstmt.setString(2, dto.getId());
			int rs = pstmt.executeUpdate();
			return rs;
		}
	}
	
	public ArrayList<PostDTO> selectAll() throws Exception{
		String sql = "select * from tbl_post";
		
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<PostDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				String id = rs.getString("id");
				String post = rs.getString("post");
				
				list.add(new PostDTO(id, post));
			}
			return list;
		}
	}

}

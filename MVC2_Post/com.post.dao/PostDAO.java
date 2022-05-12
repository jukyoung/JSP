package com.post.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.post.dto.PostDTO;

public class PostDAO {
	
	private BasicDataSource bds;
	
	public PostDAO() {
		
		try {
			Context iCtx = new InitialContext();
			Context envCtx = (Context)iCtx.lookup("java:comp/env");
			bds = (BasicDataSource)envCtx.lookup("jdbc/bds");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() throws Exception{
		return bds.getConnection();
	}
	public int insert(PostDTO dto) throws Exception{
		String sql = "insert into tbl_post values(seq_post.nextval,?, ?)";
		
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPost());
			int rs = pstmt.executeUpdate();
			return rs;
		}
	}
	
	public int delete(int seq) throws Exception{
		String sql = "delete from tbl_post where seq = ?";
		
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, seq);
			int rs = pstmt.executeUpdate();
			return rs;
		}
	}
	// seq로 데이터 조회
	public PostDTO selectBySeq(int seq) throws Exception{
		String sql = "select * from tbl_post where seq = ?";
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, seq);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String id = rs.getString("id");
				String post = rs.getString("post");
				return new PostDTO(seq, id, post);
			}
			return null;
		}
	}
	public int modify(PostDTO dto) throws Exception{
		String sql = "update tbl_post set id = ?,post = ? where seq = ?";
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPost());
			pstmt.setInt(3, dto.getSeq());
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
				int seq = rs.getInt("seq");
				String id = rs.getString("id");
				String post = rs.getString("post");
				
				list.add(new PostDTO(seq, id, post));
			}
			return list;
		}
	}

}

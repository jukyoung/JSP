package com.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.board.dto.FileDTO;

public class FileDAO {
private BasicDataSource bds;
	
	public FileDAO() {
		try {
			Context iCtx = new InitialContext();
			Context envCtx = (Context)iCtx.lookup("java:comp/env");
			bds = (BasicDataSource)envCtx.lookup("jdbc/bds");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	// 파일 이름 조회
	public FileDTO selectBySeq_Board(int seq_board) throws Exception{
		String sql = "select * from tbl_file where seq_board = ?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);) {
			
			pstmt.setInt(1, seq_board);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int seq_file = rs.getInt("seq_file");
				int rs_seq_board = rs.getInt("seq_board");
				String ori_name = rs.getString("ori_name");
				String sys_name = rs.getString("sys_name");
				
				return new FileDTO(seq_file, rs_seq_board, ori_name, sys_name);
			}
			return null;
		}
	}
	// file insert
	public int insert(FileDTO dto) throws Exception{
		String sql ="insert into tbl_file values(seq_file.nextval, ?, ?, ?)";
		
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);) {
			
			pstmt.setInt(1, dto.getSeq_board());
			pstmt.setString(2, dto.getOri_name());
			pstmt.setString(3, dto.getSys_name());
			
			int rs = pstmt.executeUpdate();
			return rs;
			
		}
	}
}

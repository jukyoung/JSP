package com.intro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.intro.dto.MessageDTO;

public class MessageDAO {
	private BasicDataSource bds;
	
	public MessageDAO() {
		try {
			Context iCtx = new InitialContext();
			Context envCtx = (Context)iCtx.lookup("java:comp/env");
			bds = (BasicDataSource)envCtx.lookup("jdbc/bds");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() throws Exception {
		return bds.getConnection();
	}
	public ArrayList<MessageDTO> selectAll() throws Exception{
		String sql = "select * from message";
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			ResultSet rs = pstmt.executeQuery();
			ArrayList<MessageDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				int seq = rs.getInt("seq");
				String nickname = rs.getString("nickname");
				String message = rs.getString("message");
				list.add(new MessageDTO(seq, nickname, message));
			}
			return list;
		}
	}
	public int insert(MessageDTO dto) throws Exception{
		String sql = "insert into message values(seq_msg.nextval, ?, ?)";
		
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, dto.getNickname());
			pstmt.setString(2, dto.getMessage());
			
			int rs = pstmt.executeUpdate();
			return rs;
			
		}
	}
}

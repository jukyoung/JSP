package com.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.board.dto.MemberDTO;
import com.board.utils.EncryptionUtils;

public class MemberDAO {
	private BasicDataSource bds;
	
	public MemberDAO() {
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
 // 정보 수정하기
 public int update(MemberDTO dto) throws Exception{
	 String sql = "update tbl_member set nickname =?, phone=?, "
	 		+ "postcode=?, roadAddr=?, detailAddr =?, extraAddr=? where id= ?";
	 try(Connection con = this.getConnection();
		 PreparedStatement pstmt = con.prepareStatement(sql);){
		 
		 pstmt.setString(1, dto.getNickname());
		 pstmt.setString(2, dto.getPhone());
		 pstmt.setString(3, dto.getPostcode());
		 pstmt.setString(4, dto.getRoadAddr());
		 pstmt.setString(5, dto.getDetailAddr());
		 pstmt.setString(6, dto.getExtraAddr());
		 pstmt.setString(7, dto.getId());
		 
		 int rs = pstmt.executeUpdate();
		 return rs;
	 }
 }
 // 정보 삭제하기
 public int delete(String id) throws Exception{
	 String sql = "delete from tbl_member where id=?";
	 
	 try(Connection con = this.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);){
		 
		 pstmt.setString(1, id);
		 int rs = pstmt.executeUpdate();
		 return rs;
	 }
 }
 
 // 아이디로 조회
 public MemberDTO selectById(String id) throws Exception{
		String sql = "select * from tbl_member where id = ?";
		
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String id_mem = rs.getString("id");
				String pw = rs.getString("pw");
				String nickname = rs.getString("nickname");
				String phone = rs.getString("phone");
				String postcode= rs.getString("postcode");
				String roadAddr = rs.getString("roadAddr");
				String detailAddr = rs.getString("detailAddr");
				String extraAddr = rs.getString("extraAddr");
				
				MemberDTO dto = new MemberDTO(id_mem, pw,nickname, phone, postcode, roadAddr, detailAddr, extraAddr);
				return dto;
			}
			return null;
		}
	}
 
 // 게시판 작성하면서 세션에 모든 값을 담아줘야 하기 때문에
 public MemberDTO isLoginOk(String id, String pw) throws Exception{
	 String sql = "select * from tbl_member where id=? and pw =?";
	 try(Connection con = this.getConnection();
		 PreparedStatement pstmt = con.prepareStatement(sql);){
		 
		 pstmt.setString(1, id);
		 pstmt.setString(2, pw);
		 
		 ResultSet rs = pstmt.executeQuery();
	
		 if(rs.next()) { // 로그인성공이라면
			 String nickname = rs.getString("nickname");
			 String phone = rs.getString("phone");
			 String postCode= rs.getString("postcode");
			 String roadAddr = rs.getString("roadAddr");
			 String detailAddr = rs.getString("detailAddr");		
			 String extraAddr = rs.getString("extraAddr");
			 
			 return new MemberDTO(id, null, nickname, phone, postCode, roadAddr, detailAddr, extraAddr);
					 
			}else { // 로그인 실패라면
				return null;
			}
		
	 }		
 }
 
 // 로그인 아이디 비밀번호 검사
// public boolean isLoginOk(String id, String pw) throws Exception{
//	 String sql = "select count(*) from tbl_member where id=? and pw =?";
//	 try(Connection con = this.getConnection();
//		 PreparedStatement pstmt = con.prepareStatement(sql);){
//		 
//		 pstmt.setString(1, id);
//		 pstmt.setString(2, pw);
//		 
//		 ResultSet rs = pstmt.executeQuery();
//		 
//		 int result = 0;
//		 // rs.next(); 이렇게 해도됨
//		 // int result = rs.getInt(1);
//		 if(rs.next()) {
//			result = rs.getInt(1); // 쿼리문 실행했을때 1행만 나옴!
//			}
//		 if(result == 1) { // 로그인 성공
//			 return true;
//		 }else { // 로그인 실패
//			 return false;
//		 }		
//		 }
//		
//	 }

 
 // 아이디 중복검사
 public boolean checkId(String id) throws Exception{
	 String sql = "select count(*) from tbl_member where id = ?";
	 try(Connection con = this.getConnection();
		 PreparedStatement pstmt = con.prepareStatement(sql);){
		 
		 pstmt.setString(1, id);
		 ResultSet rs = pstmt.executeQuery();
		 
		 int result = 0;
		 if(rs.next()) {
			 result = rs.getInt(1);
		 }
		 if(result == 0) { // 중복되지 않음
			 return true;
		 }else { // 중복됨
			 return false;
		 }
	 }
 }
 // insert
 public int signup(MemberDTO dto) throws Exception{
	 String sql = "insert into tbl_member values(?,?,?,?,?,?,?,?)";
	 
	 try(Connection con = this.getConnection();
		 PreparedStatement pstmt = con.prepareStatement(sql);){
		 
		 pstmt.setString(1, dto.getId());
		 pstmt.setString(2, dto.getPw());
		 pstmt.setString(3, dto.getNickname());
		 pstmt.setString(4, dto.getPhone());
		 pstmt.setString(5, dto.getPostcode());
		 pstmt.setString(6, dto.getRoadAddr());
		 pstmt.setString(7, dto.getDetailAddr());
		 pstmt.setString(8, dto.getExtraAddr());
		 
		 int rs = pstmt.executeUpdate();
		 return rs;
	 }
 }
}

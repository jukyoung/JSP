package com.student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.dbcp2.BasicDataSource;

import com.student.dto.StudentDTO;

public class StudentDAO {

	private BasicDataSource bds = new BasicDataSource();
	
	public StudentDAO() {
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "kh";
		String password = "kh";
		
		bds.setUrl(url);
		bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		bds.setUsername(username);
		bds.setPassword(password);
		
		bds.setInitialSize(30);
	}
	public Connection getConnection() throws Exception {
		return bds.getConnection();
	}
	// 데이터 삭제
	public int delete(int id) throws Exception{
		String sql = "delete from tbl_std where id=?";
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, id);
			int rs = pstmt.executeUpdate();
			return rs;
		}
	}
	// 데이터 수정
	public int update(StudentDTO dto) throws Exception{
	  String sql = "update tbl_std set name = ?, korean = ?, "
	  		+ "english = ?, math = ? where id = ?";
	  try(Connection con = this.getConnection();
		  PreparedStatement pstmt = con.prepareStatement(sql);){
		  
		  pstmt.setString(1, dto.getName());
		  pstmt.setInt(2, dto.getKorean());
		  pstmt.setInt(3, dto.getEnglish());
		  pstmt.setInt(4, dto.getMath());
		  pstmt.setInt(5, dto.getId());
		  
		  int rs = pstmt.executeUpdate();
		  return rs;
		  
	  }
	}
	// 데이터 삽입
	public int insert(StudentDTO dto) throws Exception{
		String sql = "insert into tbl_std values(seq_student.nextval, ?, ?, ?, ?)";
		
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getKorean());
			pstmt.setInt(3, dto.getEnglish());
			pstmt.setInt(4, dto.getMath());
			
			int rs = pstmt.executeUpdate();
			return rs;
		}	
	}
	// 이름으로 데이터 조회 -> 여러명일 수 있음
	public ArrayList<StudentDTO> selectByName(String name) throws Exception{
		String sql = "select * from tbl_std where name = ?";
		
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<StudentDTO> list = new ArrayList<>();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name_std = rs.getString("name");
				int korean = rs.getInt("korean");
				int english = rs.getInt("english");
				int math = rs.getInt("math");
				
				list.add(new StudentDTO(id, name_std, korean, english, math));
			}
			return list;
		}
	}
	
	
	// 데이터 하나만 조회
//	public StudentDTO select(String name) throws Exception{
//		String sql = "select * from tbl_std where name = ?";
//		
//		try(Connection con = this.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql);){
//			
//			pstmt.setString(1, name);
//			ResultSet rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				int id = rs.getInt("id");
//				String std_name = rs.getString("name");
//				int korean = rs.getInt("korean");
//				int english = rs.getInt("english");
//				int math = rs.getInt("math");
//				
//				StudentDTO dto = new StudentDTO(id, std_name, korean, english, math);
//				return dto;
//			}
//			return null;
//		}
//	}
	// 데이터 전체 조회
	public ArrayList<StudentDTO> selectAll() throws Exception{
		String sql = "select * from tbl_std";
		
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			ResultSet rs = pstmt.executeQuery();
			ArrayList<StudentDTO> list = new ArrayList<>();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int korean = rs.getInt("korean");
				int english = rs.getInt("english");
				int math = rs.getInt("math");
				
				list.add(new StudentDTO(id, name, korean, english, math));
			}
			return list;
		}
	}
}

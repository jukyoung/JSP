package com.cafe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.cafe.dto.CafeDTO;

public class CafeDAO {

	private BasicDataSource bds;
	
	public CafeDAO(){
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
	// 메뉴 수정
	public int modify(CafeDTO dto) throws Exception{
		String sql = "update tbl_cafe set product_name = ?, product_price =? where product_id =?";
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, dto.getProduct_name());
			pstmt.setInt(2, dto.getProduct_price());
			pstmt.setInt(3, dto.getProduct_id());
			int rs = pstmt.executeUpdate();
			return rs;
		}
	}
	// id 값을 가지고 조회
	public CafeDTO selectById(int product_id) throws Exception{
		String sql = "select * from tbl_cafe where product_id =?";
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, product_id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt("product_id");
				String name = rs.getString("product_name");
				int price = rs.getInt("product_price");
				return new CafeDTO(id, name, price);
				
			}
			return null;
		}
	}
	// 메뉴 삭제
	public int delete(int product_id) throws Exception{
		String sql = "delete from tbl_cafe where product_id = ?";
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, product_id);
			int rs = pstmt.executeUpdate();
			return rs;
		}
	}
	// 메뉴 등록
	public int insert(CafeDTO dto) throws Exception{
		String sql = "insert into tbl_cafe values(seq_cafe.nextval, ?, ?)";
		
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, dto.getProduct_name());
			pstmt.setInt(2, dto.getProduct_price());
			
			int rs = pstmt.executeUpdate();
			return rs;
		}
	}
	
	// 메뉴 전체 조회
	public ArrayList<CafeDTO> selectAll() throws Exception{
		String sql = "select * from tbl_cafe";
		
		try(Connection con = this.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			ResultSet rs = pstmt.executeQuery();
			ArrayList<CafeDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				int product_id = rs.getInt("product_id");
				String product_name = rs.getString("product_name");
				int product_price = rs.getInt("product_price");
				
				list.add(new CafeDTO(product_id, product_name, product_price));
			}
			return list;
		}
	}
}

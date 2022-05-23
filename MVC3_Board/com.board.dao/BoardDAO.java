package com.board.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.board.dto.BoardDTO;

public class BoardDAO {
	private BasicDataSource bds;
	
	public BoardDAO() {
		try {
			Context iCtx = new InitialContext();
			Context envCtx = (Context)iCtx.lookup("java:comp/env");
			bds = (BasicDataSource)envCtx.lookup("jdbc/bds");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	// 글쓴이(닉네임) 검색
	public ArrayList<BoardDTO> searchByNickname(String searchKeyword) throws Exception{
		String sql = "select * from tbl_board where writer_nickname like '%'||?||'%' order by 1 desc";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, searchKeyword);
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<BoardDTO> list = new ArrayList<>();
			while(rs.next()) {
				int seq_board = rs.getInt("seq_board");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String writer_nickname = rs.getString("writer_nickname");
				String writer_id = rs.getString("writer_id");
				int view_count = rs.getInt("view_count");
				String written_date = getStringDate(rs.getDate("written_date"));
				
				list.add(new BoardDTO(seq_board, title, content, writer_nickname, writer_id, view_count, written_date));
			}
			return list;
		}
	}
	// 내용 검색
	public ArrayList<BoardDTO> searchByContent(String searchKeyword) throws Exception{
		String sql = "select * from tbl_board where content like '%'||?||'%' order by 1 desc";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, searchKeyword);
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<BoardDTO> list = new ArrayList<>();
			while(rs.next()) {
				int seq_board = rs.getInt("seq_board");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String writer_nickname = rs.getString("writer_nickname");
				String writer_id = rs.getString("writer_id");
				int view_count = rs.getInt("view_count");
				String written_date = getStringDate(rs.getDate("written_date"));
				
				list.add(new BoardDTO(seq_board, title, content, writer_nickname, writer_id, view_count, written_date));
			}
			return list;
		}
	}
	// 제목 검색
	public ArrayList<BoardDTO> searchByTitle(String searchKeyword) throws Exception{
		String sql = "select * from tbl_board where title like '%'||?||'%' order by 1 desc";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, searchKeyword);
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<BoardDTO> list = new ArrayList<>();
			while(rs.next()) {
				int seq_board = rs.getInt("seq_board");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String writer_nickname = rs.getString("writer_nickname");
				String writer_id = rs.getString("writer_id");
				int view_count = rs.getInt("view_count");
				String written_date = getStringDate(rs.getDate("written_date"));
				
				list.add(new BoardDTO(seq_board, title, content, writer_nickname, writer_id, view_count, written_date));
			}
			return list;
		}
	}
	// 조회수 올리기 -> 결과값 리턴할 필요없어서 void 로 만듬
	public void updateView_count(int seq_board) throws Exception{
		String sql ="update tbl_board set view_count = view_count+1 where seq_board = ?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, seq_board);
			pstmt.executeUpdate();
		}
	}
	// 게시글 삭제하기
	public int delete(int seq_board) throws Exception{
		String sql = "delete from tbl_board where seq_board=?";
		try(Connection con = bds.getConnection();
		    PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, seq_board);
			int rs = pstmt.executeUpdate();
			return rs;
		}
	}
	// 게시글 수정하기
	public int modify(int seq_board, String title, String content) throws Exception{
		String sql = "update tbl_board set title =?, content =? where seq_board =?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, seq_board);
			
			int rs = pstmt.executeUpdate();
			return rs;
		}
	}
	// 아이디로 게시글 상세보기
	public BoardDTO selectBySeq(int seq_board) throws Exception{
		String sql = "select * from tbl_board where seq_board =?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, seq_board);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				int seq_board1 = rs.getInt("seq_board");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String writer_nickname = rs.getString("writer_nickname");
				String writer_id = rs.getString("writer_id");
				int view_count = rs.getInt("view_count");
				String written_date = getStringDate(rs.getDate("written_date"));
				
				BoardDTO dto = new BoardDTO(seq_board1,title,content,writer_nickname,writer_id,view_count,written_date);
				return dto;
			}
			return null;
		}
	}
	// 모든 게시글 조회
	public ArrayList<BoardDTO> selectAll() throws Exception{
		String sql = "select * from tbl_board order by 1 desc";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<BoardDTO> list = new ArrayList<>();
			while(rs.next()) {
				int seq_board = rs.getInt("seq_board");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String writer_nickname = rs.getString("writer_nickname");
				String writer_id = rs.getString("writer_id");
				int view_count = rs.getInt("view_count");
				String written_date = getStringDate(rs.getDate("written_date"));
				
				list.add(new BoardDTO(seq_board, title, content, writer_nickname, writer_id, view_count, written_date));
			}
			return list;
		}
	}
	

	// 게시글 작성
	public int write(BoardDTO dto) throws Exception{
		String sql = "insert into tbl_board values(seq_board.nextval, ?, ?, ?, ?, 0, sysdate)";
		
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getWriter_nickname());
			pstmt.setString(4, dto.getWriter_id());
			
			int rs = pstmt.executeUpdate();
			return rs;
		}
	}
	
	// date 형 -> String 으로 변환
	public String getStringDate(Date date) { // oracle의 date 타입을 받아야함
		// oracle date타입의 데이터를 java의 String을 변환 -> SimpleDateFormat
		// 생성자의 인자값을 String으로 변환할때 어떤 형식으로 변환할 것인기 format
		// format의 대소문자 구분하기
		// oracle 월(mm/MM) 분(mi)
		// java 월(MM) 분(mm)
		// 1900년 02월 02일 00시 00분 00초
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
		return sdf.format(date);
	}
}

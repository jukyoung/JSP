package com.board.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

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
	// 모든 게시글 조회 가 아니라 페이징 작업으로 하기 
	public ArrayList<BoardDTO> selectAll(int start, int end) throws Exception{
		String sql = "select * from (select tbl_board.*, row_number() over(order by seq_board desc)as num from tbl_board)"
				+ "where num between ? and ?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
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
		String sql = "insert into tbl_board values(?, ?, ?, ?, ?, 0, sysdate)";
		
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, dto.getSeq_board());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getWriter_nickname());
			pstmt.setString(5, dto.getWriter_id());
			
			int rs = pstmt.executeUpdate();
			return rs;
		}
	}
	// seq 반환
	// dual 테이블 간단한 값 꺼낼때나 계산할때 사용
	public int getNewSeq() throws Exception{
		String sql = "select seq_board.nextval from dual";
		
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int seq_board = rs.getInt(1);
			return seq_board;
			
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
	// 현재 머물고 있는 페이지가 어디인지에 따라 네비값은 어떻게 띄워줄지
	public HashMap<String, Object> getpageNavi(int curPage) throws Exception{ 
		String sql = "select count(*)as totalCnt from tbl_board"; //별칭 totalCnt 로 붙이기
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			ResultSet rs = pstmt.executeQuery();
			rs.next(); //커서를 아래로 내려서 갯수 가져오기
			
			int totalCnt = rs.getInt("totalCnt"); //전체 게시글의 개수를 별칭이용해서 가져오기
			int recordCntPerPage = 10; // 한 페이지에 몇개의 데이터(게시글)을 띄워줄지(지금은 10개)
			int naviCntPerPage = 5; // 네비바에 몇개 단위로 페이징을 구성할지(지금은 5단위)
			int pageTotalCnt = 0; // 총 몇 페이지가 나올지
			
			/* 현재 DB에 146개의 게시글
			 * 146개의 게시글 기준 10개씩 페이징을 해준다면 총 15개의 페이지가 나와야함.
			 * pageTotalCnt = 15;
			 * 고정적인 값을 사용할 수 없음 -> 왜냐하면 사용자가 삭제하거나 더 작성하기 때문
			 * 
			 * 148 / 10 = 14페이지 + 1 = 15페이지
			 * 140/10 = 14 + 1 = 15 -> 올바르지 않은 값이 됨
			 * */
			
			// 총 페이지의 개수를 구하는 것.
			if(totalCnt % recordCntPerPage > 0) { // 나머지가 생김(10의 배수가 아님x)
				pageTotalCnt = totalCnt / recordCntPerPage + 1;
			}else {
				pageTotalCnt = totalCnt / recordCntPerPage;
			}
			/* 현재 페이지는 반드시 1 이상
			 * 현재 페이지는 총 페이지의 개수를 넘어갈 수 없음
			 * */
			if(curPage < 1){ // 현재 페이지가 0이거나 음수일때
				curPage = 1; // 무조건 첫페이지로 맞춰주기
			}else if(curPage > pageTotalCnt) { // 현재 페이지가 총 페이지 수보다 크다면
				curPage = pageTotalCnt; // 무조건 마지막페이지로 맞춰주기
			}
			
			/* 현재 페이지를 기준으로
			 * 네비의 시작페이지, 끝페이지를 잡을 것
			 * 
			 * 만약 현재 페이지가 3페이지라면
			 * 네비의 시작페이지 = 1 / 네비의 끝 페이지 = 5
			 * 
			 * 만약 현재 페이지가 6페이지라면
			 * 네비의 시작페이지 = 6 / 네비의 끝 페이지 = 10
			 * 
			 * (현재 페이지 / 페이지 단위) * 페이지 단위 + 1
			 * 3 / 5 = 0 * 5 = 0 + 1 = 1
			 * 5 / 5 = 1 * 5 = 5 + 1 = 6
			 * 
			 * ((현재 페이지-1) / 페이지 단위) * 페이지 단위 + 1
			 * 3-1 =2 / 5 = 0 * 5 = 0 + 1 =1
			 * 5-1 = 4 / 5 = 0 * 5 = 0 + 1 = 1
			 * 
			 * 6-1 =5 / 5 = 1 * 5 = 5 + 1 = 6
			 * 10 - 1 = 9 / 5 = 1 * 5 + 1 = 6
			 * */
			int startNavi = ((curPage - 1) / naviCntPerPage) * naviCntPerPage + 1;
			int endNavi = startNavi + (naviCntPerPage - 1);
			
			// endNavi가 전체페이지를 넘어갈 수 없음
			if(pageTotalCnt < endNavi) { // endNavi가 전체 페이지수보다 크다면
				endNavi = pageTotalCnt; // endNavi를 마지막 페이지로 수정해주겠다.
			}
			
			
			// < > 모양을 넣어줄지 여부에 대한 검사
			boolean needPrev = true; // startNavi 가 1 일때 needPrev 가 false
			boolean needNext = true; // endNavi 가 마지막페이지(전체 페이지)와 같을때 needNext 가 false
			
			if(startNavi == 1) {
				needPrev = false;
			}
			if(endNavi == pageTotalCnt) {
				needNext = false;
			}
			
			
			// map -> key, value int boolean 둘다 들어가야 하므로 일단 Object 형으로 넣기
			// 제네릭 <키에 대한 자료형, 값에 대한 자료형>
			HashMap<String, Object> map = new HashMap<>();
			map.put("startNavi", startNavi);
			map.put("endNavi", endNavi);
			map.put("needPrev", needPrev);
			map.put("needNext", needNext);
			
			return map;
			
		}
	}
}

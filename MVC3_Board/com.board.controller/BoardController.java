package com.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.BoardDAO;
import com.board.dao.ReplyDAO;
import com.board.dto.BoardDTO;
import com.board.dto.MemberDTO;
import com.board.dto.ReplyDTO;
import com.google.gson.Gson;

@WebServlet("*.bo")
public class BoardController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri= request.getRequestURI();
		System.out.println("요청 uri : " + uri);
		request.setCharacterEncoding("utf-8");
		if(uri.equals("/board.bo")) { // 게시판 이동 요청
			// tbl_board 테이블에서 arraylist 로 가져와 request 에 담아주기
			BoardDAO dao = new BoardDAO();
			try {
				ArrayList<BoardDTO> list = dao.selectAll();
//				for(BoardDTO dto : list) {
//					System.out.println(dto.toString());
//					
//				}
				request.setAttribute("list", list);
			}catch(Exception e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("/board/board.jsp").forward(request, response);
		}else if(uri.equals("/write.bo")) { //글쓰기페이지 요청
			response.sendRedirect("/board/write.jsp");
		}else if(uri.equals("/writeProc.bo")) { // 글쓰기 요청
			MemberDTO dto = (MemberDTO)request.getSession().getAttribute("loginSession");
			// 글쓴사람이 누군지 알기 위해 세션에서 값 꺼내오기
			String writer_nickname = dto.getNickname();
			String writer_id = dto.getId();
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			System.out.println(writer_nickname + " : " + writer_id + " : " + title + " : " + content);
			
			BoardDAO dao = new BoardDAO();
			
			try {
				int rs = dao.write(new BoardDTO(0, title, content, writer_nickname, writer_id, 0, null));
				if(rs > 0) {
					response.sendRedirect("/board.bo");
					}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/detailView.bo")) { // 상세보기페이지 요청
			int seq_board = Integer.parseInt(request.getParameter("seq_board"));
			
			System.out.println("seq_board : " + seq_board);
			BoardDAO dao = new BoardDAO();
			//seq_board 를 이용해 게시글에 대한 데이터 조회
			// request 에 데이터 추가
			// /board/detailView.jsp
			ReplyDAO redao = new ReplyDAO();
			try {
				// 순서 조심할것 상세보기 페이지 값을 가져와서 조회수를 더해주면 그 값은 dto 에 반영이 안됨
				// 조회수 먼저 해줘야함
				// 조회수를 +1
				dao.updateView_count(seq_board);
				
				// 상세보기 페이지를 뿌려주기 위한 작업
				BoardDTO dto = dao.selectBySeq(seq_board);
				request.setAttribute("dto", dto);
				//게시글에 해당하는 댓글 가져와 담아주는 작업
				ArrayList<ReplyDTO> list = redao.selectAll(seq_board);
				request.setAttribute("list", list);
				request.getRequestDispatcher("/board/detailView.jsp").forward(request, response);

			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else if(uri.equals("/modify.bo")) { // 수정페이지로 이동 요청
			int seq_board = Integer.parseInt(request.getParameter("seq_board"));
			System.out.println("seq_board : " + seq_board);
			
			BoardDAO dao = new BoardDAO();
			String id = ((MemberDTO)request.getSession().getAttribute("loginSession")).getId();
			try {
				BoardDTO dto = dao.selectBySeq(seq_board);
			
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("/board/modify.jsp").forward(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/modifyProc.bo")) { // 수정 요청
			int seq_board = Integer.parseInt(request.getParameter("seq_board"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			System.out.println(seq_board + " : " + title + " : " + content);
			
			BoardDAO dao = new BoardDAO();
			try {
				int rs = dao.modify(seq_board, title, content);
				if(rs > 0) {
					// 수정된 값을 바로 확인가능하게끔 상세페이지 요청 (/detailView.bo)
					// -> 에러남 java.lang.NumberFormatException: null
					// -> BoardController.java:65 여기서는 seq_board를 받아야함
					// 데이터를 안넘겨주는 상태임 seq_board 넘기게 주소값에 넘기기
					response.sendRedirect("/detailView.bo?seq_board="+seq_board);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/delete.bo")) { // 삭제 요청
			int seq_board = Integer.parseInt(request.getParameter("seq_board"));
			System.out.println("seq_board : " + seq_board);
			
			BoardDAO dao = new BoardDAO();
			try {
				int rs = dao.delete(seq_board);
				if(rs > 0) {
					response.sendRedirect("/board.bo");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/searchProc.bo")) { //제목 검색 요청
			String searchKeyword = request.getParameter("searchKeyword");
			System.out.println("keyword : " + searchKeyword);
			
			BoardDAO dao = new BoardDAO();
			try {
				ArrayList<BoardDTO> list = dao.searchByTitle(searchKeyword);
				//request.setAttribute("list", list);
				// 테이블만 다시 뿌려줘야하는 방식
				// 부분적인 부분만 움직이는것 -> 비동기
				Gson gson = new Gson();
				String rs = gson.toJson(list);
				System.out.println(rs);
				response.setCharacterEncoding("utf-8");
				response.getWriter().append(rs);
				
				//request.getRequestDispatcher("/board/board.jsp").forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

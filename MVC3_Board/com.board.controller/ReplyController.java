package com.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dao.ReplyDAO;
import com.board.dto.BoardDTO;
import com.board.dto.MemberDTO;
import com.board.dto.ReplyDTO;
import com.google.gson.Gson;

@WebServlet("*.rp")
public class ReplyController extends HttpServlet {
	
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
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		if(uri.equals("/insert.rp")) { // 댓글 등록
			int seq_board = Integer.parseInt(request.getParameter("seq_board"));
			String content = request.getParameter("content");
			String writer_nickname = ((MemberDTO)session.getAttribute("loginSession")).getNickname();
			String writer_id = ((MemberDTO)session.getAttribute("loginSession")).getId();
			
			System.out.println(seq_board + " : " + content + " : " + writer_nickname + " : " + writer_id);
			
			ReplyDAO dao = new ReplyDAO();
			try {
				int rs = dao.insert(new ReplyDTO(0, seq_board, content, writer_nickname, writer_id, null));
				if(rs > 0) { // 댓글 등록이 제대로 이뤄졌다면
					ArrayList<ReplyDTO> list = dao.selectAll(seq_board);
					Gson gson = new Gson();
					String result = gson.toJson(list);
					response.getWriter().append(result);
				}else { // 댓글 등록에 실패했다면
					response.getWriter().append("fail");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/deleteProc.rp")) { // 삭제요청
			int seq_reply = Integer.parseInt(request.getParameter("seq_reply"));
			int seq_board = Integer.parseInt(request.getParameter("seq_board"));
			System.out.println("삭제 요청 seq : " + seq_reply + ", seq_board : " + seq_board);
			
			ReplyDAO dao = new ReplyDAO();
			try {
				int rs = dao.delete(seq_reply);
				if(rs > 0) { // 댓글 삭제 성공, 댓글 목록 응답
					ArrayList<ReplyDTO> list = dao.selectAll(seq_board);
					Gson gson = new Gson();
					String result = gson.toJson(list);
					response.getWriter().append(result);
				}else { // 댓글 삭제 실패, fail
					response.getWriter().append("fail");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/modifyProc.rp")) { // 댓글 수정요청
			int seq_reply = Integer.parseInt(request.getParameter("seq_reply"));
			int seq_board = Integer.parseInt(request.getParameter("seq_board"));
			String content = request.getParameter("content");
			
			System.out.println(seq_reply + " : " + seq_board + " : " + content);
			
			ReplyDAO dao = new ReplyDAO();
			
			try {
				int rs = dao.modify(seq_reply, seq_board, content);
				if(rs > 0) {
					ArrayList<ReplyDTO> list = dao.selectAll(seq_board);
					Gson gson = new Gson();
					String result = gson.toJson(list);
					response.getWriter().append(result);
				}else {
					response.getWriter().append("fail");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/cancleModify.rp")) { // 수정 취소 요청
			int seq_board = Integer.parseInt(request.getParameter("seq_board"));
			ReplyDAO dao = new ReplyDAO();
			
			try {
					ArrayList<ReplyDTO> list = dao.selectAll(seq_board);
					if(list != null) {
						Gson gson = new Gson();
						String result = gson.toJson(list);
						response.getWriter().append(result);
					}else {
						response.getWriter().append("fail");
					}
					
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

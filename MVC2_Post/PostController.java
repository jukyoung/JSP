package com.post.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.post.dao.PostDAO;
import com.post.dto.PostDTO;

@WebServlet("*.post")
public class PostController extends HttpServlet {
	/* 1. 하나의 객체(DTO)를 대표할 수 잇는 Controller(Servlet) 생성
	 * 2. 하나의 객체와 관련된 기능을 요청하기 위해 일관된 url 패턴을 생성 (예 : ~.post)
	 * 3. front controller 의 annotation url 값을 .post로 끝나는 모든 요청을 받아줄 수 있게 *.post 형식으로 잡아준다
	 *  *앞에 /(슬래시) 붙이지 않음
	 * 4. 하나의 객체의 기능과 관련된 클라이언트의 요청값을 모두 .post 로 끝나게끔 잡아줌.
	 * 5. doAction() 메서드 생성
	 * 6. doGet, doPost가 무조건 doAction 메서드를 호출하도록 만듦
	 * 7. doAction 메서드 내부에서 요청 url 에 대한 분석
	 * 8. if문을 통해서 기능을 분리
	 *    -> 각각의 컨트롤러에서 해줬던 작업을 if문 안쪽에 분리해서 넣어줌
	 * */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 클라이언트가 보낸 요청 url (URI =url)
	String uri = request.getRequestURI();
	System.out.println("uri : " + uri);
	 if(uri.equals("/toInput.post")) { // input 페이지 요청
	    response.sendRedirect("/input.jsp");
	}else if(uri.equals("/insert.post")) { // 포스트등록
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String post = request.getParameter("post");
		System.out.println(id + " : " + post);
			
		PostDAO dao = new PostDAO();
		try {
			int rs = dao.insert(new PostDTO(0, id, post));
		  if(rs > 0) { //insert 제대로 수행된 경우
		      System.out.println("success");
		  }else {
					System.out.println("failed");
			}
			/*
			* response.sendRedirect("url");
			* - requset, response 가 유지되지 않음
			* - url 값이 변동된다
			* - 클라이언트의 데이터가 넘어와서 삽입, 수정, 삭제(DB의 값이 변경될때)
			* 
			* forward(request, response);
			* - request, response 가 유지됨
			* - url 값도 처음 요청한 url 값으로 유지됨
			* - 새로고침을 하게 되면 중복된 데이터의 입력 / 삭제 / 수정이 일어날 수 있음
			* -> 조회한 값을 jsp에 넘겨줘야 할때 / servlet(controller)가 들고 있는 값을 jsp에 전달해야 되는 경우
			* */
			response.sendRedirect("/index.jsp"); //-> url 값이 변동된다 index.jsp 로 변동
			// 클라이언트가 즉시 요청하기 때문에 url 값이 변하는것
			//request.getRequestDispatcher("/index.jsp").forward(request, response);
			// -> 가장 처음 요청한 url 값으로 유지한다 (insert) 로 유지 클라이언트가 내부적으로 하는것 알 수 없음
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/toOutput.post")) { //output 페이지 이동
			PostDAO dao = new PostDAO();
			try {
				ArrayList<PostDTO> list = dao.selectAll();
				request.setAttribute("list", list);
				request.getRequestDispatcher("/output.jsp").forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/delete.post")) { //삭제
			int seq = Integer.parseInt(request.getParameter("seq"));
			System.out.println("seq : " + seq);
			
			PostDAO dao = new PostDAO();
			try {
				int rs = dao.delete(seq);
				if(rs > 0) {
					response.sendRedirect("/toOutput.post");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/modify.post")) { //modify 페이지로 이동
			int seq = Integer.parseInt(request.getParameter("seq"));
		 	System.out.println("seq : " + seq);
		 	
		 	// 1. DAO에게 seq 를 넘겨서 그 seq에 해당하는 행의 데이터를 조회
		 	// 2. 조회해온 데이터(DTO)를 modify.jsp에게 넘겨줌
		 	PostDAO dao = new PostDAO();
		 	try {
		 		PostDTO dto = dao.selectBySeq(seq);
		 		if(dto != null) { // 조회된 값이 있다면 modify.jsp로 값을 넘겨줌
		 			request.setAttribute("dto", dto);
		 			request.getRequestDispatcher("/modify.jsp").forward(request, response);
		 		}else { // 조회된 값이 없는 경우 -> controller 가 값을 뿌려주고 있음
		 			response.sendRedirect("/toOutput.post");
		 		}
		 	}catch(Exception e) {
		 		e.printStackTrace();
		 	}
			
		}else if(uri.equals("/modifyProc.post")) { // 수정요청
			request.setCharacterEncoding("utf-8");
			int seq = Integer.parseInt(request.getParameter("seq"));
			String id = request.getParameter("id");
			String post = request.getParameter("post");
			
			System.out.println(seq + " : " + id + " : " + post);
			
			PostDAO dao = new PostDAO();
			try {
				int rs = dao.modify(new PostDTO(seq, id, post));
				if(rs > 0) {
					response.sendRedirect("/toOutput.post");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		}

}

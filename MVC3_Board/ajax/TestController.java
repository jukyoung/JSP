package com.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.BoardDAO;
import com.board.dto.BoardDTO;
import com.google.gson.Gson;


@WebServlet("*.test")
public class TestController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		System.out.println("요청 uri : " + uri);
		
		if(uri.equals("/test.test")) {
			response.sendRedirect("/test.jsp");
		}else if(uri.equals("/toInput.test")) {
			System.out.println("요청 도착");
		}else if(uri.equals("/sendMsg.test")) {
			String msg = request.getParameter("msg");
			System.out.println("msg : " + msg);
		}else if(uri.equals("/sendForm.test")) {
			String id = request.getParameter("id");
			String nickname = request.getParameter("nickname");
			System.out.println(id + " : " + nickname);
		}else if(uri.equals("/getData.test")) { // 텍스트 데이터 요청
			String msg = request.getParameter("msg");
			System.out.println("msg : " + msg);
			System.out.println("데이터 요청");
			// ajax로 통신할때 서버에서 클라이언트로 데이터를 전송하고 싶다면
			// response.getWriter().append(보낼값); 사용
			//response.getWriter().append("Hello World");
			if(msg.equals("hello")) {
				response.getWriter().append("Hello World");
			}else if(msg.equals("goodbye")) {
				response.getWriter().append("Goodbye World");
			}
		}else if(uri.equals("/getDTO.test")) { //DTO 데이터 요청
			System.out.println("DTO 데이터 요청");
			BoardDAO dao = new BoardDAO();
			
			try {
				BoardDTO dto = dao.selectBySeq(2);
//				System.out.println(dto.toString());
				// Gson -> 객체나 객체배열을 ajax로, json 타입으로 전송하기 위해 사용하는 라이브러리
				/* json(javascript object notation) : 즉 객체
				 * ->{key:value, key:value, key:value......}
				 * 
				 * toJson(객체나 객체배열) : json 형식의 문자열로 변환해줌.
				 * */
				Gson gson = new Gson();
				String rs = gson.toJson(dto);
//				System.out.println(rs);
				response.setCharacterEncoding("utf-8");
				response.getWriter().append(rs);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/getList.test")) { // list 데이터 요청
			System.out.println("List 데이터 요청");
			BoardDAO dao = new BoardDAO();
			try {
				ArrayList<BoardDTO> list = dao.selectAll();
				Gson gson = new Gson();
				String rs = gson.toJson(list);
				response.setCharacterEncoding("utf-8");
				response.getWriter().append(rs);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

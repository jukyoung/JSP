package com.intro.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intro.dao.MessageDAO;
import com.intro.dto.MessageDTO;


@WebServlet("/insert")
public class InsertController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// nickname, message -> DAO 구성 insert 메서드 만들어서 DB 저장
		// message 테이블명 / seq / nickname / message 	/ seq_msg 시퀀스 생성
		request.setCharacterEncoding("utf-8");
		String nickname = request.getParameter("nickname");
		String message = request.getParameter("message");
		System.out.println(nickname + " : " + message);
		
		MessageDAO dao = new MessageDAO();
		
		try {
			int rs = dao.insert(new MessageDTO(0, nickname, message));
			
			if(rs > 0) {
				response.sendRedirect("/home.jsp");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

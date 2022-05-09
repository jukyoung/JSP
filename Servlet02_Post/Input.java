package com.kh.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.dao.PostDAO;


@WebServlet("/Input.proc")
public class Input extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String post = request.getParameter("post");
		
		System.out.println("id : " + id);
		System.out.println("post : " + post);
		
		PostDAO dao = new PostDAO();
		try {
			int rs = dao.insert(id, post);
			
			if(rs > 0) {
				System.out.println("post 저장 완료!");
				response.sendRedirect("/index.html");
			}else {
				System.out.println("post 저장 실패!");
				response.sendRedirect("/input.html");
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

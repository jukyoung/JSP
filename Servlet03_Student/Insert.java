package com.student.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.student.dao.StudentDAO;
import com.student.dto.StudentDTO;


@WebServlet("/insert")
public class Insert extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		int korean = Integer.parseInt(request.getParameter("korean"));
		int english = Integer.parseInt(request.getParameter("english"));
		int math = Integer.parseInt(request.getParameter("math"));
		
		// 값이 들어온게 맞는지 확인하기!
		System.out.println(name + " : " + korean + " : " + english + " : " + math);
		
		StudentDAO dao = new StudentDAO();
		try {
			// 시퀀스 자리는 의미없는 0값 넣어도 됨 new StudentDTO(0, name, korean, english, math)
			// 또는 DTO클래스에서 생성자에서 매개변수를 4개 받는것을 생성해서 하기
			int rs = dao.insert(new StudentDTO(name, korean, english, math));
			if(rs > 0) {
				response.sendRedirect("/index.html");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

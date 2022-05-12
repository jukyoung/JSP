package com.intro.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intro.dao.MessageDAO;
import com.intro.dto.MessageDTO;

@WebServlet("/toOutput")
public class OutputController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// controller 에서는 데이터를 dao를 통해 가져오는 작업
		/*
		 *  response.sendRedirect(url)
		 *  : 클라이언트에게 url을 요청해라 라는 응답값을 줌.
		 *  
		 *  request.setAttribute(key, value)
		 *  : request에 값을 추가하고 싶을때 사용하는 메서드
		 *  key -> 내가 추가해준 값을 꺼내고 싶을때 사용하는 식별자
		 *  key 값은 반드시 중복값을 사용하지 않음
		 *  value -> 내가 실제로 추가해준 값 그 자체
		 *  
		 *  forward(request, response)
		 *  : 서버가 만들어둔 request, response를 그대로 전달하는 작업을 해줌
		 * */
		
		// 가져온 데이터를 jsp 전달
//		String temp = "hello";
//		
//		request.setAttribute("temp", temp);
//		request.setAttribute("number", 50);
		
		// 객체 단위의 데이터 전달
		MessageDTO dto = new MessageDTO(1, "tom", "hi, hello, good");
		request.setAttribute("dto", dto);
		MessageDAO dao = new MessageDAO();
		try {
			ArrayList<MessageDTO> list = dao.selectAll();
			request.setAttribute("list", list);
		}catch(Exception e) {
			e.printStackTrace();
		}
		// request.getRequestDispatcher(request를 전달할 경로값)
		request.getRequestDispatcher("/output.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

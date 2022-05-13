package com.cafe.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe.dao.CafeDAO;
import com.cafe.dto.CafeDTO;

@WebServlet("*.cafe")
public class CafeController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  doAction(request, response);
	}
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		System.out.println("uri : " + uri);
		
		if(uri.equals("/toAdd.cafe")) { // 메뉴 버튼 있는 곳으로
			response.sendRedirect("/add.jsp");
		}else if(uri.equals("/addMenu.cafe")) { // 등록버튼을 누르면
			request.setCharacterEncoding("utf-8");
			String product_name = request.getParameter("product_name");
			int product_price = Integer.parseInt(request.getParameter("product_price"));
			System.out.println(product_name + " : " + product_price);
			
			CafeDAO dao = new CafeDAO();
			try {
				int rs = dao.insert(new CafeDTO(0, product_name, product_price));
				if(rs > 0) {
					System.out.println("success");
				}else {
					System.out.println("failed");
				}
				response.sendRedirect("/index.jsp");
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else if(uri.equals("/toList.cafe")) { // 메뉴 조회
			CafeDAO dao = new CafeDAO();
			
			try {
				ArrayList<CafeDTO> list = dao.selectAll();
				request.setAttribute("list", list);
				request.getRequestDispatcher("/list.jsp").forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/delete.cafe")) { //삭제버튼 눌렀을때
			int product_id = Integer.parseInt(request.getParameter("product_id"));
			System.out.println("product_id : " + product_id);
			CafeDAO dao = new CafeDAO();
			try {
				int rs = dao.delete(product_id);
				if(rs > 0) {
					response.sendRedirect("/toList.cafe");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/update.cafe")) { // 수정버튼 눌렀을때
			int product_id = Integer.parseInt(request.getParameter("product_id"));
			System.out.println("product_id : " + product_id);
			CafeDAO dao = new CafeDAO();
			
			try {
				CafeDTO dto = dao.selectById(product_id);
				if(dto != null) {
					request.setAttribute("dto", dto);
					request.getRequestDispatcher("/update.jsp").forward(request, response);
				}else {
					response.sendRedirect("/tolist.cafe");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/modify.cafe")) { //메뉴수정
			request.setCharacterEncoding("utf-8");
			int product_id = Integer.parseInt(request.getParameter("product_id"));
			String product_name = request.getParameter("product_name");
			int product_price = Integer.parseInt(request.getParameter("product_price"));
			CafeDAO dao = new CafeDAO();
			try {
				int rs = dao.modify(new CafeDTO(product_id, product_name, product_price));
				if(rs > 0) {
					response.sendRedirect("/toList.cafe");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

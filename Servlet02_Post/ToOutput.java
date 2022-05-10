package com.kh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.dao.PostDAO;
import com.kh.dto.PostDTO;

@WebServlet("/toOutput.proc")
public class ToOutput extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PostDAO dao = new PostDAO();
		try {
			ArrayList<PostDTO> list = dao.selectAll();
			
			for(PostDTO dto : list) {
				System.out.println(dto.toString());
			}
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			
			out.write("<html>");
			out.write("<head>");
			out.write("<style>");
			out.write("*{border : 1px solid black}");
			out.write("table {width: 400px; height: 300px; text-align: center}");
			out.write("</style>");
			out.write("</head>");
			out.write("<body>");
			out.write("<table>");
			out.write("<tr>"
					+ "<th>Id</th>"
					+ "<th>Post</th>"
					+ "</tr>");
			for(PostDTO dto : list) {
				out.write("<tr>"
						+ "<td>"+ dto.getId() + "</td>"
						+ "<td>" + dto.getPost() + "</td>"
						+ "</tr>");
			}
			out.write("<tr>"
					+ "<form action='/delete.proc' method='post'>"
					+ "<td colspan=2><input name='id' type='text' placeholder='삭제할 id 입력'>"
					+ "<button type='submit'>삭제</button></td>"
					+ "</form>"
					+ "</tr>");
			out.write("<tr>");
			out.write("<form action='/update.proc' method='post'>"
					+ "<td colspan=2>");
			out.write("<input type='text' name='id' placeholder='수정할 id 입력'><br>"
					+ "<textarea name='post' placeholder='수정할 포스트 입력'></textarea>"
					+ "<button type='submit'>수정</button>"
					+ "</td>");
			out.write("</form>");
			out.write("</tr>");
			out.write("<tr><td colspan=2>"
					+ "<button type='button' id='btnBack'>Back</button>");
			out.write("</td></table>");
			out.write("<script>");
			out.write("document.getElementById('btnBack').onclick = function(){");
			out.write("location.href = '/index.html';");
			out.write("}");
			out.write("</script>");
			out.write("</body>");
			out.write("</html>");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

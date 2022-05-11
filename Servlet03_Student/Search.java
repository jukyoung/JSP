package com.student.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.student.dao.StudentDAO;
import com.student.dto.StudentDTO;


@WebServlet("/search.proc")
public class Search extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String name = request.getParameter("name");
		System.out.println("name : " + name);
		
		// 1.dao selectyByName에 name 값을 넘겨 -> 데이터 조회(ArrayList형으로 받음)
		// selectById 였을 경우 중복되지 않는 고유값이기 때문에 StudentDTO형로 받음
		// 2. list 서블릿으로 갔을때와 똑같은 작업을 통해 화면을 구성
		
		StudentDAO dao = new StudentDAO();
		try {
			ArrayList<StudentDTO> list = dao.selectByName(name);
			
			PrintWriter out = response.getWriter();
			out.write("<html>");
			out.write("<head>");
			out.write("</head>");
			out.write("<body>");
			out.write("<table border=1 align=center>");
			out.write("<tr><th colspan=6>Student</th></tr>");
			out.write("<tr><th>ID</th><th>NAME</th><th>KOR</th>"
					+ "<th>ENG</th><th>MATH</th><th>SUM</th></tr>");
			// 데이터 영역
			for(StudentDTO dto : list) {
				out.write("<tr><td>"+ dto.getId() +"</td>"
						+"<td>" + dto.getName() +"</td>"
						+"<td>" + dto.getKorean() +"</td>"
					    +"<td>" + dto.getEnglish() +"</td>"
					    +"<td>" + dto.getMath() + "</td>"
					    +"<td>" + (dto.getKorean()+ dto.getEnglish()+dto.getMath()) + "</td></tr>");
			}
			// 삭제 영역
			out.write("<tr><form action='/delete.proc' method='post'>"
					+ "<td colspan=6><input name='id' type='text' placeholder='삭제할 학생의 번호'>"
					+ "<button type='submit'>Delete</button>"
					+ "</td></form></tr>");
			// 수정 영역
			out.write("<tr><form action='/update.proc' method='post'><td colspan=6>");
			out.write("<input type='text' name='id' placeholder='수정할 학생의 번호'>"
					+ "<button type='submit'>Update</button><br>"
					+ "<input type='text' name='name' placeholder='수정할 학생의 이름'><br>"
					+ "<input type='text' name='kor' placeholder='수정할 학생의 국어'><br>"
					+ "<input type='text' name='eng' placeholder='수정할 학생의 영어'><br>"
					+ "<input type='text' name='math' placeholder='수정할 학생의 수학'>");
			out.write("</td></form></tr>");
			// 검색 영역
			out.write("<tr><form action='/search.proc' method='post'>"
					+ "<td colspan=6><input name='name' type='text' placeholder='검색할 학생의 이름'>"
					+ "<button type='submit'>Search</button>"
					+ "<button id='showAll' type='button'>ShowAll</button>"
					+ "</td></form></tr>");
			out.write("<tr><td colspan=6>"
					+ "<button type='button' id='btnBack'>Back</button>");
			out.write("</td>");
			out.write("</table>");
			out.write("<script>");
			out.write("document.getElementById('showAll').onclick = function(){location.href = '/list'};");
			out.write("document.getElementById('btnBack').onclick = function(){location.href = '/index.html'};");
			out.write("</script>");
			out.write("</body></html>");
	}catch(Exception e) {
		e.printStackTrace();
	}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

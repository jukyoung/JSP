package com.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dao.MemberDAO;
import com.board.dto.MemberDTO;
import com.board.utils.EncryptionUtils;

@WebServlet("*.mem")
public class MemberController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 클라이언트의 요청 url
		String uri = request.getRequestURI();
		System.out.println("요청 uri : " + uri);
		
		if(uri.equals("/toSignup.mem")) { // 회원가입 페이지 요청
			// /(슬래시) = webapp 이라 생각하기
			// 즉 /index.jsp 는 webapp 안에 있는 jsp
			response.sendRedirect("/member/signup.jsp");
		}else if(uri.equals("/idCheckPopup.mem")) { // 아이디 중복확인 팝업 페이지 요청
			response.sendRedirect("/member/popup.jsp");
		}else if(uri.equals("/checkId.mem")) { // 아이디 중복확인 요청
			String id = request.getParameter("id");
			System.out.println("id : " + id);
			
			MemberDAO dao = new MemberDAO();
			try {
				boolean rs = dao.checkId(id);
				System.out.println("rs : " + rs);
				if(rs) { // 사용할 수 있는 아이디라면
					request.setAttribute("rs", "ok");
				}else { // 중복된 아이디라면
					request.setAttribute("rs", "no");
				}
				//사용자가 입력했던 입력값이 input 창에 남아야하므로
				request.setAttribute("id", id);
				request.getRequestDispatcher("/member/popup.jsp").forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/signup.mem")) { // 회원가입 요청
			request.setCharacterEncoding("utf-8");
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String nickname = request.getParameter("nickname");
			String phone = request.getParameter("phone");
			String postCode = request.getParameter("postCode");
			String roadAddr = request.getParameter("roadAddr");
			String detailAddr = request.getParameter("detailAddr");
			String extraAddr = request.getParameter("extraAddr");
			System.out.println(id + " : " + pw + " : " + nickname + " : " + phone + " : "
					+ postCode + " : " + roadAddr + " : " + detailAddr + " : " + extraAddr);
			
			
			
			MemberDAO dao = new MemberDAO();
			try {
				pw = EncryptionUtils.getSHA512(pw);
				System.out.println("암호회된 데이터 : " + pw);
				
				int rs = dao.signup(new MemberDTO(id,pw,nickname,phone,postCode,roadAddr,detailAddr,extraAddr));
				if(rs > 0) {
					response.sendRedirect("/");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/loginProc.mem")) { // 로그인 요청
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			System.out.println(id + " : " + pw);
			
			MemberDAO dao = new MemberDAO();
			try {
				pw = EncryptionUtils.getSHA512(pw);
				System.out.println("암호회된 비번 : " + pw);
				
				MemberDTO dto = dao.isLoginOk(id, pw);
				if(dto != null) { // 로그인 성공
					System.out.println("로그인 성공");
					request.setAttribute("rs", true);
					// 세션에 dto 값 담기
					HttpSession session = request.getSession();
					session.setAttribute("loginSession", dto);
					
				}else { // 로그인 실패
					System.out.println("로그인 실패");
					request.setAttribute("rs", false);
				}
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				
				// 세션에 아이디값만 담을 경우
				/*
				boolean rs = dao.isLoginOk(id, pw);
				if(rs) { // 로그인 성공
					System.out.println("로그인 성공");
					request.setAttribute("rs", true);
					// session 저장소를 이용해 login 정보를 저장
					HttpSession session = request.getSession();
					// 어떤 정보를 저장할지는 개발자가 정하는 것
					// id 값을 담아줌
					// session은 별도로 forward 해주지 않아도 값의 공유(jsp에서 접근)가 가능함
					session.setAttribute("loginSession", id);
				}else { // 로그인 실패
					System.out.println("로그인 실패");
					request.setAttribute("rs", false);
				}
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				*/
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/logoutProc.mem")) { // 로그아웃 요청
			// 지금 요청을 보낸 클라이언트의 세션 얻어오기
			HttpSession session = request.getSession();
			// session.getAttribute("세션의 key") -> key에 담긴 값 가져오기;
			System.out.println(session.getAttribute("loginSession"));
			// 1. session 객체를 초기화 -> 로그아웃 할때 사용하는게 제일 깔끔함
			session.invalidate();
			response.sendRedirect("/index.jsp");
			
			// 2. session 저장소에서 loginSession 값만 삭제 
//			session.removeAttribute("loginSession");
//			response.sendRedirect("/index.jsp");
		}else if(uri.equals("/myPage.mem")) { // 마이페이지로 요청
			HttpSession session = request.getSession();
			session.getAttribute("loginSession");
			
//			String id = (String)session.getAttribute("loginSession");
			// MemberDTO 로 변환한다음에 가로로 묶어서 .getId() 가져오기
			String id = ((MemberDTO)session.getAttribute("loginSession")).getId();
			
			System.out.println("id : " + id);
			
			MemberDAO dao = new MemberDAO();
			try {
				MemberDTO dto = dao.selectById(id);
			
				
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("/member/myPage.jsp").forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(uri.equals("/update.mem")) { // 정보수정 요청
			request.setCharacterEncoding("utf-8");
			// 세션에서 아이디 값 꺼내야함 -> 그 회원의 정보값 요청이니까
			HttpSession session = request.getSession();
			String id = ((MemberDTO)session.getAttribute("loginSession")).getId();
			String nickname = request.getParameter("nickname");
			String phone = request.getParameter("phone");
			String postCode = request.getParameter("postCode");
			String roadAddr = request.getParameter("roadAddr");
			String detailAddr = request.getParameter("detailAddr");
			String extraAddr = request.getParameter("extraAddr");
			
			System.out.println(id + " : " + nickname + " : " + phone + " : "+ postCode + " : " + roadAddr + " : " + detailAddr + " : " + extraAddr);
			
			MemberDAO dao = new MemberDAO();
			try {
				int rs = dao.update(new MemberDTO(id, null, nickname, phone, postCode, roadAddr, detailAddr, extraAddr));
				if(rs > 0) {
					response.sendRedirect("/myPage.mem");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else if(uri.equals("/delete.mem")) { // 삭제
			HttpSession session = request.getSession();
			session.getAttribute("loginSession");
			
			String id = ((MemberDTO)session.getAttribute("loginSession")).getId();
			System.out.println("id : " + id);
			MemberDAO dao = new MemberDAO();
			
			try {
				int rs = dao.delete(id);
				if(rs > 0) {
					session.removeAttribute("loginSession");
					response.sendRedirect("/");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

}

package com.board.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("*.file")
public class fileController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		System.out.println("요청 uri : " + uri);
		
		if(uri.equals("/upload1.file")) {
			System.out.println("파일 업로드 요청");
//			String file = request.getParameter("file");
//			System.out.println("file : " + file);
			/* file request를 받았을때에는 HttpServletRequest로 데이터를 받아 줄 수 없음
			 * 
			 * DB에 파일을 저장하지 않을 것 -> DB가 너무 무거워짐 -> 서버의 컴퓨터(물리적 경로)에 저장
			 * -> tomcat이 실행되고 있는 root 경로 -> 이 안에 files 폴더 만들어서 파일을 저장
			 * DB -> 실제 서버의 컴퓨터에 저장된 파일의 경로값만 저장
			 * 
			 * controller는 클라이언트에게 요청받은 파일이 있다면 DB에 저장된 경로값을 꺼내서
			 * InputStream을 이용해 해당 파일을 프로그램으로 불러들여오고, 그 후 클라이언트에게 응답
			 * */
			// 1. 서버의 root 경로
			String realPath = request.getServletContext().getRealPath("");
			System.out.println("realPath : " + realPath);
			
			// 2. 서버의 루트에 실제 파일이 저장될 경로값을 만들어주기
			String filePath = request.getServletContext().getRealPath("files");
			System.out.println("filePath : " + filePath);
			
			// 3. File을 이용해 실제 위의 경로값이 존재하는지 체크 -> 없다면 폴더 생성
			File dir = new File(filePath);
			System.out.println( dir.exists() ); // -> 경로값에 해당하는 파일이나 폴더가 존재하면 true 값 반환
			if(!dir.exists()) {
				System.out.println("폴더 생성!");
				dir.mkdirs(); // -> 폴더를 생성하라 라는 메서드 사용
			}
			
			// 4. 업로드할 파일의 최대크기를 얼마로 지정해줄지
			// 1MB = 1024 * 1024 * 1
			// 10MB = 1024 * 1024 * 10 -> 뒤에만 숫자만 변경하기
			int maxSize = 1024 * 1024 * 10;
			
			// MultipartRequest 객체 생성하는 작업 -> 바로 파일의 업로드가 이뤄짐.
			// MultipartRequest(request, 파일 저장경로, 파일 최대 크기, 인코딩, 파일 이름 중복 처리 방지)
			try {
				MultipartRequest multi = new MultipartRequest(request, filePath, maxSize, "utf-8", new DefaultFileRenamePolicy());
				// 업로드된 파일의 원본파일명 input 태그로 넘겼던 이름값
				// 파일 다운로드 기능을 구현할때 만약 첨부파일 목록을 띄워준다면
				// 처음 업로드됐던 파일의 원본명을 띄워주기 위한 용도로 originalfilename이 필요
				String ori_name = multi.getOriginalFileName("file");
				// 실제 서버 경로에 업로드된 파일명
				// 만약 파일을 다시 다운로드 하는 작업을 할때
				// 요청된 파일을 실제 서버 경로에서 가져오려면 파일의 경로값의 역할을 해주는 filesystemname 이 필요
				String sys_name = multi.getFilesystemName("file");
				
				System.out.println("ori_name : " + ori_name);
				System.out.println("sys_name : " + sys_name);
			}catch(Exception e) {
				e.printStackTrace();
			}

		}else if(uri.equals("/upload2.file")) {
			String filePath = request.getServletContext().getRealPath("files");
			System.out.println("filePath : " + filePath);
			
			File dir = new File(filePath);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			
			int maxSize = 1024 * 1024 * 10;
			try {
				MultipartRequest multi = new MultipartRequest(request, filePath, maxSize, "utf-8", new DefaultFileRenamePolicy());
				
				String ori_name1 = multi.getOriginalFileName("file1");
				String sys_name1 = multi.getFilesystemName("file1");
				System.out.println(ori_name1 + " : " + sys_name1);
				
				String ori_name2 = multi.getOriginalFileName("file2");
				String sys_name2 = multi.getFilesystemName("file2");
				System.out.println(ori_name2 + " : " + sys_name2);
			}catch(Exception e) {
				e.printStackTrace();
			}

		}else if(uri.equals("/upload3.file")) {
			// form 인코딩 타입이 multipart/form-data 일때
			// 보내주는 텍스트 데이터를 일반 request로 받아줄 수 없음
//			String title = request.getParameter("title");
//			String message = request.getParameter("message"); -> null 값 나옴 왜 ? form 태그에서 enctype 때문
//			System.out.println(title + " : " + message); 
			
			String filePath = request.getServletContext().getRealPath("files");
			System.out.println("filePath : " + filePath);
			
			File dir = new File(filePath);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			
			int maxSize = 1024 * 1024 * 10;
			try {
				MultipartRequest multi = new MultipartRequest(request, filePath, maxSize, "utf-8", new DefaultFileRenamePolicy());
				
				String ori_name = multi.getOriginalFileName("file1");
				String sys_name = multi.getFilesystemName("file1");
				System.out.println("파일명: " + ori_name + " : " + sys_name);
				
				String title = multi.getParameter("title");
				String message = multi.getParameter("message");
				System.out.println(title + " : " + message);
			}catch(Exception e) {
				e.printStackTrace();
			}

		}
	}
}

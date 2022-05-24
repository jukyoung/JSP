<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 업로드</title>
<style>
	form{border: 1px solid black;}
</style>
</head>
<body>
	<!-- 파일 업로드
		1. input type="file" name
		2. 파일을 업로드 할때는 반드시 post 방식으로 전송
		3. form 태그에 enctype="multipart/form-data"
	 -->
	 <form action="/upload1.file" method="post" enctype="multipart/form-data">
	 	<h2>파일 업로드1</h2>
	 	<input type="file" name="file">
	 	<button type="submit">업로드</button>
	 </form>
	 
	 <form action="/upload2.file" method="post" enctype="multipart/form-data">
	 	<h2>파일 업로드2</h2>
	 	<!-- cos.jar 라이브러리를 이용하게 되면 동일한 이름으로 여러개의 파일을
	 		업로드가 가능은 하지만 하나의 파일명밖에 얻어오지 못하기 때문에
	 		여러개의 파일을 업로드할거라면 모두 name 값을 다르게 설정해 준다.
	 	 -->
	 	<input type="file" name="file1">
	 	<input type="file" name="file2">
	 	<button type="submit">업로드</button>
	 </form>
	 
	  <form action="/upload3.file" method="post" enctype="multipart/form-data">
	 	<h2>파일 업로드3</h2>	 	
	 	<input type="text" name="title">
	 	<input type="text" name="message">
	 	<input type="file" name="file1">
	 	<button type="submit">업로드</button>
	 </form>
</body>
</html>
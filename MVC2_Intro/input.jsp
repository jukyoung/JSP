<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Input</title>
</head>
<body>
	<!-- 
		get : 서버에 데이터를 전송해 서버 DB의 데이터에 영향을 주지 않는 경우(조회 / 간단한 데이터)
		post : 서버에 데이터를 전송해 서버 DB의 데이터에 영향을 주는 경우(삽입, 수정, 삭제 / 보안이 필요한 데이터 / 길이가 긴 데이터)
	 -->
	<form action="/insert" method="post">
		<input type="text" name="nickname" placeholder="닉네임 입력">
		<input type="text" name="message" placeholder="메세지 입력">
		<button type="submit">전송</button>
	</form>
</body>
</html>
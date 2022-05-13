<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body>
    <button type="button" id="addBtn">메뉴등록</button>
    <button type="button" id="listBtn">메뉴확인</button>
   <script>
		document.getElementById("addBtn").onclick = function(){
			location.href = "/toAdd.cafe";
		}
		document.getElementById("listBtn").onclick = function(){
			location.href = "toList.cafe";
		}
   </script>
</body>
</html>

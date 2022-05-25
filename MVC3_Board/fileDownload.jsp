<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 다운로드</title>
</head>
<body>
	<button type="button" id="download">파일 다운로드</button>
	<script>
		document.getElementById("download").onclick = function(){
			location.href="/download.file";
		}
	</script>
</body>
</html>
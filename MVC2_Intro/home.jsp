<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
   <!-- html문서에서 해주던 작업을 모두 jsp파일에서도 해줄 수 있음.  -->
   <button type="button" id="toInput">toInput</button>
   <button type="button" id="toOutput">toOutput</button>
   
   <script>
      document.getElementById("toInput").onclick = function(){
    	  location.href = "/toInput";
      }
	  document.getElementById("toOutput").onclick = function(){
    	  location.href = "/toOutput";
      }
   </script>	
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Output</title>
</head>
<body>
<table border= "1px">
  <tr>
   	<th>Seq</th>
   	<th>Nickname</th>
   	<th>Message</th>
  </tr>
   <c:forEach items="${list}" var="dto">
		<tr>
		 <td>${dto.getSeq()}</td>
		 <td>${dto.getNickname()}</td>
		 <td>${dto.getMessage()}</td>
		</tr>
	</c:forEach>
</table>
	
</body>
</html>
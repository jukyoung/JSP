<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<title>메뉴 확인</title>
<style>
	*{border: 1px solid black}
   table {width: 400px; height: 200px; text-align: center}
</style>
</head>
<body>
	<table class="tbl-cafe">
		<tr>
			<th>번호</th>
			<th>이름</th>
			<th>가격</th>
			<th>수정</th>
			<th>삭제</th>
		</tr>
		<c:choose>
			<c:when test="${empty list}">
				<tr>
					<td colspan=5>등록된 메뉴가 없습니다.</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list}" var="dto">
					<tr>
						<td>${dto.getProduct_id()}</td>
						<td>${dto.getProduct_name()}</td>
						<td>${dto.getProduct_price()}</td>
						<td>
						<button type="button" class="updateBtn" value="${dto.getProduct_id()}">수정</button>
						</td>
						<td>
						<button type="button" class="deleteBtn" value="${dto.getProduct_id()}">삭제</button>
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		<tr>
			<td colspan=3><input type="text" placeholder="메뉴 이름 검색"></td>
			<td><button type="button">검색</button></td>
			<td><button type="button">전체보기</button></td>
		</tr>
	</table>
	<script>
		// 수정 버튼을 눌렀을때
		$(".tbl-cafe").on("click", ".updateBtn", function(){
			let product_id = $(this).val();
			location.href = "/update.cafe?product_id=" + product_id;
		});
		// 삭제 버튼을 눌렀을때
	   $(".tbl-cafe").on("click", ".deleteBtn", function(){
		  let answer = confirm("정말 삭제하시겠습니까?");
		  
		  if(answer){// 확인 버튼을 눌렀다면
		  let product_id = $(this).val();
		  let deleteForm = $("<form id='deleteForm'></form>");
		  deleteForm.attr("action", "/delete.cafe");
		  deleteForm.attr("method", "post");
		  
		  deleteForm.append($("<input>", {type: "text", name: "product_id", val: product_id}))
		  $(deleteForm).appendTo("body").css("display", "none");
		  deleteForm.submit();
		  }
	   });
	</script>
</body>
</html>
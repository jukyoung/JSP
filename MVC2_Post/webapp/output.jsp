<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<script
  src="https://code.jquery.com/jquery-3.6.0.js"
  integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
  crossorigin="anonymous"></script>
<title>output</title>
<style>
	table {width: 400px; height: 200px; text-align: center}
</style>
</head>
<body>
  <table class="table table-striped tbl-post">
	<tr>
	    <th>Seq</th>
		<th>Id</th>
		<th>Post</th>
		<th>Buttons</th>
	</tr>
	<%-- if문 작성 만약 list 가 null 값이라면
	     test -> 조건 eq -> equal 같다 ne -> 같지 않다--%>
	<%--<c:if test="${list == null}">
	    <c:if test="${list eq null}">
	    리스트가 비어있는지 확인
	    <c:if test="${list.size() == 0}">
	    <c:if test="${empty list}">
	    리스트가 비어있지 않다면
	    <c:if test="${not empty list})">
	    <c:if test="${empty list}">
	    <tr>
	        <td colspan=3>보여줄 게시글이 없습니다.</td>
	    </tr>
	</c:if>
	<c:if test="${not empty list})">
	<c:forEach items="${list}" var="dto">
		<tr>
		    <td>${dto.getSeq()}</td>
			<td>${dto.getId()}</td>
			<td>${dto.getPost()}</td>
		</tr>
	</c:forEach>
	</c:if>--%>
	<%-- <c:choose>
	         <c:when test=''> == if문과 같은 역할
	         
	         </c:when>
	         <c:when test=''> == 여러번 쓰면 else if
	         
	         </c:when>
	         <c:otherwise> == else와 같은 역할
	         
	         </c:otherwise>
	       </c:choose> --%>
	  <c:choose>
	   	<c:when test="${empty list}">
	   	 <tr>
	        <td colspan=4>보여줄 게시글이 없습니다.</td>
	    </tr>
	   	</c:when>
	   	<c:otherwise> 
	       <c:forEach items="${list}" var="dto">
		  <tr>
		    <td>${dto.getSeq()}</td>
			<td>${dto.getId()}</td>
			<td>${dto.getPost()}</td>
			<td>
				<button type="button" class="btn btn-outline-danger deleteBtn" value="${dto.getSeq()}">삭제</button>
				<button type="button" class="btn btn-outline-primary modifyBtn" value="${dto.getSeq()}">수정</button>
			</td>
		   </tr>
	        </c:forEach>
	     </c:otherwise>
	  </c:choose>
	 <td colspan=4>
		<button type='button' id='btnBack' class="btn btn-outline-secondary">Back</button>
	 </td>
</table>
<script>
  // 수정 버튼을 눌렀을 때
  $(".tbl-post").on("click", ".modifyBtn", function(){
	  let seq = $(this).val();
	  //console.log(seq);
	  // seq 번호를 /modify url로 get 방식 전송(단순 조회니까 get방식)
	  // "url?key값=" + 값
	  location.href = "/modify.post?seq=" + seq;
	  
  });
  // 삭제 버튼을 눌렀을 때(jquery)
  $(".tbl-post").on("click", ".deleteBtn", function(){
	  // 버튼이 눌렸을때 그 행에 대한 seq(버튼 value) 값 가져오기
	  let seq = $(this).val();
	  // form 동적 생성
	  let deleteForm = $("<form id='deleteForm'></form>");
	  // form 속성 - action, method  / attr() : 속성값을 부여하는 메서드 
	  deleteForm.attr("action", "/delete.post");
	  deleteForm.attr("method", "post");
	  
	  /* <form action ="/delete" method="post">
	     <input type="text" name='seq' value=seq>
	   </form> 이런 구성을 만들것*/
	   
	  // 데이터를 전송하기 위해 form에 input 태그를 추가 <input>으로 해도 됨 / 자바스크립트 객체{}를 넘겨줌
	  deleteForm.append($("<input/>", {type: "text", name: "seq", value: seq}));
	  // body 요소에 form 요소를 실제로 추가 + 깜빡거리는 효과 없애기
	  $(deleteForm).appendTo("body").css("display", "none");
	  // 스크립트 영역에서 만든 deleteForm 을 즉시 submit 해버리기
	  deleteForm.submit();
  });
  
  document.getElementById('btnBack').onclick = function(){
	location.href = '/index';
	}
</script>	
</body>
</html>
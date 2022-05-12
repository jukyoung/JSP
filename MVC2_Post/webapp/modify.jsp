<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>수정</title>
<style>
   textarea{ width : 200px; height : 200px;}
</style>
</head>
<body>
<form id="modifyForm" action="/modifyProc.post" method="post">
   <div class="d-none"> <%-- 안보이도록 숨기기 부트스트랩 안한경우 스타일영역에서 display: none; --%>
      <input type="text" name="seq" value="${dto.getSeq()}">
   </div>
  <div>
      <p>ID : </p>
      <input type="text" name="id" class="form-control" value="${dto.getId()}">
  </div>
  <div>
      <p>POST : </p>
      <textarea name="post" class="form-control">${dto.getPost()}</textarea>
  </div>
  <div>
      <button type="button" id="completeBtn" class="btn btn-outline-primary">수정완료</button>
  </div>
  </form>
  <script>
  	//수정 완료 버튼을 클릭하면 "정말 수정하시겠습니까?" 알림창을 띄워주고 만약 "확인"을 누르면 서버로 form 제출
  	// "취소"를 누르면 form 제출 X
  	$("#completeBtn").on("click", function(){
  	 let answer = confirm("정말 수정하시겠습니까?");
  	 //console.log(answer); -> 확인 누르면 true 취소면 false 값 나옴
  	 if(answer){// 확인 버튼을 눌렀다면
  		 $("#modifyForm").submit();
  	 }
  	})
  </script>
</body>
</html>
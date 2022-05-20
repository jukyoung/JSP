<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<title>글쓰기</title>
<style>
.title {
	height: 80px;
	margin: 5px;
	border: 1px solid lightgrey;
}

input {
	width: 100%;
	height: 90%;
	border: 1px solid lightgrey;
	margin-top: 3px;
}

.content {
	height: 200px;
	margin: 5px;
	border: 1px solid lightgrey;
}

textarea {
	width: 100%;
	height: 95%;
	border: 1px solid lightgrey;
	margin-top: 3px;
}

.btn {
	margin: 5px;
}
</style>
</head>
<body>
<form id="wirteForm" action="/writeProc.bo" method="post">
	<div class="container">
		<div class="row">
			<div class="col d-flex justify-content-center">
				<h2>글쓰기</h2>
			</div>
		</div>
		<div class="row title">
			<div class="col-4 d-flex justify-content-center">
				<p>제목</p>
			</div>
			<div class="col-8">
				<input type="text" id="title" name="title" class="form-control">
			</div>
		</div>
		<div class="row content">
			<div class="col-4 d-flex justify-content-center">
				<p>내용</p>
			</div>
			<div class="col-8">
				<textarea name="content" id="content" class="form-control"></textarea>
			</div>
		</div>
		<div class="row">
			<div class="col btn d-flex justify-content-center">
				<button type="button" id="backBtn" class="btn btn-secondary">뒤로가기</button>
				<button type="button" id="saveBtn" class="btn btn-primary">저장</button>
			</div>
		</div>
	</div>
	</form>
<script>
	$("#saveBtn").on("click", function(){ // 저장 버튼을 눌렀을때
		// 만약 재목을 입력하지 않았다면 title 에 "제목없음" 이라는 타이틀 값을 넣어줌.
		if($("#title").val() === ""){
			$("#title").val("제목없음");
		}
		// 내용이 비어있으면 내용을 입력하세요 띄우기
		if($("#content").val() === ""){
			alert("내용을 입력하세요.");
			$("#content").focus();
			return;
		}
		$("#wirteForm").submit();
	})
	$("#backBtn").on("click", function(){ // 뒤로가기 버튼
		location.href = "/board.bo";
	})
</script>
</body>
</html>
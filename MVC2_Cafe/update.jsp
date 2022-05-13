<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<title>메뉴 수정</title>
</head>
<body>
	<div class="container">
		<form id="modifyForm" action="/modify.cafe" method="post">
		<div class="d-none">
		 <input type="text" name="product_id" value="${dto.getProduct_id()}">
		</div>
			<div class="row">
				메뉴 이름
				<div class="col">
					<input type="text" name="product_name" value="${dto.getProduct_name()}">
				</div>
			</div>
			<div class="row">
				메뉴 가격
				<div class="col">
					<input type="text" name="product_price" value="${dto.getProduct_price()}">
				</div>
			</div>
			<div class="row">
				<div class="col">
					<button type="button" id="backBtn">뒤로가기</button>
					<button type="button" id="completeBtn">수정완료</button>
				</div>
			</div>
		</form>
	</div>
	<script>
	 $("#completeBtn").on("click", function(){
		 let answer = confirm("정말 수정하시겠습니까?");
		 if(answer){
			 $("#modifyForm").submit();
		 }
	 })
	  document.getElementById("backBtn").onclick = function(){
		  location.href = "/index.jsp";
	  }
	</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메뉴 등록</title>
</head>
<body>
	<div class="container">
		<form action="/addMenu.cafe" method="post">
			<div class="row">
				메뉴 이름
				<div class="col">
					<input type="text" name="product_name">
				</div>
			</div>
			<div class="row">
				메뉴 가격
				<div class="col">
					<input type="text" name="product_price">
				</div>
			</div>
			<div class="row">
				<div class="col">
					<button type="button" id="backBtn">뒤로가기</button>
					<button type="submit">등록</button>
				</div>
			</div>
		</form>
	</div>
	<script>
	  document.getElementById("backBtn").onclick = function(){
		  location.href = "/index.jsp";
	  }
	</script>
</body>
</html>
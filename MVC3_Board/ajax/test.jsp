<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.js"
	integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
	crossorigin="anonymous"></script>
<title>테스트</title>
</head>
<body>
	<div class="row">
		<div class="col btns">
			<button type="button" id="logoutBtn" class="btn btn-danger">로그아웃</button>
			<button type="button" id="toInput" class="btn btn-warning">요청보내기</button>
		</div>
	</div>
	<form id="testForm">
		<input type="text" name="id">
		<input type="text" name="nickname">
		<button type="button" id="btnSubmit">제출</button>
	</form>
	<button type="button" id="getData">데이터 가져오기</button>
	<button type="button" id="getDTO">DTO 가져오기</button>
	<button type="button" id="getList">List 가져오기</button>
<script>
	$("#getList").on("click", function(){
		$.ajax({
			url: "/getList.test"
			, type: "get"
			, dataType: "json"
			, success: function(data){
				console.log(data); // 객체배열 인 상태
				console.log(data[1].title);
				for(let dto of data){
					console.log(dto.seq_board+ " : " + dto.title + " : " + dto.content
							+ " : " + dto.writer_nickname + " : " + dto.writer_id
							+ " : " + dto.view_count + " : " + dto.written_date);
				}
				
			}
			, error: function(e){
				console.log(e);
			}
		})
	})
	$("#getDTO").on("click", function(){
		$.ajax({
			url: "/getDTO.test"
			, type: "get"
			, dataType: "json"
			// 서버에서 json형식의 데이터를 보내줬을때
			// success 안쪽에서 js 객체처럼 다루고 싶으면 dataType : "json" 으로 잡아줌.
			, success: function(data){
				console.log(data);
				console.log(data.seq_board);
				console.log(data.title);
				console.log(data.writer_id);
				console.log(data.writer_nickname);
				console.log(data.view_count);
				console.log(data.written_date);
			}
			, error: function(e){
				console.log(e);
			}
		})
	})
	$("#getData").on("click", function(){ // 요청에 따라 텍스트 데이터 가져오기
		$.ajax({
			url: "/getData.test?msg=goodbye"
			, type: "get"
			, dataType: "text" //받아올 데이터의 타입
			, success: function(data){ // 매개변수 안에 데이터 값이 담김
				console.log(data);
			}
			, error: function(e){
				console.log(e);
			}
		})
	})
	
	
	$("#btnSubmit").on("click", function(){
		// 비동기 통신으로 form 을 보낼때 설정값 안넣어줘도 됨(ajax할때 넣어주니까)
		// post 방식으로 비동기통신 데이터 전송
		// form 태그 안의 데이터를 객체 형식으로 변환시켜주는 함수
		let data = $("#testForm").serialize();
		console.log(data);
		
		$.ajax({
			url: "/sendForm.test"
			, type: "post"
			, data: data
			, success: function(){}
			, error: function(e){
				console.log(e);
			}
		})
	
	})
	$("#toInput").on("click", function(){
		/*
			ajax : 비동기 통신할때 사용하는 라이브러리
			(전체 페이지의 리로드 없이 부분적인 페이지만 서버와 통신하여 리로드 할 수 있는 것)
		*/
		
		// 1. 요청 보내기 
		// 2. 데이터 보내기(get 방식으로 보내면 주소 뒤에 바로 보내기)
		$.ajax({
			url: "/sendMsg.test?msg=HelloWorld" // 요청 보내는 주소
			, type : "get"  // 요청 타입
			//, data:
			, success: function(){ // 요청이 성공하면 함수 실행
				
			}
			, error: function(e){ // 실패했을경우 어떤 에러가 났는지 매개변수 e 를 통해 알기
				console.log(e);
			}
		});
	})
</script>
</body>
</html>

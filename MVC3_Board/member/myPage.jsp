<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<title>마이페이지</title>
<style>
.join {
	margin: auto;
}

h3 {
	text-align: center;
}

.row {
	margin: 10px;
}

.d-flex>button {
	margin: 10px;
}
</style>
</head>
<body>
	<div class="container">
	<form id="modifyForm" action="/update.mem" method="post">
		<h3>정보수정</h3>
		<div class="row">
			<p>아이디</p>
			<div class="col-12 md-4">
				<input type="text" name="id" class="form-control" value="${dto.getId()}" id="id" readonly>
			</div>
		</div>
		<div class="row">
			<p>닉네임</p>
			<div class="col-12 md-4">
				<input type="text" name="nickname" value="${dto.getNickname()}" class="form-control" id="nickname" readonly>
			</div>
		</div>
		<div class="row">
			<p>휴대폰 번호</p>
			<div class="col">
				<select class="form-select" id="phone1">
					<option value="010">010</option>
					<option value="011">011</option>
					<option value="016">016</option>
					<option value="017">017</option>
					<option value="018">018</option>
					<option value="019">019</option>
				</select>
			</div>
			<div class="col">
				<input type="text" maxlength="4" id="phone2" class="form-control" readonly>
			</div>
			<div class="col">
				<input type="text" maxlength="4" id="phone3" class="form-control" readonly>
			</div>
			<div class="col d-none">
				<input type="text" id="phone" name="phone">
			</div>
		</div>
		<div class="row">
			<div class="col">
				<input type="text" value="${dto.getPostcode()}" name="postCode" id="postCode" class="form-control" placeholder="우편번호" readonly>
			</div>
			<div class="d-grid gap-2 col-6 mx-auto">
				<button type="button" class="btn btn-info" id="postCodeFind" disabled>우편번호 찾기</button>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<input type="text" value="${dto.getRoadAddr()}" name="roadAddr" id="roadAddr" class="form-control" placeholder="도로명주소" readonly>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<input type="text" value="${dto.getDetailAddr()}" name="detailAddr"  id="detailAddr" class="form-control" placeholder="상세주소" readonly>
			</div>
			<div class="col">
				<input type="text" value="${dto.getExtraAddr()}" name="extraAddr" id="extraAddr" class="form-control" placeholder="읍면동" readonly>
			</div>
		</div>
		<div class="row btn-before">
			<div class="col d-flex justify-content-center">
				<button type="button" id="backBtn" class="btn btn-outline-warning">뒤로가기</button>
				<button type="button" class="btn btn-outline-success" id="updateBtn">수정</button>
			</div>
		</div>
		<div class="row btn-after d-none">
			<div class="col d-flex justify-content-center">
				<button type="button" id="cancleBtn" class="btn btn-outline-warning">취소</button>
				<button type="button" class="btn btn-outline-success" id="completeBtn">완료</button>
			</div>
		</div>
		</form>
	</div>
	<script>
	// 휴대폰번호 값 쪼개서 넣기(셋팅)
	let phone = "${dto.getPhone()}"
	let phone1 = phone.slice(0, 3);
	let phone2 = phone.slice(3, 7);
	let phone3 = phone.slice(7);
	
	// 셀렉트 박스에 default selected 추가
	$("#phone1").val(phone1).prop("selected", true);
	$("#phone2").val(phone2);
	$("#phone3").val(phone3);
	
	// 수정버튼을 눌렀을때
	$("#updateBtn").on("click", function(){
		$("input").not("#id").attr("readonly", false); // 아이디를 제외한 input readonly 제거
		$("#postCodeFind").attr("disabled", false); // 우편번호찾기 버튼에 걸린 disabled 속성 제거
		$(".btn-before").css("display", "none"); // 기존의 버튼들 감춰주기
		// 부트스트랩을 사용하면 display 속성 값이 기본적으로 flex 라고 걸려있음 (부트스트랩 사용안하면 block 속성 줘서 보이게 하기)
		//$(".btn-after").css("display", "flex"); -> 클래스명과 충돌되서 적용이 안됨
		$(".btn-after").removeClass("d-none"); // 취소, 완료버튼 보여주기
	});
	// 수정화면에서 취소 버튼을 눌렀을때
	$("#cancleBtn").on("click", function(){
		location.href = "/myPage.mem";
	});
	// 뒤로가기 버튼을 눌렀을때 index로 돌아가기
	$("#backBtn").on("click", function(){
		location.href = "/";
	});
	// 수정완료 버튼을 눌렀을때
	$("#completeBtn").on("click", function(){
		let regexNickname = /^[a-zA-z0-9ㄱ-흫]{4,8}$/; // 닉네임 정규식
		let regexPhone = /^[0-9]{11}$/;
		let phone = $("#phone1 option:selected").val() + $("#phone2").val() + $("#phone3").val();
		$("#phone").val(phone);
		
		if(!regexNickname.test($("#nickname").val())){
			alert("형식에 맞지 않는 닉네임 입니다.");
			return;
		}else if(!regexPhone.test(phone)){ // 숫자 데이터에 대한 별도의 형변환이 필요없음
			alert("형식에 맞지않는 휴대폰번호 입니다.");
			return;
		}else if($("#postCode").val() === "" || $("#roadAddr").val() === ""){
			alert("주소를 입력해 주세요.");
			return;
		}
		$("#modifyForm").submit();
	})
		//본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    $("#postCodeFind").on("click", function(){
    	new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('postCode').value = data.zonecode;
                document.getElementById("roadAddr").value = roadAddr;
                
                
                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("extraAddr").value = extraRoadAddr;
                } else {
                    document.getElementById("extraAddr").value = '';
                } 
            }
        }).open();
    })
	</script>
</body>
</html>
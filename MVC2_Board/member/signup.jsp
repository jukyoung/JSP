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
<title>회원가입</title>
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
	<div class="join">
	<form id="signupForm" action="/signup.mem" method="post">
		<h3>회원가입</h3>
		<div class="row">
			<p>아이디</p>
			<div class="col">
				<input type="text" name="id" class="form-control" id="id" readonly>
			</div>
			<div class="d-grid gap-2 col-4 mx-auto">
				<button type="button" name="checkIdBtn" id="checkIdBtn" class="btn btn-secondary">아이디 확인</button>
			</div>
		</div>
		<div class="row">
			<p>비밀번호</p>
			<div class="col">
				<input type="password" name="pw" class="form-control" id="pw">
			</div>
		</div>
		<div class="row">
			<p>비밀번호 확인</p>
			<div class="col">
				<input type="password" class="form-control" id="pwCheck">
			</div>
		</div>
		<div class="row">
			<p>닉네임</p>
			<div class="col">
				<input type="text" name="nickname" class="form-control" id="nickname">
			</div>
		</div>
		<div class="row">
			<p>휴대폰 번호</p>
			<div class="col">
				<select class="form-select" id="phone1">
					<option value="010" selected>010</option>
					<option value="011">011</option>
					<option value="016">016</option>
					<option value="017">017</option>
					<option value="018">018</option>
					<option value="019">019</option>
				</select>
			</div>
			<div class="col">
				<input type="text" maxlength="4" id="phone2" class="form-control">
			</div>
			<div class="col">
				<input type="text" maxlength="4" id="phone3" class="form-control">
			</div>
			<div class="col d-none">
				<input type="text" id="phone" name="phone">
			</div>
		</div>
		<div class="row">
			<div class="col">
				<input type="text" name="postCode" id="postCode" class="form-control" placeholder="우편번호">
			</div>
			<div class="d-grid gap-2 col-6 mx-auto">
				<button type="button" class="btn btn-info" id="postCodeFind">우편번호 찾기</button>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<input type="text" name="roadAddr" id="roadAddr" class="form-control" placeholder="도로명주소">
			</div>
		</div>
		<div class="row">
			<div class="col">
				<input type="text" name="detailAddr"  id="detailAddr" class="form-control" placeholder="상세주소">
			</div>
			<div class="col">
				<input type="text" name="extraAddr" id="extraAddr" class="form-control" placeholder="읍면동">
			</div>
		</div>
		<div class="row">
			<div class="col d-flex justify-content-center">
				<button type="button" class="btn btn-outline-warning">취소</button>
				<button type="button" class="btn btn-outline-success" id="submitBtn">가입</button>
			</div>
		</div>
		</form>
	</div>
	<script>
		// 아이디 확인 버튼 누르면 팝업창 띄우기
		document.getElementById("checkIdBtn").onclick = function(){
			// 팝업창을 띄우기 위해 필요한 3가지 값
			// 1. jsp 경로값(팝업창을 꾸며주는 jsp 별도로 필요)
			// 2. 팝업창의 이름값
			// 3. 팝업창의 크기, 위치
			let url = "/idCheckPopup.mem";
			let name = "아이디 중복검사";
			let option = "width=600, height=300, left=500, top=300";
			window.open(url, name, option);
		}
		// 가입 버튼을 눌렀을때 유효성 검사 후 form 제출
		$("#submitBtn").on("click", function(){
			let regexPw = /^[a-zA-z0-9~!@#$%^&*]{6,20}$/; //비밀번호 정규식
			let regexNickname = /^[a-zA-z0-9ㄱ-흫]{4,8}$/; // 닉네임 정규식
			let regexPhone = /^[0-9]{11}$/;
			
			// phone번호 합쳐주는 작업
			// select박스에서 선택된 값을 가져오는 방법
			//console.log($("#phone1 option:selected").val());
			let phone = $("#phone1 option:selected").val() + $("#phone2").val() + $("#phone3").val();
			$("#phone").val(phone);
			//console.log(phone);
			
			if($("#id").val() === ""){
				alert("아이디를 입력해 주세요.");
				return;
			}else if(!regexPw.test($("#pw").val())){
				alert("형식에 맞지 않는 비밀번호 입니다.");
				return;
			}else if($("#pw").val() !== $("#pwCheck").val()){
				alert("비밀번호와 비밀번호 확인창에 있는 값이 일치하지 않습니다.")
				return;
			}else if(!regexNickname.test($("#nickname").val())){
				alert("형식에 맞지 않는 닉네임 입니다.");
				return;
			}else if(!regexPhone.test(phone)){ // 숫자 데이터에 대한 별도의 형변환이 필요없음
				alert("형식에 맞지않는 휴대폰번호 입니다.");
				return;
			}else if($("#postCode").val() === "" || $("#roadAddr").val() === ""){
				alert("주소를 입력해 주세요.");
				return;
			}
			// form 제출
			$("#signupForm").submit();
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

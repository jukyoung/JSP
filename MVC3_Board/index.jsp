<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script src="https://code.jquery.com/jquery-3.6.0.js"
	integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
	crossorigin="anonymous"></script>

<title>Home</title>
<style>
.loginBox {
	margin: auto;
	width: 500px;
	text-align: center;
}

.col  input {
	margin: 5px;
}

button {
	margin: 10px;
}
 #welcome h2{ 
 border-top: 2px black solid;
  margin : 10px;
 }
</style>
</head>
<body>
	<c:choose>
		<c:when test="${not empty loginSession}"> <%-- 로그인 성공 : 세션이 만들어짐 --%>
		<div class="container">
			<div class="row" id="welcome">
				<div class="col">
				<%-- 닉네임 값 꺼내오기 --%>
				<%-- jsp 에서 멤버필드명 가지고 사용하는거 가능 -> loginSession.nickname 가능 --%>
					<h2>${loginSession.getNickname()} 님 환영합니다.</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-3">
					<button type="button" id="btnLogout" class="btn btn-dark">로그아웃</button>
				</div>
				<div class="col-3">
					<button type="button" id="btnMypage" class="btn btn-warning">마이페이지</button>
				</div>
				<div class="col-3">
					<button type="button" id="btnBoard" class="btn btn-success">게시판</button>
				</div>
				<div class="col-3">
					<button type="button" id="btnDelete" class="btn btn-danger">회원탈퇴</button>
				</div>
			</div>
		</div>
		<script>
			$("#btnLogout").on("click", function(){ // 로그아웃 요청
				location.href="/logoutProc.mem" //요청값만 보내는것
			})
			$("#btnMypage").on("click", function(){ // 마이 페이지 요청
				location.href="/myPage.mem"
			})
			$("#btnDelete").on("click", function(){ // 회원탈퇴 요청
				let answer = confirm("정말 탈퇴하시겠습니까?");
				if(answer){
					location.href = "/delete.mem";
				}	
			})
			$("#btnBoard").on("click", function(){ // 게시판 요청
				location.href = "/board.bo?curPage=1"
			})
		</script>
  	</c:when>
		<c:otherwise> <%-- 로그인 실패  --%>
			<c:if test="${rs eq false}"> 
				<script>
					alert("로그인 실패");
				</script>
			</c:if>
			
			<form id="loginForm" action="/loginProc.mem" method="post">
				<div class="loginBox">
					<div class="row">
						<div class="col">
							<h3>Board</h3>
						</div>
					</div>
					<div class="row">
						<div class="col d-flex justify-content-center">
							<input type="text" id="id" name="id" class="form-control"
								placeholder="id 입력">
						</div>
					</div>
					<div class="row">
						<div class="col d-flex justify-content-center">
							<input type="password" id="pw" name="pw" class="form-control"
								placeholder="pw 입력">
						</div>
					</div>
					<div class="row">
						<div class="col">
							<input type="checkbox" class="form-check-input" id="rememberId">아이디 저장
						</div>
					</div>
					<div class="row">
						<div class="col">
							<button type="button" id="loginBtn" class="btn btn-secondary">로그인</button>
							<button type="button" id="signupBtn" class="btn btn-primary">회원가입</button>
						</div>
					</div>
				</div>
			</form>
			<script>
				// 로그인 버튼 눌렀을때 값이 비어있지 않다면 submit
				$("#loginBtn").on("click", function() {
					if ($("#id").val() === "" || $("#pw").val() === "") {
						alert("아이디 혹은 비밀번호를 입력하세요.");
						return;
					}
					$("#loginForm").submit();
				})

				// 회원가입 버튼을 눌렀을때 회원가입 페이지로 이동
				document.getElementById("signupBtn").onclick = function() {
					location.href = "/toSignup.mem";
				}
				
				//쿠키 가져오기
				   $(document).ready(function(){
				      
				      let key = getCookie("key");
				      $("#id").val(key);
				      
				      if($("#id").val() != ""){ // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
				         $("#rememberId").attr("checked",true); //ID 저장하기를 체크상태로 두기
				      }
				      
				      $("#rememberId").change(function(){ //체크박스에 변화가 있다면
				         if($("#rememberId").is(":checked")){ //id저장하기 체크했을때
				            setCookie("key",$("#id").val(),30); //30일동안 쿠키 저장
				         }else{
				            deleteCookie("key");
				         }

				      })
				      
				      //ID 저장하기를 체크한 상태에서 id를 입력하는 경우 , 이럴때도 쿠키 저장.
				      $("#id").keyup(function(){ //ID 입력칸에 ID를 입력할 때
				         if($("#rememberId").is(":checked")){ //ID 저장하기를 체크한 상태라면
				            setCookie("key", $("#id").val(),30); //30일동안 쿠기 보관
				         }
				         
				         
				      })

				   });
				   // 쿠키 저장하기
				   // setCookie => saveid함수에서 넘겨준 시간이 현재시간과 비교해서 쿠키를 생성하고 지워주는 역할
				   function setCookie(cookieName, value, exdays){
				       var exdate = new Date();
				       exdate.setDate(exdate.getDate() + exdays);
				       var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
				       document.cookie = cookieName + "=" + cookieValue;
				   }
				    // 쿠키 삭제
				   function deleteCookie(cookieName){
				       var expireDate = new Date();
				       expireDate.setDate(expireDate.getDate() - 1);
				       document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
				   }
				    // 쿠키 가져오기
				   function getCookie(cookieName) {
				       cookieName = cookieName + '=';
				       var cookieData = document.cookie;
				       var start = cookieData.indexOf(cookieName);
				       var cookieValue = '';
				       if(start != -1){ // 쿠키가 존재한다면
				           start += cookieName.length;
				           var end = cookieData.indexOf(';', start);
				           if(end == -1)end = cookieData.length; // if(end == -1) -> 쿠키 값의 마지막 위치 인덱스 번호 설정
				           cookieValue = cookieData.substring(start, end);
				       }
				       return unescape(cookieValue);
				   }
			   

			</script>
		</c:otherwise>
	</c:choose>




</body>
</html>
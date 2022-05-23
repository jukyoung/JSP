<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<title>상세페이지</title>
<style>
        .container{
            margin: auto;
            text-align: center;
        }
        .content .bod{
            border: 1px solid lightgrey;
        }
        .comment {
            text-align: left;
            border: 1px solid black;
        }
        .comment{
            margin: 10px;
        }
        .btns button{
            margin: 5px;
        }
        .reply{
         border : none;
        }
        .writer-reply{
         font-weight: bold;
        }
        .date-reply{     	
        	font-size: 12px;
        	color : grey;
        }
        .content-reply{
        	border : none;
        	width: 100%;
        }
        .body-header-reply:not(:first-child){
        	border-top : 1px solid lightgrey;
        }
       textarea{
       	resize: none;
       }
       .form-control[readonly]{
       	background-color: transparent;
       }
       .body-btnAfter-reply{
       	display: none;
       }
    </style>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col">
                <h3>상세보기</h3>
            </div>
        </div>
        <div class="row content">
            <div class="row">
                <div class="col-3 bod"><p>제목</p></div>
                <div class="col-9 bod">
                    <input type="text" name="title" value="${dto.title}" id="title" class="form-control" readonly>
                </div>
            </div>
            <div class="row">
                <div class="col-3 bod"><p>글쓴이</p></div>
                <div class="col-3">
                    <p>${dto.writer_nickname}</p>
                </div>
                <div class="col-3 bod">
                    <p>작성일</p>
                </div>
                <div class="col-3">
                    <p>${dto.written_date}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-3 bod">
                    <p>내용</p>
                </div>
                <div class="col-9 bod">
                    <textarea id="content" name="content" class="form-control" readonly>${dto.content}</textarea>
                </div>
            </div>
        </div>
        <%-- 댓글 영역 --%>
        <div class="row comment">
            <div class="row">
                <div class="col d-flex justify-content-center">
                    <h3>댓글</h3>
                </div>
            </div>
            <div class="body-reply-box">
            	<c:if test="${empty list}"> <%-- 등록된 댓글이 없다면 --%>
            		<div class="row">
                		<div class="col-12">
                    		<p class="text-center">등록된 댓글이 없습니다.</p>
                		</div>
            		</div>
            	</c:if>
            	<c:if test="${not empty list}">
            		<c:forEach items="${list}" var="redto">
                			<div class="col-12 body-header-reply">
                    			<span class="writer-reply">${redto.writer_nickname}<span>
                    			<span class="date-reply">${redto.written_date}<span>
                			</div>
                			 <div class="col-9 body-content-reply">
                    			<textarea class="content-reply form-control" readonly>${redto.content}</textarea>
                			 </div>
                			  <%-- 자기자신이 쓴 댓글에만 수정 삭제만 해야하니까 --%>
                				<c:if test="${loginSession.id eq redto.writer_id}">
	                				<div class="col-3 body-btnDefault-reply">
				                		<button type="button" class="btn btn-warning modify-reply">수정</button>
				               			<button type="button" class="btn btn-danger delete-reply" value="${redto.seq_reply}">삭제</button>
				               			<%-- 버튼에게 시퀀스 값 넘겨줄때 value 쓰기 --%>
	                				</div>
	                				<div class="col-3 body-btnAfter-reply">
				                		<button type="button" class="btn btn-secondary cancle-reply">취소</button>
				               			<button type="button" class="btn btn-primary complete-reply" value="${redto.seq_reply}">완료</button>
	                				</div>
	                				
                				</c:if>
                				</c:forEach>
            				</div>
            	</c:if>
            	</div>
        <%-- 댓글 입력 영역 --%>
       	<form id="formReply"  name="formReply">
	       	<div class="col d-none">
	       		<input type="text" name="seq_board" value="${dto.seq_board}">
	       	</div>
            <div class="row">
                <div class="col-10">
                	<textarea id="inputReply" name="content" class="form-control"></textarea> 
                </div>
                <div class="col-2">
                 <button type="button" id="replyBtn" class="btn btn-success">등록</button>
                </div>
            </div>
      </form>
        <div class="row">
            <div class="col btns d-flex justify-content-center">
                <button type="button" id="backBtn" class="btn btn-secondary">뒤로가기</button>
                <%-- 자기자신이 쓴 글에만 수정 삭제만 해야하니까 --%>
                <c:if test="${loginSession.id eq dto.writer_id}">
	                <button type="button" id="btnModify" class="btn btn-warning">수정</button>
	                <button type="button" id="btnDelete" class="btn btn-danger">삭제</button>
	                
	                <script>
	                	$("#btnModify").on("click", function(){ <%-- 수정 버튼을 눌렀을때 시퀀스 번호도 가져감 --%>
	                		location.href="/modify.bo?seq_board=${dto.seq_board}";
	                	});
	                	$("#btnDelete").on("click", function(){<%-- 삭제 버튼을 눌렀을때 시퀀스 번호도 가져감 --%>
	                		let answer = confirm("게시글을 삭제하시겠습니까?");
	                		if(answer){
	                			location.href = "/delete.bo?seq_board=${dto.seq_board}";
	                		}
	                	});
	                </script>
                </c:if>
            </div>
        </div>
    </div>
<script>
	// 댓글 수정 버튼에게 이벤트 부여
	$(".body-reply-box").on("click", ".modify-reply", function(e){
		$(e.target).parent(".body-btnDefault-reply").css("display", "none"); // 수정삭제 버튼 감추기
		$(e.target).parent().next(".body-btnAfter-reply").css("display", "block"); // 취소완료 버튼 보이기
		// 댓글 수정 가능하게끔 readonly 속성 풀어주기
		$(e.target).parent(".body-btnDefault-reply").prev().children("textarea").attr("readonly", false).focus();
	});
	// 수정 완료 버튼을 눌렀을때
	$(".body-reply-box").on("click", ".complete-reply", function(e){
		// 수정한 내용(textarea value)
		// 수정한 댓글의 seq(seq_reply)
		// 게시글의 seq(seq_board)
		let seq_reply = $(e.target).val();
		let seq_board = "${dto.seq_board}";
		console.log(seq_reply + " : " + seq_board);
		// 부모의 형제(위쪽으로)요소 중 body-content-reply를 선택하는데 그 중 가장 첫번째 요소 선택
		let content = $(e.target).parent(".body-btnAfter-reply").prevAll(".body-content-reply").first().children("textarea").val();
		console.log(content);
		
		$.ajax({
			url: "/modifyProc.rp"
			, type: "post"
			, data : {seq_reply: seq_reply, seq_board: seq_board, content: content}
			, success: function(data){
				console.log(data);
				if(data === "fail"){
					alert("댓글 수정에 실패했습니다.");
				}else{ // 댓글 수정에 성공했다면 댓글 목록을 새롭게 다시 뿌려줌
					makeReply(data);
				}
			}
			, error: function(e){
				console.log(e);
			}
		})
	});
	// 수정 취소 버튼을 눌렀을때
	$(".body-reply-box").on("click", ".cancle-reply", function(e){
		
		$.ajax({
			url: "/cancleModify.rp?seq_board=${dto.seq_board}" 
			, type: "get"
			, success: function(data){
				console.log(data);
				if(data === "fail"){
					alert("취소에 실패했습니다.");
				}else{ // 취소 성공했다면 댓글 목록을 새롭게 다시 뿌려줌
					makeReply(data);
				}
			}
			,error : function(e){
				console.log(e);
			}
		})
	})
	// 댓글 삭제 버튼에게 이벤트 부여(동적요소니까 상위요소에 이벤트 잡아주기)
	$(".body-reply-box").on("click", ".delete-reply", function(e){
		let answer = confirm("댓글을 삭제하시겠습니까?");
		if(answer){
			//console.log($(e.target).val());
			
			let seq_reply = $(e.target).val();
			$.ajax({
				url : "/deleteProc.rp"
				, type : "post"
				, data : {seq_reply: seq_reply, seq_board: "${dto.seq_board}"}
				, success: function(data){
					console.log(data);
					if(data === "fail"){
						alert("댓글 삭제에 실패했습니다.");
					}else{ // 댓글 삭제에 성공했다면 댓글 목록을 새롭게 다시 뿌려줌
						makeReply(data);
					}
				}
				,error : function(e){
					console.log(e);
				}
			})
		}
	})
	$("#replyBtn").on("click", function(){ // 댓글 등록 버튼을 눌렀을때
		if($("#inputReply").val() === ""){ // 댓글 입력창이 비어있다면
			alert("입력된 댓글이 없습니다.");
			return;
		}
			// ajax를 이용해서 from 전송
			let data = $("#formReply").serialize(); // 전송할 수 있는 데이터로 변환
			console.log(data);
			$("#inputReply").val("");
			$.ajax({
				url: "/insert.rp"
				, type: "post"
				, data : data // 위에서 만든 data 변수
				// 만약 서버에서 응답해주는 값이 일반 text일 수도 있고 (에러일때 fail 값을 보내줌)
				// json 형식일 수도 있다면 dataType을 명시하지 않는다.
				, success: function(data){
					console.log(data);
					if(data === "fail"){ // 댓글등록에 실패했거나
						alert("댓글 등록에 실패했습니다.");
					}else{ 
						makeReply(data);
					}
				}	
				, error : function(e){
					console.log(e);
				}
			})
		})
		
		function makeReply(data){
		// json 형식의 데이터가 넘어오거나(댓글리스트)
		/* json 형식의 문자열을 함수를 통해서 
		자바스크립트 객체 형식으로 변환*/
		let replyList = JSON.parse(data);
		console.log(replyList);
		
		// 넘겨받은 최신 댓글 list를 실제 댓글 목록에 다시 뿌려주는 작업
		$(".body-reply-box").empty(); // 기존에 있던 댓글 사라지게 하는 작업
		
		//replyList= []; 댓글이 없는 상황을 만드는 테스트용 코드
		
		if(replyList.length == 0){ // 댓글이 없다면
			let p = $("<p>").addClass("text-center").html("등록된 댓글이 없습니다.");
			let div = $("<div>").addClass("col-12");
			div.append(p);
			$(".body-reply-box").append(div);
		}else{ // 댓글이 있다면 댓글 목록 만들어서 append 해주기
			for(let reply of replyList){
				// 댓글 타이틀 부분 요소 만들기
				let writer = $("<span>").addClass("writer-reply").html(reply.writer_nickname);
				let date = $("<span>").addClass("date-reply").html(reply.written_date);
				let header = $("<div>").addClass("col-12 body-header-reply")
				header.append(writer, date);
				
				// 댓글 내용 요소 만들기
				let textarea = $("<textarea>").attr({class: "content-reply form-control", readonly : true}).val(reply.content);
				let content = $("<div>").addClass("col-9 body-content-reply");
				content.append(textarea);
				$(".body-reply-box").append(header, content); // 작성자와 같은지 여부 상관없이 댓글은 띄워주기
				// 댓글 작성자 id 와 로그인한 사람의 id가 같다면 수정삭제버튼 보이기
				if(reply.writer_id === "${loginSession.id}"){
					let modifyBtn = $("<button>").addClass("btn btn-warning modify-reply").html("수정");
					let deleteBtn = $("<button>").addClass("btn btn-danger delete-reply").html("삭제").val(reply.seq_reply); // 여기서 reply 는 for문 돌때의 변수
					let defaultBtn = $("<div>").addClass("col-3 body-btnDefault-reply");
					defaultBtn.append(modifyBtn, deleteBtn);
					// 수정 취소 수정완료 버튼
					let cancleBtn =  $("<button>").addClass("btn btn-secondary cancle-reply").html("취소");
					let completeBtn = $("<button>").addClass("btn btn-primary complete-reply").html("완료").val(reply.seq_reply);
					let afterBtn = $("<div>").addClass("col-3 body-btnAfter-reply");
					afterBtn.append(cancleBtn, completeBtn);
					$(".body-reply-box").append(defaultBtn, afterBtn); // 작성자와 같다면 실제 요소를 html영역에 추가하기
				}
			}
		}
	}
	$("#backBtn").on("click", function(){ // 뒤로가기 버튼을 눌렀을때
		location.href = "/board.bo";
	});
	
</script>
</body>
</html>
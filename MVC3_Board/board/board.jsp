<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.js"
	integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
	crossorigin="anonymous"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<title>게시글</title>
<style>
.btns {
	margin: 3px;
	text-align: right;
}
a{
   text-decoration: none;
   color: black;
     }
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col btns">
				<button type="button" id="logoutBtn" class="btn btn-danger">로그아웃</button>
				<button type="button" id="writeBtn" class="btn btn-warning">글쓰기</button>
			</div>
		</div>
		<div class="row m-2">
			<div class="col-9 d-flex justify-content-end">
				<input type="text" id="searchKeyword" class="form-control" placeholder="제목 입력"> 
			</div>
			<div class="col-3 d-flex justify-content-end">
				<button type="button" id="searchBtn" class="btn btn-outline-dark">검색</button>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<table class="table table-bordered">
					<tr>
						<th>글번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>작성일</th>
						<th>조회수</th>
					</tr>
					<tbody class="body-board">
					<c:choose>
						<c:when test="${list.size() == 0}">
							<tr>
								<td colspan=5>등록된 게시글이 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${list}" var="dto">
								<tr>
									<td>${dto.seq_board}</td>
									<%-- 제목을 누르면 상세페이지로 들어가고 제목에 대한 고유값인 글번호 값도 같이 가져감 --%>
									<td><a href="/detailView.bo?seq_board=${dto.seq_board}">${dto.title}</a></td>
									<td>${dto.writer_nickname}</td>
									<td>${dto.written_date}</td>
									<td>${dto.view_count}</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					</tbody>
				</table>
			</div>
		</div>
		<nav>
		  <ul class="pagination justify-content-center">
		  <c:if test="${naviMap.needPrev eq true}">
		    <li class="page-item"><a class="page-link" href="/board.bo?curPage=${naviMap.startNavi-1}">Previous</a></li>
		    <%-- 현재 6페이지에 있는 상태에서 이전 버튼을 클릭했음 -> 5페이지로 이동 --%>
		  </c:if> 
		  <%-- var:변수 begin: 시작할 인덱스 값 end: 끝 값 step: 몇 씩 증가하면서 반복(인덱스를 늘릴건지)--%>
		  <c:forEach var="pageNum" begin="${naviMap.startNavi}" end="${naviMap.endNavi}" step="1" >
		      <li class="page-item"><a class="page-link" href="/board.bo?curPage=${pageNum}">${pageNum}</a></li>
		  </c:forEach>

		  <c:if test="${naviMap.needNext eq true}">
		    <li class="page-item"><a class="page-link" href="/board.bo?curPage=${naviMap.endNavi+1}">Next</a></li>
		  </c:if>
		  </ul>
		</nav>
		<div class="row">
			<div class="col btns">
				<button type="button" id="homeBtn" class="btn btn-success">홈으로</button>
			</div>
		</div>
	</div>
	<script>
    	$("#searchBtn").on("click", function(){// 검색 버튼
    		// 검색어 키워드 값을 가져오기
    		let searchKeyword = $("#searchKeyword").val();
    		//console.log(searchKeyword);
    		// /searchProc.bo -> 검색값만 넘겨주기, 조회할거니까 get방식으로
    		//location.href = "/searchProc.bo?searchKeyword="+searchKeyword;
    		
    		//ajax 사용하기
    		$.ajax({
    			url: "/searchProc.bo?searchKeyword=" +searchKeyword
    			, type: "get"
    			, dataType: "json"
    			, success: function(data){
    				console.log(data);
    				console.log(data.length); // 데이터가 있으면 배열의 길이값이 나옴, 없는 값 검색하면 배열의 길이가 0 
    				$(".body-board").empty(); // 기존 게시글 목록 모두 삭제
    				if(data.length == 0){ // 검색된 결과가 없다면
    					let tr = $("<tr>");
    					let td = $("<td colspan=5>").html("검색된 게시글이 없습니다.");
    					tr.append(td);
    					$(".body-board").append(tr);
    				}else{ // 검색된 결과가 있다면
    					for(let dto of data){ // 배열을 for문을 돌리면서 객체 하나씩 꺼내서 dto에 담기
    						let tr = $("<tr>");
    						let td1 = $("<td>").html(dto.seq_board);
    						let anchor = $("<a>").attr("href", "/detailView.bo?seq_board="+dto.seq_board).html(dto.title);
    						let td2 = $("<td>").append(anchor);
    						let td3 = $("<td>").html(dto.writer_nickname);
    						let td4 = $("<td>").html(dto.written_date);
    						let td5 = $("<td>").html(dto.view_count);
    						tr.append(td1, td2, td3, td4, td5);
    						$(".body-board").append(tr);
    					}
    				}
    			}
    			, error: function(e){
    				console.log(e);
    			}
    		})
    	})
    	/* 제이쿼리 A요소를 기준으로
    	A.empty() -> A라는 요소를 기준으로 모든 하위요소를 삭제 = A요소는 유지
    	A.remove() -> A라고 하는 요소자체를 삭제 -> 그에 따라 A안쪽에 있던 하위요소도 모두 삭제
    	*/
    	$("#writeBtn").on("click", function(){ // 글쓰기 버튼
    		location.href = "/write.bo";
    	})
    	$("#logoutBtn").on("click", function(){ // 로그아웃 버튼
    		location.href= "/logoutProc.mem";
    	})
    	$("#homeBtn").on("click", function(){ // 홈 버튼
    		location.href= "/";
    	})

    </script>
</body>
</html>

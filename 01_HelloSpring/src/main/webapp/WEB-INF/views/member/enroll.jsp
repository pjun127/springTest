<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.2.1.min.js"></script>
<!-- 부트스트랩관련 라이브러리 -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
<!-- 사용자작성 css -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/style.css" />
<meta charset=UTF-8">
<title>Insert title here</title>
<style>
	div#enroll-container{width:400px; margin:0 auto; text-align:center;}
	div#enroll-container input, div#enroll-container select {margin-bottom:10px;}
	 /*중복아이디체크관련*/
    div#enroll-container{position:relative; padding:0px;}
    div#enroll-container span.guide {display:none;font-size: 12px;position:absolute; top:12px; right:10px;}
    div#enroll-container span.ok{color:green;}
    div#enroll-container span.error{color:red;}
</style>
</head>
<body>
      <jsp:include page="/WEB-INF/views/common/header.jsp">
         <jsp:param value="회원등록" name="pageTitle"/>
      </jsp:include>
      <div id="enroll-container">
         <form name="memberEnrollFrm" action="${pageContext.request.contextPath}/member/memberEnrollEnd.do" method="post" onsubmit="return validate();" >
            <input type="text" class="form-control" placeholder="아이디 (4글자이상)" name="userId" id="userId_" required>
            <span class="guide ok">이 아이디는 사용할 수 있음</span>
            <span class="guide error">이 아이디는 사용할 수 없음</span>
            <input type="hidden" name="checkId" value="0"/>
            <script>
            	$(function(){
            		$('#userId_').keyup(function(){
            			var userId=$('#userId_').val().trim();
            			if(userId.length<4){
            				$(".guid").hide();
            				return;
            			}
            			$.ajax({
            				url:"${path}/member/checkId.do",
            				data : {"userId":userId},
            				success:function(data){
            					console.log(data);
            					console.log(data.num+" : "+typeof data.num);
            					console.log(data['char'] + " : "+ typeof data['char']);
            					console.log(data.isId+ " : "+ typeof data.isId)
            					console.log(data.list+ " : "+ typeof data.list)
            					
            					for(var i=0; i<data.list.length; i++){
            						console.log("for : "+data.list[i]);
            					}
            					
            					if(data.isId == true){
            						$(".guide.ok").hide();
            						$(".guide.error").show();
            					}
            					else{
            						$(".guide.ok").show();
            						$(".guide.error").hide();
            					}
            				}
            			});
            		});
            	});
            	
            </script>
            <input type="password" class="form-control" placeholder="비밀번호" name="password" id="password_" required>
            <input type="password" class="form-control" placeholder="비밀번호확인" id="password2" required>
            <input type="text" class="form-control" placeholder="이름" name="userName" id="userName" required>
            <input type="number" class="form-control" placeholder="나이" name="age" id="age">
            <input type="email" class="form-control" placeholder="이메일" name="email" id="email" required>
            <input type="tel" class="form-control" placeholder="전화번호 (예:01012345678)" name="phone" id="phone" maxlength="11" required>
            <input type="text" class="form-control" placeholder="주소" name="address" id="address">
            <select class="form-control" name="gender" required>
               <option value="" disabled selected>성별</option>
               <option value="M">남</option>
               <option value="F">여</option>
            </select>
            <div class="form-check-inline form-check">
               취미 : &nbsp; 
               <input type="checkbox" class="form-check-input" name="hobby" id="hobby0" value="운동"><label for="hobby0" class="form-check-label">운동</label>&nbsp;
               <input type="checkbox" class="form-check-input" name="hobby" id="hobby1" value="등산"><label for="hobby1" class="form-check-label">등산</label>&nbsp;
               <input type="checkbox" class="form-check-input" name="hobby" id="hobby2" value="독서"><label for="hobby2" class="form-check-label">독서</label>&nbsp;
               <input type="checkbox" class="form-check-input" name="hobby" id="hobby3" value="게임"><label for="hobby3" class="form-check-label">게임</label>&nbsp;
               <input type="checkbox" class="form-check-input" name="hobby" id="hobby4" value="여행"><label for="hobby4" class="form-check-label">여행</label>&nbsp;
            </div>
            <br />
            <input type="submit" class="btn btn-outline-success" value="가입" >&nbsp;
            <input type="reset" class="btn btn-outline-success" value="취소">
         </form>
      </div>
      
      <script>
      	
      </script>
</body>
</html>
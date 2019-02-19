<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*, com.kh.spring.member.model.vo.Member" %>
<%
	Member m = (Member)request.getAttribute("member");
	List<String> hobbyList = null;
	if(m.getHobby()!=null){
		hobbyList = Arrays.asList(m.getHobby());		
	}
	
%>
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
</head>
<body>
<style>
    div#update-container{width:400px; margin:0 auto; text-align:center;}
    div#update-container input, div#update-container select {margin-bottom:10px;}
</style>

    <div id="update-container">
        <form name="memberUpdateFrm" action="${pageContext.request.contextPath}/member/memberUpdate.do" method="post">
            <input type="text" class="form-control" placeholder="아이디 (4글자이상)" name="userId" id="userId_" value="${member.userId}" readonly required>
            <input type="text" class="form-control" placeholder="이름" name="userName" id="userName" value="${member.userName}" required>
            <input type="number" class="form-control" placeholder="나이" name="age" id="age" value="${member.age}">
            <input type="email" class="form-control" placeholder="이메일" name="email" id="email" value="${member.email}" required>
            <input type="tel" class="form-control" placeholder="전화번호 (예:01012345678)" name="phone" id="phone" maxlength="11" value="${member.phone}" required>
            <input type="text" class="form-control" placeholder="주소" name="address" value="${member.address}" id="address">
            <select class="form-control" name="gender" required>
            <option value="" disabled selected>성별</option>
            <option value="M" ${member.gender=='M'?'selected':'' }>남</option>
            <option value="F" ${member.gender=='F'?'selected':'' }>여</option>
            </select>
            <div class="form-check-inline form-check">
                취미 : &nbsp; 
                <input type="checkbox" class="form-check-input" name="hobby" id="hobby0" value="운동" <%=hobbyList!=null && hobbyList.contains("운동")?"checked":""%>><label for="hobby0" class="form-check-label" >운동</label>&nbsp;
                <input type="checkbox" class="form-check-input" name="hobby" id="hobby1" value="등산" <%=hobbyList!=null && hobbyList.contains("등산")?"checked":""%>><label for="hobby1" class="form-check-label" >등산</label>&nbsp;
                <input type="checkbox" class="form-check-input" name="hobby" id="hobby2" value="독서" <%=hobbyList!=null && hobbyList.contains("독서")?"checked":""%>><label for="hobby2" class="form-check-label" >독서</label>&nbsp;
                <input type="checkbox" class="form-check-input" name="hobby" id="hobby3" value="게임" <%=hobbyList!=null && hobbyList.contains("게임")?"checked":""%>><label for="hobby3" class="form-check-label" >게임</label>&nbsp;
                <input type="checkbox" class="form-check-input" name="hobby" id="hobby4" value="여행" <%=hobbyList!=null && hobbyList.contains("여행")?"checked":""%>><label for="hobby4" class="form-check-label" >여행</label>&nbsp;
            </div>
            <br />
            <input type="submit" class="btn btn-outline-success" value="수정" >&nbsp;
            <input type="reset" class="btn btn-outline-success" value="취소">
        </form>
    </div>
</body>
</html>
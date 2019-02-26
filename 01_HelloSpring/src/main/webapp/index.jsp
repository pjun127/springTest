<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="HelloSpring" name="pageTitle"/>
</jsp:include>

<img src="${pageContext.request.contextPath }/resources/images/logo-spring.png" id="center-image" alt="스프링로고"/>
<button onclick="ajaxTest();">textAjax</button>

<button onclick="ajaxTest1();">vo객체받기</button>
<input type="text" id="userId" placeholder="검색할 아이디 입력"/>

<button onclick="ajaxTest2();">map객체 받기</button>
<input type="number" id="no" placeholder="검색할 게시글 입력"/>

<div id="result"></div>

<script>

	function ajaxTest2(){
		$.ajax({
			url:"${pageContext.request.contextPath}/ajaxTest3.do",
			data:{"no":$('#no').val()},
			dataType:"json",
			success:function(data){
				console.log(data);
				console.log(data.BOARDCONTENT);
				var c = $("<h3>");
				for(var i in data){
					console.log(data[i]);
					
					c.append(data[i]);
					
				}
				$('#result').html(c);
			}
		});
	};

	function ajaxTest1(){
		$.ajax({
			url:"${pageContext.request.contextPath}/ajaxTest2.do",
			data:{"userId":$('#userId').val()},
			dataType:"json",
			success:function(data){
				console.log(data);
				console.log(data.hobby);
				console.log(data.password);
				var c = $("<h3>");
				for(var i in data){
					console.log(i);
					if(i=="password")continue;
					c.append(data[i]);
				}
				$('#result').html(c);
			}
		});
	};

	function ajaxTest(){
		$.ajax({
			url:"${pageContext.request.contextPath}/ajaxTest1.do",
			dataType:"json",
			success:function(data){
				console.log(data);
				var table = $('<table>');
				for(var i=0; i<data.length; i++){
					var tr = $('<tr>');
					console.log("인덱스 확인 " + data[i]);
					for(var key in data[i]){
						console.log("컬럼 확인 "+ key +"컬럼에 해당 하는 값" +data[i][key]);
						var td = $('<td>');
						if(key=="BOARDDATE"){
							var date = new Date(data[i][key]);
							td.append(date.getFullYear()+"년"+date.getMonth()+"월"+date.getDate()+"일");
						}
						else{
							td.append(data[i][key]);
						}
						tr.append(td);
					}
					table.append(tr);
				}
				$('#result').html(table);
			}
			
		});
	};
	
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
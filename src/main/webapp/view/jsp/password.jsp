<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%= path %>/view/CSS/password.css">
<script type="text/javascript" src="<%= path %>/view/JS/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="<%= path %>/view/JS/password.js"></script>
<title>更改口令</title>
</head>
<body>
	<div id="mainContainer">
		<h1 id="title">更改口令</h1><hr>
		<div class="line">
			<div class="label">原口令</div>
			<div class="inputContainer">
				<input type="password" id="oldPassword">
			</div>
		</div>
		<div class="line">
			<div class="label">新口令</div>
			<div class="inputContainer">
				<input type="password" id="newPassword">
			</div>
		</div>
		<div class="line">
			<input type="button" value="提交" id="submitBtn" onclick="submitForm()">
		</div>
	</div>
</body>
</html>
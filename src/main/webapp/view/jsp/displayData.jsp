<%
	String kind = request.getParameter("kind");
	String workPlace = request.getParameter("workPlace");
	String path = request.getContextPath();
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%= workPlace %>地区<%= kind %>招聘信息一览表</title>
<link rel="stylesheet" type="text/css" href="<%= path %>/view/CSS/displayData.css">
<script type="text/javascript" src="<%= path %>/view/JS/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="<%= path %>/view/JS/displayData.js"></script>
</head>
<body>
	<div class="tableContainer">
		<h1 class="header">
			<%= workPlace %>地区<%= kind %>招聘信息一览表
		</h1>
		<table>
			<tr style="height:50px">
				<th style="width:60px">序号</th>
				<th style="width:100px">职业类型</th>
				<th style="width:230px">职业名称</th>
				<th style="width:240px">招聘单位</th>
				<th style="width:60px">月薪</th>
				<th style="width:75px">工作地点</th>
				<th style="width:90px">经验要求</th>
				<th style="width:90px">学历要求</th>
				<th style="width:75px">工作性质</th>
				<th>招聘页面地址</th>
			</tr>
		</table>
		<p id="loadingLab">正在加载数据...</p>
	</div>	
</body>
</html>
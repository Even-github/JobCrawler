<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JobCrawler管理器</title>
<link rel="stylesheet" type="text/css" href="<%= path %>/view/CSS/index.css">
<script type="text/javascript" src="<%= path %>/view/JS/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="<%= path %>/view/JS/index.js"></script>
</head>
<body>
	<div class="maincontainer">
		<div class="header">
			<h1>JobCrawler管理器</h1>
		</div>
		<div class="tagContainer">
			<div class="tagControl" id="savedContentTag">
				<h2>已保存的招聘信息</h2>
			</div>
			<div class="tagControl" id="newContentTag">
				<h2>获取新的招聘信息</h2>
			</div>
		</div>
		<div class="contentContainer" id="savedContent">
			<table>
				<tr>
					<th style="width:150px">职业类型</th>
					<th style="width:150px">地区</th>
					<th style="width:200px">数据量（条）</th>
					<th style="width:100px">查看</th>
					<th style="width:100px">删除</th>
					<th style="width:100px">生成报告</th>
				</tr>
			</table>
		</div>
		<div class="contentContainer" id="newContent">
			<table>
				<tr>
					<th colspan="2">新建爬虫任务</th>
				</tr>
				<tr>
					<td style="width:30%">职业类型</td>
					<td>
						<select id="sel_kind"></select>
					</td>
				</tr>
				<tr>
					<td style="width:30%">地区</td>
					<td>
						<select id="sel_distinct">
						<option>广州</option>
						<option>北京</option>
						<option>上海</option>
						<option>深圳</option>
						<option>杭州</option>
						<option>成都</option>
						<option>南京</option>
						<option>武汉</option>
						<option>西安</option>
						<option>厦门</option>
						<option>长沙</option>
						<option>苏州</option>
						</select>					
					</td>
				</tr>
				<tr>
					<td>已获取数据量（条）</td>
					<td id="dataAmount">0</td>
				</tr>
				<tr style="height:180px">
					<td>运行状态<td>
					<textarea rows="8" cols="70" readonly="readonly"></textarea>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" value="启动爬虫" id="start" style="margin-right:20px" onclick="startCrawler()">
						<input type="button" value="终止爬虫" id="stop" style="margin-left:20px" onclick="stopCrawler()">
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
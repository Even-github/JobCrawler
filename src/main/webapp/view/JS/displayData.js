var hostpath=location.protocol+"//"+location.host+"/JobCrawler/"; //获取url根目录
$(document).ready(function(){
	$.ajax({
		type: "get",
		url: hostpath + "displayData/getData" + window.location.search,
		dataType: "json",
		success: function(data)
		{
			$("#loadingLab").remove();
			if(data == "" || data == null)
			{
				$("#tableContainer").append("<p id='emptyLab'>无数据</p>");
			}
			else
			{
				$.each(data, function(i, e)
						{
					var next = 
						"<tr style='height:45px'><td>" + (i+1) +"</td>"
						+ "<td>" + e.kind + "</td>"
						+ "<td>" + e.job + "</td>"
						+ "<td>" + e.recruitingUnit + "</td>"
						+ "<td>" + e.salary + "</td>"
						+ "<td>" + e.workPlace + "</td>"
						+ "<td>" + e.experience + "</td>"
						+ "<td>" + e.academic + "</td>"
						+ "<td>" + e.workType + "</td>"
						+ "<td><a href=" + e.url + ">"+ e.url +"</a></td>"
						+ "</tr>";
					$("table").append(next);
						});
			}
		},
		error: function()
		{
			$("#savedContent").append("<p id='errorLab'>加载失败</p>");
		}
	});
});
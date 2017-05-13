$(document).ready(function(){
	$.ajax({
		type: "get",
		url: "http://localhost:8080/JobCrawler/displayData/getData" + window.location.search,
		dataType: "json",
		success: function(data)
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
	});
});
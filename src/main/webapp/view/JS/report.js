//打开页面时，自动加载图表
$(document).ready(function() {
	getSalaryData();
	getExpData();
	getAcadeData();
});

//获取月薪数据
function getSalaryData()
{
	$.ajax({
		type: "post",
		url: "http://localhost:8080/JobCrawler/report/getSalaryData" + window.location.search,
		dataType: "json",
		success: function(data)
		{
			var p = new Array(7);
			p[0] = data['2k以下'];
			p[1] = data['2k-4k'];
			p[2] = data['4k-6k'];
			p[3] = data['6k-8k'];
			p[4] = data['8k-10k'];
			p[5] = data['10k-15k'];
			p[6] = data['15k-20k'];
			p[7] = data['20k-30k'];
			p[8] = data['30k-40k'];
			p[9] = data['40k以上'];

			generateSalaryChart(p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7], p[8], p[9]);
		}
	});
}

//生成月薪水平分布图
function generateSalaryChart(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10)
{
	var salaryChart = echarts.init(document.getElementById('salaryChartDiv'));
	var option = {
			title: {
				text: '月薪水平分布图',
				left: 'center',
				textStyle: {
					fontSize: '30',
					fontFamily: 'Monospace',
				}
			},
			tooltip: {
				trigger: 'item',
				formatter: '{a}<br>{b}:{c}条',
			},
			legend: {
				data: ['月薪'],
				right: '10%',
				textStyle: {
					fontSize: '18',
				},
			},
			xAxis: {
				name: '月薪',
				data: ["2k以下", 
					"2k-4k", 
					"4k-6k", 
					"6k-8k", 
					"8k-10k", 
					"10k-15k",
					"15k-20k",
					"20k-30k",
					"30k-40k",
					"40k以上"],
			},
			yAxis: {
				name: '招聘信息数量/条',
			},
			series: [{
				name: '月薪',
				type: 'bar',
				data: [v1, v2, v3, v4, v5, v6, v7]
			}]
	};
	salaryChart.setOption(option);
}

//获取经验要求数据
function getExpData()
{
	$.ajax({
		type: 'post',
		url: "http://localhost:8080/JobCrawler/report/getExpData" + window.location.search,
		dataType: 'json',
		success: function(data)
		{
			var p = new Array(5);
			p[0] = data['经验应届毕业生'];
			p[1] = data['经验1-3年'];
			p[2] = data['经验3-5年'];
			p[3] = data['经验5-10年'];
			p[4] = data['经验不限'];

			generateExpChart(p[0], p[1], p[2], p[3], p[4]);
		}
	});
}

//生成经验要求饼状图
function generateExpChart(v1, v2, v3, v4, v5)
{
	var expChart = echarts.init(document.getElementById("expChartDiv"));
	var option = {
			title: {
				text: '工作经验要求',
				left: 'center',
				textStyle: {
					fontSize: '30',
					fontFamily: 'Monospace',
				}
			},
			tooltip: {
				trigger: 'item',
				formatter: '{a}<br>{b}:{c}条({d}%)',
			},
			legend: {
				data: ['经验应届毕业生', '经验1-3年', '经验3-5年', '经验5-10年', '经验不限'],
				right: '20%',
				top: 'center',
				orient: 'vertical',
				textStyle: {
					fontSize: '18',
				},
			},
			series: [{
				name: '工作经验要求',
				type: 'pie',
				radius: '70%',
				calculable : true,
				center: ['40%', '50%'],
				data: [
					{value:v1, name:'经验应届毕业生'},
					{value:v2, name:'经验1-3年'},
					{value:v3, name:'经验3-5年'},
					{value:v4, name:'经验5-10年'},
					{value:v5, name:'经验不限'},
				]
			}]
	};
	expChart.setOption(option);
}

//获取学历数据
function getAcadeData()
{
	$.ajax({
		type: 'post',
		url: "http://localhost:8080/JobCrawler/report/getAcadeData" + window.location.search,
		dataType: 'json',
		success: function(data)
		{
			var p = new Array(5);
			p[0] = data['大专及以上'];
			p[1] = data['本科及以上'];
			p[2] = data['硕士及以上'];
			p[3] = data['博士及以上'];
			p[4] = data['学历不限'];

			generateAcadeChart(p[0], p[1], p[2], p[3], p[4]);
		}
	});
}

//生成学历要求饼状图
function generateAcadeChart(v1, v2, v3, v4, v5)
{
	var acadeChart = echarts.init(document.getElementById("acadeChartDiv"));
	var option = {
			title: {
				text: '学历要求',
				left: 'center',
				textStyle: {
					fontSize: '30',
					fontFamily: 'Monospace',
				}
			},
			tooltip: {
				trigger: 'item',
				formatter: '{a}<br>{b}:{c}条({d}%)',
			},
			legend: {
				data: ['大专及以上', '本科及以上', '硕士及以上', '博士及以上', '学历不限'],
				right: '20%',
				top: 'center',
				orient: 'vertical',
				textStyle: {
					fontSize: '18',
				},
			},
			series: [{
				name: '学历要求',
				type: 'pie',
				radius: '70%',
				calculable : true,
				center: ['40%', '50%'],
				data: [
					{value:v1, name:'大专及以上'},
					{value:v2, name:'本科及以上'},
					{value:v3, name:'硕士及以上'},
					{value:v4, name:'博士及以上'},
					{value:v5, name:'学历不限'},
				]
			}]
	};
	acadeChart.setOption(option);
}
var hostpath=location.protocol+"//"+location.host+"/JobCrawler/"; //获取url根目录
function submitForm()
{
	var oldPassword = $("#oldPassword").val();
	var newPassword = $("#newPassword").val();
	if(newPassword == "" || newPassword == null)
	{
		alert("口令不能为空！");
	}
	else
	{
		$.ajax({
			type: "post",
			url: hostpath + "updatePassword",
			data: "oldPassword=" + oldPassword + "&newPassword=" + newPassword,
			success: function(data)
			{
				if(data == "success")
				{
					alert("口令更改成功！");
				}
				else if(data == "failure")
				{
					alert("原口令错误，口令更改失败！");
				}
				else
				{
					alert("发生了未知错误！");
				}
			}
		});
	}
}
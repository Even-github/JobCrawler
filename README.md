# 简介
JobCrawler是一个集成了拉勾网招聘信息爬虫以及数据可视化（图表化）功能的java web项目，其爬虫功能实现了根据用户选定的职业类型以及工作地区自动抓取拉勾网上的招聘信息，并存储到本地，数据可视化功能实现统计本地存储的招聘信息，自动生成图表，使数据更为直观、有效。

# 开发环境
* 操作系统：Windows 8.1
* 开发语言：Java，html，css，JavaScript
* 集成开发环境：Eclipse neon.3 for Java EE
* 数据库：MySQL 5.7
* 服务器：apache-tomcat-9.0.0.M17
* 后端框架：SpringMVC，MyBatis
* 爬虫框架：HttpComponents 4.3.6，Jsoup 1.8.3
* 前端框架：JQuery 3.2.0.min，echarts

# 云服务器
* 操作系统：centos 7

# 更新日志
## JobCrawler 1.0 -2017-4-10
* 拉勾网爬虫及数据可视化项目

## JobCrawler 1.1 -2017-5-13
* 在首页上显示每一种职业的数据量
* 优化了界面

## JobCrawler 1.2 -2017-6-29
* 采用mybatis技术重构永久层代码

## JobCrawler 1.3 -2017-7-1
* 优化了爬虫多线程控制逻辑，提升了爬虫运行速度
* 解决了部分多线程同步问题
* 加入了删除功能的权限验证机制，现在删除数据需要输入口令
* 优化了界面，爬虫运行状态只有在出现新状态时才会显示出来
* 修复了加载信息显示bug
* 项目正式上线，链接：http://39.108.145.149:8888/JobCrawler/index

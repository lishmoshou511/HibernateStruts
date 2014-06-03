<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: lishuang
  Date: 2014/6/3
  Time: 0:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>信息列表</title>
	<style>
		table{
			border-spacing: 0;
		}
		tr,td,th{
			border: 1px solid blue;
		}
	</style>
</head>
<body>
<div>
	<a href="#">发布信息</a>

	<a href="<s:url action="logout" namespace="/user"/>">退出系统</a>
</div>

<div>
	<h4>欢迎<s:property value="#session.user.name"/>,留言信息</h4>
	<table style="border: 1px solid blue">
		<tr>
			<th>发送人</th>
			<th>发送时间</th>
			<th>接收人</th>
			<th>信息内容</th>
		</tr>
		<s:iterator value="messages" var="message">
			<tr>
				<td><s:property value="#message.sender.name"/> </td>
				<td><s:property value="#message.time"/></td>
				<td><s:property value="#message.receiver.name"/></td>
				<td><s:property value="#message.content"/></td>
			</tr>
		</s:iterator>
		<tr>
			<td>周星驰</td>
			<td>2011-11-11</td>
			<td>张三丰</td>
			<td>我要准备拍太极了</td>
		</tr>

	</table>
</div>


</body>
</html>

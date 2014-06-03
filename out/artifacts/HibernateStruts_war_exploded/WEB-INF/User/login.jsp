<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: lishuang
  Date: 2014/6/3
  Time: 0:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
	<title>Login</title>
</head>
<body>
<h1>用户登录</h1>

<form action="<s:url action="finish" namespace="/user"/>" method="post">
	<table>
		<tr>
			<td>用户ID</td>
			<td>
				<input type="text" name="id"/>
			</td>
		</tr>
		<tr>
			<td>用户密码</td>
			<td>
				<input type="password" name="password"/>
			</td>

		</tr>
		<tr>
			<td>
				<input type="submit" value="提交"/>
			</td>
			<td>
				<input type="reset" value="重置表单"/>
			</td>
		</tr>
	</table>
</form>
<p style="color: red">${message}</p>
</body>
</html>

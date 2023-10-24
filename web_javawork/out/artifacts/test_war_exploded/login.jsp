<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>登录</title>
<link type="text/css" rel="stylesheet" href="css/Login.css" />
<link type="text/css" rel="stylesheet" href="css/iconfont.css"/>
</head>
<body>
	<%--登录界面设置 --%>
	<div id="login-box">
	<form action="UserLoginServlet" method="post">
			<h1>Login</h1>
			<div class="input-box">
				<input type="text" name="userID" id="username" placeholder="账号USERID" />
			</div>
			<div class="input-box">

				<input type="password" name="password" id="password" placeholder="密码PASSWORD" />
			</div>
			<button type="submit">Sign in</button>
	</form>
	</div>
</body>
</html>
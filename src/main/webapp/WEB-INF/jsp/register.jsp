<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>
</head>
<body>
<%-- <h1>${message}</h1> --%>
<h1>${register}</h1>
 <form action="/user/userRegister" method="post">
用户名:<input type="text" name="username"><br/>
<br/>
密码:<input type="password" name="password"><br/>
<br/>
手机号码：<input type="text" name="userphone"><br/>
<br/>
<input type="submit" value="register">
 </form>
</body>
</html>
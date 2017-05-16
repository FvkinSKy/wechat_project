<%--
  Created by IntelliJ IDEA.
  User: rui
  Date: 2017/5/16
  Time: 16:56
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>群发消息</title>
    <!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>
<body>
<div>
    <form action="batch.form" method="post">
        <p><input type="text" id="msg"></p><br>
        <P><input type="submit" id="sub" value="发送"></P>
    </form>
</div>
</body>
</html>

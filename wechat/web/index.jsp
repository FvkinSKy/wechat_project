<%--
  Created by IntelliJ IDEA.
  User: rui
  Date: 2017/5/4
  Time: 17:04
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>$Title$</title>
    <!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <body>
  $END$
  </body>
</html>

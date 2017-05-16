<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: rui
  Date: 2017/5/16
  Time: 9:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>用户列表</title>
</head>
<body>
<div>
    <p>${openIdList }</p>
</div>
</body>
</html>

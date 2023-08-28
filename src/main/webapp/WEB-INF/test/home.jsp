<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 28.08.2023
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<sec:authorize access="isAnonymous()">
    <h1>WYLOGOWANY</h1>
</sec:authorize>
    <h1>HELLO PAGE</h1>
</body>
</html>

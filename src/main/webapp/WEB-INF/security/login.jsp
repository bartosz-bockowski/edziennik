<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 28.08.2023
  Time: 21:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<sec:authorize access="isAnonymous()">
    <h1>WYLOGOWANY</h1>
</sec:authorize>
<form:form method="post"
           modelAttribute="user"
            action="/login">
    <form:input path="username" />
    <form:input path="password" />
    <input type="submit" value="Save">
</form:form>
</body>
</html>

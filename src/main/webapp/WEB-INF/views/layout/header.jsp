<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>E-dziennik</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/global.js"></script>
</head>
<body>
    <header>
        <div id="headerMainDiv"><a href="/">Header</a></div>
        <sec:authorize access="isAuthenticated()">
                <spring:message code="logout" var="logoutText"/>
                <form action="${pageContext.request.contextPath}/logout" method="get"><input type="submit" value="${logoutText}"/></form>
        </sec:authorize>
    </header>
    <br/>
</body>
</html>

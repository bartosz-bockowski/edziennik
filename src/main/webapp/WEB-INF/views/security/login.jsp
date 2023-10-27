<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 28.08.2023
  Time: 21:39
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/small/login-form.css"/>
    <title>E-dziennik</title>
</head>
<body class="text-center">
<%--<form:form class="form-signin" method="post"--%>
<%--           modelAttribute="user"--%>
<%--           action="/login">--%>
<%--    <table>--%>
<%--        <tr>--%>
<%--            <td>--%>
<%--                <spring:message code="user.username"/>--%>
<%--            </td>--%>
<%--            <td>--%>
<%--                <form:input type="text" path="username"/>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--&lt;%&ndash;        <tr class="formTableErrorRow"></tr>&ndash;%&gt;--%>
<%--        <tr>--%>
<%--            <td>--%>
<%--                <spring:message code="user.password"/>--%>
<%--            </td>--%>
<%--            <td>--%>
<%--                <form:input type="password" path="password"/>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--&lt;%&ndash;        <tr class="formTableErrorRow">&ndash;%&gt;--%>
<%--&lt;%&ndash;            <c:if test="${param.error}"><spring:message code="wrongLoginCredentials"/></c:if>&ndash;%&gt;--%>
<%--&lt;%&ndash;        </tr>&ndash;%&gt;--%>
<%--    </table>--%>
<%--    <button type="submit"><spring:message code="login"/></button>--%>
<%--    <br/>--%>
<%--</form:form>--%>
<form:form class="form-signin" method="post" action="/login" modelAttribute="user">
<%--    <img class="mb-4" src="https://getbootstrap.com/docs/4.0/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">--%>
    <h1 class="h1 mb-1 font-weight-bold"><spring:message code="e-dziennik"/></h1>
    <h3 class="h3 mb-3 font-weight-normal">
        <spring:message code="login"/>
    </h3>
    <label for="inputEmail" class="sr-only">
        <spring:message code="user.username"/>
        <spring:message code="user.username" var="username"/>
    </label>
    <form:input path="username" type="text" id="inputEmail" class="form-control" placeholder="${username}" autofocus=""/>
    <label for="inputPassword" class="sr-only">
        <spring:message code="user.password"/>
        <spring:message code="user.password" var="password"/>
    </label>
    <form:input path="password" type="password" id="inputPassword" class="form-control" placeholder="${password}"/>
    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="login"/></button>
    <p class="mt-5 mb-3 text-muted">Bartosz Boćkowski © 2023</p>
</form:form>
</body>
</html>

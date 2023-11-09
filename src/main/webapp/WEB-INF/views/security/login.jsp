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
<jsp:include page="../layout/linksHeader.jsp"/>
<body class="text-center">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/small/login-form.css">
<form:form class="form-signin" method="post" action="/login" modelAttribute="user">
    <h1 class="h1 mb-1 font-weight-bold"><spring:message code="e-dziennik"/></h1>
    <h3 class="h3 mb-3 font-weight-normal">
        <spring:message code="login"/>
    </h3>
    <label for="inputEmail" class="sr-only">
        <spring:message code="user.username"/>
        <spring:message code="user.username" var="username"/>
    </label>
    <form:input path="username" type="text" id="inputEmail" class="form-control" placeholder="${username}"
                autofocus=""/>
    <label for="inputPassword" class="sr-only">
        <spring:message code="user.password"/>
        <spring:message code="user.password" var="password"/>
    </label>
    <form:input path="password" type="password" id="inputPassword" class="form-control" placeholder="${password}"/>
    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="login"/></button>
    <br/>
    <div class="text-danger">
            <c:if test="${param.error && blockedUser == null}">
                <div class="mb-2">
                    <spring:message code="wrongLoginCredentials"/>
                </div>
            </c:if>
            <c:if test="${blockedUser != null}">
                <div class="mb-2">
                    <spring:message code="security.antiBruteForce.blockedUser"/>
                </div>
            </c:if>
            <c:if test="${badPasswordCode == 1}">
                <div class="mb-2">
                    <spring:message code="security.password.badPasswordCode"/>
                </div>
            </c:if>
    </div>
    <button type="button" id="iForgotPasswordButton" class="btn btn-danger mb-4"><spring:message code="security.login.iForgotPassword"/></button>
    <div id="forgottenPasswordDiv" style="display: none;">
        <div id="sent" hidden><spring:message code="security.password.recover.sent"/></div>
        <div id="cantBeEmpty" hidden><spring:message code="security.password.recover.fieldCantBeEmpty"/></div>
        <input type="text" id="inputUsernameForgottenPassword" class="form-control" placeholder="${username}"/>
        <label for="inputUsernameForgottenPassword" class="sr-only">
            <spring:message code="user.username"/>
        </label>
        <button id="recoverPasswordButton" type="button" class="btn btn-lg btn-primary btn-block mt-2"><spring:message code="security.password.recoverPassword"/></button>
    </div>
    <script src="${pageContext.request.contextPath}/js/ajax/security/login.js"></script>
    <jsp:include page="../layout/footer.jsp"/>
</form:form>
</body>
</html>

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
<body>
<jsp:include page="../layout/header.jsp"/>
<h1><spring:message code="login"/></h1>
<form:form method="post"
           modelAttribute="user"
           action="/login">
    <table class="formTable centerBlock">
        <tr>
            <td>
                <spring:message code="user.username"/>
            </td>
            <td>
                <form:input type="text" path="username"/>
            </td>
        </tr>
        <tr class="formTableErrorRow"></tr>
        <tr>
            <td>
                <spring:message code="user.password"/>
            </td>
            <td>
                <form:input type="password" path="password"/>
            </td>
        </tr>
        <tr class="formTableErrorRow">
            <c:if test="${param.error}"><spring:message code="wrongLoginCredentials"/></c:if>
        </tr>
    </table>
    <button type="submit"><spring:message code="login"/></button>
    <br/>
</form:form>
<script src="${pageContext.request.contextPath}/js/addForm.js"></script>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

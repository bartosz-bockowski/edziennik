<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 30.08.2023
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../../layout/header.jsp"/>
<a href="/admin/user/list"><spring:message code="user.list"/></a>
<h1><spring:message code="user.add"/></h1>
<form:form action="/admin/user/add" method="post" modelAttribute="user">
    <table class="formTable centerBlock">
        <tr>
            <td class="formTableNameCell">
                <spring:message code="user.username"/>
            </td>
            <td class="${result.hasFieldErrors('username') ? 'backgroundRedError' : ''}">
                <form:input type="text" path="username" disabled="true" value="${maxId}"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td class="${result.hasFieldErrors('username') ? 'backgroundRedError' : ''}">
                <spring:message code="${result.getFieldError('username').defaultMessage}"/>
            </td>
        </tr>
        <tr>
            <td class="formTableNameCell">
                <spring:message code="user.password"/>
            </td>
            <td class="${result.hasFieldErrors('password') ? 'backgroundRedError' : ''}">
                <form:input type="password" path="password"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td class="${result.hasFieldErrors('password') ? 'backgroundRedError' : ''}">
                <spring:message code="${result.getFieldError('password').defaultMessage}"/>
            </td>
        </tr>
    </table>
    <spring:message code="add" var="add"/>
    <input type="submit" value="${add}"/>
</form:form>
<script src="${pageContext.request.contextPath}/js/addForm.js"></script>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

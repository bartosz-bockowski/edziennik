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
<jsp:include page="../layout/header.jsp"/>
<a href="/admin/student/list"><spring:message code="student.list"/></a>
<h1><spring:message code="student.add"/></h1>
<form:form action="/admin/student/add" method="post" modelAttribute="student">
    <table class="formTable centerBlock">
        <tr>
            <td class="formTableNameCell">
                <spring:message code="student.firstName"/>
            </td>
            <td class="${result.hasFieldErrors('firstName') ? 'formTableErrorCell' : ''}">
                <form:input type="text" path="firstName"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td class="${result.hasFieldErrors('firstName') ? 'formTableErrorCell' : ''}">
                <spring:message code="${result.getFieldError('firstName').defaultMessage}"/>
            </td>
        </tr>
        <tr>
            <td class="formTableNameCell">
                <spring:message code="student.lastName"/>
            </td>
            <td class="${result.hasFieldErrors('lastName') ? 'formTableErrorCell' : ''}">
                <form:input type="text" path="lastName"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td class="${result.hasFieldErrors('lastName') ? 'formTableErrorCell' : ''}">
                <spring:message code="${result.getFieldError('lastName').defaultMessage}"/>
            </td>
        </tr>
    </table>
    <spring:message code="add" var="add"/>
    <input type="submit" value="${add}"/>
</form:form>
<script src="${pageContext.request.contextPath}/js/addForm.js"></script>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

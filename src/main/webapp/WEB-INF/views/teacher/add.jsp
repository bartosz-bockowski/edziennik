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
<a href="/admin/teacher/list"><spring:message code="teacher.list"/></a>
<h1><spring:message code="teacher.add"/></h1>
<form:form action="/admin/teacher/add" method="post" modelAttribute="teacher">
    <table class="formTable centerBlock">
        <tr>
            <td class="formTableNameCell">
                <spring:message code="teacher.firstName"/>
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
                <spring:message code="teacher.lastName"/>
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
    <button type="submit"><spring:message code="add"/></button>
</form:form>
<script src="${pageContext.request.contextPath}/js/addForm.js"></script>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

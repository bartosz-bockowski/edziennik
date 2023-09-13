<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 13.09.2023
  Time: 23:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<spring:message code="lessonHour.adding"/>
<form:form method="post" modelAttribute="lessonHour">
    <table class="formTable centerBlock">
        <tr>
            <td class="formTableNameCell">
                <spring:message code="lessonHour.start"/>
            </td>
            <td>
                <form:input type="time" path="start"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td class="${result.hasFieldErrors('start') ? 'backgroundRedError' : ''}">
                <spring:message code="${result.getFieldError('start').defaultMessage}"/>
            </td>
        </tr>
        <tr>
            <td class="formTableNameCell">
                <spring:message code="lessonHour.end"/>
            </td>
            <td>
                <form:input type="time" path="end"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td class="${result.hasFieldErrors('end') ? 'backgroundRedError' : ''}">
                <spring:message code="${result.getFieldError('end').defaultMessage}"/>
            </td>
        </tr>
    </table>
    <spring:message code="add" var="add"/>
    <input type="submit" value="${add}"/>
</form:form>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

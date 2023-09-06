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
<a href="/admin/subject/list"><spring:message code="subject.list"/></a>
<h1><spring:message code="subject.add"/></h1>
<form:form action="/admin/subject/add" method="post" modelAttribute="subject">
    <table class="formTable centerBlock">
        <tr>
            <td class="formTableNameCell">
                <spring:message code="subject.name"/>
            </td>
            <td class="${result.hasFieldErrors('name') ? 'formTableErrorCell' : ''}">
                <form:input type="text" path="name"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td class="${result.hasFieldErrors('name') ? 'formTableErrorCell' : ''}">
                <spring:message code="${result.getFieldError('name').defaultMessage}"/>
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

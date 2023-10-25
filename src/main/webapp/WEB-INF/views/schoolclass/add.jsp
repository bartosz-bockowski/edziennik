<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 29.08.2023
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<a href="/admin/schoolclass/list"><spring:message code="schoolClass.list"/></a>
<h1><spring:message code="schoolClass.add"/></h1>
<form:form action="/admin/schoolclass/add" method="post" modelAttribute="schoolclass">
    <table class="formTable centerBlock">
        <tr>
            <td class="formTableNameCell">
                <spring:message code="schoolClass.name"/>
            </td>
            <td>
                <form:input type="text" path="name"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td class="${result.hasFieldErrors('name') ? 'backgroundRedError' : ''}">
                <spring:message code="${result.getFieldError('name').defaultMessage}"/>
            </td>
        </tr>
    </table>
    <button type="submit"><spring:message code="add"/></button>
</form:form>
<script src="${pageContext.request.contextPath}/js/addForm.js"></script>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

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
<jsp:include page="../../layout/header.jsp"/>
<a href="/admin/role/list"><spring:message code="role.list"/></a>
<h1><spring:message code="role.add"/></h1>
<form:form action="/admin/role/add" method="post" modelAttribute="role">
    <table class="formTable centerBlock">
        <tr>
            <td class="formTableNameCell">
                <spring:message code="role.name"/>
            </td>
            <td>
                <form:input type="text" path="name"/>
            </td>
        </tr>
        <tr class="formTableErrorRow">
            <td></td>
            <td class="formTableErrorCell"><spring:message code="${result.getFieldError('name').defaultMessage}"/></td>
        </tr>
    </table>
    <br>
    <spring:message code="add" var="add"/>
    <input type="submit" value="${add}"/>
    <spring:message code="login" var="loginText"/>
</form:form>
<script src="${pageContext.request.contextPath}/js/addForm.js"></script>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

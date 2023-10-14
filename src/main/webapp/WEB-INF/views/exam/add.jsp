<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 10.10.2023
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<form:form action="/exam/add" method="post" modelAttribute="exam">
    <form:input type="hidden" path="lesson"/>
    <input type="hidden" value="${date}" name="date"/>
    <input type="hidden" value="${teacherId}" name="teacherId"/>
    <table class="formTable centerBlock">
        <tr>
            <td class="formTableNameCell">
                <spring:message code="exam.name"/>
            </td>
            <td>
                <form:input type="text" path="name"/>
            </td>
        </tr>
    </table>
    <spring:message code="add" var="add"/>
    <input type="submit" value="${add}"/>
</form:form>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

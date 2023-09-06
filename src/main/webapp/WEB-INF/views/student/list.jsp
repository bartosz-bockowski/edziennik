<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 30.08.2023
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<h1><spring:message code="student.list"/></h1>
<a href="/admin/student/add"><spring:message code="student.add"/></a>
<br/><br/>
<table class="centerBlock">
    <thead>
    <th><spring:message code="student.id"/></th>
    <th><spring:message code="student.fullName"/></th>
    <th><spring:message code="student.schoolClass"/></th>
    <th><spring:message code="student.active"/></th>
    <th><spring:message code="actions"/></th>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="student">
    <tr>
        <td>${student.id}</td>
        <td>${student.getFullName()}</td>
        <spring:message code="student.nullClass" var="nullClass"/>
        <td>${student.schoolClass.name != null ? student.schoolClass.name : nullClass}</td>
        <td><spring:message code="${student.active}"/></td>
        <td>
            <a href="/admin/student/${student.id}/details"><spring:message code="student.details"/></a>
            <spring:message code="student.switch.confirm" var="confirm"/>
            <spring:message code="activate" var="activate"/>
            <spring:message code="disactivate" var="disactivate"/>
            <a msg="${confirm}" class="confirm" href="/admin/student/${student.id}/switch"><spring:message code="${student.active ? disactivate : activate}"/></a>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

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
<h1><spring:message code="teacher.list"/></h1>
<a href="/admin/teacher/add"><spring:message code="teacher.add"/></a>
<table class="centerBlock mainTable basicListTable">
    <thead>
    <th><spring:message code="teacher.id"/></th>
    <th><spring:message code="teacher.fullName"/></th>
    <th><spring:message code="teacher.status"/></th>
    <th><spring:message code="actions"/></th>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="teacher">
    <tr>
        <td>${teacher.id}</td>
        <td>${teacher.getFullName()}</td>
        <td><spring:message code="${teacher.active}"/></td>
        <td>
            <a href="/admin/teacher/${teacher.id}/details"><spring:message code="teacher.details"/></a>
            <spring:message code="teacher.switch.confirm" var="confirm"/>
            <spring:message code="activate" var="activate"/>
            <spring:message code="disactivate" var="disactivate"/>
            <a class="confirm" msg="${confirm}" href="/admin/teacher/${teacher.id}/switch"><spring:message code="${teacher.active ? disactivate : activate}"/></a>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

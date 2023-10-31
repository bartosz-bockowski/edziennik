<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 13.09.2023
  Time: 23:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<h1><spring:message code="lessonHour.list"/></h1>
<a href="/admin/lessonHour/add"><spring:message code="lessonHour.add"/></a>
<table class="table table-striped table-bordered">
    <thead>
        <th><spring:message code="lessonHour.id"/></th>
        <th><spring:message code="lessonHour.start"/></th>
        <th><spring:message code="lessonHour.end"/></th>
        <th><spring:message code="lessonHour.active"/></th>
        <th><spring:message code="actions"/></th>
    </thead>
    <tbody>
<c:forEach items="${hours}" var="hour">
    <tr>
        <td>${hour.id}</td>
        <td>${hour.start}</td>
        <td>${hour.end}</td>
        <td><spring:message code="${hour.active}"/></td>
        <td></td>
    </tr>
</c:forEach>
    </tbody>
</table>

<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 04.09.2023
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<h1><spring:message code="classRoom.list"/></h1>
<a href="/admin/classRoom/add"><spring:message code="classRoom.add"/></a>
<table class="table table-striped table-bordered">
    <thead>
    <th><spring:message code="classRoom.id"/></th>
    <th><spring:message code="classRoom.name"/></th>
    <th><spring:message code="classRoom.status"/></th>
    <th><spring:message code="actions"/></th>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="classRoom">
        <tr>
            <td>${classRoom.id}</td>
            <td>${classRoom.name}</td>
            <td><spring:message code="${classRoom.active}"/></td>
            <td></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

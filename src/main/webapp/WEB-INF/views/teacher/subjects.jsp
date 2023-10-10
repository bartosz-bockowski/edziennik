<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 10.10.2023
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<h1><spring:message code="teacher.subjects"/></h1>
<table class="centerBlock mainTable basicListTable">
    <thead>
    <tr>
        <th><spring:message code="subject.id"/></th>
        <th><spring:message code="subject.name"/></th>
        <th><spring:message code="subject.active"/></th>
        <th><spring:message code="actions"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${teacher.subjects}" var="subject">
    <tr>
        <td>${subject.id}</td>
        <td>${subject.name}</td>
        <td><spring:message code="${subject.active}"/></td>
        <td>
            <a href="/subject/${subject.id}/schoolClasses"><spring:message code="schoolClasses"/></a>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

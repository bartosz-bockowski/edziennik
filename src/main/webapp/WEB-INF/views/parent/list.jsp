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
<h1><spring:message code="parent.list"/></h1>
<a href="/admin/parent/add"><spring:message code="parent.add"/></a>
<table class="centerBlock mainTable basicListTable">
    <thead>
    <th><spring:message code="parent.id"/></th>
    <th><spring:message code="parent.fullName"/></th>
    <th><spring:message code="parent.active"/></th>
    <th><spring:message code="actions"/></th>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="parent">
    <tr>
        <td>${parent.id}</td>
        <td>${parent.getFullName()}</td>
        <td><spring:message code="${parent.active}"/></td>
        <td>
            <a href="/admin/parent/${parent.id}/details"><spring:message code="parent.details"/></a>
            <a class="confirm" href="/admin/parent/${parent.id}/switch">
                <spring:message code="${parent.active ? 'disactivate' : 'activate'}"/>
                <p class="msg" hidden><spring:message code="parent.switch.confirm"/></p>
            </a>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

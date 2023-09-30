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
<jsp:include page="../../layout/header.jsp"/>
<h1><spring:message code="user.list"/></h1>
<spring:message code="user.add.confirm" var="userConfirm"/>
<a class="confirm" msg="${userConfirm}" href="/admin/user/add"><spring:message code="user.add"/></a>
<table class="centerBlock mainTable basicListTable">
    <thead>
    <th><spring:message code="user.id"/></th>
    <th><spring:message code="user.username"/></th>
    <th><spring:message code="user.status"/></th>
    <th><spring:message code="actions"/></th>
    </thead>
    <tbody>
<c:forEach items="${page.content}" var="user">
    <tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td><spring:message code="${user.active}"/></td>
        <td>
            <a href="/admin/user/${user.id}/details"><spring:message code="user.details"/></a>
            <c:if test="${user.username != 'admin'}">
                <spring:message code="user.switch.confirm" var="confirm"/>
                <spring:message code="activate" var="activate"/>
                <spring:message code="disactivate" var="disactivate"/>
                <a msg="${confirm}" class="confirm" href="/admin/user/${user.id}/switch"><spring:message code="${user.active ? disactivate : activate}"/></a>
            </c:if>
        </td>
    </tr>
    </tbody>
</c:forEach>
</table>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

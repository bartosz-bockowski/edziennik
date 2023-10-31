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
<a class="confirm" href="/admin/user/add">
    <spring:message code="user.add"/>
    <p class="msg"><spring:message code="user.add.confirm"/></p>
</a>
<table class="table table-striped table-bordered" id="datatable">
    <thead>
    <tr>
        <th><spring:message code="user.id"/></th>
        <th><spring:message code="user.username"/></th>
        <th><spring:message code="user.status"/></th>
        <th><spring:message code="actions"/></th>
    </tr>
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
                    <a class="confirm" href="/admin/user/${user.id}/switch">
                        <spring:message code="${user.active ? 'disactivate' : 'activate'}"/>
                        <p class="msg"><spring:message code="user.switch.confirm"/></p>
                    </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

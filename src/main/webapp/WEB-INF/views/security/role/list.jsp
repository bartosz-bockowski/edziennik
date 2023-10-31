<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../../layout/header.jsp"/>
<h1><spring:message code="role.list"/></h1>
<a href="/admin/role/add"><spring:message code="role.add"/></a>
<table class="table table-striped table-bordered basicListTable">
    <thead>
    <th><spring:message code="role.id"/></th>
    <th><spring:message code="role.name"/></th>
    <th><spring:message code="role.active"/></th>
    <th><spring:message code="actions"/></th>
    </thead>
    <tbody>
<c:forEach items="${page.content}" var="role">
    <tr>
        <td>${role.id}</td>
        <td>${role.name}</td>
        <td><spring:message code="${role.active}"/></td>
        <td>
            <a href="/admin/role/${role.id}/details"><spring:message code="role.details"/></a>
            <a class="confirm" href="/admin/role/${role.id}/switch">
                <spring:message code="${role.active ? 'disactivate' : 'activate'}"/>
                <p class="msg"><spring:message code="role.switch.confirm"/></p>
            </a>
        </td>
    </tr>
</c:forEach>
    </tbody>
</table>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

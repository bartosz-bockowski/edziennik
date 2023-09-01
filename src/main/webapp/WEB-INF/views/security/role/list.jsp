<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../../layout/header.jsp"/>
<h1><spring:message code="role.list"/></h1>
<a href="/admin/role/add"><spring:message code="role.add"/></a>
<br/><br/>
<table class="centerBlock">
    <thead>
    <th><spring:message code="role.name"/></th>
    <th><spring:message code="role.active"/></th>
    <th><spring:message code="actions"/></th>
    </thead>
    <tbody>
<c:forEach items="${roles}" var="role">
    <tr>
        <td>${role.name}</td>
        <td>${role.active}</td>
        <td></td>
    </tr>
</c:forEach>
    </tbody>
</table>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

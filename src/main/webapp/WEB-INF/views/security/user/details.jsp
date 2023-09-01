<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 30.08.2023
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../../layout/header.jsp"/>
<a href="/admin/user/list"><spring:message code="user.list"/></a>
<h1><spring:message code="user.userDetails"/></h1>
<div><spring:message code="user.username"/>: <b>${user.username}</b></div>
<div><spring:message code="user.roles"/>:</div>
<form method="get" action="/admin/user/${user.id}/setRoles">
<c:forEach items="${roles}" var="role" varStatus="loopStatus">
    <input type="checkbox" value="${role.id}" id="role${role.id}" name="roles" <c:if test="${user.roles.contains(role)}">checked="checked"</c:if>>
    <label for="role${role.id}">${role.name}</label><br>
</c:forEach>
    <spring:message code="user.saveRoles" var="save"/>
    <input type="submit" value="${save}"/>
</form>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

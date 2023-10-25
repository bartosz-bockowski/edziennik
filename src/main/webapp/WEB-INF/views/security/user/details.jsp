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
        <input type="checkbox" value="${role.id}" id="role${role.id}" name="roles"
               <c:if test="${user.roles.contains(role)}">checked="checked"</c:if>>
        <label for="role${role.id}">${role.name}</label><br>
    </c:forEach>
    <button type="submit"><spring:message code="user.saveRoles"/></button>
</form>
<c:if test="${user.student != null}">
    <h1><spring:message code="user.student"/></h1>
    ${user.student.fullNameWithId}
</c:if>
<c:if test="${user.teacher != null}">
    <h1><spring:message code="user.teacher"/></h1>
    ${user.teacher.fullNameWithId}
</c:if>
<c:if test="${user.parent != null}">
    <h1><spring:message code="user.parent"/></h1>
    ${user.parent.fullNameWithId}
</c:if>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

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
<a href="/admin/teacher/list"><spring:message code="teacher.list"/></a>
<h1><spring:message code="teacher.teacherDetails"/></h1>
<h3>${teacher.getFullName()}</h3>
<h3><spring:message code="teacher.subjects"/>:</h3>
<c:forEach items="${subjects}" var="subject">
    <div>${subject.name}</div>
</c:forEach>
<h3><spring:message code="teacher.users"/></h3>
<form method="get" action="/admin/teacher/${teacher.id}/addUser">
    <select class="selectpicker" data-live-search="true" name="user">
        <c:forEach items="${users}" var="user">
            <c:if test="${!teacher.users.contains(user)}">
                <option value="${user.id}">${user.username} (ID: ${user.id})</option>
            </c:if>
        </c:forEach>
    </select>
    <br/>
    <button type="submit" class="selectpickerButton"><spring:message code="teacher.setUser"/></button>
</form>
<c:if test="${teacher.users.size() > 0}">
    <spring:message code="teacher.confirmRemoveUser" var="confirmRemoveUser"/>
    <c:forEach items="${teacher.users}" var="user">
        ${user.username} (ID: ${user.id}) <a class="confirm" msg="${confirmRemoveUser} (${user.username} ID: ${user.id})" href="/admin/teacher/${teacher.id}/removeUser/${user.id}">
        <spring:message code="teacher.removeUser"/>
    </a><br/>
    </c:forEach>
</c:if>
<c:if test="${teacher.users.size() == 0}">
    <spring:message code="teacher.noUsers"/>
</c:if>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

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
<a href="/admin/student/list"><spring:message code="student.list"/></a>
<h1><spring:message code="student.studentDetails"/></h1>
<h3>${student.getFullName()}</h3>
<spring:message code="student.nullClass" var="nullClass"/>
<spring:message code="student.schoolClass"/>:
<b>${student.schoolClass.name != null ? student.schoolClass.name : nullClass}</b>
<form method="get" action="/admin/student/${student.id}/setClass">
    <select class="selectpicker" data-live-search="true" name="classId">
        <c:forEach items="${schoolClasses}" var="schoolClass">
            <option ${(student.schoolClass != null && student.schoolClass.id == schoolClass.id) ? 'selected' : ''} value="${schoolClass.id}">${schoolClass.name} (ID: ${schoolClass.id})</option>
        </c:forEach>
    </select>
    <br/>
    <button type="submit" class="selectpickerButton"><spring:message code="student.setClass"/></button>
</form>
<br/>
<h3><spring:message code="student.users"/></h3>
<form method="get" action="/admin/student/${student.id}/addUser">
    <select class="selectpicker" data-live-search="true" name="user">
        <c:forEach items="${users}" var="user">
            <c:if test="${!student.users.contains(user)}">
                <option value="${user.id}">${user.username} (ID: ${user.id})</option>
            </c:if>
        </c:forEach>
    </select>
    <br/>
    <button type="submit" class="selectpickerButton"><spring:message code="student.setUser"/></button>
</form>
<c:if test="${student.users.size() > 0}">
    <spring:message code="student.confirmRemoveUser" var="confirmRemoveUser"/>
    <c:forEach items="${student.users}" var="user">
        ${user.username} (ID: ${user.id}) <a class="confirm" msg="${confirmRemoveUser} (${user.username} ID: ${user.id})" href="/admin/student/${student.id}/removeUser/${user.id}">
        <spring:message code="student.removeUser"/>
    </a><br/>
    </c:forEach>
</c:if>
<c:if test="${student.users.size() == 0}">
    <spring:message code="student.noUsers"/>
</c:if>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

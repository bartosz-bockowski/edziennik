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
<jsp:include page="../layout/header.jsp"/>
<a href="/admin/parent/list"><spring:message code="parent.list"/></a>
<h1><spring:message code="parent.parentDetails"/></h1>
<div><spring:message code="parent.fullName"/>: <b>${parent.getFullName()}</b></div>
<div><spring:message code="parent.students"/>:</div>
<spring:message code="parent.confirmRemoveStudent" var="conRemStu"/>
<c:if test="${parent.students.size() == 0}">
    <div><spring:message code="parent.noStudents"/></div>
</c:if>
<c:forEach items="${parent.students}" var="student">
    <div><span>${student.getFullName()}</span> <a class="confirm" msg="${conRemStu} (${student.getFullName()} ID: ${student.id})" href="/admin/parent/${parent.id}/removeStudent/${student.id}"><spring:message code="parent.removeStudent"/></a></div>
</c:forEach>
<div>
    <h2><spring:message code="parent.addStudent"/></h2>
    <form id="addStudentForm" action="/admin/parent/${parent.id}/addStudent" method="get">
        <select class="selectpicker" data-live-search="true" name="studentId">
            <c:forEach items="${students}" var="student">
                <c:if test="${!parent.students.contains(student)}">
                    <option value="${student.id}">${student.getFullName()} (ID: ${student.id})</option>
                </c:if>
            </c:forEach>
        </select>
        <br/>
        <button type="submit" class="selectpickerButton"><spring:message code="parent.addUser"/></button>
    </form>
</div>
<h3><spring:message code="parent.users"/></h3>
<form method="get" action="/admin/parent/${parent.id}/addUser">
    <select class="selectpicker" data-live-search="true" name="user">
        <c:forEach items="${users}" var="user">
            <c:if test="${!parent.users.contains(user)}">
                <option value="${user.id}">${user.username} (ID: ${user.id})</option>
            </c:if>
        </c:forEach>
    </select>
    <br/>
    <button type="submit" class="selectpickerButton"><spring:message code="parent.setUser"/></button>
</form>
<c:if test="${parent.users.size() > 0}">
    <spring:message code="parent.confirmRemoveUser" var="confirmRemoveUser"/>
    <c:forEach items="${parent.users}" var="user">
        ${user.username} (ID: ${user.id}) <a class="confirm" msg="${confirmRemoveUser} (${user.username} ID: ${user.id})" href="/admin/parent/${parent.id}/removeUser/${user.id}">
        <spring:message code="parent.removeUser"/>
    </a><br/>
    </c:forEach>
</c:if>
<c:if test="${parent.users.size() == 0}">
    <spring:message code="parent.noUsers"/>
</c:if>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

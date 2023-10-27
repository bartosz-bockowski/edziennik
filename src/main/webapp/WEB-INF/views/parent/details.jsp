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

<c:if test="${parent.students.size() == 0}">
    <div><spring:message code="parent.noStudents"/></div>
</c:if>
<c:forEach items="${parent.students}" var="student">
    <div><span>${student.getFullName()}</span>
        <a class="confirm" href="/admin/parent/${parent.id}/removeStudent/${student.id}">
            <spring:message code="parent.removeStudent"/>
            <p class="msg" hidden><spring:message code="parent.confirmRemoveStudent"/> (${student.getFullName()} ID: ${student.id})</p>
        </a>
    </div>
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
<h3><spring:message code="parent.user"/></h3>
<c:if test="${parent.user != null}">
    ${parent.user.username} (ID: ${parent.user.id}) <a class="confirm" href="/admin/parent/${parent.id}/removeUser">
    <spring:message code="parent.removeUser"/>
    <p class="msg" hidden><spring:message code="parent.confirmRemoveUser"/></p>
</a><br/>
</c:if>
<c:if test="${parent.user == null}">
    <div><spring:message code="parent.noUser"/></div>
    <div><spring:message code="user.add"/></div>
    <form method="get" action="/admin/parent/${parent.id}/setUser">
        <select data-live-search="true" name="user">
            <c:forEach items="${freeUsers}" var="user">
                <option value="${user.id}">${user.username} (ID: ${user.id})</option>
            </c:forEach>
        </select>
        <br/>
        <button type="submit" class="selectpickerButton"><spring:message code="parent.setUser"/></button>
    </form>
</c:if>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

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
<h3>${student.fullName}</h3>
<spring:message code="student.schoolClass"/>:
<b>
    <c:if test="${student.schoolClass != null}">
        ${student.schoolClass.name}
    </c:if>
    <c:if test="${student.schoolClass == null}">
        <spring:message code="student.nullClass"/>
    </c:if>
</b>
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
<h3><spring:message code="student.user"/></h3>
<c:if test="${student.user != null}">
        ${student.user.username} (ID: ${student.user.id}) <a class="confirm" href="/admin/student/${student.id}/removeUser">
        <spring:message code="student.removeUser"/>
    <p class="msg" hidden>
        <spring:message code="student.confirmRemoveUser"/>
    </p>
    </a><br/>
</c:if>
<c:if test="${student.user == null}">
    <div><spring:message code="student.noUser"/></div>
    <div><spring:message code="user.add"/></div>
    <form method="get" action="/admin/student/${student.id}/setUser">
        <select class="selectpicker" data-live-search="true" name="user">
            <c:forEach items="${freeUsers}" var="user">
                <option value="${user.id}">${user.username} (ID: ${user.id})</option>
            </c:forEach>
        </select>
        <br/>
        <button type="submit" class="selectpickerButton"><spring:message code="student.setUser"/></button>
    </form>
</c:if>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

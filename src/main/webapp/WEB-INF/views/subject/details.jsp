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
<a href="/admin/subject/list"><spring:message code="subject.list"/></a>
<h1><spring:message code="subject.subjectDetails"/></h1>
<div><spring:message code="subject.name"/>: <b>${subject.name}</b></div>
<!-- teachers -->
<div>
    <h2><spring:message code="subject.addTeacher"/></h2>
    <form id="addTeacherForm" action="/admin/subject/${subject.id}/addTeacher" method="get">
        <select class="selectpicker" data-live-search="true" name="teacherId">
            <c:forEach items="${teachers}" var="teacher">
                <c:if test="${!subject.teachers.contains(teacher)}">
                    <option value="${teacher.id}">${teacher.getFullName()} (ID: ${teacher.id})</option>
                </c:if>
            </c:forEach>
        </select>
        <br/>
        <button type="submit" class="selectpickerButton"><spring:message code="subject.addT"/></button>
    </form>
    <div><spring:message code="subject.teachers"/>:</div>
    <spring:message code="subject.confirmRemoveTeacher" var="conRemTea"/>
    <c:if test="${subject.teachers.size() == 0}">
        <div><spring:message code="subject.noTeachers"/></div>
    </c:if>
    <c:forEach items="${subject.teachers}" var="teacher">
        <div><span>${teacher.getFullName()}</span> <a msg="${conRemTea} (${teacher.getFullName()} ID: ${teacher.id})" class="confirm" href="/admin/subject/${subject.id}/removeTeacher/${teacher.id}"><spring:message code="subject.removeTeacher"/></a></div>
    </c:forEach>
</div>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

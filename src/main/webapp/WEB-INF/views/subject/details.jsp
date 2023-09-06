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
<script src="${pageContext.request.contextPath}/js/subject/subjectDetails.js"></script>
<!-- teachers -->
<div><spring:message code="subject.teachers"/>:</div>
<spring:message code="subject.confirmRemoveTeacher" var="conRemTea"/>
<input type="hidden" id="confirmRemoveDetails" value="${conRemTea}"/>
<c:if test="${subject.teachers.size() == 0}">
    <div><spring:message code="subject.noTeachers"/></div>
</c:if>
<c:forEach items="${subject.teachers}" var="teacher">
    <div><span>${teacher.getFullName()}</span> <a class="confirmRemoveDetails" href="/admin/subject/${subject.id}/removeTeacher/${teacher.id}"><spring:message code="subject.removeTeacher"/></a></div>
</c:forEach>
<div>
    <h2><spring:message code="subject.addTeacher"/></h2>
    <form id="addTeacherForm" action="/admin/subject/${subject.id}/addTeacher" method="get">
        <spring:message code="form.cantBeEmpty" var="cantBeEmpty"/>
        <input type="hidden" id="cantBeEmpty" value="${cantBeEmpty}"/>
        <spring:message code="subject.addTeacher.nonExistsentTeacher" var="nonExistent"/>
        <input type="hidden" id="nonExistentTeacherMsg" value="${nonExistent}"/>
        <spring:message code="subject.insertTeacherId"/>
        <input type="number" name="teacherId" id="teacherId" required="required"/>
        <br/>
        <spring:message code="add" var="add"/>
        <input class="submit" type="submit" value="${add}"/>
    </form>
</div>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

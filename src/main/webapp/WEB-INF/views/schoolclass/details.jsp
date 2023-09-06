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
<a href="/admin/schoolclass/list"><spring:message code="schoolClass.list"/></a>
<h1><spring:message code="schoolClass.schoolClassDetails"/></h1>
<div><spring:message code="schoolClass.name"/>: <b>${schoolClass.name}</b></div>
<script src="${pageContext.request.contextPath}/js/schoolClass/schoolClassDetails.js"></script>
<!-- students -->
<div><spring:message code="schoolClass.students"/>:</div>
<spring:message code="schoolClass.studentAlreadyHasClass" var="stuAlrHasCla"/>
<input type="hidden" id="studentAlreadyHasClass" value="${stuAlrHasCla}"/>
<spring:message code="schoolClass.confirmRemoveStudent" var="conRemStu"/>
<input type="hidden" id="confirmRemoveStudent" value="${conRemStu}"/>
<c:if test="${students.size() == 0}">
    <div><spring:message code="schoolClass.noStudents"/></div>
</c:if>
<c:forEach items="${students}" var="student">
    <div><span>${student.getFullName()}</span> <a class="confirmRemoveStudent" href="/admin/schoolclass/${schoolClass.id}/removeStudent/${student.id}"><spring:message code="schoolClass.removeStudent"/></a></div>
</c:forEach>
<div>
    <h2><spring:message code="schoolClass.addStudent"/></h2>
    <form id="addStudentForm" action="/admin/schoolclass/${schoolClass.id}/addStudent" method="get">
        <spring:message code="form.cantBeEmpty" var="cantBeEmpty"/>
        <input type="hidden" id="cantBeEmpty" value="${cantBeEmpty}"/>
        <spring:message code="schoolClass.addStudent.nonExistentStudent" var="nonExistent"/>
        <input type="hidden" id="nonExistentStudentMsg" value="${nonExistent}"/>
        <spring:message code="schoolClass.insertStudentId"/>
        <input type="number" name="studentId" id="studentId" required="required"/>
        <br/>
        <spring:message code="add" var="add"/>
        <input class="submit" type="submit" value="${add}"/>
    </form>
</div>
<!-- subjects -->
<div><spring:message code="schoolClass.subjects"/>:</div>
<spring:message code="schoolClass.confirmRemoveSubject" var="conRemSub"/>
<input type="hidden" id="confirmRemoveSubject" value="${conRemSub}"/>
<c:if test="${schoolClass.subjects.size() == 0}">
    <div><spring:message code="schoolClass.noSubjects"/></div>
</c:if>
<c:forEach items="${schoolClass.subjects}" var="subject">
    <div><span>${subject.name}</span> <a class="confirmRemoveSubject" href="/admin/schoolclass/${schoolClass.id}/removeSubject/${subject.id}"><spring:message code="schoolClass.removeSubject"/></a></div>
</c:forEach>
<div>
    <h2><spring:message code="schoolClass.addSubject"/></h2>
    <form id="addSubjectForm" action="/admin/schoolclass/${schoolClass.id}/addSubject" method="get">
        <spring:message code="form.cantBeEmpty" var="cantBeEmpty"/>
        <input type="hidden" id="cantBeEmpty" value="${cantBeEmpty}"/>
        <spring:message code="schoolClass.addSubject.nonExistentSubject" var="nonExistent"/>
        <input type="hidden" id="nonExistentSubjectMsg" value="${nonExistent}"/>
        <spring:message code="schoolClass.insertSubjectId"/>
        <input type="number" name="subjectId" id="subjectId" required="required"/>
        <br/>
        <spring:message code="add" var="add"/>
        <input class="submitSubject" type="submit" value="${add}"/>
    </form>
</div>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

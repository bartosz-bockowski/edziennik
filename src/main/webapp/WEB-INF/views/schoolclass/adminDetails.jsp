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
<br/>
<spring:message code="schoolClass.supervisingTeachers"/>:
<c:forEach items="${schoolClass.supervisingTeachers}" var="t">
    <div>${t.fullNameWithId}</div>
</c:forEach>
<br/>
<script src="${pageContext.request.contextPath}/js/schoolClass/schoolClassDetails.js"></script>
<!-- students -->
<div><spring:message code="schoolClass.students"/>:</div>
<spring:message code="schoolClass.confirmRemoveStudent" var="conRemStu"/>
<c:if test="${students.size() == 0}">
    <div><spring:message code="schoolClass.noStudents"/></div>
</c:if>
<c:forEach items="${students}" var="student">
    <div><span>${student.getFullName()}</span> <a msg="${conRemStu} (${student.getFullName()} ID: ${student.id})" class="confirm" href="/admin/schoolclass/${schoolClass.id}/removeStudent/${student.id}"><spring:message code="schoolClass.removeStudent"/></a></div>
</c:forEach>
<div>
    <h2><spring:message code="schoolClass.addStudent"/></h2>
    <form id="addStudentForm" action="/admin/schoolclass/${schoolClass.id}/addStudent" method="get">
        <select class="selectpicker" data-live-search="true" name="studentId">
            <c:forEach items="${allStudents}" var="allStudent">
                <c:if test="${!students.contains(allStudent)}">
                    <option value="${allStudent.id}">${allStudent.getFullName()} (ID: ${allStudent.id})</option>
                </c:if>
            </c:forEach>
        </select>
        <br/>
        <button type="submit" class="selectpickerButton"><spring:message code="schoolClass.setUser"/></button>
    </form>
</div>
<!-- subjects -->
<div><spring:message code="schoolClass.subjects"/>:</div>
<spring:message code="schoolClass.confirmRemoveSubject" var="conRemSub"/>
<c:if test="${schoolClass.subjects.size() == 0}">
    <div><spring:message code="schoolClass.noSubjects"/></div>
</c:if>
<c:forEach items="${schoolClass.subjects}" var="subject">
    <div><span>${subject.name}</span>
        <a class="confirm" msg="${conRemSub} (${subject.name} ID: ${subject.id})" href="/admin/schoolclass/${schoolClass.id}/removeSubject/${subject.id}"><spring:message code="schoolClass.removeSubject"/></a>
        <a href="/schoolclass/${schoolClass.id}/marks/${subject.id}"><spring:message code="schoolClass.marks"/></a>
    </div>
</c:forEach>
<div>
    <h2><spring:message code="schoolClass.addSubject"/></h2>
    <form id="addSubjectForm" action="/admin/schoolclass/${schoolClass.id}/addSubject" method="get">
        <select class="selectpicker" data-live-search="true" name="subjectId">
            <c:forEach items="${subjects}" var="subject">
                <c:if test="${!schoolClass.subjects.contains(subject)}">
                    <option value="${subject.id}">${subject.name} (ID: ${subject.id})</option>
                </c:if>
            </c:forEach>
        </select>
        <br/>
        <button type="submit" class="selectpickerButton"><spring:message code="schoolClass.saveSubject"/></button>
    </form>
</div>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

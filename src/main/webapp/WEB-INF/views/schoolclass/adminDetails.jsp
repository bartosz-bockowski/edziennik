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
<script src="${pageContext.request.contextPath}/js/schoolClass/schoolClassDetails.js"></script>
<a href="/admin/schoolclass/list"><spring:message code="schoolClass.list"/></a>
<h1><spring:message code="schoolClass.schoolClassDetails"/></h1>
<div><spring:message code="schoolClass.name"/>: <b>${schoolClass.name}</b></div>
<table class="container-fluid table-bordered text-center table-responsive-xl">
    <tr>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="schoolClass.supervisingTeachers"/></h5>
        </td>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="schoolClass.students"/></h5>
        </td>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="schoolClass.subjects"/></h5>
        </td>
    </tr>
    <tr>
        <td class="card-body">
            <c:forEach items="${schoolClass.supervisingTeachers}" var="t">
                <div>${t.fullNameWithId}</div>
            </c:forEach>
        </td>
        <td class="card-body">
            <c:if test="${schoolClass.students.size() == 0}">
                <div><spring:message code="schoolClass.noStudents"/></div>
            </c:if>
            <c:forEach items="${schoolClass.students}" var="student">
                <div>${student.fullNameWithId}
                    <a class="confirm" href="/admin/schoolclass/${schoolClass.id}/removeStudent/${student.id}">
                        <spring:message code="schoolClass.removeStudent"/>
                        <p class="msg"><spring:message code="schoolClass.confirmRemoveStudent"/> (${student.getFullName()}, ID: ${student.id})</p>
                    </a></div>
            </c:forEach>
        </td>
        <td class="card-body">
            <c:if test="${schoolClass.subjects.size() == 0}">
                <div><spring:message code="schoolClass.noSubjects"/></div>
            </c:if>
            <c:forEach items="${schoolClass.subjects}" var="subject">
                <div>${subject.name}
                    <a class="confirm" href="/admin/schoolclass/${schoolClass.id}/removeSubject/${subject.id}">
                        <spring:message code="schoolClass.removeSubject"/>
                        <p class="msg"><spring:message code="schoolClass.confirmRemoveSubject"/> (${subject.name} ID: ${subject.id})</p>
                    </a>
                    <a href="/schoolclass/${schoolClass.id}/marks/${subject.id}"><spring:message code="schoolClass.marks"/></a>
                </div>
            </c:forEach>
        </td>
    </tr>
    <tr>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="schoolClass.addSupervisingTeacher"/></h5>
        </td>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="schoolClass.addStudent"/></h5>
        </td>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="schoolClass.addSubject"/></h5>
        </td>
    </tr>
    <tr>
        <td class="card-body">
            <form id="addStudentForm" action="/admin/schoolclass/${schoolClass.id}/addStudent" method="get">
                <select class="selectpicker" data-live-search="true" name="studentId">
                    <c:forEach items="${allStudents}" var="allStudent">
                        <c:if test="${!schoolClass.students.contains(allStudent)}">
                            <option value="${allStudent.id}">${allStudent.fullNameWithId}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <br/>
                <button type="submit" class="btn btn-primary"><spring:message code="schoolClass.setUser"/></button>
            </form>
        </td>
        <td class="card-body">
            <form id="addStudentForm" action="/admin/schoolclass/${schoolClass.id}/addStudent" method="get">
                <select class="selectpicker" data-live-search="true" name="studentId">
                    <c:forEach items="${allStudents}" var="allStudent">
                        <c:if test="${!schoolClass.students.contains(allStudent)}">
                            <option value="${allStudent.id}">${allStudent.fullNameWithId}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <br/>
                <button type="submit" class="btn btn-primary"><spring:message code="schoolClass.setUser"/></button>
            </form>
        </td>
        <td class="card-body">
            <form id="addSubjectForm" action="/admin/schoolclass/${schoolClass.id}/addSubject" method="get">
                <select class="selectpicker" data-live-search="true" name="subjectId">
                    <c:forEach items="${subjects}" var="subject">
                        <c:if test="${!schoolClass.subjects.contains(subject)}">
                            <option value="${subject.id}">${subject.name} (ID: ${subject.id})</option>
                        </c:if>
                    </c:forEach>
                </select>
                <br/>
                <button type="submit" class="btn btn-primary"><spring:message code="schoolClass.saveSubject"/></button>
            </form>
        </td>
    </tr>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

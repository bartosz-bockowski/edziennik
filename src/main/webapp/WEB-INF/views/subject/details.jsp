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
<input type="hidden" value="${subject.id}" id="subjectId"/>
<input type="hidden" value="${subject.nameWithId}" id="subjectName"/>
<p id="removeTeacherConfirmText" hidden><spring:message code="subject.confirmRemoveTeacher"/></p>
<h1><spring:message code="subject.subjectDetails"/></h1>
<div><spring:message code="subject.name"/>: <b>${subject.name}</b></div>
<!-- teachers -->
<table class="container-fluid table-bordered text-center table-responsive-xl">
    <tr>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="subject.teachers"/></h5>
        </td>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="subject.addTeacher"/></h5>
        </td>
    </tr>
    <tr>
        <td class="card-body">
            <div id="subjectTeacherListNone" <c:if test="${subject.teachers.size() > 0}">hidden</c:if>><spring:message code="none"/></div>
            <div class="d-inline-block text-left">
                <div id="removeTeacherText" hidden><spring:message code="subject.removeTeacher"/></div>
                <ul id="subjectTeacherList">
                <c:forEach items="${subject.teachers}" var="teacher">
                    <li>${teacher.fullNameWithId} <a class="confirm" href="/admin/subject/${subject.id}/removeTeacher?teacherId=${teacher.id}" ajax="subjectRemoveTeacherA">
                        <spring:message code="subject.removeTeacher"/>
                        <p class="msg"><spring:message code="subject.confirmRemoveTeacher" arguments="${teacher.fullNameWithId},${subject.nameWithId}"/></p>
                    </a></li>
                </c:forEach>
                </ul>
            </div>
        </td>
        <td class="card-body">
            <form id="subjectAddTeacherForm" class="confirm d-inline-block" action="/admin/subject/${subject.id}/addTeacher" method="get" ajax="subjectAddTeacherForm">
                <p class="msg"><spring:message code="subject.confirmAddTeacher" arguments="${subject.nameWithId}"/></p>
                <select class="selectpicker" data-live-search="true" name="teacherId" required="required">
                    <c:forEach items="${teachers}" var="teacher">
                        <option value="${teacher.id}">${teacher.fullNameWithId}</option>
                    </c:forEach>
                </select>
                <br/>
                <button type="submit" class="btn btn-primary"><spring:message code="subject.addT"/></button>
            </form>
        </td>
    </tr>
    <tr>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="subject.schoolClassList"/></h5>
        </td>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="subject.addSchoolClass"/></h5>
        </td>
    </tr>
    <tr>
        <td class="card-body">
            <div id="subjectSchoolClassListNone" <c:if test="${subject.schoolClasses.size() > 0}">hidden</c:if>><spring:message code="none"/></div>
            <div class="d-inline-block text-left">
                <div id="removeSchoolClassText" hidden><spring:message code="subject.removeSchoolClass"/></div>
                <ul id="subjectSchoolClassList">
                <c:forEach items="${subject.schoolClasses}" var="schoolClass">
                    <li>${schoolClass.nameWithId} <a class="confirm" href="/admin/subject/${subject.id}/removeSchoolClass?schoolClassId=${schoolClass.id}" ajax="subjectRemoveSchoolClassA">
                        <spring:message code="subject.removeSchoolClass"/>
                        <p class="msg"><spring:message code="subject.confirmRemoveSchoolClass" arguments="${schoolClass.nameWithId},${subject.nameWithId}"/></p>
                    </a></li>
                </c:forEach>
                </ul>
            </div>
        </td>
        <td class="card-body">
            <form id="subjectAddSchoolClassForm" class="confirm d-inline-block" action="/admin/subject/${subject.id}/addSchoolClass" method="get" ajax="subjectAddSchoolClassForm">
                <p class="msg"><spring:message code="subject.confirmAddSchoolClass" arguments="${subject.nameWithId}"/></p>
                <select class="selectpicker" data-live-search="true" name="schoolClassId" required="required">
                    <c:forEach items="${schoolClasses}" var="schoolClass">
                        <option value="${schoolClass.id}">${schoolClass.nameWithId}</option>
                    </c:forEach>
                </select>
                <br/>
                <button type="submit" class="btn btn-primary"><spring:message code="subject.addSC"/></button>
            </form>
        </td>
    </tr>
</table>
<script src="${pageContext.request.contextPath}/js/ajax/subject/details.js"></script>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

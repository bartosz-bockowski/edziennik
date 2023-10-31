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
<table class="container-fluid table text-center table-responsive-xl">
    <tr>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="subject.teachers"/></h5>
        </td>
    </tr>
    <tr>
        <td class="card-body">
            <c:if test="${subject.teachers.size() == 0}">
                <div><spring:message code="subject.noTeachers"/></div>
            </c:if>
            <c:forEach items="${subject.teachers}" var="teacher">
                <div>${teacher.fullNameWithId} <a executed-by-js="true" href="/admin/subject/removeTeacher?subject=${subject.id}&teacher=${teacher.id}">
                    <spring:message code="subject.removeTeacher"/>
                    <p class="msg"><spring:message code="subject.confirmRemoveTeacher"/> (${teacher.fullNameWithId})</p>
                </a></div>
            </c:forEach>
            <div><spring:message code="subject.addTeacher"/></div>
            <form id="addTeacherForm" action="/admin/subject/addTeacher" executed-by-js="true" method="get">
                <p class="msg"><spring:message code="subject.confirmAddTeacher"/></p>
                <input type="hidden" name="subject" value="${subject.id}"/>
                <select class="selectpicker" data-live-search="true" name="teacher">
                    <c:forEach items="${teachers}" var="teacher">
                        <c:if test="${!subject.teachers.contains(teacher)}">
                            <option value="${teacher.id}">${teacher.getFullName()} (ID: ${teacher.id})</option>
                        </c:if>
                    </c:forEach>
                </select>
                <br/>
                <button type="submit" class="btn btn-primary"><spring:message code="subject.addT"/></button>
            </form>
        </td>
    </tr>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

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
            <c:if test="${subject.teachers.size() == 0}">
                <div><spring:message code="none"/></div>
            </c:if>
            <c:forEach items="${subject.teachers}" var="teacher">
                <div>${teacher.fullNameWithId} <a class="confirm" href="/admin/subject/${subject.id}/removeTeacher?teacherId=${teacher.id}">
                    <spring:message code="subject.removeTeacher"/>
                    <p class="msg"><spring:message code="subject.confirmRemoveTeacher" arguments="${teacher.fullNameWithId},${subject.nameWithId}"/></p>
                </a></div>
            </c:forEach>
        </td>
        <td class="card-body">
            <form class="confirm" action="/admin/subject/${subject.id}/addTeacher" method="get">
                <p class="msg"><spring:message code="subject.confirmAddTeacher" arguments="${subject.nameWithId}"/></p>
                <select class="selectpicker" data-live-search="true" name="teacherId">
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

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
<jsp:include page="../../layout/header.jsp"/>
<script src="${pageContext.request.contextPath}/js/schoolClass/schoolClassDetails.js"></script>
<a href="/admin/schoolclass/list"><spring:message code="schoolClass.list"/></a>
<h1><spring:message code="schoolClass.schoolClassDetails"/></h1>
<div><spring:message code="schoolClass.name"/>: <b>${schoolClass.name}</b></div>
<table class="container-fluid table-bordered text-center align-top table-responsive-xl">
    <tr>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="schoolClass.supervisingTeachers"/></h5>
        </td>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="schoolClass.students"/></h5>
        </td>
    </tr>
    <tr>
        <td class="card-body">
            <c:forEach items="${schoolClass.supervisingTeachers}" var="t">
                <div>${t.fullNameWithId}
                    <a class="confirm" href="/admin/schoolclass/${schoolClass.id}/removeSupervisingTeacher?teacherId=${t.id}">
                        <spring:message code="schoolClass.removeSupervisingTeacher"/>
                        <p class="msg"><spring:message code="schoolClass.confirmRemoveSupervisingTeacher" arguments="${t.fullNameWithId},${schoolClass.nameWithId}"/></p>
                    </a>
                </div>
            </c:forEach>
            <c:if test="${schoolClass.supervisingTeachers.size() == 0}">
                <spring:message code="none"/>
            </c:if>
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
    </tr>
    <tr>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="schoolClass.addSupervisingTeacher"/></h5>
        </td>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="schoolClass.addStudent"/></h5>
        </td>
    </tr>
    <tr>
        <td class="card-body">
            <form class="confirm" action="/admin/schoolclass/${schoolClass.id}/addSupervisingTeacher" method="get">
                <p class="msg"><spring:message code="schoolClass.confirmAddSupervisingTeacher" arguments="${schoolClass.nameWithId}"/></p>
                <select class="selectpicker" data-live-search="true" name="teacherId">
                    <c:forEach items="${teachers}" var="teacher">
                        <c:if test="${!schoolClass.supervisingTeachers.contains(teacher)}">
                            <option value="${teacher.id}">${teacher.fullNameWithId}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <br/>
                <button type="submit" class="btn btn-primary"><spring:message code="schoolClass.setUser"/></button>
            </form>
        </td>
        <td class="card-body">
            <form action="/admin/schoolclass/${schoolClass.id}/addStudent" method="get">
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
    </tr>
</table>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

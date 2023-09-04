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
<a href="/admin/parent/list"><spring:message code="parent.list"/></a>
<h1><spring:message code="parent.parentDetails"/></h1>
<div><spring:message code="parent.fullName"/>: <b>${parent.getFullName()}</b></div>
<div><spring:message code="parent.students"/>:</div>
<spring:message code="parent.confirmRemoveStudent" var="conRemStu"/>
<input type="hidden" id="confirmRemoveStudent" msg="${conRemStu}"/>
<c:if test="${parent.students.size() == 0}">
    <div><spring:message code="parent.noStudents"/></div>
</c:if>
<c:forEach items="${parent.students}" var="student">
    <div><span>${student.getFullName()}</span> <a class="confirmRemoveStudent" href="/admin/parent/${parent.id}/removeStudent/${student.id}"><spring:message code="parent.removeStudent"/></a></div>
</c:forEach>
<div>
    <h2><spring:message code="parent.addStudent"/></h2>
    <form id="addStudentForm" action="/admin/parent/${parent.id}/addStudent" method="get">
        <script src="${pageContext.request.contextPath}/js/parent/parent.js"></script>
        <spring:message code="parent.addStudent.nonExistsentStudent" var="nonExistent"/>
        <input type="hidden" id="nonExistentStudentMsg" value="${nonExistent}"/>
        <spring:message code="parent.insertStudentId"/>
        <input type="number" name="studentId" id="studentId" required="required"/>
        <br/>
        <spring:message code="add" var="add"/>
        <input class="submit" type="submit" value="${add}"/>
    </form>
</div>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="org.springframework.cglib.core.Local" %>
<%--rrrrrrr
  Created by IntelliJ IDEA.
  User: Admin
  Date: 10.10.2023
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<h1><spring:message code="lessonPlan.creation"/></h1>
<div><spring:message code="schoolClass.name"/>: <b>${lessonPlan.schoolClass.name}</b></div>
<div><spring:message code="subject"/>: <b>${lessonPlan.subject.name}</b></div>
<div><spring:message code="date"/>: <b>${lessonPlan.formattedDate}</b></div>
<div><spring:message code="lessonHour"/>: <b>${lessonPlan.lessonHour.id}</b></div>
<form:form action="/lessonplan/create" method="post" modelAttribute="lessonPlan">
    <form:input type="hidden" path="id"/>
    <form:input type="hidden" path="date"/>
    <form:input type="hidden" path="classRoom"/>
    <form:input type="hidden" path="lessonHour"/>
    <form:input type="hidden" path="schoolClass"/>
    <form:input type="hidden" path="teacher"/>
    <div>
        <spring:message code="lessonPlan.topic"/>
        <form:input type="text" path="topic" required="required"/>
    </div>
    <h3><spring:message code="lessonPlan.attendance"/></h3>
    <table class="centerBlock mainTable">
        <thead>
        <tr>
            <th><spring:message code="student"/></th>
            <th><spring:message code="lessonPlan.studentPresence"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${lessonPlan.schoolClass.students}" var="student" varStatus="loop">
            <tr>
                <td>${student.fullNameWithId}</td>
                <td>
                    <form:input type="hidden" path="attendance[${loop.index}].id"
                                value="${lessonPlan.getAttendanceIdByStudentId(student.id)}"/>
                    <form:input type="hidden" path="attendance[${loop.index}].student"
                                value="${student.id}"/>
                    <form:input type="hidden" path="attendance[${loop.index}].lessonPlan"
                                value="${lessonPlan.id}"/>
                    <c:forEach items="${attendanceTypes}" var="attendanceType" varStatus="typeLoop">
                        <label class="blockRadioLabel">
                            <div><spring:message code="attendanceType.${attendanceType.name()}"/>
                                <form:radiobutton
                                        path="attendance[${loop.index}].type"
                                        name="attendance[${loop.index}].type"
                                        value="${attendanceType.name()}"
                                        required="required"
                                />
                            </div>
                        </label>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <button type="submit"><spring:message code="save"/></button>
</form:form>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>
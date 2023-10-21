<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 21.10.2023
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<h1><spring:message code="student.attendanceOfStudent"/> ${student.fullName}</h1>
<a href="?period=${period - 1}"><<< <spring:message code="attendance.previousThirtyDays"/></a>
---
<a href="?period=${period + 1}"><spring:message code="attendance.nextThirtyDays"/> >>></a>
<div class="centerBlock"><spring:message code="attendance.table.legend"/></div>
<table class="centerBlock mainTable studentAttendanceTable">
    <thead>
    <tr>
        <th><spring:message code="lessonHour"/></th>
        <c:forEach items="${lessonHours}" var="lessonHour" varStatus="loop">
            <th>${loop.index + 1}</th>
        </c:forEach>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${dates}" var="date">
        <tr>
            <td>${date.format(dateFormatter)}</td>
            <c:forEach items="${lessonHours}" var="lessonHour">
                <c:if test="${student.getAttendanceByDateAndLessonHourId(date, lessonHour.id) != null}">
                    <td class="${student.getAttendanceByDateAndLessonHourId(date, lessonHour.id).type.cellColor}Cell">
                    <spring:message
                            code="attendanceType.letter.${student.getAttendanceByDateAndLessonHourId(date, lessonHour.id).type}"/>
                </c:if>
                <c:if test="${student.getAttendanceByDateAndLessonHourId(date, lessonHour.id) == null}">
                    <td>
                    ${' - '}
                </c:if>
                </td>
            </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

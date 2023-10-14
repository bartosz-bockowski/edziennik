<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<form action="/lessonplan/create" method="post">
    <div>
        <spring:message code="lessonPlan.topic"/>
        <input type="text" name="topic" required/>
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
        <c:forEach items="${lessonPlan.schoolClass.students}" var="student">
            <tr>
                <td>${student.fullNameWithId}</td>
                <td>
                    <c:forEach items="${attendanceTypes}" var="attendanceType">
                        <label class="blockRadioLabel" for="${attendanceType.name()}${student.id}">
                            <div><spring:message code="attendanceType.${attendanceType.name()}"/>
                                <input type="radio"
                                       id="${attendanceType.name()}${student.id}"
                                       name="attendanceType${student.id}"
                                       required/></div>
                        </label>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <button type="submit"><spring:message code="save"/></button>
</form>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

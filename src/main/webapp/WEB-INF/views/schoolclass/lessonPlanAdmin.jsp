<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 13.09.2023
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<h1><spring:message code="lessonPlan.ofClass"/> ${schoolClass.name}</h1>
<table class="lessonPlanTable centerBlock">
    <thead>
        <tr>
            <td></td>
            <td><spring:message code="monday"/></td>
            <td><spring:message code="tuesday"/></td>
            <td><spring:message code="wednesday"/></td>
            <td><spring:message code="thursday"/></td>
            <td><spring:message code="friday"/></td>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${hours}" var="hour">
            <tr>
                <td>${hour.start} - ${hour.end}</td>
                <td>
                    <c:forEach items="${lessons}" var="lesson">
                        <c:if test="${lesson.date == date}">test</c:if>
                    </c:forEach>
                </td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 13.09.2023
  Time: 23:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<h1><spring:message code="mark.history"/></h1>
<table class="table table-striped table-bordered">
    <tr>
        <td><spring:message code="mark.student"/></td>
        <td class="font-weight-bold">${student.fullName} (<spring:message code="student.schoolClass"/>: ${student.schoolClass.name})</td>
    </tr>
    <tr>
        <td><spring:message code="mark.category"/></td>
        <td class="font-weight-bold">${markCategory.name}</td>
    </tr>
    <tr>
        <td><spring:message code="mark.category.subject"/></td>
        <td class="font-weight-bold">${markCategory.subject.name}</td>
    </tr>
    <tr>
        <td><spring:message code="mark.category.weight"/></td>
        <td class="font-weight-bold">${markCategory.weight}</td>
    </tr>
</table>
<c:if test="${history.size() > 0}">
    <span><spring:message code="mark.history.firstRowCreationNotification"/></span>
    <table class="table table-striped table-bordered">
        <thead>
            <tr>
                <th><spring:message code="mark.newMark"/></th>
                <th><spring:message code="mark.changedBy"/></th>
                <th><spring:message code="mark.changed"/></th>
            </tr>
        </thead>
        <c:forEach items="${history}" var="mark">
            <tr>
                <td>${mark.mark}</td>
                <td>${mark.teacher.fullNameWithId}</td>
                <td>${mark.changed.format(dateFormatter)}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${history.size() == 0}">
    <div><spring:message code="mark.history.empty"/></div>
</c:if>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

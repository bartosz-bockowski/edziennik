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
<a href="/schoolclass/${markCategory.schoolClass.id}/marks/${markCategory.subject.id}"><spring:message code="back"/></a>
<table class="centerBlock noBorderTable paddingTable">
    <tr>
        <td><spring:message code="mark.student"/></td>
        <td><h3>${student.fullName} (<spring:message code="student.schoolClass"/>: ${student.schoolClass.name})</h3></td>
    </tr>
    <tr>
        <td><spring:message code="mark.category"/></td>
        <td><h3>${markCategory.name}</h3></td>
    </tr>
    <tr>
        <td><spring:message code="mark.category.subject"/></td>
        <td><h3>${markCategory.subject.name}</h3></td>
    </tr>
</table>
<span><spring:message code="mark.category.weight"/>: ${markCategory.weight}</span>
<h3><spring:message code="mark.history"/></h3>
<c:if test="${history.size() > 0}">
    <table class="centerBlock mainTable">
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
    <span><spring:message code="mark.history.firstRowCreationNotification"/></span>
</c:if>
<c:if test="${history.size() == 0}">
    <div><spring:message code="mark.history.empty"/></div>
</c:if>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

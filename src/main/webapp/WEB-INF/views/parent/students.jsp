<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 30.08.2023
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<h1><spring:message code="parent.studentList"/></h1>
<table class="table table-striped table-bordered">
    <thead>
    <th><spring:message code="student.fullName"/></th>
    <th><spring:message code="student.schoolClass"/></th>
    <th><spring:message code="actions"/></th>
    </thead>
    <tbody>
    <c:forEach items="${parent.students}" var="student">
        <tr>
            <td>${student.getFullName()}</td>
            <td>
                <c:if test="${student.schoolClass != null}">
                    ${student.schoolClass.name}
                </c:if>
                <c:if test="${student.schoolClass == null}">
                    <spring:message code="student.nullClass"/>
                </c:if>
            </td>
            <td>
                <a href="/admin/student/${student.id}/details"><spring:message code="student.details"/></a>
                <a href="/student/${student.id}/marks"><spring:message code="student.marks"/></a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

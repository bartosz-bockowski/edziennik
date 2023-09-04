<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 04.09.2023
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<h1><spring:message code="schoolClass.list"/></h1>
<a href="/admin/schoolclass/add"><spring:message code="schoolClass.add"/></a>
<br/><br/>
<table class="centerBlock">
    <thead>
    <th><spring:message code="schoolClass.id"/></th>
    <th><spring:message code="schoolClass.name"/></th>
    <th><spring:message code="schoolClass.status"/></th>
    <th><spring:message code="actions"/></th>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="schoolclass">
    <tr>
        <td>${schoolclass.id}</td>
        <td>${schoolclass.name}</td>
        <td><spring:message code="${schoolclass.active}"/></td>
        <td>
            <a href="/admin/schoolclass/${schoolclass.id}/details"><spring:message code="schoolClass.details"/></a>
            <spring:message code="schoolClass.switch.confirm" var="confirm"/>
            <spring:message code="activate" var="activate"/>
            <spring:message code="disactivate" var="disactivate"/>
            <a msg="${confirm}" class="confirm" href="/admin/schoolclass/${schoolclass.id}/switch"><spring:message code="${schoolclass.active ? disactivate : activate}"/></a>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

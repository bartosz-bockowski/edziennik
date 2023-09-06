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
<h1><spring:message code="subject.list"/></h1>
<a href="/admin/subject/add"><spring:message code="subject.add"/></a>
<br/><br/>
<table class="centerBlock">
    <thead>
    <th><spring:message code="subject.id"/></th>
    <th><spring:message code="subject.name"/></th>
    <th><spring:message code="subject.active"/></th>
    <th><spring:message code="actions"/></th>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="subject">
    <tr>
        <td>${subject.id}</td>
        <td>${subject.name}</td>
        <td><spring:message code="${subject.active}"/></td>
        <td>
            <a href="/admin/subject/${subject.id}/details"><spring:message code="subject.details"/></a>
            <spring:message code="subject.switch.confirm" var="confirm"/>
            <spring:message code="activate" var="activate"/>
            <spring:message code="disactivate" var="disactivate"/>
            <a msg="${confirm}" class="confirm" href="/admin/subject/${subject.id}/switch"><spring:message code="${subject.active ? disactivate : activate}"/></a>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

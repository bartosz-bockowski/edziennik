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
<h1><spring:message code="lessonHour.list"/></h1>
<a href="/admin/lessonHour/add"><spring:message code="lessonHour.add"/></a>
<br/>
<c:forEach items="${hours}" var="hour">
    <spring:message code="lessonHour.confirmSwitch" var="confirmSwitch"/>
    ${hour.start} - ${hour.end} <a class="confirm" msg="${confirmSwitch}" href="/admin/lessonHour/${hour.id}/switch"><spring:message code="${hour.active ? 'disactivate' : 'activate'}"/></a><br/>
</c:forEach>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <jsp:include page="../layout/header.jsp"/>
    <h1><spring:message code="homePage"/></h1>
    <jsp:include page="../layout/footer.jsp"/>
</body>
</html>

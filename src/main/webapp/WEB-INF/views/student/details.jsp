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
<h1><spring:message code="student.list"/></h1>
<a href="/admin/student/list"><spring:message code="student.list"/></a>
<h1><spring:message code="student.studentDetails"/></h1>
<h3>${student.getFullName()}</h3>
<spring:message code="student.nullClass" var="nullClass"/>
<spring:message code="student.schoolClass"/>: <h3>${student.schoolClass.name != null ? student.schoolClass.name : nullClass}</h3>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

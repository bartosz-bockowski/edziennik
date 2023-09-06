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
<a href="/admin/teacher/list"><spring:message code="teacher.list"/></a>
<h1><spring:message code="teacher.teacherDetails"/></h1>
<h3>${teacher.getFullName()}</h3>
<h3><spring:message code="teacher.subjects"/>:</h3>
<c:forEach items="${subjects}" var="subject">
    <div>${subject.name}</div>
</c:forEach>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

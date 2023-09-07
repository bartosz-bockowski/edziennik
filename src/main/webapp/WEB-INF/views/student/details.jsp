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
<a href="/admin/student/list"><spring:message code="student.list"/></a>
<h1><spring:message code="student.studentDetails"/></h1>
<h3>${student.getFullName()}</h3>
<spring:message code="student.nullClass" var="nullClass"/>
<spring:message code="student.schoolClass"/>: <b>${student.schoolClass.name != null ? student.schoolClass.name : nullClass}</b>
<br/>
<h3><spring:message code="student.user"/></h3>
<c:if test="${student.user == null}">
    <form method="get" action="/admin/student/${student.id}/setUser">
        <select class="selectpicker selectpickerCustom" data-live-search="true" name="user">
            <c:forEach items="${users}" var="user">
                <option value="${user.id}">${user.username} (ID: ${user.id})</option>
            </c:forEach>
        </select>
        <br/>
        <button type="submit" class="selectpickerButton"><spring:message code="student.setUser"/></button>
    </form>
</c:if>
<c:if test="${student.user != null}">
    <spring:message code="student.confirmClearUser" var="confirmClearUser"/>
    ${student.user.username} (ID: ${student.user.id}) <a class="defaultConfirm" msg="${confirmClearUser}" href="/admin/student/${student.id}/clearUser"><spring:message code="student.clearUser"/></a>
</c:if>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

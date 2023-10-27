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
<a href="/admin/teacher/list"><spring:message code="teacher.list"/></a>
<h1><spring:message code="teacher.teacherDetails"/></h1>
<h3>${teacher.getFullName()}</h3>
<h3><spring:message code="teacher.subjects"/>:</h3>
<c:forEach items="${subjects}" var="subject">
    <div>${subject.name}</div>
</c:forEach>
<h3><spring:message code="teacher.user"/></h3>
<c:if test="${teacher.user != null}">
    ${teacher.user.username}<a class="confirm" href="/admin/teacher/${teacher.id}/removeUser">
    <spring:message code="teacher.removeUser"/>
    <p class="msg"><spring:message code="teacher.confirmRemoveUser"/></p>
</a>
</c:if>
<c:if test="${teacher.user == null}">
    <div><spring:message code="teacher.noUser"/></div>
    <div><spring:message code="user.add"/></div>
    <form method="get" action="/admin/teacher/${teacher.id}/setUser">
        <select class="selectpicker" data-live-search="true" name="user">
            <c:forEach items="${freeUsers}" var="user">
                <option value="${user.id}">${user.username} (ID: ${user.id})</option>
            </c:forEach>
        </select>
        <br/>
        <button type="submit" class="btn btn-primary"><spring:message code="teacher.setUser"/></button>
    </form>
</c:if>
<h3><spring:message code="teacher.supervisedClasses"/></h3>
<form method="get" action="/admin/teacher/${teacher.id}/addSupervisedClass">
    <select class="selectpicker" data-live-search="true" name="schoolClass">
        <c:forEach items="${classes}" var="schoolClass">
            <option value="${schoolClass.id}">${schoolClass.name} (ID: ${schoolClass.id})</option>
        </c:forEach>
    </select>
    <br/>
    <button type="submit" class="btn btn-primary"><spring:message code="teacher.setUser"/></button>
</form>
<c:if test="${teacher.supervisedClasses.size() > 0}">
    <c:forEach items="${teacher.supervisedClasses}" var="schoolClass">
        ${schoolClass.name} (ID: ${schoolClass.id}) <a class="confirm"
                                                       href="/admin/teacher/${teacher.id}/removeSupervisedClass/${schoolClass.id}">
        <spring:message code="teacher.removeSupervisedClass"/>
        <p class="msg"><spring:message code="teacher.confirmRemoveClass"/> (${schoolClass.name}
            ID: ${schoolClass.id})</p>
    </a><br/>
    </c:forEach>
</c:if>
<c:if test="${teacher.supervisedClasses.size() == 0}">
    <spring:message code="teacher.noSupervisedClasse"/>
</c:if>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

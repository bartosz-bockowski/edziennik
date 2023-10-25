<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 30.08.2023
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<a href="/admin/schoolclass/list"><spring:message code="schoolClass.list"/></a>
<h1><spring:message code="schoolClass.schoolClassDetails"/></h1>
<div><spring:message code="schoolClass.name"/>: <b>${schoolClass.name}</b></div>
<br/>
<h3><spring:message code="schoolClass.supervisingTeachers"/></h3>
<c:forEach items="${schoolClass.supervisingTeachers}" var="teacher">
    <div>${teacher.fullNameWithId}</div>
</c:forEach>
<br/>
<script src="${pageContext.request.contextPath}/js/schoolClass/schoolClassDetails.js"></script>
<!-- students -->
<h3><spring:message code="schoolClass.students"/></h3>
<spring:message code="schoolClass.confirmRemoveStudent" var="conRemStu"/>
<c:if test="${schoolClass.students.size() == 0}">
    <div><spring:message code="schoolClass.noStudents"/></div>
</c:if>
<c:forEach items="${schoolClass.students}" var="student">
    <div>${student.getFullName()}</div>
</c:forEach>
<!-- subjects -->
<br/>
<h3><spring:message code="schoolClass.subjects"/></h3>
<spring:message code="schoolClass.confirmRemoveSubject" var="conRemSub"/>
<c:if test="${schoolClass.subjects.size() == 0}">
    <div><spring:message code="schoolClass.noSubjects"/></div>
</c:if>
<c:forEach items="${schoolClass.subjects}" var="subject">
    <div>${subject.name}
        <a href="/schoolclass/${schoolClass.id}/marks/${subject.id}"><spring:message code="schoolClass.marks"/></a>
    </div>
</c:forEach>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

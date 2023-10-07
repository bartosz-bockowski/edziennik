<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <jsp:include page="../layout/header.jsp"/>
    <h1><spring:message code="homePage"/></h1>
    <c:if test="${loggedUser.student != null}">
        <div class="mainPageUser">
            <span class="mainPageUserHeader"><spring:message code="user.student"/></span>
            <span class="mainPageUserInfo">${loggedUser.student.fullNameWithId}</span>
            <a href="/student/${loggedUser.student.id}/marks"><spring:message code="student.marks"/></a>
            <a href="/schoolclass/${loggedUser.student.schoolClass.id}/lessonPlan"><spring:message code="lessonPlan"/></a>
        </div>
    </c:if>
    <c:if test="${loggedUser.parent != null}">
        <h1><spring:message code="parent.students"/></h1>
        <c:if test="${loggedUser.parent.students.size() == 0}">
            <spring:message code="parent.noStudents"/>
        </c:if>
        <c:forEach items="${loggedUser.parent.students}" var="student">
            <div class="mainPageUser">
                <span class="mainPageUserHeader"><spring:message code="user.student"/></span>
                <span class="mainPageUserInfo">${loggedUser.student.fullNameWithId}</span>
                <a href="/student/${student.id}/marks"><spring:message code="student.marks"/></a>
                <a href="/schoolclass/${student.schoolClass.id}/lessonPlan"><spring:message code="lessonPlan"/></a>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${loggedUser.teacher != null}">
        <div class="mainPageUser">
            <span class="mainPageUserHeader"><spring:message code="user.teacher"/></span>
            <span class="mainPageUserInfo">${loggedUser.teacher.fullNameWithId}</span>
            <a href="/teacher/${loggedUser.teacher.id}/lessonPlan"><spring:message code="lessonPlan"/></a>
            <a href="/teacher/${loggedUser.teacher.id}/subjects"><spring:message code="teacher.subjects"/></a>
            <a href="/teacher/${loggedUser.teacher.id}/supervisedClasses"><spring:message code="teacher.supervisedClasses"/></a>
        </div>
    </c:if>
    <jsp:include page="../layout/footer.jsp"/>
</body>
</html>

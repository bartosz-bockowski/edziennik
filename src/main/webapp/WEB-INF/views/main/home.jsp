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
        </div>
    </c:if>
    <c:if test="${loggedUser.parent != null}">
        <div class="mainPageUser">
            <span class="mainPageUserHeader"><spring:message code="user.parent"/></span>
            <span class="mainPageUserInfo">${loggedUser.parent.fullNameWithId}</span>
        </div>
    </c:if>
    <c:if test="${loggedUser.teacher != null}">
        <div class="mainPageUser">
            <span class="mainPageUserHeader"><spring:message code="user.teacher"/></span>
            <span class="mainPageUserInfo">${loggedUser.teacher.fullNameWithId}</span>
        </div>
    </c:if>
    <jsp:include page="../layout/footer.jsp"/>
</body>
</html>

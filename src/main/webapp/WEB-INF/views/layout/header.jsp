<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>E-dziennik</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/global.js"></script>
    <!-- select picker -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
    <header>
        <div id="headerMainDiv">
            <a class="headerOption mainHeaderOption" href="/"><spring:message code="mainPage"/></a>
            <sec:authorize access="hasAuthority('admin')">
                <a class="headerOption" href="/admin/user/list"><spring:message code="user.list"/></a>
                <a class="headerOption" href="/admin/role/list"><spring:message code="role.list"/></a>
                <a class="headerOption" href="/admin/teacher/list"><spring:message code="teacher.list"/></a>
                <a class="headerOption" href="/admin/schoolclass/list"><spring:message code="schoolClass.list"/></a>
                <a class="headerOption" href="/admin/student/list"><spring:message code="student.list"/></a>
                <a class="headerOption" href="/admin/parent/list"><spring:message code="parent.list"/></a>
                <a class="headerOption" href="/admin/subject/list"><spring:message code="subject.list"/></a>
                <a class="headerOption" href="/admin/lessonHour/list"><spring:message code="lessonHour.list"/></a>
                <a class="headerOption" href="/admin/classRoom/list"><spring:message code="classRoom.list"/></a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <div id="rightDiv">
                    <div class="notificationsButtonParent">
                        <div class="notificationsButton">
                        </div>
                        <div class="notificationsList">
                        </div>
                    </div>
                    <form action="${pageContext.request.contextPath}/logout" method="get">
                        <button type="submit"><spring:message code="logout"/></button>
                    </form>
                </div>
            </sec:authorize>
        </div>
    </header>
    <br/>
</body>
</html>

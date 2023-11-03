<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>E-dziennik</title>
    <!-- Include Bootstrap Datepicker -->
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/ico/favicon.ico">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/master.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.0.4/popper.js"></script>
    <script src="${pageContext.request.contextPath}/js/global.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <%--    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>--%>
    <%--    <script src="${pageContext.request.contextPath}/js/npm.js"></script>--%>
    <!-- select picker -->
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.min.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
</head>
<body class="text-center">
<sec:authorize access="isAuthenticated()">
    <div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-dark border-bottom box-shadow">
        <h5 class="my-0 mr-md-auto font-weight-normal"><a href="/"><spring:message code="e-dziennik"/></a></h5>
        <nav class="my-2 my-md-0 mr-md-3">
            <sec:authorize access="hasAuthority('admin')">
                <a class="p-2 text-white" href="/admin/user/list"><spring:message code="user.list"/></a>
                <a class="p-2 text-white" href="/admin/role/list"><spring:message code="role.list"/></a>
                <a class="p-2 text-white" href="/admin/teacher/list"><spring:message code="teacher.list"/></a>
                <a class="p-2 text-white" href="/admin/schoolclass/list"><spring:message code="schoolClass.list"/></a>
                <a class="p-2 text-white" href="/admin/student/list"><spring:message code="student.list"/></a>
                <a class="p-2 text-white" href="/admin/parent/list"><spring:message code="parent.list"/></a>
                <a class="p-2 text-white" href="/admin/subject/list"><spring:message code="subject.list"/></a>
                <a class="p-2 text-white" href="/admin/lessonHour/list"><spring:message code="lessonHour.list"/></a>
                <a class="p-2 text-white" href="/admin/classRoom/list"><spring:message code="classRoom.list"/></a>
            </sec:authorize>
        </nav>
        <a class="btn btn-danger" href="/logout"><spring:message code="logout"/></a>
    </div>
</sec:authorize>
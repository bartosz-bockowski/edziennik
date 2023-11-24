<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="linksHeader.jsp"/>
<body>
<sec:authorize access="isAuthenticated()">
    <div class="text-center d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-dark border-bottom box-shadow">
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
        <div class="position-relative">
            <button id="headerUserButton" class="btn btn-primary"><%= request.getUserPrincipal().getName() %>
                <span class="small">&#9660;</span>
            </button>
            <div class="border border-light position-absolute bg-white text-dark text-left" style="top:100%; right:0; display: none;">
                <a class="d-block m-3" href="/user/<%= request.getUserPrincipal().getName() %>/account">
                    <div><spring:message code="user.myAccount"/></div>
                </a>
                <div class="m-3">
                    <a class="btn btn-danger" href="/logout"><spring:message code="logout"/></a>
                </div>
            </div>
        </div>
    </div>
</sec:authorize>
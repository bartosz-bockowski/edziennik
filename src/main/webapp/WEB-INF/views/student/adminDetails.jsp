<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<h3>${student.fullName}</h3>
<table class="container-fluid table text-center table-responsive-xl">
    <tr>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="student.schoolClass"/></h5>
        </td>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="student.user"/></h5>
        </td>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="student.studentData"/></h5>
        </td>
    </tr>
    <tr>
        <td class="card-body">
            <c:if test="${student.schoolClass != null}">
                <h5>${student.schoolClass.name}</h5>
            </c:if>
            <c:if test="${student.schoolClass == null}">
                <spring:message code="student.nullClass"/>
            </c:if>
            <form method="get" action="/admin/student/${student.id}/setClass">
                <select class="selectpicker" data-live-search="true" name="classId">
                    <c:forEach items="${schoolClasses}" var="schoolClass">
                        <option ${(student.schoolClass != null && student.schoolClass.id == schoolClass.id) ? 'selected' : ''}
                                value="${schoolClass.id}">${schoolClass.name} (ID: ${schoolClass.id})
                        </option>
                    </c:forEach>
                </select>
                <br/>
                <button type="submit" class="btn btn-primary"><spring:message code="student.setClass"/></button>
            </form>
        </td>
        <td class="card-body">
            <c:if test="${student.user != null}">
                ${student.user.username} (ID: ${student.user.id}) <a class="confirm"
                                                                     href="/admin/student/${student.id}/removeUser">
                <spring:message code="student.removeUser"/>
                <p class="msg"><spring:message code="student.confirmRemoveUser"/></p>
            </a>
            </c:if>
            <c:if test="${student.user == null}">
                <div><spring:message code="student.noUser"/></div>
                <div><spring:message code="user.add"/></div>
                <form method="get" action="/admin/student/${student.id}/setUser">
                    <select class="selectpicker" data-live-search="true" name="user">
                        <c:forEach items="${freeUsers}" var="user">
                            <option value="${user.id}">${user.username} (ID: ${user.id})</option>
                        </c:forEach>
                    </select>
                    <button type="submit" class="btn btn-primary"><spring:message code="student.setUser"/></button>
                </form>
            </c:if>
        </td>
        <td class="card-body justify-content-center d-flex">
            <table class="table-borderless">
                <tr>
                    <td><spring:message code="student.firstName"/></td>
                    <td>${student.firstName}</td>
                </tr>
                <tr>
                    <td><spring:message code="student.secondName"/></td>
                    <td>${student.secondName}</td>
                </tr>
                <tr>
                    <td><spring:message code="student.lastName"/></td>
                    <td>${student.lastName}</td>
                </tr>
                <tr>
                    <td><spring:message code="student.dateOfBirth"/></td>
                    <td>${student.dateOfBirth}</td>
                </tr>
                <tr>
                    <td><spring:message code="student.gender"/></td>
                    <td>
                        <c:if test="${student.isMale}"><spring:message code="male"/></c:if>
                        <c:if test="${!student.isMale}"><spring:message code="female"/></c:if>
                    </td>
                </tr>
                <tr>
                    <td><spring:message code="student.pesel"/></td>
                    <td>${student.pesel}</td>
                </tr>
                <tr>
                    <td><spring:message code="student.street"/></td>
                    <td>${student.street}</td>
                </tr>
                <tr>
                    <td><spring:message code="student.homeNumber"/></td>
                    <td>${student.homeNumber}</td>
                </tr>
                <tr>
                    <td><spring:message code="student.postCode"/></td>
                    <td>${student.postCode}</td>
                </tr>
                <tr>
                    <td><spring:message code="student.city"/></td>
                    <td>${student.city}</td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

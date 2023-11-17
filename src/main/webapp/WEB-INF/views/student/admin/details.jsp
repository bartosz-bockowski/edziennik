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
<jsp:include page="../../layout/header.jsp"/>
<a href="/admin/student/list"><spring:message code="student.list"/></a>
<h1><spring:message code="student.studentDetails"/></h1>
<h3>${student.fullName}</h3>
<table class="container-fluid table-bordered text-center table-responsive-xl">
    <tr>
        <td class="card-header bg-secondary text-white">
            <h5 class="card-title mb-1"><spring:message code="student.schoolClass"/></h5>
        </td>
        <td class="card-header bg-secondary text-white">
            <h5 class="card-title mb-1"><spring:message code="student.user"/></h5>
        </td>
    </tr>
    <tr>
        <td class="card-body align-text-top">
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
        <td class="card-body align-text-top">
            <c:if test="${student.user != null}">
                ${student.user.username} (ID: ${student.user.id})
                <div><a class="confirm btn btn-primary" href="/admin/student/${student.id}/removeUser">
                    <spring:message code="student.removeUser"/>
                    <p class="msg"><spring:message code="student.confirmRemoveUser"/></p>
                </a></div>
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
                    <div>
                        <button type="submit" class="btn btn-primary"><spring:message code="student.setUser"/></button>
                    </div>
                </form>
            </c:if>
        </td>
    </tr>
</table>
<h3 class="card-title mb-1 my-2"><spring:message code="student.studentData"/></h3>
<div>
    <table class="ml-auto mr-auto table-borderless my-2">
        <tr>
            <td><spring:message code="userData.firstName"/></td>
            <td class="font-weight-bold my-2">${student.firstName}</td>
        </tr>
        <tr>
            <td><spring:message code="userData.secondName"/></td>
            <td class="font-weight-bold my-2">${student.secondName}</td>
        </tr>
        <tr>
            <td><spring:message code="userData.lastName"/></td>
            <td class="font-weight-bold my-2">${student.lastName}</td>
        </tr>
        <tr>
            <td><spring:message code="userData.dateOfBirth"/></td>
            <td class="font-weight-bold my-2">${student.dateOfBirth}</td>
        </tr>
        <tr>
            <td><spring:message code="userData.gender"/></td>
            <td class="font-weight-bold my-2">
                <c:if test="${student.isMale}"><spring:message code="male"/></c:if>
                <c:if test="${!student.isMale}"><spring:message code="female"/></c:if>
            </td>
        </tr>
        <tr>
            <td><spring:message code="userData.pesel"/></td>
            <td class="font-weight-bold my-2">${student.pesel}</td>
        </tr>
        <tr>
            <td><spring:message code="address.street"/></td>
            <td class="font-weight-bold my-2">${student.street}</td>
        </tr>
        <tr>
            <td><spring:message code="address.homeNumber"/></td>
            <td class="font-weight-bold my-2">${student.homeNumber}<c:if test="${student.apartment != ''}">/${student.apartment}</c:if></td>
        </tr>
        <tr>
            <td><spring:message code="address.postCode"/></td>
            <td class="font-weight-bold my-2">${student.postCode}</td>
        </tr>
        <tr>
            <td><spring:message code="address.city"/></td>
            <td class="font-weight-bold my-2">${student.city}</td>
        </tr>
        <tr>
            <td><spring:message code="userData.email"/></td>
            <td class="font-weight-bold my-2">${student.email}</td>
        </tr>
        <tr>
            <td><spring:message code="userData.phoneNumber"/></td>
            <td class="font-weight-bold my-2">${student.phoneNumber}</td>
        </tr>
    </table>
    <div class="my-3">
        <a href="/admin/student/${student.id}/edit" class="btn btn-primary"><spring:message code="student.edit"/></a>
    </div>
</div>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

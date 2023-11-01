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
<a href="/admin/parent/list"><spring:message code="parent.list"/></a>
<h1><spring:message code="parent.parentDetails"/></h1>
<div><spring:message code="parent.fullName"/>: <b>${parent.getFullName()}</b></div>
<table class="container-fluid table-bordered text-center table-responsive-xl">
    <tr>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="parent.students"/></h5>
        </td>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="parent.addStudent"/></h5>
        </td>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="parent.user"/></h5>
        </td>
    </tr>
    <tr>
        <td class="card-body">
            <c:if test="${parent.students.size() == 0}">
                <div><spring:message code="parent.noStudents"/></div>
            </c:if>
            <c:forEach items="${parent.students}" var="student">
                <div><span>${student.getFullName()}</span>
                    <a class="confirm" href="/admin/parent/${parent.id}/removeStudent/${student.id}">
                        <spring:message code="parent.removeStudent"/>
                        <p class="msg"><spring:message code="parent.confirmRemoveStudent"/> (${student.getFullName()}
                            ID: ${student.id})</p>
                    </a>
                </div>
            </c:forEach>
        </td>
        <td class="card-body">
            <form id="addStudentForm" action="/admin/parent/${parent.id}/addStudent" method="get">
                <select class="selectpicker" data-live-search="true" name="studentId">
                    <c:forEach items="${students}" var="student">
                        <c:if test="${!parent.students.contains(student)}">
                            <option value="${student.id}">${student.getFullName()} (ID: ${student.id})</option>
                        </c:if>
                    </c:forEach>
                </select>
                <br/>
                <button type="submit" class="btn btn-primary"><spring:message code="parent.addUser"/></button>
            </form>
        </td>
        <td class="card-body">
            <c:if test="${parent.user != null}">
                ${parent.user.username} (ID: ${parent.user.id}) <a class="confirm"
                                                                   href="/admin/parent/${parent.id}/removeUser">
                <spring:message code="parent.removeUser"/>
                <p class="msg"><spring:message code="parent.confirmRemoveUser"/></p>
            </a><br/>
            </c:if>
            <c:if test="${parent.user == null}">
                <div><spring:message code="parent.noUser"/></div>
                <div><spring:message code="user.add"/></div>
                <form method="get" action="/admin/parent/${parent.id}/setUser">
                    <select class="selectpicker" data-live-search="true" name="user">
                        <c:forEach items="${freeUsers}" var="user">
                            <option value="${user.id}">${user.username} (ID: ${user.id})</option>
                        </c:forEach>
                    </select>
                    <br/>
                    <button type="submit" class="btn btn-primary"><spring:message code="parent.setUser"/></button>
                </form>
            </c:if>
        </td>
    </tr>
</table>
<h3 class="card-title mb-1 my-2"><spring:message code="parent.parentData"/></h3>
<div>
    <table class="ml-auto mr-auto table-borderless my-2">
        <tr>
            <td><spring:message code="student.firstName"/></td>
            <td class="font-weight-bold my-2">${parent.firstName}</td>
        </tr>
        <tr>
            <td><spring:message code="student.lastName"/></td>
            <td class="font-weight-bold my-2">${parent.lastName}</td>
        </tr>
        <tr>
            <td><spring:message code="userData.dateOfBirth"/></td>
            <td class="font-weight-bold my-2">${parent.dateOfBirth}</td>
        </tr>
        <tr>
            <td><spring:message code="userData.gender"/></td>
            <td class="font-weight-bold my-2">
                <c:if test="${parent.isMale}"><spring:message code="male"/></c:if>
                <c:if test="${!parent.isMale}"><spring:message code="female"/></c:if>
            </td>
        </tr>
        <tr>
            <td><spring:message code="userData.pesel"/></td>
            <td class="font-weight-bold my-2">${parent.pesel}</td>
        </tr>
        <tr>
            <td><spring:message code="address.street"/></td>
            <td class="font-weight-bold my-2">${parent.street}</td>
        </tr>
        <tr>
            <td><spring:message code="address.homeNumber"/></td>
            <td class="font-weight-bold my-2">${parent.homeNumber}<c:if test="${parent.apartment != ''}">/${parent.apartment}</c:if></td>
        </tr>
        <tr>
            <td><spring:message code="address.postCode"/></td>
            <td class="font-weight-bold my-2">${parent.postCode}</td>
        </tr>
        <tr>
            <td><spring:message code="address.city"/></td>
            <td class="font-weight-bold my-2">${parent.city}</td>
        </tr>
        <tr>
            <td><spring:message code="userData.email"/></td>
            <td class="font-weight-bold my-2">${parent.email}</td>
        </tr>
        <tr>
            <td><spring:message code="userData.phoneNumber"/></td>
            <td class="font-weight-bold my-2">${parent.phoneNumber}</td>
        </tr>
    </table>
    <div class="my-3">
        <a href="/admin/parent/${parent.id}/edit" class="btn btn-primary"><spring:message code="parent.edit"/></a>
    </div>
</div>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

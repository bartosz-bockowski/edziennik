<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 30.08.2023
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<a href="/admin/teacher/list"><spring:message code="teacher.list"/></a>
<h1>
    <c:if test="${teacher.id == null}">
        <spring:message code="teacher.add"/>
    </c:if>
    <c:if test="${teacher.id != null}">
        <spring:message code="teacher.edit"/>
    </c:if>
</h1>
<form:form action="/admin/teacher/add" method="post" modelAttribute="teacher">
    <form:input path="user" type="hidden"/>
    <form:input path="id" type="hidden"/>
    <div class="d-flex justify-content-center">
        <table class="table-bordered">
            <tr>
                <td><spring:message code="userData.firstName"/><span class="text-danger mx-1">*</span></td>
                <td><form:input required="required" type="text" class="form-control" path="firstName"/></td>
            </tr>
            <tr>
                <td><spring:message code="userData.lastName"/><span class="text-danger mx-1">*</span></td>
                <td><form:input required="required" type="text" class="form-control" path="lastName"/></td>
            </tr>
            <tr>
                <td><spring:message code="userData.dateOfBirth"/><span class="text-danger mx-1">*</span></td>
                <td><form:input required="required" type="text" class="form-control datepicker" path="dateOfBirth"/></td>
            </tr>
            <tr>
                <td><spring:message code="userData.gender"/><span class="text-danger mx-1">*</span></td>
                <td>
                    <div class="form-check">
                        <input <c:if test="${teacher.isMale}">checked="checked"</c:if> class="form-check-input" type="radio" id="male" name="isMale" value="true">
                        <label class="form-check-label" for="male">
                            <spring:message code="male"/>
                        </label>
                    </div>
                    <div class="form-check">
                        <input <c:if test="${!teacher.isMale}">checked="checked"</c:if> class="form-check-input" type="radio" id="female" name="isMale" value="false">
                        <label class="form-check-label" for="female">
                            <spring:message code="female"/>
                        </label>
                    </div>
                </td>
            </tr>
            <tr>
                <td><spring:message code="userData.pesel"/><span class="text-danger mx-1">*</span></td>
                <td><form:input required="required" type="number" class="form-control" path="pesel"/></td>
            </tr>
            <tr>
                <td><spring:message code="address.street"/><span class="text-danger mx-1">*</span></td>
                <td><form:input required="required" type="text" class="form-control" path="street"/></td>
            </tr>
            <tr>
                <td><spring:message code="address.homeNumber"/><span class="text-danger mx-1">*</span></td>
                <td><form:input required="required" type="number" class="form-control" path="homeNumber"/></td>
            </tr>
            <tr>
                <td><spring:message code="address.apartment"/></td>
                <td><form:input type="number" class="form-control" path="apartment"/></td>
            </tr>
            <tr>
                <td><spring:message code="address.postCode"/><span class="text-danger mx-1">*</span></td>
                <td><form:input required="required" type="text" class="form-control" path="postCode"/></td>
            </tr>
            <tr>
                <td><spring:message code="address.city"/><span class="text-danger mx-1">*</span></td>
                <td><form:input required="required" type="text" class="form-control" path="city"/></td>
            </tr>
            <tr>
                <td><spring:message code="userData.email"/><span class="text-danger mx-1">*</span></td>
                <td><form:input required="required" type="email" class="form-control" path="email"/></td>
            </tr>
            <tr>
                <td><spring:message code="userData.phoneNumber"/><span class="text-danger mx-1">*</span></td>
                <td><form:input required="required" type="number" class="form-control" path="phoneNumber"/></td>
            </tr>
        </table>
    </div>
    <div class="text-danger">
        <form:errors path="*"/>
    </div>
    <div>
        <button class="btn btn-primary" type="submit"><spring:message code="save"/></button>
    </div>
</form:form>
<script src="${pageContext.request.contextPath}/js/addForm.js"></script>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
<a href="/admin/student/list"><spring:message code="student.list"/></a>
<h1><spring:message code="student.add"/></h1>
<form:form action="/admin/student/add" method="post" modelAttribute="student">
    <div class="d-flex justify-content-center">
        <table class="table-bordered table-striped">
            <tr class="border-0">
                <td><spring:message code="student.firstName"/><span class="text-danger mx-1">*</span></td>
                <td><form:input required="required" type="text" class="form-control" path="firstName"/></td>
            </tr>
            <tr>
                <td><spring:message code="student.secondName"/></td>
                <td><form:input type="text" class="form-control" path="secondName"/></td>
            </tr>
            <tr>
                <td><spring:message code="student.lastName"/><span class="text-danger mx-1">*</span></td>
                <td><form:input required="required" type="text" class="form-control" path="lastName"/></td>
            </tr>
            <tr>
                <td><spring:message code="student.dateOfBirth"/><span class="text-danger mx-1">*</span></td>
                <td><form:input required="required" type="date" class="form-control" path="dateOfBirth"/></td>
            </tr>
            <tr>
                <td><spring:message code="student.gender"/><span class="text-danger mx-1">*</span></td>
                <td>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" id="male" name="isMale">
                        <label class="form-check-label" for="male">
                            <spring:message code="male"/>
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" id="female" name="isMale">
                        <label class="form-check-label" for="female">
                            <spring:message code="female"/>
                        </label>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <button class="btn btn-primary" type="submit"><spring:message code="add"/></button>
</form:form>
<script src="${pageContext.request.contextPath}/js/addForm.js"></script>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

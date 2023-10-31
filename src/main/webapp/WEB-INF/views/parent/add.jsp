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
<a href="/admin/parent/list"><spring:message code="parent.list"/></a>
<h1><spring:message code="parent.add"/></h1>
<form:form action="/admin/parent/add" method="post" modelAttribute="parent">
    <div class="table-bordered">
        <table class="table-bordered">
            <tr>
                <td>
                    <spring:message code="parent.firstName"/>
                </td>
                <td>
                    <form:input type="text" class="form-control" path="firstName"/>
                </td>
            </tr>
            <tr>
                <td>
                    <spring:message code="parent.lastName"/>
                </td>
                <td>
                    <form:input type="text" class="form-control" path="lastName"/>
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

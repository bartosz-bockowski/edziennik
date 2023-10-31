<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 29.08.2023
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<a href="/admin/classRoom/list"><spring:message code="classRoom.list"/></a>
<h1><spring:message code="classRoom.add"/></h1>
<form:form action="/admin/classRoom/add" method="post" modelAttribute="classRoom">
    <div class="d-flex justify-content-center">
        <table class="table-bordered">
            <tr>
                <td class="text-right">
                    <spring:message code="classRoom.name"/>
                </td>
                <td>
                    <form:input type="text" class="form-control" path="name"/>
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

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 13.09.2023
  Time: 23:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<spring:message code="lessonHour.adding"/>
<form:form method="post" modelAttribute="lessonHour">
    <div class="d-flex justify-content-center">
        <table class="table-bordered">
            <tr>
                <td>
                    <spring:message code="lessonHour.start"/>
                </td>
                <td>
                    <form:input type="time" class="form-control" path="start"/>
                </td>
            </tr>
            <tr>
                <td>
                    <spring:message code="lessonHour.end"/>
                </td>
                <td>
                    <form:input type="time" class="form-control" path="end"/>
                </td>
            </tr>
        </table>
    </div>
    <c:if test="${result.hasFieldErrors('end')}">
        <div class="text-danger"><spring:message code="${result.getFieldError('end').defaultMessage}"/></div>
    </c:if>
    <button class="btn btn-primary" type="submit"><spring:message code="add"/></button>
</form:form>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

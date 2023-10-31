<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 31.10.2023
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../layout/header.jsp"/>
<h1><spring:message code="address.changeAddress"/></h1>
<form:form action="/admin/address/change" method="post" modelAttribute="address">
    <input type="hidden" name="targetType" value="${targetType}"/>
    <input type="hidden" name="targetId" value="${targetId}"/>
    <form:input type="hidden" path="id"/>
    <div class="d-flex justify-content-center">
        <table class="table-borderless border-0">
            <tr>
                <td class="text-right">
                    <spring:message code="address.street"/>
                </td>
                <td>
                    <form:input type="text" class="form-control" path="street"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    <spring:message code="address.homeNumber"/>
                </td>
                <td>
                    <form:input type="text" class="form-control" path="homeNumber"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    <spring:message code="address.apartment"/>
                </td>
                <td>
                    <form:input type="text" class="form-control" path="apartment"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    <spring:message code="address.postCode"/>
                </td>
                <td>
                    <form:input type="text" class="form-control" path="postCode"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    <spring:message code="address.city"/>
                </td>
                <td>
                    <form:input type="text" class="form-control" path="city"/>
                </td>
            </tr>
        </table>
    </div>
    <c:if test="${result.hasFieldErrors('street')}">
        <div class="text-danger"><spring:message code="address.street"/>: <spring:message code="${result.getFieldError('street').defaultMessage}"/></div>
    </c:if>
    <c:if test="${result.hasFieldErrors('homeNumber')}">
        <div class="text-danger"><spring:message code="address.homeNumber"/>: <spring:message code="${result.getFieldError('homeNumber').defaultMessage}"/></div>
    </c:if>
    <c:if test="${result.hasFieldErrors('postCode')}">
        <div class="text-danger"><spring:message code="address.postCode"/>: <spring:message code="${result.getFieldError('postCode').defaultMessage}"/></div>
    </c:if>
    <c:if test="${result.hasFieldErrors('city')}">
        <div class="text-danger"><spring:message code="address.city"/>: <spring:message code="${result.getFieldError('city').defaultMessage}"/></div>
    </c:if>
    <button class="btn btn-primary" type="submit"><spring:message code="address.changeAddress"/></button>
</form:form>
<jsp:include page="../layout/footer.jsp"/>
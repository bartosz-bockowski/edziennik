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
<jsp:include page="../../layout/header.jsp"/>
<a href="/admin/user/list"><spring:message code="user.list"/></a>
<h1><spring:message code="user.userDetails"/></h1>
<div><spring:message code="user.username"/>: <b>${user.username}</b></div>
<table class="container-fluid table-bordered text-center table-responsive-xl">
    <tr>
        <td class="card-header">
            <h5 class="card-title mb-1"><spring:message code="user.roles"/></h5>
        </td>
    </tr>
    <tr>
        <td class="card-body">
            <form method="get" action="/admin/user/${user.id}/setRoles">
                <c:forEach items="${roles}" var="role" varStatus="loopStatus">
                    <input class="checkbox" type="checkbox" value="${role.id}" id="role${role.id}" name="roles"
                           <c:if test="${user.roles.contains(role)}">checked="checked"</c:if>>
                    <label for="role${role.id}"><spring:message code="role.${role.name}"/></label><br>
                </c:forEach>
                <button class="btn btn-primary" type="submit"><spring:message code="user.saveRoles"/></button>
            </form>
        </td>
    </tr>
    <tr>
        <c:if test="${user.teacher != null}">
            <td class="card-header">
                <h5 class="card-title text-white mb-1"><spring:message code="user.teacher"/></h5>
            </td>
        </c:if>
        <c:if test="${user.parent != null}">
            <td class="card-header">
                <h5 class="card-title text-white mb-1"><spring:message code="user.parent"/></h5>
            </td>
        </c:if>
        <c:if test="${user.student != null}">
            <td class="card-header">
                <h5 class="card-title text-white mb-1"><spring:message code="user.student"/></h5>
            </td>
        </c:if>
    </tr>
    <tr>
        <c:if test="${user.teacher != null}">
            <td class="card-body">
                ${user.teacher.fullNameWithId}
            </td>
        </c:if>
        <c:if test="${user.parent != null}">
            <td class="card-body">
                ${user.parent.fullNameWithId}
            </td>
        </c:if>
        <c:if test="${user.student != null}">
            <td class="card-body">
                ${user.student.fullNameWithId}
            </td>
        </c:if>
    </tr>
</table>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

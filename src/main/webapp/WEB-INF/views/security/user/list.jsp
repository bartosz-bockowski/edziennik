<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
<h1><spring:message code="user.list"/></h1>
<div>
    <a class="confirm" href="/admin/user/add">
        <spring:message code="user.add"/>
        <p class="msg"><spring:message code="user.add.confirm"/></p>
    </a>
</div>
<div class="mr-auto ml-auto w-auto d-inline-block text-left mt-2">
    <form method="get" action="?">
        <div>
            <input type="text" name="id" id="id"
                   value="<%= request.getParameter("id") == null ? "" : request.getParameter("id") %>"/>
            <label for="id" class="ml-1">
                <spring:message code="user.id"/>
            </label>
        </div>
        <div>
            <input type="text" name="username" id="username"
                   value="<%= request.getParameter("id") == null ? "" : request.getParameter("username") %>"/>
            <label for="username" class="ml-1">
                <spring:message code="user.username"/>
            </label>
        </div>
        <div class="text-center">
            <button class="btn btn-primary" type="submit"><spring:message code="filter"/></button>
        </div>
        <div class="text-center">
            <button class="btn btn-secondary clearFilterButton mt-1" type="button"><spring:message code="clearFilter"/></button>
        </div>
    </form>
</div>
<table class="table table-striped table-bordered" id="datatable">
    <thead>
    <tr>
        <th><spring:message code="user.id"/></th>
        <th><spring:message code="user.username"/></th>
        <th><spring:message code="user.status"/></th>
        <th><spring:message code="actions"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td><spring:message code="${user.active}"/></td>
            <td>
                <a href="/admin/user/${user.id}/details"><spring:message code="user.details"/></a>
                <c:if test="${user.username != 'admin'}">
                    <a class="confirm" href="/admin/user/${user.id}/switch">
                        <spring:message code="${user.active ? 'disactivate' : 'activate'}"/>
                        <p class="msg"><spring:message code="user.switch.confirm"/></p>
                    </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="w-100">
    <div class="float-right">
        <div class="border border-secondary float-left rounded-left <c:if test="${page.pageable.pageNumber == 0}">disabled</c:if>">
            <a href="?page=${page.pageable.pageNumber - 1}">
                <div class="p-2">
                    &#5176;
                </div>
            </a>
        </div>
        <div class="border border-secondary p-2 float-left">
            ${page.pageable.pageNumber + 1}
        </div>
        <div class="border border-secondary float-left rounded-right <c:if test="${!page.hasNext()}">disabled</c:if>">
            <a href="?page=${page.pageable.pageNumber + 1}">
                <div class="p-2">
                    &#5171;
                </div>
            </a>
        </div>
    </div>
</div>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

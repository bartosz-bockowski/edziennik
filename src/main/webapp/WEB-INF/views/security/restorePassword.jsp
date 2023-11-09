<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../layout/linksHeader.jsp"/>
<html>
<body class="text-center">
<a href="/login"><spring:message code="loginPage"/></a>
<table class="container-fluid table-bordered text-center table-responsive-xl">
    <tr>
        <td class="card-header bg-secondary text-white">
            <h5 class="card-title mb-1"><spring:message code="security.password.restoringPassword"/></h5>
        </td>
    </tr>
    <tr>
        <td class="card-body">
            <form id="userAccountPasswordChangeForm" action="/user/${restorePasswordCode}/restorePassword" class="confirm" method="get" ajax="clearPasswordChangeForm" ajaxRes="true">
                <p class="msg"><spring:message code="security.password.changeConfirm"/></p>
                <div class="d-inline-block">
                    <div>
                        <spring:message code="security.password.criteriaLabel"/>:
                    </div>
                    <ul class="d-inline-block text-left">
                        <c:forEach items="${passwordCriterias}" var="passwordCritera">
                            <li><spring:message code="security.password.criteria.${passwordCritera.name()}"/></li>
                        </c:forEach>
                    </ul>
                    <div class="text-success" id="passSuccessDiv" style="display:none;">
                        <spring:message code="security.password.changeSuccess"/>
                    </div>
                    <div class="text-danger" id="passErrorDiv" style="display: none;">
                        <ul class="d-inline-block text-left" id="passwordCriteriaErrorList">
                        </ul>
                    </div>
                    <hr>
                    <label for="newPassword" class="d-block"><spring:message code="user.changePassword.newPassword"/></label>
                    <input type="password" class="form-control" name="newPassword" id="newPassword"/>
                    <hr>
                    <label for="confirmNewPassword" class="d-block"><spring:message code="user.changePassword.confirmNewPassword"/></label>
                    <input type="password" class="form-control" name="confirmNewPassword" id="confirmNewPassword"/>
                    <hr>
                    <button type="submit" class="btn btn-primary"><spring:message code="save"/></button>
                </div>
            </form>
        </td>
    </tr>
</table>
<script src="${pageContext.request.contextPath}/js/ajax/security/account/passwordChange.js"></script>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../layout/header.jsp"/>
<h5><spring:message code="user.usersAccount"/></h5>
<h3>${user.username}</h3>
<table class="container-fluid table-bordered text-center table-responsive-xl">
    <tr>
        <td class="card-header bg-secondary text-white">
            <h5 class="card-title mb-1"><spring:message code="user.passwordChange"/></h5>
        </td>
    </tr>
    <tr>
        <td class="card-body">
            <form id="userAccountPasswordChangeForm" method="/user/changePassword" ajax="clearPasswordChangeForm">
                <div class="d-inline-block">
                    <label for="currentPassword" class="d-block"><spring:message code="user.changePassword.currentPassword"/></label>
                    <input type="text" class="form-control" name="currentPassword" id="currentPassword"/>
                    <div>
                        <spring:message code="user.changePassword.criteriaLabel"/>
                    </div>
                    <ul class="d-inline-block text-left">
                        <li><spring:message code="user.changePassword.criteria.1"/></li>
                        <li><spring:message code="user.changePassword.criteria.2"/></li>
                        <li><spring:message code="user.changePassword.criteria.3"/></li>
                    </ul>
                    <label for="newPassword" class="d-block"><spring:message code="user.changePassword.newPassword"/></label>
                    <input type="text" class="form-control" name="newPassword" id="newPassword"/>
                    <hr>
                    <label for="confirmNewPassword" class="d-block"><spring:message code="user.changePassword.confirmNewPassword"/></label>
                    <input type="text" class="form-control" name="confirmNewPassword" id="confirmNewPassword"/>
                    <hr>
                    <button type="submit" class="btn btn-primary"><spring:message code="save"/></button>
                </div>
            </form>
        </td>
    </tr>
</table>
<jsp:include page="../../layout/footer.jsp"/>
</body>
</html>

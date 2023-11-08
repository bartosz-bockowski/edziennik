<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../layout/header.jsp"/>
<script src="${pageContext.request.contextPath}/js/notifications.js"></script>
<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
    <h1 class="display-4"><spring:message code="homePage"/></h1>
</div>
<div class="container">
    <c:if test="${loggedUser.student != null}">
        <div class="card mb-4 box-shadow">
            <div class="card-header bg-secondary text-white">
                <h4 class="my-0 font-weight-normal"><spring:message code="user.student"/></h4>
            </div>
            <div class="card-body">
                <h1 class="card-title pricing-card-title">${loggedUser.student.fullName}</small></h1>
                <ul class="list-unstyled mt-3 mb-4">
                    <li><a class="btn btn-primary my-1" href="/student/${loggedUser.student.id}/marks"><spring:message
                            code="student.marks"/></a></li>
                    <li><a class="btn btn-primary my-1"
                           href="/schoolclass/${loggedUser.student.schoolClass.id}/lessonPlan"><spring:message
                            code="lessonPlan"/></a></li>
                    <li><a class="btn btn-primary my-1"
                           href="/student/${loggedUser.student.id}/attendance"><spring:message
                            code="student.attendance"/></a></li>
                    <li><a class="btn btn-primary my-1" href="/student/${loggedUser.student.id}/details"><spring:message
                            code="student.details "/></a></li>
                </ul>
            </div>
        </div>
    </c:if>
    <c:if test="${loggedUser.parent != null}">
        <div class="card mb-4 box-shadow">
            <div class="card-header bg-secondary text-white">
                <h4 class="my-0 font-weight-normal"><spring:message code="user.parent"/></h4>
            </div>
            <div class="card-body">
                <h1 class="card-title pricing-card-title">${loggedUser.parent.fullName}</h1>
                <ul class="list-unstyled mt-3 mb-4">
                    <li><a class="btn btn-primary my-1" href="/parent/${loggedUser.parent.id}/students"><spring:message
                            code="parent.students"/></a></li>
                    <li><a class="btn btn-primary my-1" href="/parent/${loggedUser.parent.id}/details"><spring:message
                            code="parent.details"/></a></li>
                </ul>
            </div>
        </div>
    </c:if>
    <c:if test="${loggedUser.teacher != null}">
        <div class="card mb-4 box-shadow">
            <div class="card-header bg-secondary text-white">
                <h4 class="my-0 font-weight-normal"><spring:message code="user.teacher"/></h4>
            </div>
            <div class="card-body">
                <h1 class="card-title pricing-card-title">${loggedUser.teacher.fullName}</h1>
                <ul class="list-unstyled mt-3 mb-4">
                    <li><a class="btn btn-primary my-1"
                           href="/teacher/${loggedUser.teacher.id}/lessonPlan"><spring:message code="lessonPlan"/></a>
                    </li>
                    <li><a class="btn btn-primary my-1"
                           href="/teacher/${loggedUser.teacher.id}/subjects"><spring:message
                            code="teacher.subjects"/></a></li>
                    <li><a class="btn btn-primary my-1"
                           href="/teacher/${loggedUser.teacher.id}/supervisedClasses"><spring:message
                            code="teacher.supervisedClasses"/></a></li>
                    <li><a class="btn btn-primary my-1" href="/teacher/${loggedUser.teacher.id}/details"><spring:message
                            code="teacher.details"/></a></li>
                </ul>
            </div>
        </div>
    </c:if>
</div>
<p id="noNotificationsVal" hidden><spring:message code="notifications.noNotifications"/></p>
<p id="noMoreNotificationsVal" hidden><spring:message code="notifications.noMoreNotifications"/></p>
<p id="moreVal" hidden><spring:message code="notifications.more"/></p>
<div id="notificationsCount" hidden>0</div>
<div class="notificationsList w-50 ml-auto mr-auto">
    <h3><spring:message code="notifications"/></h3>
</div>
<button id="loadMoreNotificationsButton" class="btn btn-primary"><spring:message code="notifications.older"/></button>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="lessonPlanTable centerBlock">
    <thead>
    <tr>
        <td></td>
        <td><spring:message code="monday"/></td>
        <td><spring:message code="tuesday"/></td>
        <td><spring:message code="wednesday"/></td>
        <td><spring:message code="thursday"/></td>
        <td><spring:message code="friday"/></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${plan}" var="hour" varStatus="loop">
        <tr>
            <td>${hours.get(loop.index).start} - ${hours.get(loop.index).end}</td>
            <c:forEach items="${hour}" var="lesson">
                <c:if test="${lesson == null}">
                    <td>-</td>
                </c:if>
                <c:if test="${lesson != null}">
                    <td>
                        ${lesson.subject.name}<br>
                        ${lesson.classRoom.name}<br>
                        ${teacher == null ? lesson.teacher.fullNameWithId : lesson.schoolClass.name}
                    </td>
                </c:if>
            </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
</table>
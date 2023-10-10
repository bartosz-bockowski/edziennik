<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="lessonPlanTable centerBlock">
    <thead>
    <tr>
        <td></td>
        <td><a href="?date=${date.minusDays(7)}"><<< <spring:message code="lessonPlan.previousWeek"/></a></td>
        <td></td>
        <td></td>
        <td></td>
        <td><a href="?date=${date.plusDays(7)}"><spring:message code="lessonPlan.nextWeek"/> >>></a></td>
    </tr>
    <tr>
        <td></td>
        <td>
            <spring:message code="monday"/>
            <br/>
            ${date.format(dateFormatter)}
        </td>
        <td>
            <spring:message code="tuesday"/>
            <br/>
            ${date.plusDays(1).format(dateFormatter)}
        </td>
        <td>
            <spring:message code="wednesday"/>
            <br/>
            ${date.plusDays(2).format(dateFormatter)}
        </td>
        <td>
            <spring:message code="thursday"/>
            <br/>
            ${date.plusDays(3).format(dateFormatter)}
        </td>
        <td>
            <spring:message code="friday"/>
            <br/>
            ${date.plusDays(4).format(dateFormatter)}
        </td>
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
                        <c:if test="${isTeacher}">
                            <a href="/exam/newExam/${lesson.id}"><spring:message code="exam.addExam"/></a>
                        </c:if>
                    </td>
                </c:if>
            </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
</table>
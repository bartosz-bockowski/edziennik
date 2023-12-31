<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 13.09.2023
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<p id="emptyVal" hidden><spring:message code="lesson.lessonPlanAdmin.emptyVal"/></p>
<p id="teacherNotFree" hidden><spring:message code="lesson.teacherNotFree"/></p>
<p id="classRoomNotFree" hidden><spring:message code="lesson.classRoomNotFree"/></p>
<p id="noChanges" hidden><spring:message code="lesson.noChanges"/></p>
<p id="confirmUpdate" hidden><spring:message code="lesson.confirm.updateLesson"/></p>
<h1><spring:message code="lessonPlan.ofClass"/> ${schoolClass.name}</h1>
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
            <c:forEach items="${hour}" var="lesson" varStatus="hourLoop">
                <td class="${lesson == null ? 'null' : 'present'}LessonTd">
                    <br/>
                    <form method="get" action="/lesson/updateLesson?date=${date}" class="lessonAddForm">
                        <input type="hidden" name="id" value="${lesson.id}"/>
                        <input type="hidden" name="classId" value="${schoolClass.id}"/>
                        <input type="hidden" name="lessonHour" value="${hours.get(loop.index).id}"/>
                        <input type="hidden" name="date" value="${date.plusDays(hourLoop.index)}"/>
                        <spring:message code="subject"/><br/>
                        <spring:message code="none" var="none"/>
                        <select ${lesson == null ? 'title="'.concat(none).concat('" ') : ''}class="selectpicker"
                                data-live-search="true" name="subject" required>
                            <c:forEach items="${subjects}" var="subject">
                                <option value="${subject.id}"${subject.id == lesson.subject.id ? ' selected' : ''}>${subject.name}</option>
                            </c:forEach>
                        </select>
                        <br/>
                        <spring:message code="teacher"/><br/>
                        <select ${lesson == null ? 'title="'.concat(none).concat('" ') : ''}class="selectpicker"
                                data-live-search="true" name="teacher" required>
                            <c:forEach items="${teachers}" var="teacher">
                                <option value="${teacher.id}"${teacher.id == lesson.teacher.id ? ' selected' : ''}>${teacher.getFullName()}</option>
                            </c:forEach>
                        </select>
                        <br/>
                        <spring:message code="classRoom"/><br/>
                        <select ${lesson == null ? 'title="'.concat(none).concat('" ') : ''}class="selectpicker"
                                data-live-search="true" name="classRoom" required>
                            <c:forEach items="${classRooms}" var="classRoom">
                                <option value="${classRoom.id}"${classRoom.id == lesson.classRoom.id ? ' selected' : ''}>${classRoom.name}</option>
                            </c:forEach>
                        </select><br/>
                        <button type="submit"><spring:message code="save"/></button>
                    </form>
                    <c:if test="${lesson != null}">
                        <form class="confirm" method="get" action="/lesson/${lesson.id}/removeLesson">
                            <p class="msg"><spring:message code="lesson.confirm.delete"/></p>
                            <input type="hidden" name="date" value="${date}"/>
                            <button type="submit"><spring:message code="remove"/></button>
                        </form>
                    </c:if>
                </td>
            </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
</table>
<script src="${pageContext.request.contextPath}/js/lessonPlanAdmin.js"></script>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

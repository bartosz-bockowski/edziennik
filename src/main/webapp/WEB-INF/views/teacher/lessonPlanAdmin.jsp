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
<h1><spring:message code="lessonPlan.ofTeacher"/> ${teacher.fullNameWithId}</h1>
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
                        <form method="get" action="/admin/lessonplan/updateLesson?date=${date}">
                            <input type="hidden" name="redirect" value="teacher"/>
                            <input type="hidden" name="id" value="${lesson.id}"/>
                            <input type="hidden" name="teacher" value="${teacher.id}"/>
                            <input type="hidden" name="lessonHour" value="${hours.get(loop.index).id}"/>
                            <input type="hidden" name="date" value="${date.plusDays(hourLoop.index)}"/>
                            Przedmiot
                            <spring:message code="none" var="none"/>
                            <select ${lesson == null ? 'title="'.concat(none).concat('" ') : ''}class="selectpicker" data-live-search="true" name="subject">
                                <c:forEach items="${subjects}" var="subject">
                                    <option value="${subject.id}"${subject.id == lesson.subject.id ? ' selected' : ''}>${subject.name}</option>
                                </c:forEach>
                            </select>
                            Nauczyciel
                            <select ${lesson == null ? 'title="'.concat(none).concat('" ') : ''}class="selectpicker" data-live-search="true" name="classId">
                                <c:forEach items="${schoolClasses}" var="schoolClass">
                                    <option value="${schoolClass.id}"${schoolClass.id == lesson.schoolClass.id ? ' selected' : ''}>${schoolClass.name}</option>
                                </c:forEach>
                            </select>
                            Sala
                            <select ${lesson == null ? 'title="'.concat(none).concat('" ') : ''}class="selectpicker" data-live-search="true" name="classRoom">
                                <c:forEach items="${classRooms}" var="classRoom">
                                    <option value="${classRoom.id}"${classRoom.id == lesson.classRoom.id ? ' selected' : ''}>${classRoom.name}</option>
                                </c:forEach>
                            </select>
                            <button type="submit"><spring:message code="save"/></button>
                        </form>
                        <c:if test="${lesson != null}">
                            <form method="get" action="/admin/schoolclass/${lesson.id}/removeLesson?date=${date}">
                                <button type="submit"><spring:message code="remove"/></button>
                            </form>
                        </c:if>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </tbody>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

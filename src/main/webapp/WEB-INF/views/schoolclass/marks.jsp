<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 22.09.2023
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<spring:message code="mark.addedMark" var="addedMark"/>
<input type="hidden" id="addedMark" value="${addedMark}"/>
<spring:message code="mark.deleteConfirm" var="removeMarkConfirm"/>
<input type="hidden" id="removeMarkConfirm" value="${removeMarkConfirm}"/>
<jsp:include page="../layout/header.jsp"/>
<a href="/admin/schoolclass/${schoolClass.id}/adminDetails"><spring:message code="schoolClass.schoolClassDetails"/></a>
<h1><spring:message code="schoolClass.marks"/> ${schoolClass.name}</h1>
<h3><spring:message code="subject"/>: ${subject.name}</h3>
<spring:message code="schoolClass.marks.addCategory"/><br/>
<form:form action="/markCategory/create" method="post" modelAttribute="markCategory">
    <form:input type="hidden" path="schoolClass" value="${schoolClass.id}"/>
    <form:input type="hidden" path="subject" value="${subjectId}"/>
    <form:input type="text" path="name"/>
    <form:input type="number" path="weight" step="1"/>
    <button type="submit"><spring:message code="add"/></button>
</form:form>
<spring:message code="mark.average"/>: <span
        class="subjectClassAverage">${schoolClass.getAverageMarkBySubjectId(subject.id)}</span>
<div class="marksTableDiv">
    <table class="mainTable marksTable">
        <thead>
        <tr>
            <th><spring:message code="schoolClass.marks.student"/></th>
            <c:forEach items="${markCategories}" var="category">
                <th class="marksTableCategoryName">
                        ${category.name}
                </th>
            </c:forEach>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${schoolClass.students}" var="student">
            <tr>
                <td>${student.fullName}</td>
                <c:forEach items="${markCategories}" var="category">
                    <td>
                        <span ${!student.checkMark(category.id) ? 'style="display: none;"' : ''}
                                class="markSpan">${student.checkMark(markCategory.id) ? '' : student.getMarkByMarkCategoryId(category.id).mark}</span>
                        <form ${!student.checkMark(category.id) ? '' : 'style="display: none;"'}
                                class="markForm">
                            <input type="hidden" class="markId"
                                   value="${student.getMarkByMarkCategoryId(category.id) == null ? 'null' : student.getMarkByMarkCategoryId(category.id).id}"/>
                            <input type="hidden" class="student" name="student" value="${student.id}"/>
                            <input type="hidden" class="markCategory" name="markCategory" value="${category.id}"/>
                            <input type="hidden" class="subject" name="subject" value="${subject.id}"/>
                            <input type="hidden" class="schoolClass" name="schoolClass" value="${schoolClass.id}"/>
                            <input type="text" class="mark" name="mark"
                                   value="${!student.checkMark(category.id) ? '' : student.getMarkByMarkCategoryId(category.id).mark}"/>
                            <button class="markFormSubmit" type="submit"><spring:message code="save"/></button>
                        </form>
                        <div class="markSwitch">X</div>
                        <a target="_blank" href="/mark/history/${category.id}/${student.id}">
                            <div class="markHistory">X</div>
                        </a>
                        <form class="markDeleteForm" method="post">
                            <input type="hidden" class="schoolClassId" value="${schoolClass.id}"/>
                            <input type="hidden" class="subjectId" value="${subject.id}"/>
                            <input type="hidden" class="category" value="${category.id}"/>
                            <input type="hidden" class="student" value="${student.id}"/>
                            <button type="submit" class="markHistory deleteButton">D</button>
                        </form>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script src="${pageContext.request.contextPath}/js/markForm.js"></script>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

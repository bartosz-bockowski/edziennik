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
<p id="schoolClass" hidden>${schoolClass.id}</p>
<p id="subject" hidden>${subject.id}</p>
<p id="addedMark" hidden><spring:message code="mark.addedMark"/></p>
<p id="removeMarkConfirm" hidden><spring:message code="mark.deleteConfirm"/></p>
<jsp:include page="../layout/header.jsp"/>
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
                <input type="hidden" id="student" value="${student.id}"/>
                <td>${student.fullName}</td>
                <c:forEach items="${markCategories}" var="category">
                    <td>
                        <c:if test="${!manageMarks}">
                            <span class="markSpan">${student.checkMark(category.id) ? student.getMarkByMarkCategoryId(category.id).mark : ' - '}</span>
                        </c:if>
                        <c:if test="${manageMarks}">
                            <span ${student.checkMark(category.id) ? '' : 'style="display: none;"'}
                                    class="markSpan">${student.checkMark(category.id) ? student.getMarkByMarkCategoryId(category.id).mark : ' - '}</span>
                            <form ${!student.checkMark(category.id) ? '' : 'style="display: none;"'}
                                    class="markForm">
                                <input type="hidden" class="markId"
                                       value="${student.getMarkByMarkCategoryId(category.id) == null ? 'null' : student.getMarkByMarkCategoryId(category.id).id}"/>
                                <input type="hidden" class="markCategory" name="markCategory" value="${category.id}"/>
                                <input type="text" class="mark" name="mark"
                                       value="${!student.checkMark(category.id) ? '' : student.getMarkByMarkCategoryId(category.id).mark}"
                                       required/>
                                <button class="markFormSubmit" type="submit"><spring:message code="save"/></button>
                            </form>
                            <div class="markSwitch">X</div>
                            <a target="_blank" href="/mark/history/${category.id}/${student.id}">
                                <div class="markHistory">X</div>
                            </a>
                            <form class="markDeleteForm" method="post">
                                <input type="hidden" class="category" value="${category.id}"/>
                                <button type="submit" class="markHistory deleteButton">D</button>
                            </form>
                        </c:if>
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

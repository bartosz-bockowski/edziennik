<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 25.09.2023
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<h3><spring:message code="student.markList"/> ${student.fullName}</h3>
<a href="/admin/student/list"><spring:message code="student.list"/></a>
<br/><br/>
<table class="centerBlock mainTable basicListTable">
    <thead>
        <tr>
            <th><spring:message code="subject"/></th>
            <th><spring:message code="marks"/></th>
            <th><spring:message code="mark.average"/></th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${student.schoolClass.subjects}" var="subject">
        <tr>
            <td>${subject.name}</td>
            <td>
                <c:set var="subjectMarks" value="${student.getMarksBySubjectId(subject.id)}"/>
                <c:forEach items="${subjectMarks}" var="mark">
                    ${mark.mark}  (${mark.markCategory.weight}) -
                </c:forEach>
            </td>
            <td>${markUtils.countAverageOfMarks(subjectMarks)}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 03.10.2023
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="../layout/header.jsp"/>
<div><spring:message code="student.subjectMarksPart1"/> <b>${student.getFullName()}</b> <spring:message code="student.subjectMarksPart2"/> <b>${subject.name}</b></div>
<c:forEach items="${student.getMarksBySubjectId(subject.id)}" var="mark">
    <a class="markHref" href="/mark/history/${mark.markCategory.id}/${student.id}">
        <div class="markDiv">
            ${mark.getMarkString()}
        <span><spring:message code="mark.category.weight"/>: ${mark.markCategory.weight}</span>
                <span><b>${mark.markCategory.name}</b></span>
    </div>
    </a>
</c:forEach>
<c:if test="${student.getMarksBySubjectId(subject.id).size() == 0}">
    <div><spring:message code="student.noMarks"/></div>
</c:if>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>

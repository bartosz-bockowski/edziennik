<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <jsp:include page="../layout/header.jsp"/>
    <h1><spring:message code="homePage"/></h1>
    <a href="/admin/user/list"><spring:message code="user.list"/></a>
    <br/><br/>
    <a href="/admin/role/list"><spring:message code="role.list"/></a>
    <br/><br/>
    <a href="/admin/teacher/list"><spring:message code="teacher.list"/></a>
    <br/><br/>
    <a href="/admin/schoolclass/list"><spring:message code="schoolClass.list"/></a>
    <br/><br/>
    <a href="/admin/student/list"><spring:message code="student.list"/></a>
    <br/><br/>
    <a href="/admin/parent/list"><spring:message code="parent.list"/></a>
    <br/><br/>
    <a href="/admin/subject/list"><spring:message code="subject.list"/></a>
    <jsp:include page="../layout/footer.jsp"/>
</body>
</html>

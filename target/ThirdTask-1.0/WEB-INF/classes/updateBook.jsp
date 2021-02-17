<%--
  Created by IntelliJ IDEA.
  User: naic infa
  Date: 2/8/2021
  Time: 11:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>Изменить данные пользователя</title>
</head>
<body>

Редактировать аннотацию книги
${param.name}
${param.surname}
<form action="books" method="post">

<%--    <%request.setCharacterEncoding("UTF-8");--%>
<%--    response.setCharacterEncoding("UTF-8");%>--%>

    <input type="text" name="newAnnotation" value="new annotation">
    <input type="hidden" name="name" value="${param.name}">
    <input type="hidden" name="surname" value="${param.surname}">
    <input type="hidden" name="bookName" value="${param.bookName}">
    <input type="hidden" name="action" value="update">
    <input type="submit" value="Обновить">
</form>

</body>
</html>

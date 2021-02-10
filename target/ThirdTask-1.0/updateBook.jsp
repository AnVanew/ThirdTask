<%--
  Created by IntelliJ IDEA.
  User: naic infa
  Date: 2/8/2021
  Time: 11:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменить данные пользователя</title>
</head>
<body>

Редактировать пользователя

<form action="books" method="post">
    <input type="text" name="newAnnotation" value="${param.newAnnotation}" placeholder=${param.newAnnotation}>
    <input type="hidden" name="name" value="${param.name}" placeholder=${param.name}>
    <input type="hidden" name="surname" value="${param.surname}" placeholder=${param.surname}>
    <input type="hidden" name="bookName" value="${param.bookName}" placeholder=${param.bookName}>
    <input type="hidden" name="annotation" value="${param.annotation}" placeholder=${param.annotation}>
    <input type="hidden" name="year" value="${param.year}" placeholder=${param.year}>
    <input type="hidden" name="action" value="update">
    <input type="submit" value="Обновить">
</form>

</body>
</html>

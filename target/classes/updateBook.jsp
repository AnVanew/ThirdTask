<%--
  Created by IntelliJ IDEA.
  User: naic infa
  Date: 2/8/2021
  Time: 11:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Изменить данные пользователя</title>
</head>
<body>

Редактировать книгу

<form action="books" method="post">

    <select name="authorId">
        <c:forEach var="author" items="${authors}">
            <option value=${author.getId()}>${author.getName()}  ${author.getSurname()}</option>
        </c:forEach>
    </select>

    <input type="hidden" name="name" value="${param.name}">
    <input type="hidden" name="surname" value="${param.surname}">
    <input type="hidden" name="bookName" value="${param.bookName}">

    <input type="text" name="newBookName" value="${book.getBookName()}">
    <input type="text" name="newYear" value="${book.getYear()}">
    <input type="text" name="newAnnotation" value="${book.getAnnotation()}">

    <input type="hidden" name="action" value="update">

    <input type="submit" value="Обновить">
</form>

</body>
</html>

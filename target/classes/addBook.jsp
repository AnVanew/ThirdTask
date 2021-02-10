<%--
  Created by IntelliJ IDEA.
  User: naic infa
  Date: 2/7/2021
  Time: 9:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавить новую книгу</title>
</head>
<body>
<form action = "books" method="post">
    <input required type="text" name="name" placeholder="Имя">
    <input required type="text" name="surname" placeholder="Фамилия">
    <input required type="text" name="bookName" placeholder="Название книги">
    <input required type="text" name="annotation" placeholder="Аннотация">
    <input required type="text" name="year" placeholder="Год">
    <input type="submit" value="Сохранить">
</form>
</body>
</html>

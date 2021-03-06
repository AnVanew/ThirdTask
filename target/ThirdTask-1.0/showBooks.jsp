<%--
  Created by IntelliJ IDEA.
  User: naic infa
  Date: 2/8/2021
  Time: 5:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Список книг</title>
</head>
<body>

Поиск по автору и названию
<form action="searchBook" method="get">
    <input required type="text" name="name" placeholder="Имя">
    <input required type="text" name="surname" placeholder="Фамилия">
    <input required type="text" name="bookName" placeholder="Название книги">
    <input type="hidden" name="action" value="searchBook">
    <input type="submit" value="Поиск" >
</form>
Найти все книги автора
<form action="authorsBooks" method="get">
    <input required type="text" name="name" placeholder="Имя">
    <input required type="text" name="surname" placeholder="Фамилия">
    <input type="submit" value="Поиск" >
</form>

<table border="2">
    <tr>
        <td>Имя</td>
        <td>Фамилия</td>
        <td>Название </td>
        <td>Год</td>
        <td>Аннотация</td>
    </tr>
    <c:forEach items="${books}" var = "book">
        <tr>
            <td>${book.getName()}</td>
            <td>${book.getSurname()}</td>
            <td>${book.getBookName()}</td>
            <td>${book.getYear()}</td>
            <td>${book.getAnnotation()}</td>
            <td>

                <form action = "searchBook" >
                    <input type="hidden" name="book_id" value="${book.getId()}">
                    <input type="submit" value="Подробнее" style="float:left">
                </form>

                <form action = "updateBook" >
                    <input type="hidden" name="bookName" value="${book.getBookName()}">
                    <input type="hidden" name="year" value="${book.getYear()}">
                    <input type="hidden" name="annotation" value="${book.getAnnotation()}">
                    <input type="hidden" name="book_id" value="${book.getId()}">
                    <input type="submit" value="Изменить" style="float:left">
                </form>

                <form action="deleteBook.jsp">
                    <input type="hidden" name="book_id" value="${book.getId()}">
                    <input type="submit" value="Удалить" style="float:left">
                </form>

            </td>
        </tr>
    </c:forEach>
</table>

<form action="authorsBooks" method="post">
    <input required type="text" name="name" placeholder="Имя">
    <input required type="text" name="surname" placeholder="Фамилия">
    <input type="hidden" name="action" value="addAuthor">
    <input type="submit" value="Добавить автора" >
</form>

<form action = "books" method="post">
    <select name="authorId">
        <c:forEach var="author" items="${authors}">
            <option value=${author.getId()}>${author.getName()}  ${author.getSurname()}</option>
        </c:forEach>
    </select>
    <input required type="text" name="bookName" placeholder="Название книги">
    <input required type="text" name="annotation" placeholder="Аннотация">
    <input required type="text" name="year" placeholder="Год">
    <input type="submit" value="Добавить новую книгу">
</form>

</body>
</html>

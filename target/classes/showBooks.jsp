<%--
  Created by IntelliJ IDEA.
  User: naic infa
  Date: 2/8/2021
  Time: 5:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Список книг</title>
</head>
<body>

<form action="searchBook" method="get">
    <input required type="text" name="name" placeholder="Имя">
    <input required type="text" name="surname" placeholder="Фамилия">
    <input required type="text" name="bookName" placeholder="Название книги">
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
                    <input type="hidden" name="name" value="${book.getName()}">
                    <input type="hidden" name="surname" value="${book.getSurname()}">
                    <input type="hidden" name="bookName" value="${book.getBookName()}">
                    <input type="submit" value="Подробнее" style="float:left">
                </form>

                <form action = "updateBook.jsp" method="post">
                    <input type="hidden" name="name" value="${book.getName()}">
                    <input type="hidden" name="surname" value="${book.getSurname()}">
                    <input type="hidden" name="bookName" value="${book.getBookName()}">
                    <input type="hidden" name="year" value="${book.getYear()}">
                    <input type="submit" value="Изменить" style="float:left">
                </form>

                <form action="deleteBook.jsp" method="post">
                    <input type="hidden" name="name" value="${book.getName()}">
                    <input type="hidden" name="surname" value="${book.getSurname()}">
                    <input type="hidden" name="bookName" value="${book.getBookName()}">
                    <input type="submit" value="Удалить" style="float:left">
                </form>

            </td>
        </tr>
    </c:forEach>
</table>

<form action = "addBook.jsp">
    <input type="submit" value="Добавить новую книгу">
</form>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: naic infa
  Date: 2/9/2021
  Time: 11:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>

    <tr>
        <td>Имя автора:</td>
        <td>${book.getName()}</td>
    </tr>

    <tr>
        <td>Фамилия автора:</td>
        <td>${book.getSurname()}</td>
    </tr>

    <tr>
        <td>Название книги:</td>
        <td>${book.getBookName()}</td>
    </tr>

    <tr>
        <td>Год:</td>
        <td>${book.getYear()}</td>
    </tr>

    <tr>
        <td>Аннотация:</td>
        <td>${book.getAnnotation()}</td>
    </tr>

    <tr>
        <td>Понравилось:</td>
        <td>${likes}</td>
        <td>
            <form action="searchBook" method="post">
                <input type="hidden" name="name" value="${param.name}">
                <input type="hidden" name="surname" value="${param.surname}">
                <input type="hidden" name="bookName" value="${param.bookName}">
                <input type="submit" value="Like">
                <input type="hidden" name="action" value="like">
            </form>
        </td>
    </tr>

    <tr>
        <td>Не понравилось:</td>
        <td>${dislikes}</td>
        <td>
            <form action="searchBook" method="post">
                <input type="hidden" name="name" value="${param.name}">
                <input type="hidden" name="surname" value="${param.surname}">
                <input type="hidden" name="bookName" value="${param.bookName}">
                <input type="submit" value="Dislike">
                <input type="hidden" name="action" value="dislike">
            </form>
        </td>
    </tr>

    <tr>
        <td>
            <form action="searchBook" method="post">
                <input type="hidden" name="name" value="${param.name}">
                <input type="hidden" name="surname" value="${param.surname}">
                <input type="hidden" name="bookName" value="${param.bookName}">
                <input type="text" name="userName" placeholder="имя пользователя">
                <input type="text" name="comment" placeholder="Ваш комментарий">
                <input type="hidden" name="action" value="newComment">
                <input type="submit" value="Добавить комментарий">
            </form>
        </td>
    </tr>

    <c:forEach items="${comments}" var = "comment">
        <tr>
            <td>${comment.getUserName()}</td>
            <td>${comment.getComment()}</td>
        </tr>
    </c:forEach>


</table>

<form action="books">
    <input type="submit" value="К списку всех книг" style="float:left">
</form>

</body>
</html>

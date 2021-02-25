<%--
  Created by IntelliJ IDEA.
  User: naic infa
  Date: 2/9/2021
  Time: 12:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Удалить пользователя</title>
</head>
<body>

Вы действительно хотите удалить книгу ${param.bookName}?

<%request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");%>

<form action="books" method="post">
<input type="hidden" name="book_id" value="${param.book_id}">
<input type="hidden" name="action" value="delete">
<input type="submit" value="Удалить">
</form>

</body>
</html>

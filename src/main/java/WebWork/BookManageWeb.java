package WebWork;

import DBWork.BookManageDB;
import Models.Book;

import javax.servlet.http.HttpServletRequest;

public class BookManageWeb {

    public static Book getBookFromRequest(HttpServletRequest req){
        Book book;
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String bookName = req.getParameter("bookName");
        book = BookManageDB.getBookByAuthorAndName(name, surname, bookName);
        return  book;
    }

}

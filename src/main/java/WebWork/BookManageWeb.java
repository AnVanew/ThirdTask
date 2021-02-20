package WebWork;

import DBWork.BookManageDB;

import javax.servlet.http.HttpServletRequest;

public class BookManageWeb {
    private static BookManageDB bookManageDB = new BookManageDB();
    public static int getBookIdFromRequestt(HttpServletRequest req){
        int authorId = AuthorsWeb.getAuthorIdFronRequest(req);
        String bookName = req.getParameter("bookName");
        int bookId = bookManageDB.getBookId(bookName, authorId);
        return  bookId;
    }

}

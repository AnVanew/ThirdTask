package Servlets;

import DBWork.AuthorsDB;
import DBWork.BookManageDB;
import DBWork.CommentsDB;
import DBWork.MarksDB;
import Models.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import Models.Comment;
import org.apache.log4j.Logger;

public class BookServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(BookServlet.class);
    private final BookManageDB bookManageDB = new BookManageDB();
    private final AuthorsDB authorsDB = new AuthorsDB();
    private final MarksDB marksDB = new MarksDB();
    private final CommentsDB commentsDB = new CommentsDB();

    public void init(ServletConfig servletConfig) {
        try {
            super.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Method GET");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        int authorId = authorsDB.getAuthorId(name, surname);
        String bookName = req.getParameter("bookName");
        int bookId = bookManageDB.getBookId(bookName, authorId);
        Book book = bookManageDB.getBookByAuthorAndBookName(bookId);
        if (book == null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/BookNotFound.jsp");
            dispatcher.forward(req, resp);
        }
        int likes = marksDB.getBookLikes(bookId);
        int dislikes = marksDB.getBookDislikes(bookId);
        List<Comment> comments = commentsDB.getAllComments(bookId);
        req.setAttribute("book", book);
        req.setAttribute("likes", likes);
        req.setAttribute("dislikes", dislikes);
        req.setAttribute("comments", comments);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/showBook.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Method POST");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String bookName = req.getParameter("bookName");
        int authorId = authorsDB.getAuthorId(name, surname);
        int bookId = bookManageDB.getBookId(bookName, authorId);
        Book book = bookManageDB.getBookByAuthorAndBookName(bookId);
        int likes = marksDB.getBookLikes(bookId);
        int dislikes = marksDB.getBookDislikes(bookId);
        List<Comment> comments = commentsDB.getAllComments(bookId);
        req.setAttribute("book", book);
        req.setAttribute("likes", likes);
        req.setAttribute("dislikes", dislikes);
        req.setAttribute("comments", comments);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/showBook.jsp");
        dispatcher.forward(req, resp);
    }
}

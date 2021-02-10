package Servlets;

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

public class OneBookServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(OneBookServlet.class);
    private BookManageDB bookManageDB = new BookManageDB();
    private MarksDB marksDB = new MarksDB();
    private CommentsDB commentsDB = new CommentsDB();

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
        String bookName = req.getParameter("bookName");
        Book book = bookManageDB.getBooksByAutorAndBookName(name, surname, bookName);
        int bookId = bookManageDB.getBookId(book);
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
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String bookName = req.getParameter("bookName");
        Book book = bookManageDB.getBooksByAutorAndBookName(name, surname, bookName);
        int bookId = bookManageDB.getBookId(book);
        if ("like".equals(req.getParameter("action")))
            marksDB.like(book);
        if ("dislike".equals(req.getParameter("action")))
            marksDB.dislike(book);
        if ("newComment".equals(req.getParameter("action"))) {
            String userName = req.getParameter("userName");
            String comment = req.getParameter("comment");
            commentsDB.addComment(bookId, userName, comment);
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
}

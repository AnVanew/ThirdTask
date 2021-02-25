package Servlets;

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
import WebWork.BookManageWeb;
import org.apache.log4j.Logger;

public class BookServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(BookServlet.class);

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
        Book book;
        if ("searchBook".equals(req.getParameter("action"))) {
            book = BookManageWeb.getBookFromRequestNameSurnameBookName(req);
        }
        else {
            book = BookManageWeb.getBookFromRequestId(req);
        }
        if (book == null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/BookNotFound.jsp");
            dispatcher.forward(req, resp);
        }
        int likes = MarksDB.getBookLikes(book.getId());
        int dislikes = MarksDB.getBookDislikes(book.getId());
        List<Comment> comments = CommentsDB.getAllComments(book.getId());
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
        Book book = BookManageWeb.getBookFromRequestId(req);
        int likes = MarksDB.getBookLikes(book.getId());
        int dislikes = MarksDB.getBookDislikes(book.getId());
        List<Comment> comments = CommentsDB.getAllComments(book.getId());
        req.setAttribute("book", book);
        req.setAttribute("likes", likes);
        req.setAttribute("dislikes", dislikes);
        req.setAttribute("comments", comments);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/showBook.jsp");
        dispatcher.forward(req, resp);
    }
}

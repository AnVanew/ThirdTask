package Servlets;

import DBWork.BookManageDB;
import DBWork.CommentsDB;
import DBWork.MarksDB;
import Models.Book;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BookManageServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(BookManageServlet.class);
    private BookManageDB bookManageDB = new BookManageDB();
    MarksDB marksDB = new MarksDB();
    CommentsDB commentsDB = new CommentsDB();

    public void init(ServletConfig servletConfig) {
        try {
            super.init(servletConfig);
        } catch (ServletException e) {
            logger.fatal(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Method GET ");
        List <Book> books = bookManageDB.getAllBooks();
        req.setAttribute("books", books);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/showBooks.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Method POST ");
        if ("delete".equals(req.getParameter("action"))) {
            doDelete(req, resp);
        } else if ("update".equals(req.getParameter("action"))){
            doPut(req,resp);
        }
                else {
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            String bookName = req.getParameter("bookName");
            String annotation = req.getParameter("annotation");
            int year = Integer.parseInt(req.getParameter("year"));
            bookManageDB.addBookWithAutor(name, surname, bookName, year, annotation);
            resp.sendRedirect("books");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Method PUT ");
        String newAnnotation = req.getParameter("newAnnotation");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String bookName = req.getParameter("bookName");
        int bookId = bookManageDB.getBookId(name, surname, bookName);
        bookManageDB.updateBook(newAnnotation, bookId);
        resp.sendRedirect("books");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Method DELETE ");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String bookName = req.getParameter("bookName");
        Book book = bookManageDB.getBooksByAutorAndBookName(name, surname, bookName);
        int bookId = bookManageDB.getBookId(book);
        marksDB.deleteBookMarks(bookId);
        commentsDB.deleteBookComments(bookId);
        bookManageDB.deleteBook(bookId);
        resp.sendRedirect("books");
    }
}

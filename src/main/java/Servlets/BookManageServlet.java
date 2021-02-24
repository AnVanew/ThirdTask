package Servlets;

import DBWork.*;
import Models.Author;
import Models.Book;
import WebWork.BookManageWeb;
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

    public void init(ServletConfig servletConfig) {
        DBWorker.createDB();
        try {
            super.init(servletConfig);
        } catch (ServletException e) {
            logger.fatal(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Method GET ");
        List <Book> books = BookManageDB.getAllBooks();
        List <Author> authors = AuthorsDB.getAllAuthors();
        req.setAttribute("books", books);
        req.setAttribute("authors", authors);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/showBooks.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Method POST ");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if ("delete".equals(req.getParameter("action"))) {
            doDelete(req, resp);
        } else if ("update".equals(req.getParameter("action"))) {
            doPut(req, resp);
        }
        else {
            Author author = AuthorsDB.getAuthorById(Integer.parseInt(req.getParameter("authorId")));
            String name = author.getName();
            String surname = author.getSurname();
            String bookName = req.getParameter("bookName");
            String annotation = req.getParameter("annotation");
            int year = Integer.parseInt(req.getParameter("year"));
            BookManageDB.addBookWithAuthor(name, surname, bookName, year, annotation);
            resp.sendRedirect("books");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Method PUT ");
        Book book = BookManageWeb.getBookFromRequest(req);
        String newAnnotation = req.getParameter("newAnnotation");
        BookManageDB.updateBook(newAnnotation, book.getId());
        resp.sendRedirect("books");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Method DELETE ");
        Book book = BookManageWeb.getBookFromRequest(req);
        MarksDB.deleteBookMarks(book.getId());
        CommentsDB.deleteBookComments(book.getId());
        BookManageDB.deleteBook(book.getId());
        resp.sendRedirect("books");
    }
}

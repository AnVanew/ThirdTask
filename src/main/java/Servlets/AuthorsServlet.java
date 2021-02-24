package Servlets;

import DBWork.AuthorsDB;
import DBWork.BookManageDB;
import Models.Author;
import Models.Book;
import WebWork.AuthorsWeb;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorsServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(AuthorsServlet.class);

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
        Author author = AuthorsWeb.getAuthorFromRequest(req);
        List<Book> books = new ArrayList<>();
        if (author != null)
        books = BookManageDB.getBooksByAuthor(author.getId());
        if (books.size() == 0){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/BookNotFound.jsp");
            dispatcher.forward(req, resp);
        }
        req.setAttribute("books", books);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/showAuthorBooks.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Method POST ");
        req.setCharacterEncoding("UTF-8");
        String surname = req.getParameter("surname");
        String name = req.getParameter("name");
        AuthorsDB.addAuthor(name, surname);
        resp.sendRedirect("books");
    }
}

package Servlets;

import DBWork.AuthorsDB;
import DBWork.BookManageDB;
import DBWork.DBWorker;
import Models.Author;
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

public class UpdateBookServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(UpdateBookServlet.class);

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
        List <Author> authors = AuthorsDB.getAllAuthors();
        req.setAttribute("authors", authors);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/updateBook.jsp");
        dispatcher.forward(req, resp);
    }
}

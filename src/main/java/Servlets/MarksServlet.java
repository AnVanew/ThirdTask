package Servlets;

import DBWork.MarksDB;
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

public class MarksServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(BookServlet.class);

    public void init(ServletConfig servletConfig) {
        try {
            super.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        Book book = BookManageWeb.getBookFromRequest(req);
        if ("like".equals(req.getParameter("action")))
            MarksDB.like(book.getId());
        if ("dislike".equals(req.getParameter("action")))
            MarksDB.dislike(book.getId());
        RequestDispatcher dispatcher = req.getRequestDispatcher("searchBook");
        dispatcher.forward(req, resp);
    }
}

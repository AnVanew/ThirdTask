package Servlets;

import DBWork.AuthorsDB;
import DBWork.BookManageDB;
import DBWork.CommentsDB;
import WebWork.BookManageWeb;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommentsServlet extends HttpServlet {

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
        logger.info("Method POST");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        int bookId = BookManageWeb.getBookIdFromRequestt(req);
        String userName = req.getParameter("userName");
        String comment = req.getParameter("comment");
        CommentsDB.addComment(bookId, userName, comment);
        RequestDispatcher dispatcher = req.getRequestDispatcher("searchBook");
        dispatcher.forward(req, resp);
    }
}

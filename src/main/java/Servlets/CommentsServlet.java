package Servlets;

import DBWork.AuthorsDB;
import DBWork.BookManageDB;
import DBWork.CommentsDB;
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
    private final BookManageDB bookManageDB = new BookManageDB();
    private final AuthorsDB authorsDB = new AuthorsDB();
    private final CommentsDB commentsDB = new CommentsDB();

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
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String bookName = req.getParameter("bookName");
        int authorId = authorsDB.getAuthorId(name, surname);
        int bookId = bookManageDB.getBookId(bookName, authorId);
        String userName = req.getParameter("userName");
        String comment = req.getParameter("comment");
        commentsDB.addComment(bookId, userName, comment);
        RequestDispatcher dispatcher = req.getRequestDispatcher("searchBook");
        dispatcher.forward(req, resp);
    }
}

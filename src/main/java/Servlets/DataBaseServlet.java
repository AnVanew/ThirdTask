package Servlets;

import DBWork.DBWorker;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DataBaseServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(DataBaseServlet.class);

    public void init(ServletConfig servletConfig) {
        try {
            super.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String createBooksTable = "CREATE TABLE books (\n" +
                "    id int PRIMARY KEY AUTO_INCREMENT,\n" +
                "    autor_id int,\n" +
                "    book_name VARCHAR (50),\n" +
                "    year int,\n" +
                "    annotation VARCHAR(200),\n" +
                "    FOREIGN KEY (autor_id) REFERENCES AUTORS (id)\n" +
                ")";
        String createAutorsTable = "CREATE TABLE autors (\n" +
                "    id int PRIMARY KEY AUTO_INCREMENT,\n" +
                "    name VARCHAR (50),\n" +
                "    surname VARCHAR (100)\n" +
                ")";
        String createMarksTable = "CREATE TABLE marks (\n" +
                "    id int,\n" +
                "    likes int,\n" +
                "    dislikes int,\n" +
                "    FOREIGN KEY (id) REFERENCES BOOKS (id)\n" +
                ")";
        String createCommentsTable = "CREATE TABLE comments (\n" +
                "    id int,\n" +
                "    user_name VARCHAR (50),\n" +
                "    comment VARCHAR (1000),\n" +
                "    FOREIGN KEY (id) REFERENCES BOOKS (id)\n" +
                ")";

        DBWorker.executeUpdate(createAutorsTable);
        DBWorker.executeUpdate(createBooksTable);
        DBWorker.executeUpdate(createMarksTable);
        DBWorker.executeUpdate(createCommentsTable);
        resp.sendRedirect("books");

    }
}

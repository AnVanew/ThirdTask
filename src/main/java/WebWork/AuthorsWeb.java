package WebWork;

import DBWork.AuthorsDB;
import Models.Author;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

public class AuthorsWeb {
    private  static AuthorsDB authorsDB = new AuthorsDB();

    public static Author getAuthorFromRequest(HttpServletRequest req){
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        Author author = AuthorsDB.getAuthorByNameAndSurname(name, surname);
        return author;
    }
}

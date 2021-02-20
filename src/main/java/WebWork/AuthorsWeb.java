package WebWork;

import DBWork.AuthorsDB;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

public class AuthorsWeb {
    private  static AuthorsDB authorsDB = new AuthorsDB();
    public static int getAuthorIdFronRequest(HttpServletRequest req){
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        int authorId = authorsDB.getAuthorId(name, surname);
        return authorId;
    }
}

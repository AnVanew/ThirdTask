import DBWork.BookManageDB;
import DBWork.DBWorker;

import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {
        BookManageDB bookManageDB = new BookManageDB();
        DBWorker dbWorker = new DBWorker();
       // System.out.println(bookManageDB.checkSameAutor("Ivan", "Andreev"));
//        String querry = "SELECT * FROM books WHERE book_name = " + "'"+  "Happy New Year" + "'";
//        System.out.println(querry);
//            ResultSet resultSet = dbWorker.getQuerry(querry);
//            while (resultSet.next()) System.out.println(resultSet.getString("book_name"));



    }
}

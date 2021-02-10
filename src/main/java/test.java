import DBWork.BookManageDB;
import DBWork.MarksDB;
import Models.Book;

public class test {
    public static void main(String[] args) {
        BookManageDB bookManageDB = new BookManageDB();
        System.out.println(bookManageDB.getAllBooks());
    }
}

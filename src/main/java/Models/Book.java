package Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Book extends DBEntity {
    Author author;
    String bookName;
    String annotation;
    int year;

    public String getName(){
        return author.getName();
    }

    public String getSurname(){
        return author.getSurname();
    }

    public Book(Author author, String bookName, String annotation, int year, int id) {
        this.author = author;
        this.bookName = bookName;
        this.annotation = annotation;
        this.year = year;
        this.id = id;
    }
}

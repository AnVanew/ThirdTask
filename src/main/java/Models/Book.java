package Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Book {
    Author author;
    String bookName;
    int year;
    String annotation;

    public String getName(){
        return author.getName();
    }

    public String getSurname(){
        return  author.getSurname();
    }
}

package Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Book {
    Autor autor;
    String bookName;
    int year;
    String annotation;

    public String getName(){
        return autor.getName();
    }

    public String getSurname(){
        return  autor.getSurname();
    }
}

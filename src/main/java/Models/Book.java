package Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Book {
    String name;
    String surname;
    String bookName;
    int year;
    String annotation;
}

package Models;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Author extends DBEntity {

    String name;
    String surname;

    public Author(String name, String surname, int id) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
}

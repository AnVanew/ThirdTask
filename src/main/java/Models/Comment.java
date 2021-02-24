package Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Comment extends DBEntity {
    private String userName;
    private String comment;
}

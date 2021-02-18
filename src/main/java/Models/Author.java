package Models;

import DBWork.DBWorker;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Author {

    String name;
    String surname;

    public int getAuthorId(){
        int authorId=-1;
        String query = "SELECT ID FROM AUTHORS WHERE NAME = ? AND SURNAME = ?";
        Object res =  DBWorker.executeQuery(query, (preparedStatement) -> {
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);},
            (resultSet)->{
            int id = resultSet.getInt(1);
            return id;
        });
        if (res != null) authorId = (int) res;
        return authorId;
    }

}

package Models;

import DBWork.DBWorker;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Autor {


    String name;
    String surname;

    public int getAutorId(){
        int autorId=-1;
        String query = "SELECT ID FROM AUTORS WHERE NAME = '"+name+"' AND SURNAME = '"+surname+"'";
        Object res =  DBWorker.executeQuery(query, (resultSet) ->{
            int id = resultSet.getInt(1);
            return id;
        });
        if (res != null) autorId = (int) res;
        return autorId;
    }

}

package aplicatie.dao;
import aplicatie.model.Departament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//select
//insert

public class DepartamentDao {

    Connection connection;
    PreparedStatement selectQueriy;
    PreparedStatement insertQuery;

    public DepartamentDao(Connection connection) {
        this.connection = connection;
        try {
            insertQuery = connection.prepareStatement("INSERT INTO departamente VALUES (null,?)");
            selectQueriy = connection.prepareStatement("SELECT * FROM departamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insert(String numeDepartament){
        try {
            insertQuery.setString(1,numeDepartament);
            int nrLindiMedicate = 0;
            nrLindiMedicate = insertQuery.executeUpdate();
            return  nrLindiMedicate != 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public  List<Departament>  FindAllById(){
        try (ResultSet resultSet = selectQueriy.executeQuery() ){
            List<Departament> departamente = new ArrayList<>();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                Departament departament = new Departament(id,nume);
                departamente.add(departament);
            }
            return departamente;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}

package aplicatie.dao;

import aplicatie.model.Angajat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AngajatDao {
    Connection connection;
    PreparedStatement insertQuery;
    PreparedStatement deleteQuery;
    PreparedStatement selectQuery;

    public AngajatDao(Connection connection) {
        this.connection = connection;
        try {
            insertQuery = connection.prepareStatement("INSERT INTO angajati VALUES (null, ?, ?, ?)");
            deleteQuery = connection.prepareStatement("DELETE FROM angajati WHERE nume=?");
            selectQuery = connection.prepareStatement("SELECT * FROM angajati WHERE id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insert(Angajat angajat) {
        try {
            insertQuery.setString(1, angajat.getNume());
            insertQuery.setInt(2, angajat.getVarsta());
            insertQuery.setInt(3, angajat.getDepartamentiId());
            return insertQuery.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteByNume(String nume) {
        try {
            deleteQuery.setString(1, nume);
            return   deleteQuery.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Angajat> findAllById(int departamentId) {

        try {
            selectQuery.setInt(1, departamentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (ResultSet result = selectQuery.executeQuery()){
            List<Angajat> angajati = new ArrayList<>();
            while(result.next()) {
                Angajat angajat = new Angajat(
                        result.getInt("id"),
                        result.getString("nume"),
                        result.getInt("varsta"),
                        result.getInt("departament_id")
                );
                angajati.add(angajat);
            }
            return angajati;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}

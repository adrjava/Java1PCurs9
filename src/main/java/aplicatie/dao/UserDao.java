package aplicatie.dao;


import aplicatie.model.User;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class UserDao {
    Connection connection;
    PreparedStatement insertQuery;
    PreparedStatement selectQuery;

    public UserDao(Connection connection) {
        this.connection = connection;
        try {
            insertQuery = connection.prepareStatement("INSERT INTO users VALUES (null, ?,?,?)");
            selectQuery = connection.prepareStatement("SELECT userName, userPassword FROM users WHERE userName = ? AND userPassword = ? ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insert (User user){
        try {
            insertQuery.setString(1, user.getUserName());
            insertQuery.setString(2,user.getPassword());
            insertQuery.setString(3,user.getEmail());
            return insertQuery.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verificaUser(String user, String password) {

        try {
            selectQuery.setString(1, user);
            selectQuery.setString(2,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (ResultSet resultSet = selectQuery.executeQuery()){
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            System.out.println("eroare de conexiune tratata in loginButton.addActionListener(event->{  }");
        }
        return false;
    }
}

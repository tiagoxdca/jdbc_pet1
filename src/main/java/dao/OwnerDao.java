package dao;

import database.DB;
import services.PetCreateRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerDao {

    public int getOwnerId(PetCreateRequest request) throws SQLException {
        PreparedStatement preparedStatement = DB.getDB().queryPrepared("select id from owner where name = ?");
        preparedStatement.setString(1, request.getOwnerName());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            return id;
        }
        return 0;
    }

    public void insertOwner(PetCreateRequest request) throws SQLException {

        String sql = "INSERT INTO owner (name, phone) VALUES (?, ?)";
        try (PreparedStatement statement = DB.getDB().getConn().prepareStatement(sql)) {

                statement.setString(1, request.getOwnerName());
                statement.setString(2, request.getOwnerPhone());
                statement.executeUpdate();
                DB.getDB().getConn().commit();
        }

    }


}

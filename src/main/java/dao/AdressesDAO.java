package dao;

import database.DB;
import services.PetCreateRequest;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdressesDAO {



    public List<String> insertAdressesIntoDatabase(PetCreateRequest request) throws SQLException {

        int ownerId = new OwnerDao().getOwnerId(request);


        List<String> adressesInserted = new ArrayList<>();

        String sql = "INSERT INTO adresses (name, fk_owner) VALUES (?, ?)";
        try (PreparedStatement statement = DB.getDB().getConn().prepareStatement(sql)) {
            for (String adress : request.getAdresses()) {
                    statement.setString(1, adress);
                    statement.setInt(2, ownerId);
                    statement.executeUpdate();
                    DB.getDB().getConn().commit();
                    adressesInserted.add(adress);

            }
        }

        return adressesInserted ;
    }
}

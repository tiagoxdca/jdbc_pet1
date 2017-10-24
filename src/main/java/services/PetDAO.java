package services;

import database.DB;
import domain.Pet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PetDAO {

    PreparedStatement preparedStatement;

    public Pet createPetFromFetch(ResultSet petRS) throws SQLException {
        Integer id = petRS.getInt("id");
        String petName = petRS.getString("name");
        Integer petGender = petRS.getInt("gender");
        Integer owner_id = petRS.getInt("fk_owner");

        //devolver uma instancia Pet
        return Pet.builder().id(id).name(petName).gender(petGender).owner_id(owner_id).build();
    }

    public void insertPetIntoDatabase(PetCreateRequest request, int ownerId) throws SQLException {
        //criar pet
        String insertPet = "insert into pet(name, gender, fk_owner) values (?, ?, ?)";

        preparedStatement = DB.getConnection().queryPrepared(insertPet);
        preparedStatement.setString(1, request.getPetName());
        preparedStatement.setInt(2, request.getPetGender());
        preparedStatement.setInt(3, ownerId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        DB.getConnection().close();
    }

    public ResultSet getPetResultSet(PetCreateRequest request) throws SQLException {
        String petLookupQuery = "select * from pet where name = ?";

        preparedStatement = DB.getConnection().queryPrepared(petLookupQuery);
        preparedStatement.setString(1, request.getPetName());
        return preparedStatement.executeQuery();
    }

    public ResultSet getOwnerResultSet(PetCreateRequest request) throws SQLException {
        preparedStatement = DB.getConnection().queryPrepared("select id from owner where name = ?");
        preparedStatement.setString(1, request.getOwnerName());
        return preparedStatement.executeQuery();
    }

    public PetCreateResponse createPetResponse(PetCreateRequest request) throws SQLException {


        String sql = "select p.name as petName, p.gender, ow.name as ownerName, ow.phone from pet as p inner join owner ow" +
                " on p.fk_owner = ow.id where p.name = ? and ow.name = ?";
        PreparedStatement preparedStatement = DB.getConnection().queryPrepared(sql);
        preparedStatement.setString(1,request.getPetName());
        preparedStatement.setString(2,request.getOwnerName());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String petName = resultSet.getString("petName");
            Integer gender = resultSet.getInt("gender");
            String ownerName = resultSet.getString("ownerName");
            String phone = resultSet.getString("phone");
            PetCreateResponse response = PetCreateResponse.builder().petName(petName).petGender(gender).ownerName(ownerName).ownerPhone(phone).build();
            return response;

        }

        return null;

    }
}

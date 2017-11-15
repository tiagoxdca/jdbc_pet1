package dao;

import database.DB;
import domain.Pet;
import services.PetCreateRequest;
import services.PetCreateResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        preparedStatement = DB.getDB().queryPrepared(insertPet);
        preparedStatement.setString(1, request.getPetName());
        preparedStatement.setInt(2, request.getPetGender());
        preparedStatement.setInt(3, ownerId);
        preparedStatement.executeUpdate();

    }

    public ResultSet getPetResultSet(PetCreateRequest request) throws SQLException {
        String petLookupQuery = "select * from pet where name = ?";


        preparedStatement = DB.getDB().queryPrepared(petLookupQuery);
        preparedStatement.setString(1, request.getPetName());
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    public List<String> matchPetWithOwner_returnOwnerAdresses(PetCreateRequest request) throws SQLException {

        List<String> adresses = new ArrayList<>();

       String sql =  "insert into pet (name, gender, fk_owner) values (?, ?, ?)";
        preparedStatement = DB.getDB().getConn().prepareStatement(sql);
        preparedStatement.setString(1, request.getPetName());
        preparedStatement.setInt(2, request.getPetGender());
        preparedStatement.setInt(3,new OwnerDao().getOwnerId(request));
        preparedStatement.executeUpdate();
        DB.getDB().getConn().commit();

        new AdressesDAO().insertAdressesIntoDatabase(request);

        sql = "select distinct adress.name as adressName from pet p right join owner as ow on p.fk_owner = ow.id right join adresses adress on adress.fk_owner = ow.id where ow.id = ? order by adress.name asc";
        preparedStatement = DB.getDB().getConn().prepareStatement(sql);
        preparedStatement.setInt(1, new OwnerDao().getOwnerId(request));
        ResultSet resultSet = preparedStatement.executeQuery();
        DB.getDB().getConn().commit();
        while (resultSet.next()){
            String nameAdress = resultSet.getString("adressName");
            adresses.add(nameAdress);
        }
        return adresses;
    }

    public PetCreateResponse createPetResponse(PetCreateRequest request) throws SQLException {


        String sql = "select p.name as petName, p.gender, ow.name as ownerName, ow.phone from pet as p inner join owner ow" +
                " on p.fk_owner = ow.id where p.name = ? and ow.name = ?";
        PreparedStatement preparedStatement = DB.getDB().queryPrepared(sql);
        preparedStatement.setString(1,request.getPetName());
        preparedStatement.setString(2,request.getOwnerName());
        ResultSet resultSet = preparedStatement.executeQuery();
        DB.getDB().getConn().commit();
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

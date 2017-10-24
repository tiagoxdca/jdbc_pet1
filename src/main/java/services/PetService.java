package services;

import database.DB;
import domain.Pet;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PetService {
    private PetDAO petDAO;

    public PetCreateResponse createResponse(PetCreateRequest request) throws SQLException {

        return petDAO.createPetResponse(request);
    }

    public PetService(PetDAO petDAO){

        this.petDAO = petDAO;
    }

    public Pet create(PetCreateRequest request) throws SQLException {

        //find owner
        int ownerId = 0;
        ResultSet ownerRS = petDAO.getOwnerResultSet(request);

        //guardar owner id
        while (ownerRS.next()) { //expected one
            ownerId = ownerRS.getInt("id"); //guardar id owner
        }

        //create pet into database
        petDAO.insertPetIntoDatabase(request, ownerId);

        //buscar info pet da bd
        ResultSet petRS = petDAO.getPetResultSet(request);
        if (petRS.next()) { //expected one
            Pet pet = petDAO.createPetFromFetch(petRS);
            closeConnections(ownerRS, petRS);
            return pet;
        }
        return null;
    }

    private void closeConnections(ResultSet ownerRS, ResultSet petRS) throws SQLException {
        ownerRS.close();
        petRS.close();
        DB.getConnection().close();
    }


}

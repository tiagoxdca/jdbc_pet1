package services;

import dao.OwnerDao;
import dao.PetDAO;
import database.DB;
import domain.Pet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
        ownerId = new OwnerDao().getOwnerId(request);

        //create pet into database
        petDAO.insertPetIntoDatabase(request, ownerId);

        //buscar info pet da bd
        ResultSet petRS = petDAO.getPetResultSet(request);
        if (petRS.next()) { //expected one
            Pet pet = petDAO.createPetFromFetch(petRS);
            return pet;
        }
        return null;
    }

    public PetCreateResponse createPet_returnOwnerAdresses(PetCreateRequest request) throws SQLException {
        List<String> adresses = petDAO.matchPetWithOwner_returnOwnerAdresses(request);
        return new PetCreateResponse().builder().listAdresses(adresses).build();
    }

    private void closeConnections(ResultSet ownerRS, ResultSet petRS) throws SQLException {
        ownerRS.close();
        petRS.close();
        DB.getDB().close();
    }


}

package services;

import com.github.javafaker.Faker;
import database.DB;
import domain.Pet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.beans.Transient;
import java.sql.SQLException;

public class PetServiceUT {

    PetService petService;
    PetDAO petDAO;

    @Before
    public void setUp() throws SQLException {
        DB.getDB().getConn().setAutoCommit(false);
        petDAO = new PetDAO();
        petService = new PetService(petDAO);

        String string = new Faker().address().streetAddress().toString();

    }



    @After
    public void tearDown() throws SQLException {

        DB.getDB().getConn().rollback();

    }

    @Test
    public void createPet_validRequest_sucess() throws SQLException {

        Pet savedPt = petService.create(buildRequest("Adriano", "Cubito", 1));


        Assert.assertNotNull(savedPt);
        Assert.assertEquals("Cubito", savedPt.getName());
    }

    @Test
    public void createPet_validResponse_sucess() throws SQLException {
        PetCreateRequest request = buildRequest("Andre", "Bob2", 0);
        PetCreateResponse response = petService.createResponse(request);
        Assert.assertNotNull(response);
        Assert.assertEquals("Andre", response.getOwnerName());
        Assert.assertEquals("Bob2", response.getPetName());
        Assert.assertEquals(0, response.getPetGender());
        //linha de teste de git
    }

    private PetCreateRequest buildRequest(String ownerName,
                                          String petName,
                                          int petGender) {

        PetCreateRequest request = PetCreateRequest.builder()
                .ownerName(ownerName)
                .petName(petName)
                .petGender(petGender)
                .build();
        return request;
    }
}

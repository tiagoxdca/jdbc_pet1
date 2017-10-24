package services;

import domain.Pet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class PetServiceUT {

    PetService petService;
    PetDAO petDAO;

    @Before
    public void setUp() {
        petDAO = new PetDAO();
        petService = new PetService(petDAO);

    }

    @Test
    public void createPet_validRequest_sucess() throws SQLException {

        Pet savedPt = petService.create(buildRequest("Andre", "Bob2", 0));

        Assert.assertNotNull(savedPt);
        Assert.assertEquals("Bob2", savedPt.getName());
    }

    @Test
    public void createPet_validResponse_sucess() throws SQLException {
        PetCreateRequest request = buildRequest("Andre", "Bob2", 0);
        PetCreateResponse response = petService.createResponse(request);
        Assert.assertNotNull(response);
        Assert.assertEquals("Andre", response.getOwnerName());
        Assert.assertEquals("Bob2", response.getPetName());
        Assert.assertEquals(0, response.getPetGender());
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

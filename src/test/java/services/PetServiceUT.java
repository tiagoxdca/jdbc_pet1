package services;

import com.github.javafaker.Faker;
import dao.PetDAO;
import database.DB;
import domain.Pet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetServiceUT {

    PetService petService;
    PetDAO petDAO;
    List<String> adresses;

    @Before
    public void setUp() throws SQLException {
        DB.getDB().getConn().setAutoCommit(false);
        petDAO = new PetDAO();
        petService = new PetService(petDAO);
        adresses = new ArrayList<>();

        int random = (int) (Math.random() * 3);
        for (int i = 0; i <= random; i++) {
            adresses.add(new Faker().address().streetAddress().toString());
        }


    }



    @After
    public void tearDown() throws SQLException {

        DB.getDB().getConn().rollback();
        DB.getDB().getConn().close();

    }

    @Test
    public void createPet_validRequest_sucess() throws SQLException {

        Pet savedPt = petService.create(buildRequest("Trump", "Melania", 1, adresses));


        Assert.assertNotNull(savedPt);
        Assert.assertEquals("Melania", savedPt.getName());

    }

    @Test
    public void createPet_validResponse_sucess() throws SQLException {
        PetCreateRequest request = buildRequest("Andre", "Bob2", 0, adresses);
        PetCreateResponse response = petService.createResponse(request);
        Assert.assertNotNull(response);
        Assert.assertEquals("Andre", response.getOwnerName());
        Assert.assertEquals("Bob2", response.getPetName());
        Assert.assertEquals(0, response.getPetGender());
        //linha de teste de git
    }

    private PetCreateRequest buildRequest(String ownerName,
                                          String petName,
                                          int petGender, List<String> adresses) {

        PetCreateRequest request = PetCreateRequest.builder()
                .ownerName(ownerName)
                .petName(petName)
                .petGender(petGender)
                .adresses(adresses)
                .build();
        return request;
    }

    @Test
    public void createPet_returnOwnerAdresses(){
        PetCreateRequest request = buildRequest("Jose", "Bibi", 0, adresses);
        PetCreateResponse response = null;
        try {
           response = petService.createPet_returnOwnerAdresses(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<String> listAdresses = response.getListAdresses();

        Assert.assertNotNull(listAdresses);

    }
}

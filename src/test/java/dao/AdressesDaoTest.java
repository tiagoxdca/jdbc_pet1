package dao;

import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.PetCreateRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdressesDaoTest {

    List<String> adresses;

    @Before
    public void setup(){

        adresses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
           adresses.add(new Faker().address().streetAddress());
        }

    }

    @Test
    public void insertAdresses_Sucessfully() throws SQLException {
        PetCreateRequest request = new PetCreateRequest();
        request.setOwnerName("Tiago");
        request.setAdresses(adresses);
        AdressesDAO adressesDAO = new AdressesDAO();
        List<String> adressesReturned = adressesDAO.insertAdressesIntoDatabase(request);


        Assert.assertNotNull(adressesReturned);
        Assert.assertEquals(10, adressesReturned.size());


    }


}

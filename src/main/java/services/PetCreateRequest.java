package services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetCreateRequest {

    private String ownerName;

    private String petName;
    private String ownerPhone;

    private Integer petGender;

    private List<String> adresses;
}

package services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetCreateRequest {

    private String ownerName;

    private String petName;

    private Integer petGender;

    private  String adress;
}

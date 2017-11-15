package services;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PetCreateResponse {

    private String petName;

    private int petGender;

    private String ownerName;

    private String ownerPhone;
    private List<String> listAdresses;
}

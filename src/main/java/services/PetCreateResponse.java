package services;

import lombok.*;

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
}

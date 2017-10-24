package domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Pet {

    private Integer id;
    private String name;
    private int gender;
    private int owner_id;
}

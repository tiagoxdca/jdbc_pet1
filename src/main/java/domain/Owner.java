package domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Owner {

    private Integer id;
    private String name;
    private String telefone;
}

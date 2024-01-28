package univ.iwa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionDto {
    private Long id;
    private Date date;
    private String name;
    private String email;
    private String mots_cles;
    private boolean status;

    public InscriptionDto(String name, String email,String motsCles) {
        this.name = name;
        this.email=email;
        this.mots_cles=motsCles;
    }
}

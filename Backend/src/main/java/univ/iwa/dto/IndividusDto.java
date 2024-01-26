package univ.iwa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import univ.iwa.model.Individus;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndividusDto {
    private Long id;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String ville;
    private String email;
    private String telephone;
    //private Long groupe;
}

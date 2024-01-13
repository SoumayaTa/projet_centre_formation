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
    private Date date_de_naissance;
    private String ville;
    private String email;
    private String telephone;

    public static IndividusDto toDto(Individus individus){
        return IndividusDto.builder()
                .id(individus.getId())
                .nom(individus.getNom())
                .prenom(individus.getPrenom())
                .date_de_naissance(individus.getDate_de_naissance())
                .email(individus.getEmail())
                .ville(individus.getVille())
                .telephone(individus.getTelephone())
                .build();
    }
}

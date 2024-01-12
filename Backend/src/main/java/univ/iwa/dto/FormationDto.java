package univ.iwa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import univ.iwa.model.Formation;
import univ.iwa.model.UserInfo;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormationDto {
    private Long id;
    private String nom;
    private Long nombreHeur;
    private Long cout;
    private String objectifs;
    private String programme;
    private String categorie;
    private String ville;
    private LocalDate date;

    public static FormationDto toDto(Formation formation){
        return FormationDto.builder()
                .id(formation.getId())
                .nom(formation.getNom())
                .nombreHeur(formation.getNombreHeur())
                .cout(formation.getCout())
                .objectifs(formation.getObjectifs())
                .programme(formation.getProgramme())
                .categorie(formation.getCategorie())
                .ville(formation.getVille())
                .date(formation.getDate())
                .build();
    }
}

package univ.iwa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import univ.iwa.dto.FormationDto;

import java.time.LocalDate;
import java.util.Date;
@Builder
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Long nombreHeur;
    private Long cout;
    private String objectifs;
    private String programme;
    private String categorie;
    private String ville;
    private LocalDate date;

    public static Formation toEntity(FormationDto form) {
        return Formation.builder()
                .id(form.getId())
                .nom(form.getNom())
                .nombreHeur(form.getNombreHeur())
                .cout(form.getCout())
                .objectifs(form.getObjectifs())
                .programme(form.getProgramme())
                .categorie(form.getCategorie())
                .ville(form.getVille())
                .date(form.getDate())
                .build();
    }
}

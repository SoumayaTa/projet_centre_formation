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
    private String photos;
    private Long groupe_seuil;
    public FormationDto(String nom, Long nombreHeur, Long cout, String objectifs, String programme, String categorie, String ville,Long groupe_seuil) {
        this.nom = nom;
        this.nombreHeur = nombreHeur;
        this.cout = cout;
        this.objectifs = objectifs;
        this.programme = programme;
        this.categorie = categorie;
        this.ville = ville;
        this.groupe_seuil=groupe_seuil;
    }

}

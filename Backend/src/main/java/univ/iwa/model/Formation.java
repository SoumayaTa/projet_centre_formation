package univ.iwa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import univ.iwa.dto.FormationDto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
    private  String photos;
    private Long groupe_seuil;

    @OneToMany(mappedBy = "formation")
    private List<Individus> inscrits;

    @OneToMany(mappedBy = "formation")
    private List<Groupe> groupes;




}

package univ.iwa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Individus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String ville;
    private String email;
    private String telephone;

    @ManyToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @OneToOne(mappedBy = "individus", cascade = CascadeType.ALL)
    private Evaluation evaluation;


}

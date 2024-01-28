package univ.iwa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;
}

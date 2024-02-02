package univ.iwa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long qualite_p;
    private Long rythme;
    private Long scours;
    private Long stp;
    private Long maitrise;
    private Long totalpercent;

    @OneToOne
    @JoinColumn(name = "individus_id")
    private Individus individus;

    @ManyToOne
    @JoinColumn(name = "user_info_id")
    private UserInfo formateur;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "formation_id", nullable = false)
    private Formation formation;

    public void calculateTotalPercent() {
        if (qualite_p != null && rythme != null && scours != null && stp != null && maitrise != null) {
            totalpercent = (qualite_p + rythme + scours + stp + maitrise) / 5;
        } else {
            totalpercent = null;
        }
    }


}

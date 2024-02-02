package univ.iwa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "groupe")
    private List<Individus> inscrits;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;


    @ManyToOne
    @JoinColumn(name = "user_info_id")
    private UserInfo formateur;




}

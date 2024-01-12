package univ.iwa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import univ.iwa.dto.EntrepriseDto;
@Builder
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String url;

    public static Entreprise toEntity(EntrepriseDto entreprise) {
        return Entreprise.builder()
                .id(entreprise.getId())
                .name((entreprise.getName()))
                .address(entreprise.getAddress())
                .url(entreprise.getUrl())
                .phoneNumber(entreprise.getPhoneNumber())
                .email(entreprise.getEmail()).build();
    }
}

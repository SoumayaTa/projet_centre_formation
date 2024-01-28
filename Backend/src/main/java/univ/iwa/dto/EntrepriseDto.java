package univ.iwa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import univ.iwa.model.Entreprise;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrepriseDto {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String url;

}

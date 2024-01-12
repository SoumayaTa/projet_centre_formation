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
    private long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String url;

    public static EntrepriseDto toDto(Entreprise entreprise){
        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .name(entreprise.getName())
                .address(entreprise.getAddress())
                .phoneNumber(entreprise.getPhoneNumber())
                .email(entreprise.getEmail())
                .url(entreprise.getUrl())
                .build();
    }
}

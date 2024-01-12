package univ.iwa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import univ.iwa.model.Calendrier;
import univ.iwa.model.Entreprise;

import java.util.Date;
@Builder
@Data @NoArgsConstructor @AllArgsConstructor
public class CalendrierDto {
    private Long id;
    private Date datedebut;
    private Date datefin;
    private Long formationId;
    private int formateurId;
    private Long entrepriseId;

    public static CalendrierDto toDto(Calendrier calendrier){
        return CalendrierDto.builder()
                .id(calendrier.getId())
                .datedebut(calendrier.getDatedebut())
                .datefin(calendrier.getDatefin())
                .formationId(calendrier.getFormation().getId())
                .formateurId(calendrier.getFormateur().getId())
                .entrepriseId(calendrier.getEntreprise().getId())
                .build();
    }
}

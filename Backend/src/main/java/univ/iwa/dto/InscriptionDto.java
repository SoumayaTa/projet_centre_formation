package univ.iwa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionDto {
    private Long id;
    private Long individusId;
    private Long formationId;
    private Date date;
    private boolean confirme;
}

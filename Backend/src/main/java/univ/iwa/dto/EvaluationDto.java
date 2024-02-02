package univ.iwa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDto {
    private Long id;
    private Long qualite_p;
    private Long rythme;
    private Long scours;
    private Long stp;
    private Long maitrise;
    private Long totalpercent;
}

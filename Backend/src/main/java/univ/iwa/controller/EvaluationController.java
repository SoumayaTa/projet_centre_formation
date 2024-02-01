package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import univ.iwa.dto.EvaluationDto;
import univ.iwa.service.EvaluationService;

@RestController
@RequestMapping("/evaluation")
@CrossOrigin(origins = "*")
public class EvaluationController {
    @Autowired
    EvaluationService service;

   @PostMapping("/add/{individuid}")
    public EvaluationDto addEvaluation(@RequestBody EvaluationDto evaluationDto,
                                       @PathVariable Long individuid) {
       System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        return service.addEvaluation(evaluationDto,individuid);
    }
}

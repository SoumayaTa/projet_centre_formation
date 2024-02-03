package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/averageRating/{formateurId}")
    public ResponseEntity<Double> getAverageRatingForFormateur(@PathVariable Long formateurId) {
        Double averageRating = service.calculateAverageRatingForFormateur(formateurId);

        if (averageRating != null) {
            return ResponseEntity.ok(averageRating);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/hasSubmittedFeedback")
    public ResponseEntity<Boolean> hasSubmittedFeedback(@RequestParam Long userId) {
        boolean hasSubmittedFeedback = service.hasSubmittedFeedback(userId);
        return ResponseEntity.ok(hasSubmittedFeedback);
    }

}

package univ.iwa.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.iwa.dto.EvaluationDto;
import univ.iwa.model.Evaluation;
import univ.iwa.model.Individus;
import univ.iwa.model.UserInfo;
import univ.iwa.repository.EvaluationRepository;
import univ.iwa.repository.FormationRepository;
import univ.iwa.repository.IndividusRepository;
import univ.iwa.repository.UserInfoRepository;
import univ.iwa.model.Formation;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import univ.iwa.model.Calendrier;
@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IndividusRepository indrepo;
    @Autowired
    private UserInfoRepository userepo;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private FormationRepository frepo;
    public EvaluationDto addEvaluation(EvaluationDto evaluationDto, Long individuId) {
        Optional<Individus> individuOptional = indrepo.findById(individuId);
        Individus individu = individuOptional.get();
        Optional<UserInfo> formateurOptional = userepo.findById(individu.getGroupe().getFormateur().getId());
        UserInfo formateur = formateurOptional.get();
        Long formationId = individu.getFormation().getId();
        Optional<Formation> formationOptional = frepo.findById(formationId);
        Formation formation = formationOptional.get();
        Evaluation evaluation = modelMapper.map(evaluationDto, Evaluation.class);
        evaluation.setIndividus(individu);
        evaluation.setFormation(formation);
        evaluation.setFormateur(formateur);
        evaluation.calculateTotalPercent();
        sendThanksForFeedbackEmail(individuId);
        return modelMapper.map(repository.save(evaluation), EvaluationDto.class);
    }

    public void sendThanksForFeedbackEmail(Long individuId) {
        Individus individu = indrepo.findById(individuId)
                .orElseThrow(() -> new EntityNotFoundException("Individu not found with ID: " + individuId));
            String to = individu.getEmail();
            String cc = "tayoubsoumaya21@gmail.com";
            String subject = "Thanks for Your Feedback";
            String body = "On a bien recu votre feedback on va le prendre en compte pour ameliorer notre service ";
            emailService.sendMail(to, cc, subject,body);
    }
    public Double calculateAverageRatingForFormateur(Long formateurId) {
        List<Evaluation> evaluations = repository.findByFormateurId(formateurId);
        if (evaluations != null && !evaluations.isEmpty()) {
            double totalRating = 0;
            int numberOfEvaluations = 0;
            for (Evaluation evaluation : evaluations) {
                if (evaluation.getTotalpercent() != null) {
                    totalRating += evaluation.getTotalpercent();
                    numberOfEvaluations++;
                }
            }
            if (numberOfEvaluations > 0) {
                return totalRating / numberOfEvaluations;
            }
        }
        return null;
    }

    public boolean hasSubmittedFeedback(Long userId) {
        return repository.existsByIndividusId(userId);
    }
}

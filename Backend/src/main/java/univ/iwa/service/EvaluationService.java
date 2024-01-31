package univ.iwa.service;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.iwa.dto.EvaluationDto;
import univ.iwa.model.Evaluation;
import univ.iwa.model.Individus;
import univ.iwa.model.UserInfo;
import univ.iwa.repository.EvaluationRepository;
import univ.iwa.repository.IndividusRepository;
import univ.iwa.repository.UserInfoRepository;
import univ.iwa.model.Formation;
import java.util.Date;

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

//    public EvaluationDto addEvaluation(EvaluationDto evaluationDto, Long individuId) {
//        Individus individu = indrepo.findById(individuId)
//                .orElseThrow(() -> new EntityNotFoundException("Individu not found with ID: " + individuId));
//
//        Evaluation evaluation = modelMapper.map(evaluationDto, Evaluation.class);
//        evaluation.setIndividus(individu);
//
//        UserInfo formateur = userepo.findById(individu.getGroupe().getFormateur().getId())
//                .orElseThrow(() -> new EntityNotFoundException("Formateur not found for Individu with ID: " + individuId));
//        evaluation.setFormateur(formateur);
//        Formation formation = individu.getFormation();
//        if (formation != null && formation.getCalendrier() != null) {
//            evaluation.setFormation(formation);
//            Date dateFinFormation = evaluation.getFormation().getCalendrier().getDatefin();
//            Date currentDate = new Date();
//            if (currentDate.after(dateFinFormation)) {
//                notifyEvaluationCompletion(evaluation, individu);
//            }
//            return modelMapper.map(repository.save(evaluation), EvaluationDto.class);
//        } else {
//            throw new EntityNotFoundException("Formation or Calendrier is null for Individu with ID: " + individuId);
//        }
//    }


    private void notifyEvaluationCompletion(Evaluation evaluation, Individus individu) {
        String email = individu.getEmail();
        if (email != null) {
            String to = email;
            String cc = "tayoubsoumaya21@gmail.com";
            String subject = "Evaluation Completion Notification";

            // Add implementation to notify or send email
            emailService.sendMail(to, cc, subject, "http://localhost:4200/evaluation?id=" + evaluation.getId());
        } else {
            // Handle the case where UserInfo is not available
            throw new EntityNotFoundException("UserInfo not found for Individu with ID: " + individu.getId());
        }
    }
}

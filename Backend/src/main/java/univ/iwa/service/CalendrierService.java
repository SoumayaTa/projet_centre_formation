package univ.iwa.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.iwa.dto.CalendrierDto;
import univ.iwa.model.*;
import univ.iwa.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalendrierService {
    @Autowired
    private CalendrierRepository repository;

    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private  EmailServiceImpl emailService;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private EntrepriseRepository entrepriseRepository;
    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private ModelMapper modelMapper;

   /* public CalendrierDto addCalendrier(CalendrierDto calendrierDto, Long formationId, int formateurId, Long entrepriseId) {
        Optional<Formation> formation = formationRepository.findById(formationId);
        Optional<UserInfo> formausteur = userInfoRepository.findById(formateurId);
        Optional<Entreprise> entreprise = entrepriseRepository.findById(entrepriseId);

            Calendrier entity = new Calendrier();
            entity.setDatedebut(calendrierDto.getDatedebut());
            entity.setDatefin(calendrierDto.getDatefin());
            entity.setFormation(formation.get());
            entity.setFormateur(formateur.get());
            entity.setEntreprise(entreprise.get());
            return CalendrierDto.toDto( repository.save(entity));
    }*/

    public CalendrierDto addCalendrier(CalendrierDto calendrierDto, Long formationId, int formateurId, Long entrepriseId, Long groupeId) {
        Formation formationEntity = formationRepository.findById(formationId)
                .orElseThrow(() -> new EntityNotFoundException("Formation not found with ID: " + formationId));

        UserInfo formateurEntity = userInfoRepository.findById(formateurId)
                .orElseThrow(() -> new EntityNotFoundException("Formateur not found with ID: " + formateurId));

        Entreprise entrepriseEntity = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new EntityNotFoundException("Entreprise not found with ID: " + entrepriseId));

        Groupe groupeEntity = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new EntityNotFoundException("Groupe not found with ID: " + groupeId));
        groupeEntity.setFormateur(formateurEntity);
        Calendrier entity = modelMapper.map(calendrierDto, Calendrier.class);
        entity.setFormation(formationEntity);
        entity.setFormateur(formateurEntity);
        entity.setEntreprise(entrepriseEntity);
        entity.setGroupe(groupeEntity);

        return modelMapper.map(repository.save(entity), CalendrierDto.class);
    }


    public List<CalendrierDto> getEvents() {
        List<Calendrier> events = repository.findAll();
        return events.stream()
                .map(event -> modelMapper.map(event, CalendrierDto.class))
                .collect(Collectors.toList());
    }

//
//    private void notifyIfDateExceeded() {
//        List<Calendrier> calendriers = repository.findAll();
//        for (Calendrier calendrier : calendriers) {
//            if (calendrier.isFormationTerminee()) {
//                Hibernate.initialize(calendrier.getFormation().getInscrits());
//                for (Individus inscrit : calendrier.getFormation().getInscrits()) {
//                    String email = inscrit.getEmail();
//                    if (email != null) {
//                        String to = email;
//                        String cc = "tayoubsoumaya21@gmail.com";
//                        String subject = "Formation Date Exceeded Notification";
//                        emailService.sendMail(to, cc, subject, "http://localhost:4200/evaluation?id=" + inscrit.getId());
//                    } else {
//                        throw new EntityNotFoundException("Email not found for Individu with ID: " + inscrit.getId());
//                    }
//                }
//            }
//        }
//    }
    public void deleteCalendriersByEntreprise(Entreprise entreprise) {
    	repository.deleteByEntreprise(entreprise);
    }
}

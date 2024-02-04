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

//    public CalendrierDto addCalendrier(CalendrierDto calendrierDto, Long formationId, int formateurId, Long entrepriseId, Long groupeId) {
//        Formation formationEntity = formationRepository.findById(formationId)
//                .orElseThrow(() -> new EntityNotFoundException("Formation not found with ID: " + formationId));
//
//        UserInfo formateurEntity = userInfoRepository.findById(formateurId)
//                .orElseThrow(() -> new EntityNotFoundException("Formateur not found with ID: " + formateurId));
//
//        Entreprise entrepriseEntity = entrepriseRepository.findById(entrepriseId)
//                .orElseThrow(() -> new EntityNotFoundException("Entreprise not found with ID: " + entrepriseId));
//
//        Groupe groupeEntity = groupeRepository.findById(groupeId)
//                .orElseThrow(() -> new EntityNotFoundException("Groupe not found with ID: " + groupeId));
//        groupeEntity.setFormateur(formateurEntity);
//        Calendrier entity = modelMapper.map(calendrierDto, Calendrier.class);
//        entity.setFormation(formationEntity);
//        entity.setFormateur(formateurEntity);
//        entity.setEntreprise(entrepriseEntity);
//        entity.setGroupe(groupeEntity);
//
//        return modelMapper.map(repository.save(entity), CalendrierDto.class);
//    }
        public CalendrierDto addCalendrier(CalendrierDto calendrierDto, Long formationId, int formateurId, String select) {
            System.out.println("ana hnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa 3333333");

            Formation formationEntity = formationRepository.findById(formationId)
                    .orElseThrow(() -> new EntityNotFoundException("Formation not found with ID: " + formationId));

            UserInfo formateurEntity = userInfoRepository.findById(formateurId)
                    .orElseThrow(() -> new EntityNotFoundException("Formateur not found with ID: " + formateurId));

            Calendrier entity = modelMapper.map(calendrierDto, Calendrier.class);
            entity.setFormation(formationEntity);
            entity.setFormateur(formateurEntity);
            entity.setTitle(calendrierDto.getTitle());
            if ("entreprise".equals(select)) {
                if (calendrierDto.getEntrepriseId() != null) {
                    Entreprise entrepriseEntity = entrepriseRepository.findById(calendrierDto.getEntrepriseId())
                            .orElseThrow(() -> new EntityNotFoundException("Entreprise not found with ID: " + calendrierDto.getEntrepriseId()));
                    entity.setEntreprise(entrepriseEntity);
                    System.out.println();
                } else {
                    entity.setEntreprise(null);
                }
                entity.setGroupe(null);
            } else if ("groupe".equals(select)) {
                if (calendrierDto.getGroupeId() != null) {
                    Groupe groupeEntity = groupeRepository.findById(calendrierDto.getGroupeId())
                            .orElseThrow(() -> new EntityNotFoundException("Groupe not found with ID: " + calendrierDto.getGroupeId()));
                    entity.setGroupe(groupeEntity);
                } else {
                    entity.setGroupe(null);
                }
                entity.setEntreprise(null);
            } else {
                throw new IllegalArgumentException("Invalid value for 'select'");
            }

            return modelMapper.map(repository.save(entity), CalendrierDto.class);
        }

    public List<CalendrierDto> getEvents() {
        List<Calendrier> events = repository.findAll();
        return events.stream()
                .map(event -> {

                    CalendrierDto calendrierDto = modelMapper.map(event, CalendrierDto.class);

                    if( event.getFormateur() != null)  calendrierDto.setFormateurId(event.getFormateur().getId());
                    if( event.getGroupe() != null)  calendrierDto.setGroupeId(event.getGroupe().getId());
                    if( event.getFormation() != null) calendrierDto.setFormationId(event.getFormation().getId());
                    if( event.getEntreprise() != null) calendrierDto.setEntrepriseId(event.getEntreprise().getId());
                    return calendrierDto ;
                })
                .collect(Collectors.toList());
    }
    public List<CalendrierDto> getEventsByFormateurId(int formateurId) {
        List<Calendrier> events = repository.findByFormateur_Id(formateurId);
        return events.stream()
                .map(event -> {
                    CalendrierDto calendrierDto = modelMapper.map(event, CalendrierDto.class);

                    if (event.getFormateur() != null) calendrierDto.setFormateurId(event.getFormateur().getId());
                    if (event.getGroupe() != null) calendrierDto.setGroupeId(event.getGroupe().getId());
                    if (event.getFormation() != null) calendrierDto.setFormationId(event.getFormation().getId());
                    if (event.getEntreprise() != null) calendrierDto.setEntrepriseId(event.getEntreprise().getId());

                    return calendrierDto;
                })
                .collect(Collectors.toList());
    }



    public CalendrierDto getEventById(Long eventId) {
        Calendrier eventEntity = repository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with ID: " + eventId));

        CalendrierDto calendrierDto = modelMapper.map(eventEntity, CalendrierDto.class);

        if (eventEntity.getFormateur() != null) {
            calendrierDto.setFormateurId(eventEntity.getFormateur().getId());

        }

        if (eventEntity.getGroupe() != null) {
            calendrierDto.setGroupeId(eventEntity.getGroupe().getId());
        }

        if (eventEntity.getFormation() != null) {
            calendrierDto.setFormationId(eventEntity.getFormation().getId());

        }

        if (eventEntity.getEntreprise() != null) {
            calendrierDto.setEntrepriseId(eventEntity.getEntreprise().getId());
        }

        return calendrierDto;
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

    public CalendrierDto updateCalendrier(CalendrierDto calendrierDto,String select) {
        System.out.println("ana hnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa update 3333333");

        Calendrier existingCalendrier = repository.findById(calendrierDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Calendrier not found with ID: " + calendrierDto.getId()));

        Formation formationEntity = formationRepository.findById(calendrierDto.getFormationId())
                .orElseThrow(() -> new EntityNotFoundException("Formation not found with ID: " + calendrierDto.getFormationId()));

        UserInfo formateurEntity = userInfoRepository.findById(calendrierDto.getFormateurId())
                .orElseThrow(() -> new EntityNotFoundException("Formateur not found with ID: " + calendrierDto.getFormateurId()));

        existingCalendrier.setFormation(formationEntity);
        existingCalendrier.setFormateur(formateurEntity);
        existingCalendrier.setTitle(calendrierDto.getTitle());

        if ("entreprise".equals(select)) {
            if (calendrierDto.getEntrepriseId() != null) {
                Entreprise entrepriseEntity = entrepriseRepository.findById(calendrierDto.getEntrepriseId())
                        .orElseThrow(() -> new EntityNotFoundException("Entreprise not found with ID: " + calendrierDto.getEntrepriseId()));
                existingCalendrier.setEntreprise(entrepriseEntity);
            } else {
                existingCalendrier.setEntreprise(null);
            }
            existingCalendrier.setGroupe(null);
        } else if ("groupe".equals(select)) {
            if (calendrierDto.getGroupeId() != null) {
                Groupe groupeEntity = groupeRepository.findById(calendrierDto.getGroupeId())
                        .orElseThrow(() -> new EntityNotFoundException("Groupe not found with ID: " + calendrierDto.getGroupeId()));
                existingCalendrier.setGroupe(groupeEntity);
            } else {
                existingCalendrier.setGroupe(null);
            }
            existingCalendrier.setEntreprise(null);
        } else {
            throw new IllegalArgumentException("Invalid value for 'select'");
        }

        // Enregistrez les modifications apportées à l'entité existante
        return modelMapper.map(repository.save(existingCalendrier), CalendrierDto.class);
    }



}

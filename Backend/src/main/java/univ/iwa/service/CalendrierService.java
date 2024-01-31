package univ.iwa.service;

import jakarta.persistence.EntityNotFoundException;
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
}

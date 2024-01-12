package univ.iwa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.iwa.dto.CalendrierDto;
import univ.iwa.model.Calendrier;
import univ.iwa.model.Entreprise;
import univ.iwa.model.Formation;
import univ.iwa.model.UserInfo;
import univ.iwa.repository.CalendrierRepository;
import univ.iwa.repository.EntrepriseRepository;
import univ.iwa.repository.FormationRepository;
import univ.iwa.repository.UserInfoRepository;

import java.util.Optional;

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

    public CalendrierDto addCalendrier(CalendrierDto calendrierDto, Long formationId, int formateurId, Long entrepriseId) {
        Optional<Formation> formation = formationRepository.findById(formationId);
        Optional<UserInfo> formateur = userInfoRepository.findById(formateurId);
        Optional<Entreprise> entreprise = entrepriseRepository.findById(entrepriseId);

            Calendrier entity = new Calendrier();
            entity.setDatedebut(calendrierDto.getDatedebut());
            entity.setDatefin(calendrierDto.getDatefin());
            entity.setFormation(formation.get());
            entity.setFormateur(formateur.get());
            entity.setEntreprise(entreprise.get());
            return CalendrierDto.toDto( repository.save(entity));
    }
}

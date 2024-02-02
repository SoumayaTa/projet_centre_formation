package univ.iwa.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.iwa.dto.GroupeDto;
import univ.iwa.dto.IndividusDto;
import univ.iwa.model.Formation;
import univ.iwa.model.Groupe;
import univ.iwa.model.Individus;
import univ.iwa.repository.FormationRepository;
import univ.iwa.repository.GroupeRepository;
import univ.iwa.repository.IndividusRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndividusService {

    @Autowired
    private IndividusRepository individusRepository;

    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public IndividusDto inscription(IndividusDto individuDto, Long formationId) {
        Formation formation = formationRepository.findById(formationId).orElseThrow(() -> new IllegalArgumentException("Formation not found"));
        Groupe groupe = getOrCreateGroupe(formation);
        Individus individus = modelMapper.map(individuDto, Individus.class);
        individus.setGroupe(groupe);
        individus.setFormation(formation);
        Individus savedIndividu = individusRepository.save(individus);
        return modelMapper.map(savedIndividu, IndividusDto.class);
    }

    private Groupe getOrCreateGroupe(Formation formation) {
        List<Groupe> existingGroupes = formation.getGroupes();
        if (existingGroupes == null || existingGroupes.isEmpty()) {
            return createNewGroupe(formation);
        }
        Groupe currentGroup = existingGroupes.get(existingGroupes.size() - 1);
        if (currentGroup.getInscrits().size() >= formation.getGroupe_seuil()) {
            return createNewGroupe(formation);
        }
        return currentGroup;
    }

    private Groupe createNewGroupe(Formation formation) {
        Groupe newGroupe = new Groupe();
        newGroupe.setFormation(formation);
        return groupeRepository.save(newGroupe);
    }

    public List<IndividusDto> getAllIndividus() {
        List<Individus> individuses = individusRepository.findAll();
        return individuses.stream()
                .map(individu -> modelMapper.map(individu, IndividusDto.class))
                .collect(Collectors.toList());
    }
}
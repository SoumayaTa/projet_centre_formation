package univ.iwa.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.iwa.dto.FormationDto;
import univ.iwa.dto.GroupeDto;
import univ.iwa.model.Formation;
import univ.iwa.model.Groupe;
import univ.iwa.repository.GroupeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupeService {
    @Autowired
    GroupeRepository repository;
    @Autowired
    ModelMapper modelMapper;

    public List<GroupeDto> getAllFormations() {
        List<Groupe> groupes = repository.findAll();
        return groupes.stream()
                .map(groupe -> modelMapper.map(groupe, GroupeDto.class))
                .collect(Collectors.toList());
    }
}

package univ.iwa.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import univ.iwa.dto.InscriptionDto;
import univ.iwa.dto.UserInfoDto;
import univ.iwa.model.Inscription;
import univ.iwa.model.UserInfo;
import univ.iwa.repository.InscriptionRepository;
import univ.iwa.repository.UserInfoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InscriptionService {
    @Autowired
    InscriptionRepository repo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    UserInfoRepository repository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    EmailService emailService;

    public InscriptionDto inscrireFormateurExterne(String name, String email,String  mots_cles){
        Inscription entity = modelMapper.map(new InscriptionDto(name,email,mots_cles), Inscription.class);
        entity.setStatus(false);
        entity.setDate(new Date());
        return modelMapper.map(repo.save(entity), InscriptionDto.class);
    }
    public boolean acceptFormateur(Long inscriptionId) {
        Inscription inscription = repo.findById(inscriptionId).orElse(null);
        if (inscription != null && !inscription.isStatus()) {
            repo.delete(inscription);
            UserInfo userInfo = new UserInfo();
            userInfo.setName(inscription.getName());
            userInfo.setEmail(inscription.getEmail());
            userInfo.setMots_cles(inscription.getMots_cles());
            userInfo.setRoles("ROLE_FORMAT");
            String password = RandomStringUtils.randomAlphanumeric(10);
            userInfo.setPassword(encoder.encode(password));
            repository.save(userInfo);
            sendAcceptanceEmail(inscription.getEmail(), inscription.getName(), userInfo.getName(), password);
            return true;
        }
        return false;
    }
    private void sendAcceptanceEmail(String to, String nom, String username, String password) {
        String subject = "Félicitations ! Vous avez été accepté en tant que formateur";
        String body = "Félicitations " + nom + ",<br/><br/>" +
                "Vous avez été accepté en tant que formateur avec nous. Voici vos informations de connexion :<br/>" +
                "<b>Nom d'utilisateur :</b> " + username + "<br/>" +
                "<b>Mot de passe :</b> " + password + "<br/><br/>" +
                "Connectez-vous avec ces informations et commencez à partager votre expertise avec nos apprenants.";
        String cc = "wiam.elberrari@etu.uae.ac.ma";
        emailService.sendMail(to, cc, subject, body);
    }
    public List<InscriptionDto> getAllInscriptions() {
        List<Inscription> inscriptions = repo.findAll();

        List<InscriptionDto> filteredInscriptions = inscriptions.stream()
        		.filter(inscription -> !inscription.isStatus())
                .map(inscription -> modelMapper.map(inscription, InscriptionDto.class))
                .collect(Collectors.toList());

        return filteredInscriptions;
    }
    public void deleteInscription(Long id) {
    	repo.deleteById(id);
	}
   
	public InscriptionDto getInscriptionById(Long id) {
		Optional<Inscription> inscriptionOptional = repo.findById(id);
        if (inscriptionOptional.isPresent()) {
        	Inscription inscription = inscriptionOptional.get();
            return modelMapper.map(inscription, InscriptionDto.class);
        } else {
            throw new RuntimeException("Formateur not found with id: " + id);
        }
	}

}

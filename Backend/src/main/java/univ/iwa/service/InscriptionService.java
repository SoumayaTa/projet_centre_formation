package univ.iwa.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import univ.iwa.dto.InscriptionDto;
import univ.iwa.model.Inscription;
import univ.iwa.model.UserInfo;
import univ.iwa.repository.InscriptionRepository;
import univ.iwa.repository.UserInfoRepository;

import java.util.Date;

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
        String cc = "tayoubsoumaya21@gmail.com";
        emailService.sendMail(to, cc, subject, body);
    }
}

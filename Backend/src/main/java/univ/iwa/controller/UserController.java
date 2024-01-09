package univ.iwa.controller;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import univ.iwa.model.AuthRequest;
import univ.iwa.model.Formation;
import univ.iwa.model.UserInfo;
import univ.iwa.service.JwtService;
import univ.iwa.service.UserInfoService;
@RestController
@RequestMapping("/auth") 
public class UserController { 
    @Autowired UserInfoService service; 
    @Autowired JwtService jwtService; 
    @Autowired AuthenticationManager authenticationManager; 
    @GetMapping("/welcome") 
    public String welcome() {return "Welcome this endpoint is not secure";}

    @PostMapping("/addNewUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addNewUser(@RequestBody UserInfo format) {
        return service.addUser(format);
    }


    @GetMapping("/assistant/assistantProfile")
    @PreAuthorize("hasAuthority('ROLE_ASSISTANT')")
    public String userProfile() { return "Welcome to Assistant Profile"; }
    @GetMapping("/admin/adminProfile") 
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
    public String adminProfile() { return "Welcome to Admin Profile"; }

    @GetMapping("/format/formatProfile")
    @PreAuthorize("hasAuthority('ROLE_FORMAT')")
    public String formatProfile() { return "Welcome to instructor Profile"; }

    @PostMapping("/generateToken") 
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); 
        if (authentication.isAuthenticated()) { 
            return jwtService.generateToken(authRequest.getUsername()); 
        } else { 
            throw new UsernameNotFoundException("invalid user request !"); 
        } 
    }

} 
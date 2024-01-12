package univ.iwa.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import univ.iwa.dto.AuthRequest;
import univ.iwa.model.UserInfo;
import univ.iwa.service.JwtService;
import univ.iwa.service.UserInfoService;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
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
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            System.out.println("gen token");
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return new ResponseEntity<>("{\"message\":\"" + jwtService.generateToken(authRequest.getUsername(), roles.get(0)) + "\",\"role\":\""+ roles.get(0)+"\"}", HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

} 
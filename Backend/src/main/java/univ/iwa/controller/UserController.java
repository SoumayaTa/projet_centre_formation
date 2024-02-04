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
import univ.iwa.dto.UserInfoDto;
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

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome! This endpoint is not secure.";
    }


    @PostMapping("/addNewUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addNewUser(@RequestBody UserInfoDto userInfo) {
        System.out.println("############");
        System.out.println(userInfo);
        System.out.println("############");
        return userInfoService.addUser(userInfo);
    }

        @PostMapping("/addNewAssisstant")
        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        public String addNewAssisstant(@RequestBody UserInfoDto userInfo) {
            System.out.println("############");
            System.out.println(userInfo);
            System.out.println("############");
            return userInfoService.addAssisstant(userInfo);
        }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserInfoDto updateUser(@RequestBody UserInfoDto userdto, @PathVariable int id){
    	System.out.println("==================================================");
    	System.out.println("id"+id);
    	System.out.println("userdto"+userdto);
        return userInfoService.updateUser(userdto,id);
    }
    @GetMapping("/allFormateur")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    @ResponseBody
    public List<UserInfoDto> getAllFormateurs() {
        return userInfoService.getAllFormateurs();
    }
    @GetMapping("/assistant/assistantProfile")
    @PreAuthorize("hasAuthority('ROLE_ASSISTANT')")
    public String assistantProfile() {
        return "Welcome to Assistant Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @GetMapping("/format/formatProfile")
    @PreAuthorize("hasAuthority('ROLE_FORMAT')")
    public String formatProfile() {
        return "Welcome to Instructor Profile";
    }
    @PutMapping("/format/updateFormateur/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserInfoDto updateFormateur(@RequestBody UserInfoDto formateurDto, @PathVariable int id) {
        return userInfoService.updateFormateur(formateurDto, id);
    }
    @DeleteMapping("/deleteFormateur/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteFormateur(@PathVariable("id") long id) {
        userInfoService.deleteFormateur(id);
    }
    @GetMapping("getFormateurById/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserInfoDto> getFormateurById(@PathVariable("id") int id) {
        try {
            UserInfoDto formateur = userInfoService.getFormateurById(id);
            return ResponseEntity.ok(formateur);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allFormateurByNom")
    @ResponseBody
    public List<UserInfoDto> getAllFormatByNom() {
        return userInfoService.getAllFormateurs();
    }

//    @PostMapping("/generateToken")
//    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//        if (authentication.isAuthenticated()) {
//            List<String> roles = authentication.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .collect(Collectors.toList());
//            String token = jwtService.generateToken(authRequest.getUsername(), roles.get(0));
//            return ResponseEntity.ok("{\"message\":\"" + token + "\",\"role\":\"" + roles.get(0) + "\"}");
//        } else {
//            throw new UsernameNotFoundException("Invalid user request!");
//        }
//    }

    @PostMapping("/generateToken")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        UserInfoDto user = userInfoService.findUserByName(authRequest.getUsername());
        if (authentication.isAuthenticated()) {
            System.out.println("gen token");
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return new ResponseEntity<>("{\"message\":\"" + jwtService.generateToken(authRequest.getUsername(), roles.get(0)) + "\",\"role\":\""+ roles.get(0)+"\",\"userId\":\""+user.getId()+"\"}", HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }


}

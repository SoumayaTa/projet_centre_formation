package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import univ.iwa.service.CryptUrlService;

@RestController
@RequestMapping("/crypt")
@CrossOrigin(origins = "*")
public class DecryptController {
    @Autowired
    CryptUrlService service;

    @GetMapping("/{encryptedId}")
    public Long decryptId(@PathVariable String encryptedId) {
        return service.decryptId(encryptedId);
    }

    @GetMapping("/enc/{encryptedId}")
    public String encryptId(@PathVariable Long encryptedId) {
        return service.encryptId(encryptedId);
    }
}

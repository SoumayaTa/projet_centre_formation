package univ.iwa.service;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CryptUrlService {

    @Value("${jasypt.encryptor.password}")
    private String jasyptPassword;

    public String encryptId(Long id) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(jasyptPassword);

        String encryptedId = encryptor.encrypt(String.valueOf(id));
        System.out.println("Clé de chiffrement utilisée côté serveur : " + jasyptPassword);

        return encryptedId;
    }

    public Long decryptId(String encryptedId) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(jasyptPassword);
        String decryptedIdString = encryptor.decrypt(encryptedId);
        try {
            return Long.parseLong(decryptedIdString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Failed to convert decrypted data to Long.", e);
        }
    }
}

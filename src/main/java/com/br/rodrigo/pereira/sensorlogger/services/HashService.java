package com.br.rodrigo.pereira.sensorlogger.services;

import com.br.rodrigo.pereira.sensorlogger.util.configs.PropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashService {

    @Autowired
    PropertiesConfig propertiesConfig;

    public String hashPassword(String purePassword){
        String passSalted = purePassword + propertiesConfig.getTailSalt();
        String salt = propertiesConfig.getHeadSalt();
        String passwordHashed = this.encodePassword(passSalted, salt);
        return passwordHashed;
    }

    private String encodePassword(String password, String salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < bytes.length; i++)
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

            password = stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }
}

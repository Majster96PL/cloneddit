package com.example.cloneddit.api.login.jwt;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
@Slf4j
public class JWTProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init(){
        try{
            keyStore = KeyStore.getInstance("JKS");
            InputStream inputStream = getClass().getResourceAsStream("/cloneddit.jks");
            keyStore.load(inputStream, "secret".toCharArray());
        }catch(KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e){
            log.error("Error during loading keystore", e);
        }
    }

    public String tokenGenerated(Authentication authentication) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        org.springframework.security.core.userdetails.User
                userPrincipal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .signWith(getKey())
                .compact();
    }

    private PrivateKey getKey() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
            return (PrivateKey)  keyStore.getKey("cloneddit", "secret".toCharArray());
    }
}

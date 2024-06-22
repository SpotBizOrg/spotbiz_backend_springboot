package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.entity.VerificationToken;
import com.spotbiz.spotbiz_backend_springboot.repo.VerificationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepo tokenRepository;

    public VerificationToken createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        return tokenRepository.save(verificationToken);
    }

    public VerificationToken getByToken(String token) {
        return tokenRepository.findByToken(token);
    }

}

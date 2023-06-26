package com.api.taskManagement.services.impl;

import com.api.taskManagement.data.models.VerificationToken;
import com.api.taskManagement.data.repository.VerificationTokenRepository;
import com.api.taskManagement.exception.TokenValidationException;
import com.api.taskManagement.services.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    private final VerificationTokenRepository repository;


    /**
     * @param token
     */
    @Override
    public void saveConfirmationToken(VerificationToken token) {
        repository.save(token);
    }

    /**
     * @param token
     * @return
     */
    @Override
    public Optional<VerificationToken> getToken(String token) {
        return repository.findByToken(token);
    }

    /**
     * @param token
     */
    @Override
    public void setConfirmedAt(String token) {
        VerificationToken verificationToken = new VerificationToken();
        if (isValidToken(token)) {
            verificationToken.setConfirmedAt(LocalDate.now ());
        } else {
            throw new TokenValidationException("Invalid verification");
        }
    }

    private boolean isValidToken(String token) {
        return true;
    }

}

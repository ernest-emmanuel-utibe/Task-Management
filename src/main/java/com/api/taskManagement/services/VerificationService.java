package com.api.taskManagement.services;


public interface VerificationService {
    void saveConfirmationToken(VerificationToken token);

    Optional<VerificationToken> getToken(String token);

    void setConfirmedAt(String token);
}

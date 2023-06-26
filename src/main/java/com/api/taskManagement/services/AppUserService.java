package com.api.taskManagement.services;

import com.api.taskManagement.data.dto.request.UpdateRequest;
import com.api.taskManagement.data.dto.response.AppUserDto;
import com.api.taskManagement.data.models.AppUser;
import jakarta.transaction.Transactional;

public interface AppUserService {
    AppUserDto signUp(SignUpRequest request);

    @Transactional
    String confirmToken(String token);

//    AccountUser update(Long userId, JsonPatch patch);

//    AppUserDto updateForLevelTierTwo(Long userId, UpdateRequest request);
//    AppUserDto updateForLevelTierThree(Long userId, UpdateRequest request);
    AppUser findByEmail(String email);

//    AccountUser updatePassword(String newPassword, String confirmNewPassword);
}

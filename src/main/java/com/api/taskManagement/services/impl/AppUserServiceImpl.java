package com.api.taskManagement.services.impl;

import com.api.taskManagement.data.dto.response.AppUserDto;
import com.api.taskManagement.data.models.AppUser;
import com.api.taskManagement.data.repository.AppUserRepository;
import com.api.taskManagement.services.AppUserService;
import com.api.taskManagement.services.VerificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService {
//    private static final String ACCOUNT_NUMBER_PREFIX = "98";
//    private static final int ACCOUNT_NUMBER_LENGTH = 10;
//    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(0.0);
//    private final AccountUserRepository repository;
    private final AppUserRepository userRepository;
//    private final CloudService cloudService;
//    private final MailService mailService;
    private final VerificationService verification;
//    private final TemplateEngine templateEngine;
//    private final SmsService smsService;

    @Override
    public AppUserDto signUp(SignUpRequest request) {
        var user = AppUser.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
//                .accountNumber (generateAccountNumber())
                .password(request.getPassword())
                .confirmPassword(request.getConfirmPassword())
                .phonenumber(request.getPhonenumber())
                .email(request.getEmail())
                .role(List.of(Role.USER))
                .build();
//        var accountUser = AccountUser.builder()
//                .userDetails(user)
//                .level (AccountLevel.TIER_ONE)
//                .balance (INITIAL_BALANCE)
//                .build();
        var saved = repository.save(accountUser);
        log.info("AppUser{}", appUser);
        String token = generateToken(5);
        VerificationToken verificationToken = new VerificationToken (
                token,
                LocalDateTime.now ().plusMinutes (20),
                LocalDateTime.now (),
                saved.getUserDetails ()
        );
        verification.saveConfirmationToken (verificationToken);
        EmailNotificationRequest notificationRequest =
                buildNotificationRequest(
                        saved.getUserDetails ().getEmail (),
                        saved.getUserDetails ().getFirstname (),
                        verificationToken.getToken ()
                );
        String response = mailService.sendHtmlMail (
                notificationRequest
        );
        SmsRequest smsRequest = buildSmsRequest(
                verificationToken.getToken (),
                saved.getUserDetails ().getPhonenumber (),
                saved.getUserDetails ().getFirstname ()

        );
        smsService.sendSms(smsRequest);
        if (response == null) {
            return getRegisterFailureResponse();
        }
        return getResponse(saved);
    }

    private SmsRequest buildSmsRequest(String token, String phonenumber, String firstname) {
        SmsRequest request = new SmsRequest();
        request.setPhoneNumber (phonenumber);

        Context context = new Context ();
        context.setVariables (Map.of ("lastname", firstname, "token", token));


        String content = templateEngine.process ("Activate", context);
        request.setMessage (content);
        return request;
    }



//    public static String generateAccountNumber() {
//        StringBuilder accountNumber = new StringBuilder(ACCOUNT_NUMBER_PREFIX);
//        Random random = new Random();
//        for (int i = ACCOUNT_NUMBER_PREFIX.length(); i < ACCOUNT_NUMBER_LENGTH; i++) {
//            int digit = random.nextInt(10);
//            accountNumber.append(digit);
//        }
//        return accountNumber.toString();
//    }



    @Transactional
    @Override
    public String confirmToken(String token){
        VerificationToken verificationToken =
                verification.getToken (token)
                        .orElseThrow(() ->
                                new NotFoundException ("Token not found"));
        if (verificationToken.getConfirmedAt () != null) {
            throw new BusinessLogicException ("Token already confirmed");
        }
        LocalDateTime expiredAt =
                verificationToken.getExpiryTime ();
        if (expiredAt.isAfter (LocalDateTime.now ())){
            throw new TokenExpiredException ("Token already expired");
        }
        verification.setConfirmedAt(token);
        enableUserAccount (
                verificationToken
                        .getUserDetails ()
                        .getEmail ()
        );
        EmailNotificationRequest notificationRequest =
                buildAccCreationRequest(
                        verificationToken.getUserDetails ().getEmail (),
                        verificationToken.getUserDetails ().getFirstname (),
                        verificationToken.getUserDetails ().getAccountNumber ()
                );
        mailService.sendHtmlMail (
                notificationRequest
        );
        verification.saveConfirmationToken (verificationToken);
        return "confirmed";
    }


//    @Override
//    public AppUserDto updateForLevelTierTwo(Long userId, UpdateRequest request) {
//        AccountUser existingAccountUser = getAccountUserById (userId);
//        AppUser accountUserDetails = existingAccountUser.getUserDetails ();
//        existingAccountUser.getUserDetails ().setBvn (request.getBvn ());
//        existingAccountUser.getUserDetails ().setAge (request.getAge ());
//        existingAccountUser.getUserDetails ().setGender (request.getGender ());
//        existingAccountUser.getUserDetails ().setAddress (request.getAddress ());
//        existingAccountUser.setLevel (AccountLevel.TIER_TWO);
//        String imageUrl = cloudService.upload (request.getNinImage ());
//        existingAccountUser.setNinImage (imageUrl);
//        AccountUser response = repository.save (existingAccountUser);
//        return getResponse (response);
//    }

//    @Override
//    public AppUserDto updateForLevelTierThree(Long userId, UpdateRequest request) {
//        AccountUser existingAccountUser = getAccountUserById (userId);
//        AppUser accountUserDetails = existingAccountUser.getUserDetails ();
//        String imageUrl = cloudService.upload (request.getMeansOfIdentifier ());
//        existingAccountUser.setMeansOfIdentifier (imageUrl);
//        String utilityBill = cloudService.upload (request.getUtilityBill ());
//        existingAccountUser.setUtilityBill (utilityBill);
//        existingAccountUser.setLevel (AccountLevel.TIER_THREE);
//        AccountUser response = repository.save (existingAccountUser);
//        return getResponse (response);
//
//    }

//    public AccountUser getAccountUserById(Long passengerId) {
//        return repository.findById(passengerId).orElseThrow(()->
//                new BusinessLogicException(
//                        String.format("Passenger with id %d not found", passengerId)));
//    }

//    @Override
//    public AccountUser update(Long userId, JsonPatch patch) {
//        ObjectMapper objectMapper = new ObjectMapper ();
//        AccountUser existingAccountUser = getAccountUserById (userId);
//        AppUser accountUserDetails = existingAccountUser.getUserDetails ();
//        JsonNode existingNode = objectMapper.convertValue (existingAccountUser, JsonNode.class);
//
//        try {
//            JsonNode updatedNode = patch.apply (existingNode);
//            AccountUser updatedAccountUser = objectMapper.convertValue (updatedNode, AccountUser.class);
//            updatedAccountUser = repository.save (updatedAccountUser);
//
//            return updatedAccountUser;
//        } catch (JsonPatchException e) {
//            log.error ("Error applying JsonPatch: " + e.getMessage ());
//            throw new BusinessLogicException ("Failed to apply JsonPatch.");
//        }
//    }

    private EmailNotificationRequest buildAccCreationRequest(String email, String firstname, String accNo) {
        EmailNotificationRequest request = new EmailNotificationRequest();
        request.getTo ().add (new Recipient (firstname, email));

        Context context = new Context ();
        context.setVariables (Map.of ("fullname", firstname, "accountNumber", accNo));


        String content = templateEngine.process ("Welcome", context);
        request.setHtmlContent (content);
        return request;
    }

    private void enableUserAccount(String email){
        userRepository.enableAppUser(email);
    }
    private static AppUserDto getRegisterFailureResponse() {
        return AppUserDto.builder()
                .id (-1L)
                .success(false)
                .message(Message.FAILED)
                .code (HttpStatus.NO_CONTENT.value ())
                .build();
    }

    private EmailNotificationRequest buildNotificationRequest(String email, String firstname, String token) {
        EmailNotificationRequest request = new EmailNotificationRequest();
        request.getTo ().add (new Recipient (firstname, email));

        Context context = new Context ();
        context.setVariables (Map.of ("lastname", firstname, "token", token));


        String content = templateEngine.process ("Activate", context);
        request.setHtmlContent (content);
        return request;
    }

    public static String generateToken(int length) {
        byte[] bytes = new byte[length];
        new SecureRandom ().nextBytes(bytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    private AppUserDto getResponse(AccountUser saved) {
        return AppUserDto.builder()
                .id (saved.getId())
                .code(HttpStatus.CREATED.value())
                .fullName(saved.getUserDetails().getFullName())
                .message(Message.CREATED)
                .success(true).build();
    }


    @Override
    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public AccountUser updatePassword(String newPassword, String confirmNewPassword) {
        AccountUser updating = AccountUser.builder()
                .build();
        return repository.save(updating);
    }
}

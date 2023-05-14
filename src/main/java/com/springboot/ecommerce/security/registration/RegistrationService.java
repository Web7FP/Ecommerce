package com.springboot.ecommerce.security.registration;

import com.springboot.ecommerce.exception.EmailAlreadyTakenException;
import com.springboot.ecommerce.exception.EmailNotValidException;
import com.springboot.ecommerce.security.jwt.JwtService;
import com.springboot.ecommerce.security.registration.emailRegistration.EmailSender;
import com.springboot.ecommerce.security.registration.emailRegistration.EmailService;
import com.springboot.ecommerce.security.registration.emailRegistration.EmailValidator;
import com.springboot.ecommerce.model.token.Token;
import com.springboot.ecommerce.model.token.TokenRepository;
import com.springboot.ecommerce.model.token.TokenService;
import com.springboot.ecommerce.model.user.User;
import com.springboot.ecommerce.model.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final EmailValidator emailValidator;
    private final UserService userService;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final EmailSender emailSender;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;

    public String register(RegistrationRequest request){
        boolean isEmailValid = emailValidator.test(request.getEmail());
        if (!isEmailValid)
            throw new EmailNotValidException(); // exception tu dinh nghia

        boolean userExists = userService.findByEmailUser(request.getEmail()).isPresent();
        if (userExists) {
            throw new EmailAlreadyTakenException();
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .userRole(request.getUserRole())
                .build();

        var savedUser = userService.saveUser(user);
        var jwtToken = jwtService.generateToken(user);
        tokenService.saveUserToken(user, jwtToken);

        String link  = "http://localhost:8080/registration/confirm?token=" + jwtToken;
        emailSender.send(request.getEmail(), emailService.buildEmail(request.getFirstName(), link));

        return jwtToken;
    }

    public void confirmedToken(String jwtToken){
        Token token = tokenService.getToken(jwtToken)
                .orElseThrow(() -> new IllegalStateException("token not found"));
        if (token.isConfirmedRegister())
            throw new IllegalStateException("email already taken");
        if (token.isExpired() || token.isRevoked())
            throw new IllegalStateException("token expired");

        tokenService.setConfirmedAt(jwtToken);
        userService.enableUser(token.getUser().getEmail());
    }

    private void revokeAllUserToken(User user){
        var validUserTokens = tokenService.findAllValidToken(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}

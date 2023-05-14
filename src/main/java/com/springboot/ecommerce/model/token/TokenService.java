package com.springboot.ecommerce.model.token;

import com.springboot.ecommerce.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public void saveUserToken(User user, String jwtToken){
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }



    public Optional<Token> getToken(String token){return tokenRepository.findByToken(token);}

    public int setConfirmedAt(String token){return tokenRepository.updateConfirmedAt(token);}

    public List<Token> findAllValidToken(Long id){return tokenRepository.findAllValidTokenByUser(id);}
}

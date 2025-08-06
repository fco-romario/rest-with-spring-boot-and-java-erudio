package br.com.fco_romario.services;

import br.com.fco_romario.data.dto.Security.AccountCredentialsDTO;
import br.com.fco_romario.data.dto.Security.TokenDTO;
import br.com.fco_romario.repositories.UserRepository;
import br.com.fco_romario.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    public ResponseEntity<TokenDTO> singIn(AccountCredentialsDTO credentials) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(),
                    credentials.getUsername()
            )
        );

        var user =  repository.findByUsername(credentials.getUsername());
        if(user == null) throw new UsernameNotFoundException("Username "+ credentials.getUsername() + " not found!");

        var tokenDTO = tokenProvider.createAcessToken(
                credentials.getUsername(),
                user.getRoles()
        );
        return ResponseEntity.ok(tokenDTO);
    }



}

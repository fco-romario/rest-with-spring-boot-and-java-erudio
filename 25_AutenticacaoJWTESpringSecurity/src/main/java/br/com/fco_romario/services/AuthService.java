package br.com.fco_romario.services;

import br.com.fco_romario.data.dto.PersonDTO;
import br.com.fco_romario.data.dto.Security.AccountCredentialsDTO;
import br.com.fco_romario.data.dto.Security.TokenDTO;
import br.com.fco_romario.exception.RequiredObjectIsNullException;
import br.com.fco_romario.model.Person;
import br.com.fco_romario.model.User;
import br.com.fco_romario.repositories.UserRepository;
import br.com.fco_romario.security.jwt.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static br.com.fco_romario.mapper.ObjectMapper.parseObject;

@Service
public class AuthService {

    Logger logger = LoggerFactory.getLogger(AuthService.class);

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
                    credentials.getPassword()
            )
        );

        var user =  repository.findByUsername(credentials.getUsername());
        if(user == null) throw new UsernameNotFoundException("Username "+ credentials.getUsername() + " not found!");

        var tokenDTO = tokenProvider.createAccessToken(
                credentials.getUsername(),
                user.getRoles()
        );
        return ResponseEntity.ok(tokenDTO);
    }


    public ResponseEntity<TokenDTO> refreshToken(String username, String refreshToken) {
        var user =  repository.findByUsername(username);
        TokenDTO tokenDTO;
        if(user != null) {
            tokenDTO = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username "+ username + " not found!");
        }
        return ResponseEntity.ok(tokenDTO);
    }

    public AccountCredentialsDTO create(AccountCredentialsDTO user) {
        if(user == null) throw new RequiredObjectIsNullException();

        logger.info("Creating a new User");
        var entity = new User();
        entity.setFullName(user.getFullname());
        entity.setUserName(user.getUsername());
        entity.setPassword(generateHashedPassword(user.getPassword()));
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);
        entity.setEnabled(true);

        //return parseObject(repository.save(entity), AccountCredentialsDTO.class);
        var dto =  repository.save(entity);
        return new AccountCredentialsDTO(dto.getUsername(),dto.getPassword(), dto.getFullName());
    }

    private String generateHashedPassword(String password) {

        PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder(
                "", 8, 185000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
        return passwordEncoder.encode(password);
    }
}

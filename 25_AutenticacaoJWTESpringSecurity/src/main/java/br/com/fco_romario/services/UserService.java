package br.com.fco_romario.services;

import br.com.fco_romario.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repository; //Injeção de dependência via propriedade

    public UserService(UserRepository repository) {  //Injeção de dependência via contrutor
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username);
        if (user != null) return user;
        else throw new UsernameNotFoundException("Username " + username + "not found!");
    }
}

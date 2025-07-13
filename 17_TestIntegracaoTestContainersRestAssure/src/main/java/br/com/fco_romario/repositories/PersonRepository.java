package br.com.fco_romario.repositories;

import br.com.fco_romario.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Neste caso é opcional, mas abaixo da versão 3.x  do Spring Boot é recomendado o uso.
public interface PersonRepository extends JpaRepository<Person, Long> {
}

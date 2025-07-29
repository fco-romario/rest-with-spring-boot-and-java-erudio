package br.com.fco_romario.repositories;

import br.com.fco_romario.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.fco_romario.model.Person;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(SpringExtension.class) //- Integra o Spring com o JUnit 5 (Jupiter),
// permitindo o uso de funcionalidades do Spring nos testes (como injeção de dependência)

@DataJpaTest// - Configura um ambiente de teste focado apenas em JPA.
// Por padrão, usa um banco de dados em memória, configura o Hibernate, Spring Data JPA, etc.
// Desabilita autoconfiguração completa do Spring para carregar apenas componentes relevantes a JPA

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// Indica para NÃO substituir o banco de dados configurado (ex: em application.properties)
// por um banco em memória. Mantém o banco configurado originalmente
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//Indica que os testes não serão executados em ordem aleatória (padrão do JUnit), mas seguirão uma ordem específica.
class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    PersonRepository repository;

    private static Person person;

    @BeforeAll
    static void setUp() {
        person = new Person();
    }

    @Test
    @Order(1)
    void findPeopleByName() {
        Pageable pageable = PageRequest.of(0,12, Sort.by(Sort.Direction.ASC, "firstName"));

        person = repository.findPeopleByName("iko", pageable).getContent().get(0);

        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Nikola", person.getFirstName());
        assertEquals("Tesla", person.getLastName());
        assertEquals("Male", person.getGender());
        assertTrue(person.getEnabled());

    }

    @Order(2)
    @Test
    void disabledPerson() {
        Long id = person.getId();
        repository.disabledPerson(id);

        var result = repository.findById(id);
        person = result.get();

        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Nikola", person.getFirstName());
        assertEquals("Tesla", person.getLastName());
        assertEquals("Male", person.getGender());
        assertFalse(person.getEnabled());

    }
}
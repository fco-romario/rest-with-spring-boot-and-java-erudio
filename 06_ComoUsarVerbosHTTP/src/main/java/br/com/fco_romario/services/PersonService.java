package br.com.fco_romario.services;

import br.com.fco_romario.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service //dentre outras coisa, Pode ser injetada em outras classes usando @Autowired
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public List<Person> findAll() {
        logger.info("Finding all people!");
        List<Person> people = new ArrayList<>();
        for (int i = 0; i <= 5 ; i++) {
            people.add(mockPerson(i));
        }

        return people;
    }

    public Person findById(String id) {
        logger.info("Finding one Person!");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Firstname");
        person.setLastName("Lastname");
        person.setAddress("Some Address in Brasil");
        person.setGender("Masculino");

        return person;
    }

    public Person create(Person person) {
        logger.info("Creating one person");
        return person;
    }

    public Person update(Person person) {
        logger.info("Updating one person");
        return person;
    }

    public void delete(String id) {
        logger.info("Deleting one Person! id: " + id);
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Firstname " + i);
        person.setLastName("Lastname " + i);
        person.setAddress("Some Address in Brasil " + i);
        person.setGender((i % 2 == 0) ? "Masculino" : "Feminino");

        return person;
    }

}

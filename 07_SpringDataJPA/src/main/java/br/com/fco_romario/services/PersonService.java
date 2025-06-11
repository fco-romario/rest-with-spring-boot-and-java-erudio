package br.com.fco_romario.services;

import br.com.fco_romario.exception.ExceptionResponse;
import br.com.fco_romario.exception.ResourceNotFoundException;
import br.com.fco_romario.model.Person;
import br.com.fco_romario.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service //dentre outras coisa, Pode ser injetada em outras classes usando @Autowired
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository repository;

    public List<Person> findAll() {
        logger.info("Finding all people!");
        return repository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding one Person!");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
    }

    public Person create(Person person) {
        logger.info("Creating one person");
        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one person");

        Person obj = findById(person.getId());

        obj.setFirstName(person.getFirstName());
        obj.setLastName(person.getLastName());
        obj.setAddress(person.getAddress());
        obj.setGender(person.getGender());

        return repository.save(obj);
    }

    public void delete(Long id) {
        logger.info("Deleting one Person! id: " + id);

        Person obj = findById(id);
        repository.delete(obj);
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

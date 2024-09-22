package br.com.erudio.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.erudio.model.Person;

@Service
public class PersonServices {
	
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName()); 
	
	public Person findById(String id) {
		logger.info("Find one person!");
		
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Romário");
		person.setLastName("Alves");
		person.setAddress("Fortaleza - Ceará - Brasil");
		person.setGender("Male");
		return person;
	}	
	
	public List<Person> findAll() {
		logger.info("Find all people!");
		
		List<Person> persons = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}
		return persons;
	}

	private Person mockPerson(int i) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Romário " + i);
		person.setLastName("Alves " + i);
		person.setAddress("Fortaleza - Ceará - Brasil " + 1);
		if(i % 2 == 0) {
			person.setGender("Male " + i);			
		} else {
			person.setGender("Female " + i);
		}
		return person;
	}
}

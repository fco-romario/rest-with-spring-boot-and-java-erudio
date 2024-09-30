package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName()); 
	
	@Autowired
	private PersonRepository personRepository;
	
	
	public List<PersonVO> findAll() {
		logger.info("find All people!");
		
		var vos = DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
		vos
			.stream()
			.forEach(vo -> vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel()));
		return vos;
	}
	
	public PersonVO findById(Long id) {
		logger.info("Find one person!");
		
		var entity =  personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
		
	}	
	
	public PersonVO create(PersonVO person) {
		logger.info("Create person!");
		var entity =  DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
		
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		logger.info("Update person de id ");
		
		Person entity = personRepository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deleted person de id " + id);		
		
		Person entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		personRepository.delete(entity);
	}
}

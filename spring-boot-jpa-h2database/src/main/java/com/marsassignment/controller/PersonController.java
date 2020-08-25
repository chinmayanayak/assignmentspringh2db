package com.marsassignment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import com.marsassignment.dto.PersonDTO;
import com.marsassignment.exception.PersonNotFoundException;
import com.marsassignment.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    // Find all person
    @GetMapping("/person")
    List<PersonDTO> findAllPerson() {
        return personRepository.findAll();
    }
    
    // Count number of  person
    @GetMapping("/countperson")
    Long countNoOfPerson() {
        return personRepository.count();
    }
    

    // Add a person
    @PostMapping("/person")
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    PersonDTO newPerson(@RequestBody PersonDTO newPersonDTO) {
        return personRepository.save(newPersonDTO);
    }

    // Find a person with given id
    @GetMapping("/person/{id}")
    PersonDTO findOne(@PathVariable Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    // Update a person with given id
    @PutMapping("/person/{id}")
    Optional<Object> saveOrUpdate(@RequestBody PersonDTO newPersonDTO, @PathVariable Long id) {
    	
    	personRepository.findAll().forEach(x -> System.out.println(x));
    	System.out.println("fooooooooooooooooooo");
    	if(personRepository.existsById(id)) {
	        return personRepository.findById(id)
	                .map(x -> {
	                    x.setFirstName(newPersonDTO.getFirstName());
	                    x.setLastName(newPersonDTO.getLastName());
	                    return personRepository.save(x);
	                    
	                });
    	}
    	else {
                throw new PersonNotFoundException(id);
        
    	}
    }

    // update author only
    /*@PatchMapping("/person/{id}")
    PersonDTO patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

        return personRepository.findById(id)
                
                .orElseGet(() -> {
                    throw new PersonNotFoundException(id);
                });

    }*/

    @DeleteMapping("/person/{id}")
    void deleteBook(@PathVariable Long id) {
        personRepository.deleteById(id);
    }

}

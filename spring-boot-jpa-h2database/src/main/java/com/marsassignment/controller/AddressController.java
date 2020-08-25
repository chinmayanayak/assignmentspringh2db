package com.marsassignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.marsassignment.dto.AddressDTO;

import com.marsassignment.exception.AddressNotFoundException;
import com.marsassignment.exception.PersonNotFoundException;
import com.marsassignment.repository.AddressRepository;
import com.marsassignment.repository.PersonRepository;

public class AddressController {
	
	@Autowired
    private PersonRepository personRepository;
	
	@Autowired
    private AddressRepository addressRepository;
	
	// Find a address with given id
    @GetMapping("/address/{id}")
    AddressDTO findOne(@PathVariable Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
    }
    // Add address
    @PostMapping("/address")
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    AddressDTO newPerson(@RequestBody AddressDTO newAddressDTO) {
    	
    	if(personRepository.findById(newAddressDTO.getId()).isPresent()) {
    		return addressRepository.save(newAddressDTO);
    	}
    	else {
    		throw new PersonNotFoundException(newAddressDTO.getId()
    				);
    	}
    }
    
    @DeleteMapping("/address/{id}")
    void deleteBook(@PathVariable Long id) {
    	addressRepository.deleteById(id);
    }


}

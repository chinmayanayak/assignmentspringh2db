package com.marsassignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marsassignment.dto.PersonDTO;


public interface PersonRepository extends JpaRepository<PersonDTO, Long> {

}

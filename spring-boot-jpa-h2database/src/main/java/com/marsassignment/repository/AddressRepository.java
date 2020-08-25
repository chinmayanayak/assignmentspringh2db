package com.marsassignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marsassignment.dto.AddressDTO;

public interface AddressRepository extends JpaRepository<AddressDTO, Long>{

}

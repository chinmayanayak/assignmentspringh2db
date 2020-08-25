package com.marsassignment.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class AddressDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8153354589583162230L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String street;
	private String city;
	private String state;
	private String postalCode;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pid", nullable = false,referencedColumnName="pid")
	private PersonDTO person;
	
	public AddressDTO() {
		
	}
	
	public AddressDTO(Long id, String street, String city, String state, String postalCode, PersonDTO person) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.person = person;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public PersonDTO getPerson() {
		return person;
	}

	public void setPerson(PersonDTO person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "AddressDTO [id=" + id + ", street=" + street + ", city=" + city + ", state=" + state + ", postalCode="
				+ postalCode + ", person=" + person + "]";
	}

	
	
	
	
	
	
	

}

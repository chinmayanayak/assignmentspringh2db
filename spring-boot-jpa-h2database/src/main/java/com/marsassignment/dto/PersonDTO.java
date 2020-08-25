package com.marsassignment.dto;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class PersonDTO implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	//@Column(name="pid")
    private Long pid;
    private String firstName;
    private String lastName;
    
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "person")
    private AddressDTO address;

    public PersonDTO() {
    }
    
    public PersonDTO(Long pid,String firstName,String lastName) {
    	this.pid = pid;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    

    public PersonDTO(String firstName,String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    

    public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	@Override
    public String toString() {
        return "Person{" +
                "id=" + pid +
                ", firstName='" + firstName + '\'' +
                 ", lastName='" + lastName + '\'' +
                '}';
    }

   
}

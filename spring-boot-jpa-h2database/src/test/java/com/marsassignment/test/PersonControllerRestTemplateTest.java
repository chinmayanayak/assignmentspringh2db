package com.marsassignment.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marsassignment.dto.PersonDTO;
import com.marsassignment.repository.PersonRepository;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
@ActiveProfiles("test")
public class PersonControllerRestTemplateTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PersonRepository mockRepository;

    @Before
    public void init() {
    	mockRepository.deleteAllInBatch();
        PersonDTO person = new PersonDTO(1L,"Person Name", "Nayak");
        when(mockRepository.findById(1L)).thenReturn(Optional.of(person));
        
    }

    @Test
    public void find_personId_OK() throws JSONException {

        String expected = "{pid:1,firstName:\"Person Name\",lastName:\"Nayak\"}";

        ResponseEntity<String> response = restTemplate.getForEntity("/person/1", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());

        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(mockRepository, times(1)).findById(1L);
        

    }

    @Test
    public void find_allPerson_OK() throws Exception {
    	
    	mockRepository.deleteAllInBatch();
        List<PersonDTO> persons = Arrays.asList(
                new PersonDTO("Person A", "Ah P"),
                new PersonDTO("Person B", "Ah D"));
        
       

        when(mockRepository.findAll()).thenReturn(persons);

        String expected = om.writeValueAsString(persons);

        ResponseEntity<String> response = restTemplate.getForEntity("/person", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(mockRepository, times(2)).findAll();
    }

    @Test
    public void find_personIdNotFound_404() throws Exception {

        String expected = "{status:404,error:\"Not Found\",message:\"Person id not found : 9\",path:\"/persons/9\"}";

        ResponseEntity<String> response = restTemplate.getForEntity("/person/9", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

    }

    @Test
    public void save_person_OK() throws Exception {
    
        mockRepository.deleteAllInBatch();

        PersonDTO newPerson = new PersonDTO("Demo", "AC");
        when(mockRepository.save(any(PersonDTO.class))).thenReturn(newPerson);

        String expected = om.writeValueAsString(newPerson);

        ResponseEntity<String> response = restTemplate.postForEntity("/person", newPerson, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(mockRepository, times(1)).save(any(PersonDTO.class));

    }

    @Test
    public void update_person_OK() throws Exception {
    	
        //save_person_OK();
    	PersonDTO newPerson = new PersonDTO(1L,"Demo", "AC");
        when(mockRepository.save(any(PersonDTO.class))).thenReturn(newPerson);

        String expected = om.writeValueAsString(newPerson);

        ResponseEntity<String> response = restTemplate.postForEntity("/person", newPerson, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(mockRepository, times(1)).save(any(PersonDTO.class));
        
        mockRepository.findAll().forEach(x -> System.out.println(x));
        
        PersonDTO updatePerson = new PersonDTO(1L,"ABC", "nay");
        when(mockRepository.save(any(PersonDTO.class))).thenReturn(updatePerson);
        
        mockRepository.findAll().forEach(x -> System.out.println(x));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(om.writeValueAsString(updatePerson), headers);

        response = restTemplate.exchange("/person/1", HttpMethod.PUT, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(om.writeValueAsString(updatePerson), response.getBody(), false);

        verify(mockRepository, times(1)).findById(1L);
        verify(mockRepository, times(1)).save(any(PersonDTO.class));

    }

    
    

    @Test
    public void delete_person_OK() {

        doNothing().when(mockRepository).deleteById(1L);

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange("/person/1", HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(mockRepository, times(1)).deleteById(1L);
    }

    private static void printJSON(Object object) {
        String result;
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}

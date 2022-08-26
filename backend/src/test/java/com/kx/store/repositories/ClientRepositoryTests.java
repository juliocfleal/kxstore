package com.kx.store.repositories;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.kx.store.entities.Client;

@DataJpaTest
public class ClientRepositoryTests {

	
	@Autowired
	private ClientRepository repository;
	
	private Long validId;
	private Long invalidId;
	private Long totalClients;
	private Client client = new Client(null, "Julio", "19238746591");
	
	@BeforeEach
	void setUp() throws Exception{
		validId = 1L;
		invalidId = 1000L;
		totalClients = 10L;
	}
	
	@Test
	public void saveShouldPersistNewClientWhenIdIsNull() {
		client = repository.save(client);
		Assertions.assertNotNull(client.getId());
		Assertions.assertEquals((totalClients + 1L), client.getId());
	}
	
	@Test
	public void updateShouldUpdateWhenIdIsValid() {
		String expectedDocument = "33344455521";
		String expectedName = "Helena Blotsky";
		Client result = repository.getReferenceById(validId);
		Assertions.assertNotNull(result);
		result.setDocument(expectedDocument);
		result.setName(expectedName);
		result = repository.save(result);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), validId);
		Assertions.assertEquals(result.getDocument(), expectedDocument);
		Assertions.assertEquals(result.getName(), expectedName);
	}
	
	@Test
	public void updateShouldThrowsExceptionWhenIdIsInvalid() {
		Assertions.assertThrows(EntityNotFoundException.class, ()->{
			String expectedDocument = "99988877765";
			String expectedName = "Marieta Valenta";
			Client result = repository.getReferenceById(invalidId);
			result.setDocument(expectedDocument);
			result.setName(expectedName);
			result = repository.save(result);
		});
	}
	
	
	@Test
	public void deletShouldDeletWhenIdIsValid() {
		repository.deleteById(validId);
		Optional<Client> result = repository.findById(validId);
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldThrowsExceptionWhenIdIsInvalid() {
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () ->{
			repository.deleteById(invalidId);
			
		});
	}
}

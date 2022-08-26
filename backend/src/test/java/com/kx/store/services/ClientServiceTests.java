package com.kx.store.services;

import static org.mockito.Mockito.times;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kx.store.DTO.ClientDTO;
import com.kx.store.entities.Client;
import com.kx.store.repositories.ClientRepository;

@WebMvcTest(SpringExtension.class)
public class ClientServiceTests {
	
	@InjectMocks
	private ClientService service;
	
	@Mock
	private ClientRepository repository;
	
	private Long validId;
	private Long invalidId;
	private Client client = new Client(validId, "Julio", "19238746591");
	private ClientDTO clientDTO = new ClientDTO(client);
	
	@BeforeEach
	void setUp() throws Exception{
		validId = 1L;
		invalidId = 1000L;
		Mockito.when(repository.save(client)).thenReturn(client);
		Mockito.doNothing().when(repository).deleteById(validId);
		Mockito.doThrow(com.kx.store.services.exceptions.ResourceNotFoundException.class).when(repository).deleteById(invalidId);
		Mockito.when(repository.getReferenceById(validId)).thenReturn(client);
		Mockito.when(repository.getReferenceById(invalidId)).thenThrow(EntityNotFoundException.class);
	}

	@Test
	public void insertShouldReturnNewClient() {
		ClientDTO result = service.insert(clientDTO);
		Assertions.assertNotNull(result);
		Mockito.verify(repository, Mockito.times(1)).save(client);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdIsValid() {

		Assertions.assertDoesNotThrow(() -> {
			service.delete(validId);
		});
		Mockito.verify(repository, Mockito.times(1)).deleteById(validId);
	}

	@Test
	public void deleteShouldThrowsExceptionsWhenIdIsInvalid() {
		Assertions.assertThrows(com.kx.store.services.exceptions.ResourceNotFoundException.class, () -> {
			service.delete(invalidId);
		});
		Mockito.verify(repository, Mockito.times(1)).deleteById(invalidId);
	}
	
	@Test
	public void updateShouldReturnClientDTOWhenIdIsValid() {
		ClientDTO result = service.update(validId, clientDTO);
		Assertions.assertNotNull(result);
		Mockito.verify(repository, times(1)).getReferenceById(validId);
	}
	
	@Test
	public void updateShouldThrowExceptionwhenInvalidId() {
		Assertions.assertThrows(com.kx.store.services.exceptions.ResourceNotFoundException.class, ()->{
			service.update(invalidId, clientDTO);
		});
	}
}

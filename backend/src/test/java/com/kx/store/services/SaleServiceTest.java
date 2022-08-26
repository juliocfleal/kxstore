package com.kx.store.services;

import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kx.store.DTO.SaleDTO;
import com.kx.store.entities.Client;
import com.kx.store.entities.Sale;
import com.kx.store.repositories.ClientRepository;
import com.kx.store.repositories.SaleRepository;
import com.kx.store.services.exceptions.ResourceNotFoundException;

@WebMvcTest(SpringExtension.class)
public class SaleServiceTest {

	
	@InjectMocks
	private SaleService service;
	
	@Mock
	private SaleRepository repository;
	
	@Mock
	private ClientRepository clientRepository;
	
	private Long validId;
	private Long invalidId;
	private Client client = new Client(validId, "Julio", "19238746591");
	private Sale sale = new Sale(validId,client, 100.00);
	private SaleDTO saleDTO = new SaleDTO(sale);
	private java.util.List<Sale> sales;
	
	@BeforeEach
	void setUp() throws Exception{
		validId = 1L;
		invalidId = 1000L;
		sales = java.util.List.of(sale);
		Mockito.when(repository.findAll()).thenReturn(sales);
		Mockito.when(repository.findById(validId)).thenReturn(Optional.of(sale));
		Mockito.when(repository.findById(invalidId)).thenReturn(Optional.empty());
		Mockito.when(repository.save(sale)).thenReturn(sale);
		Mockito.when(clientRepository.findById(saleDTO.getClient())).thenReturn(Optional.of(client));
	}
	
	@Test
	public void findAllShouldReturnListOfSales() {
		java.util.List<SaleDTO> result = service.findAll();
		Assertions.assertNotNull(result);
		Mockito.verify(repository).findAll();
	}
	
	@Test
	public void findByIdShouldReturnSaleDTOWhenIdIsValid() {
		SaleDTO result = service.findById(validId);
		Assertions.assertNotNull(result);
		Mockito.verify(repository).findById(validId);
	}
	
	@Test
	public void findByIdShouldThrowsExceptionWhenIdIsInvalid() {
		Assertions.assertThrows(com.kx.store.services.exceptions.ResourceNotFoundException.class, () -> {
			service.findById(invalidId);
		});
		
		
		Mockito.verify(repository, times(1)).findById(invalidId);
	}
	
	@Test
	public void insertShouldReturnSaleDTOWhenClientIsValid() {
		SaleDTO result = service.insert(saleDTO);
		Assertions.assertNotNull(result);
		Mockito.verify(repository, times(1)).save(sale);
		
	}
	


}

package com.kx.store.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kx.store.entities.Client;
import com.kx.store.entities.Sale;

@DataJpaTest
public class SaleRepositoryTests {
	
	@Autowired
	private SaleRepository repository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	private Long validId;
	private Long invalidId;
	private Long totalSales;
	private Client client = new Client(validId, "Julio", "19238746591");
	private Sale sale = new Sale(validId,client, 100.00);
	
	@BeforeEach
	void setUp() throws Exception{
		validId = 1L;
		invalidId = 1000L;
		totalSales = 18L;
	}
	
	@Test
	public void findAllShouldReturnListOfSales() {
		List<Sale> sales = repository.findAll();
		Assertions.assertNotNull(sales);
		Assertions.assertEquals(sales.size(), totalSales);
	}
	
	@Test
	public void findByIdShouldReturnSaleWhenIdIsValid() {
		Optional<Sale> result = repository.findById(validId);
		Assertions.assertNotNull(result.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnEmptyWhenIdIsValid() {
		Optional<Sale> result = repository.findById(invalidId);
		Assertions.assertNotNull(result.isEmpty());
	}
	
	@Test
	public void insertShouldInsertWhenClientIsValid() {
		clientRepository.save(client);
		Sale result = repository.save(sale);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), totalSales + 1);
	}

}

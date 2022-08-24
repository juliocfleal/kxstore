package com.kx.store.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kx.store.DTO.SaleDTO;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class SaleIT {
	
	private Long validId;
	private Long invalidId;


	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception{
		validId = 3L;
		invalidId = 1000L;
	}
	
	@Test
	public void getAllSaleShouldReturnAllSales() throws Exception{
		ResultActions result = mockMvc.perform(get("/sales").accept(MediaType.APPLICATION_JSON));
	
		result.andExpectAll(status().isOk());
		result.andExpect(jsonPath("$").exists());
		result.andExpect(jsonPath("$[0].client").value("10"));
		result.andExpect(jsonPath("$[0].total").value("100.0"));
		result.andExpect(jsonPath("$[0].id").value("1"));
		result.andExpect(jsonPath("$[1].client").value("3"));
		result.andExpect(jsonPath("$[1].total").value("500.0"));
		result.andExpect(jsonPath("$[1].id").value("2"));
	}
	
	@Test
	public void getSaleShouldReturnSaleWhenIdIsValid() throws Exception{
		ResultActions result = mockMvc.perform(get("/sales/{id}", validId).accept(MediaType.APPLICATION_JSON));
	
		result.andExpectAll(status().isOk());
		result.andExpect(jsonPath("$").exists());
		result.andExpect(jsonPath("$.client").value("6"));
		result.andExpect(jsonPath("$.total").value("10.0"));
		result.andExpect(jsonPath("$.id").value("3"));

	}
	
	
	@Test
	public void getSaleShouldReturnExceptionWhenIdIsInvalid() throws Exception{
		ResultActions result = mockMvc.perform(get("/sales/{id}", invalidId).accept(MediaType.APPLICATION_JSON));
	
		result.andExpect(status().isNotFound());

	}
	
	@Test
	public void InsertSaleShouldInserNewSale() throws Exception{
		SaleDTO saletDTO = new SaleDTO();
		saletDTO.setClient(1L);
		saletDTO.setTotal(999.99);
		String jsonBody = objectMapper.writeValueAsString(saletDTO);
		Long expectedClient = saletDTO.getClient();
		Double expectedTotal = saletDTO.getTotal();
		
		ResultActions result = mockMvc.perform(post("/sales")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.client").value(expectedClient));
		result.andExpect(jsonPath("$.total").value(expectedTotal));
		
	}
	
	
	
}

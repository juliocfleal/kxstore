package com.kx.store.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.kx.store.resources.SaleResource;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class SaleIT {
	
	private Long validId;
	private Long invalidId;
	private Long numberSales;
	
	@Autowired
	private SaleResource resource;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception{
		validId = 1L;
		invalidId = 1000L;
		numberSales = 18L;
	}
	
	@Test
	public void getAllSaleShouldReturnAllSales() throws Exception{
		ResultActions result = mockMvc.perform(get("/sales").accept(MediaType.APPLICATION_JSON));
	
		result.andExpectAll(status().isOk());
		result.andExpect(jsonPath("$").exists());
		result.andExpect(jsonPath("$[0].client").value("10"));
		result.andExpect(jsonPath("$[0].total").value("100.0"));
		result.andExpect(jsonPath("$[0].id").value("1"));
		result.andExpect(jsonPath("$[1].client").value("2"));
		result.andExpect(jsonPath("$[1].total").value("500.0"));
		result.andExpect(jsonPath("$[1].id").value("2"));
	}
	
}

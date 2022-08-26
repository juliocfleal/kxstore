package com.kx.store.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.kx.store.DTO.ClientDTO;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ClientIT {
	
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
	public void insertClientShouldInserNewClient() throws Exception{
		ClientDTO clientDto = new ClientDTO();
		clientDto.setDocument("12345678912");
		clientDto.setNome("Julio Cesar Freitas Leal");
		String jsonBody = objectMapper.writeValueAsString(clientDto);
		String expectedDocument = clientDto.getDocument();
		String expectedName = clientDto.getName();
		
		ResultActions result = mockMvc.perform(post("/client")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.document").value(expectedDocument));
		result.andExpect(jsonPath("$.name").value(expectedName));	
	}
	
	@Test
	public void updateClientShouldUpdateCLientWhenIdIsValid() throws Exception {
		ClientDTO clientDto = new ClientDTO();
		clientDto.setDocument("09876543211");
		clientDto.setNome("Ariano Machado");
		String jsonBody = objectMapper.writeValueAsString(clientDto);
		String expectedDocument = clientDto.getDocument();
		String expectedName = clientDto.getName();
		
		ResultActions result = mockMvc.perform(put("/client/{id}", validId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.document").value(expectedDocument));
		result.andExpect(jsonPath("$.name").value(expectedName));	
		
	}
	
	@Test
	public void updateClientShouldThrowsExceptionWhenIdISInvalid() throws Exception {
		ClientDTO clientDto = new ClientDTO();
		clientDto.setDocument("19283746501");
		clientDto.setNome("Chico Suassuna");
		String jsonBody = objectMapper.writeValueAsString(clientDto);
		
		ResultActions result = mockMvc.perform(put("/client/{id}", invalidId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	
	@Test
	public void DeleteClientShouldReturnNoContentWhenIdIsValid() throws Exception {

		
		ResultActions result = mockMvc.perform(delete("/client/{id}", validId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNoContent());
	}
	
	@Test
	public void DeleteClientShouldReturnNotFoundWhenIdIsInvalid() throws Exception {		
		ResultActions result = mockMvc.perform(delete("/client/{id}", invalidId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	
}

package com.kx.store.services;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kx.store.DTO.ClientDTO;
import com.kx.store.entities.Client;
import com.kx.store.repositories.ClientRepository;
import com.kx.store.repositories.SaleRepository;
import com.kx.store.services.exceptions.DataBaseException;
import com.kx.store.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	ClientRepository repository;
	
	@Autowired
	SaleRepository salesRepository;

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client client = new Client();
		client.setDocument(dto.getDocument());
		client.setName(dto.getName());
		client = repository.save(client);
		return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client client = repository.getReferenceById(id);
			client.setDocument(dto.getDocument());
			client.setName(dto.getName());
			client = repository.save(client);
			return new ClientDTO(client);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		}

	}

	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found!");
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
	}

}

package com.kx.store.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kx.store.DTO.SaleDTO;
import com.kx.store.entities.Client;
import com.kx.store.entities.Sale;
import com.kx.store.repositories.ClientRepository;
import com.kx.store.repositories.SaleRepository;
import com.kx.store.services.exceptions.ResourceNotFoundException;

@Service
public class SaleService {

	@Autowired
	SaleRepository repository;

	@Autowired
	ClientRepository clientRepository;

	@Transactional(readOnly = true)
	public List<SaleDTO> findAll() {
		List<Sale> list = repository.findAll();
		List<SaleDTO> listDTO = (List<SaleDTO>) list.stream().map(x -> new SaleDTO(x)).collect(Collectors.toList());
		return listDTO;
	}

	@Transactional(readOnly = true)
	public SaleDTO findById(Long id) {
		Sale sale = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
		return new SaleDTO(sale);
	}

	
	@Transactional
	public SaleDTO insert(SaleDTO saleDTO) {
		Client client = clientRepository.findById(saleDTO.getClient())
				.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
		Sale sale = new Sale();
		sale.setClient(client);
		sale.setTotal(saleDTO.getTotal());
		sale = repository.save(sale);
		return new SaleDTO(sale);
	}

}

package com.kx.store.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kx.store.DTO.SaleDTO;
import com.kx.store.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleResource {

	@Autowired
	SaleService service;

	@GetMapping
	public ResponseEntity<List<SaleDTO>> findAll() {
		List<SaleDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleDTO> findById(@PathVariable Long id){
		SaleDTO saleDTO = service.findById(id);
		return ResponseEntity.ok().body(saleDTO);
	}
	

	@PostMapping
	public ResponseEntity<SaleDTO> insert(@RequestBody SaleDTO saleDTO) {
		saleDTO = service.insert(saleDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saleDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(saleDTO);
	}
}

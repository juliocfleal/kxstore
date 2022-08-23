package com.kx.store.DTO;

import java.io.Serializable;
import java.util.Objects;

import com.kx.store.entities.Sale;

public class SaleDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long client;
	private Double total;
	
	public SaleDTO() {
		
	}

	public SaleDTO(Long id, Long client, Double total) {
		this.id = id;
		this.client = client;
		this.total = total;
	}

	public SaleDTO(Sale sale) {
		this.id = sale.getId();
		this.client = sale.getClient().getId();
		this.total = sale.getTotal();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClient() {
		return client;
	}

	public void setClient(Long client) {
		this.client = client;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SaleDTO other = (SaleDTO) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}

package com.kx.store.DTO;

import java.io.Serializable;
import java.util.Objects;

import com.kx.store.entities.Client;

public class ClientDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String document;
	
	public ClientDTO() {
	}

	public ClientDTO(Long id, String name, String document) {
		this.id = id;
		this.name = name;
		this.document = document;
	}

	public ClientDTO(Client client) {
		this.id = client.getId();
		this.name = client.getName();
		this.document = client.getDocument();
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setNome(String name) {
		this.name = name;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	@Override
	public int hashCode() {
		return Objects.hash(document, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientDTO other = (ClientDTO) obj;
		return Objects.equals(document, other.document) && Objects.equals(id, other.id);
	}

	
}

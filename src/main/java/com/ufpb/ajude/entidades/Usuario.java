package com.ufpb.ajude.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@AllArgsConstructor
@Getter
@Setter
//@Builder(builderClassName = "Builder")
@Entity
public class Usuario {
	@Id
	private String email;
	
	private String primeiroNome;
	
	private String ultimoNome;
	
	private String numeroCartao;
	
	private String senha;
	
	@JsonManagedReference
	@OneToMany(mappedBy="criador")
	private List<Campanha> campanhas = new ArrayList<Campanha>();
	
	Usuario() {
		
	}
	
}

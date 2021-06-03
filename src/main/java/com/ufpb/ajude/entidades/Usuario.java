package com.ufpb.ajude.entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder")
@Entity
public class Usuario {
	@Id
	private String email;
	
	private String primeiroNome;
	
	private String ultimoNome;
	
	private String numeroCartao;
	
	private String senha;
	
	Usuario() {
		
	}
	
}

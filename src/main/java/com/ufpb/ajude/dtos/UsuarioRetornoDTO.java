package com.ufpb.ajude.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder")
public class UsuarioRetornoDTO {
	private String email;
	
	private String primeiroNome;
	
	private String ultimoNome;
}

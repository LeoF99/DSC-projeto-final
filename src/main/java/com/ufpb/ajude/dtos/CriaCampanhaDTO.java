package com.ufpb.ajude.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder")
public class CriaCampanhaDTO {
	private String nome;
	
	private String descricao;
	
	private String deadline;
	
	private String status;
	
	private double meta;
	
	private String emailCriador;
}

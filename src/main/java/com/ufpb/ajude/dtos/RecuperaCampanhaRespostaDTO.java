package com.ufpb.ajude.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder")
public class RecuperaCampanhaRespostaDTO {
	private long id;
	
	private String nome;
	
	private Date deadline;
	
	private String status;
	
	private double meta;
}

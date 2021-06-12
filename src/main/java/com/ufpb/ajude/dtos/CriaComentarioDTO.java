package com.ufpb.ajude.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder")
public class CriaComentarioDTO {
	private long campanha;
	
	private String conteudo;
	
	private String criador;
}

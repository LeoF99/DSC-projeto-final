package com.ufpb.ajude.entidades;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder")
@Entity
public class Campanha {
	@Id
	@GeneratedValue()
	private long id;
	
	private String nome;
	
	private String descricao;
	
	private Date deadline;
	
	private String status;
	
	private double meta;
	
	//private Doacao[] doacoes;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="criador_id")
	private Usuario criador;
	
	//private Comentario[] comentarios;
	
	//private Like[] likes;
}

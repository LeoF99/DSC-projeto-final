package com.ufpb.ajude.entidades;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
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
	
	@JsonBackReference
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="criador_id")
	private Usuario criador;
	
	//private Comentario[] comentarios;
	
	//private Like[] likes;
	
	public Campanha(String nome, String descricao, Date deadline, String status, double meta, Usuario criador) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.deadline = deadline;
		this.status = status;
		this.meta = meta;
		this.criador = criador;
	}

}

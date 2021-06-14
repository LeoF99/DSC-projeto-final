package com.ufpb.ajude.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class Comentario {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne()
	@JoinColumn(name="campanha_id")
	private Campanha campanha;
	
	private String conteudo;
	
	private String criador;
	
	@OneToMany()
	private List<Comentario> respostas = new ArrayList<Comentario>();
	
	@JsonIgnore
	@ManyToOne()
	@JoinColumn()
	private Comentario comentarioPai;
	
	public Comentario(String conteudo, String criador) {
		super();
		this.conteudo = conteudo;
		this.criador = criador;
	}
	
	public Comentario(String conteudo, String criador, Comentario comentarioPai) {
		super();
		this.conteudo = conteudo;
		this.criador = criador;
		this.comentarioPai = comentarioPai;
	}
	
	public Comentario() {}
}

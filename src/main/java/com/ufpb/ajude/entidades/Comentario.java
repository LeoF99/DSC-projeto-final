package com.ufpb.ajude.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class Comentario {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne()
	private Campanha campanha;
	
	private String conteudo;
	
	private String criador;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Comentario> respostas = new ArrayList<Comentario>();
	
	public Comentario(Campanha campanha, String conteudo, String criador) {
		super();
		this.campanha = campanha;
		this.conteudo = conteudo;
		this.criador = criador;
	}
	
	public Comentario() {}
}

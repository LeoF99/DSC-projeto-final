package com.ufpb.ajude.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
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
	
	@JsonIgnore
	@OneToMany()
	private List<Doacao> doacoes = new ArrayList<Doacao>();
	
	@JsonBackReference
	@ManyToOne()
	@JoinColumn(name="criador_id")
	private Usuario criador;
	
	@JsonIgnore
	@OneToMany()
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	@JsonIgnore
	@OneToMany()
	private List<Like> likes = new ArrayList<Like>();
	
	public Campanha(String nome, String descricao, Date deadline, String status, double meta, Usuario criador) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.deadline = deadline;
		this.status = status;
		this.meta = meta;
		this.criador = criador;
	}
	
	public Campanha() {
		
	}
}

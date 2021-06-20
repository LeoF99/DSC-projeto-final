package com.ufpb.ajude.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class Doacao {
	@Id
	@GeneratedValue()
	private int id;
	
	private double valor;
	
	private String doador;
	
	private long campanhaId;
	
	private Date dataDeDoacao;

	public Doacao(double valor, String doador, long campanhaId, Date dataDeDoacao) {
		super();
		this.valor = valor;
		this.doador = doador;
		this.campanhaId = campanhaId;
		this.dataDeDoacao = dataDeDoacao;
	}
}

package com.ufpb.ajude.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="likes")
public class Like {
	@Id
	@GeneratedValue()
	private int id;
	
	private String usuario;

	public Like(String usuario) {
		super();
		this.usuario = usuario;
	}
}

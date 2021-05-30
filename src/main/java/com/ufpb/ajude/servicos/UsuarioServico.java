package com.ufpb.ajude.servicos;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.ufpb.ajude.entidades.Usuario;
import com.ufpb.ajude.repositorios.UsuarioRepositorio;
import com.ufpb.ajude.dtos.UsuarioRetornoDTO;

@Service
public class UsuarioServico {
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	public UsuarioServico() {
		super();
	}
	
	public UsuarioRetornoDTO criaUsuario(Usuario usuario) {
		if(!this.usuarioRepositorio.findById(usuario.getEmail()).isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		this.usuarioRepositorio.save(usuario);
		
		return new UsuarioRetornoDTO(usuario.getEmail(), usuario.getPrimeiroNome(), usuario.getUltimoNome());
	}
}

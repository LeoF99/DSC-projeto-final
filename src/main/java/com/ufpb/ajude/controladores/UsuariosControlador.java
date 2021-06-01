package com.ufpb.ajude.controladores;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.ufpb.ajude.entidades.Usuario;
import com.ufpb.ajude.dtos.UsuarioRetornoDTO;
import com.ufpb.ajude.servicos.UsuarioServico;

@RestController
public class UsuariosControlador {
	@Autowired
	private UsuarioServico usuarioServico;
	
	public UsuariosControlador(UsuarioServico usuarioServico) {
		super();
		this.usuarioServico = usuarioServico;
	}
	
	@PostMapping("usuarios")
	public ResponseEntity<UsuarioRetornoDTO> criaUsuario(@RequestBody Usuario usuario) {
		try {
			UsuarioRetornoDTO usuarioCriado = this.usuarioServico.criaUsuario(usuario);
			
			return new ResponseEntity<UsuarioRetornoDTO>(usuarioCriado, HttpStatus.OK);
		} catch (HttpClientErrorException error) {
			return new ResponseEntity<UsuarioRetornoDTO>(error.getStatusCode());
		}
	}
}

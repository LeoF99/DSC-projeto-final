package com.ufpb.ajude.controladores;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.ufpb.ajude.entidades.Usuario;
import com.ufpb.ajude.dtos.LoginDTO;
import com.ufpb.ajude.dtos.RespostaDeLogin;
import com.ufpb.ajude.dtos.UsuarioRetornoDTO;
import com.ufpb.ajude.servicos.UsuarioServico;
import com.ufpb.ajude.servicos.JwtService;

@RestController
public class UsuariosControlador {
	@Autowired
	private UsuarioServico usuarioServico;
	
	@Autowired
	private JwtService jwtService;
	
	public UsuariosControlador(UsuarioServico usuarioServico, JwtService jwtService) {
		super();
		this.usuarioServico = usuarioServico;
		this.jwtService = jwtService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<RespostaDeLogin> login(@RequestBody LoginDTO login) throws ServletException {
		return new ResponseEntity<RespostaDeLogin>(jwtService.autentica(login), HttpStatus.OK);
	}
	
	@PostMapping("/usuarios")
	public ResponseEntity<UsuarioRetornoDTO> criaUsuario(@RequestBody Usuario usuario) {
		try {
			UsuarioRetornoDTO usuarioCriado = this.usuarioServico.criaUsuario(usuario);
			
			return new ResponseEntity<UsuarioRetornoDTO>(usuarioCriado, HttpStatus.OK);
		} catch (HttpClientErrorException error) {
			return new ResponseEntity<UsuarioRetornoDTO>(error.getStatusCode());
		}
	}
}

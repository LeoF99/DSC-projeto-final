package com.ufpb.ajude.servicos;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.ufpb.ajude.entidades.Usuario;
import com.ufpb.ajude.repositorios.UsuarioRepositorio;
import com.ufpb.ajude.dtos.LoginDTO;
import com.ufpb.ajude.dtos.UsuarioRetornoDTO;

@Service
public class UsuarioServico {
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private JwtService jwtService;
	
	public UsuarioServico() {
		super();
	}
	
	@PostConstruct
	public void initDisciplinas() {
		Usuario usuario = new Usuario("email@email.com", "Leo", "Silva", "12345", "12345", null);
		
		this.usuarioRepositorio.save(usuario);
	}
	
	public UsuarioRetornoDTO criaUsuario(Usuario usuario) {
		if(!this.usuarioRepositorio.findById(usuario.getEmail()).isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		this.usuarioRepositorio.save(usuario);
		
		return new UsuarioRetornoDTO(usuario.getEmail(), usuario.getPrimeiroNome(), usuario.getUltimoNome());
	}
	
	public boolean validaUsuarioSenha(LoginDTO login) {
		Optional<Usuario> optUsuario = this.usuarioRepositorio.findById(login.getEmail());
		if (optUsuario.isPresent() && optUsuario.get().getSenha().equals(login.getSenha()))
			return true;
		return false;
	}
	
	public boolean usuarioTemPermissao(String authorizationHeader, String email) throws ServletException {
		String subject = jwtService.getSujeitoDoToken(authorizationHeader);
		Optional<Usuario> optUsuario = this.usuarioRepositorio.findById(subject);
		return optUsuario.isPresent() && optUsuario.get().getEmail().equals(email);
	}
}

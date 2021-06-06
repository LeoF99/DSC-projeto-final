package com.ufpb.ajude.servicos;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufpb.ajude.dtos.LoginDTO;
import com.ufpb.ajude.dtos.RespostaDeLogin;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Service
public class JwtService {
	@Autowired
	private UsuarioServico usuariosService;
	private final String TOKEN_KEY = "TOKEN_BOLADO";

	public RespostaDeLogin autentica(LoginDTO login) {

		if (!usuariosService.validaUsuarioSenha(login)) {
			return new RespostaDeLogin("Usuario ou senha invalidos. Nao foi realizado o login.");
		}

		String token = geraToken(login.getEmail());
		return new RespostaDeLogin(token);
	}

	private String geraToken(String email) {
		return Jwts.builder().setSubject(email)
				.signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)).compact();// 30 min
	}
	
	public String getSujeitoDoToken(String authorizationHeader) throws ServletException {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new ServletException("Token inexistente ou mal formatado!");
		}
		
		String token = authorizationHeader.substring(7);

		String subject = null;
		try {
			subject = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
		} catch (SignatureException e) {
			throw new ServletException("Token invalido ou expirado!");
		}
		return subject;
	}
}

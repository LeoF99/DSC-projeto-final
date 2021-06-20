package com.ufpb.ajude.servicos;

import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.ufpb.ajude.entidades.Campanha;
import com.ufpb.ajude.entidades.Like;
import com.ufpb.ajude.repositorios.CampanhaRepositorio;
import com.ufpb.ajude.repositorios.LikeRepositorio;

@Service
public class LikeServico {
	@Autowired
	private LikeRepositorio likeRepositorio;
	
	@Autowired
	private CampanhaRepositorio campanhaRepositorio;
	
	@Autowired
	private UsuarioServico usuarioServico;
	
	public LikeServico() {
		super();
	}
	
	public Campanha darLike(long idCampanha, String email, String header) throws ServletException {
		if(!this.usuarioServico.usuarioTemPermissaoRota(header, email)) {
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
		}
		
		Optional<Campanha> buscaCampanha = this.campanhaRepositorio.findById(idCampanha);
		
		if(buscaCampanha.isEmpty()) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		
		Campanha campanha = buscaCampanha.get();
		
		List<Like> likes = campanha.getLikes();
		
		boolean deuLike = this.checarSeUsuarioDeuLike(likes, email);
		
		if(deuLike) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		
		return this.adicionaLikeCampanha(campanha, likes, email);
	}
	
	public Campanha removerLike(long idCampanha, String email, String header) throws ServletException {
		if(!this.usuarioServico.usuarioTemPermissaoRota(header, email)) {
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
		}
		
		Optional<Campanha> buscaCampanha = this.campanhaRepositorio.findById(idCampanha);
		
		if(buscaCampanha.isEmpty()) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		
		Campanha campanha = buscaCampanha.get();
		
		List<Like> likes = campanha.getLikes();
		
		boolean deuLike = this.checarSeUsuarioDeuLike(likes, email);
		
		if(!deuLike) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		
		return this.removeLikeCampanha(campanha, likes, email);
	}
	
	private Campanha removeLikeCampanha(Campanha campanha, List<Like> likes, String email) {
		for(Like l : likes) {
			if(l.getUsuario().equals(email)) {
				likes.remove(l);
			}
		}
		
		campanha.setLikes(likes);
		
		return this.campanhaRepositorio.save(campanha);
	}
	
	private Campanha adicionaLikeCampanha(Campanha campanha, List<Like> likes, String email) {
		Like like = new Like(email);
		
		this.likeRepositorio.save(like);
		
		likes.add(like);
		
		campanha.setLikes(likes);
		
		return this.campanhaRepositorio.save(campanha);
	}
	
	private boolean checarSeUsuarioDeuLike(List<Like> likes, String email) {
		boolean deuLike = false;
		
		for(Like like : likes) {
			if(like.getUsuario().equals(email)) {
				deuLike = true;
			}
		}
		
		return deuLike;
	}
}

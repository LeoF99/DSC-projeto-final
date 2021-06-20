package com.ufpb.ajude.controladores;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.ufpb.ajude.entidades.Campanha;
import com.ufpb.ajude.servicos.LikeServico;

@RestController
public class LikesControlador {
	@Autowired
	private LikeServico likeServico;
	
	@PostMapping("/auth/campanhas/{id}/likes")
	public ResponseEntity<Campanha> darLike(@PathVariable Long id, @RequestParam String email, @RequestHeader("Authorization") String header) throws ServletException {
		try {
			Campanha campanha = this.likeServico.darLike(id, email, header);
			
			return new ResponseEntity<Campanha>(campanha, HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<Campanha>(e.getStatusCode());
		}
	}
	
	@DeleteMapping("/auth/campanhas/{id}/likes")
	public ResponseEntity<Campanha> removerLike(@PathVariable Long id, @RequestParam String email, @RequestHeader("Authorization") String header) throws ServletException {
		try {
			Campanha campanha = this.likeServico.removerLike(id, email, header);
			
			return new ResponseEntity<Campanha>(campanha, HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<Campanha>(e.getStatusCode());
		}
	}
}

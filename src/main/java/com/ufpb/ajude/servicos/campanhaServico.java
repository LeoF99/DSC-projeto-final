package com.ufpb.ajude.servicos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.ufpb.ajude.repositorios.CampanhaRepositorio;
import com.ufpb.ajude.entidades.Campanha;
import com.ufpb.ajude.dtos.CriaCampanhaDTO;
import com.ufpb.ajude.repositorios.UsuarioRepositorio;
import com.ufpb.ajude.entidades.Usuario;

@Service
public class campanhaServico {
	@Autowired
	private CampanhaRepositorio campanhaRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	public campanhaServico(CampanhaRepositorio campanhaRepositorio, UsuarioRepositorio usuarioRepositorio) {
		this.campanhaRepositorio = campanhaRepositorio;
		this.usuarioRepositorio = usuarioRepositorio;
	}
	
	public Campanha criaCampanha(CriaCampanhaDTO campanha) {
		Optional<Usuario> usuario = this.usuarioRepositorio.findById(campanha.getEmailCriador());
		
		if(!usuario.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		
		Campanha campanhaCriada;
		try {
			campanhaCriada = new Campanha(
				campanha.getNome(),
				campanha.getDescricao(),
				new SimpleDateFormat("dd/MM/yyyy").parse(campanha.getDeadline()),
				campanha.getStatus(),
				campanha.getMeta(),
				usuario.get()
			);
			
			if(campanhaCriada.getDeadline().after(new Date())) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
			}
			
			if(!campanha.getStatus().equals("Ativa")) {
				campanha.setStatus("Ativa");
			}
			
			campanhaCriada = this.campanhaRepositorio.save(campanhaCriada);
			
			return campanhaCriada;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public Campanha encerraCampanha(Long id) {
		Optional<Campanha> buscaCampanha = this.campanhaRepositorio.findById(id);
		
		if(!buscaCampanha.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		
		Campanha campanha = buscaCampanha.get();
		
		if(!campanha.getStatus().equals("Encerrada") || campanha.getStatus().equals("Concluída")) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		
		campanha.setStatus("Encerrada");
		
		return campanha;
	}
	
	public Campanha concluiCampanha(Long id) {
		Optional<Campanha> buscaCampanha = this.campanhaRepositorio.findById(id);
		
		if(!buscaCampanha.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		
		Campanha campanha = buscaCampanha.get();
		
		if(!campanha.getStatus().equals("Encerrada") || campanha.getStatus().equals("Concluída")) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		
		campanha.setStatus("Concluída");
		
		return campanha;
	}
	
	public List<Campanha> buscaCampanhas(String busca, boolean retornarTodas) {
		if(busca.length() == 0) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		
		if(retornarTodas == true) {
			List<Campanha> todasCampanhas = this.campanhaRepositorio.buscaPorNome(busca);	
			
			return todasCampanhas;
		}
		
		List<Campanha> campanhasAtivas = this.campanhaRepositorio.buscaPorNome(busca);
		
		return campanhasAtivas;
	}
	
	private void atualizaStatusVencido(Campanha campanha) {
		if(campanha.getStatus().equals("Ativa")) {
			if(campanha.getDeadline().after(new Date())) {
				campanha.setStatus("Vencida");
			}
		}
	}
}

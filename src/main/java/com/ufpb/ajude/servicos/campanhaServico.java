package com.ufpb.ajude.servicos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.ufpb.ajude.repositorios.CampanhaRepositorio;
import com.ufpb.ajude.repositorios.ComentarioRepositorio;
import com.ufpb.ajude.entidades.Campanha;
import com.ufpb.ajude.entidades.Comentario;
import com.ufpb.ajude.dtos.CriaCampanhaDTO;
import com.ufpb.ajude.dtos.CriaComentarioDTO;
import com.ufpb.ajude.repositorios.UsuarioRepositorio;
import com.ufpb.ajude.entidades.Usuario;
import com.ufpb.ajude.dtos.AtualizaCampanhaDTO;

@Service
public class campanhaServico {
	@Autowired
	private CampanhaRepositorio campanhaRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private UsuarioServico usuarioServico;
	
	@Autowired
	private ComentarioRepositorio comentarioRepositorio;
	
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
	
	public List<Campanha> buscaCampanhas(String busca, boolean retornarTodas) {
		if(busca.length() == 0) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		
		List<Campanha> todasCampanhas = this.campanhaRepositorio.findAll();
		
		if(!retornarTodas) {
			List<Campanha> apenasAtivas = new ArrayList<Campanha>();
			
			for(Campanha c : todasCampanhas) {
				this.atualizaStatusVencido(c);
				if(c.getStatus().equals("Ativa")) {
					String nome = c.getNome().toLowerCase();
					
					if(nome.contains(busca.toLowerCase())) {
						apenasAtivas.add(c);
					}
				}
			}
			
			return apenasAtivas;
		}
		
		List<Campanha> matchBusca = new ArrayList<Campanha>();
		
		for(Campanha c : todasCampanhas) {
			this.atualizaStatusVencido(c);
			String nome = c.getNome().toLowerCase();
				
			if(nome.contains(busca.toLowerCase())) {
				matchBusca.add(c);
			}
		}
		
		return matchBusca;
	}
	
	public Campanha encerraCampanha(Long id, String email, String header) throws ServletException {
		Optional<Usuario> usuario = this.usuarioRepositorio.findById(email);
		
		if(usuario.isPresent()) {
			if (this.usuarioServico.usuarioTemPermissao(header, email)) {
				Optional<Campanha> buscaCampanha = this.campanhaRepositorio.findById(id);
				
				if(buscaCampanha.isEmpty()) {
					throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
				}
				
				Campanha campanha = buscaCampanha.get();
				
				campanha.setStatus("Encerrada");
				
				this.campanhaRepositorio.save(campanha);
				
				return campanha;
			}
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
	}
	
	public Campanha concluiCampanha(Long id, String email, String header) throws ServletException {
		Optional<Usuario> usuario = this.usuarioRepositorio.findById(email);
		
		if(usuario.isPresent()) {
			if (this.usuarioServico.usuarioTemPermissao(header, email)) {
				Optional<Campanha> buscaCampanha = this.campanhaRepositorio.findById(id);
				
				if(buscaCampanha.isEmpty()) {
					throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
				}
				
				Campanha campanha = buscaCampanha.get();
				
				campanha.setStatus("Concluída");
				
				this.campanhaRepositorio.save(campanha);
				
				return campanha;
			}
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
	}
	
	public Campanha atualizaCampanha(Long id, AtualizaCampanhaDTO campanha, String email, String header) throws ServletException, ParseException {
		Optional<Usuario> usuario = this.usuarioRepositorio.findById(email);
		
		if(usuario.isPresent()) {
			if(this.usuarioServico.usuarioTemPermissao(header, email)) {
				Optional<Campanha> buscaCampanha = this.campanhaRepositorio.findById(id);
				
				if(buscaCampanha.isEmpty()) {
					throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
				}
				
				Campanha campanhaAtualizada = buscaCampanha.get();
				
				campanhaAtualizada.setDescricao(campanha.getDescricao());
				campanhaAtualizada.setDeadline(new SimpleDateFormat("dd/MM/yyyy").parse(campanha.getDeadline()));
				campanhaAtualizada.setMeta(campanha.getMeta());
				
				this.campanhaRepositorio.save(campanhaAtualizada);
				
				return campanhaAtualizada;
			}
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
	}
	
	public Comentario adicionaComentario(CriaComentarioDTO dados) {
		Optional<Campanha> buscaCampanha = this.campanhaRepositorio.findById(dados.getCampanha());
		
		if(buscaCampanha.isEmpty()) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		Campanha campanha = buscaCampanha.get();
		System.out.println(campanha.getDescricao());
		Comentario comentario = new Comentario(campanha, dados.getConteudo(), dados.getCriador());
		Comentario novoComentario = this.comentarioRepositorio.save(comentario);
		
		
		campanha.getComentarios().add(novoComentario);
		
		this.campanhaRepositorio.save(campanha);
		
		return novoComentario;
	}
	
	public Comentario respondeComentario(CriaComentarioDTO dados, long aResponderId) {
		Optional<Comentario> buscaComentario = this.comentarioRepositorio.findById(aResponderId);
		if(buscaComentario.isEmpty()) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		
		Optional<Campanha> buscaCampanha = this.campanhaRepositorio.findById(dados.getCampanha());
		if(buscaCampanha.isEmpty()) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		
		Comentario resposta = new Comentario(buscaCampanha.get(), dados.getConteudo(), dados.getCriador());
		resposta = this.comentarioRepositorio.save(resposta);
		
		Comentario aResponder = buscaComentario.get();
		aResponder.getRespostas().add(resposta);
		this.comentarioRepositorio.save(aResponder);
		
		return resposta;
	}
	
	private void atualizaStatusVencido(Campanha campanha) {
		if(campanha.getStatus().equals("Ativa")) {
			if(campanha.getDeadline().after(new Date())) {
				campanha.setStatus("Vencida");
				this.campanhaRepositorio.save(campanha);
			}
		}
	}
}


package org.springframework.cheapy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.model.Usuario;
import org.springframework.cheapy.repository.UsuarioRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

	private UsuarioRepository	usuarioRepository;
	private ReviewClientService	reviewClientService;
	private ReviewService		reviewService;


	@Autowired
	public UsuarioService(final UsuarioRepository usuarioRepository, final ReviewClientService reviewClientService, final ReviewService reviewService) {
		this.usuarioRepository = usuarioRepository;
		this.reviewClientService = reviewClientService;
		this.reviewService = reviewService;
	}

	@Transactional
	public Usuario getCurrentUsuario() throws DataAccessException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return this.usuarioRepository.findByUsername(username);
	}

	@Transactional
	public Usuario findByUsername(final String username) throws DataAccessException {
		return this.usuarioRepository.findByUsername(username);
	}
	
	@Transactional
	public Usuario findById(final Integer id) throws DataAccessException {
		return this.usuarioRepository.findById(id).get();
	}

	@Transactional
	public List<Usuario> findAllUsuario(final Pageable page) throws DataAccessException {
		return this.usuarioRepository.findAllUsuario(page);
	}

	@Transactional
	public List<Usuario> findUsuarioEnabled(final Pageable page) throws DataAccessException {
		return this.usuarioRepository.findUsuarioEnabled(page);
	}

	@Transactional
	public void saveUsuario(final Usuario usuario) throws DataAccessException {
		this.usuarioRepository.save(usuario);
	}

	@Transactional
	public void deleteUsuario(final Usuario usuario) throws DataAccessException {
		User user = usuario.getUsuar();
		this.reviewClientService.deleteReviewsByUser(user);
		this.reviewService.deleteReviewsByUser(user);
		this.usuarioRepository.delete(usuario);
	}
}

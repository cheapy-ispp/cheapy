
package org.springframework.cheapy.repository;

import java.util.List;

import org.springframework.cheapy.model.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, String> {

	@Query("SELECT usuario FROM Usuario usuario WHERE username =:username")
	@Transactional(readOnly = true)
	Usuario findByUsername(String username);

	@Query("SELECT usuario FROM Usuario usuario")
	@Transactional(readOnly = true)
	List<Usuario> findAllUsuario(Pageable page);

	@Query("SELECT usuario FROM Usuario usuario WHERE usuario.usuar.enabled = true")
	@Transactional(readOnly = true)
	List<Usuario> findUsuarioEnabled(Pageable page);

	//void save(Usuario usuario);

}

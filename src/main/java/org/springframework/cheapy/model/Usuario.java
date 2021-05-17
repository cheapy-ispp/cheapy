
package org.springframework.cheapy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "usuarios")
public class Usuario extends BaseEntity {

	/*
	 * nombre, apellidos, dni, direccion, telefono, email, username
	 * (id,nombre, apellidos, dni, direccion, telefono, email, usuar)
	 */

	private static final long	serialVersionUID	= 1L;

	@NotBlank(message = "No debe estar vacío")
	private String				nombre;

	@NotBlank(message = "No debe estar vacío")
	private String				apellidos;

	@Pattern(message = "El formato no es correcto", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
	@NotBlank(message = "No debe estar vacío")
	private String				email;
	
	@NotBlank(message = "No debe estar vacío")
	@JoinColumn(name = "pregunta_segura1")
	private String				preguntaSegura1;
	
	@NotBlank(message = "No debe estar vacío")
	@JoinColumn(name = "pregunta_segura2")
	private String				preguntaSegura2;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User				usuar;

	@ManyToMany
	@JoinTable(name = "usuario_favoritos", joinColumns = {
		@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	}, inverseJoinColumns = {
		@JoinColumn(name = "client_id", referencedColumnName = "id")
	})
	private List<Client>		favoritos			= new ArrayList<>();


	public List<Client> getFavoritos() {
		return this.favoritos;
	}

	public void setFavoritos(final List<Client> favoritos) {
		this.favoritos = favoritos;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(final String apellidos) {
		this.apellidos = apellidos;
	}

	public User getUsuar() {
		return this.usuar;
	}

	public void setUsuar(final User usuar) {
		this.usuar = usuar;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPreguntaSegura1() {
		return preguntaSegura1;
	}

	public void setPreguntaSegura1(String preguntaSegura1) {
		this.preguntaSegura1 = preguntaSegura1;
	}

	public String getPreguntaSegura2() {
		return preguntaSegura2;
	}

	public void setPreguntaSegura2(String preguntaSegura2) {
		this.preguntaSegura2 = preguntaSegura2;
	}
}

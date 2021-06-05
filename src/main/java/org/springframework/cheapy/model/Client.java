
package org.springframework.cheapy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "clients")
public class Client extends BaseEntity {

	private static final long	serialVersionUID	= 1L;

	// (id, name, email, address, init, finish, telephone, description, code, food,
	// usuar)

	@Pattern(message = "La primera letra debe estar en mayúscula", regexp = "^[A-Z]['a-zA-ZÀ-ÿ\\\\u00f1\\\\u00d1]?+(\\\\s*['a-zA-Z0-9À-ÿ\\\\u00f1\\\\u00d1]*)*['a-zA-ZÀ-ÿ\\\\u00f1\\\\u00d1]*$")
	@NotEmpty(message = "No debe estar vacío")
	private String				name;

	@Pattern(message = "El formato no es correcto", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
	@NotEmpty(message = "No debe estar vacío")
	private String				email;

	@NotBlank(message = "No debe estar vacío")
	private String				address;
	
	@NotNull(message = "No debe estar vacío")
	private Boolean				parking;

	@NotNull(message = "No debe estar vacío")
	@Enumerated(value = EnumType.STRING)
	private Municipio			municipio;

	// Hora de apertura del local
	@DateTimeFormat(pattern = "HH:mm")
	@NotNull(message = "Debe introducir una hora de apertura")
	private LocalTime			init;

	// Hora de cierre del local
	@DateTimeFormat(pattern = "HH:mm")
	@NotNull(message = "Debe introducir una hora de cierre")
	private LocalTime			finish;

	@NotEmpty(message = "No debe estar vacío")
	@Pattern(regexp = "\\d{9}", message = "Debe tener 9 dígitos")
	private String				telephone;

	@NotBlank(message = "No debe estar vacía")
	private String				description;

	@NotBlank(message = "No debe estar vacío")
	private String				food;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate			expiration;
	
	@URL(message="Debe introducir una url")
	@Size(max=2000, message="La url es demasiado grande")
	private String 				image;

	@NotBlank(message = "No debe estar vacío")
	@JoinColumn(name = "pregunta_segura1")
	private String				preguntaSegura1;
	
	@NotBlank(message = "No debe estar vacío")
	@JoinColumn(name = "pregunta_segura2")
	private String				preguntaSegura2;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User				usuar;

	@OneToMany
	private List<FoodOffer>		foodOffers;

	@OneToMany
	private List<NuOffer>		nuOffers;

	@OneToMany
	private List<SpeedOffer>	speedOffers;

	@OneToMany
	private List<TimeOffer>		timeOffers;


	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}
	
	public Boolean getParking() {
		return parking;
	}

	public void setParking(Boolean parking) {
		this.parking = parking;
	}

	public Municipio getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(final Municipio municipio) {
		this.municipio = municipio;
	}

	public LocalTime getInit() {
		return this.init;
	}

	public void setInit(final LocalTime init) {
		this.init = init;
	}

	public LocalTime getFinish() {
		return this.finish;
	}

	public void setFinish(final LocalTime finish) {
		this.finish = finish;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getFood() {
		return this.food;
	}

	public void setFood(final String food) {
		this.food = food;
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

	public User getUsuar() {
		return this.usuar;
	}

	public void setUsuar(final User usuar) {
		this.usuar = usuar;
	}

	public List<FoodOffer> getFoodOffers() {
		return this.foodOffers;
	}

	public void setFoodOffers(final List<FoodOffer> foodOffers) {
		this.foodOffers = foodOffers;
	}

	public List<NuOffer> getNuOffers() {
		return this.nuOffers;
	}

	public void setNuOffers(final List<NuOffer> nuOffers) {
		this.nuOffers = nuOffers;
	}

	public List<SpeedOffer> getSpeedOffers() {
		return this.speedOffers;
	}

	public void setSpeedOffers(final List<SpeedOffer> speedOffers) {
		this.speedOffers = speedOffers;
	}

	public List<TimeOffer> getTimeOffers() {
		return this.timeOffers;
	}

	public void setTimeOffers(final List<TimeOffer> timeOffers) {
		this.timeOffers = timeOffers;
	}

	public LocalDate getExpiration() {
		return this.expiration;
	}

	public void setExpiration(final LocalDate expiration) {
		this.expiration = expiration;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	

}

package org.springframework.cheapy.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "speed_offers")
public class SpeedOffer extends Offer {

	// Ofertar por rapidez comiendo
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Debe rellenar el tiempo del rango oro")
	@DateTimeFormat(pattern = "HH:mm:ss")
	private LocalTime gold;

	@Column(name = "discount_gold")
	@NotNull(message = "Debe rellenar el descuento del rango oro")
	@Range(min = 1, max = 100, message = "El descuento debe estar entre 1 y 100 %")
	private Integer discountGold;

	@NotNull(message = "Debe rellenar el tiempo del rango plata")
	@DateTimeFormat(pattern = "HH:mm:ss")
	private LocalTime silver;

	@Column(name = "discount_silver")
	@NotNull(message = "Debe rellenar el descuento del rango plata")
	@Range(min = 1, max = 100, message = "El descuento debe estar entre 1 y 100 %")
	private Integer discountSilver;

	@NotNull(message = "Debe rellenar el tiempo del rango bronce")
	@DateTimeFormat(pattern = "HH:mm:ss")
	private LocalTime bronze;

	@Column(name = "discount_bronze")
	@NotNull(message = "Debe rellenar el descuento del rango bronce")
	@Range(min = 1, max = 100, message = "El descuento debe estar entre 1 y 100 %")
	private Integer discountBronze;

	public LocalTime getGold() {
		return gold;
	}

	public void setGold(LocalTime gold) {
		this.gold = gold;
	}

	public Integer getDiscountGold() {
		return discountGold;
	}

	public void setDiscountGold(Integer discountGold) {
		this.discountGold = discountGold;
	}

	public LocalTime getSilver() {
		return silver;
	}

	public void setSilver(LocalTime silver) {
		this.silver = silver;
	}

	public Integer getDiscountSilver() {
		return discountSilver;
	}

	public void setDiscountSilver(Integer discountSilver) {
		this.discountSilver = discountSilver;
	}

	public LocalTime getBronze() {
		return bronze;
	}

	public void setBronze(LocalTime bronze) {
		this.bronze = bronze;
	}

	public Integer getDiscountBronze() {
		return discountBronze;
	}

	public void setDiscountBronze(Integer discountBronze) {
		this.discountBronze = discountBronze;
	}

}

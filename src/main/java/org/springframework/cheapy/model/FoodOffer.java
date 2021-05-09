/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.cheapy.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "food_offers")
public class FoodOffer extends Offer {
	
	private static final long serialVersionUID = 1L;

	//Plato específico
	@NotBlank(message = "Debe rellenar la comida a ofertar")
	private String food;

	@NotNull(message = "Debe rellenar el descuento que proporciona")
	@Range(min = 1, max = 100, message = "El descuento debe estar entre 1 y 100 %")
	private Integer discount;
	
	@NotNull(message = "Debe rellenar el precio del plato")
	@Min(1)
	@Max(500000)
	private Double price;
	
	private String image;
	
	public Double getNewPrice() {
		Double cuenta = this.price - (this.price*this.discount)/100;
		BigDecimal bd = new BigDecimal(cuenta).setScale(2, RoundingMode.HALF_UP);
	    double val2 = bd.doubleValue();
		return val2;
	}
	
	public Double getPrice() {
		double val2 = 0.0;
		if(price != null) {
			BigDecimal bd = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
		    val2 = bd.doubleValue();
			return val2;
		}
		return price;
	}

	public void setPrice(Double price) {
		if(price != null) {
			BigDecimal bd = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
		    double val2 = bd.doubleValue();
			this.price = val2;
		}
		
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	

}
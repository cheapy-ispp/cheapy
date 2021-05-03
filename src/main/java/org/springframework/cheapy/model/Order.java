package org.springframework.cheapy.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Order{
    
    private double price;

    private String currency;

    private String method;

    private String intent;
    
	@NotBlank
    private String description;
	
	public Order() {

	}
	
	public Order(@NotNull double price, @NotBlank String currency, @NotBlank String method, @NotBlank String intent,
			@NotBlank String description) {
		super();
		this.price = price;
		this.currency = currency;
		this.method = method;
		this.intent = intent;
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getIntent() {
		return intent;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Order [price=" + price + ", currency=" + currency + ", method=" + method + ", intent=" + intent
				+ ", description=" + description + "]";
	}
}

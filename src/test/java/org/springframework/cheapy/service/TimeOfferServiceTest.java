package org.springframework.cheapy.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cheapy.configuration.PaypalConfig;
import org.springframework.cheapy.configuration.SecurityConfiguration;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.TimeOffer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class)  )
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@ContextConfiguration(classes = PaypalConfig.class)
public class TimeOfferServiceTest {

	@Autowired
	protected TimeOfferService service;
	
	private TimeOffer test;
	
	@Test
	void findByUserId() {
		List<TimeOffer> test = service.findTimeOfferActOclByUserId(1);
		Assertions.assertTrue(test!=null && test.get(0).getClient().getId()==1);
	}
	
	@Test
	void findByid() {
		TimeOffer test = service.findTimeOfferById(1);
		Assertions.assertTrue(test.getId()==1);
	}
	
	@Test
	void findAll() {
		Pageable elements = PageRequest.of(0, 5);
		List<TimeOffer> test = service.findAllTimeOffer(elements);
		Assertions.assertTrue(test.size()!=0);
	}
	
	@Test
	void findActive() {
		Pageable elements = PageRequest.of(0, 5);
		List<TimeOffer> test = service.findActiveTimeOffer(elements);
		Assertions.assertTrue(test.size()!=0 && !test.get(0).isInactive());
	}
	
	@Test
	void findByClientName() {
		Pageable elements = PageRequest.of(0, 5);
		List<TimeOffer> test = service.findTimeOfferByClientName("bar manoli",elements);
		Assertions.assertTrue(test.size()!=0 && test.get(0).getClient().getName().equals("bar manoli"));
	}
	
	@Test
	void findByClientFood() {
		Pageable elements = PageRequest.of(0, 5);
		List<TimeOffer> test = service.findTimeOfferByClientFood("americana",elements);
		Assertions.assertTrue(test.size()!=0 && test.get(0).getClient().getFood().equals("americana"));
	}
	
	@Test
	void findByClientPlace() {
		Pageable elements = PageRequest.of(0, 5);
		List<TimeOffer> test = service.findTimeOfferByClientPlace(Municipio.Sevilla,elements);
		Assertions.assertTrue(test.size()!=0 && test.get(0).getClient().getMunicipio().equals(Municipio.Sevilla));
	}
}

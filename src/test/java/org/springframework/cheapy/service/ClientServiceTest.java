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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class)  )
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@ContextConfiguration(classes = PaypalConfig.class)
public class ClientServiceTest {

	@Autowired
	protected ClientService service;
	
	private Client testClient;
	
	@Test
	void findByUsername() {
		Client test = service.findByUsername("manoli");
		Assertions.assertTrue(test!=null);
	}
	
	@Test
	void findByid() {
		Client test = service.findById(1);
		Assertions.assertTrue(test!=null);
	}
	
	@Test
	void findAll() {
		Pageable elements = PageRequest.of(0, 5);
		List<Client> test = service.findAllClient(elements);
		Assertions.assertTrue(test.size()!=0);
	}
	@Test
	void mediaValoraciones() {
		Client c = service.findById(1);
		Integer test = service.mediaValoraciones(c);
		Assertions.assertNotNull(test);
	}
}

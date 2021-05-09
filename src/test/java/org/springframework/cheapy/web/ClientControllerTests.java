package org.springframework.cheapy.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cheapy.configuration.SecurityConfiguration;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.NuOffer;
import org.springframework.cheapy.model.SpeedOffer;
import org.springframework.cheapy.model.TimeOffer;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.model.Usuario;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.FoodOfferService;
import org.springframework.cheapy.service.NuOfferService;
import org.springframework.cheapy.service.SpeedOfferService;
import org.springframework.cheapy.service.TimeOfferService;
import org.springframework.cheapy.service.UserService;
import org.springframework.cheapy.service.UsuarioService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = ClientController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
class ClientControllerTest {

	private static final int TEST_CLIENT_ID = 1;

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ClientService clientService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private UsuarioService usuarioService;
	
	@MockBean
	private FoodOfferService foodOfferService;
	
	@MockBean
	private SpeedOfferService speedOfferService;
	
	@MockBean
	private NuOfferService nuOfferService;
	
	@MockBean
	private TimeOfferService timeOfferService;


	@BeforeEach
	void setup() {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("user1");
		Client client1 = new Client();
		client1.setId(TEST_CLIENT_ID);
		client1.setName("client1");
		client1.setEmail("client1");
		client1.setAddress("client1");
		client1.setParking(true);
		client1.setInit(LocalTime.of(01, 00));
		client1.setFinish(LocalTime.of(01, 01));
		client1.setExpiration(LocalDate.of(3000,12,30));
		client1.setTelephone("123456789");
		client1.setDescription("client1");
		client1.setFood("client1");
		client1.setPreguntaSegura1("client1");
		client1.setPreguntaSegura2("client1");
		client1.setUsuar(user1);
		BDDMockito.given(this.clientService.getCurrentClient()).willReturn(client1);
		BDDMockito.given(this.clientService.findById(TEST_CLIENT_ID)).willReturn(client1);
		
		Usuario usuario = new Usuario();
		usuario.getFavoritos().add(client1);
		BDDMockito.given(this.usuarioService.getCurrentUsuario()).willReturn(usuario);
		BDDMockito.given(this.clientService.mediaValoraciones(client1)).willReturn(1);
		
		BDDMockito.given(this.foodOfferService.findFoodOfferActOclByUserId(1)).willReturn(new ArrayList<FoodOffer>());
		BDDMockito.given(this.nuOfferService.findNuOfferActOclByUserId(1)).willReturn(new ArrayList<NuOffer>());
		BDDMockito.given(this.speedOfferService.findSpeedOfferActOclByUserId(1)).willReturn(new ArrayList<SpeedOffer>());
		BDDMockito.given(this.timeOfferService.findTimeOfferActOclByUserId(1)).willReturn(new ArrayList<TimeOffer>());	
				
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testShow() throws Exception {
		mockMvc.perform(get("/clients/show"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("client"))
				.andExpect(view().name("clients/clientShow"));
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/clients/edit"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("client"))
				.andExpect(view().name("clients/createOrUpdateClientForm"));
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		
	    mockMvc.perform(post("/clients/edit")	
	    		.with(csrf())
					.param("init", "11:30")
					.param("finish", "23:30")
					.param("expiration", "3000-12-30")
					.param("name", "Restaurante Pepe")
					.param("email", "pepe@hotmail.es")
					.param("address", "Pirineos 10")
					.param("parking", "true")
					.param("telephone", "654999999")
					.param("description", "Comida al mejor precio")
					.param("food", "Americana")
					.param("municipio", "Dos_Hermanas")
					.param("image", "")
					.param("preguntaSegura1", "test")
					.param("preguntaSegura2", "test")
					.param("municipio", "Dos_Hermanas"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/clients/show"));
	}
	

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {

	    mockMvc.perform(post("/clients/edit")	
	    			.with(csrf())
					.param("init", "24:30")
					.param("finish", "a:30")
					.param("name", "")
					.param("expiration", "")
					.param("email", "")
					.param("address", "")
					.param("parking", "")
					.param("telephone", "654999")
					.param("description", "")
					.param("food", "")
					.param("municipio", "Dos Hermanas")
					.param("image", "")
					.param("preguntaSegura1", "")
					.param("preguntaSegura2", "")
					.param("municipio", "Dos Hermanas"))
				.andExpect(model().attributeHasErrors("client"))
				.andExpect(model().attributeHasFieldErrors("client", "init"))
				.andExpect(model().attributeHasFieldErrors("client", "finish"))
				.andExpect(model().attributeHasFieldErrors("client", "expiration"))
				.andExpect(model().attributeHasFieldErrors("client", "name"))
				.andExpect(model().attributeHasFieldErrors("client", "email"))
				.andExpect(model().attributeHasFieldErrors("client", "address"))
				.andExpect(model().attributeHasFieldErrors("client", "parking"))
				.andExpect(model().attributeHasFieldErrors("client", "telephone"))
				.andExpect(model().attributeHasFieldErrors("client", "description"))
				.andExpect(model().attributeHasFieldErrors("client", "food"))
				.andExpect(model().attributeHasFieldErrors("client", "preguntaSegura1"))
				.andExpect(model().attributeHasFieldErrors("client", "preguntaSegura2"))
				.andExpect(model().attributeHasFieldErrors("client", "municipio"))
				
				.andExpect(view().name("clients/createOrUpdateClientForm"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testInitDisableForm() throws Exception {
		mockMvc.perform(get("/clients/disable"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("client"))
				.andExpect(view().name("/clients/clientDisable"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testProcessDisableFormSuccess() throws Exception {
		mockMvc.perform(post("/clients/disable")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testInitDeleteForm() throws Exception {
		mockMvc.perform(get("/clients/delete"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("client"))
				.andExpect(view().name("/clients/clientDelete"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testProcessDeleteFormSuccess() throws Exception {
		mockMvc.perform(post("/clients/delete")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
		
		Client cliente = clientService.findById(TEST_CLIENT_ID);
		Assertions.assertTrue(cliente.getAddress().equals("Eliminado"));
		Assertions.assertTrue(cliente.getParking().equals(false));
		Assertions.assertTrue(cliente.getDescription().equals("Eliminado"));
		Assertions.assertTrue(cliente.getEmail().equals("eliminado@gmail.com"));
		Assertions.assertTrue(cliente.getExpiration().equals(LocalDate.now()));
		Assertions.assertTrue(cliente.getFinish().equals(LocalTime.of(00, 00)));
		Assertions.assertTrue(cliente.getInit().equals(LocalTime.of(00, 00)));
		Assertions.assertTrue(cliente.getFood().equals("Eliminado"));
		Assertions.assertTrue(cliente.getPreguntaSegura1().equals("Eliminado"));
		Assertions.assertTrue(cliente.getPreguntaSegura2().equals("Eliminado"));
		Assertions.assertTrue(cliente.getTelephone().equals("000000000"));	
		Assertions.assertTrue(cliente.getMunicipio().equals(Municipio.Sevilla));
		Assertions.assertTrue(cliente.getUsuar()==null);
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testProcessUpdatePassClientSuccess() throws Exception {
		mockMvc.perform(get("/clients/edit/password")
				.with(csrf()))
				.andExpect(model().attributeExists("client"))
				.andExpect(status().isOk())
				.andExpect(view().name("clients/password"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testUpdatePassClientSuccess() throws Exception {
		mockMvc.perform(post("/clients/edit/password")
				.with(csrf())
				.param("init", "11:30")
				.param("finish", "23:30")
				.param("expiration", "3000-12-30")
				.param("name", "Restaurante Pepe")
				.param("email", "pepe@hotmail.es")
				.param("address", "Pirineos 10")
				.param("parking", "true")
				.param("telephone", "654999999")
				.param("description", "Comida al mejor precio")
				.param("food", "Americana")
				.param("preguntaSegura1", "test")
				.param("preguntaSegura2", "test")
				.param("municipio", "Dos_Hermanas")
				.param("usuar.password", "testSuccess"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/clients/show"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testUpdatePassClientError() throws Exception {
		mockMvc.perform(post("/clients/edit/password")
				.with(csrf())
				.param("init", "11:30")
				.param("finish", "23:30")
				.param("expiration", "3000-12-30")
				.param("name", "Restaurante Pepe")
				.param("email", "pepe@hotmail.es")
				.param("address", "Pirineos 10")
				.param("parking", "true")
				.param("telephone", "654999999")
				.param("description", "Comida al mejor precio")
				.param("food", "Americana")
				.param("preguntaSegura1", "test")
				.param("preguntaSegura2", "test")
				.param("municipio", "Dos_Hermanas")
				.param("usuar.password", ""))
				.andExpect(model().attributeHasFieldErrors("client","usuar.password"))
				.andExpect(status().isOk())
				.andExpect(view().name("clients/password"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testShowRestaurant() throws Exception {
		mockMvc.perform(get("/restaurant/"+TEST_CLIENT_ID)
				.with(csrf()))
				.andExpect(model().attributeExists("favoritos"))
				.andExpect(model().attribute("favoritos",1))	
				.andExpect(model().attributeExists("client"))
				.andExpect(model().attributeExists("reviews"))
				.andExpect(status().isOk())
				.andExpect(view().name("clients/restaurantShow"));
	}
}

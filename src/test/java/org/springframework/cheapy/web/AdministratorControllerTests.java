package org.springframework.cheapy.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cheapy.configuration.SecurityConfiguration;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.NuOffer;
import org.springframework.cheapy.model.SpeedOffer;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.model.TimeOffer;
import org.springframework.cheapy.model.Usuario;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.FoodOfferService;
import org.springframework.cheapy.service.NuOfferService;
import org.springframework.cheapy.service.SpeedOfferService;
import org.springframework.cheapy.service.TimeOfferService;
import org.springframework.cheapy.service.UsuarioService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(value = AdministratorController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
class AdministratorControllerTest {

	private static final String TEST_USUARIO_USERNAME = "user1";
	private static final String TEST_CLIENT_USERNAME = "user2";
  	private static final int TEST_FOODOFFER_ID = 1;
  	private static final int TEST_NUOFFER_ID = 1;
  	private static final int TEST_SPEEDOFFER_ID = 1;
  	private static final int TEST_TIMEOFFER_ID = 1;

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ClientService clientService;

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
		user1.setUsername("use1");
		user1.setPassword("user1");
		Usuario usuario = new Usuario();
		usuario.setNombre("usuario");
		usuario.setApellidos("usuario");
		usuario.setDireccion("usuario");
		usuario.setMunicipio(Municipio.Sevilla);
		usuario.setEmail("usuario@gmail.com");
		usuario.setUsuar(user1);
		BDDMockito.given(this.usuarioService.findByUsername("user1")).willReturn(usuario);
    
    User user2 = new User();
		user2.setUsername("user1");
		user2.setPassword("user1");
		Client client1 = new Client();
		client1.setId(1);
		client1.setName("client1");
		client1.setEmail("client1");
		client1.setAddress("client1");
		client1.setInit(LocalTime.of(01, 00));
		client1.setFinish(LocalTime.of(01, 01));
		client1.setTelephone("123456789");
		client1.setDescription("client1");
		client1.setFood("client1");
		client1.setUsuar(user2);
		
		BDDMockito.given(this.clientService.getCurrentClient()).willReturn(client1);
		BDDMockito.given(this.clientService.findByUsername(TEST_CLIENT_USERNAME)).willReturn(client1);
		
		BDDMockito.given(this.foodOfferService.findFoodOfferActOclByUserId(1)).willReturn(new ArrayList<FoodOffer>());
		BDDMockito.given(this.nuOfferService.findNuOfferActOclByUserId(1)).willReturn(new ArrayList<NuOffer>());
		BDDMockito.given(this.speedOfferService.findSpeedOfferActOclByUserId(1)).willReturn(new ArrayList<SpeedOffer>());
		BDDMockito.given(this.timeOfferService.findTimeOfferActOclByUserId(1)).willReturn(new ArrayList<TimeOffer>());
		
		FoodOffer fo1test = new FoodOffer();
		fo1test.setId(TEST_FOODOFFER_ID);
		fo1test.setStart(LocalDateTime.of(2021, 12, 23, 12, 30));
		fo1test.setEnd(LocalDateTime.of(2022, 12, 23, 12, 30));
		fo1test.setFood("fo1test");
		fo1test.setDiscount(1);
		fo1test.setPrice(10.0);
		fo1test.setStatus(StatusOffer.hidden);
		fo1test.setCode("");
		fo1test.setClient(client1);
		BDDMockito.given(this.foodOfferService.findFoodOfferById(TEST_FOODOFFER_ID)).willReturn(fo1test);
		
		NuOffer nu1test = new NuOffer();
		nu1test.setId(TEST_NUOFFER_ID);
		nu1test.setStart(LocalDateTime.of(2021, 12, 23, 12, 30));
		nu1test.setEnd(LocalDateTime.of(2022, 12, 23, 12, 30));
		nu1test.setGold(15);
		nu1test.setDiscountGold(15);
		nu1test.setSilver(10);
		nu1test.setDiscountSilver(10);
		nu1test.setBronze(5);
		nu1test.setDiscountBronze(5);
		nu1test.setClient(client1);
		nu1test.setStatus(StatusOffer.hidden);
		nu1test.setCode("");
		BDDMockito.given(this.nuOfferService.findNuOfferById(TEST_NUOFFER_ID)).willReturn(nu1test);
		
		SpeedOffer sp1test = new SpeedOffer();
		sp1test.setId(TEST_SPEEDOFFER_ID);
		sp1test.setStart(LocalDateTime.of(2021, 12, 23, 12, 30));
		sp1test.setEnd(LocalDateTime.of(2022, 12, 23, 12, 30));
		sp1test.setGold(LocalTime.of(00,05,30 ));
		sp1test.setDiscountGold(15);
		sp1test.setSilver(LocalTime.of(00,10,30 ));
		sp1test.setDiscountSilver(10);
		sp1test.setBronze(LocalTime.of(00,15,30 ));
		sp1test.setDiscountBronze(5);
		sp1test.setClient(client1);
		sp1test.setStatus(StatusOffer.hidden);
		sp1test.setCode("");
		BDDMockito.given(this.speedOfferService.findSpeedOfferById(TEST_SPEEDOFFER_ID)).willReturn(sp1test);
		
		TimeOffer time1test = new TimeOffer();
		time1test.setId(TEST_TIMEOFFER_ID);
		time1test.setStart(LocalDateTime.of(2021, 12, 23, 12, 30));
		time1test.setEnd(LocalDateTime.of(2022, 12, 23, 12, 30));
		time1test.setInit(LocalTime.of(12, 00));
		time1test.setFinish(LocalTime.of(13, 00));
		time1test.setDiscount(10);
		time1test.setClient(client1);
		time1test.setStatus(StatusOffer.hidden);
		time1test.setCode("");
		BDDMockito.given(this.timeOfferService.findTimeOfferById(TEST_TIMEOFFER_ID)).willReturn(time1test);
	}
	
	@WithMockUser(value = "spring", authorities = "administrator")
	@Test
	void testListClients() throws Exception {
		mockMvc.perform(get("/administrators/clients/page/0"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("clientLs"))
				.andExpect(view().name("clients/clientsList"));
	}
	
	@WithMockUser(value = "spring", authorities = "administrator")
	@Test
	void testShowClient() throws Exception {
		mockMvc.perform(get("/administrators/clients/"+TEST_CLIENT_USERNAME))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("client"))
				.andExpect(view().name("clients/clientShow"));
	}

	
	@WithMockUser(value = "spring", authorities = "administrator")
	@Test
  void testListUsuarios() throws Exception {
		mockMvc.perform(get("/administrators/usuarios/page/0"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("usuarioLs"))
				.andExpect(view().name("usuarios/usuariosList"));
	}

	@WithMockUser(value = "spring", authorities = "administrator")
	@Test
	void testShowUsuario() throws Exception {
		mockMvc.perform(get("/administrators/usuarios/{username}", TEST_USUARIO_USERNAME))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("usuario"))
				.andExpect(view().name("usuarios/usuariosShow"));

	}
  
  @WithMockUser(value = "spring", authorities = "administrator")
	@Test
	void testInitDisableClientForm() throws Exception {
		mockMvc.perform(get("/administrators/clients/"+TEST_CLIENT_USERNAME+"/disable"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("client"))
				.andExpect(view().name("clients/clientDisable"));
    }
	
	@WithMockUser(value = "spring", authorities = "administrator")
	@Test
	void testProcessDisableClientFormSuccess() throws Exception {
		mockMvc.perform(post("/administrators/clients/"+TEST_CLIENT_USERNAME+"/disable")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/administrators/clients/page/0"));
	}

	@WithMockUser(value = "spring", authorities = "administrator")
	@Test
  void testInitDisableUsuario() throws Exception {
		mockMvc.perform(get("/administrators/usuarios/{username}/disable", TEST_USUARIO_USERNAME))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("usuario"))
			.andExpect(view().name("usuarios/usuariosDisable"));

	}
  
  @WithMockUser(value = "spring", authorities = "administrator")
	@Test
  void testProcessDisableUsuarioSuccess() throws Exception {
		mockMvc.perform(post("/administrators/usuarios/{username}/disable", TEST_USUARIO_USERNAME)
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/administrators/usuarios/page/0"));
	}
  
  @WithMockUser(value = "spring", authorities = "administrator")
	@Test
	void testInitActivateClientForm() throws Exception {
		mockMvc.perform(get("/administrators/clients/"+TEST_CLIENT_USERNAME+"/activate"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("client"))
				.andExpect(view().name("clients/clientActivate"));
    }
	
	@WithMockUser(value = "spring", authorities = "administrator")
	@Test
	void testProcessActivateFormSuccess() throws Exception {
		mockMvc.perform(post("/administrators/clients/"+TEST_CLIENT_USERNAME+"/activate")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/administrators/clients/page/0"));
	}
	
	@WithMockUser(value = "spring", authorities = "administrator")
	@Test
	void testProcessOffersRecordForm() throws Exception {
		mockMvc.perform(get("/administrators/offersRecord0"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("datos"))
		.andExpect(view().name("offers/offersRecordList"));
	}
	
	@WithMockUser(value = "spring", authorities = "administrator")
	@Test
	void testProcessShowNuForm() throws Exception {
		mockMvc.perform(get("/administrators/offers/nu/"+TEST_NUOFFER_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("nuOffer"))
		.andExpect(view().name("offers/nu/nuOffersShow"));
	}
	
	@WithMockUser(value = "spring", authorities = "administrator")
	@Test
	void testProcessShowFoodForm() throws Exception {
		mockMvc.perform(get("/administrators/offers/food/"+TEST_FOODOFFER_ID)
		.with(csrf()))
		.andExpect(model().attributeExists("foodOffer"))
		.andExpect(status().isOk())
		.andExpect(view().name("offers/food/foodOffersShow"));
	}
	
	@WithMockUser(value = "spring", authorities = "administrator")
	@Test
	void testProcessShowSpeedForm() throws Exception {
		mockMvc.perform(get("/administrators/offers/speed/"+TEST_SPEEDOFFER_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("speedOffer"))
		.andExpect(view().name("offers/speed/speedOffersShow"));
	}
	
	@WithMockUser(value = "spring", authorities = "administrator")
	@Test
	void testProcessShowTimeForm() throws Exception {
		mockMvc.perform(get("/administrators/offers/time/"+TEST_TIMEOFFER_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("timeOffer"))
		.andExpect(view().name("offers/time/timeOffersShow"));
	}
}

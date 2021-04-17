package org.springframework.cheapy.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cheapy.configuration.SecurityConfiguration;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.Code;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.model.Municipio;
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
		Code code1 = new Code();
		code1.setActivo(true);
		code1.setCode("codeTest1");
		user2.setUsername("user1");
		user2.setPassword("user1");
		Client client1 = new Client();;
		client1.setId(1);
		client1.setName("client1");
		client1.setEmail("client1");
		client1.setAddress("client1");
		client1.setInit(LocalTime.of(01, 00));
		client1.setFinish(LocalTime.of(01, 01));
		client1.setTelephone("123456789");
		client1.setDescription("client1");
		client1.setCode(code1);
		client1.setFood("client1");
		client1.setUsuar(user2);
		BDDMockito.given(this.clientService.getCurrentClient()).willReturn(client1);
		
		BDDMockito.given(this.clientService.findByUsername(TEST_CLIENT_USERNAME)).willReturn(client1);
		
		
	

		
	}
	
	@WithMockUser(value = "spring", authorities = "administrator")
	@Test
	void testListClients() throws Exception {
		mockMvc.perform(get("/administrators/clients0"))
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
		mockMvc.perform(get("/administrators/usuarios0"))
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
				.andExpect(view().name("redirect:/administrators/clients"));
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
				.andExpect(view().name("redirect:/administrators/usuarios"));
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
				.andExpect(view().name("redirect:/administrators/clients"));
	}
	

}

package org.springframework.cheapy.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cheapy.configuration.SecurityConfiguration;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.model.Usuario;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.UsuarioService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;




@WebMvcTest(value = UsuarioController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
class UsuarioControllerTest {


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UsuarioService usuarioService;
	
	@MockBean
	private ClientService clientService;

	@BeforeEach
	void setup() {
		User user = new User();
		user.setUsername("user");
		user.setPassword("user");
		Usuario usuario = new Usuario();
		usuario.setId(0);
		usuario.setNombre("usuario");
		usuario.setApellidos("usuario");
		usuario.setEmail("usuario@gmail.com");
		usuario.setUsuar(user);
		
		Client client1 = new Client();
		client1.setId(1);
		User user1 = new User();
		user.setUsername("user1");
		user.setPassword("user1");
		client1.setUsuar(user1);
		usuario.getFavoritos().add(client1);

		
		BDDMockito.given(this.usuarioService.getCurrentUsuario()).willReturn(usuario);
		
		Client client = new Client();
		User user2 = new User();
		user.setUsername("user2");
		user.setPassword("user2");
		client.setUsuar(user2);
		client.setId(0);
		BDDMockito.given(this.clientService.findById(0)).willReturn(client);
		BDDMockito.given(this.clientService.findById(1)).willReturn(client1);
	}

	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testShow() throws Exception {
		mockMvc.perform(get("/usuarios/show"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("usuario"))
				.andExpect(view().name("usuarios/usuariosShow"));
	}

	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/usuarios/edit"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("usuario"))
				.andExpect(view().name("usuarios/createOrUpdateUsuarioForm"));
	}

	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/usuarios/edit")
					.with(csrf())
					.param("usuar.password", "Contrasenya123")
					.param("nombre", "nombre")
					.param("apellidos", "apellidos")
					.param("email", "email@gmail.com"))
				.andExpect(status().is3xxRedirection());
	}
	

	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(post("/usuarios/edit")
				.with(csrf())
				.param("usuar.password", "")
				.param("nombre", "")
				.param("apellidos", "")
				.param("email", "email"))
				.andExpect(model().attributeHasErrors("usuario"))
				.andExpect(model().attributeHasFieldErrors("usuario", "nombre"))
				.andExpect(model().attributeHasFieldErrors("usuario", "apellidos"))
				.andExpect(model().attributeHasFieldErrors("usuario", "email"))
				.andExpect(view().name("usuarios/createOrUpdateUsuarioForm"));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testInitDisable() throws Exception {
		mockMvc.perform(get("/usuarios/disable"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("usuario"))
			.andExpect(view().name("usuarios/usuariosDisable"));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testProcessDisableSuccess() throws Exception {
		mockMvc.perform(post("/usuarios/disable")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testInitDelete() throws Exception {
		mockMvc.perform(get("/usuarios/delete"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("usuario"))
			.andExpect(view().name("usuarios/usuariosDelete"));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testProcessDeleteSuccess() throws Exception {
		mockMvc.perform(post("/usuarios/delete")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
		Assertions.assertTrue(usuarioService.findByUsername("user") == null);
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testProcessUpdatePassUsuarioSuccess() throws Exception {
		mockMvc.perform(get("/usuarios/edit/password")
				.with(csrf()))
				.andExpect(model().attributeExists("usuario"))
				.andExpect(status().isOk())
				.andExpect(view().name("usuarios/password"));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testUpdatePassUsuarioSuccess() throws Exception {
		mockMvc.perform(post("/usuarios/edit/password")
				.with(csrf())
				.param("nombre", "nombre")
				.param("apellidos", "apellidos")
				.param("email", "email@gmail.com")
				.param("usuar.password", "testSuccess"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/usuarios/show"));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testUpdatePassUsuarioError() throws Exception {
		mockMvc.perform(post("/usuarios/edit/password")
				.with(csrf())
				.param("nombre", "nombre")
				.param("apellidos", "apellidos")
				.param("email", "email@gmail.com")
				.param("usuar.password", ""))
				.andExpect(model().attributeHasFieldErrors("usuario","usuar.password"))
				.andExpect(status().isOk())
				.andExpect(view().name("usuarios/password"));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testListFavoriteSuccess() throws Exception {
		mockMvc.perform(get("/usuarios/favoritos/0")
				.with(csrf()))
				.andExpect(model().attributeExists("municipios"))
				.andExpect(model().attributeExists("clientLs"))
				.andExpect(model().attributeExists("nextPage"))
				.andExpect(status().isOk())
				.andExpect(view().name("usuarios/favoritos"));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testAddFavoriteSuccess() throws Exception {
		mockMvc.perform(get("/usuarios/favoritos/0/add")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/restaurant/0"));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testAddFavoriteError() throws Exception {
		mockMvc.perform(get("/usuarios/favoritos/1/add")
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("error"));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testRemoveFavoriteSuccess() throws Exception {
		mockMvc.perform(get("/usuarios/favoritos/1/remove")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/restaurant/1"));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testRemoveFavoriteError() throws Exception {
		mockMvc.perform(get("/usuarios/favoritos/0/remove")
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("error"));
	}
}

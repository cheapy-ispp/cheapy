
package org.springframework.cheapy.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	private String clientId;
    private String clientSecret;
	
    public SecurityConfiguration(@Value("${spring.security.oauth2.client.registration.google.clientId}") String clientId, @Value("${spring.security.oauth2.client.registration.google.clientSecret}") String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/resources/**", "/webjars/**", "/h2-console/**").permitAll()
		.antMatchers(HttpMethod.GET, "/", "/oups").permitAll()
		.antMatchers("/users/new").permitAll()
		
		.antMatchers("/clients/new").permitAll()
		.antMatchers("/clients/show").hasAnyAuthority("client","notsubscribed")
		.antMatchers("/clients/delete").hasAnyAuthority("client","notsubscribed")
		.antMatchers("/clients/edit").hasAnyAuthority("client","notsubscribed")
		.antMatchers("/clients/edit/**").hasAnyAuthority("client","notsubscribed")
		.antMatchers("/clients/disable").hasAnyAuthority("client","notsubscribed")

		.antMatchers("/sign-up-client/new/**").anonymous()
		.antMatchers("/sign-up-user/new/**").anonymous()
		.antMatchers("/login/**").anonymous()
		.antMatchers("/googleForm").authenticated()
		.antMatchers("/logout").authenticated()

		.antMatchers("/usuarios/new").permitAll()
		.antMatchers("/usuarios/**").hasAnyAuthority("usuario")
		.antMatchers("/administrators/**").hasAnyAuthority("admin")


		.antMatchers("/offers/**/edit").hasAnyAuthority("client")
		.antMatchers("/offers/**/new").hasAnyAuthority("client")
		.antMatchers("/offers/**/activate").hasAnyAuthority("client")
		.antMatchers("/offers/**/disable").hasAnyAuthority("client")

		.antMatchers("/myOffers").hasAnyAuthority("client")
		
		.antMatchers("/offers").permitAll()
		.antMatchers("/offersCreate").hasAuthority("client")


		.antMatchers("/reviews/new").hasAnyAuthority("usuario","client","notsubscribed")
		.antMatchers("/reviewsList/**").authenticated()
		.antMatchers("/reviewsClient/new/**").hasAnyAuthority("usuario")
		.antMatchers("/pay/**").hasAnyAuthority("notsubscribed","client")
		

		.and().oauth2Login().loginPage("/login").userInfoEndpoint().userAuthoritiesMapper(this.userAuthoritiesMapper())
		.and().defaultSuccessUrl("/googleForm",true)
		.and().formLogin().loginPage("/login")
			.failureUrl("/login?error")
		    .and().logout().logoutSuccessUrl("/");
		
		
			

		// Configuración para que funcione la consola de administración
		// de la BD H2 (deshabilitar las cabeceras de protección contra
		// ataques de tipo csrf y habilitar los framesets si su contenido
		// se sirve desde esta misma página.
		//http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().sameOrigin();
		}
		
		private GrantedAuthoritiesMapper userAuthoritiesMapper() {
	        return (authorities) -> {
	        	List<GrantedAuthority> mappedAuthorities= new ArrayList<>();
	            mappedAuthorities.add(new SimpleGrantedAuthority("usuario"));
	            return mappedAuthorities;
	        };
	}
	
	

	@Override

	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.dataSource)
			//[login de admin,owner y vet] .usersByUsernameQuery("select username,password,enabled " + "from users " + "where username = ?")
			.usersByUsernameQuery("select username, password, enabled from users where username=?").authoritiesByUsernameQuery("select username, authority " + "from authorities " + "where username = ?")
			.passwordEncoder(this.passwordEncoder());

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
//		PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();
		return new MessageDigestPasswordEncoder("MD5");
	}
	
	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		List<ClientRegistration> registrations = new ArrayList<>();
		registrations.add(googleClientRegistration());
		return new InMemoryClientRegistrationRepository(registrations);
	}
	private ClientRegistration googleClientRegistration() {
		//String prueba = this.env.getProperty("spring.security.oauth2.client.registration.google.client-id");
//		String prueba2 = this.clientSecreto;
		return ClientRegistration.withRegistrationId("google")
                .clientId(this.clientId)
				.clientSecret(this.clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
				.scope("openid", "profile", "email", "address", "phone")
				.authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
				.tokenUri("https://www.googleapis.com/oauth2/v4/token")
				.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
				.userNameAttributeName("email")
                                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
				.clientName("Google").build();
	}

}

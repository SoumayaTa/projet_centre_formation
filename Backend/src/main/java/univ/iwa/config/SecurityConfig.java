package univ.iwa.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import univ.iwa.filters.JwtAuthFilter;
import univ.iwa.service.UserInfoService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Autowired JwtAuthFilter authFilter;
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserInfoService();
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.cors(cors->cors.configurationSource(request -> new CorsConfiguration(corsFilter())))
				.authorizeHttpRequests((auth)->auth
			.requestMatchers("/auth/welcome", "/auth/addNewUser", "/auth/generateToken", "/form/getByDate/**", "/form/getByVille/**", "/form/getByCategorie/**","/form/getall","/form/addFormation/image","/individus/**").permitAll()
			.requestMatchers("/auth/assistant/**").authenticated()
			.requestMatchers("/auth/admin/**").authenticated()
								.requestMatchers("/auth/updateUser/**").authenticated()
								.requestMatchers("/auth/format/**").authenticated()
								.requestMatchers("/form/formation/**").authenticated()
								.requestMatchers("/entreprise/**").authenticated()
								.requestMatchers("/calendrier/**").authenticated()
								.requestMatchers("/auth/allFormateur").authenticated()
								.requestMatchers("/auth/deleteFormateur/**").authenticated()

				).csrf(csrf->csrf.disable())
			.authenticationProvider(authenticationProvider())
			.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	// Password Encoding
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	public CorsConfiguration corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:4200");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		return config;
	}

}

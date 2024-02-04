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
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import univ.iwa.filters.JwtAuthFilter;
import univ.iwa.service.UserInfoService;



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
			.requestMatchers("/auth/welcome","/mail2/**","/crypt/**","/mail/**","/calendrieraddnewCalendar/**","/images/**","/externe/**","/externe/deleteinscription/**","/externe/deleteAndCreateUserInfo/**", "/auth/addNewUser","/auth/getFormateurById/**","/auth/generateToken","/form/categories","/form/villes","/form/groupes/**","/form/individus/**","/form/sendemail/**", "/form/getByFilters","/form/getall","/form/addFormation/image","/individus/**",
					"/evaluation/add/**","/evaluation/averageRating/**","evaluation/hasSubmittedFeedback").permitAll()
				.requestMatchers("/auth/addNewUser", "/auth/generateToken", "/form/getByDate/**", "/form/getByVille/**", "/form/getByCategorie/**","/form/getall").permitAll()

			.requestMatchers("/auth/welcome","/evaluation/**","/mail2/**","/mail/**","/images/**","/externe/**","/externe/deleteinscription/**","/externe/deleteAndCreateUserInfo/**", "/auth/addNewUser","/auth/getFormateurById/**","/auth/generateToken","/form/categories","/form/villes", "/form/getByFilters","/form/getall","/form/groupes/**","/form/sendemail/**","/form/individus/**","/form/addFormation/image","/individus/**","/evaluation/add/**").permitAll()
			.requestMatchers("/auth/welcome","/mail2/**","/mail/**","/calendrier/addnewCalendar/**","/images/**","/externe/**","/group/getAllGroupes","/externe/deleteinscription/**","/externe/deleteAndCreateUserInfo/**", "/auth/addNewUser","/auth/getFormateurById/**","/auth/generateToken","/form/categories","/form/villes", "/form/getByFilters","/form/getall","/form/addFormation/image","/individus/**","/evaluation/add/**").permitAll()
			.requestMatchers("/auth/assistant/**").authenticated()
								.requestMatchers("/individus/getAllIndividus").permitAll()
								.requestMatchers("/images/**").permitAll()
								.requestMatchers("auth/allFormateurByNom").permitAll()
								.requestMatchers("/auth/welcome", "/auth/addNewUser", "/auth/generateToken", "/form/getByDate/**", "/form/getByVille/**", "/form/getByCategorie/**","/form/getall").permitAll()
								.requestMatchers("/auth/assistant/**").authenticated()
								.requestMatchers("/auth/admin/**").authenticated()
								.requestMatchers("/auth/updateUser/**").authenticated()
								.requestMatchers("/auth/format/**").authenticated()
								.requestMatchers("/form/formation/**").authenticated()
								.requestMatchers("/form/formation/**").authenticated()
								.requestMatchers("/entreprise/**").authenticated()
								.requestMatchers("/entreprise/removeEntreprise/**").authenticated()
								.requestMatchers("/entreprise/updateEntreprise/**").authenticated()
								.requestMatchers("/entreprise/geEntrepriseById/**").authenticated()
								.requestMatchers("/calendrier/**").authenticated()
								.requestMatchers("/groupe/**").authenticated()
								.requestMatchers("/auth/allFormateur").authenticated()
								.requestMatchers("/auth/deleteFormateur/**").authenticated()
								.requestMatchers("/auth/addNewAssisstant/**").authenticated()
								.requestMatchers("/calendrier/addnewCalendar").authenticated()

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
		// Ajoutez ce bloc pour permettre CORS pour /images/**
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/images/**", config);
		return config;
	}
	

}

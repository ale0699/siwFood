package it.uniroma3.siwFood.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.authoritiesByUsernameQuery("SELECT username, ruolo from credentials WHERE username=?")
		.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	}

	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeHttpRequests()
		.requestMatchers(HttpMethod.GET, "/", "/login/**", "/recipes", "/recipeDetails/**", "/searchRecipes/**", "/searchRecipesByIngredient/**", "/recipesWithIngredient/**", "/css/**", "/images/**").permitAll()
		.requestMatchers(HttpMethod.POST, "/register").permitAll()
		.requestMatchers(HttpMethod.GET, "/admin/**").hasAnyRole("ADMIN")
		.requestMatchers(HttpMethod.GET, "/cooks/**").hasAnyRole("DEFAULT")
		.anyRequest().authenticated()
		.and().formLogin()
		.loginPage("/login").failureUrl("/login/error").defaultSuccessUrl("/")
		.and().logout()
		.logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).clearAuthentication(true).permitAll();
		
		return http.build();
	}
	
	@Bean
	protected PasswordEncoder passwordEncoder(){
		
		return new BCryptPasswordEncoder();
	}

}

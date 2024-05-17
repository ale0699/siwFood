package it.uniroma3.siwFood.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {
	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeHttpRequests()
		.requestMatchers(HttpMethod.GET, "/admin").hasRole("ADMIN")
		.anyRequest().permitAll()
		.and().formLogin()
		.loginPage("/login").failureUrl("/login/error").defaultSuccessUrl("/")
		.and().logout()
		.logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).clearAuthentication(true).permitAll();
		
		return http.build();
	}
}

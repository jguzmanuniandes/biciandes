package com.BiciAndes.www;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import com.BiciAndes.www.models.entity.Factura;
import com.BiciAndes.www.models.entity.Libro;

@Configuration
public class MvcConfig {

	public MvcConfig() {
		// TODO Auto-generated constructor stub
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
	@Bean
	@Scope("singleton")
	public Factura cartSingleton() {
	    return new Factura();
	}


	@Bean
	public Libro cartLibro() {
	    return new Libro();
	}
}

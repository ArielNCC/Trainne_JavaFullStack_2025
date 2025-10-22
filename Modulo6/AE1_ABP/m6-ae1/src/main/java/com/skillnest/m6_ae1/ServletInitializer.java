package com.skillnest.m6_ae1;

import com.skillnest.m6_ae1.servlet.UsuarioServlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(M6Ae1Application.class);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		ServletRegistration.Dynamic servlet = servletContext.addServlet("usuarioServlet", new UsuarioServlet());
		servlet.addMapping("/usuario/servlet");
		servlet.setLoadOnStartup(1);
	}

}

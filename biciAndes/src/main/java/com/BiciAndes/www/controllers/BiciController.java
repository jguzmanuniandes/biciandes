package com.BiciAndes.www.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.BiciAndes.www.models.entity.Cliente;
import com.BiciAndes.www.models.entity.Libro;
import com.BiciAndes.www.models.entity.Usuario;
import com.BiciAndes.www.models.entity.dao.intefaces.IClienteDao;
import com.BiciAndes.www.models.entity.dao.intefaces.IUsuarioDao;
import com.BiciAndes.www.models.service.ILibroService;

import net.bytebuddy.agent.builder.AgentBuilder.InitializationStrategy.SelfInjection.Split;

@Controller
@SessionAttributes("libro")
public class BiciController {

	@Autowired
	private ILibroService libroService;
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private IClienteDao clienteDao;

	@Autowired
	private Libro nlib;

	@RequestMapping(value = { "/index", "/" })
	public String cargar(Model model, @RequestParam(value = "sidebar", required = false) String id) {

		int flagSideBar = 1;
		if (id == null) flagSideBar = 1;
		if (id != null) {
			if (id.equals("1")) {
				flagSideBar = 0;
			}
			if (id.equals("0")) {
				flagSideBar = 1;
			}
		}else {flagSideBar=0;};
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
//		System.out.println(auth.getName());
		
//		String idUser = auth.getName().split(",")[0];
//		System.out.println("idUser: "+idUser);
		
		try {
			Usuario usuario = usuarioDao.findByUsername(auth.getName());
//	    	System.out.println(usuario.getUsername());
			Cliente cliente = clienteDao.findByIdUser(usuario.getId());	
			model.addAttribute("idUser",cliente.getId());
		} catch (NullPointerException e) {
			return "login";
		}
		
		model.addAttribute("titulo", "BiciAndes");
		model.addAttribute("sidebar", flagSideBar);
		return "pages/index";
	};

	@RequestMapping(value = "/search")
	public String buscar(Model model, Libro libro) {

		this.nlib.setCantidad(1);
		List<Libro> libros = libroService.findByNombre(libro.getNombre());

		model.addAttribute("libros", libros);
		model.addAttribute("nlib", this.nlib);
		model.addAttribute("titulo", "BookStore Harry Potter");
		return "pages/index";
	}
	
	//ant
	@RequestMapping(value = { "/HarryPotter", "/h"})
	public String cargarHarry(Model model, @RequestParam(value = "sidebar", required = false) String id) {

		int flagSideBar = 0;
		if (id == null) flagSideBar = 1;
		if (id != null) {
			if (id.equals("1")) {
				flagSideBar = 0;
			}
			if (id.equals("0")) {
				flagSideBar = 1;
			}
		}else {flagSideBar=0;};
		
		this.nlib.setCantidad(1);
		List<Libro> libros = libroService.findAll();
		model.addAttribute("libros", libros);
		model.addAttribute("nlib", this.nlib);
		model.addAttribute("titulo", "BookStore Harry Potter");
		model.addAttribute("sidebar", flagSideBar);
		return "pages/index2";
	};

}

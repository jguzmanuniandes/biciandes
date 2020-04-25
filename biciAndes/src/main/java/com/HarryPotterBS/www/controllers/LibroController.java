package com.HarryPotterBS.www.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.HarryPotterBS.www.models.entity.Libro;
import com.HarryPotterBS.www.models.service.ILibroService;

@Controller
@SessionAttributes("libro")
public class LibroController {

	@Autowired
	private ILibroService libroService;

	@Autowired
	private Libro nlib;

	@RequestMapping(value = { "/index", "/" })
	public String cargar(Model model, @RequestParam(value = "sidebar", required = false) String id) {

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

}

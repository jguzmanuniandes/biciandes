package com.HarryPotterBS.www.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HarryPotterBS.www.models.entity.Factura;
import com.HarryPotterBS.www.models.entity.ItemFactura;
import com.HarryPotterBS.www.models.entity.Libro;
import com.HarryPotterBS.www.models.service.IClienteService;
import com.HarryPotterBS.www.models.service.ILibroService;

@Controller
@RequestMapping("/cart")
public class CarritoController {

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private ILibroService libroService;

	@Autowired
	private Factura carrito;
	
	public CarritoController() {
	}
	
	@ModelAttribute("cart")
	public Factura getPerson(){
	    return this.carrito;
	}
	
	@RequestMapping(value = { "/", "" })
	public String cargar(Model model) {
		
		model.addAttribute("cart", this.carrito);
		model.addAttribute("titulo", "Mis nuevos Libros de Harry Potter");
		return "pages/cart";
	}
	@PostMapping(value = { "/", "" })
	public String reload(Libro libro, Map<String, Object> model) {
		System.out.println(ResponseEntity.ok(libro.getCantidad()+"id"+libro.getId()+"nom:"+libro.getNombre()));
		System.out.println(ResponseEntity.ok(this.carrito.getItems()));
		
		for (ItemFactura item : this.carrito.getItems()) {
			if (item.getProducto().getId() == libro.getId()) {
				item.setCantidad(libro.getCantidad());
				return "pages/cart";
			}
		}
//		this.carrito = carrito;
		model.put("cart", this.carrito);
		return "pages/cart";
	}

	@PostMapping("/agregar")
	public String addLibro(Libro libro, RedirectAttributes flash) {
		ItemFactura itemCart = new ItemFactura(libro.getCantidad(), libro);

		boolean existe = false;
		for (ItemFactura item : this.carrito.getItems()) {
			if (item.getProducto().getId() == libro.getId()) {
				item.setCantidad(item.getCantidad() + libro.getCantidad());
				existe = true;
			}
		}
		if (!existe)
			this.carrito.addItemFactura(itemCart);

		if (libro.getCantidad() > 1)
			flash.addFlashAttribute("success", "Agragaste " + libro.getCantidad() + " libros a tu carrito!");
		if (libro.getCantidad() == 1)
			flash.addFlashAttribute("success", "El Libro" + libro.getNombre() + "fue agregado a tu carrito!");
		return "redirect:/index";
	}

	@GetMapping("/eliminar/{id}")
	public String deleteLibro(@PathVariable Long id, RedirectAttributes flash, Model model) {
		if (id == -1) {
			this.carrito.delAllItemsFactura();
			String mensaje = "Su compra se ha cancelado";
			model.addAttribute("cart", this.carrito);
			flash.addFlashAttribute("warning", mensaje);
			return "redirect:/cart";
		}
		
		for (ItemFactura item : this.carrito.getItems()) {
			if (item.getProducto().getId() == id) {
				String mensaje = "Se ha Eliminado el Libro " + item.getProducto().getNombre() + " de tu carrito";
				this.carrito.delItemFactura(item);
				model.addAttribute("cart", this.carrito);
				flash.addFlashAttribute("warning", mensaje);
				return "redirect:/cart";
			}
		}
		flash.addFlashAttribute("error", "No se puedo elimnar el Articulo");
		return "redirect:/cart";
	}
	
	@RequestMapping(value = "/compra")
	public String comprar(Model model) {
		
		this.libroService.updateExistencias(this.carrito);	
		clienteService.saveFactura(this.carrito);
		
		this.carrito.delAllItemsFactura();
		return "redirect:/index";
	}
}

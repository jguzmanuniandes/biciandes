package com.uniandes.biciandes.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uniandes.biciandes.model.User;
import com.uniandes.biciandes.repository.UserRepository;
import com.uniandes.biciandes.service.UserService;

@Controller
public class UserController {
	
	private UserService userService;
	
	@Autowired
	private UserRepository usuarioDao;

	public UserController() {
	}

	@GetMapping(value = "/verUser")
	public String ver(Map<String, Object> model, RedirectAttributes flash, Authentication auth) {

		User usuario = usuarioDao.findByEmail(auth.getName());
		
		if (usuario == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/login";
		}
		if(usuario.getUrlPicture() == null) {
			usuario.setUrlPicture("");
		}

		model.put("cliente", usuario);
		model.put("titulo", "Detalle cliente: " + usuario.getName() + " " +usuario.getLastName());
		return "pages/verUser";
	}
	
	@RequestMapping(value = "/editarUser")
	public String editarUsuario(Map<String, Object> model, Authentication auth) {
		
		User usuario = usuarioDao.findByEmail(auth.getName());
		model.put("cliente", usuario);
		model.put("titulo", "Editar datos del Usuario " + usuario.getName()+" "+ usuario.getLastName());
		model.put("btnLabel", "Guardar Cambios");
		return "pages/editarUser";
	}
	
	@RequestMapping(value = "/editarUser", method = RequestMethod.POST)
	public String saveEditUser(@Valid User cliente,BindingResult result, Model model,
			 RedirectAttributes flash, SessionStatus status) {

		usuarioDao.save(cliente);
		return "redirect:/verUser";
	}
}

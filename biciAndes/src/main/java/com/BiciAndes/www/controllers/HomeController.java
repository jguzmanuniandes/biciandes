package com.BiciAndes.www.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.BiciAndes.www.models.entity.Cliente;
import com.BiciAndes.www.models.entity.Role;
import com.BiciAndes.www.models.entity.SuperClient;
import com.BiciAndes.www.models.entity.Usuario;
import com.BiciAndes.www.models.entity.dao.intefaces.IUsuarioDao;
import com.BiciAndes.www.models.service.IClienteService;
import com.BiciAndes.www.models.service.IUploadFileService;

@Controller
public class HomeController {
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
}

package com.BiciAndes.www.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.BiciAndes.www.models.entity.Cliente;
import com.BiciAndes.www.models.entity.Libro;
import com.BiciAndes.www.models.entity.SuperClient;
import com.BiciAndes.www.models.entity.Usuario;
import com.BiciAndes.www.models.entity.dao.intefaces.IClienteDao;
import com.BiciAndes.www.models.entity.dao.intefaces.IUsuarioDao;
import com.BiciAndes.www.models.service.IClienteService;
import com.BiciAndes.www.models.service.ILibroService;
import com.BiciAndes.www.models.service.IUploadFileService;
import com.BiciAndes.www.util.paginator.PageRender;

@Controller
@SessionAttributes("libro")
public class PublicacionController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IPublicacionService publicacionService;

	@Autowired
	private IUploadFileService uploadFileService;
	
//	@Value("${conf.version}")
//	private String version;
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Secured({"ROLE_USER"})
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

		
	@PreAuthorize("hasRole('ROLE_USER"
			+ "')")
	@RequestMapping(value = "/publicar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null) {
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: ".concat(auth.getName()));
		}
		
		if(hasRole("ROLE_ADMIN")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "");
		
		if(securityContext.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}

		if(request.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}	
		
		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Publicacion> publicaciones = publicacionService.findAll(pageRequest);

		PageRender<Publicacion> pageRender = new PageRender<Publicacion>("/publicar", publicaciones);
		model.addAttribute("titulo", "Publicaciones");
		model.addAttribute("publicaciones", publicaciones);
		model.addAttribute("page", pageRender);
		return "/publicar";
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid SuperClient scliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile archivo, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			//model.addAttribute("titulo", "Formulario de Cliente");
			//return "form";
		}

		if (!archivo.isEmpty()) {

			if (scliente.getCliente().getId() != null && scliente.getCliente().getId() > 0 && scliente.getCliente().getFoto() != null
					&& scliente.getCliente().getFoto().length() > 0) {

				uploadFileService.delete(scliente.getCliente().getFoto());
			}

			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(archivo);
			} catch (IOException e) {
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

			scliente.getCliente().setFoto(uniqueFilename);

		}
		
		System.out.println("scliente.getCliente().getId(): "+scliente.getCliente().getId());
		String mensajeFlash = (scliente.getCliente().getId() != null) ? "Cliente editado con éxito!" : "Cliente creado con éxito!";

		clienteService.save(scliente.getCliente());
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:ver/"+scliente.getCliente().getId();
	}

		
	private boolean hasRole(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context == null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		return authorities.contains(new SimpleGrantedAuthority(role));
		
	}
}

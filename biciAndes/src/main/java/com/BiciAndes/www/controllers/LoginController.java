package com.BiciAndes.www.controllers;

import java.io.File;
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
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

@Controller
public class LoginController {
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//AWS
    private AmazonS3 amazonClient;
//    private AmazonClient asmazonClient;

//    @Autowired
//    BucketController(AmazonClient amazonClient) {
//        this.amazonClient = amazonClient;
//    }

	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required = false) String logout,
			Model model, Principal principal, RedirectAttributes flash) {
		
		if(principal != null) {
			flash.addFlashAttribute("info", "Ya ha inciado sesión anteriormente");
			return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute("error", "Error en el login: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
		}
		
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");
		}
		
		return "login";
	}
	

	@RequestMapping(value = { "/register"})
	public String register(@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required = false) String logout,
			Model model, Principal principal, RedirectAttributes flash) {
		
		if(error != null) {
			model.addAttribute("error", "Error en el login: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
		}
		
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");
		}
		SuperClient sclient = new SuperClient();
		
		model.addAttribute("sclient", sclient);
//		model.addAttribute("usuario", sclient.getUsuario());
//		model.addAttribute("cliente", sclient.getCliente());
		model.addAttribute("titulo", "Ingrese los datos de Registro del cliente");
		model.addAttribute("isRegister", "true");
		model.addAttribute("btnLabel", "Registrar");
		model.addAttribute("url","/register");
		model.addAttribute("version", "master");
		
		
		return "form";
	}
	
//	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String guardar(@Valid SuperClient sclient, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}

		if (!foto.isEmpty()) {
			System.out.println("Empezo carga de Foto");
			AWSCredentials credentials = new BasicAWSCredentials(
					  "AKIA2JSBIF6LEUDL3PAF", 
					  "P9MURA7O1iH0b35DCTm4OdG3XzbJE303fxbOekeE"
					);		
			
			AmazonS3 s3client = AmazonS3ClientBuilder
					  .standard()
					  .withCredentials(new AWSStaticCredentialsProvider(credentials))
					  .withRegion(Regions.US_EAST_2)
					  .build();
			
			TransferManager tm = TransferManagerBuilder.standard()
					  .withS3Client(s3client)
					  .withMultipartUploadThreshold((long) (5 * 1024 * 1025))
					  .build();
			
			ObjectMetadata objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(foto.getContentType());
			objectMetaData.setContentLength(foto.getSize());
			System.out.println(foto.getName());
			System.out.println(foto.getContentType());
			try {
				s3client.putObject(new PutObjectRequest("biciandes", foto.getName(), foto.getInputStream(),objectMetaData));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					  
				

//			if (sclient.getCliente().getId() != null && sclient.getCliente().getId() > 0 && sclient.getCliente().getFoto() != null
//					&& sclient.getCliente().getFoto().length() > 0) {
//
//				uploadFileService.delete(sclient.getCliente().getFoto());
//			}
//
//			String uniqueFilename = null;
//			try {
//				uniqueFilename = uploadFileService.copy(foto);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
//
//			sclient.getCliente().setFoto(uniqueFilename);

		}

		String mensajeFlash = (sclient.getCliente().getId() != null) ? "Cliente creado con éxito!" : "Cliente creado con éxito!";

		
		//Tabla Users //set Role
		Role role = new Role();
		role.setAuthority("ROLE_USER");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		//Tabla 
//		Usuario usuario = new Usuario();
//		usuario.setPassword(bcryptPassword);
//		usuario.setUsername(sclient.getUsuario().getUsername());
//		usuario.setId(44L);
//		usuario.setRoles(roles);
//		usuario.setEnabled(true);
		
		sclient.getUsuario().setEnabled(true);
		sclient.getUsuario().setRoles(roles);
		String bcryptPassword = passwordEncoder.encode(sclient.getUsuario().getPassword());
		sclient.getUsuario().setPassword(bcryptPassword);
//		clienteDao.save(cliente);
		
		Long idUser = usuarioDao.save(sclient.getUsuario()).getId();
		
		//Almacenar cliente
		sclient.getCliente().setIdUser(idUser);
		clienteService.save(sclient.getCliente());
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		
		return "redirect:login";
	}

}

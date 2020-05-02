package com.uniandes.biciandes.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.uniandes.biciandes.dto.BicycleDto;
import com.uniandes.biciandes.model.Bicycle;
import com.uniandes.biciandes.model.User;
import com.uniandes.biciandes.service.BicycleService;
import com.uniandes.biciandes.service.UserService;
import com.uniandes.biciandes.util.S3Upload;

@Controller
public class BicycleController {
	
	private BicycleService bicycleService;
	
	private UserService userService;
	
	private S3Upload s3Upload;
	
	@Autowired
	public BicycleController(BicycleService bicycleService, UserService userService, S3Upload s3Upload) {
		this.bicycleService = bicycleService;
		this.userService = userService;
		this.s3Upload = s3Upload;
	}

	@GetMapping(value="/bicycles/create")
	public String formCreateDatosBicicleta(Map<String, Object> model) {
		
		
		model.put("titulo", "Crear Datos Bicicleta");
		model.put("url", "/bicycles/0");
		model.put("bicycle", new Bicycle());
		
		return "form-bicicleta";
		
	}

	@GetMapping(value="/bicycles/{id}/edit")
	public String formUpdateDatosBicicleta(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		
		Bicycle bicycle = bicycleService.getBicycle(id);
		model.put("titulo", "Editar Datos Bicicleta");
		model.put("url", "/bicycles/" + bicycle.getId());
		model.put("bicycle", bicycle);
		
		return "form-bicicleta";
		
	}

	@GetMapping("bicycles")
	public String bicyclesList(Map<String, Object> model, Authentication authentication) {
		String userId = authentication.getName();
		
		List<Bicycle> bicycles = bicycleService.getUserBicycles(userId);
		model.put("bicycles", bicycles);
		
		return "bicycle-list";
	}

	@PostMapping(value="/bicycles/{id}")
	public String guardarDatosBicicleta(@PathVariable(value = "id") Long id, @Valid @ModelAttribute BicycleDto bicycleDto, 
			@RequestParam(value = "file", required = false) MultipartFile file, Authentication authentication) {
		
		System.out.println(bicycleDto);
		Bicycle bicycle = bicycleDto.toBicycleEntity();
		
		if(id != 0L) {
			bicycle.setId(id);
		}

		if (file!=null && !file.isEmpty()) {
            String urlFoto = s3Upload.uploadFile(file);
            bicycle.setUrlPicture(urlFoto);
        }
		
		User usuario = userService.getUser(authentication.getName());
		
		bicycle.setUser(usuario);
		bicycleService.saveBicycle(bicycle);

		return "redirect:/bicycles";
	}

}

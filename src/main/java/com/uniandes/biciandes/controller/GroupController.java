package com.uniandes.biciandes.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniandes.biciandes.model.Group;
import com.uniandes.biciandes.model.User;

import com.uniandes.biciandes.service.GroupService;
import com.uniandes.biciandes.service.UserService;

@Controller
public class GroupController {

	private GroupService groupService;
	private UserService userService;
	
	@Autowired
    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

	
	@RequestMapping("/group")
	public String grupo(Model model) {
		
		List<Group> lista = new ArrayList<Group>();
		
		try {
			lista = groupService.findAll();
		}catch(Exception e){
			System.out.println(e.getMessage());
			lista = new ArrayList<Group>();
		}
		
		model.addAttribute("listGroup", lista);
		model.addAttribute("url","/group");
		return "group";
	}
	
	@GetMapping("/saveGroup/{id}")
	public String showSaveGroup(@PathVariable("id") Long id, Model model) {
		if(id != null && id != 0) {
			model.addAttribute("group", groupService.findById(id));
		}else {
			model.addAttribute("group", new Group());
		}
		return "saveGroup";
	}
	
	@PostMapping("/saveGroup")
	public String saveGroup(Group group, Model model) {
		boolean flag = true;
		/*Group grupo1 = new Group();
		grupo1.setId(3L);
		grupo1.setDescription("Grupo3");
		grupo1.setIsPrivate(flag);*/
		groupService.saveGroup(group);
		return "redirect:/group";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteGroup(@PathVariable("id") Long id, Model model) {
		Group group = this.groupService.findById(id);
		if (group != null) {
			this.groupService.delete(group);
		}
		return "redirect:/group";
	}
	
	/**
	 * Metodos para ingresar a un grupo
	 */
	@GetMapping("/insertMembers")
	public String insertMembers() {
		return "/insertMembers";
	}
	
	/**
	 * Metodo para  redirigir a los miembors del grupo.
	 */
	@RequestMapping("/viewGroup/{id}")
	public String showMembers(@PathVariable("id") Long id, Model model) {

		List<User>  members;

		try {
			members = groupService.findByGroup(id);
			System.out.println(members);
		}catch(Exception e){
			System.out.println(e.getMessage());
			members =  new ArrayList<>();
		}
		
		model.addAttribute("listUser", members);
		model.addAttribute("idGrupo", id);
		return "viewGroup";
	}
	
	@GetMapping("/deleteMember/{email}")
	public String deleteMember(@PathVariable("email") String email, Model model) {
		/*Group group = this.groupService.findByMember(email);
		if (group != null) {
			this.groupService.delete(group);
		}*/
		Long id = 0L;
		
		List<User> members = new ArrayList<User>();
		
		if(id != null && id != 0) {
			model.addAttribute("listUser", members);
		}else {
			model.addAttribute("listUser", members);
		}
		return "redirect:/viewGroup/3";
	}
	
	@GetMapping("/saveMember/{id}")
	public String showSaveMember(@PathVariable("id") Long id, Model model) {
		List<User> listUser = new ArrayList<User>();
		listUser = userService.findAll();
		if(id != null && id != 0) {
			model.addAttribute("listUser", listUser);
			model.addAttribute("idGroup", id);
		}else {
			model.addAttribute("listUser", new ArrayList<User>());
		}
		return "viewMembers";
	}
	
	@GetMapping("/joinMember/{email}")
	public String joinMember(@PathVariable("email") String email,@RequestParam(value = "id", required = false) Long id, Model model) {
		System.out.println(email);
		System.out.println(id);
		groupService.saveMembers(email, id);
		return "redirect:/group";
	}
	
}

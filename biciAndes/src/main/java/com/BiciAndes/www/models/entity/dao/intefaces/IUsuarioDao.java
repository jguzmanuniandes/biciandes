package com.BiciAndes.www.models.entity.dao.intefaces;

import org.springframework.data.repository.CrudRepository;

import com.BiciAndes.www.models.entity.Usuario;



public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

	public Usuario findByUsername(String username);
}

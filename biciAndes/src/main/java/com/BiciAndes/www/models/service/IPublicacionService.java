package com.BiciAndes.www.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.BiciAndes.www.models.entity.Cliente;
import com.BiciAndes.www.models.entity.Factura;
import com.BiciAndes.www.models.entity.Libro;



public interface IPublicacionService {

	public List<Publicaciones> findAll();
	
	public Page<Publicaciones> findAll(Pageable pageable);

	public void save(Publicacion publicacion);
	
	public Publicacion findOne(Long id);
	
	public Publicaciones fetchByIdWithClientes(Long id);
	
	public Cliente findClienteById(Long id);

}

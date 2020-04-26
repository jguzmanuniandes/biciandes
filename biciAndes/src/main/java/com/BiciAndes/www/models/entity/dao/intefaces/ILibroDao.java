package com.BiciAndes.www.models.entity.dao.intefaces;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.BiciAndes.www.models.entity.Libro;



public interface ILibroDao extends PagingAndSortingRepository<Libro, Long>{

	@Query("select p from Libro p where p.nombre like %?1%")
	public List<Libro> findByNombre(String term);
	
	public List<Libro> findByNombreLikeIgnoreCase(String term);
//	        UPDATE Material m SET m.inventory_count = ?2 WHERE m.id = ?1
	@Modifying
	@Transactional
	@Query("UPDATE Libro l SET l.cantidad = ?2 WHERE l.id =?1")
	public void updateCant(Long id, int cant);
}

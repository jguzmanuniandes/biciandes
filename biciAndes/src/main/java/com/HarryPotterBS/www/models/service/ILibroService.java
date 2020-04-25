package com.HarryPotterBS.www.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.HarryPotterBS.www.models.entity.Cliente;
import com.HarryPotterBS.www.models.entity.Factura;
import com.HarryPotterBS.www.models.entity.Libro;



public interface ILibroService {

	public List<Libro> findAll();
	
	public void updateExistencias(Factura factura);
	
	public Libro finLibroById(Long id);
	
	public Page<Libro> findAll(Pageable pageable);

	public void save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public Cliente fetchByIdWithFacturas(Long id);
	
	public void delete(Long id);
	
	public List<Libro> findByNombre(String term);
	
	public void saveFactura(Factura factura);
	
	public Libro findProductoById(Long id);
	
	public Factura findFacturaById(Long id);
	
	public void deleteFactura(Long id);
	
	public Factura fetchFacturaByIdWithClienteWhithItemFacturaWithProducto(Long id);

}

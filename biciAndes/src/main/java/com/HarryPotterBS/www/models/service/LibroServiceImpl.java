package com.HarryPotterBS.www.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.HarryPotterBS.www.models.entity.Cliente;
import com.HarryPotterBS.www.models.entity.Factura;
import com.HarryPotterBS.www.models.entity.ItemFactura;
import com.HarryPotterBS.www.models.entity.Libro;
import com.HarryPotterBS.www.models.entity.dao.intefaces.IClienteDao;
import com.HarryPotterBS.www.models.entity.dao.intefaces.IFacturaDao;
import com.HarryPotterBS.www.models.entity.dao.intefaces.ILibroDao;

@Service
public class LibroServiceImpl implements ILibroService{

	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private ILibroDao libroDao;
	
	@Autowired
	private IFacturaDao facturaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Libro> findAll() {
		// TODO Auto-generated method stub
		return (List<Libro>) libroDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cliente fetchByIdWithFacturas(Long id) {
		return clienteDao.fetchByIdWithFacturas(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Libro> findAll(Pageable pageable) {
		return libroDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Libro> findByNombre(String term) {
		return libroDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
	}

	@Override
	@Transactional(readOnly=true)
	public Libro findProductoById(Long id) {
		return libroDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Factura findFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteFactura(Long id) {
		facturaDao.deleteById(id); // facturaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Factura fetchFacturaByIdWithClienteWhithItemFacturaWithProducto(Long id) {
		return facturaDao.fetchByIdWithClienteWhithItemFacturaWithProducto(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Libro finLibroById(Long id) {
		return libroDao.findById(id).orElse(null);
	}

	@Override
	@Modifying
	@Transactional
	public void updateExistencias(Factura factura) {
		for (ItemFactura itemF : factura.getItems()) {
			Long id= itemF.getProducto().getId();
			int cant = this.finLibroById(id).getCantidad()-itemF.getCantidad();
			System.out.println(itemF.getProducto().getCantidad()+ "---"+ cant);
			libroDao.updateCant(id,cant);
			
		}
		
	}
	
	
}

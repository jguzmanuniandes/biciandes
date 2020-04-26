package com.BiciAndes.www.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "facturas_items")
public class ItemFactura implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer cantidad;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="producto_id")
	private Libro producto;

	public ItemFactura() {}
	
	public ItemFactura(Integer cantidad, Libro producto) {
		this.cantidad = cantidad;
		this.producto = producto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Libro getProducto() {
		return producto;
	}

	public void setProducto(Libro producto) {
		this.producto = producto;
	}

	public Double calcularImporte() {
		return cantidad.doubleValue() * producto.getPrecio();
	}
	
	private static final long serialVersionUID = 1L;

}

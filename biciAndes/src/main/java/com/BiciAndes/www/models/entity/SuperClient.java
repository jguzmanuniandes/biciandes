package com.BiciAndes.www.models.entity;

public class SuperClient {
	
	private Cliente cliente;
	private Usuario usuario;
	
	public SuperClient() {
		this.cliente = new Cliente();
		this.usuario = new Usuario();
	}
	
	public SuperClient(Cliente cliente, Usuario usuario) {
		this.cliente = cliente;
		this.usuario = usuario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}

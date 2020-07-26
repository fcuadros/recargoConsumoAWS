package com.zugatti.recargoconsumo.model;

public class AreaEmpresa {
	
	private int id;
	private String nombre;
	private int estado;

	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "AreaEmpresa [nombre=" + nombre + ", estado=" + estado + "]";
	}

	


}

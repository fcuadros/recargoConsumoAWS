package com.zugatti.recargoconsumo.model;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class RecargoConsumo {
	
	private Empleado empleado;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate fecha;
	private double montoServicio;
	private double montoAdicional;
	private double montoAbonar;
	
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public double getMontoServicio() {
		return montoServicio;
	}
	public void setMontoServicio(double montoServicio) {
		this.montoServicio = montoServicio;
	}
	public double getMontoAdicional() {
		return montoAdicional;
	}
	public void setMontoAdicional(double montoAdicional) {
		this.montoAdicional = montoAdicional;
	}
	public double getMontoAbonar() {
		return montoAbonar;
	}
	public void setMontoAbonar(double montoAbonar) {
		this.montoAbonar = montoAbonar;
	}
	@Override
	public String toString() {
		return "RecargoConsumo [empleado=" + empleado + ", fecha=" + fecha + ", montoServicio=" + montoServicio
				+ ", montoAdicional=" + montoAdicional + ", montoAbonar=" + montoAbonar + "]";
	}
	
	
	
	

}

package com.zugatti.recargoconsumo.handler;

import java.time.LocalDate;

public class DistribucionRCResponse {
	
	double puntosCargo = 1;
	String nombreCargo;
	String nombreArea;
	int empId;
	String nombresEmpleado;
	
	LocalDate fechaIngreso;
	double factorAntiguedad;
	double promedioLocal=1;
	
	double nota;
	int diasTrabajados;
	double bonoLogro;
	double servicioBasico;
	double montoServicio;
	double montoAdicional;
	double montoAbonar;
	int areaId;
	
	double subtotalRC;

	
	
	public String getNombreCargo() {
		return nombreCargo;
	}

	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}

	public double getPuntosCargo() {
		return puntosCargo;
	}

	public void setPuntosCargo(double puntosCargo) {
		this.puntosCargo = puntosCargo;
	}

	public String getNombreArea() {
		return nombreArea;
	}

	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getNombresEmpleado() {
		return nombresEmpleado;
	}

	public void setNombresEmpleado(String nombresEmpleado) {
		this.nombresEmpleado = nombresEmpleado;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public double getFactorAntiguedad() {
		return factorAntiguedad;
	}

	public void setFactorAntiguedad(double factorAntiguedad) {
		this.factorAntiguedad = factorAntiguedad;
	}

	public double getPromedioLocal() {
		return promedioLocal;
	}

	public void setPromedioLocal(double promedioLocal) {
		this.promedioLocal = promedioLocal;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public int getDiasTrabajados() {
		return diasTrabajados;
	}

	public void setDiasTrabajados(int diasTrabajados) {
		this.diasTrabajados = diasTrabajados;
	}

	public double getBonoLogro() {
		return bonoLogro;
	}

	public void setBonoLogro(double bonoLogro) {
		this.bonoLogro = bonoLogro;
	}

	public double getServicioBasico() {
		return servicioBasico;
	}

	public void setServicioBasico(double servicioBasico) {
		this.servicioBasico = servicioBasico;
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

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public double getSubtotalRC() {
		return subtotalRC;
	}

	public void setSubtotalRC(double subtotalRC) {
		this.subtotalRC = subtotalRC;
	}

	@Override
	public String toString() {
		return "DistribucionRCResponse [puntosCargo=" + puntosCargo + ", nombreCargo=" + nombreCargo + ", nombreArea="
				+ nombreArea + ", empId=" + empId + ", nombresEmpleado=" + nombresEmpleado + ", fechaIngreso="
				+ fechaIngreso + ", factorAntiguedad=" + factorAntiguedad + ", promedioLocal=" + promedioLocal
				+ ", nota=" + nota + ", diasTrabajados=" + diasTrabajados + ", bonoLogro=" + bonoLogro
				+ ", servicioBasico=" + servicioBasico + ", montoServicio=" + montoServicio + ", montoAdicional="
				+ montoAdicional + ", montoAbonar=" + montoAbonar + ", areaId=" + areaId + ", subtotalRC=" + subtotalRC
				+ "]";
	}
	
	
	
	
	
}

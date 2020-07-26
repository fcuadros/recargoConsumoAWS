package com.zugatti.recargoconsumo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gson.Gson;
import com.zugatti.recargoconsumo.dao.RecargoConsumoDAO;
import com.zugatti.recargoconsumo.handler.DistribucionRCResponse;
import com.zugatti.recargoconsumo.handler.RespuestaResponse;
import com.zugatti.recargoconsumo.model.PresupuestoArea;
import com.zugatti.recargoconsumo.model.RecargoConsumo;
import com.zugatti.recargoconsumo.util.Utilitario;

public class RecargoConsumoService {

	RecargoConsumoDAO rcDAO = new RecargoConsumoDAO();
	Gson gson = new Gson();
		
	public String getRCPorAreaHistorico(int anio,int mes) {
	
		LinkedHashMap<String, Object> map = rcDAO.getRCPorAreaHistorico(anio, mes);
		
		String json = gson.toJson(map);
		return json;
	
	}

	public String getMontosPorTiendaHistorico(int tiendaId, int anio) {
	
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		List<?> listaCostoPlanilla = rcDAO.getCostoPlanillaPorAreaMensual(tiendaId, anio);
		List<?> listaMontoServicio = rcDAO.getMontoServicioPorAreaMensual(tiendaId, anio);
		List<?> listaVentas = rcDAO.getVentasPorTiendaMensual(tiendaId, anio);
				
		map.put("costoPlanilla", listaCostoPlanilla);
		map.put("distribucionRC", listaMontoServicio);
		map.put("ventas", listaVentas);
		String json = gson.toJson(map);
		return json;
	
	}

	public RespuestaResponse actualizarDistribucionRCPorEmpleado(RecargoConsumo rc) {
		
		RespuestaResponse respuestaResponse = new RespuestaResponse();
		boolean respuestaActualizarDistribucionRCPorEmpleado = false;
		
		respuestaActualizarDistribucionRCPorEmpleado = rcDAO.actualizarDistribucionRCPorEmpleado(rc);
		
		if (respuestaActualizarDistribucionRCPorEmpleado) {
			respuestaResponse.setCodigoRespuesta("0");
			respuestaResponse.setMensajeRespuesta("Exito");
		} else {
			respuestaResponse.setCodigoRespuesta("1");
			respuestaResponse.setMensajeRespuesta("Error");
		}
		
		return respuestaResponse;
	}

	public String realizarDistribucionRC(int anio, int mes, int areaId){
		
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		double presupuestoFinal=0;
		List<PresupuestoArea> listaPresupuestos = rcDAO.getPresupuestoRCporArea(anio, mes);
		//Recuperar presupuesto de area elegida
		for (PresupuestoArea presupuesto : listaPresupuestos) {
			
			if (presupuesto.getAreaEmpresa().getId()== areaId){
				presupuestoFinal= presupuesto.getPresupuesto();
			}			
		}
				
		List<DistribucionRCResponse> listaEmpleadosTabla = rcDAO.getTablaDistribucionRC(anio,mes,areaId);
		//Calcular Subtotal
		calcularSubtotalRC(listaEmpleadosTabla);
		//calcular sumaSubtotal( array)
		double sumaSubtotalRC = calcularSumaSubtotalRC(listaEmpleadosTabla);
		//calcular montoservicio(array, sumasubtotal,monto a distribuir)
		calcularMontoRC(listaEmpleadosTabla, sumaSubtotalRC, presupuestoFinal);

		map.put("listaEmpleadosTabla", listaEmpleadosTabla);
		map.put("presupuesto", presupuestoFinal);
		String json = gson.toJson(map);
		return json;
		
		
	}
	
	public RespuestaResponse actualizarDistribucionPorArea(PresupuestoArea presupuesto) {
			
			RespuestaResponse respuestaResponse = new RespuestaResponse();
			boolean respuestaActualizarDistribucionPorArea = false;
			
			respuestaActualizarDistribucionPorArea = rcDAO.actualizarDistribucionPorArea(presupuesto);
			
			if (respuestaActualizarDistribucionPorArea) {
				respuestaResponse.setCodigoRespuesta("0");
				respuestaResponse.setMensajeRespuesta("Exito");
			} else {
				respuestaResponse.setCodigoRespuesta("1");
				respuestaResponse.setMensajeRespuesta("Error");
			}
			
			return respuestaResponse;
		}
		
	

	public String getDistribucionRCporArea(int anio, int mes) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		List<?> listaPresupuestos = rcDAO.getPresupuestoRCporArea(anio, mes);
		
		//TODO: remplazar con DAO para ventas
		//ArrayList<LinkedHashMap<Integer,Double>> listaVentas = new ArrayList<LinkedHashMap<Integer,Double>>();
		
		LinkedHashMap<Integer,Double> ventaMap = new LinkedHashMap<Integer,Double>();
		for (int i = 1; i < 13; i++) {
						
			Integer clave=Integer.valueOf(i);
			Double valor = Double.valueOf(93391-217*i*i);
			ventaMap.put(clave,valor);			
		}
		
		Double ventas = ventaMap.get(mes);
		
		map.put("listaPresupuestos", listaPresupuestos);
		map.put("ventas", ventas);
		String json = gson.toJson(map);
		return json;

	}

	public void calcularMontoRC (List<DistribucionRCResponse> listaEmpleados, double sumaSubtotal, double montoPresupuesto){
		for (DistribucionRCResponse empleado : listaEmpleados) {
			double montoServicio=0;
			//MontoServicio
			if (sumaSubtotal!=0){			
				montoServicio= empleado.getSubtotalRC()*montoPresupuesto/sumaSubtotal;			
			}
			empleado.setMontoServicio(Utilitario.round(montoServicio,4));
			//MontoAbonar
			empleado.setMontoAbonar(Utilitario.round(montoServicio + empleado.getBonoLogro() + empleado.getServicioBasico() + empleado.getMontoAdicional(),4));
		}
	}

	public void calcularAntiguedad(DistribucionRCResponse empleado) {
		// 10 anios equivale a 1
	
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaIngreso = empleado.getFechaIngreso();
	
		long diasAntiguedad = ChronoUnit.DAYS.between(fechaActual, fechaIngreso);
	
		double factorAntiguedad = diasAntiguedad / 3650;
	
		empleado.setFactorAntiguedad(factorAntiguedad);
	}

	public double calcularSumaSubtotalRC(List<DistribucionRCResponse> listaEmpleados) {
	
		double sumaSubtotalRC = 0;
	
		for (DistribucionRCResponse empleado : listaEmpleados) {
	
			double subtotal = empleado.getSubtotalRC();
	
			sumaSubtotalRC += subtotal;
		}
	
		return sumaSubtotalRC;
	
	}

	public void calcularSubtotalRC(List<DistribucionRCResponse> listaEmpleados) {
	
		for (DistribucionRCResponse empleado : listaEmpleados) {
			calcularAntiguedad(empleado);
	
			double subtotalRC = (empleado.getFactorAntiguedad() + empleado.getNota()) * empleado.getDiasTrabajados()
					* empleado.getPromedioLocal() * empleado.getPuntosCargo();
			empleado.setSubtotalRC(subtotalRC);
		}
	
	}

}

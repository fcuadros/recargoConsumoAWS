package com.zugatti.recargoconsumo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.gson.Gson;
import com.zugatti.recargoconsumo.handler.RespuestaResponse;
import com.zugatti.recargoconsumo.model.PresupuestoArea;
import com.zugatti.recargoconsumo.model.RecargoConsumo;
import com.zugatti.recargoconsumo.service.RecargoConsumoService;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@EnableWebMvc
public class RecargoConsumoController {

	RecargoConsumoService recargoConsumoService = new RecargoConsumoService();
	Gson gson = new Gson();

	
	@GetMapping(value = "/getRCPorAreaHistorico", produces = "application/json; charset=UTF-8")
	public String getRCPorAreaHistorico(@RequestParam("anio") int anio, @RequestParam("mes") int mes) {
	
		return recargoConsumoService.getRCPorAreaHistorico(anio,mes);
	
	}

	@GetMapping(value = "/getMontosPorTiendaHistorico", produces = "application/json; charset=UTF-8")
	public String getMontosPorTiendaHistorico(@RequestParam("tiendaId") int tiendaId, @RequestParam("anio") int anio) {
	
		return recargoConsumoService.getMontosPorTiendaHistorico(tiendaId, anio);
	
	}

	@PostMapping(value = "/actualizarDistribucionRCPorEmpleado", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")	
	public String actualizarDistribucionRCPorEmpleado(@RequestBody RecargoConsumo rc){

		
		System.out.println(rc.toString());

		RespuestaResponse response = recargoConsumoService.actualizarDistribucionRCPorEmpleado(rc);
		String json = gson.toJson(response);

		return json;
	}
	
	@GetMapping(value = "/realizarDistribucionRC", produces = "application/json; charset=UTF-8")
	public String realizarDistribucionRC(
			@RequestParam("anio") int anio, 
			@RequestParam("mes") int mes,
			@RequestParam("areaId") int areaId) {
	
		return recargoConsumoService.realizarDistribucionRC(anio, mes,areaId);
	
	}

	@GetMapping(value = "/obtenerDistribucionRCPorArea", produces = "application/json; charset=UTF-8")
	public String getDistribucionRCporArea(@RequestParam("anio") int anio, @RequestParam("mes") int mes) {

		return recargoConsumoService.getDistribucionRCporArea(anio, mes);

	}
	
	@PostMapping(value = "/actualizarDistribucionRCPorArea", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")	
		public String actualizarDistribucionPorArea(@RequestBody PresupuestoArea presupuestoArea){
	//			@RequestParam ("areaId") int areaId,
	//			@RequestParam ("mes") String mes,
	//			@RequestParam ("porcentaje") int porcentaje,
	//			@RequestParam ("presupuesto") int presupuesto) {		
	//
	//		AreaEmpresa area = new AreaEmpresa();
	//		area.setId(areaId);
	//		presupuestoArea.setAreaEmpresa(area);
	//		
	//		presupuestoArea.setMes(LocalDate.parse(mes));
	//		presupuestoArea.setPorcentaje(porcentaje);
	//		presupuestoArea.setPresupuesto(presupuesto);
			
			System.out.println(presupuestoArea.toString());
	
			RespuestaResponse response = recargoConsumoService.actualizarDistribucionPorArea(presupuestoArea);
			String json = gson.toJson(response);
	
			return json;
		}

}

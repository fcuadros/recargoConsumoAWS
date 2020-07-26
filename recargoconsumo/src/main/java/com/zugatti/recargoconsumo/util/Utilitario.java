package com.zugatti.recargoconsumo.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utilitario {

	public static String concatenarMesAnio(String mes, String anio) {
		String cadenaRespuesta = "";
		
		if ("1".equals(mes)) {
			cadenaRespuesta = "Enero".concat("-").concat(anio);
		} else if ("2".equals(mes)) {
			cadenaRespuesta = "Febrero".concat("-").concat(anio);
		} else if ("3".equals(mes)) {
			cadenaRespuesta = "Marzo".concat("-").concat(anio);
		} else if ("4".equals(mes)) {
			cadenaRespuesta = "Abril".concat("-").concat(anio);
		} else if ("5".equals(mes)) {
			cadenaRespuesta = "Mayo".concat("-").concat(anio);
		} else if ("6".equals(mes)) {
			cadenaRespuesta = "Junio".concat("-").concat(anio);
		} else if ("7".equals(mes)) {
			cadenaRespuesta = "Julio".concat("-").concat(anio);
		} else if ("8".equals(mes)) {
			cadenaRespuesta = "Agosto".concat("-").concat(anio);
		} else if ("9".equals(mes)) {
			cadenaRespuesta = "Setiembre".concat("-").concat(anio);
		} else if ("10".equals(mes)) {
			cadenaRespuesta = "Octubre".concat("-").concat(anio);
		} else if ("11".equals(mes)) {
			cadenaRespuesta = "Noviembre".concat("-").concat(anio);
		} else if ("12".equals(mes)) {
			cadenaRespuesta = "Diciembre".concat("-").concat(anio);
		}
		return cadenaRespuesta;
	}
	
	public static String concatenarMes(String mes) {
		String cadenaRespuesta = "";
		
		if ("1".equals(mes)) {
			cadenaRespuesta = "Enero";
		} else if ("2".equals(mes)) {
			cadenaRespuesta = "Febrero";
		} else if ("3".equals(mes)) {
			cadenaRespuesta = "Marzo";
		} else if ("4".equals(mes)) {
			cadenaRespuesta = "Abril";
		} else if ("5".equals(mes)) {
			cadenaRespuesta = "Mayo";
		} else if ("6".equals(mes)) {
			cadenaRespuesta = "Junio";
		} else if ("7".equals(mes)) {
			cadenaRespuesta = "Julio";
		} else if ("8".equals(mes)) {
			cadenaRespuesta = "Agosto";
		} else if ("9".equals(mes)) {
			cadenaRespuesta = "Setiembre";
		} else if ("10".equals(mes)) {
			cadenaRespuesta = "Octubre";
		} else if ("11".equals(mes)) {
			cadenaRespuesta = "Noviembre";
		} else if ("12".equals(mes)) {
			cadenaRespuesta = "Diciembre";
		}
		return cadenaRespuesta;
	}
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	
}

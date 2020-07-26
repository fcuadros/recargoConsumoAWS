package com.zugatti.recargoconsumo.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.zugatti.recargoconsumo.database.ConnectionJDBC;
import com.zugatti.recargoconsumo.handler.DistribucionRCResponse;
import com.zugatti.recargoconsumo.model.AreaEmpresa;
import com.zugatti.recargoconsumo.model.PresupuestoArea;
import com.zugatti.recargoconsumo.model.RecargoConsumo;

public class RecargoConsumoDAO {
	
	ConnectionJDBC jdbc = new ConnectionJDBC();
	
	public LinkedHashMap<String, Object> getRCPorAreaHistorico(int anio, int mes){
	
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		ArrayList<String> listaAreas = new ArrayList<String>();
		ArrayList<Double> listaRC = new ArrayList<Double>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = ""
				+ "SELECT ARE.ARE_NOMBRE, COALESCE(SUM(ERC_MONTO_SERVICIO),0) AS RC "
				+ "FROM pentium.t_empleado_recargo_consumo ERC "
				+ "RIGHT JOIN t_empleado EMP "
				+ "ON ERC.ERC_EMPLEADO=EMP.EMP_ID "
				+ "AND YEAR(ERC_FECHA)=" + anio +  " "
				+ "AND MONTH(ERC_FECHA)=" + mes +  " "
				+ "RIGHT JOIN t_area_empresa ARE " 
				+ "ON EMP.EMP_AREA=ARE.ARE_ID "
				+ "GROUP BY ARE_ID "
				+ "ORDER BY ARE_ID";
		
		
		try {
			ps = jdbc.getConnection().prepareStatement(sql);			
			System.out.println("QUERY getRCPorAreaHistorico: " + sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				listaAreas.add(rs.getString("ARE_NOMBRE"));
				listaRC.add(rs.getDouble("RC"));				 
			}
			
			map.put("listaAreas", listaAreas);
			map.put("listaRC", listaRC);
			
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jdbc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return map;
		
		
	}


	public ArrayList<Double> getVentasPorTiendaMensual(int tiendaId, int anio){
	
		ArrayList<Double> listaMontos = new ArrayList<Double>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql =  "select TVM_MES, TVM_VENTA AS MONTO from t_tienda_venta_mes "
				+ "WHERE TVM_TIENDA= " + tiendaId + " "
				+ "AND YEAR(TVM_MES)="+ anio + " "
				+ "ORDER BY TVM_MES";
		
		
		try {
			ps = jdbc.getConnection().prepareStatement(sql);			
			System.out.println("QUERY getVentasPorTiendaMensual: " + sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				listaMontos.add(rs.getDouble("MONTO"));
				 
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jdbc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return listaMontos;
		
		
	}


	public ArrayList<Double> getCostoPlanillaPorAreaMensual(int tiendaId, int anio){
	
		ArrayList<Double> listaMontos = new ArrayList<Double>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql =  "select ACP_MES, ACP_COSTO_PLANILLA AS MONTO from t_area_costo_planilla ACP "
				+ "INNER JOIN t_area_empresa ARE ON "
				+ "ARE.ARE_ID=ACP.ACP_AREA_ID "
				+ "WHERE ARE.ARE_ID= " + tiendaId + " "
				+ "AND YEAR(ACP.ACP_MES)="+ anio + " "
				+ "ORDER BY ACP_MES";
		
		
		try {
			ps = jdbc.getConnection().prepareStatement(sql);			
			System.out.println("QUERY getCostoPlanillaPorAreaMensual: " + sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				listaMontos.add(rs.getDouble("MONTO"));
				 
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jdbc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return listaMontos;
		
		
	}


	public ArrayList<Double> getMontoServicioPorAreaMensual(int tiendaId, int anio){

		ArrayList<Double> listaMontos = new ArrayList<Double>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql =  "select SUM(ERC_MONTO_SERVICIO) AS MONTO,ERC.ERC_FECHA "
				+ "from t_empleado_recargo_consumo ERC "
				+ "INNER JOIN t_empleado EMP ON "
				+ "ERC.ERC_EMPLEADO=EMP.EMP_ID "
				+ "WHERE EMP.EMP_AREA= " + tiendaId + " "
				+ "AND YEAR(ERC.ERC_FECHA)="+ anio + " "
				+ "GROUP BY ERC_FECHA "
				+ "ORDER BY ERC_FECHA";
		
		
		try {
			ps = jdbc.getConnection().prepareStatement(sql);			
			System.out.println("QUERY getMontoServicioPorAreaMensual: " + sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				listaMontos.add(rs.getDouble("MONTO"));
				 
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jdbc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaMontos;
		
		
	}
	
	
	public ArrayList<Double> getMontosPorTiendaHistorico(int tiendaId, int anio){
		
		ArrayList<Double> listaMontos = new ArrayList<Double>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT "
				+ "(select SUM(ERC_MONTO_SERVICIO) "
				+ "from t_empleado_recargo_consumo ERC "
				+ "INNER JOIN t_empleado EMP ON "
				+ "ERC.ERC_EMPLEADO=EMP.EMP_ID "
				+ "WHERE EMP.EMP_AREA= " + tiendaId + " "
				+ "AND YEAR(ERC.ERC_FECHA)="+ anio + ") AS TOTAL_RC, "
				+ "(select SUM(ACP_COSTO_PLANILLA) "
				+ "from t_area_costo_planilla ACP "
				+ "INNER JOIN t_area_empresa ARE ON "
				+ "ARE.ARE_ID=ACP.ACP_AREA_ID "
				+ "WHERE ARE.ARE_ID= " + tiendaId + " " 
				+ "AND YEAR(ACP.ACP_MES)="+ anio + ") AS TOTAL_COSTO_PLANILLA, "
				+ "(select SUM(TVM_VENTA) "
				+ "from t_tienda_venta_mes "
				+ "WHERE TVM_TIENDA= " + tiendaId + " "
				+ "AND YEAR(TVM_MES)="+ anio + ") AS TOTAL_VENTAS;";
		
		try {
			ps = jdbc.getConnection().prepareStatement(sql);			
			System.out.println("QUERY getMontosPorTiendaHistorico: " + sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				listaMontos.add(rs.getDouble("TOTAL_COSTO_PLANILLA"));
				listaMontos.add(rs.getDouble("TOTAL_RC"));
				listaMontos.add(rs.getDouble("TOTAL_VENTAS"));			
				 
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jdbc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaMontos;
	}
		
		
	
	
	public ArrayList<Double> getSumaMontosPorTiendaHistorico(int tiendaId, int anio){
		
		ArrayList<Double> listaMontos = new ArrayList<Double>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT "
				+ "(select SUM(ERC_MONTO_SERVICIO) "
				+ "from t_empleado_recargo_consumo ERC "
				+ "INNER JOIN t_empleado EMP ON "
				+ "ERC.ERC_EMPLEADO=EMP.EMP_ID "
				+ "WHERE EMP.EMP_AREA= " + tiendaId + " "
				+ "AND YEAR(ERC.ERC_FECHA)="+ anio + ") AS TOTAL_RC, "
				+ "(select SUM(ACP_COSTO_PLANILLA) "
				+ "from t_area_costo_planilla ACP "
				+ "INNER JOIN t_area_empresa ARE ON "
				+ "ARE.ARE_ID=ACP.ACP_AREA_ID "
				+ "WHERE ARE.ARE_ID= " + tiendaId + " " 
				+ "AND YEAR(ACP.ACP_MES)="+ anio + ") AS TOTAL_COSTO_PLANILLA, "
				+ "(select SUM(TVM_VENTA) "
				+ "from t_tienda_venta_mes "
				+ "WHERE TVM_TIENDA= " + tiendaId + " "
				+ "AND YEAR(TVM_MES)="+ anio + ") AS TOTAL_VENTAS;";
		
		try {
			ps = jdbc.getConnection().prepareStatement(sql);			
			System.out.println("QUERY getTablaDistribucionRC: " + sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				listaMontos.add(rs.getDouble("TOTAL_COSTO_PLANILLA"));
				listaMontos.add(rs.getDouble("TOTAL_RC"));
				listaMontos.add(rs.getDouble("TOTAL_VENTAS"));			
				 
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jdbc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return listaMontos;
	}




	public boolean actualizarDistribucionRCPorEmpleado(RecargoConsumo rc) {
		PreparedStatement ps = null;
		boolean respuesta = false;
		//String sql = "update impacto set factorImpacto=? where id_riesgo=?";		
		
		String sql = "INSERT INTO pentium.t_empleado_recargo_consumo(ERC_EMPLEADO,ERC_FECHA,ERC_MONTO_SERVICIO,ERC_MONTO_ADICIONAL,ERC_MONTO_ABONAR) "   
		+ " VALUES "	 
		+ " (?,?,?,?,?) "    
		+ " ON DUPLICATE KEY UPDATE "
		+ " ERC_MONTO_SERVICIO = ?, "
		+ " ERC_MONTO_ADICIONAL= ?, "
		+ " ERC_MONTO_SERVICIO = ? ";
		
		try {
			ps = jdbc.getConnection().prepareStatement(sql);
			ps.setInt(1, rc.getEmpleado().getEmpId());			
			ps.setDate(2, Date.valueOf(rc.getFecha()));
			ps.setDouble(3, rc.getMontoServicio());
			ps.setDouble(4, rc.getMontoAdicional());
			ps.setDouble(5, rc.getMontoAbonar());
			ps.setDouble(6, rc.getMontoServicio());
			ps.setDouble(7, rc.getMontoAdicional());
			ps.setDouble(8, rc.getMontoAbonar());
			System.out.println("QUERY actualizarDistribucionRCPorEmpleado: " + sql);
			ps.execute();
			ps.close();
			respuesta = true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jdbc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return respuesta;
	}
	
	public ArrayList<DistribucionRCResponse> getTablaDistribucionRC(int anio, int mes, int areaId){
		
		ArrayList<DistribucionRCResponse> listaEmpleados = new ArrayList<DistribucionRCResponse>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql =	"SELECT "
						+ "EMP.EMP_PUNTOS_CARGO, "
						+ "CAR.CAR_DESCRIPCION, "
						+ "ARE.ARE_NOMBRE, "
						+ "EMP.EMP_ID, "
						+ "CONCAT(EMP.EMP_APELLIDOS,', ',EMP.EMP_NOMBRES) AS EMP_NOMBRES, "
						+ "EMP.EMP_FECHA_INGRESO, "
						+ "TIE.TIE_PROMEDIO_LOCAL, "
						+ "EDT.EDT_NOTA, "
						+ "EDT.EDT_DIAS_TRABAJADOS, "
						+ "ELM.ELM_BONO_LOGRO, "
						+ "EMP.EMP_SERVICIO_BASICO, "
						+ "ERC.ERC_MONTO_SERVICIO, "
						+ "ERC.ERC_MONTO_ADICIONAL, "
						+ "ERC.ERC_MONTO_ABONAR, "
						+ "EMP.EMP_AREA "
						+ "from pentium.t_empleado EMP "
						+ "LEFT JOIN pentium.t_empleado_dias EDT "
						+ "ON EMP.EMP_ID=EDT.EDT_EMPLEADO "
						+ "LEFT JOIN pentium.t_empleado_logro_meta ELM "
						+ "ON EMP.EMP_ID=ELM.ELM_EMPLEADO "
						+ "LEFT JOIN pentium.t_cargo CAR "
						+ "ON EMP.EMP_CARGO = CAR.CAR_CODIGO "
						+ "LEFT JOIN pentium.t_area_empresa ARE "
						+ "ON EMP.EMP_AREA = ARE.ARE_ID "
						+ "LEFT JOIN pentium.t_tienda TIE "
						+ "ON EMP.EMP_AREA = TIE.TIE_ID "
						+ "LEFT JOIN pentium.t_empleado_recargo_consumo ERC "
						+ "ON EMP.EMP_ID = ERC_EMPLEADO "
						+ "AND (YEAR(ERC_FECHA) LIKE " + anio + " " + " OR ERC_FECHA IS NULL) "
						+ "AND (MONTH(ERC_FECHA) LIKE " + mes + " " + " OR ERC_FECHA IS NULL) "
						+ "WHERE EMP.EMP_AREA = " + areaId + " ";
						
		
		try {
			ps = jdbc.getConnection().prepareStatement(sql);			
			System.out.println("QUERY getTablaDistribucionRC: " + sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {													
				
				DistribucionRCResponse empleadoRC = new DistribucionRCResponse();
				 
				double doublePuntosCargo = rs.getDouble("EMP_PUNTOS_CARGO");
				
				if (doublePuntosCargo!=0) {
					empleadoRC.setPuntosCargo(doublePuntosCargo);
				}

				empleadoRC.setNombreCargo(rs.getString("CAR_DESCRIPCION"));
				empleadoRC.setNombreArea(rs.getString("ARE_NOMBRE"));				
				empleadoRC.setEmpId(rs.getInt("EMP_ID"));
				empleadoRC.setNombresEmpleado(rs.getString("EMP_NOMBRES"));
				empleadoRC.setFechaIngreso(LocalDate.parse(rs.getString("EMP_FECHA_INGRESO")));
				
				double  doublePromedioLocal = rs.getDouble("TIE_PROMEDIO_LOCAL");
				
				if (doublePromedioLocal==0) {
					doublePromedioLocal=1;
				}				
				empleadoRC.setPromedioLocal(doublePromedioLocal);
				
				empleadoRC.setNota(rs.getDouble("EDT_NOTA"));
				empleadoRC.setDiasTrabajados(rs.getInt("EDT_DIAS_TRABAJADOS"));
				empleadoRC.setBonoLogro(rs.getDouble("ELM_BONO_LOGRO"));
				empleadoRC.setServicioBasico(rs.getDouble("EMP_SERVICIO_BASICO"));
				empleadoRC.setMontoServicio(rs.getDouble("ERC_MONTO_SERVICIO"));
				empleadoRC.setMontoAdicional(rs.getDouble("ERC_MONTO_ADICIONAL"));
				empleadoRC.setMontoAbonar(rs.getDouble("ERC_MONTO_ABONAR"));
				
				listaEmpleados.add(empleadoRC);
				 
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jdbc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaEmpleados;
	}
	
	public boolean actualizarDistribucionPorArea(PresupuestoArea presupuesto) {
		PreparedStatement ps = null;
		boolean respuesta = false;
		//String sql = "update impacto set factorImpacto=? where id_riesgo=?";		
		
		String sql = "INSERT INTO pentium.t_area_presupuesto(APR_AREA,APR_MES,APR_PORCENTAJE,APR_PRESUPUESTO) "   
		+ " VALUES "	 
		+ " (?,?,?,?) "    
		+ " ON DUPLICATE KEY UPDATE "
		+ " APR_PORCENTAJE = ?, "
		+ " APR_PRESUPUESTO= ? ";		
		
		try {
			ps = jdbc.getConnection().prepareStatement(sql);
			ps.setInt(1, presupuesto.getAreaEmpresa().getId());			
			ps.setDate(2, Date.valueOf(presupuesto.getMes()));
			ps.setDouble(3, presupuesto.getPorcentaje());
			ps.setDouble(4, presupuesto.getPresupuesto());
			ps.setDouble(5, presupuesto.getPorcentaje());
			ps.setDouble(6, presupuesto.getPresupuesto());
			System.out.println("QUERY actualizarDistribucionPorArea: " + sql);
			ps.execute();
			ps.close();
			respuesta = true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jdbc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return respuesta;
	}
	
	
	
	public ArrayList<PresupuestoArea> getPresupuestoRCporArea(int anio, int mes){
		
		
		ArrayList<PresupuestoArea> listaPresupuesto =  new ArrayList<PresupuestoArea>();
		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "select ARE_ID,ARE_NOMBRE as area,month(APR_MES) as mes , APR_PORCENTAJE as porcentaje ,APR_PRESUPUESTO as presupuesto " +
		"from `t_area_empresa` ARE " +
		"LEFT  JOIN t_area_presupuesto APR " +
		"ON ARE.ARE_ID=APR.APR_AREA " +
		"AND YEAR(APR.APR_MES) = " + anio + " " +
		"AND MONTH(APR.APR_MES) = " + mes + " " +
		"ORDER BY ARE.ARE_ID";
	
		
		try {
			ps = jdbc.getConnection().prepareStatement(sql);			
			System.out.println("QUERY DistRCporArea: " + sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				PresupuestoArea presupuesto = new PresupuestoArea();
				 
				AreaEmpresa area = new AreaEmpresa();
				 area.setId(rs.getInt("ARE_ID"));
				 area.setNombre(rs.getString("area"));
				 presupuesto.setAreaEmpresa(area);
				 
				 presupuesto.setPorcentaje(rs.getDouble("porcentaje"));
				 presupuesto.setPresupuesto(rs.getDouble("presupuesto"));
				 
				 listaPresupuesto.add(presupuesto);
				 
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jdbc.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaPresupuesto;

	}

	

}

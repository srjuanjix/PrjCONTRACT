/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vo;

/**
 *
 * @author acondaqua
 */
public class LiquidacionesVo {
  
	
	private Integer id;
        private String fecha;  
        private String comercial; 
        private String zona ;
        private String ncertificacion ;
        private double importe;
        
        
		
	/**
	 * @return the idContrato
	 */
	public Integer getIdLiquidacion() {
		return id;
	}
	/**
	 * @param id_m_p the id_m_p to set
	 */
	public void setIdLiquidacion(Integer id) {
		this.id = id;
	}
	
	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}
        /**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
        
        /**
	 * @return the comercial
	 */
	public String getComercial() {
		return comercial;
	}
        /**
	 * @param fecha the fecha to set
	 */
	public void setComercial(String comercial) {
		this.comercial = comercial;
	}
         /**
	 * @return the comercial
	 */
	public String getZona() {
		return zona;
	}
        /**
	 * @param fecha the zona to set
	 */
	public void setZona(String zona) {
		this.zona = zona;
	}
        /**
	 * @return the ncertificación
	 */
	public String getnCertificacion() {
		return ncertificacion;
	}
        /**
	 * @param ncertificacion the zona to set
	 */
	public void setnCertificacion(String ncertificacion) {
		this.ncertificacion = ncertificacion;
	}
        /**
	 * @return the importe
	 */
	public double getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(Integer importe) {
		this.importe = importe;
	}
        

              
}

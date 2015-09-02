package vo;

public class PymesVo {
	
	private Integer id_m_r;
	private Integer estado;
        private String memo;
        private Integer incidencia;
        private String fecha;  
        private String comercial; 
        private Integer orden;
        private Integer swg;
        private Integer swe;
        private Integer dualFuel;
        private String cupsE ;
        private String cupsG ;       
        private Integer codPostal;
        private String municipio;
        private String provincia;
        private String direccion;
        private String titular;
        private String nifCif;
        private String fechaFirmaCliente;
        private double consumoElect;
        private double consumoGas;       
        private String telefonoCli;      
        private String observaciones ;
        private Integer svgCompleto; 
        private Integer svgXpres; 
        private Integer svgBasico; 
        private Integer svgElectricXpres; 
        private Integer servihogar; 
        private String tarifaGas;  
        private String tarifaElec;  
        private String AgenteComercial;
        private Integer svgConCalef; 
        private Integer svgSinCalef; 
         private Integer TurGas;
        private Integer Punteado;
       private Integer tarifaPlana;
       private Integer SPP;
       
		
	/**
	 * @return the idContrato
	 */
	public Integer getIdContrato() {
		return id_m_r;
	}
	/**
	 * @param id_m_p the id_m_p to set
	 */
	public void setIdContrato(Integer id_m_p) {
		this.id_m_r = id_m_p;
	}
	/**
	 * @return the estado
	 */
	public Integer getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
        /**
	 * @return the comercial
	 */
	public String getComercial() {
		return comercial;
	}
	/**
	 * @param comercial the fecha to set
	 */
	public void setComercial(String comercial) {
		this.comercial = comercial;
	}
	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}
        
        /**
	 * @param swg the fecha to set
	 */
	public void setSwg(Integer swg) {
		this.swg = swg;
	}
	/**
	 * @return the swg
	 */
	public Integer getSwg() {
		return swg;
	}
        
         /**
	 * @param swe the fecha to set
	 */
	public void setSwe(Integer swe) {
		this.swe = swe;
	}
	/**
	 * @return the swg
	 */
	public Integer getSwe() {
		return swe;
	}
        
         /**
	 * @param swe the fecha to set
	 */
	public void setDualFuel(Integer dualFuel) {
		this.dualFuel = dualFuel;
	}
	/**
	 * @return the dualFuel
	 */
	public Integer getDualFuel() {
		return dualFuel;
	}
        
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the incidencia
	 */
	public Integer getIncidencia() {
		return incidencia;
	}
	/**
	 * @param incidencia the incidencia to set
	 */
	public void setIncidencia(Integer incidencia) {
		this.incidencia = incidencia;
	}
	/**
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}
	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	/**
	 * @return the cups_elec
	 */
	public String getCupsE() {
		return cupsE;
	}
	/**
	 * @param cupsE the orden to set
	 */
	public void setCupsE(String cupsE) {
		this.cupsE = cupsE;
	}
        /**
	 * @return the cups_gas
	 */
	public String getCupsG() {
		return cupsG;
	}
	/**
	 * @param cupsG the orden to set
	 */
	public void setCupsG(String cupsG) {
		this.cupsG = cupsG;
	}
        
        
         /**
	 * @return the CodPostal
	 */
	public Integer getCodPostal() {
		return codPostal;
	}
	/**
	 * @param codPostal the orden to set
	 */
	public void setCodPostal(Integer codPostal) {
		this.codPostal = codPostal;
	}
          /**
	 * @return the Municipio
	 */
	public String getMunicipio() {
		return municipio;
	}
	/**
	 * @param municipio the orden to set
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
          /**
	 * @return the provincia
	 */
	public String getProvincia() {
		return provincia;
	}
	/**
	 * @param provincia the orden to set
	 */
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
           /**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the orden to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
           /**
	 * @return the titular
	 */
	public String getTitular() {
		return titular;
	}
	/**
	 * @param titular the orden to set
	 */
	public void setTitular(String titular) {
		this.titular = titular;
	}
        /**
	 * @return the NIF_CIF
	 */
	public String getNifCif() {
		return nifCif;
	}
	/**
	 * @param nifCif the orden to set
	 */
	public void setNifCif(String nifCif) {
		this.nifCif = nifCif;
	}
        /**
	 * @return the Consumo electrico
	 */
	public double getConsumoElect() {
		return consumoElect;
	}
	/**
	 * @param consumoElect the orden to set
	 */
	public void setConsumoElect(double consumoElect) {
		this.consumoElect = consumoElect;
	}
        /**
	 * @return the Consumo gas
	 */
	public double getConsumoGas() {
		return consumoGas;
	}
	/**
	 * @param consumoGas the orden to set
	 */
	public void setConsumoGas(double consumoGas) {
		this.consumoGas = consumoGas;
	}
      
        /**
	 * @return the Fecha Firma Cliente
	 */
	public String getFechaFirma() {
		return fechaFirmaCliente;
	}
	/**
	 * @param fechaFirmaCliente the orden to set
	 */
	public void setFechaFirma(String fechaFirmaCliente) {
		this.fechaFirmaCliente = fechaFirmaCliente;
	}
         /**
	 * @return the Oferta
	 */
	public Integer getSVG_1() {
		return svgCompleto;
	}
	/**
	 * @param oferta the orden to set
	 */
	public void setSVG_1(Integer svgCompleto) {
		this.svgCompleto = svgCompleto;
	}
         /**
	 * @return the Oferta
	 */
	public Integer getSVG_2() {
		return svgXpres;
	}
	/**
	 * @param oferta the orden to set
	 */
	public void setSVG_2(Integer svgXpres) {
		this.svgXpres = svgXpres;
	}
         /**
	 * @return the Oferta
	 */
	public Integer getSVG_3() {
		return svgBasico;
	}
	/**
	 * @param oferta the orden to set
	 */
	public void setSVG_3(Integer svgBasico) {
		this.svgBasico = svgBasico;
	}
          /**
	 * @return the Oferta
	 */
	public Integer getSVG_4() {
		return svgElectricXpres;
	}
	/**
	 * @param oferta the orden to set
	 */
	public void setSVG_4(Integer svgElectricXpres) {
		this.svgElectricXpres = svgElectricXpres;
	}
        
          /**
	 * @return the Oferta
	 */
	public Integer getSVG_5() {
		return servihogar;
	}
	/**
	 * @param oferta the orden to set
	 */
	public void setSVG_5(Integer servihogar) {
		this.servihogar = servihogar;
	}
       
        /**
	 * @return the Telefono Cliente
	 */
	public String getTelefonoCli() {
		return telefonoCli;
	}
	/**
	 * @param telefonoCli the orden to set
	 */
	public void setTelefonoCli(String telefonoCli) {
		this.telefonoCli = telefonoCli;
	}
       
        /**
	 * @return the Observaciones
        */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the Persona de contacto to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
         /**
	 * @return the 
        */
	public String getTarifaGas() {
		return tarifaGas;
	}
	/**
	 * @param  the Persona de contacto to set
	 */
	public void setTarifaGas(String tarifaGas) {
		this.tarifaGas = tarifaGas;
	}
          /**
	 * @return the 
        */
	public String getTarifaElec() {
		return tarifaElec;
	}
	/**
	 * @param  the Persona de contacto to set
	 */
	public void setTarifaElec(String tarifaElec) {
		this.tarifaElec = tarifaElec;
	}
           /**
	 * @return the 
        */
	public String getAgenteComercial() {
		return AgenteComercial;
	}
	/**
	 * @param  the Persona de contacto to set
	 */
	public void setAgenteComercial(String AgenteComercial) {
		this.AgenteComercial = AgenteComercial;
	}
          /**
	 * @param 
	 */

        /**
         *
         * @param svgConCalef
         */
         public void setSVG_6(Integer svgConCalef) {
		this.svgConCalef = svgConCalef;
	}
	/**
	 * @return the swg
	 */
	public Integer getSVG_6() {
		return svgConCalef;
	}
           /**
     * @param svgSinCalef	 */
	public void setSVG_7(Integer svgSinCalef) {
		this.svgSinCalef = svgSinCalef;
	}
	/**
	 * @return the swg
	 */
	public Integer getSVG_7() {
		return svgSinCalef;
	}
             /**
	 * @param swg the fecha to set
	 */
	public void setTurGas(Integer TurGas) {
		this.TurGas = TurGas;
	}
	/**
	 * @return the swg
	 */
	public Integer getTurGas() {
		return TurGas;
	}
            /**
	 * @param swg the fecha to set
	 */
	public void setPunteado(Integer Punteado) {
		this.Punteado = Punteado;
	}
	/**
	 * @return the swg
	 */
	public Integer getPunteado() {
		return Punteado;
	}
             /**
        * @param svgSinCalef	 */
	public void setTarifaPlana(Integer tarifaPlana) {
		this.tarifaPlana = tarifaPlana;
	}
	/**
	 * @return the swg
	 */
	public Integer getTarifaPlana() {
		return tarifaPlana;
	}
        public void setSVG_9(Integer SPP) {
		this.SPP = SPP;
	}
	/**
	 * @return the swg
	 */
	public Integer getSVG_9() {
		return SPP;
	}
}
              
    
       
       
   

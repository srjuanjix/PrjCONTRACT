package dao;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.dateTime;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import vo.PymesVo;

import conexion.Conexion;
import java.text.SimpleDateFormat;

/**
 * Clase que permite el acceso a la base de datos
 * 
 * @author chenao
 * 
 */
public class PymesDao {
 
	/**
	 * Usa el objeto enviado para almacenar la persona
	 * @param miPyme
	 */
    
        public String tablaDatos[][] = new String[10000][55] ;    // Tabla  con los datos a procesar.
        public String tablaLiquidaciones[][] = new String[10000][15] ;    // Tabla  con los datos de la liquidación
        public int nRegistros = 0;
        public int nLiquida = 0;
    
	public int registrarContrato(PymesVo miPyme,String str1,String str2) {
            
            System.out.println("str1,str2="+str1+" - "+str2);
            Conexion conex = new Conexion(str1,str2);
            int estadoInsert = 0 ;
            String sqlStr;

		try {
			                        
                        sqlStr = "INSERT INTO t_makro_pymes (Estado,Fecha_docout,Memo,Incidencia,Orden,CUPS_Elect,CUPS_Gas,Agente,CodPostal,Municipio,Provincia,"
                                        + "Direccion,Titular,NIF_CIF,Fecha_Firma_Cliente,CV,Consumo_elect_kwha,Consumo_elect_kwha_websale,Pagado,P_Fenosa,Tarifa,Campaña,"
                                        + "Telefono_Cli,Per_Contacto,Explicacion,Solucion,Observaciones,Tarifa_gas,C_servicios,Reactiva)"
                                        + " VALUES ('"					
					+ miPyme.getEstado() + "', '"
                                        + miPyme.getFechaDocout() + "', '"
                                        + miPyme.getFechaMemo() + "', '"
                                        + miPyme.getIncidencia() + "', '"    
					+ miPyme.getFechaOrden() + "', '"
                                        + miPyme.getCupsE() + "', '"
                                        + miPyme.getCupsG() + "', '"
                                        + miPyme.getAgente() + "', '" 
		 			+ miPyme.getCodPostal() + "', '"
                                        + miPyme.getMunicipio() + "', '"
                                        + miPyme.getProvincia() + "', '"
                                        + miPyme.getDireccion() + "', '"
                                        + miPyme.getTitular() + "', '"
                                        + miPyme.getNifCif() + "', '" 
                                        + miPyme.getFechaFirma() + "', '"
                                        + miPyme.getCVComercial() + "', '" 
                                        + miPyme.getConsumoElect() + "', '"
                                        + miPyme.getConsumoElectWS() + "', '"
                                        + miPyme.getPagado() + "', '"
                                        + miPyme.getPFenosa() + "', '"
                                        + miPyme.getTarifa() + "', '"
                                        + miPyme.getCampaña() + "', '"                                       
                                        + miPyme.getTelefonoCli() + "', '"
                                        + miPyme.getPerContacto() + "', '"
                                        + miPyme.getExplicacion() + "', '"
                                        + miPyme.getSolucion() + "', '"
                                        + miPyme.getObservaciones() + "', '"
                                        + miPyme.getTarifaGas() + "', '"
                                        + miPyme.getCServicios() + "', '"
                                        + miPyme.getReactiva() + "')" ;
                        
			System.out.println(sqlStr);
                        Statement estatuto = conex.getConnection().createStatement();
			estatuto.executeUpdate(sqlStr);
			estatuto.close();
			conex.desconectar();
                       

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			 estadoInsert = 2 ;
		}
                
                return estadoInsert;
	}

	/**
	 * Usa el objeto model para almacenar directamente la información obtenida
	 * de la BD, y se muestra en la tabla, el tamaño es 26 por defecto ya que
	 * conocemos el numero de columnas
	 * 
	 * @param model
	 */
	public void buscarContratos(DefaultTableModel model,String str1,String str2,int filtroEstado,int filtroFecha,int filtroProvincia,int filtroAgente, int filtroMakro,int filtroIncidencia,String fProd) {
                String str;
                int val;
                String strquery = "SELECT id_m_p,Estado,Fecha_docout,Memo,Incidencia,Orden,CUPS_Elect,CUPS_Gas,Agente,CodPostal,Municipio,Provincia,"
                        + "Direccion,Titular,NIF_CIF,Fecha_Firma_Cliente,CV,Consumo_elect_kwha,Consumo_elect_kwha_websale,Pagado,P_Fenosa,Tarifa,Campaña,"
                        + "Telefono_Cli,Per_Contacto,Explicacion,Solucion,Observaciones,Tarifa_gas,Consumo_gas_kwha,C_servicios,Reactiva,iServicios,iPunteado,P_Fenosa_Total,Empresa_Origen,Oferta"
                        + " FROM t_makro_pymes" ;
            //    filtroEstado = 3 ;
                switch (filtroEstado){
                    
                    case 0:
                        strquery = strquery + " WHERE Estado = 0" ;
                        break;
                    case 1:
                        strquery = strquery + " WHERE Estado = 1" ;
                        break;
                    case 2:
                        strquery = strquery + " WHERE Estado = 2" ;            
                        break;       
                    case 3:
                        strquery = strquery + " WHERE Estado <= 3" ; 
                        break; 
                    case 4:
                        strquery = strquery + " WHERE Estado = 3" ; 
                        break; 
                    case 5:
                        strquery = strquery + " WHERE Punteado = 1" ; 
                        break; 
                    case 6:
                        strquery = strquery + " WHERE Punteado = 0" ; 
                        break; 
                }        
                  switch (filtroIncidencia){
                    
                    case 0:
                        strquery = strquery + " AND Incidencia = 0" ;
                        break;
                    case 1:
                        strquery = strquery + " AND Incidencia = 1 " ;
                        break;
                    case 2:
                        strquery = strquery + " AND Incidencia = 2 " ;
                        break;
                    case 3:
                        strquery = strquery + " AND Incidencia = 3 " ;
                        break;
                    case 4:
                        strquery = strquery + " AND Incidencia = 4 " ;
                         break;
                    case 5:
                        strquery = strquery + " AND Incidencia = 5 " ;
                        break;
                    case 6:
                        strquery = strquery + " AND Incidencia = 6 " ;
                        break;
                    case 7:
                        strquery = strquery + "" ;
                        break;
                 }
                if (fProd.equals("")) {
                
                switch (filtroFecha){
                    
                    case 0:
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 5 DAY) <= Orden" ;
                        break;
                    case 1:
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 30 DAY) <= Orden" ;
                        break;
                    case 2:
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 60 DAY) <= Orden" ;            
                        break;  
                    case 3:
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 90 DAY) <= Orden" ;            
                        break;  
                    case 4:
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 180 DAY) <= Orden" ;            
                        break; 
                    case 5:                       
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 365 DAY) <= Orden" ;            
                        break;       
                    case 6:                       
                        strquery = strquery + "" ;            
                        break;
                    case 7:                       
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 10 DAY) <= Orden" ;            
                        break; 
                    case 8:                       
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 15 DAY) <= Orden" ;            
                        break; 
                             
                   
                }   
                } else {
                      strquery = strquery + " AND  Orden LIKE '"+fProd+" 00:00:00' ";                    
                }
                switch (filtroProvincia){
                    
                    case 0:
                        strquery = strquery + "" ;
                        break;
                    case 1:
                        strquery = strquery + " AND  Provincia LIKE 'CASTELL%'" ;
                        break;
                    case 2:
                        strquery = strquery + " AND  Provincia LIKE 'VALENCIA'" ;            
                        break;  
                    case 3:
                        strquery = strquery + " AND  Provincia LIKE 'ALICANTE'" ;            
                        break;  
                    case 4:
                        strquery = strquery + " AND  Provincia NOT LIKE 'VALENCIA' AND Provincia NOT LIKE 'CASTELL%' " ;            
                        break;  
                   
                }     
                 switch (filtroAgente){
                    
                    case 0:
                        strquery = strquery + "" ;
                        break;
                    case 1:
                        strquery = strquery + " AND (Comercial LIKE 'JOSE' OR Comercial LIKE 'J&C') " ;
                        break;
                    case 2:
                        strquery = strquery + " AND Comercial LIKE 'ETP' " ;
                        break;
                    case 3:
                        strquery = strquery + " AND Comercial LIKE 'NADINE' " ;
                        break;
                    case 4:
                        strquery = strquery + " AND Comercial LIKE 'ELANTY' " ;
                        break;
                    case 5:
                        strquery = strquery + " AND Comercial LIKE 'SERNOVEN' " ;
                        break;
                    case 6:
                        strquery = strquery + " AND Comercial LIKE 'MIGUEL' " ;
                        break;
                    case 7:
                        strquery = strquery + " AND Comercial LIKE 'SHEILA' " ;
                        break;
                 }
                 
                switch (filtroMakro){
                    
                    case 0:
                        strquery = strquery + " ORDER BY id_m_p ASC" ;
                        break;
                    case 1:
                        strquery = strquery + " ORDER BY Titular " ;
                        break;
                    case 2:
                        strquery = strquery + " ORDER BY Municipio " ;            
                        break;  
                    case 3:
                        strquery = strquery + " ORDER BY id_m_p DESC" ;            
                        break;  
                    case 4:
                        strquery = strquery + " ORDER BY NIF_CIF DESC" ;            
                        break;  
                  
                   
                }     
                
                System.out.println("La cadena de la consulta ="+strquery);
                System.out.println("str1,str2="+str1+" - "+str2);
		Conexion conex = new Conexion(str1,str2);
                 int cnt =0;
                 
               //converting date to string dd/MM/yyyy format for example "14/09/2011"
                SimpleDateFormat formatDateJava = new SimpleDateFormat("dd-MM-yyyy");
              
		try {
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery(strquery);

			while (rs.next()) {
				// es para obtener los datos y almacenar las filas
				Object[] fila = new Object[34];
				// para llenar cada columna con lo datos almacenados
				for (int i = 0; i < 34; i++) 
					fila[i] = rs.getObject(i + 1);          // es para cargar los datos en filas a la tabla modelo
                                       
                                        try {
                                            fila[2]  = formatDateJava.format(rs.getDate("Fecha_docout")) ;      // fecha docout ; 
                                            
                                        } catch (NullPointerException nfe){
                                            fila[2]  = ""; 
                                        }
                                        try {
                                            fila[5]  = formatDateJava.format(rs.getDate("Orden")) ; 
                                            
                                        } catch (NullPointerException nfe){
                                            fila[5]  = ""; 
                                        }
                                        try {
                                             fila[15] = formatDateJava.format(rs.getDate("Fecha_Firma_Cliente")) ;  
                                            
                                        } catch (NullPointerException nfe){
                                             fila[15]  = ""; 
                                        }
                                       
                                     
                                        //...........................................................
                                      
                                        tablaDatos[cnt][30] = Integer.toString(rs.getInt("id_m_p")) ;      //  Id_m_p
                                        tablaDatos[cnt][32] = "-1" ;                                       // Id última Locucion
                                        //...........................................................        Aqui cargamos en la tabla de datos de formulario
                                        
                                        tablaDatos[cnt][0] = Integer.toString(rs.getInt("Estado"));         // System.out.println(cnt+"-Estado="+tablaDatos[cnt][0]+" ID_C="+tablaDatos[cnt][30] );
                                                                              
                                        tablaDatos[cnt][1] = Integer.toString(rs.getInt("Incidencia"));     // Incidencia
                                        tablaDatos[cnt][2] = formatDateJava.format(rs.getDate("Orden")) ;   // Fecha de orden
                                      
                                        tablaDatos[cnt][3] = rs.getString("CUPS_Gas") ;                     // CUPS gas
                                        tablaDatos[cnt][4] = rs.getString("CUPS_Elect") ;                   // CUPS electrico
                                        
                                        tablaDatos[cnt][5] = Integer.toString(rs.getInt("CodPostal")) ;     // cod_postal ;
                                        tablaDatos[cnt][6] = rs.getString("Municipio");                     // municipio.toUpperCase() ;
                                        tablaDatos[cnt][7] = rs.getString("Provincia");                     //provincia.toUpperCase();
                                        tablaDatos[cnt][8] = rs.getString("Direccion");                     // direccion.toUpperCase();
                                        tablaDatos[cnt][9] = rs.getString("Titular");                       // titular.toUpperCase();
                                        tablaDatos[cnt][10]= rs.getString("NIF_CIF");                       // nif_cif.toUpperCase();
                                        tablaDatos[cnt][16]= rs.getString("Telefono_Cli");                  // telefono;     
                                        tablaDatos[cnt][11]= formatDateJava.format(rs.getDate("Fecha_Firma_Cliente"));         // fecha_firma ; 
                                        tablaDatos[cnt][12]= Integer.toString(rs.getInt("Consumo_elect_kwha")) ;              // consumo elec
                                        tablaDatos[cnt][13]= Integer.toString(rs.getInt("Consumo_elect_kwha_websale")) ;      // consumo elec websale                                                        
                                       
                                        tablaDatos[cnt][14] = Integer.toString(rs.getInt("Consumo_gas_kwha")) ;             // Consumo Gas
                                       
                                        tablaDatos[cnt][15]= rs.getString("Observaciones");                 // observaciones.toUpperCase();   
                                        tablaDatos[cnt][20]= rs.getString("Explicacion");                   // incidencia.toUpperCase();   
                                        tablaDatos[cnt][21]= rs.getString("Solucion");                      // solucion.toUpperCase();   

                                        tablaDatos[cnt][17] = rs.getString("Agente") ;                      //  agente.toUpperCase() ;
                                        
                                        tablaDatos[cnt][29] = rs.getString("Tarifa_gas") ;                  //  tarifa gas
                                        tablaDatos[cnt][28] = rs.getString("Tarifa") ;                      //  tarifa electrica
                                        
                                        tablaDatos[cnt][33] = rs.getString("C_servicios");
                                                                                                                                                                                                
                                        try {
                                            tablaDatos[cnt][39] = formatDateJava.format(rs.getDate("Fecha_docout"));      // fecha docout ; 
                                            
                                        } catch (NullPointerException nfe){
                                            tablaDatos[cnt][39] = ""; 
                                        }
                                        tablaDatos[cnt][40] = "-1";                                             // ID certificación
                                        tablaDatos[cnt][41] = rs.getString("CV") ;                              // Agente  Comercial
                                        tablaDatos[cnt][42] = rs.getString("Empresa_Origen") ;                  // Empresa origen
                                        System.out.println(" tablaDatos[cnt][41] ="+ tablaDatos[cnt][41] );
                                        tablaDatos[cnt][43] = Integer.toString(rs.getInt("iServicios"));        // iServicios 
                                        tablaDatos[cnt][44] = Integer.toString(rs.getInt("iPunteado"));         // Punteado
                                                                      
                                        
                                        tablaDatos[cnt][34]= rs.getString("Oferta") ;                           // Oferta
                                        tablaDatos[cnt][35]= rs.getString("Campaña") ;                          // Campaña
                                        tablaDatos[cnt][36]= rs.getString("Per_Contacto") ;                     // Persona de contacto
                                        tablaDatos[cnt][37]= Double.toString(rs.getDouble("Pagado"));           // Pagado
                                        tablaDatos[cnt][38]= rs.getString("P_Fenosa") ;                         // Pagado Fenosa
                                        tablaDatos[cnt][45]= Double.toString(rs.getDouble("P_Fenosa_Total"));   // Pagado Fenosa número
                                      
                                        tablaDatos[cnt][46]= Double.toString(rs.getDouble("Reactiva"));         // Reactiva
                                        tablaDatos[cnt][43]="0"; // Tur GAS
                                        tablaDatos[cnt][48]="0"; // Tarifa plana
                                        tablaDatos[cnt][51]="0"; // SPP
                                        
                                        
                                cnt ++;
                                
				model.addRow(fila);

			}
                        this.nRegistros = cnt;
                        System.out.println("Hemos introducido "+this.nRegistros+" registros en tablaDatos");
			rs.close();
			estatuto.close();
			conex.desconectar();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error al consultar", "Error",
					JOptionPane.ERROR_MESSAGE);

		}
	}
 public String buscarCodPost(String str1,String str2,String CodPost) {

               
                String resMunicipio="" ;
              
             
                int res = 0 ;
                
		 Conexion conex = new Conexion(str1,str2);
		try {
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery("SELECT * FROM t_municipios WHERE CodPostal LIKE '"+CodPost+"'");

                         int contador = 0;

                        while (rs.next()) {

                            contador++;

                        }
                       
                        if (contador >0){
                        
                               
                            rs.beforeFirst();

                            while (rs.next()) {
                                    resMunicipio = rs.getString("Municipio");    resMunicipio = resMunicipio.toUpperCase();                                 
                            }
                        } 
			rs.close();
			estatuto.close();
			conex.desconectar();
                                               

		} catch (SQLException e) {
			System.out.println(e.getMessage());
                        //                JOptionPane.showMessageDialog(null, "Error al consultar", "Error",JOptionPane.ERROR_MESSAGE);

		}
                return resMunicipio;
}
 public int compruebaCodPost(String str1,String str2,String CodPost,String municipio) {

                String resMunis[] = new String[10];
                String resMunicipio="" ;
                int res = 0 ;
                
		 Conexion conex = new Conexion(str1,str2);
		try {
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery("SELECT * FROM t_municipios WHERE CodPostal LIKE '"+CodPost+"'");

                         int contador = 0;

                        while (rs.next()) {

                            contador++;

                        }
                         System.out.println("Busco codpost, resultado contador ="+contador);
                          int cnt = 0 ;
                          int marca = 0 ;
                        if (contador >0){
                        
                               
                            rs.beforeFirst();
                           
                            while (rs.next()) {

                                    resMunicipio = rs.getString("Municipio");    resMunicipio = resMunicipio.toUpperCase();
                                    resMunis[cnt] = resMunicipio ;
                                    System.out.println("He encontrado registro CODPOSTAL "+CodPost+" y el municipio es ="+resMunicipio);
                                    if (resMunicipio.equals(municipio)) {marca = 1 ;System.out.println("ENCONTRADA SEMEJANZA");}
                                    cnt++;
                            }
                        } else {
                             System.out.println("No he encontrado el codigo postal"+CodPost+ "NO EXISTE EN BD");
                             res = 1 ;
                            
                        }
			rs.close();
			estatuto.close();
			conex.desconectar();
                       
                            if (res == 0) {
                                if (marca == 1) {
                                    res = 0 ;        
                                   
                                } else {
                                    System.out.println("No he encontrado el MUNICIPIO ("+municipio+") que corresponde con codigo postal="+CodPost);
                                    res = 2;
                                }
                            }
                       
                        

		} catch (SQLException e) {
			System.out.println(e.getMessage());
                         res = 2;
                        //                JOptionPane.showMessageDialog(null, "Error al consultar", "Error",JOptionPane.ERROR_MESSAGE);

		}
                return res;
}
  public String objectToString(Object o) {
        String st;
        st = (String) o;
        return st;
    }
  
// ---------------------------------------------------------------------------------------------------------------------------------------------
 
  
  
  public int modificarContrato(PymesVo miPyme,String str1,String str2) {
		 
            Conexion conex = new Conexion(str1,str2);
            int estadoInsert = 0 ;
            String sqlStr, strMemo="";

            if (miPyme.getMemo().equals("NULL") || miPyme.getMemo().equals("")) { 
                strMemo = "Memo = NULL "  ; 
                
            } else {
                 strMemo = "Memo ='"+miPyme.getMemo() + "' " ;
            }
                   
            
		try {
			                        
                        sqlStr = "UPDATE  t_makro_pymes SET "					
                                    	+ "Estado = "+ miPyme.getEstado() + ", "
                                        + "Fecha_docout = "+ miPyme.getFechaDocout() + ", "
                                        + "Incidencia = "+miPyme.getIncidencia() + ", "   
                                        + "Orden = '"+miPyme.getFechaOrden() + "', "  
                                        + "CUPS_Elect = '"+miPyme.getCupsE() + "', "
                                        + "CUPS_Gas='"+miPyme.getCupsG() + "', "
                                        + "Reactiva='"+miPyme.getReactiva() + "', "
                                        + "Agente='"+miPyme.getAgente() + "', " 
                                        + "Empresa_Origen='"+miPyme.getEmpresaOrigen() + "', " 
                                        + "CodPostal = "+miPyme.getCodPostal() + ", "
                                        + "Municipio='"+miPyme.getMunicipio() + "', "
                                        + "Provincia='"+miPyme.getProvincia() + "', "
                                        + "Direccion='"+miPyme.getDireccion() + "', "
                                        + "Titular='"+miPyme.getTitular() + "', "
                                        + "NIF_CIF='"+miPyme.getNifCif() + "', "
                                        + "Consumo_elect_kwha="+miPyme.getConsumoElect() + ", "
                                        + "Consumo_gas_kwha="+miPyme.getConsumoGas() + ", "
                                        + "Fecha_Firma_Cliente='"+miPyme.getFechaFirma() + "', "
                                        + "Oferta ='"+miPyme.getOferta() + "', "  
                                        + "Tarifa ='"+miPyme.getTarifa() + "', "   
                                        + "Campaña = '"+miPyme.getCampaña() + "',"
                                        + "Telefono_Cli='"+miPyme.getTelefonoCli() + "', "
                                        + "Per_Contacto = '"+miPyme.getPerContacto() + "', " 
                                        + "Observaciones ='"+miPyme.getObservaciones() + "', " 
                                        + "Tarifa_Gas ='"+miPyme.getTarifaGas() + "', " 
                                        + "Explicacion ='"+miPyme.getsExplicacion() + "', "
                                        + "Solucion ='"+miPyme.getSolucion() + "', "
                                        + "CV ='"+miPyme.getCVComercial() + "', "
                                        + "Consumo_elect_kwha_websale ='"+miPyme.getConsumoElectWS() + "', "
                                        + "Pagado = '"+miPyme.getPagado() + "',"
                                        + "P_Fenosa = '"+miPyme.getPFenosa() + "',"
                                        + "C_servicios = '"+miPyme.getCServicios()+ "',"
                                        + "iPunteado = "+miPyme.getPunteado()+ ","
                                        + "P_Fenosa_Total = "+miPyme.getPagadoFenosa()
                                        + " WHERE id_m_p ="+miPyme.getIdContrato();
                        
			System.out.println(sqlStr);
                        Statement estatuto = conex.getConnection().createStatement();
                        System.out.println("CADENA SQL="+sqlStr) ;
			estatuto.executeUpdate(sqlStr);
			estatuto.close();
			conex.desconectar();
                       

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			 estadoInsert = 2 ;
		}
                
                return estadoInsert;
	}
  // ---------------------------------------------------------------------------------------------------------------------------------------------
  
  public int modificarTablaIncidencia(PymesVo miPyme,String str1,String str2) {
		 
            Conexion conex = new Conexion(str1,str2);
            int estadoInsert = 0 ;
            String sqlStr;

		try { 
                        sqlStr = "UPDATE  t_makro_residencial SET "				
					+ "IdIncidencia = "+miPyme.getIncidencia()  					                             
                                        + " WHERE id_m_r = "+miPyme.getIdContrato();
                        
			System.out.println(sqlStr);
                        Statement estatuto = conex.getConnection().createStatement();
			estatuto.executeUpdate(sqlStr);
			estatuto.close();
			conex.desconectar();
                       

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			 estadoInsert = 2 ;
		}
                
                return estadoInsert;
	}
  // ---------------------------------------------------------------------------------------------------------------------------------------------
  
  public int modificarTablaEstado(PymesVo miPyme,String str1,String str2) {
		 
            Conexion conex = new Conexion(str1,str2);
            int estadoInsert = 0 ;
            String sqlStr;

		try { 
                        sqlStr = "UPDATE  t_makro_residencial SET "				
					+ "Estado = "+miPyme.getEstado()  					                             
                                        + " WHERE id_m_r = "+miPyme.getIdContrato();
                        
			System.out.println(sqlStr);
                        Statement estatuto = conex.getConnection().createStatement();
			estatuto.executeUpdate(sqlStr);
			estatuto.close();
			conex.desconectar();
                       
		} catch (SQLException e) {
			 System.out.println(e.getMessage());
			 estadoInsert = 2 ;
		}
                
                return estadoInsert;
	}
  // -------------------------------------------------------------------------------------------------------------------------------------------
  public void buscarRechazados(String str1,String str2,String sFecha,int incidencia) {
                String str="";
                int val;
                
                if ( incidencia == 0) str=" Estado = 3 ";
                if ( incidencia == 1) str=" IdIncidencia=1 ";
                String strquery = "SELECT * FROM t_makro_residencial WHERE "+str+" AND Fecha='"+sFecha+" 00:00:00' ORDER BY id_m_r ASC" ;
            
                System.out.println("La cadena de la consulta ="+strquery);
		 Conexion conex = new Conexion(str1,str2);
                 int cnt =0;
                 
               //converting date to string dd/MM/yyyy format for example "14/09/2011"
                SimpleDateFormat formatDateJava = new SimpleDateFormat("dd-MM-yyyy");
                 
		try {
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery(strquery);

			while (rs.next()) {
				// es para obtener los datos y almacenar las filas
				
				// para llenar cada columna con lo datos almacenados
				for (int i = 0; i < 33; i++) 
					
                                        
                                        //...........................................................
                                      
                                        tablaDatos[cnt][30] = Integer.toString(rs.getInt(1)) ;      //  Id_m_r
                                        tablaDatos[cnt][32] = "-1" ;                                // Id última Locucion
                                        //........................................................... Aqui cargamos en la tabla de datos de formulario
                                        tablaDatos[cnt][0] = Integer.toString(rs.getInt(2)); // System.out.println(cnt+"-Estado="+tablaDatos[cnt][0]+" ID_C="+tablaDatos[cnt][30] );
                                                                              
                                        tablaDatos[cnt][1] = Integer.toString(rs.getInt(4)); 
                                        tablaDatos[cnt][2] = formatDateJava.format(rs.getDate(5)) ;  
                                      
                                       
                                        
                                        tablaDatos[cnt][3] = rs.getString(11) ;  // CUPS gas
                                        tablaDatos[cnt][4] = rs.getString(12) ; // CUPS electrico
                                        
                                        tablaDatos[cnt][5] = Integer.toString(rs.getInt(13)) ;   // cod_postal ;
                                        tablaDatos[cnt][6] = rs.getString(14);   // municipio.toUpperCase() ;
                                        tablaDatos[cnt][7] = rs.getString(15);   //provincia.toUpperCase();
                                        tablaDatos[cnt][8] = rs.getString(16);   // direccion.toUpperCase();
                                        tablaDatos[cnt][9] = rs.getString(17);   // titular.toUpperCase();
                                        tablaDatos[cnt][10]= rs.getString(18);    // nif_cif.toUpperCase();
                                        tablaDatos[cnt][16]= rs.getString(19);   // telefono;     
                                        tablaDatos[cnt][11]= formatDateJava.format(rs.getDate(20));      // fecha_firma ; 
                                        tablaDatos[cnt][12]= Integer.toString(rs.getInt(21)) ;      // consumo elec
                                        tablaDatos[cnt][13]= Integer.toString(rs.getInt(22)) ;      // consumo gas                                                        
                                       
                                        tablaDatos[cnt][14] = "0";
                                       
                                        val = rs.getInt(23); if ( val==1 ) {tablaDatos[cnt][34] = "1" ; }  else tablaDatos[cnt][34] = "0" ;
                                        val = rs.getInt(24); if ( val==1 ) {tablaDatos[cnt][35] = "1" ; }  else tablaDatos[cnt][35] = "0" ;
                                        val = rs.getInt(25); if ( val==1 ) {tablaDatos[cnt][36] = "1" ; }  else tablaDatos[cnt][36] = "0" ;
                                        val = rs.getInt(26); if ( val==1 ) {tablaDatos[cnt][37] = "1" ; }  else tablaDatos[cnt][37] = "0" ;
                                        val = rs.getInt(27); if ( val==1 ) {tablaDatos[cnt][38] = "1" ; }  else tablaDatos[cnt][38] = "0" ;
                                        
                             //           System.out.println("VAl1 ="+rs.getInt(23)+" VAl2 ="+rs.getInt(24)+" VAl3 ="+rs.getInt(25)+" VAl4 ="+rs.getInt(26)+" VAl5 ="+rs.getInt(27)+" para Titular = "+ tablaDatos[cnt][9]);
                   
                
                                        tablaDatos[cnt][15]= rs.getString(32); // observaciones.toUpperCase();   
                                        tablaDatos[cnt][20]= rs.getString(29); // incidencia.toUpperCase();   
                                        tablaDatos[cnt][21]= rs.getString(30); // solucion.toUpperCase();   

                                        tablaDatos[cnt][17] = rs.getString(6) ; //  agente.toUpperCase() ;
                                        
                                        tablaDatos[cnt][28] = rs.getString(33) ; //  tarifa gas
                                        tablaDatos[cnt][29] = rs.getString(34) ; //  tarifa electrica
                                        
                                        tablaDatos[cnt][33] = "0" ;                                             // de entrada definimos a cero
                                        
                                        val = rs.getInt(8); if ( val==1 ) {tablaDatos[cnt][33] = "1" ; }        // Contrato Swg
                                        val = rs.getInt(9); if ( val==1 ) {tablaDatos[cnt][33] = "2" ; }        // Contrato Swe
                                        val = rs.getInt(10); if ( val==1 ) {tablaDatos[cnt][33] = "3" ; }        // Contrato DualFuel
                                                                                                                                                         
                                        try {
                                            tablaDatos[cnt][39] = formatDateJava.format(rs.getDate(3));      // fecha memo ; 
                                            
                                        } catch (NullPointerException nfe){
                                            tablaDatos[cnt][39] = ""; 
                                        }
                                        tablaDatos[cnt][40] = "-1";                                             // ID certificación
                                        tablaDatos[cnt][41] = rs.getString(35) ;                                // Agente comercial
                                
                                    
                                    
                                        tablaDatos[cnt][43] = "0";  
                                        tablaDatos[cnt][44] = "0"; 
                                       
                                        val = rs.getInt(36); if ( val==1 ) {tablaDatos[cnt][43] = "1" ; }        // Tur Gas
                                        val = rs.getInt(37); if ( val==1 ) {tablaDatos[cnt][44] = "1" ; }        // Punteado
                                        tablaDatos[cnt][45] = "0" ;
                                        tablaDatos[cnt][46] = "0" ;
                                        val = rs.getInt(38); if ( val==1 ) {tablaDatos[cnt][45] = "1" ; }        // SVG Con Calefaccion
                                        val = rs.getInt(39); if ( val==1 ) {tablaDatos[cnt][46] = "1" ; }        // SVG Sin Calefacción
                                        tablaDatos[cnt][47] = "1";
                                        tablaDatos[cnt][48] = "0" ;
                                        val = rs.getInt(40); if ( val==1 ) {tablaDatos[cnt][48] = "1" ; }        // Tarifa Plana
                                        tablaDatos[cnt][51] = "0" ;
                                        val = rs.getInt(41); if ( val==1 ) {tablaDatos[cnt][51] = "1" ; }        // SPP
                                        
                                        
                                        
                                cnt ++;
                                
				

			}
                        this.nRegistros = cnt;
                        System.out.println("Hemos encontrado "+this.nRegistros+" registros para devolver");
			rs.close();
			estatuto.close();
			conex.desconectar();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error al consultar", "Error",
					JOptionPane.ERROR_MESSAGE);

		}
	}
  // -------------------------------------------------------------------------------------------------------------------------------------------------
  public int tablaLiquidaciones(DefaultTableModel model,String str1,String str2,String fechaSel, int filtroProvincia,int filtroAgente) {
                String str;
                int val,p1,p2,p3,p4,p5,p6,p7,p8,pTotal=0;
                int nReg=0;
                
                String strquery = "SELECT idIncidencia,TurGas,CUPS_gas,CUPS_Elect,Titular,NIF_CIF,SVGCompleto,SVGXpres,SVGBasico,SVelectricXpres,Servihogar,SVGCompletoConCalef,SVGCompletoSinCalef,SPP,TarifaPlana,Observaciones FROM t_makro_residencial  WHERE (Estado=0 OR ESTADO=1 OR Estado=3) " ;
            //    filtroEstado = 3 ;
                 
                strquery = strquery + " AND (idIncidencia=0 OR idIncidencia=4  OR idIncidencia=5 OR idIncidencia=3 OR idIncidencia=1 OR idIncidencia=2) AND  Fecha LIKE '"+fechaSel+"'" ;
                  
                switch (filtroProvincia){
                    
                    case 0:
                        strquery = strquery + "" ;
                        break;
                    case 1:
                        strquery = strquery + " AND  Provincia LIKE 'CASTELL%'" ;
                        break;
                    case 2:
                        strquery = strquery + " AND  ( Provincia LIKE 'VALENCIA' OR Provincia NOT LIKE 'CASTELL%')" ;            
                        break;  
                    case 3:
                        strquery = strquery + " AND  Provincia LIKE 'ALICANTE'" ;            
                        break;  
                    case 4:
                        strquery = strquery + " AND  (Provincia NOT LIKE 'VALENCIA' OR Provincia NOT LIKE 'CASTELL%') " ;            
                        break;  
                   
                }     
                 switch (filtroAgente){
                    
                    case 0:
                        strquery = strquery + "" ;
                        break;
                    case 1:
                        strquery = strquery + " AND Comercial LIKE 'JOSE' OR Comercial LIKE 'J & C' " ;
                        break;
                    case 2:
                        strquery = strquery + " AND Comercial LIKE 'ETP' " ;
                        break;
                    case 3:
                        strquery = strquery + " AND Comercial LIKE 'NADINE' " ;
                        break;
                    case 4:
                        strquery = strquery + " AND Comercial LIKE 'ELANTY' " ;
                        break;
                    case 5:
                        strquery = strquery + " AND Comercial LIKE 'SERNOVEN' " ;
                        break;
                    case 6:
                        strquery = strquery + " AND Comercial LIKE 'MIGUEL' " ;
                        break;
                    case 7:
                        strquery = strquery + " AND Comercial LIKE 'SHEILA' " ;
                        break;
                        
                 }
                
                  strquery = strquery + " ORDER BY idIncidencia,id_m_r ASC";
                
                System.out.println("La cadena de la consulta ="+strquery);
         Conexion conex = new Conexion(str1,str2);
                 int cnt =0;
                 
               //converting date to string dd/MM/yyyy format for example "14/09/2011"
                SimpleDateFormat formatDateJava = new SimpleDateFormat("dd-MM-yyyy");
              
        try {
            Statement estatuto = conex.getConnection().createStatement();
            ResultSet rs = estatuto.executeQuery(strquery);

            while (rs.next()) {
                 
                 // es para obtener los datos y almacenar las filas
                 Object[] fila = new Object[17];
                
                 p1 = rs.getInt(7);
                 p2 = rs.getInt(8);
                 p3 = rs.getInt(9);
                 p4 = rs.getInt(10);
                 p5 = rs.getInt(11);
                 p6 = rs.getInt(12);
                 p7 = rs.getInt(13);
               
                
                 pTotal = p1+p2+p3+p4+p5+p6+p7 ;
                
                // para llenar cada columna con lo datos almacenados
                for (int i = 0; i < 16; i++) 
                    
                    fila[i] = rs.getObject(i + 1);          // es para cargar los datos en filas a la tabla modelo
                  
                fila[16] = pTotal ;
                model.addRow(fila);
                nReg++;

            }
                     
                        System.out.println("Hemos introducido "+nReg+" registros en tablaDatos");
            rs.close();
            estatuto.close();
            conex.desconectar();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al consultar", "Error",
                    JOptionPane.ERROR_MESSAGE);

        }
        return nReg;
    }
   // -------------------------------------------------------------------------------------------------------------------------------------------------
   public int contarProductosLiquidaciones(DefaultTableModel model,String str1,String str2,String fechaSel, int filtroProvincia,int filtroAgente,int producto) {
                String str,prod="";
                int val,p1,p2,p3,p4,p5,p6,p7,p8,pTotal=0;
                  switch (producto){
                       case 0:
                           prod = "SVGCompleto";
                           break;
                       case 1:
                           prod = "SVGXpres";
                           break;
                       case 2:
                           prod = "SVGBasico";
                           break;
                       case 3:
                           prod = "SVelectricXpres";
                           break;     
                       case 4:
                           prod = "Servihogar";
                           break; 
                       case 5:
                           prod = "SVGCompletoConCalef";
                           break;
                       case 6:
                           prod = "SVGCompletoSinCalef";
                           break;
                       case 7:
                           prod = "TurGas";
                           break;
                       case 8:
                           prod = "CUPS_gas";
                           break;
                       case 9:
                           prod = "CUPS_Elect";
                           break;
                       case 10:
                           prod = "TarifaPlana";
                           break;
                       case 11:
                           prod = "TarifaPlana";
                           break;
                        case 12:
                           prod = "SPP";
                           break;
                  }
                 String strquery = "";
                if (producto ==8 || producto==9) {
                     strquery = "SELECT COUNT("+prod+") FROM t_makro_residencial  WHERE (Estado = 0 OR Estado= 1 OR Estado=2) AND "+prod+" LIKE '%0%'" ;
                } else { 
                    if (producto <8) {
                     strquery = "SELECT COUNT("+prod+") FROM t_makro_residencial  WHERE (Estado = 0 OR Estado= 1 OR Estado=2) AND "+prod+"=1" ;
                    } else {
                    if (producto ==10 ){
                      strquery = "SELECT COUNT("+prod+") FROM t_makro_residencial  WHERE (Estado = 0 OR Estado= 1 OR Estado=2) AND "+prod+"=1 AND CUPS_gas LIKE '%0%'"  ;   
                    }    
                    if (producto ==11 ){
                      strquery = "SELECT COUNT("+prod+") FROM t_makro_residencial  WHERE (Estado = 0 OR Estado= 1 OR Estado=2) AND "+prod+"=1 AND CUPS_Elect LIKE '%0%'"  ;     
                    }  
                    if (producto ==12 ){
                      strquery = "SELECT COUNT("+prod+") FROM t_makro_residencial  WHERE (Estado = 0 OR Estado= 1 OR Estado=2) AND "+prod+"=1" ;  
                    }  
                        
                    }
                } 
            //    filtroEstado = 3 ;
                 
                strquery = strquery + " AND (idIncidencia=0 OR idIncidencia=4  ) AND  Fecha LIKE '"+fechaSel+"'" ;
                  
                switch (filtroProvincia){
                    
                    case 0:
                        strquery = strquery + "" ;
                        break;
                    case 1:
                        strquery = strquery + " AND  Provincia LIKE 'CASTELL%'" ;
                        break;
                    case 2:
                        strquery = strquery + " AND  ( Provincia LIKE 'VALENCIA' OR Provincia NOT LIKE 'CASTELL%')" ;            
                        break;  
                    case 3:
                        strquery = strquery + " AND  Provincia LIKE 'ALICANTE'" ;            
                        break;  
                    case 4:
                        strquery = strquery + " AND  ( Provincia NOT LIKE 'VALENCIA' OR Provincia NOT LIKE 'CASTELL%' ) " ;            
                        break;  
                   
                }     
                 switch (filtroAgente){
                    
                    case 0:
                        strquery = strquery + "" ;
                        break;
                    case 1:
                        strquery = strquery + " AND Comercial LIKE 'JOSE' OR Comercial LIKE 'J & C' " ;
                        break;
                    case 2:
                        strquery = strquery + " AND Comercial LIKE 'ETP' " ;
                        break;
                    case 3:
                        strquery = strquery + " AND Comercial LIKE 'NADINE' " ;
                        break;
                     case 4:
                        strquery = strquery + " AND Comercial LIKE 'ELANTY' " ;
                        break;
                    case 5:
                        strquery = strquery + " AND Comercial LIKE 'SERNOVEN' " ;
                        break;
                    case 6:
                        strquery = strquery + " AND Comercial LIKE 'MIGUEL' " ;
                        break;
                    case 7:
                        strquery = strquery + " AND Comercial LIKE 'SHEILA' " ;
                        break;
                    
                 }
                
                  strquery = strquery + " ORDER BY id_m_r DESC";
                
                System.out.println("La cadena de la consulta ="+strquery);
                Conexion conex = new Conexion(str1,str2);
                 int cnt =0;
                 
             
              
        try {
            Statement estatuto = conex.getConnection().createStatement();
            ResultSet rs = estatuto.executeQuery(strquery);

            rs.next();
            pTotal = rs.getInt(1);
                    
            System.out.println("Producto "+prod+" --> "+pTotal+" registros en liquidacion");
            rs.close();
            estatuto.close();
            conex.desconectar();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al consultar", "Error",
                    JOptionPane.ERROR_MESSAGE);

        }
        return pTotal;
    }
    // -------------------------------------------------------------------------------------------------------------------------------------------------
   public int contarProductosLiquidacionesNegativas(DefaultTableModel model,String str1,String str2,String fechaSel, int filtroProvincia,int filtroAgente,int producto) {
                String str,prod="";
                int val,p1,p2,p3,p4,p5,p6,p7,p8,pTotal=0;
                  switch (producto){
                       case 0:
                           prod = "SVGCompleto";
                           break;
                       case 1:
                           prod = "SVGXpres";
                           break;
                       case 2:
                           prod = "SVGBasico";
                           break;
                       case 3:
                           prod = "SVelectricXpres";
                           break;     
                       case 4:
                           prod = "Servihogar";
                           break; 
                       case 5:
                           prod = "SVGCompletoConCalef";
                           break;
                       case 6:
                           prod = "SVGCompletoSinCalef";
                           break;
                       case 7:
                           prod = "TurGas";
                           break;
                       case 8:
                           prod = "CUPS_gas";
                           break;
                       case 9:
                           prod = "CUPS_Elect";
                           break;
                  }
                 String strquery = "";
                if (producto ==8 || producto==9) {
                     strquery = "SELECT COUNT("+prod+") FROM t_makro_residencial  WHERE Estado <> 3 AND "+prod+" LIKE '%0%'" ;
                } else {
                     strquery = "SELECT COUNT("+prod+") FROM t_makro_residencial  WHERE Estado <> 3 AND "+prod+"=1" ;
                }
            //    filtroEstado = 3 ;
                 
                strquery = strquery + " AND idIncidencia=5 AND  Fecha LIKE '"+fechaSel+"'" ;
                  
                switch (filtroProvincia){
                    
                    case 0:
                        strquery = strquery + "" ;
                        break;
                    case 1:
                        strquery = strquery + " AND  Provincia LIKE 'CASTELL%'" ;
                        break;
                    case 2:
                        strquery = strquery + " AND  Provincia LIKE 'VALENCIA'" ;            
                        break;  
                    case 3:
                        strquery = strquery + " AND  Provincia LIKE 'ALICANTE'" ;            
                        break;  
                    case 4:
                        strquery = strquery + " AND  Provincia NOT LIKE 'VALENCIA' AND Provincia NOT LIKE 'CASTELL%' " ;            
                        break;  
                   
                }     
                 switch (filtroAgente){
                    
                    case 0:
                        strquery = strquery + "" ;
                        break;
                    case 1:
                        strquery = strquery + " AND Comercial LIKE 'JOSE' OR Comercial LIKE 'J & C' " ;
                        break;
                    case 2:
                        strquery = strquery + " AND Comercial LIKE 'ETP' " ;
                        break;
                    case 3:
                        strquery = strquery + " AND Comercial LIKE 'NADINE' " ;
                        break;
                     case 4:
                        strquery = strquery + " AND Comercial LIKE 'ELANTY' " ;
                        break;
                    case 5:
                        strquery = strquery + " AND Comercial LIKE 'SERNOVEN' " ;
                        break;
                    case 6:
                        strquery = strquery + " AND Comercial LIKE 'MIGUEL' " ;
                        break;
                    case 7:
                        strquery = strquery + " AND Comercial LIKE 'SHEILA' " ;
                        break;
                 }
                
                  strquery = strquery + " ORDER BY id_m_r DESC";
                
                System.out.println("La cadena de la consulta ="+strquery);
                Conexion conex = new Conexion(str1,str2);
                 int cnt =0;
                 
             
              
        try {
            Statement estatuto = conex.getConnection().createStatement();
            ResultSet rs = estatuto.executeQuery(strquery);

            rs.next();
            pTotal = rs.getInt(1);
                    
            System.out.println("Producto "+prod+" --> "+pTotal+" registros en liquidacion");
            rs.close();
            estatuto.close();
            conex.desconectar();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al consultar", "Error",
                    JOptionPane.ERROR_MESSAGE);

        }
        return pTotal;
    }
}


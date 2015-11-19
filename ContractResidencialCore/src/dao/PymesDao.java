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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.table.TableColumn;

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
    
        public String tablaDatos[][] = new String[5000][55] ;               // Tabla  con los datos a procesar.
        public String tablaLiquidaciones[][] = new String[5000][15] ;       // Tabla  con los datos de la liquidación
        public String tablaComisionesAgente[][] = new String[15][3] ;               // Tabla  con las comisiones de residencial
        public int nRegistros = 0;
        public int nLiquida = 0;
    
	public int registrarContrato(PymesVo miPyme,String str1,String str2) {
		 
            Conexion conex = new Conexion(str1,str2);
            int estadoInsert = 0 ;
            String sqlStr;

		try {
			
                        
                        sqlStr = "INSERT INTO t_makro_residencial (Estado,IdIncidencia,Fecha,Comercial,Swg,Swe,DualFuel,CUPS_Elect,"
                                        + "CUPS_Gas,CodPostal,Municipio,Provincia,Direccion,Titular,NIF_CIF,telefono,Fecha_Firma_Cliente,"
                                        + "Consumo_elect_kwha,Consumo_gas_kwha,"
                                        + "SVGCompleto,SVGXpres,SVGBasico,SVelectricXpres,Servihogar,SPP,"
                                        + "Observaciones) VALUES ('"					
					+ miPyme.getEstado() + "', '"
                                        + miPyme.getIncidencia() + "', '"    
					+ miPyme.getFecha() + "', '"
		 			+ miPyme.getComercial() + "', '"  
                                        + miPyme.getSwg() + "', '"
                                        + miPyme.getSwe() + "', '"
                                        + miPyme.getDualFuel() + "', '"
                                        + miPyme.getCupsE() + "', '"
                                        + miPyme.getCupsG() + "', '"
                                        + miPyme.getCodPostal() + "', '"
                                        + miPyme.getMunicipio() + "', '"
                                        + miPyme.getProvincia() + "', '"
                                        + miPyme.getDireccion() + "', '"
                                        + miPyme.getTitular() + "', '"
                                        + miPyme.getNifCif() + "', '"
                                        + miPyme.getTelefonoCli() + "', '"
                                        + miPyme.getFechaFirma() + "', '"
                                        + miPyme.getConsumoElect() + "', '"
                                        + miPyme.getConsumoGas() + "', '"
                                        + miPyme.getSVG_1() + "', '"
                                        + miPyme.getSVG_2() + "', '"
                                        + miPyme.getSVG_3() + "', '"
                                        + miPyme.getSVG_4() + "', '"
                                        + miPyme.getSVG_5() + "', '"    
                                        + miPyme.getTurGas() + "', '"
                                        + miPyme.getSPP() + "', '"
                                        + miPyme.getObservaciones() + "')" ;
                        
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
	public void buscarContratos(DefaultTableModel model,String str1,String str2,int filtroEstado,int filtroFecha,int filtroProvincia,int filtroAgente, int filtroMakro,int filtroIncidencia,String fProd,String fProd2) {
                String str;
                int val;
                String strquery = "SELECT * FROM t_makro_residencial" ;
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
                        strquery = strquery + " WHERE Estado <= 6" ; 
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
                    case 7:
                        strquery = strquery + " WHERE Estado = 4" ; 
                        break; 
                    case 8:
                        strquery = strquery + " WHERE Estado = 5" ; 
                        break;    
                    case 9:
                        strquery = strquery + " WHERE Estado = 7" ;                 // decomisionado
                        break;        
                         
                }        
                  switch (filtroIncidencia){
                    
                    case 0:
                        strquery = strquery + " AND IdIncidencia = 0" ;
                        break;
                    case 1:
                        strquery = strquery + " AND IdIncidencia = 1 " ;
                        break;
                    case 2:
                        strquery = strquery + " AND IdIncidencia = 2 " ;
                        break;
                    case 3:
                        strquery = strquery + " AND IdIncidencia = 3 " ;
                        break;
                    case 4:
                        strquery = strquery + " AND IdIncidencia = 4 " ;
                         break;
                    case 5:
                        strquery = strquery + " AND IdIncidencia = 5 " ;
                        break;
                    case 6:
                        strquery = strquery + " AND IdIncidencia = 6 " ;
                        break;
                    case 7:
                        strquery = strquery + "" ;
                        break;
                 }
                if (fProd.equals("")) {
                
                switch (filtroFecha){
                    
                    case 0:
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 5 DAY) <= Fecha" ;
                        break;
                    case 1:
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 30 DAY) <= Fecha" ;
                        break;
                    case 2:
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 60 DAY) <= Fecha" ;            
                        break;  
                    case 3:
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 90 DAY) <= Fecha" ;            
                        break;  
                    case 4:
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 180 DAY) <= Fecha" ;            
                        break; 
                    case 5:                       
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 365 DAY) <= Fecha" ;            
                        break;       
                    case 6:                       
                        strquery = strquery + "" ;            
                        break;
                    case 7:                       
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 10 DAY) <= Fecha" ;            
                        break; 
                    case 8:                       
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 15 DAY) <= Fecha" ;            
                        break; 
                             
                   
                }   
                } else {
                    
                    if ( fProd2.equals("")){
                    
                      strquery = strquery + " AND  Fecha LIKE '"+fProd+" 00:00:00' ";     
                    } else {
                      strquery = strquery + " AND  Fecha >= '"+fProd+" 00:00:00' AND Fecha <='"+fProd2+" 00:00:00' ";    
                    }
                        
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
                        strquery = strquery + " AND  Provincia NOT LIKE 'VALENCIA' AND Provincia NOT LIKE 'CASTELL%' AND Provincia NOT LIKE 'ALICANTE'  " ;            
                        break;  
                   
                }     
                 switch (filtroAgente){
                    
                    case 0:
                        strquery = strquery + "" ;
                        break;
                    case 1:
                        strquery = strquery + " AND (Comercial LIKE 'JOSE' OR Comercial LIKE 'J & C') " ;
                        break;
                    case 2:
                        strquery = strquery + " AND Comercial LIKE 'ETP' " ;
                        break;
                    case 3:
                        strquery = strquery + " AND Comercial LIKE 'NADINE' " ;
                        break;
                    case 4:
                        strquery = strquery + " AND Comercial LIKE 'EMILIO-RAQUEL' " ;
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
                    case 8:
                        strquery = strquery + " AND Comercial LIKE 'MARIO SORIA' " ;
                    break;
                    case 9:
                        strquery = strquery + " AND Comercial LIKE 'ALBERTO' " ;
                    break;
                    case 10:
                        strquery = strquery + " AND Comercial LIKE 'AGENTE 01' " ;
                    break;
                    case 11:
                        strquery = strquery + " AND Comercial LIKE 'AGENTE 02' " ;
                    break;
                    case 12:
                        strquery = strquery + " AND Comercial LIKE 'AGENTE 03' " ;
                    break;
                    case 13:
                        strquery = strquery + " AND Comercial LIKE 'AGENTE 04' " ;
                    break;
                 }
                 
                switch (filtroMakro){
                    
                    case 0:
                        strquery = strquery + " ORDER BY id_m_r ASC" ;
                        break;
                    case 1:
                        strquery = strquery + " ORDER BY Titular " ;
                        break;
                    case 2:
                        strquery = strquery + " ORDER BY Municipio " ;            
                        break;  
                    case 3:
                        strquery = strquery + " ORDER BY id_m_r DESC" ;            
                        break;  
                    case 4:
                        strquery = strquery + " ORDER BY NIF_CIF DESC" ;            
                        break;  
                  
                   
                }     
                
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
				Object[] fila = new Object[41];
				// para llenar cada columna con lo datos almacenados
				for (int i = 0; i < 41; i++) 
					fila[i] = rs.getObject(i + 1);          // es para cargar los datos en filas a la tabla modelo
                                       
                                        try {
                                            fila[2]  = formatDateJava.format(rs.getDate(3)) ;      // fecha memo ; 
                                            
                                        } catch (NullPointerException nfe){
                                            fila[2]  = ""; 
                                        }
                              
                                        fila[4]  = formatDateJava.format(rs.getDate(5)) ; 
                                        fila[19] = formatDateJava.format(rs.getDate(20)) ; 
                                        
                                        //...........................................................
                                      
                                        tablaDatos[cnt][30] = Integer.toString(rs.getInt(1)) ;      //  Id_m_r
                                        tablaDatos[cnt][32] = "-1" ;                                // Id última Locucion
                                        //........................................................... Aqui cargamos en la tabla de datos de formulario
                                        tablaDatos[cnt][0] = Integer.toString(rs.getInt(2)); // System.out.println(cnt+"-Estado="+tablaDatos[cnt][0]+" ID_C="+tablaDatos[cnt][30] );
                                                                              
                                        tablaDatos[cnt][1] = Integer.toString(rs.getInt(4)); 
                                        tablaDatos[cnt][2] = formatDateJava.format(rs.getDate(5)) ;  
                                      
                                       
                                        
                                        tablaDatos[cnt][3] = rs.getString(11) ;  // CUPS gas
                                        tablaDatos[cnt][4] = rs.getString(12) ;  // CUPS electrico
                                        
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
                                        
                                        tablaDatos[cnt][33] = "0";
                                        val = rs.getInt(8); if ( val==1 ) {tablaDatos[cnt][33] = "1" ; }        // Contrato Swg
                                        val = rs.getInt(9); if ( val==1 ) {tablaDatos[cnt][33] = "2" ; }        // Contrato Swe
                                        val = rs.getInt(10); if ( val==1 ) {tablaDatos[cnt][33] = "3" ; }        // Contrato DualFuel
                                                                                                                                                         
                                        try {
                                            tablaDatos[cnt][39] = formatDateJava.format(rs.getDate(3));      // fecha memo ; 
                                            
                                        } catch (NullPointerException nfe){
                                            tablaDatos[cnt][39] = ""; 
                                        }
                                        tablaDatos[cnt][40] = "-1";                                             // ID certificación
                                        tablaDatos[cnt][41] = rs.getString(35) ;                                // Agente  Comercial
                                        System.out.println(" tablaDatos[cnt][41] ="+ tablaDatos[cnt][41] );
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
			
                        
                        sqlStr = "UPDATE  t_makro_residencial SET "					
					+ "Estado = "+ miPyme.getEstado() + ", "
                                        + "IdIncidencia = "+miPyme.getIncidencia() + ", "    
					+ "Fecha = '"+miPyme.getFecha() + "',"
		 			+ "Comercial = '"+miPyme.getComercial() + "', "  
                                        + "Swg = "+miPyme.getSwg() + ", "
                                        + "Swe = "+miPyme.getSwe() + ", "
                                        + "DualFuel = "+miPyme.getDualFuel() + ", "
                                        + "CUPS_Elect = '"+miPyme.getCupsE() + "', "
                                        + "CUPS_Gas='"+miPyme.getCupsG() + "', "
                                        + "CodPostal = "+miPyme.getCodPostal() + ", "
                                        + "Municipio='"+miPyme.getMunicipio() + "', "
                                        + "Provincia='"+miPyme.getProvincia() + "', "
                                        + "Direccion='"+miPyme.getDireccion() + "', "
                                        + "Titular='"+miPyme.getTitular() + "', "
                                        + "NIF_CIF='"+miPyme.getNifCif() + "', "
                                        + "telefono='"+miPyme.getTelefonoCli() + "', "
                                        + "Fecha_Firma_Cliente='"+miPyme.getFechaFirma() + "', "
                                        + "Consumo_elect_kwha="+miPyme.getConsumoElect() + ", "
                                        + "Consumo_gas_kwha="+miPyme.getConsumoGas() + ", "
                                        + "SVGCompleto ="+miPyme.getSVG_1() + ", "
                                        + "SVGXpres = "+miPyme.getSVG_2() + ", "
                                        + "SVGBasico = "+miPyme.getSVG_3() + ", "
                                        + "SVelectricXpres = "+miPyme.getSVG_4() + ", "
                                        + "Servihogar = "+miPyme.getSVG_5() + ", "                                       
                                        + "Observaciones ='"+miPyme.getObservaciones() + "', " 
                                        + "Incidencia ='"+miPyme.getsIncidencia() + "', " 
                                        + "Explicacion ='"+miPyme.getsExplicacion() + "', "
                                        + "Tarifa_Gas ='"+miPyme.getTarifaGas() + "', " 
                                        + "Tarifa_Elec ='"+miPyme.getTarifaElec() + "', "    
                                        + "TurGas ='"+miPyme.getTurGas() + "', "  
                                        + "Punteado ='"+miPyme.getPunteado() + "', "  
                                        + "AgenteComercial ='"+miPyme.getAgenteCom() + "', "  
                                        + "SVGCompletoConCalef ='"+miPyme.getSVG_6()+ "', "  
                                        + "SVGCompletoSinCalef ='"+miPyme.getSVG_7() + "', "  
                                        + "TarifaPlana ='"+miPyme.getTarifaPlana() + "', " 
                                        + "SPP ='"+miPyme.getSPP() + "', " 
                                        + strMemo 
                                        + "WHERE id_m_r ="+miPyme.getIdContrato();
                        
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
  public int tablaLiquidaciones(DefaultTableModel model,String str1,String str2,String fechaSel, int filtroProvincia,int filtroAgente,String fechaSel2) {
                String str;
                int val,p1,p2,p3,p4,p5,p6,p7,p8,pTotal=0;
                int nReg=0;
                
                String strquery = "SELECT idIncidencia,TurGas,CUPS_gas,CUPS_Elect,Titular,NIF_CIF,SVGCompleto,SVGXpres,SVGBasico,SVelectricXpres,Servihogar,SVGCompletoConCalef,SVGCompletoSinCalef,SPP,TarifaPlana,Observaciones FROM t_makro_residencial  WHERE (Estado=0 OR ESTADO=1 OR Estado=3 OR Estado=6) " ;
            //    filtroEstado = 3 ;
                 
                 
                if (!fechaSel2.equals("")) {
                    
                   strquery = strquery + " AND (Incidencia=0 OR Incidencia=4  OR Incidencia=5 OR Incidencia=3 OR Incidencia=1 OR Incidencia=2) AND  Fecha >= '"+fechaSel+"' AND Fecha <='"+fechaSel2+"'";
                
                } else {
                    
                   strquery = strquery + " AND (idIncidencia=0 OR idIncidencia=4  OR idIncidencia=5 OR idIncidencia=3 OR idIncidencia=1 OR idIncidencia=2 ) AND  Fecha LIKE '"+fechaSel+"'" ;
                
                }
                
                
                
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
                        strquery = strquery + " AND Comercial LIKE 'EMILIO-RAQUEL' " ;
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
                    case 8:
                        strquery = strquery + " AND Comercial LIKE 'MARIO SORIA' " ;
                    break;
                    case 9:
                        strquery = strquery + " AND Comercial LIKE 'ALBERTO' " ;
                    break;
                    case 10:
                        strquery = strquery + " AND Comercial LIKE 'AGENTE 01' " ;
                    break;
                    case 11:
                        strquery = strquery + " AND Comercial LIKE 'AGENTE 02' " ;
                    break;
                    case 12:
                        strquery = strquery + " AND Comercial LIKE 'AGENTE 03' " ;
                    break;
                    case 13:
                        strquery = strquery + " AND Comercial LIKE 'AGENTE 04' " ;
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
   public int contarProductosLiquidaciones(DefaultTableModel model,String str1,String str2,String fechaSel, int filtroProvincia,int filtroAgente,int producto,String fechaSel2) {
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
                 if (!fechaSel2.equals("")) {
                     strquery = strquery + " AND (idIncidencia=0 OR idIncidencia=4  ) AND  Fecha >= '"+fechaSel+"' AND  Fecha <= '"+fechaSel2+"'" ;
                 } else {
                     strquery = strquery + " AND (idIncidencia=0 OR idIncidencia=4  ) AND  Fecha LIKE '"+fechaSel+"'" ;
                 }
                  
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
                        strquery = strquery + " AND Comercial LIKE 'EMILIO-RAQUEL' " ;
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
                    case 8:
                        strquery = strquery + " AND Comercial LIKE 'MARIO SORIA' " ;
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
   public int contarProductosLiquidacionesNegativas(DefaultTableModel model,String str1,String str2,String fechaSel, int filtroProvincia,int filtroAgente,int producto, String fechaSel2) {
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
                     strquery = "SELECT COUNT("+prod+") FROM t_makro_residencial  WHERE Estado = 7 AND "+prod+" LIKE '%0%'" ;
                } else {
                     strquery = "SELECT COUNT("+prod+") FROM t_makro_residencial  WHERE Estado = 7 AND "+prod+"=1" ;
                }
            //    filtroEstado = 3 ;
                 if (!fechaSel2.equals("")) {
                     strquery = strquery + " AND (idIncidencia=0 OR idIncidencia=4  ) AND  Fecha >= '"+fechaSel+"' AND  Fecha <= '"+fechaSel2+"'" ;
                 } else { 
                     strquery = strquery + " AND idIncidencia=5 AND  Fecha LIKE '"+fechaSel+"'" ;
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
                        strquery = strquery + " AND Comercial LIKE 'JOSE' OR Comercial LIKE 'J & C' " ;
                        break;
                    case 2:
                        strquery = strquery + " AND Comercial LIKE 'ETP' " ;
                        break;
                    case 3:
                        strquery = strquery + " AND Comercial LIKE 'NADINE' " ;
                        break;
                     case 4:
                        strquery = strquery + " AND Comercial LIKE 'EMILIO-RAQUEL' " ;
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
                    case 8:
                        strquery = strquery + " AND Comercial LIKE 'MARIO SORIA' " ;
                    break;
                    case 9:
                        strquery = strquery + " AND Comercial LIKE 'ALBERTO' " ;
                    break;
                    case 10:
                        strquery = strquery + " AND Comercial LIKE 'AGENTE 01' " ;
                    break;
                    case 11:
                        strquery = strquery + " AND Comercial LIKE 'AGENTE 02' " ;
                    break;
                    case 12:
                        strquery = strquery + " AND Comercial LIKE 'AGENTE 03' " ;
                    break;
                    case 13:
                        strquery = strquery + " AND Comercial LIKE 'AGENTE 04' " ;
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
   // ---------------------------------------------------------------------------------------------------------------------------------------
   
   public int compruebaLocucion(DefaultTableModel model,String str1,String str2,String nif,String telefono,String direccion,String titular) {

                String sqlStr;
                int res = 0 ;
                SimpleDateFormat formatDateJava = new SimpleDateFormat("dd-MM-yyyy");
                
                sqlStr  = "SELECT * FROM t_locuciones_residencial  WHERE ";
                sqlStr += "titular LIKE '%"+nif+"%'" ;
                if (!telefono.equals("") )
                sqlStr +="OR telefono LIKE '%"+telefono+"%'" ;
                
                sqlStr += "OR direccion LIKE '%"+direccion+"%' OR titular LIKE '%"+titular+"%'" ;
                sqlStr += "ORDER BY id DESC" ;
                
                 System.out.println("sqlstr="+sqlStr);
                
		 Conexion conex = new Conexion(str1,str2);
		try {
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery(sqlStr);
                        
                         int contador = 0;

                        while (rs.next()) {

                            contador++;

                        }
                         System.out.println("Busco LOCUCIONES, resultado contador ="+contador);
                         int cnt = 0 ;
                         int marca = 0 ;
                        if (contador >0){
                                                       
                            rs.beforeFirst();
                           
                            while (rs.next()) {

                                    Object[] fila = new Object[25];
                              
                                         // para llenar cada columna con lo datos almacenados
                                  
                                            fila[0] = rs.getObject("id");                                    // ID
                                            fila[1] = rs.getObject("id_tipo");                               // IDT
                                            
                                            try {
                                                fila[2]  = formatDateJava.format(rs.getDate("fecha")) ;      // fecha  ; 

                                            } catch (NullPointerException nfe){
                                                fila[2]  = ""; 
                                            }

                                            fila[3]  = rs.getObject("titular");               // TITULAR
                                            fila[4]  = rs.getObject("verifica");              // Verifica
                                            fila[5]  = rs.getObject("direccion");             // Direccion
                                            fila[6]  = rs.getObject("contrato");              // Contrato
                                            fila[7]  = rs.getObject("llam1");                 // Llamada 1
                                            fila[8]  = rs.getObject("llam2");                 // Llamada 2
                                            fila[9]  = rs.getObject("llam3");                 // Llamada 3
                                            fila[10] = rs.getObject("llam4");                 // Llamada 4
                                            fila[11] = rs.getObject("llam5");                 // Llamada 5
                                            fila[12] = rs.getObject("llam6");                 // Llamada 6                                                                                   
                                            fila[13] = rs.getObject("telefono");              // telefono  
                                            fila[14] = rs.getObject("fechaNac");              // fechaNac     
                                            fila[15] = rs.getObject("apoderadoPyme");         // ApoderadoPyme     
                                            fila[16] = rs.getObject("facturas");              // Facturas     
                                            fila[17] = rs.getObject("tratoAcre");              // TratoAcre     
                                            fila[18] = rs.getObject("informado");              // Informado    
                                            fila[19] = rs.getObject("cuenta");                // Cuenta    
                                            fila[20] = rs.getObject("copia");                 // Copia    
                                            fila[21] = rs.getObject("observaciones");         // observaciones    
                                            fila[22] = rs.getObject("comercial");             // comercial    
                                            fila[23] = rs.getObject("hora");                  // hora
                                            fila[24] = rs.getObject("precios");               // precios
                                            
                                    model.addRow(fila);
                                    cnt++;
                            }
                        } else {
                             System.out.println("No he encontrado ninguna locucion");
                             res = 1 ;
                            
                        }
			rs.close();
			estatuto.close();
			conex.desconectar();
                       
                            if (res == 0) {
                                if (marca == 1) {
                                    res = 0 ;        
                                   
                                } else {
                                    System.out.println("No he encontrado nada");
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
   // ---------------------------------------------------------------------------------------------------------------------------------------

   public int compruebaCertificacion(DefaultTableModel model,String str1,String str2,String nif,String direccion,String cupsGas,String cupsEle) {

                String sqlStr;
                int res = 0 ;
                SimpleDateFormat formatDateJava = new SimpleDateFormat("dd-MM-yyyy");
                
                sqlStr  = "SELECT * FROM t_tabla_certificaciones_nominal  WHERE ";
                sqlStr += "nif LIKE '%"+nif+"%' " ;
                sqlStr += "OR calle LIKE '%"+direccion+"%' ";
                
                if ( !cupsGas.equals(""))  sqlStr += "OR CUPS_Gas LIKE '%"+cupsGas+"%' ";                
                if ( !cupsEle.equals(""))  sqlStr += "OR CUPS_Elect LIKE '%"+cupsEle+"%'" ;
                
                sqlStr += "ORDER BY id DESC" ;
                
                 System.out.println("sqlstr="+sqlStr);
                
		 Conexion conex = new Conexion(str1,str2);
		try {
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery(sqlStr);
                        
                         int contador = 0;

                        while (rs.next()) {

                            contador++;

                        }
                         System.out.println("Busco CERTIFICACIONES, resultado contador ="+contador);
                         int cnt = 0 ;
                         int marca = 0 ;
                         if (contador >0){
                                                       
                            rs.beforeFirst();
                           
                            while (rs.next()) {

                                    Object[] fila = new Object[23];
                                    // para llenar cada columna con lo datos almacenados
                                    for (int i = 0; i < 23; i++) 
                                            fila[i] = rs.getObject(i + 1);          // es para cargar los datos en filas a la tabla modelo

                                            try {
                                                fila[9]  = formatDateJava.format(rs.getDate(10)) ;      // fecha  ; 
                                                fila[10]  = formatDateJava.format(rs.getDate(11)) ;      // fecha recepción ; 

                                            } catch (NullPointerException nfe){
                                                fila[9]  = ""; 
                                                fila[10]  = ""; 
                                            }

                                    model.addRow(fila);
                                    cnt++;
                            }
                        } else {
                             System.out.println("No he encontrado ninguna CERTIFICACIÓN");
                             res = 1 ;
                            
                        }
			rs.close();
			estatuto.close();
			conex.desconectar();
                       
                            if (res == 0) {
                                if (marca == 1) {
                                    res = 0 ;        
                                   
                                } else {
                                    System.out.println("No he encontrado nada");
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
   // ------------------------------------------------------------------------------------------------------------------
  	public int consultaTablaComisionesResidencial(String str1,String str2,int idcomercial) {
		 
            Conexion conex = new Conexion(str1,str2);
            int estadoInsert = 0, ntot=0;
            
            String sqlStr ="SELECT id_producto,comision,descripcion";
                   sqlStr +=" FROM v_lista_comisiones_agente_producto WHERE id_comercial="+idcomercial+"  ORDER BY id_producto ASC";
       //      System.out.println("sqlstr ="+sqlStr); 
            
            try {
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery(sqlStr);

                         int contador = 0;

                        while (rs.next()) {

                            contador++;

                        }
                        ntot = contador ;
                                                
                        if (contador >0){
                           
                            rs.beforeFirst();
                            contador = 0;
                            while (rs.next()) {
                                   
                                    // para llenar cada columna con lo datos almacenados
                                      
                                        this.tablaComisionesAgente[contador][0] = String.valueOf(rs.getInt("id_producto"));
                                        this.tablaComisionesAgente[contador][1] = rs.getString("descripcion");
                                        this.tablaComisionesAgente[contador][2] = String.valueOf(redondear(rs.getDouble("comision"),2));  
                                        
                                    contador++;
                                   
                            }
                        } 
			rs.close();
			estatuto.close();
			conex.desconectar();
                        System.out.println("He cargado "+contador+" datos del comisiones");             

		} catch (SQLException e) {
			System.out.println(e.getMessage());
                        //                JOptionPane.showMessageDialog(null, "Error al consultar", "Error",JOptionPane.ERROR_MESSAGE);

		}
                return ntot;
		
	}
        // ------------------------------------------------------------------------------------------------------------------
  	public int consultaTablaIngresosResidencial(String str1,String str2) {
		 
            Conexion conex = new Conexion(str1,str2);
            int estadoInsert = 0, ntot=0;
            
            String sqlStr ="SELECT id_producto,comision,descripcion";
                   sqlStr +=" FROM v_lista_ingresos_producto ORDER BY id_producto ASC";
       //      System.out.println("sqlstr ="+sqlStr); 
            
            try {
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery(sqlStr);

                         int contador = 0;

                        while (rs.next()) {

                            contador++;

                        }
                        ntot = contador ;
                                                
                        if (contador >0){
                           
                            rs.beforeFirst();
                            contador = 0;
                            while (rs.next()) {
                                   
                                    // para llenar cada columna con lo datos almacenados
                                      
                                        this.tablaComisionesAgente[contador][0] = String.valueOf(rs.getInt("id_producto"));
                                        this.tablaComisionesAgente[contador][1] = rs.getString("descripcion");
                                        this.tablaComisionesAgente[contador][2] = String.valueOf(redondear(rs.getDouble("comision"),2));  
                                        
                                        System.out.println("Descripción:"+this.tablaComisionesAgente[contador][1]+" comision="+this.tablaComisionesAgente[contador][2]);
                                        
                                    contador++;
                                   
                            }
                        } 
			rs.close();
			estatuto.close();
			conex.desconectar();
                        System.out.println("He cargado "+contador+" datos de ingresos por certificaciones");             

		} catch (SQLException e) {
			System.out.println(e.getMessage());
                        //                JOptionPane.showMessageDialog(null, "Error al consultar", "Error",JOptionPane.ERROR_MESSAGE);

		}
                return ntot;
		
	}
        // -----------------------------------------------------------------------------------
        
        public double redondear( double numero, int decimales ) {
         return Math.round(numero*Math.pow(10,decimales))/Math.pow(10,decimales);
       }
         // -------------------------------------------------------------------------------------------------------------------------------------------------
        public int tablaCertificaciones(DefaultTableModel model,String str1,String str2,String fechaSelDesde,String fechaSelHasta) {
                String str;
                int val,p1,p2,p3,p4,p5,p6,p7,p8,pTotal=0;
                int nReg=0;
                
                String strquery = "SELECT idIncidencia,TurGas,CUPS_gas,CUPS_Elect,Titular,NIF_CIF,SVGCompleto,SVGXpres,SVGBasico,SVelectricXpres,Servihogar,SVGCompletoConCalef,SVGCompletoSinCalef,SPP,TarifaPlana,Observaciones FROM t_makro_residencial  WHERE (Estado=0 OR ESTADO=1 OR Estado=3 OR Estado=6) " ;
            //    filtroEstado = 3 ;
                 
                strquery = strquery + " AND (idIncidencia=0 OR idIncidencia=4  OR idIncidencia=5 OR idIncidencia=3 OR idIncidencia=1 OR idIncidencia=2 ) AND  Fecha >= '"+fechaSelDesde+"' AND  Fecha <= '"+fechaSelHasta+"'" ;
                  
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
   public int contarProductosCertificaciones(String str1,String str2,String fechaSelDesde,String fechaSelHasta,int producto) {
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
                     strquery = "SELECT COUNT("+prod+") FROM t_makro_residencial  WHERE (Estado = 0 OR Estado= 1 OR Estado=2 ) AND "+prod+" LIKE '%0%'" ;
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
                 
                strquery = strquery + " AND (idIncidencia=0 OR idIncidencia=4  ) AND  Fecha >= '"+fechaSelDesde+"'  AND  Fecha <= '"+fechaSelHasta+"'" ;
                  
                strquery = strquery + " ORDER BY id_m_r DESC";
                
                System.out.println("La cadena de la consulta ="+strquery);
                Conexion conex = new Conexion(str1,str2);
                 int cnt =0;
                 
             
              
        try {
            Statement estatuto = conex.getConnection().createStatement();
            ResultSet rs = estatuto.executeQuery(strquery);

            rs.next();
            pTotal = rs.getInt(1);
                    
            System.out.println("Producto "+prod+" --> "+pTotal+" registros en certificacion");
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
       // ------------------------------------------------------------------------------------------------------------------------
}


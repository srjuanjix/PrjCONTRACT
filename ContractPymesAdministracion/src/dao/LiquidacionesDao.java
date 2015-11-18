/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

import vo.LiquidacionesVo;

import conexion.Conexion;
import java.text.SimpleDateFormat;

/**
 * Clase que permite el acceso a la base de datos
 * 
 * @author chenao
 * 
 */
public class LiquidacionesDao {
 
	/**
	 * Usa el objeto enviado para almacenar la persona
	 * @param miPyme
	 */
    
        public String tablaDatos[][]        = new String[5000][50] ;    // Tabla  con los datos a procesar.
        public String tablaLiquidaciones[][] = new String[5000][15] ;    // Tabla  con los datos de la liquidación
        public int nRegistros = 0;
        public int nLiquida = 0;
    
	public int registrarLiquidacion(LiquidacionesVo miLiquida,String str1,String str2) {
		 
            Conexion conex = new Conexion(str1,str2);
            int estadoInsert = 0 ;
            String sqlStr;

		try {
			
                        sqlStr = "INSERT INTO t_liquidaciones_validadas (fecha,importe,comercial,zona,ncertificacion)"
                                       
                                        + " VALUES ('"					
					+ miLiquida.getFecha() + "', '"
                                        + miLiquida.getImporte() + "', '"    
					+ miLiquida.getComercial() + "', '"
		 			+ miLiquida.getZona() + "', '"  
                                        + miLiquida.getnCertificacion() + "') " ;
                                                               
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
	public void buscarLiquidaciones(DefaultTableModel model,String str1,String str2,int filtroEstado,int filtroFecha,int filtroProvincia,int filtroAgente, int filtroMakro,int filtroIncidencia,String fProd) {
                String str;
                int val;
                String strquery = "SELECT * FROM t_makro_pymes" ;
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
                      strquery = strquery + " AND  Fecha LIKE '"+fProd+" 00:00:00' ";                    
                }
                switch (filtroProvincia){
                    
                    case 0:
                        strquery = strquery + "" ;
                        break;
                    case 1:
                        strquery = strquery + " AND  Provincia LIKE 'CASTELL%'" ;
                        break;
                    case 2:
                        strquery = strquery + " AND ( Provincia LIKE 'VALENCIA' OR Provincia NOT LIKE 'CASTELL%') " ;            
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
				Object[] fila = new Object[40];
				// para llenar cada columna con lo datos almacenados
				for (int i = 0; i < 40; i++) 
					fila[i] = rs.getObject(i + 1);          // es para cargar los datos en filas a la tabla modelo
                                       
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
                   
                
                                        tablaDatos[cnt][15]= rs.getString(33); // observaciones.toUpperCase();   
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
 
}
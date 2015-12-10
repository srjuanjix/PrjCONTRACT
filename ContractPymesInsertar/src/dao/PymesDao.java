package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import vo.PymesVo;

import conexion.Conexion;

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
//	public int registrarContrato(PymesVo miPyme,String str1,String str2) {
    public int registrarContrato(PymesVo miPyme) {
		 
            Conexion conex = new Conexion("","");
            int estadoInsert = 0 ;
            String sqlStr;

		try {
			
                        
                        sqlStr = "INSERT INTO t_makro_pymes (Estado,Fecha_docout,Memo,Incidencia,Orden,CUPS_Elect,CUPS_Gas,Agente,CV,CodPostal,Municipio,Provincia,"
                                        + "Direccion,Titular,NIF_CIF,Fecha_Firma_Cliente,Consumo_elect_kwha,Consumo_elect_kwha_websale,Consumo_gas_kwha,Pagado,P_Fenosa,P_Fenosa_Total,Tarifa,Campaña,Oferta,"
                                        + "Telefono_Cli,Per_Contacto,Empresa_Origen,Explicacion,Solucion,Observaciones,Tarifa_gas,C_servicios,Planes,Directriz,Reactiva)"
                                        + " VALUES ('"					
					+ miPyme.getEstado() + "', '"
                                        + miPyme.getFechaDocout() + "', '"
                                        + miPyme.getFechaMemo() + "', '"
                                        + miPyme.getIncidencia() + "', '"    
					+ miPyme.getFechaOrden() + "', '"
                                        + miPyme.getCupsE() + "', '"
                                        + miPyme.getCupsG() + "', '"
                                        + miPyme.getAgente() + "', '" 
                                        + miPyme.getCVComercial()+ "', '"
		 			+ miPyme.getCodPostal() + "', '"
                                        + miPyme.getMunicipio() + "', '"
                                        + miPyme.getProvincia() + "', '"
                                        + miPyme.getDireccion() + "', '"
                                        + miPyme.getTitular() + "', '"
                                        + miPyme.getNifCif() + "', '" 
                                        + miPyme.getFechaFirma() + "', '"                                       
                                        + miPyme.getConsumoElect() + "', '"
                                        + miPyme.getConsumoElectWS() + "', '"
                                        + miPyme.getConsumoGas() + "', '"
                                        + miPyme.getPagado() + "', '"
                                        + miPyme.getPFenosa() + "', '"
                                        + miPyme.getPagadoFenosa() + "', '"
                                        + miPyme.getTarifa() + "', '"
                                        + miPyme.getCampaña() + "', '" 
                                        + miPyme.getOferta() + "', '"        
                                        + miPyme.getTelefonoCli() + "', '"
                                        + miPyme.getPerContacto() + "', '"
                                        + miPyme.getEmpresaOrigen() + "', '"
                                        + miPyme.getsExplicacion() + "', '"
                                        + miPyme.getsIncidencia() + "', '"
                                        + miPyme.getObservaciones() + "', '"
                                        + miPyme.getTarifaGas() + "', '"
                                        + miPyme.getCServicios() + "', "
                                        + miPyme.getPlanes()+ ","
                                        + miPyme.getDirectriz()+ ",'"
                                        + miPyme.getReactiva() + "')" ;
                        
			System.out.println(sqlStr);
                        Statement estatuto = conex.getConnection().createStatement();
			estatuto.executeUpdate(sqlStr);
			estatuto.close();
		//	conex.desconectar();
                       

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
	public void buscarContratos(DefaultTableModel model,String str1,String str2,int filtroEstado,int filtroFecha) {

                String strquery = "SELECT * FROM t_makro_pymes" ;
                
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
                }        
                
                switch (filtroFecha){
                    
                    case 0:
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 7 DAY) <= Fecha" ;
                        break;
                    case 1:
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 30 DAY) <= Fecha" ;
                        break;
                    case 2:
                        strquery = strquery + " AND  DATE_SUB(CURDATE(),INTERVAL 365 DAY) <= Fecha" ;            
                        break;                   
                   
                }      
                System.out.println("La cadena de la consulta ="+strquery);
		 Conexion conex = new Conexion(str1,str2);
		try {
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery(strquery);

			while (rs.next()) {
				// es para obtener los datos y almacenar las filas
				Object[] fila = new Object[26];
				// para llenar cada columna con lo datos almacenados
				for (int i = 0; i < 25; i++)
					fila[i] = rs.getObject(i + 1);
				// es para cargar los datos en filas a la tabla modelo
				model.addRow(fila);

                              
                                
			}
			rs.close();
			estatuto.close();
			conex.desconectar();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error al consultar", "Error",
					JOptionPane.ERROR_MESSAGE);

		}
	}
 public String buscarCodPost(Conexion conex,String CodPost) {

               
                String resMunicipio="" ;
              
             
                int res = 0 ;
                
		 
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
			
                                               

		} catch (SQLException e) {
			System.out.println(e.getMessage());
                        //                JOptionPane.showMessageDialog(null, "Error al consultar", "Error",JOptionPane.ERROR_MESSAGE);

		}
                return resMunicipio;
}
 public int compruebaCodPost(Conexion conex,String CodPost,String municipio) {

                String resMunis[] = new String[100];
                String resMunicipio="" ;
                int res = 0 ;
                
		
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
	
}


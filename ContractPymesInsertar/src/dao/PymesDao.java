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
    public int registrarContrato(PymesVo miPyme,Conexion conex) {
		 
     //       Conexion conex = new Conexion(str1,str2);
            int estadoInsert = 0 ;
            String sqlStr;

		try {
			
                        
                        sqlStr = "INSERT INTO t_makro_residencial (Estado,IdIncidencia,Fecha,Comercial,Swg,Swe,DualFuel,CUPS_Elect,"
                                        + "CUPS_Gas,CodPostal,Municipio,Provincia,Direccion,Titular,NIF_CIF,telefono,Fecha_Firma_Cliente,"
                                        + "Consumo_elect_kwha,Consumo_gas_kwha,"
                                        + "SVGCompleto,SVGXpres,SVGBasico,SVelectricXpres,Servihogar,"
                                        + "Tarifa_Gas,Tarifa_Elec,AgenteComercial,SVGCompletoConCalef,SVGCompletoSinCalef,SPP,TurGas,TarifaPlana,Punteado,Observaciones) VALUES ('"					
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
                                        + miPyme.getTarifaGas() + "', '"   
                                        + miPyme.getTarifaElec() + "', '"   
                                        + miPyme.getAgenteComercial() + "', '" 
                                        + miPyme.getSVG_6() + "', '"
                                        + miPyme.getSVG_7() + "', '"
                                        + miPyme.getSVG_9() + "', '"
                                        + miPyme.getTurGas() + "', '"
                                        + miPyme.getTarifaPlana() + "', '"
                                        + miPyme.getPunteado() + "', '"
                                        + miPyme.getObservaciones() + "')" ;
                        
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

                String strquery = "SELECT * FROM T_MAKRO_RESIDENCIAL" ;
                
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
	/**
	 * Retorna una lista con los datos de la BD, para luego ser 
	 * recorrida y almacenada en la tabla por medio de la Matriz
	 * @return
	 */
        /*
	public ArrayList<PersonaVo> buscarUsuariosConMatriz(String str1,String str2) {

		Conexion conex = new Conexion(str1,str2);
		ArrayList<PersonaVo> miLista = new ArrayList<PersonaVo>();
		PersonaVo persona;
		try {
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery("SELECT * FROM persona ");

			while (rs.next()) {
				persona = new PersonaVo();
				persona.setIdPersona(Integer.parseInt(rs.getString("id")));
				persona.setNombrePersona(rs.getString("nombre"));
				persona.setEdadPersona(Integer.parseInt(rs.getString("edad")));
				persona.setProfesionPersona(rs.getString("profesion"));
				persona.setTelefonoPersona(Integer.parseInt(rs
						.getString("telefono")));
				miLista.add(persona);
			}
			rs.close();
			estatuto.close();
			conex.desconectar();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error al consultar", "Error",
					JOptionPane.ERROR_MESSAGE);

		}
		return miLista;
	}
        */
}


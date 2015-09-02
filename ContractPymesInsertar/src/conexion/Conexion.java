package conexion;

import java.sql.*;
import javax.swing.JOptionPane;


/**
 * Clase que permite conectar con la base de datos
 * @author jalbarracin
 *
 */
public class Conexion {
   static String bd = "sernovenDB"; 
   static String login = "";
   static String password = "";
   static String url = "jdbc:mysql://192.168.1.103/"+bd;

   Connection conn = null;

   /** Constructor de DbConnection */
   public Conexion(String Str1,String Str2) {

      try{            
          
         login      =  Str1;
         password   =  Str2;
          
         //obtenemos el driver de para mysql
         Class.forName("com.mysql.jdbc.Driver");
         //obtenemos la conexión
         conn = DriverManager.getConnection(url,login,password);

     /*    if (conn!=null){
            JOptionPane.showMessageDialog(null,
		 "La base de datos se ha conectado exitosamente",
		 "Información",JOptionPane.INFORMATION_MESSAGE);
         } */
      }
      catch(SQLException e){
         System.out.println(e);
      }catch(ClassNotFoundException e){
         System.out.println(e);
      }catch(Exception e){
         System.out.println(e);
      }
   }
   /**Permite retornar la conexión*/
   public Connection getConnection(){
      return conn;
   }

   public void desconectar(){
      conn = null;
   }

}
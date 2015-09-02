/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ventana;

import java.awt.Container;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class ConexionBD extends JDialog{
private final Container contenedor;
JLabel labelTitulo;
 
public ConexionBD(ClaseFrame miVentanaConexion, boolean modal){
 /**Al llamar al constructor super(), le enviamos el
  * JFrame Padre y la propiedad booleana que determina
  * que es hija*/
  super(miVentanaConexion, modal);
  contenedor=getContentPane();
  contenedor.setLayout(null);
  //Asigna un titulo a la barra de titulo
  setTitle("SernovenBD: Ventana de conexión");
  
  labelTitulo= new JLabel();
  labelTitulo.setText("VENTANA DE CONEXION");
  labelTitulo.setBounds(20, 20, 180, 23);

  contenedor.add(labelTitulo);
  //tamaño de la ventana
  setSize(450,350);
  //pone la ventana en el Centro de la pantalla
  setLocationRelativeTo(null);
 }
}
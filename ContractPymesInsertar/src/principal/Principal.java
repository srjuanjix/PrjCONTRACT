package principal;

import javax.swing.UnsupportedLookAndFeelException;
import ventana.ClaseFrame;

public class Principal {

	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, UnsupportedLookAndFeelException{
		ClaseFrame miVentana = new ClaseFrame();
                /**Declaramos el objeto*/
                ClaseFrame miVentanaPrincipal;
                                /**Enviamos el objeto como parametro para que sea unico
                 * en toda la aplicación*/
                 miVentana.setVentanaPrincipal(miVentana);
		miVentana.setVisible(true);
	}
}

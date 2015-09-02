
package ventana;

/**
 *
 * @author jab7b7
 */

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

//Implementa la interfaz TableCellRenderer que servirá para dar formato a la tabla
public class FormatoTabla implements TableCellRenderer{
    
    int id;
    
    //Sobreescribimos el método getTableCellRendererComponent para poder formatear los componentes
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        
  //      System.out.println("--------- ESTOY CAMBIANDO FORMATO TABLA ------------ row="+row+" y column="+column);
        
        //campoTexto nos sirve como componente dentro de la tabla, es cada una de las celdas de la misma
        JFormattedTextField campoTexto = new JFormattedTextField();
        //Se establece primeramente un borde vacío o sin borde
        campoTexto.setBorder(BorderFactory.createEmptyBorder());
        
        //Si el valor que contiene actualmente es cadena se establece el valor sin formatear en ningún sentido
      
        if(value instanceof String){
   //         System.out.println("String value = " + value);
            campoTexto.setText((String)value);
            
    //         campoTexto.setBackground(new Color(0x33CC33)); 
             campoTexto.setOpaque(true); 
            
        }
        
        //Si el valor que contiene actualmente es entero se establece el valor y se alinea al centro 
        if(value instanceof Integer){
     //       System.out.println("Integer value = " + value);
            campoTexto.setText(value+"");
            campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
        }
        //Si el valor que contiene actualmente es Double se establece el valor
        //y se le aplica un formato para agregar comas cada tres dítigos
        if(value instanceof Double){
       //     System.out.println("Double value = " + value);
            Double valor = (Double)value;
            campoTexto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00")))); 
            //Se aplica alineación a la izquierda
            campoTexto.setHorizontalAlignment(SwingConstants.TRAILING); 
            campoTexto.setValue(valor);
            
            //Si el valor es negativo se elimina el símbolo - y se establece un fondo de color rojo
            if(valor.compareTo(new Double(0))==-1) { 
              campoTexto.setText(campoTexto.getText().replace("-", "")); 
              campoTexto.setBackground(new Color(0xFFFF99)); 
              campoTexto.setOpaque(true); 
               id = (Integer)table.getValueAt(row, 0) ;
               System.out.println("He cambiado a ROJO id="+id);
            }
                        
        }
    
  //      System.out.println("Formateamos Valor columna 3 ="+(Integer)table.getValueAt(row,3));
           
        //Se verifica si el valor de la INCIDENCIA estado es igual a 3  para establecer todas las celdas de dicha fila en color rojo
       // if(((Double)table.getValueAt(row, table.getColumnCount()-1)).compareTo(new Double(0))==-1){ 
       
        if(((Integer)table.getValueAt(row, 3)).compareTo(new Integer(1))== 0){ 
            campoTexto.setBackground(new Color(0xFFFF99)); 
            campoTexto.setOpaque(true); 
        } 
          
        
        //Si el campo está seleccionado se establece un color gris para distinguir
        if(isSelected){ 
            campoTexto.setBackground(Color.lightGray); 
            id = (Integer)table.getValueAt(row, 0) ;
            System.out.println("He seleccionado id_m_r="+id);
            
        }
        
        //Por último se devuelve el componente ya con el formato establecido para mostrarlo en la tabla
        return campoTexto;
    }
    
}

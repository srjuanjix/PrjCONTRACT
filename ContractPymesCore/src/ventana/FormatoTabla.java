
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
    
    public int fuente ;
    
    //Sobreescribimos el método getTableCellRendererComponent para poder formatear los componentes
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        
  //      System.out.println("--------- ESTOY CAMBIANDO FORMATO TABLA ------------ row="+row+" y column="+column);
        
        //campoTexto nos sirve como componente dentro de la tabla, es cada una de las celdas de la misma
        JFormattedTextField campoTexto = new JFormattedTextField();
        //Se establece primeramente un borde vacío o sin borde
        campoTexto.setBorder(BorderFactory.createEmptyBorder());
        
        //Si el valor que contiene actualmente es cadena se establece el valor sin formatear en ningún sentido
        
        // .................................................................................................... tamaño de fuente
        
        if ( fuente == 1)  campoTexto.setFont(new java.awt.Font("Tahoma", 0, 16)); else campoTexto.setFont(new java.awt.Font("Tahoma", 0, 12));
        
// ....................................................................................................
        
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
            }
                        
        }
        /*
        if (column == 7) {
          if(((Integer)table.getValueAt(row, 7)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }
        }
        if (column == 8) {
          if(((Integer)table.getValueAt(row, 8)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }
        }
        if (column == 9) {
          if(((Integer)table.getValueAt(row, 9)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }
        }
         if (column == 22) {
          if(((Integer)table.getValueAt(row, 22)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }
        }
         if (column == 23) {
          if(((Integer)table.getValueAt(row, 23)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }
        }
        if (column == 24) {
          if(((Integer)table.getValueAt(row, 24)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }
        }
        if (column == 25) {
          if(((Integer)table.getValueAt(row, 25)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }
        }
          if (column == 26) {
          if(((Integer)table.getValueAt(row, 26)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }
        
        }
        if (column == 37) {
          if(((Integer)table.getValueAt(row, 37)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }
        
        }
         if (column == 38) {
          if(((Integer)table.getValueAt(row, 38)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }                 
        }
        if (column == 36) {
          if(((Integer)table.getValueAt(row, 36)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }                 
        }
        if (column == 35) {
          if(((Integer)table.getValueAt(row, 35)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }                 
        }
         if (column == 39) {
          if(((Integer)table.getValueAt(row, 39)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }                 
        }
        if (column == 40) {
          if(((Integer)table.getValueAt(row, 40)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }                 
        }
        /*
        if (column == 39) {
          if(((Integer)table.getValueAt(row, 39)).compareTo(new Integer(0))== 0){ 
             campoTexto.setText("");
             campoTexto.setOpaque(true); 
         } else {
             campoTexto.setText("X");
             campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
             campoTexto.setOpaque(true); 
          }                 
        }
         */  
        //Se verifica si el valor de la INCIDENCIA estado es igual a 1  para establecer todas las celdas de dicha fila en color amarillo
        
       
        if(((Integer)table.getValueAt(row, 4)).compareTo(new Integer(1))== 0){ 
            campoTexto.setBackground(new Color(0xFFFF99)); 
            campoTexto.setOpaque(true); 
         
        } 
         //Se verifica si el valor de la INCIDENCIA estado es igual a 3  para establecer todas las celdas de dicha fila en color rojo
         if(((Integer)table.getValueAt(row, 4)).compareTo(new Integer(3))== 0){ 
            campoTexto.setBackground(new Color(0xFE899B)); 
            campoTexto.setOpaque(true); 
        } 
        //Se verifica si el valor de la INCIDENCIA estado es igual a 2  para establecer todas las celdas de dicha fila en color naranja
         if(((Integer)table.getValueAt(row, 4)).compareTo(new Integer(2))== 0){ 
            campoTexto.setBackground(new Color(0xFFCC99)); 
            campoTexto.setOpaque(true); 
        } 
         //Se verifica si el valor de la INCIDENCIA estado es igual a 4  para establecer todas las celdas de dicha fila en color verde
         if(((Integer)table.getValueAt(row, 4)).compareTo(new Integer(4))== 0){ 
            campoTexto.setBackground(new Color(0x66FF66)); 
            campoTexto.setOpaque(true); 
        } 
        //Se verifica si el valor de la INCIDENCIA estado es igual a 5  para establecer todas las celdas de dicha fila en color MORADO
         if(((Integer)table.getValueAt(row, 4)).compareTo(new Integer(5))== 0){ 
            campoTexto.setBackground(new Color(0xFF99FF)); 
            campoTexto.setOpaque(true); 
        } 
         //Se verifica si el valor de la INCIDENCIA estado es igual a 5  para establecer todas las celdas de dicha fila en color AZUL
         if(((Integer)table.getValueAt(row, 4)).compareTo(new Integer(6))== 0){ 
            campoTexto.setBackground(new Color(0x99CCFF)); 
            campoTexto.setOpaque(true); 
        } 
        
        //Si el campo está seleccionado se establece un color gris para distinguir
        if(isSelected){ 
            campoTexto.setBackground(Color.lightGray); 
        }
        
        //Por último se devuelve el componente ya con el formato establecido para mostrarlo en la tabla
        return campoTexto;
    }
    
}

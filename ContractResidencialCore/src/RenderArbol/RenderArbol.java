package RenderArbol;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

/**
 *
 * @author jab7b7
 */
public class RenderArbol extends DefaultTreeCellRenderer{
/** Creates a new instance of RendererArbol */
public Integer locuciones[] = new Integer[5000];
    RenderArbol() {
        int i;
        for (i=0; i< 5000; i++) this.locuciones[i] = 0 ;
        System.out.println("he construido RenderArbol");
    }
    
    
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)

    {             
        String sind ;
        int ind=0;
        
        System.out.println("-- HE ENTRADO A RENDERIZAR EL ARBOL --");
        
        super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);

        String val = value.toString();
        
        
     
        String[] palabrasSeparadas = val.split(" ");
        
        sind = palabrasSeparadas[0];
        
         System.out.println("Val ="+val+" sind="+sind);
         if(sind.compareTo("-1")!=0){  
         try {
             ind = Integer.valueOf(sind) ;  
             System.out.println("Estoy analizando string val="+val+" extraigo indice ind="+ind+" valor locución ="+this.locuciones[ind]);
       
              
                  DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)value;
                   // setIcon(conectados);
                  if (this.locuciones[ind] == 0 ) {
                      System.out.println("Es igual a 0!");  
                      this.setTextNonSelectionColor(new Color(0x000000));
                  } else {
                     System.out.println("Es igual a 1!"); 
                     this.setTextNonSelectionColor(new Color(0x006600));

                  }
            
                      
         } catch (NumberFormatException nfe){
                  System.out.println("No es valor numerico");
        }
         } 
        return this;

    }

}
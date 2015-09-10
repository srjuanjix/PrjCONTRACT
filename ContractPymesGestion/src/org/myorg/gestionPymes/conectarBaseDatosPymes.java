/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.myorg.gestionPymes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "conexion.conectarBaseDatosPymes"
)
@ActionRegistration(
        iconBase = "conexion/IconoConectarBDP.png",
        displayName = "#CTL_conectarBaseDatosPymes"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1100),
    @ActionReference(path = "Toolbars/File", position = 100)
})
@Messages("CTL_conectarBaseDatosPymes=Conectar con la base de datos de Pymes")
public final class conectarBaseDatosPymes implements ActionListener {

    @Override
      public void actionPerformed(ActionEvent e) {
        pymesAdministracionTopComponent tc2 = pymesAdministracionTopComponent.findInstance();       
        tc2.conectarBDBoton();
    }
}

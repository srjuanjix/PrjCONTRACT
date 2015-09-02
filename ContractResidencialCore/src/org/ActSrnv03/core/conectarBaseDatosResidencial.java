/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ActSrnv03.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "org.ActSrnv03.core.conectarBaseDatosResidencial"
)
@ActionRegistration(
        iconBase = "org/ActSrnv03/core/IconoConectarBDR.png",
        displayName = "#CTL_conectarBaseDatosResidencial"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1200),
    @ActionReference(path = "Toolbars/File", position = 200)
})
@Messages("CTL_conectarBaseDatosResidencial=Conectar con la base de datos Residencial")
public final class conectarBaseDatosResidencial implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
         ResidencialTopComponent tc = ResidencialTopComponent.findInstance();       
         tc.conectarBDBoton();
    }
}

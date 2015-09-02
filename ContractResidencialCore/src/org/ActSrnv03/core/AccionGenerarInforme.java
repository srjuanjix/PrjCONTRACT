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
        category = "Tools",
        id = "org.ActSrnv03.core.AccionGenerarInforme"
)
@ActionRegistration(
        iconBase = "org/ActSrnv03/core/IconoGenerarInforme.png",
        displayName = "#CTL_AccionGenerarInforme"
)
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 0),
    @ActionReference(path = "Toolbars/File", position = 600)
})
@Messages("CTL_AccionGenerarInforme=Generar informe EXEL Residencial")
public final class AccionGenerarInforme implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
         ResidencialTopComponent tc = ResidencialTopComponent.findInstance();
         
         tc.GuardarArchivoExelDevueltos();
    }
}

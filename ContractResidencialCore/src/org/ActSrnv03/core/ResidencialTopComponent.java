/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ActSrnv03.core;

import RenderArbol.RenderArbol;
import conexion.Conexion;
import dao.PymesDao;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.ProgressMonitor;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import proceso.Proceso;
// import ventana.ClaseFrame;
import ventana.FormatoTabla;
import ventana.FormatoTablaLiquida;
// import RenderArbol.RenderArbol;
import vo.PymesVo;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.openide.util.Exceptions;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.ActSrnv03.core//Residencial//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "ResidencialTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "org.ActSrnv03.core.ResidencialTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ResidencialAction",
        preferredID = "ResidencialTopComponent"
)
@Messages({
    "CTL_ResidencialAction=Gestión contratos Residencial",
    "CTL_ResidencialTopComponent=GESTION RESIDENCIAL",
    "HINT_ResidencialTopComponent=Ventana administracion de contratos residencial"
})
public final class ResidencialTopComponent extends TopComponent {

    public int filtroEstadoSel = 0 ;
    public int filtroFechaSel = 0 ;
    public int filtroProvincia = 0;
    public int filtroAgente = 0;
    public int filtroMakro = 0 ;
   
    public int filtroComercial = 0 ;
    public int filtroZonaLiq = 0 ;
    public int filtroIncidencia = 7;
    
     String texto;
    File nombre = null ;
    String archivo = null;  
    public ProgressMonitor progressMonitor;
    // .......................................................... 
    public String tablaDatos[][]            = new String[5000][55];  
    public String tablaDatosIncpl[][]       = new String[5000][55];
    public String tablaDatosCmpl[][]        = new String[5000][55];
    public Integer tablaErrores[][]         = new Integer[5000][55];
    public String tablaErrorCod[][]         = new String[5000][55];
    public String tablaLocuciones[][]       = new String[5000][25];
    public String tablaCertificaciones[][]  = new String[5000][60];
    public Integer locuciones[]             = new Integer[5000];
    public Integer llamadasLocuciones[][]   = new Integer[5000][15];
    
    public Integer tablaConfiguracion[] = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1} ;
    
    // .......................................................... 
    public int nRegistros=0;
    public int Incompletos ;
    public int Completos ;
    public int nLocuciones =0;
    public int nCertificaciones =0;
    public int nLiquidaciones=0;
    
    public int idt=0 ;
    public int indGen=-1;
    public int nDias = 0;
    public int indGenReg = 0 ;
    
      // .......................................................... 
    public String plogin    = "";
    public String ppassword = "";
            
    public int idSelect=0;
    public int indTabla = 0 ;
       
    // .......................................................... 
  //  public ClaseFrame miVentanaPrincipal;
    
    // ..........................................................
    
    public int fuente = 0 ;
    
    // ..........................................................
    
    public  String estado     ;
    public  String incidencia ;
    public  String fecha_prod  ;
    public  String cups_gas   ;
    public  String cups_elec  ;
    public  String cod_postal ;
    public  String municipio   ;
    public  String provincia  ;
    public  String direccion   ;
    public  String titular   ;
    public  String nif_cif    ;
    public  String fecha_firma ;
    public  String consumo_gas ;
    public  String consumo    ;
    public  String tarifa_gas  ;
    public  String tarifa    ;
    public  String prd01     ;
    public  String prd02     ;
    public  String prd03      ;
    public  String prd04      ;
    public  String prd05    ;
    public  String observaciones ;
    public  String telefono    ;
    public  String agente      ;
    public  String iEstado   ;
    public  String iIncidencia  ;
    public  String Comercial   ;
    
    // ..........................................................
    
    private static ResidencialTopComponent instance;
    private static final String PREFERRED_ID = "ResidencialTopComponent"; 
    
    // ..........................................................
    
    public ResidencialTopComponent() {
        initComponents();
        setName(Bundle.CTL_ResidencialTopComponent());
        setToolTipText(Bundle.HINT_ResidencialTopComponent());
        System.setProperty("netbeans.buildnumber", "2.0.6");
        
        System.setProperty("netbeans.projectname","CONTRACT - SERNOVEN");

    }
     public static synchronized ResidencialTopComponent getDefault() {
        if (instance == null) {
            instance = new ResidencialTopComponent();
        }
        return instance;
    }

  
    public static synchronized ResidencialTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(ResidencialTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof ResidencialTopComponent) {
            return (ResidencialTopComponent) win;
        }
        Logger.getLogger(ResidencialTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        arbol = new javax.swing.JTree();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        botonModificar = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jTextField27 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox4 = new javax.swing.JComboBox();
        jCheckBox28 = new javax.swing.JCheckBox();
        jCheckBox29 = new javax.swing.JCheckBox();
        jCheckBox30 = new javax.swing.JCheckBox();
        jLabel47 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jTextField24 = new javax.swing.JTextField();
        jCheckBox31 = new javax.swing.JCheckBox();
        jCheckBox32 = new javax.swing.JCheckBox();
        botonBuscarID = new javax.swing.JButton();
        jTextField75 = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        jCheckBox35 = new javax.swing.JCheckBox();
        jPanel15 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jCheckBox23 = new javax.swing.JCheckBox();
        jCheckBox24 = new javax.swing.JCheckBox();
        jCheckBox25 = new javax.swing.JCheckBox();
        jCheckBox26 = new javax.swing.JCheckBox();
        jCheckBox27 = new javax.swing.JCheckBox();
        jCheckBox33 = new javax.swing.JCheckBox();
        jCheckBox34 = new javax.swing.JCheckBox();
        jCheckBox36 = new javax.swing.JCheckBox();
        jPanel22 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        botonBuscarCUPSe = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        botonBuscarCUPSg = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        botonBuscarTelefono = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField74 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        botonBuscarNIF = new javax.swing.JButton();
        jTextField11 = new javax.swing.JTextField();
        actuaMunicipio = new javax.swing.JButton();
        jLabel138 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        botonBuscarTitular = new javax.swing.JButton();
        jTextField123 = new javax.swing.JTextField();
        jLabel130 = new javax.swing.JLabel();
        jTextField125 = new javax.swing.JTextField();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel26 = new javax.swing.JPanel();
        miBarraLocu = new javax.swing.JScrollPane();
        miTablaLocu = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        miBarraCer = new javax.swing.JScrollPane();
        miTablaCer = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel23 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel56 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        miBarra01 = new javax.swing.JScrollPane();
        miTabla01 = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        botonRefrescar = new javax.swing.JButton();
        ListaTiempo = new javax.swing.JComboBox();
        jComboBox1 = new javax.swing.JComboBox();
        ListaProvincias = new javax.swing.JComboBox();
        ListaAgentes = new javax.swing.JComboBox();
        FiltroIncidencia = new javax.swing.JComboBox();
        jLabel131 = new javax.swing.JLabel();
        jTextField124 = new javax.swing.JTextField();
        reduceFuente = new javax.swing.JButton();
        aumentaFuente = new javax.swing.JButton();
        ListaOrden = new javax.swing.JComboBox();
        generaExelMakro = new javax.swing.JButton();
        jTextField30 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jTextField40 = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        ListaComercialLiq = new javax.swing.JComboBox();
        jTextField41 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        miBarra03 = new javax.swing.JScrollPane();
        miTablaLiquida = new javax.swing.JTable();
        botonGeneraExelLiquida = new javax.swing.JButton();
        botonActualizarCalculos = new javax.swing.JButton();
        jTextField72 = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jTextField73 = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        ListaZonaLiq = new javax.swing.JComboBox();
        jPanel14 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jTextField42 = new javax.swing.JTextField();
        jTextField43 = new javax.swing.JTextField();
        jTextField44 = new javax.swing.JTextField();
        jTextField46 = new javax.swing.JTextField();
        jTextField47 = new javax.swing.JTextField();
        jTextField48 = new javax.swing.JTextField();
        jTextField49 = new javax.swing.JTextField();
        jTextField50 = new javax.swing.JTextField();
        jTextField56 = new javax.swing.JTextField();
        jTextField108 = new javax.swing.JTextField();
        jTextField111 = new javax.swing.JTextField();
        jTextField76 = new javax.swing.JTextField();
        jTextField77 = new javax.swing.JTextField();
        jTextField78 = new javax.swing.JTextField();
        jTextField80 = new javax.swing.JTextField();
        jTextField81 = new javax.swing.JTextField();
        jTextField82 = new javax.swing.JTextField();
        jTextField83 = new javax.swing.JTextField();
        jTextField51 = new javax.swing.JTextField();
        jTextField52 = new javax.swing.JTextField();
        jTextField53 = new javax.swing.JTextField();
        jTextField54 = new javax.swing.JTextField();
        jTextField55 = new javax.swing.JTextField();
        jTextField57 = new javax.swing.JTextField();
        jTextField107 = new javax.swing.JTextField();
        jTextField112 = new javax.swing.JTextField();
        jTextField79 = new javax.swing.JTextField();
        jTextField86 = new javax.swing.JTextField();
        jTextField89 = new javax.swing.JTextField();
        jTextField92 = new javax.swing.JTextField();
        jTextField93 = new javax.swing.JTextField();
        jTextField94 = new javax.swing.JTextField();
        jTextField95 = new javax.swing.JTextField();
        jTextField58 = new javax.swing.JTextField();
        jTextField59 = new javax.swing.JTextField();
        jTextField60 = new javax.swing.JTextField();
        jTextField61 = new javax.swing.JTextField();
        jTextField62 = new javax.swing.JTextField();
        jTextField63 = new javax.swing.JTextField();
        jTextField109 = new javax.swing.JTextField();
        jTextField113 = new javax.swing.JTextField();
        jTextField84 = new javax.swing.JTextField();
        jTextField87 = new javax.swing.JTextField();
        jTextField90 = new javax.swing.JTextField();
        jTextField96 = new javax.swing.JTextField();
        jTextField97 = new javax.swing.JTextField();
        jTextField99 = new javax.swing.JTextField();
        jTextField98 = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jTextField45 = new javax.swing.JTextField();
        jTextField64 = new javax.swing.JTextField();
        jTextField65 = new javax.swing.JTextField();
        jTextField66 = new javax.swing.JTextField();
        jTextField67 = new javax.swing.JTextField();
        jTextField68 = new javax.swing.JTextField();
        jTextField69 = new javax.swing.JTextField();
        jTextField110 = new javax.swing.JTextField();
        jTextField114 = new javax.swing.JTextField();
        jTextField85 = new javax.swing.JTextField();
        jTextField88 = new javax.swing.JTextField();
        jTextField91 = new javax.swing.JTextField();
        jTextField100 = new javax.swing.JTextField();
        jTextField101 = new javax.swing.JTextField();
        jTextField102 = new javax.swing.JTextField();
        jTextField103 = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jTextField115 = new javax.swing.JTextField();
        jTextField116 = new javax.swing.JTextField();
        jTextField117 = new javax.swing.JTextField();
        jTextField118 = new javax.swing.JTextField();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jTextField119 = new javax.swing.JTextField();
        jTextField120 = new javax.swing.JTextField();
        jTextField121 = new javax.swing.JTextField();
        jTextField122 = new javax.swing.JTextField();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jLabel132 = new javax.swing.JLabel();
        jTextField126 = new javax.swing.JTextField();
        jLabel133 = new javax.swing.JLabel();
        jTextField127 = new javax.swing.JTextField();
        jTextField128 = new javax.swing.JTextField();
        jTextField129 = new javax.swing.JTextField();
        jLabel134 = new javax.swing.JLabel();
        jTextField130 = new javax.swing.JTextField();
        jLabel135 = new javax.swing.JLabel();
        jTextField131 = new javax.swing.JTextField();
        jTextField132 = new javax.swing.JTextField();
        jTextField133 = new javax.swing.JTextField();
        jLabel136 = new javax.swing.JLabel();
        jTextField134 = new javax.swing.JTextField();
        jLabel137 = new javax.swing.JLabel();
        jTextField135 = new javax.swing.JTextField();
        jTextField136 = new javax.swing.JTextField();
        jTextField137 = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        jTextField71 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jTextField70 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        miBarra02 = new javax.swing.JScrollPane();
        miTablaCertifica = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        jTextField104 = new javax.swing.JTextField();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jTextField105 = new javax.swing.JTextField();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jTextField106 = new javax.swing.JTextField();
        jLabel114 = new javax.swing.JLabel();
        botonActualizarCalculos1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jTextField138 = new javax.swing.JTextField();
        jTextField139 = new javax.swing.JTextField();
        jTextField140 = new javax.swing.JTextField();
        jTextField141 = new javax.swing.JTextField();
        jTextField142 = new javax.swing.JTextField();
        jTextField143 = new javax.swing.JTextField();
        jTextField144 = new javax.swing.JTextField();
        jTextField145 = new javax.swing.JTextField();
        jTextField146 = new javax.swing.JTextField();
        jTextField147 = new javax.swing.JTextField();
        jTextField148 = new javax.swing.JTextField();
        jTextField156 = new javax.swing.JTextField();
        jTextField157 = new javax.swing.JTextField();
        jTextField158 = new javax.swing.JTextField();
        jTextField159 = new javax.swing.JTextField();
        jTextField160 = new javax.swing.JTextField();
        jTextField161 = new javax.swing.JTextField();
        jTextField162 = new javax.swing.JTextField();
        jTextField163 = new javax.swing.JTextField();
        jTextField171 = new javax.swing.JTextField();
        jTextField172 = new javax.swing.JTextField();
        jTextField173 = new javax.swing.JTextField();
        jTextField174 = new javax.swing.JTextField();
        jTextField175 = new javax.swing.JTextField();
        jTextField176 = new javax.swing.JTextField();
        jTextField177 = new javax.swing.JTextField();
        jTextField178 = new javax.swing.JTextField();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jTextField186 = new javax.swing.JTextField();
        jTextField187 = new javax.swing.JTextField();
        jTextField188 = new javax.swing.JTextField();
        jTextField189 = new javax.swing.JTextField();
        jTextField190 = new javax.swing.JTextField();
        jTextField191 = new javax.swing.JTextField();
        jTextField192 = new javax.swing.JTextField();
        jTextField193 = new javax.swing.JTextField();
        jTextField194 = new javax.swing.JTextField();
        jLabel152 = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        jLabel154 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        jLabel156 = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        jLabel158 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        jLabel160 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel145 = new javax.swing.JLabel();
        jTextField149 = new javax.swing.JTextField();
        jLabel146 = new javax.swing.JLabel();
        jTextField150 = new javax.swing.JTextField();
        jTextField151 = new javax.swing.JTextField();
        jTextField152 = new javax.swing.JTextField();
        jLabel147 = new javax.swing.JLabel();
        jTextField153 = new javax.swing.JTextField();
        jLabel148 = new javax.swing.JLabel();
        jTextField154 = new javax.swing.JTextField();
        jTextField155 = new javax.swing.JTextField();
        jTextField164 = new javax.swing.JTextField();
        jLabel149 = new javax.swing.JLabel();
        jTextField165 = new javax.swing.JTextField();
        jLabel150 = new javax.swing.JLabel();
        jTextField166 = new javax.swing.JTextField();
        jTextField167 = new javax.swing.JTextField();
        jTextField168 = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        jLabel151 = new javax.swing.JLabel();
        jTextField169 = new javax.swing.JTextField();
        jLabel161 = new javax.swing.JLabel();
        jTextField170 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        numLineas = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nComienzo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jCheckBox13 = new javax.swing.JCheckBox();
        jCheckBox14 = new javax.swing.JCheckBox();
        jCheckBox15 = new javax.swing.JCheckBox();
        jCheckBox16 = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        botonGuardaConf = new javax.swing.JButton();
        jTextField28 = new javax.swing.JTextField();
        botonCalendario = new javax.swing.JButton();
        botonActualizaFecha = new javax.swing.JButton();
        login = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        passw = new javax.swing.JPasswordField();
        jLabel14 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        scrollPaneArea = new javax.swing.JScrollPane();
        areaDeTexto = new javax.swing.JTextArea();
        scrollPaneAreaProceso = new javax.swing.JScrollPane();
        areaDeTextoProcesado = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();

        arbol.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        arbol.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                arbolValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(arbol);

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel7.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel8.text")); // NOI18N

        botonModificar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(botonModificar, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.botonModificar.text")); // NOI18N
        botonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel38, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel38.text")); // NOI18N

        jTextField27.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField27.text")); // NOI18N

        jLabel41.setForeground(new java.awt.Color(204, 102, 0));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel41, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel41.text")); // NOI18N

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BLANCO", "AMARILLO", "NARANJA", "ROJO", "VERDE", "MORADO", "AZUL" }));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PENDIENTE", "DOCOUT", "CERTIFICADO", "-     KO      -", "VALIDADO", ".................", "ERROR WEBSALES", "DECOMISIONADO" }));

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox28, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox28.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox29, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox29.text")); // NOI18N
        jCheckBox29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox29ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox30, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox30.text")); // NOI18N
        jCheckBox30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox30ActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel47, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel47.text")); // NOI18N

        jTextField21.setEditable(false);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel48, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel48.text")); // NOI18N

        jTextField24.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField24.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox31, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox31.text")); // NOI18N
        jCheckBox31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox31ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox32, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox32.text")); // NOI18N

        botonBuscarID.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ventana/IconoBusquedaPequeño_16x16.png"))); // NOI18N
        botonBuscarID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarIDActionPerformed(evt);
            }
        });

        jLabel107.setForeground(new java.awt.Color(204, 204, 204));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel107, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel107.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox35, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox35.text")); // NOI18N

        jPanel15.setBackground(new java.awt.Color(204, 204, 204));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel42, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel42.text")); // NOI18N

        jCheckBox23.setBackground(new java.awt.Color(204, 204, 204));
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox23, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox23.text")); // NOI18N

        jCheckBox24.setBackground(new java.awt.Color(204, 204, 204));
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox24, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox24.text")); // NOI18N

        jCheckBox25.setBackground(new java.awt.Color(204, 204, 204));
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox25, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox25.text")); // NOI18N

        jCheckBox26.setBackground(new java.awt.Color(204, 204, 204));
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox26, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox26.text")); // NOI18N

        jCheckBox27.setBackground(new java.awt.Color(204, 204, 204));
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox27, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox27.text")); // NOI18N

        jCheckBox33.setBackground(new java.awt.Color(204, 204, 204));
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox33, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox33.text")); // NOI18N

        jCheckBox34.setBackground(new java.awt.Color(204, 204, 204));
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox34, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox34.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox36, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox36.text")); // NOI18N

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jLabel42)
                .addGap(46, 46, 46))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox24)
                    .addComponent(jCheckBox23)
                    .addComponent(jCheckBox25)
                    .addComponent(jCheckBox27)
                    .addComponent(jCheckBox26)
                    .addComponent(jCheckBox33)
                    .addComponent(jCheckBox34, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox36))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox36)
                .addGap(8, 8, 8))
        );

        jPanel22.setBackground(new java.awt.Color(204, 204, 204));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel10.text")); // NOI18N

        jTextField4.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField4.text")); // NOI18N

        botonBuscarCUPSe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ventana/IconoBusquedaPequeño_16x16.png"))); // NOI18N
        botonBuscarCUPSe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarCUPSeActionPerformed(evt);
            }
        });

        jTextField5.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField5.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel11, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel11.text")); // NOI18N

        botonBuscarCUPSg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ventana/IconoBusquedaPequeño_16x16.png"))); // NOI18N
        botonBuscarCUPSg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarCUPSgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonBuscarCUPSe, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonBuscarCUPSg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonBuscarCUPSg)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addComponent(botonBuscarCUPSe)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel24.setBackground(new java.awt.Color(204, 204, 255));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel17, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel17.text")); // NOI18N

        jTextField12.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField12.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel27, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel27.text")); // NOI18N

        jTextField22.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField22.text")); // NOI18N

        botonBuscarTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ventana/IconoBusquedaPequeño_16x16.png"))); // NOI18N
        botonBuscarTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarTelefonoActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel15, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel15.text")); // NOI18N

        jTextField10.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField10.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel39, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel39.text")); // NOI18N

        jTextField19.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField19.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel46, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel46.text")); // NOI18N

        jTextField20.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField20.text")); // NOI18N

        jTextField2.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel12.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel9.text")); // NOI18N

        jTextField18.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField18.text")); // NOI18N

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel46)
                        .addComponent(jLabel39)
                        .addComponent(jLabel9)
                        .addComponent(jLabel12)
                        .addComponent(jLabel27)
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonBuscarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField12)
                    .addComponent(jTextField10)))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(botonBuscarTelefono))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel21.setBackground(new java.awt.Color(204, 204, 255));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel28, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel28.text")); // NOI18N

        jTextField23.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField23.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel21, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel21.text")); // NOI18N

        jTextField16.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField16.text")); // NOI18N

        jLabel108.setForeground(new java.awt.Color(204, 204, 204));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel108, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel108.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel13, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel13.text")); // NOI18N

        jTextField7.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField7.text")); // NOI18N
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel20, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel20.text")); // NOI18N

        jTextField15.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField15.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel18, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel18.text")); // NOI18N

        jTextField13.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField13.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel16, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel16.text")); // NOI18N

        botonBuscarNIF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ventana/IconoBusquedaPequeño_16x16.png"))); // NOI18N
        botonBuscarNIF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarNIFActionPerformed(evt);
            }
        });

        jTextField11.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField11.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(actuaMunicipio, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.actuaMunicipio.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel138, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel138.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel19, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel19.text")); // NOI18N

        jTextField14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField14.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField14.text")); // NOI18N

        botonBuscarTitular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ventana/IconoBusquedaPequeño_16x16.png"))); // NOI18N
        botonBuscarTitular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarTitularActionPerformed(evt);
            }
        });

        jTextField123.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField123.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField123.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField123.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel130, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel130.text")); // NOI18N

        jTextField125.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField125.text")); // NOI18N

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel108))
                                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel138, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField74)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(47, 47, 47))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(22, 22, 22)
                                        .addComponent(actuaMunicipio))
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20)
                                        .addComponent(botonBuscarTitular, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(botonBuscarNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel130)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField125, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField123, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(28, 28, 28))))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(botonBuscarTitular))
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel20)
                                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(botonBuscarNIF)
                            .addComponent(jLabel130)))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jTextField123, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(actuaMunicipio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jTextField125, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel108)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel138))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(17, 17, 17))
        );

        miBarraLocu.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        miBarraLocu.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        miTablaLocu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        miBarraLocu.setViewportView(miTablaLocu);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(miBarraLocu, javax.swing.GroupLayout.DEFAULT_SIZE, 1152, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(miBarraLocu, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jPanel26.TabConstraints.tabTitle"), jPanel26); // NOI18N

        miBarraCer.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        miBarraCer.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        miTablaCer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        miBarraCer.setViewportView(miTablaCer);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(miBarraCer, javax.swing.GroupLayout.DEFAULT_SIZE, 1152, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(miBarraCer, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jPanel17.TabConstraints.tabTitle"), jPanel17); // NOI18N

        jPanel20.setBackground(new java.awt.Color(204, 204, 204));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel29, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel29.text")); // NOI18N

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane4.setViewportView(jTextArea3);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel22, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel22.text")); // NOI18N

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel23, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel23.text")); // NOI18N

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)))
                    .addComponent(jScrollPane2))
                .addGap(74, 74, 74))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                                .addGap(914, 914, 914)
                                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTabbedPane2)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel107)
                        .addGap(129, 129, 129)
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextField75, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonBuscarID, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(botonModificar)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox32)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonBuscarID)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel48)
                        .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBox32))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(botonModificar)))
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel38)
                        .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel107))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47)
                            .addComponent(jCheckBox28)
                            .addComponent(jCheckBox29)
                            .addComponent(jCheckBox30)
                            .addComponent(jCheckBox31)
                            .addComponent(jCheckBox35)
                            .addComponent(jButton2))
                        .addGap(1, 1, 1)))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
        );

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel56, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel56.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jPanel3.TabConstraints.tabTitle"), jPanel3); // NOI18N

        miTabla01.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        miTabla01.setCellSelectionEnabled(true);
        miTabla01.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                miTabla01MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                miTabla01MousePressed(evt);
            }
        });
        miBarra01.setViewportView(miTabla01);

        jPanel16.setBackground(new java.awt.Color(204, 204, 204));

        org.openide.awt.Mnemonics.setLocalizedText(botonRefrescar, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.botonRefrescar.text")); // NOI18N
        botonRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRefrescarActionPerformed(evt);
            }
        });

        ListaTiempo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Última semana", "Últimas 2 semanas", "Últimas 3 semanas", "Último mes", "Últimos dos meses", "Últimos tres meses", "Últimos seis meses", "Último año", "Todos" }));
        ListaTiempo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListaTiempoActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PENDIENTES", "DOCOUT", "CERTIFICADOS", "TODOS", "KOs", "PUNTEADOS", "NO PUNTEADOS", "VALIDADOS", "ERROR WEB SALES", "DECOMISIONADO", " " }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        ListaProvincias.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TODAS PROVINCIAS", "CASTELLÓN", "VALENCIA", "ALICANTE", "OTRAS", " ", " ", " " }));
        ListaProvincias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListaProvinciasActionPerformed(evt);
            }
        });

        ListaAgentes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TODOS AGENTES", "J & C", "ETP", "NADINE", "EMILIO-RAQUEL", "SERNOVEN", "MIGUEL", "SHEILA", "MARIO SORIA", "ALBERTO", "AGENTE 01", "AGENTE 02", "AGENTE 03", "AGENTE 04" }));
        ListaAgentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListaAgentesActionPerformed(evt);
            }
        });

        FiltroIncidencia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Filtrar por Incidencia...", "BLANCA", "AMARILLA", "NARANJA", "ROJA", "VERDE", "MORADA", "AZUL", "TODAS" }));
        FiltroIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FiltroIncidenciaActionPerformed(evt);
            }
        });

        jLabel131.setForeground(new java.awt.Color(153, 153, 153));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel131, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel131.text")); // NOI18N

        jTextField124.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField124.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(reduceFuente, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.reduceFuente.text")); // NOI18N
        reduceFuente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reduceFuenteActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(aumentaFuente, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.aumentaFuente.text")); // NOI18N
        aumentaFuente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aumentaFuenteActionPerformed(evt);
            }
        });

        ListaOrden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ordenar por id Asc", "Ordenar por TITULAR", "Ordenar por Municipio", "Ordenar por id Desc", "Ordenar por NIF" }));
        ListaOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListaOrdenActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(generaExelMakro, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.generaExelMakro.text")); // NOI18N
        generaExelMakro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generaExelMakroActionPerformed(evt);
            }
        });

        jTextField30.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField30.text")); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonRefrescar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ListaOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ListaTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField124, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel131)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(FiltroIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ListaAgentes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ListaProvincias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(generaExelMakro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(aumentaFuente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reduceFuente)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ListaTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonRefrescar)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ListaProvincias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ListaAgentes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FiltroIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField124, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel131)
                    .addComponent(reduceFuente)
                    .addComponent(aumentaFuente)
                    .addComponent(ListaOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generaExelMakro)
                    .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(miBarra01, javax.swing.GroupLayout.DEFAULT_SIZE, 1796, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(miBarra01, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jPanel5.TabConstraints.tabTitle"), jPanel5); // NOI18N

        jTextField40.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField40.text")); // NOI18N

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel60, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel60.text")); // NOI18N

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel61, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel61.text")); // NOI18N

        ListaComercialLiq.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "J & C Asesores", "ETP", "NADINE", "EMILIO-RAQUEL", "SERNOVEN", "MIGUEL", "SHEILA", "MARIO SORIA", "ALBERTO", "AGENTE 01", "AGENTE 02", "AGENTE 03", "AGENTE 04", "TODOS" }));
        ListaComercialLiq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListaComercialLiqActionPerformed(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel62, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel62.text")); // NOI18N

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel69, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel69.text")); // NOI18N

        miTablaLiquida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        miBarra03.setViewportView(miTablaLiquida);

        org.openide.awt.Mnemonics.setLocalizedText(botonGeneraExelLiquida, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.botonGeneraExelLiquida.text")); // NOI18N
        botonGeneraExelLiquida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGeneraExelLiquidaActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(botonActualizarCalculos, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.botonActualizarCalculos.text")); // NOI18N
        botonActualizarCalculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarCalculosActionPerformed(evt);
            }
        });

        jTextField72.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField72.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel71, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel71.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel72, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel72.text")); // NOI18N

        jLabel73.setForeground(new java.awt.Color(153, 153, 153));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel73, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel73.text")); // NOI18N

        jLabel74.setForeground(new java.awt.Color(153, 153, 153));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel74, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel74.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel75, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel75.text")); // NOI18N

        jTextField73.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField73.text")); // NOI18N

        jLabel76.setForeground(new java.awt.Color(153, 153, 153));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel76, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel76.text")); // NOI18N

        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel77, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel77.text")); // NOI18N

        ListaZonaLiq.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir...", "CASTELLON", "VALENCIA", "OTRAS", "TODAS" }));
        ListaZonaLiq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListaZonaLiqActionPerformed(evt);
            }
        });

        jPanel14.setBackground(new java.awt.Color(204, 204, 204));

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel63, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel63.text")); // NOI18N

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel64, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel64.text")); // NOI18N

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel65, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel65.text")); // NOI18N

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel67, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel67.text")); // NOI18N

        jTextField42.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField42.text")); // NOI18N
        jTextField42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField42ActionPerformed(evt);
            }
        });

        jTextField43.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField43.text")); // NOI18N

        jTextField44.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField44.text")); // NOI18N
        jTextField44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField44ActionPerformed(evt);
            }
        });

        jTextField46.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField46.text")); // NOI18N

        jTextField47.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField47.text")); // NOI18N

        jTextField48.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField48.text")); // NOI18N

        jTextField49.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField49.text")); // NOI18N

        jTextField50.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField50.text")); // NOI18N
        jTextField50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField50ActionPerformed(evt);
            }
        });

        jTextField56.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField56.text")); // NOI18N

        jTextField108.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField108.text")); // NOI18N

        jTextField111.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField111.text")); // NOI18N

        jTextField76.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField76.text")); // NOI18N

        jTextField77.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField77.text")); // NOI18N

        jTextField78.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField78.text")); // NOI18N

        jTextField80.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField80.text")); // NOI18N

        jTextField81.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField81.text")); // NOI18N

        jTextField82.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField82.text")); // NOI18N

        jTextField83.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField83.text")); // NOI18N

        jTextField51.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField51.text")); // NOI18N

        jTextField52.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField52.text")); // NOI18N

        jTextField53.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField53.text")); // NOI18N

        jTextField54.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField54.text")); // NOI18N
        jTextField54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField54ActionPerformed(evt);
            }
        });

        jTextField55.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField55.text")); // NOI18N

        jTextField57.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField57.text")); // NOI18N

        jTextField107.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField107.text")); // NOI18N
        jTextField107.setPreferredSize(new java.awt.Dimension(146, 20));

        jTextField112.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField112.text")); // NOI18N
        jTextField112.setMaximumSize(new java.awt.Dimension(146, 20));
        jTextField112.setMinimumSize(new java.awt.Dimension(146, 20));
        jTextField112.setPreferredSize(new java.awt.Dimension(146, 20));
        jTextField112.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField112ActionPerformed(evt);
            }
        });

        jTextField79.setForeground(new java.awt.Color(204, 0, 0));
        jTextField79.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField79.text")); // NOI18N

        jTextField86.setForeground(new java.awt.Color(204, 0, 0));
        jTextField86.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField86.text")); // NOI18N

        jTextField89.setForeground(new java.awt.Color(204, 0, 0));
        jTextField89.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField89.text")); // NOI18N

        jTextField92.setForeground(new java.awt.Color(204, 0, 0));
        jTextField92.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField92.text")); // NOI18N

        jTextField93.setForeground(new java.awt.Color(204, 0, 0));
        jTextField93.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField93.text")); // NOI18N

        jTextField94.setForeground(new java.awt.Color(204, 0, 0));
        jTextField94.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField94.text")); // NOI18N

        jTextField95.setForeground(new java.awt.Color(204, 0, 0));
        jTextField95.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField95.text")); // NOI18N

        jTextField58.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField58.text")); // NOI18N

        jTextField59.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField59.text")); // NOI18N

        jTextField60.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField60.text")); // NOI18N

        jTextField61.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField61.text")); // NOI18N

        jTextField62.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField62.text")); // NOI18N

        jTextField63.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField63.text")); // NOI18N

        jTextField109.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField109.text")); // NOI18N

        jTextField113.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField113.text")); // NOI18N

        jTextField84.setForeground(new java.awt.Color(204, 0, 0));
        jTextField84.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField84.text")); // NOI18N

        jTextField87.setForeground(new java.awt.Color(204, 0, 0));
        jTextField87.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField87.text")); // NOI18N

        jTextField90.setForeground(new java.awt.Color(204, 0, 0));
        jTextField90.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField90.text")); // NOI18N

        jTextField96.setForeground(new java.awt.Color(204, 0, 0));
        jTextField96.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField96.text")); // NOI18N

        jTextField97.setForeground(new java.awt.Color(204, 0, 0));
        jTextField97.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField97.text")); // NOI18N

        jTextField99.setForeground(new java.awt.Color(204, 0, 0));
        jTextField99.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField99.text")); // NOI18N

        jTextField98.setForeground(new java.awt.Color(204, 0, 0));
        jTextField98.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField98.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel66, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel66.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel80, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel80.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel83, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel83.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel85, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel85.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel87, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel87.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel89, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel89.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel91, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel91.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel122, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel122.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel124, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel124.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel94, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel94.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel96, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel96.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel102, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel102.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel103, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel103.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel104, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel104.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel105, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel105.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel106, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel106.text")); // NOI18N

        jTextField45.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField45.text")); // NOI18N

        jTextField64.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField64.text")); // NOI18N
        jTextField64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField64ActionPerformed(evt);
            }
        });

        jTextField65.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField65.text")); // NOI18N

        jTextField66.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField66.text")); // NOI18N

        jTextField67.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField67.text")); // NOI18N

        jTextField68.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField68.text")); // NOI18N

        jTextField69.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField69.text")); // NOI18N

        jTextField110.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField110.text")); // NOI18N

        jTextField114.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField114.text")); // NOI18N

        jTextField85.setForeground(new java.awt.Color(204, 0, 0));
        jTextField85.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField85.text")); // NOI18N

        jTextField88.setForeground(new java.awt.Color(204, 0, 0));
        jTextField88.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField88.text")); // NOI18N

        jTextField91.setForeground(new java.awt.Color(204, 0, 0));
        jTextField91.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField91.text")); // NOI18N

        jTextField100.setForeground(new java.awt.Color(204, 0, 0));
        jTextField100.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField100.text")); // NOI18N

        jTextField101.setForeground(new java.awt.Color(204, 0, 0));
        jTextField101.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField101.text")); // NOI18N

        jTextField102.setForeground(new java.awt.Color(204, 0, 0));
        jTextField102.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField102.text")); // NOI18N

        jTextField103.setForeground(new java.awt.Color(204, 0, 0));
        jTextField103.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField103.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel68, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel68.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel81, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel81.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel82, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel82.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel84, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel84.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel86, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel86.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel88, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel88.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel90, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel90.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel123, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel123.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel125, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel125.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel93, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel93.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel95, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel95.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel97, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel97.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel98, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel98.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel99, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel99.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel100, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel100.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel101, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel101.text")); // NOI18N

        jTextField115.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField115.text")); // NOI18N

        jTextField116.setForeground(new java.awt.Color(204, 0, 0));
        jTextField116.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField116.text")); // NOI18N

        jTextField117.setForeground(new java.awt.Color(204, 0, 0));
        jTextField117.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField117.text")); // NOI18N

        jTextField118.setForeground(new java.awt.Color(204, 0, 0));
        jTextField118.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField118.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel126, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel126.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel127, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel127.text")); // NOI18N

        jTextField119.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField119.text")); // NOI18N

        jTextField120.setForeground(new java.awt.Color(204, 0, 0));
        jTextField120.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField120.text")); // NOI18N

        jTextField121.setForeground(new java.awt.Color(204, 0, 0));
        jTextField121.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField121.text")); // NOI18N

        jTextField122.setForeground(new java.awt.Color(204, 0, 0));
        jTextField122.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField122.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel128, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel128.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel129, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel129.text")); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField119, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextField115, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField47)
                        .addComponent(jTextField46, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField42, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                        .addComponent(jLabel63, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField48, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField108, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField50, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField56, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField49, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField77, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField76, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField78, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField80, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField81, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField111, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField82, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField83, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel64)
                        .addGap(275, 275, 275)
                        .addComponent(jLabel65)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField112, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField43)
                            .addComponent(jTextField79, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField51, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField107, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField57, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField55, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField54, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField53, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField52))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField44)
                            .addComponent(jTextField58)
                            .addComponent(jTextField59)
                            .addComponent(jTextField60)
                            .addComponent(jTextField61)
                            .addComponent(jTextField62)
                            .addComponent(jTextField63)
                            .addComponent(jTextField84)
                            .addComponent(jTextField109)
                            .addComponent(jTextField113, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField89)
                            .addComponent(jTextField86)
                            .addComponent(jTextField92))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField87, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField90, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField96, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField93)
                            .addComponent(jTextField94))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField97, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField99, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField116)
                            .addComponent(jTextField95)
                            .addComponent(jTextField120))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField98, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addComponent(jTextField117)
                            .addComponent(jTextField121))))
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel67))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel83)
                                .addComponent(jLabel80)
                                .addComponent(jLabel85)
                                .addComponent(jLabel87)
                                .addComponent(jLabel89)
                                .addComponent(jLabel91))
                            .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel94)
                                        .addComponent(jLabel96)
                                        .addComponent(jLabel102)
                                        .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel106, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel105, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel104, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addComponent(jLabel126, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jTextField85, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel93))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jTextField88, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel95))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jTextField91, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel97))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jTextField100, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel98))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField114, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField110, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField69, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField68, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField67, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField66, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField65, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField64, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField45, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel68)
                                    .addComponent(jLabel81)
                                    .addComponent(jLabel82)
                                    .addComponent(jLabel84)
                                    .addComponent(jLabel86)
                                    .addComponent(jLabel88)
                                    .addComponent(jLabel90)
                                    .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField122, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField118, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField103)
                                    .addComponent(jTextField102)
                                    .addComponent(jTextField101, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel101)
                                    .addComponent(jLabel100)
                                    .addComponent(jLabel99)
                                    .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel64)
                            .addComponent(jLabel63)
                            .addComponent(jLabel65)
                            .addComponent(jLabel67))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel66)
                            .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel68))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel80)
                            .addComponent(jLabel81))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel82)
                                .addComponent(jLabel83))
                            .addComponent(jTextField52, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel84)
                            .addComponent(jLabel85))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel86)
                            .addComponent(jLabel87)
                            .addComponent(jTextField54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTextField49, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField68, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel88)
                    .addComponent(jLabel89))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel90)
                    .addComponent(jLabel91))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField107, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField108, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField109, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField110, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel122)
                    .addComponent(jLabel123))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField111, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField112, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField113, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField114, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel124)
                    .addComponent(jLabel125))
                .addGap(8, 8, 8)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField85, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel93))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField88, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel95))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField91, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel97))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField100, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel98))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField101, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel99))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel94)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField77, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField86, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField87, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel96))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField78, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField89, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel102))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField92, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel103)
                                .addComponent(jTextField96, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField93, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField97, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel104))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField94, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField99, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel105)
                            .addComponent(jTextField102, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel100))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField95, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField98, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel106)
                            .addComponent(jTextField103, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel101))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField115, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField116, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField117, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField118, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel126)
                            .addComponent(jLabel127))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField119, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField120, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField121, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField122, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel128)
                            .addComponent(jLabel129))
                        .addGap(46, 46, 46))))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jButton1.text")); // NOI18N
        jButton1.setEnabled(false);

        jPanel18.setBackground(new java.awt.Color(204, 204, 255));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel132, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel132.text")); // NOI18N

        jTextField126.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField126.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel133, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel133.text")); // NOI18N

        jTextField127.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField127.text")); // NOI18N

        jTextField128.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField128.text")); // NOI18N
        jTextField128.setMinimumSize(new java.awt.Dimension(236, 20));
        jTextField128.setPreferredSize(new java.awt.Dimension(123, 20));

        jTextField129.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField129.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel134, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel134.text")); // NOI18N

        jTextField130.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField130.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel135, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel135.text")); // NOI18N

        jTextField131.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField131.text")); // NOI18N

        jTextField132.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField132.text")); // NOI18N
        jTextField132.setMinimumSize(new java.awt.Dimension(236, 20));

        jTextField133.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField133.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel136, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel136.text")); // NOI18N

        jTextField134.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField134.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel137, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel137.text")); // NOI18N

        jTextField135.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField135.text")); // NOI18N

        jTextField136.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField136.text")); // NOI18N
        jTextField136.setMinimumSize(new java.awt.Dimension(236, 20));

        jTextField137.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField137.text")); // NOI18N

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField129, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(jTextField133)
                    .addComponent(jTextField137))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField128, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField132, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(jTextField136, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jTextField135)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel137, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jTextField127, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel133, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jTextField131)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jTextField134)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel136, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jTextField126, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jTextField130)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel134, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel132)
                    .addComponent(jTextField126, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel133)
                    .addComponent(jTextField127, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField128, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField129, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel134)
                    .addComponent(jTextField130, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel135)
                    .addComponent(jTextField131, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField132, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField133, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel136)
                    .addComponent(jTextField134, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel137)
                    .addComponent(jTextField135, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField136, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField137, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        jPanel19.setBackground(new java.awt.Color(204, 204, 204));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel92, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel92.text")); // NOI18N

        jTextField71.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField71.text")); // NOI18N

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel70, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel70.text")); // NOI18N

        jTextField70.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField70.text")); // NOI18N

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jTextField70, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField71, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel19Layout.createSequentialGroup()
                    .addContainerGap(341, Short.MAX_VALUE)
                    .addComponent(jLabel70)
                    .addGap(256, 256, 256)))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel92)
                    .addComponent(jTextField71, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel19Layout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(jLabel70)
                    .addContainerGap(15, Short.MAX_VALUE)))
        );

        jTextField9.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField9.text")); // NOI18N

        jTextField17.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField17.text")); // NOI18N

        jTextField29.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField29.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel34, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel34.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel35, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel35.text")); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGap(163, 163, 163)
                                        .addComponent(jLabel35)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel61))
                                    .addComponent(jLabel60))
                                .addGap(18, 18, 18)
                                .addComponent(ListaComercialLiq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel77)
                                .addGap(18, 18, 18)
                                .addComponent(ListaZonaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                                .addComponent(jLabel62))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel71)
                                            .addComponent(jLabel72)
                                            .addComponent(jLabel75))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField72, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField73, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel34))
                                        .addGap(40, 40, 40)
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel11Layout.createSequentialGroup()
                                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                                        .addComponent(jLabel76)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                                        .addComponent(jLabel74)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(63, 63, 63)
                                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(botonActualizarCalculos, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(botonGeneraExelLiquida, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                                        .addGap(26, 26, 26)
                                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(jPanel11Layout.createSequentialGroup()
                                                .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(jLabel69)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 202, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(miBarra03)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel61)
                            .addComponent(ListaComercialLiq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel77)
                            .addComponent(ListaZonaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel35)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel62)
                        .addGap(3, 3, 3)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonActualizarCalculos)
                            .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonGeneraExelLiquida)
                            .addComponent(jButton1)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel71)
                            .addComponent(jLabel73)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel72)
                            .addComponent(jLabel74)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel75)
                            .addComponent(jTextField73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel76)
                            .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addComponent(jLabel69)
                .addGap(18, 18, 18)
                .addComponent(miBarra03, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jPanel11.TabConstraints.tabTitle"), jPanel11); // NOI18N

        miTablaCertifica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        miBarra02.setViewportView(miTablaCertifica);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel109, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel109.text")); // NOI18N

        jTextField104.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField104.text")); // NOI18N

        jLabel110.setForeground(new java.awt.Color(153, 153, 153));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel110, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel110.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel111, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel111.text")); // NOI18N

        jTextField105.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField105.text")); // NOI18N

        jLabel112.setForeground(new java.awt.Color(153, 153, 153));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel112, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel112.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel113, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel113.text")); // NOI18N

        jTextField106.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField106.text")); // NOI18N

        jLabel114.setForeground(new java.awt.Color(153, 153, 153));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel114, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel114.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(botonActualizarCalculos1, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.botonActualizarCalculos1.text")); // NOI18N
        botonActualizarCalculos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarCalculos1ActionPerformed(evt);
            }
        });

        jTextField1.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField1.text")); // NOI18N

        jTextField3.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField3.text")); // NOI18N
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField6.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField6.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel25, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel25.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel26, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel26.text")); // NOI18N

        jTextField8.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField8.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel32, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel32.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel33, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel33.text")); // NOI18N

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel24, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel24.text")); // NOI18N

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel79, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel79.text")); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel109)
                                    .addComponent(jLabel111)
                                    .addComponent(jLabel113))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jTextField104, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel110))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField105, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField106, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel112)
                                            .addComponent(jLabel114))))
                                .addGap(100, 100, 100)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING))))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel25)
                                .addGap(20, 20, 20)))
                        .addGap(49, 49, 49)
                        .addComponent(jLabel79)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonActualizarCalculos1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel33)))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(botonActualizarCalculos1)
                        .addComponent(jLabel25)
                        .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel26))
                .addGap(1, 1, 1)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField104, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel109)
                    .addComponent(jLabel110)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField105, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel111)
                    .addComponent(jLabel112)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel113)
                    .addComponent(jTextField106, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel114)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jLabel24))
        );

        jPanel23.setBackground(new java.awt.Color(204, 204, 204));

        jLabel115.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel115, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel115.text")); // NOI18N

        jLabel116.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel116, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel116.text")); // NOI18N

        jLabel117.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel117, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel117.text")); // NOI18N

        jLabel118.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel118, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel118.text")); // NOI18N

        jTextField138.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField138.text")); // NOI18N
        jTextField138.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField138ActionPerformed(evt);
            }
        });

        jTextField139.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField139.text")); // NOI18N

        jTextField140.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField140.text")); // NOI18N
        jTextField140.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField140ActionPerformed(evt);
            }
        });

        jTextField141.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField141.text")); // NOI18N

        jTextField142.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField142.text")); // NOI18N

        jTextField143.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField143.text")); // NOI18N

        jTextField144.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField144.text")); // NOI18N

        jTextField145.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField145.text")); // NOI18N
        jTextField145.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField145ActionPerformed(evt);
            }
        });

        jTextField146.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField146.text")); // NOI18N

        jTextField147.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField147.text")); // NOI18N

        jTextField148.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField148.text")); // NOI18N

        jTextField156.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField156.text")); // NOI18N

        jTextField157.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField157.text")); // NOI18N

        jTextField158.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField158.text")); // NOI18N

        jTextField159.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField159.text")); // NOI18N
        jTextField159.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField159ActionPerformed(evt);
            }
        });

        jTextField160.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField160.text")); // NOI18N

        jTextField161.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField161.text")); // NOI18N

        jTextField162.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField162.text")); // NOI18N
        jTextField162.setPreferredSize(new java.awt.Dimension(146, 20));

        jTextField163.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField163.text")); // NOI18N
        jTextField163.setMaximumSize(new java.awt.Dimension(146, 20));
        jTextField163.setMinimumSize(new java.awt.Dimension(146, 20));
        jTextField163.setPreferredSize(new java.awt.Dimension(146, 20));
        jTextField163.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField163ActionPerformed(evt);
            }
        });

        jTextField171.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField171.text")); // NOI18N

        jTextField172.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField172.text")); // NOI18N

        jTextField173.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField173.text")); // NOI18N

        jTextField174.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField174.text")); // NOI18N

        jTextField175.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField175.text")); // NOI18N

        jTextField176.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField176.text")); // NOI18N

        jTextField177.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField177.text")); // NOI18N

        jTextField178.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField178.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel119, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel119.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel120, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel120.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel121, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel121.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel139, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel139.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel140, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel140.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel141, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel141.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel142, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel142.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel143, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel143.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel144, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel144.text")); // NOI18N

        jTextField186.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField186.text")); // NOI18N

        jTextField187.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField187.text")); // NOI18N
        jTextField187.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField187ActionPerformed(evt);
            }
        });

        jTextField188.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField188.text")); // NOI18N

        jTextField189.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField189.text")); // NOI18N

        jTextField190.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField190.text")); // NOI18N

        jTextField191.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField191.text")); // NOI18N

        jTextField192.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField192.text")); // NOI18N

        jTextField193.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField193.text")); // NOI18N

        jTextField194.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField194.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel152, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel152.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel153, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel153.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel154, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel154.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel155, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel155.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel156, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel156.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel157, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel157.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel158, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel158.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel159, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel159.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel160, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel160.text")); // NOI18N

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField142)
                    .addComponent(jTextField141, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField138, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(jLabel115, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField143, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField147, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField145, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField146, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField144, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField148, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel116)
                        .addGap(275, 275, 275)
                        .addComponent(jLabel117)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField163, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField139)
                            .addComponent(jTextField156, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField162, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField161, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField160, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField159, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField158, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField157))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField140)
                            .addComponent(jTextField171)
                            .addComponent(jTextField172)
                            .addComponent(jTextField173)
                            .addComponent(jTextField174)
                            .addComponent(jTextField175)
                            .addComponent(jTextField176)
                            .addComponent(jTextField177)
                            .addComponent(jTextField178, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel118))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel121)
                                .addComponent(jLabel120)
                                .addComponent(jLabel139)
                                .addComponent(jLabel140)
                                .addComponent(jLabel141)
                                .addComponent(jLabel142))
                            .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel143, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel144, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField194, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField193, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField192, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField191, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField190, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField189, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField188, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField187, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField186, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel152)
                            .addComponent(jLabel153)
                            .addComponent(jLabel154)
                            .addComponent(jLabel155)
                            .addComponent(jLabel156)
                            .addComponent(jLabel157)
                            .addComponent(jLabel158)
                            .addComponent(jLabel159, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel160, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(1, 1, 1))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel116)
                            .addComponent(jLabel115)
                            .addComponent(jLabel117)
                            .addComponent(jLabel118))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField138, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField139, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField140, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel119)
                            .addComponent(jTextField186, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel152))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField141, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField156, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField171, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField187, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel120)
                            .addComponent(jLabel153))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField142, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField172, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField188, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel154)
                                .addComponent(jLabel121))
                            .addComponent(jTextField157, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField143, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField158, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField173, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField189, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel155)
                            .addComponent(jLabel139))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField174, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField190, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel156)
                            .addComponent(jLabel140)
                            .addComponent(jTextField159, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTextField144, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField145, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField160, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField175, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField191, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel157)
                    .addComponent(jLabel141))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField146, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField161, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField176, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField192, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel158)
                    .addComponent(jLabel142))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField162, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField147, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField177, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField193, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel143)
                    .addComponent(jLabel159))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField148, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField163, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField178, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField194, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel144)
                    .addComponent(jLabel160))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel25.setBackground(new java.awt.Color(204, 204, 255));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel145, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel145.text")); // NOI18N

        jTextField149.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField149.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel146, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel146.text")); // NOI18N

        jTextField150.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField150.text")); // NOI18N

        jTextField151.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField151.text")); // NOI18N
        jTextField151.setMinimumSize(new java.awt.Dimension(236, 20));
        jTextField151.setPreferredSize(new java.awt.Dimension(123, 20));

        jTextField152.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField152.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel147, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel147.text")); // NOI18N

        jTextField153.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField153.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel148, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel148.text")); // NOI18N

        jTextField154.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField154.text")); // NOI18N

        jTextField155.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField155.text")); // NOI18N
        jTextField155.setMinimumSize(new java.awt.Dimension(236, 20));

        jTextField164.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField164.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel149, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel149.text")); // NOI18N

        jTextField165.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField165.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel150, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel150.text")); // NOI18N

        jTextField166.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField166.text")); // NOI18N

        jTextField167.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField167.text")); // NOI18N
        jTextField167.setMinimumSize(new java.awt.Dimension(236, 20));

        jTextField168.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField168.text")); // NOI18N

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField152, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(jTextField164)
                    .addComponent(jTextField168))
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField151, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField155, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(jTextField167, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addComponent(jTextField166)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel150, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addComponent(jTextField150, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel146, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addComponent(jTextField154)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel148, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addComponent(jTextField165)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel149, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addComponent(jTextField149, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel145, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addComponent(jTextField153)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel147, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel145)
                    .addComponent(jTextField149, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel146)
                    .addComponent(jTextField150, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField151, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField152, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel147)
                    .addComponent(jTextField153, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel148)
                    .addComponent(jTextField154, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField155, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField164, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel149)
                    .addComponent(jTextField165, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel150)
                    .addComponent(jTextField166, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField167, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField168, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        jPanel27.setBackground(new java.awt.Color(204, 204, 204));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel151, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel151.text")); // NOI18N

        jTextField169.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField169.text")); // NOI18N

        jLabel161.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel161, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel161.text")); // NOI18N

        jTextField170.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField170.text")); // NOI18N

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jTextField170, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 429, Short.MAX_VALUE)
                .addComponent(jTextField169, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel151, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel27Layout.createSequentialGroup()
                    .addContainerGap(341, Short.MAX_VALUE)
                    .addComponent(jLabel161)
                    .addGap(256, 256, 256)))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel151)
                    .addComponent(jTextField169, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField170, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel27Layout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(jLabel161)
                    .addContainerGap(15, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(miBarra02)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(153, 153, 153))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(miBarra02))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(284, 284, 284))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jPanel10.TabConstraints.tabTitle"), jPanel10); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel1.text")); // NOI18N

        numLineas.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.numLineas.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel2.text")); // NOI18N

        nComienzo.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.nComienzo.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel36, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel36.text")); // NOI18N

        jCheckBox1.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox1.text")); // NOI18N

        jCheckBox2.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox2, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox2.text")); // NOI18N

        jCheckBox3.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox3, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox3.text")); // NOI18N
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jCheckBox4.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox4, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox4.text")); // NOI18N

        jCheckBox5.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox5, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox5.text")); // NOI18N
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });

        jCheckBox6.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox6, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox6.text")); // NOI18N

        jCheckBox7.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox7, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox7.text")); // NOI18N

        jCheckBox8.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox8, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox8.text")); // NOI18N

        jCheckBox9.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox9, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox9.text")); // NOI18N

        jCheckBox10.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox10, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox10.text")); // NOI18N

        jCheckBox11.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox11, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox11.text")); // NOI18N

        jCheckBox12.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox12, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox12.text")); // NOI18N

        jCheckBox13.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox13, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox13.text")); // NOI18N

        jCheckBox14.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox14, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox14.text")); // NOI18N

        jCheckBox15.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox15, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox15.text")); // NOI18N

        jCheckBox16.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox16, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jCheckBox16.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel4.text")); // NOI18N

        jTextField25.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField25.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel5.text")); // NOI18N

        jTextField26.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField26.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(botonGuardaConf, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.botonGuardaConf.text")); // NOI18N

        jTextField28.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jTextField28.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(botonCalendario, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.botonCalendario.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(botonActualizaFecha, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.botonActualizaFecha.text")); // NOI18N
        botonActualizaFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizaFechaActionPerformed(evt);
            }
        });

        login.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.login.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel30, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel30.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel31, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel31.text")); // NOI18N

        passw.setText(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.passw.text")); // NOI18N

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel14, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel14.text")); // NOI18N

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ventana/logo.JPG"))); // NOI18N

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel40, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel40.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel78, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel78.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nComienzo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(numLineas, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(1514, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox1)
                                    .addComponent(jCheckBox2)
                                    .addComponent(jCheckBox3)
                                    .addComponent(jCheckBox4)
                                    .addComponent(jCheckBox5)
                                    .addComponent(jCheckBox6)
                                    .addComponent(jCheckBox7)
                                    .addComponent(jCheckBox8))
                                .addGap(76, 76, 76)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox15)
                                    .addComponent(jCheckBox12)
                                    .addComponent(jCheckBox16)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(jCheckBox9)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(botonGuardaConf))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jCheckBox10)
                                                            .addComponent(jCheckBox11))
                                                        .addGap(133, 133, 133)
                                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(botonCalendario)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(botonActualizaFecha))
                                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                    .addComponent(jLabel5)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                    .addComponent(jTextField26))
                                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                    .addComponent(jLabel4)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                .addGap(58, 58, 58)
                                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jCheckBox14)
                                            .addComponent(jCheckBox13))
                                        .addGap(46, 46, 46)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel40)
                                            .addComponent(jLabel37)))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel31)
                                .addGap(3, 3, 3)
                                .addComponent(passw, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(370, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jCheckBox1)
                            .addComponent(jCheckBox10)
                            .addComponent(jLabel4)
                            .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox2)
                            .addComponent(jCheckBox11)
                            .addComponent(jLabel5)
                            .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel37)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox4)
                    .addComponent(jCheckBox13)
                    .addComponent(jLabel78))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox5)
                    .addComponent(jCheckBox14)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox6)
                    .addComponent(jCheckBox15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox7)
                    .addComponent(jCheckBox16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox8)
                    .addComponent(jCheckBox9)
                    .addComponent(botonGuardaConf))
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(passw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonActualizaFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(155, 155, 155)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numLineas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nComienzo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jPanel2.TabConstraints.tabTitle"), jPanel2); // NOI18N

        areaDeTexto.setColumns(20);
        areaDeTexto.setRows(5);
        scrollPaneArea.setViewportView(areaDeTexto);

        areaDeTextoProcesado.setColumns(20);
        areaDeTextoProcesado.setRows(5);
        scrollPaneAreaProceso.setViewportView(areaDeTextoProcesado);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jLabel6.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(scrollPaneArea)
                        .addGap(302, 302, 302))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(scrollPaneAreaProceso, javax.swing.GroupLayout.DEFAULT_SIZE, 1476, Short.MAX_VALUE)
                        .addGap(310, 310, 310))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneArea, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneAreaProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(154, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(ResidencialTopComponent.class, "ResidencialTopComponent.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 47, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void arbolValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_arbolValueChanged
        // TODO add your handling code here:
        System.out.println("Acabo de capturar el evento!!!");
    }//GEN-LAST:event_arbolValueChanged

    private void botonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarActionPerformed
         ActualizarTablaFormulario();
         ActualizaRegistro();
    }//GEN-LAST:event_botonModificarActionPerformed

    private void jCheckBox29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox29ActionPerformed

    private void jCheckBox30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox30ActionPerformed

    private void jCheckBox31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox31ActionPerformed

    private void miTabla01MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miTabla01MouseClicked

    }//GEN-LAST:event_miTabla01MouseClicked

    private void miTabla01MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miTabla01MousePressed

    }//GEN-LAST:event_miTabla01MousePressed

    private void ListaTiempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListaTiempoActionPerformed
        // TODO add your handling code here:
        String str;
        str = ListaTiempo.getSelectedItem().toString()  ;
        System.out.println("Acabo de capturar el comboBox 2!!! Selecciono="+str);

        if (str.equals("Última semana"))      this.filtroFechaSel = 0 ;
        if (str.equals("Último mes"))         this.filtroFechaSel = 1 ;
        if (str.equals("Últimos dos meses"))  this.filtroFechaSel = 2 ;
        if (str.equals("Últimos tres meses")) this.filtroFechaSel = 3 ;
        if (str.equals("Últimos seis meses")) this.filtroFechaSel = 4 ;
        if (str.equals("Último año"))         this.filtroFechaSel = 5 ;
        if (str.equals("Todos"))              this.filtroFechaSel = 6 ;
        if (str.equals("Últimas 2 semanas"))  this.filtroFechaSel = 7 ;
        if (str.equals("Últimas 3 semanas"))  this.filtroFechaSel = 8 ;
    }//GEN-LAST:event_ListaTiempoActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String str;
        str = jComboBox1.getSelectedItem().toString()  ;
        System.out.println("Acabo de capturar el comboBox 1!!! Selecciono="+str);

        if (str.equals("PENDIENTES"))   this.filtroEstadoSel = 0 ;
        if (str.equals("DOCOUT"))       this.filtroEstadoSel = 1 ;
        if (str.equals("CERTIFICADOS")) this.filtroEstadoSel = 2 ;
        if (str.equals("TODOS"))        this.filtroEstadoSel = 3 ;
        if (str.equals("KOs"))          this.filtroEstadoSel = 4 ;
        if (str.equals("PUNTEADOS"))    this.filtroEstadoSel = 5 ;
        if (str.equals("NO PUNTEADOS")) this.filtroEstadoSel = 6 ;
        if (str.equals("VALIDADOS"))    this.filtroEstadoSel = 7 ;
        if (str.equals("ERROR WEB SALES")) this.filtroEstadoSel = 8 ;
        if (str.equals("DECOMISIONADO")) this.filtroEstadoSel = 9 ;
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void ListaProvinciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListaProvinciasActionPerformed

        String str;
        str = ListaProvincias.getSelectedItem().toString()  ;
        System.out.println("Acabo de capturar el comboBox ListaProvincias!!! Selecciono="+str);

        if (str.equals("TODAS PROVINCIAS"))     this.filtroProvincia = 0 ;
        if (str.equals("CASTELLÓN"))            this.filtroProvincia = 1 ;
        if (str.equals("VALENCIA"))             this.filtroProvincia = 2 ;
        if (str.equals("ALICANTE"))             this.filtroProvincia = 3 ;
        if (str.equals("OTRAS"))                this.filtroProvincia = 4 ;
    }//GEN-LAST:event_ListaProvinciasActionPerformed

    private void ListaAgentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListaAgentesActionPerformed

        String str;
        str = ListaAgentes.getSelectedItem().toString()  ;
        System.out.println("Acabo de capturar el comboBox ListaAgentes!!! Selecciono="+str);

        if (str.equals("TODOS AGENTES"))     this.filtroAgente = 0 ;
        if (str.equals("J & C"))             this.filtroAgente = 1 ;
        if (str.equals("ETP"))               this.filtroAgente = 2 ;
        if (str.equals("NADINE"))            this.filtroAgente = 3 ;
        if (str.equals("EMILIO-RAQUEL"))     this.filtroAgente = 4 ;
        if (str.equals("SERNOVEN"))          this.filtroAgente = 5 ;
        if (str.equals("MIGUEL"))            this.filtroAgente = 6 ;
        if (str.equals("SHEILA"))            this.filtroAgente = 7 ;
        if (str.equals("MARIO SORIA"))       this.filtroAgente = 8 ;
        if (str.equals("ALBERTO"))           this.filtroAgente = 9 ;
        if (str.equals("AGENTE 01"))         this.filtroAgente = 10 ;
        if (str.equals("AGENTE 02"))         this.filtroAgente = 11 ;
        if (str.equals("AGENTE 03"))         this.filtroAgente = 12 ;
        if (str.equals("AGENTE 04"))         this.filtroAgente = 13 ;
        
    }//GEN-LAST:event_ListaAgentesActionPerformed

    private void FiltroIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FiltroIncidenciaActionPerformed
        // .................................... Marcar filtro de Lista de consulta makro
        String str;
        str = FiltroIncidencia.getSelectedItem().toString()  ;
        System.out.println("Acabo de capturar el filtro de incidencias!!! Selecciono="+str);

        this.filtroIncidencia = 7 ;
        if (str.equals("Filtrar por Incidencia..."))             this.filtroIncidencia = 7;
        if (str.equals("BLANCA"))                this.filtroIncidencia = 0;
        if (str.equals("AMARILLA"))              this.filtroIncidencia = 1 ;
        if (str.equals("NARANJA"))               this.filtroIncidencia = 2 ;
        if (str.equals("ROJA"))                  this.filtroIncidencia = 3 ;
        if (str.equals("VERDE"))                 this.filtroIncidencia = 4 ;
        if (str.equals("MORADA"))                this.filtroIncidencia = 5 ;
        if (str.equals("AZUL"))                  this.filtroIncidencia = 6 ;
        if (str.equals("TODAS"))                 this.filtroIncidencia = 7 ;
    }//GEN-LAST:event_FiltroIncidenciaActionPerformed

    private void ListaOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListaOrdenActionPerformed
        // .................................... Marcar filtro de orden de consulta makro
        String str;
        str = ListaOrden.getSelectedItem().toString()  ;
        System.out.println("Acabo de capturar el comboBox Ordenar !!! Selecciono="+str);

        if (str.equals("Ordenar por id Asc"))               this.filtroMakro = 0 ;
        if (str.equals("Ordenar por TITULAR"))              this.filtroMakro = 1 ;
        if (str.equals("Ordenar por Municipio"))            this.filtroMakro = 2 ;
        if (str.equals("Ordenar por id Desc"))              this.filtroMakro = 3 ;
        if (str.equals("Ordenar por NIF"))                  this.filtroMakro = 4 ;
    }//GEN-LAST:event_ListaOrdenActionPerformed

    private void ListaComercialLiqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListaComercialLiqActionPerformed

        // .................................... Marcar filtro de liquidacion comerciales
        String str;
        str = ListaComercialLiq.getSelectedItem().toString()  ;
        System.out.println("Acabo de capturar el comboBox comercial liquidaciones !!! Selecciono="+str);
        this.filtroComercial = 0 ;
        if (str.equals("J & C Asesores"))       this.filtroComercial = 1;
        if (str.equals("ETP"))                  this.filtroComercial = 2 ;
        if (str.equals("NADINE"))               this.filtroComercial = 3 ;
        if (str.equals("EMILIO-RAQUEL"))        this.filtroComercial = 4 ;
        if (str.equals("SERNOVEN"))             this.filtroComercial = 5 ;
        if (str.equals("TODOS"))                this.filtroComercial = 0 ;
        if (str.equals("MIGUEL"))               this.filtroComercial = 6 ;
        if (str.equals("SHEILA"))               this.filtroComercial = 7 ;
        if (str.equals("MARIO SORIA"))          this.filtroComercial = 8 ;
        if (str.equals("ALBERTO"))              this.filtroComercial = 9 ;
        if (str.equals("AGENTE 01"))            this.filtroComercial = 10 ;
        if (str.equals("AGENTE 02"))            this.filtroComercial = 11 ;
        if (str.equals("AGENTE 03"))            this.filtroComercial = 12 ;
        if (str.equals("AGENTE 04"))            this.filtroComercial = 13 ;
        
        if (this.filtroComercial == 0)  this.filtroComercial = 1; 
        
        actualizarTablaComisiones(this.filtroComercial);
    }//GEN-LAST:event_ListaComercialLiqActionPerformed

    private void ListaZonaLiqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListaZonaLiqActionPerformed
        // .................................... Marcar filtro de Lista de consulta makro
        String str;
        str = ListaZonaLiq.getSelectedItem().toString()  ;
        System.out.println("Acabo de capturar el zona liquidaciones!!! Selecciono="+str);

        this.filtroZonaLiq = 0 ;
        if (str.equals("CASTELLON"))            this.filtroZonaLiq = 1;
        if (str.equals("VALENCIA"))             this.filtroZonaLiq = 2 ;
        if (str.equals("OTRAS"))                this.filtroZonaLiq = 3 ;
        if (str.equals("TODAS"))                this.filtroZonaLiq = 4 ;
    }//GEN-LAST:event_ListaZonaLiqActionPerformed

    private void jTextField42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField42ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField42ActionPerformed

    private void jTextField44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField44ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField44ActionPerformed

    private void jTextField50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField50ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField50ActionPerformed

    private void jTextField54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField54ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField54ActionPerformed

    private void jTextField112ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField112ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField112ActionPerformed

    private void jTextField64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField64ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField64ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void botonActualizaFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizaFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonActualizaFechaActionPerformed

    private void botonRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRefrescarActionPerformed
        RefrescarTablaBD();
        modificarArbolNuevos();
        CuentaTitularesRepetidos();
    }//GEN-LAST:event_botonRefrescarActionPerformed

    private void generaExelMakroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generaExelMakroActionPerformed
             GuardarArchivoExelResultados();
    }//GEN-LAST:event_generaExelMakroActionPerformed

    private void botonActualizarCalculosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarCalculosActionPerformed
          actualizarCalculosLiquidaciones();
    }//GEN-LAST:event_botonActualizarCalculosActionPerformed

    private void botonBuscarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarTelefonoActionPerformed
       buscaTelefono();
    }//GEN-LAST:event_botonBuscarTelefonoActionPerformed

    private void botonBuscarTitularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarTitularActionPerformed
        buscaTitular();
    }//GEN-LAST:event_botonBuscarTitularActionPerformed

    private void botonBuscarNIFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarNIFActionPerformed
        buscaNIF();
    }//GEN-LAST:event_botonBuscarNIFActionPerformed

    private void botonBuscarCUPSgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarCUPSgActionPerformed
        buscarCUPSg();
    }//GEN-LAST:event_botonBuscarCUPSgActionPerformed

    private void botonBuscarCUPSeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarCUPSeActionPerformed
        buscarCUPSe();
    }//GEN-LAST:event_botonBuscarCUPSeActionPerformed

    private void botonBuscarIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarIDActionPerformed
       buscarID();
    }//GEN-LAST:event_botonBuscarIDActionPerformed

    private void reduceFuenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reduceFuenteActionPerformed
        reduceFuenteTabla();
    }//GEN-LAST:event_reduceFuenteActionPerformed

    private void aumentaFuenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aumentaFuenteActionPerformed
        aumentaFuenteTabla();
    }//GEN-LAST:event_aumentaFuenteActionPerformed

    private void botonGeneraExelLiquidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGeneraExelLiquidaActionPerformed
         GenerarExelLiquida();   
    }//GEN-LAST:event_botonGeneraExelLiquidaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cargarDatosCertificaciones();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void botonActualizarCalculos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarCalculos1ActionPerformed
        actualizarCalculosCertificaciones();
    }//GEN-LAST:event_botonActualizarCalculos1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField187ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField187ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField187ActionPerformed

    private void jTextField163ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField163ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField163ActionPerformed

    private void jTextField159ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField159ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField159ActionPerformed

    private void jTextField145ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField145ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField145ActionPerformed

    private void jTextField140ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField140ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField140ActionPerformed

    private void jTextField138ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField138ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField138ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox FiltroIncidencia;
    private javax.swing.JComboBox ListaAgentes;
    private javax.swing.JComboBox ListaComercialLiq;
    private javax.swing.JComboBox ListaOrden;
    private javax.swing.JComboBox ListaProvincias;
    private javax.swing.JComboBox ListaTiempo;
    private javax.swing.JComboBox ListaZonaLiq;
    private javax.swing.JButton actuaMunicipio;
    private javax.swing.JTree arbol;
    private javax.swing.JTextArea areaDeTexto;
    private javax.swing.JTextArea areaDeTextoProcesado;
    private javax.swing.JButton aumentaFuente;
    private javax.swing.JButton botonActualizaFecha;
    private javax.swing.JButton botonActualizarCalculos;
    private javax.swing.JButton botonActualizarCalculos1;
    private javax.swing.JButton botonBuscarCUPSe;
    private javax.swing.JButton botonBuscarCUPSg;
    private javax.swing.JButton botonBuscarID;
    private javax.swing.JButton botonBuscarNIF;
    private javax.swing.JButton botonBuscarTelefono;
    private javax.swing.JButton botonBuscarTitular;
    private javax.swing.JButton botonCalendario;
    private javax.swing.JButton botonGeneraExelLiquida;
    private javax.swing.JButton botonGuardaConf;
    private javax.swing.JButton botonModificar;
    private javax.swing.JButton botonRefrescar;
    private javax.swing.JButton generaExelMakro;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox15;
    private javax.swing.JCheckBox jCheckBox16;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox23;
    private javax.swing.JCheckBox jCheckBox24;
    private javax.swing.JCheckBox jCheckBox25;
    private javax.swing.JCheckBox jCheckBox26;
    private javax.swing.JCheckBox jCheckBox27;
    private javax.swing.JCheckBox jCheckBox28;
    private javax.swing.JCheckBox jCheckBox29;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox30;
    private javax.swing.JCheckBox jCheckBox31;
    private javax.swing.JCheckBox jCheckBox32;
    private javax.swing.JCheckBox jCheckBox33;
    private javax.swing.JCheckBox jCheckBox34;
    private javax.swing.JCheckBox jCheckBox35;
    private javax.swing.JCheckBox jCheckBox36;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField100;
    private javax.swing.JTextField jTextField101;
    private javax.swing.JTextField jTextField102;
    private javax.swing.JTextField jTextField103;
    private javax.swing.JTextField jTextField104;
    private javax.swing.JTextField jTextField105;
    private javax.swing.JTextField jTextField106;
    private javax.swing.JTextField jTextField107;
    private javax.swing.JTextField jTextField108;
    private javax.swing.JTextField jTextField109;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField110;
    private javax.swing.JTextField jTextField111;
    private javax.swing.JTextField jTextField112;
    private javax.swing.JTextField jTextField113;
    private javax.swing.JTextField jTextField114;
    private javax.swing.JTextField jTextField115;
    private javax.swing.JTextField jTextField116;
    private javax.swing.JTextField jTextField117;
    private javax.swing.JTextField jTextField118;
    private javax.swing.JTextField jTextField119;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField120;
    private javax.swing.JTextField jTextField121;
    private javax.swing.JTextField jTextField122;
    private javax.swing.JTextField jTextField123;
    private javax.swing.JTextField jTextField124;
    private javax.swing.JTextField jTextField125;
    private javax.swing.JTextField jTextField126;
    private javax.swing.JTextField jTextField127;
    private javax.swing.JTextField jTextField128;
    private javax.swing.JTextField jTextField129;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField130;
    private javax.swing.JTextField jTextField131;
    private javax.swing.JTextField jTextField132;
    private javax.swing.JTextField jTextField133;
    private javax.swing.JTextField jTextField134;
    private javax.swing.JTextField jTextField135;
    private javax.swing.JTextField jTextField136;
    private javax.swing.JTextField jTextField137;
    private javax.swing.JTextField jTextField138;
    private javax.swing.JTextField jTextField139;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField140;
    private javax.swing.JTextField jTextField141;
    private javax.swing.JTextField jTextField142;
    private javax.swing.JTextField jTextField143;
    private javax.swing.JTextField jTextField144;
    private javax.swing.JTextField jTextField145;
    private javax.swing.JTextField jTextField146;
    private javax.swing.JTextField jTextField147;
    private javax.swing.JTextField jTextField148;
    private javax.swing.JTextField jTextField149;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField150;
    private javax.swing.JTextField jTextField151;
    private javax.swing.JTextField jTextField152;
    private javax.swing.JTextField jTextField153;
    private javax.swing.JTextField jTextField154;
    private javax.swing.JTextField jTextField155;
    private javax.swing.JTextField jTextField156;
    private javax.swing.JTextField jTextField157;
    private javax.swing.JTextField jTextField158;
    private javax.swing.JTextField jTextField159;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField160;
    private javax.swing.JTextField jTextField161;
    private javax.swing.JTextField jTextField162;
    private javax.swing.JTextField jTextField163;
    private javax.swing.JTextField jTextField164;
    private javax.swing.JTextField jTextField165;
    private javax.swing.JTextField jTextField166;
    private javax.swing.JTextField jTextField167;
    private javax.swing.JTextField jTextField168;
    private javax.swing.JTextField jTextField169;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField170;
    private javax.swing.JTextField jTextField171;
    private javax.swing.JTextField jTextField172;
    private javax.swing.JTextField jTextField173;
    private javax.swing.JTextField jTextField174;
    private javax.swing.JTextField jTextField175;
    private javax.swing.JTextField jTextField176;
    private javax.swing.JTextField jTextField177;
    private javax.swing.JTextField jTextField178;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField186;
    private javax.swing.JTextField jTextField187;
    private javax.swing.JTextField jTextField188;
    private javax.swing.JTextField jTextField189;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField190;
    private javax.swing.JTextField jTextField191;
    private javax.swing.JTextField jTextField192;
    private javax.swing.JTextField jTextField193;
    private javax.swing.JTextField jTextField194;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField44;
    private javax.swing.JTextField jTextField45;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField48;
    private javax.swing.JTextField jTextField49;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField50;
    private javax.swing.JTextField jTextField51;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField53;
    private javax.swing.JTextField jTextField54;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField57;
    private javax.swing.JTextField jTextField58;
    private javax.swing.JTextField jTextField59;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField60;
    private javax.swing.JTextField jTextField61;
    private javax.swing.JTextField jTextField62;
    private javax.swing.JTextField jTextField63;
    private javax.swing.JTextField jTextField64;
    private javax.swing.JTextField jTextField65;
    private javax.swing.JTextField jTextField66;
    private javax.swing.JTextField jTextField67;
    private javax.swing.JTextField jTextField68;
    private javax.swing.JTextField jTextField69;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField70;
    private javax.swing.JTextField jTextField71;
    private javax.swing.JTextField jTextField72;
    private javax.swing.JTextField jTextField73;
    private javax.swing.JTextField jTextField74;
    private javax.swing.JTextField jTextField75;
    private javax.swing.JTextField jTextField76;
    private javax.swing.JTextField jTextField77;
    private javax.swing.JTextField jTextField78;
    private javax.swing.JTextField jTextField79;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField80;
    private javax.swing.JTextField jTextField81;
    private javax.swing.JTextField jTextField82;
    private javax.swing.JTextField jTextField83;
    private javax.swing.JTextField jTextField84;
    private javax.swing.JTextField jTextField85;
    private javax.swing.JTextField jTextField86;
    private javax.swing.JTextField jTextField87;
    private javax.swing.JTextField jTextField88;
    private javax.swing.JTextField jTextField89;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jTextField90;
    private javax.swing.JTextField jTextField91;
    private javax.swing.JTextField jTextField92;
    private javax.swing.JTextField jTextField93;
    private javax.swing.JTextField jTextField94;
    private javax.swing.JTextField jTextField95;
    private javax.swing.JTextField jTextField96;
    private javax.swing.JTextField jTextField97;
    private javax.swing.JTextField jTextField98;
    private javax.swing.JTextField jTextField99;
    private javax.swing.JTextField login;
    private javax.swing.JScrollPane miBarra01;
    private javax.swing.JScrollPane miBarra02;
    private javax.swing.JScrollPane miBarra03;
    private javax.swing.JScrollPane miBarraCer;
    private javax.swing.JScrollPane miBarraLocu;
    private javax.swing.JTable miTabla01;
    private javax.swing.JTable miTablaCer;
    private javax.swing.JTable miTablaCertifica;
    private javax.swing.JTable miTablaLiquida;
    private javax.swing.JTable miTablaLocu;
    private javax.swing.JTextField nComienzo;
    private javax.swing.JTextField numLineas;
    private javax.swing.JPasswordField passw;
    private javax.swing.JButton reduceFuente;
    private javax.swing.JScrollPane scrollPaneArea;
    private javax.swing.JScrollPane scrollPaneAreaProceso;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    private File abrirArchivo() {
        
        JFileChooser jfc = new JFileChooser();        
        jfc.showOpenDialog(this);
        File abre=jfc.getSelectedFile();
        
     
        JOptionPane.showMessageDialog(null,
                "\nEl nombre del archivo es:"+abre,
                "AVISO",JOptionPane.WARNING_MESSAGE);
	return abre;
}
 
 public void conectarBDBoton() {
      
     conectarConBD();  
     modificarArbolNuevos();
     CuentaTitularesRepetidos();
     actualizarTablaComisiones(1) ; // Iniciamos tabla liquidacines con precios de comercial generico
     actualizarTablaIngresos() ;    // Cargamos las comisiones de las cerficaciones
     
 }
    
 // .......................................................................
private void abrirArchivo2() {
    
        JFileChooser jfc = new JFileChooser();        
        jfc.showOpenDialog(this);
        File abre=jfc.getSelectedFile();
        
        JOptionPane.showMessageDialog(null,
                "\nEl nombre del archivo es:"+abre,
                "AVISO",JOptionPane.WARNING_MESSAGE);
        
        this.nombre = abre ;
        
        try {
            this.archivo = mostrarArchivo(this.nombre);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
           
        areaDeTexto.setText(this.archivo);            
        
        
	return;
}
 // .......................................................................
private void abrirArchivo3() {
        JFileChooser jfc = new JFileChooser();        
        jfc.showOpenDialog(this);
        File abre=jfc.getSelectedFile();
       
        JOptionPane.showMessageDialog(null,
                "\nEl nombre del archivo es:"+abre,
                "AVISO",JOptionPane.WARNING_MESSAGE);
        
        this.nombre = abre ;
        
          try {           
                this.archivo = mostrarArchivo(this.nombre);
            } catch (IOException ex) {
      //          Logger.getLogger(ClaseFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            areaDeTexto.setText(this.archivo);            
        
        
	return;
}
 // .......................................................................
private void procesarArchivo() {
     // LLama a función procesar archivo
     
            Proceso myProces ;             // Hacemos una instancia de la clase de  de proceso de registros
            myProces = new Proceso(this.tablaConfiguracion);
                 
                int p = 2 ;
                int n = 10000 ;                 // ojo!!! valores de prueba, 
            try {  

                 this.texto = myProces.ProcesaDatosReales(this.nombre,n,p);
                 this.tablaLocuciones   = myProces.tablaLocuciones ;
                 this.nLocuciones       = myProces.nLocuciones ; 
                 this.nDias             = myProces.nDias ;
                 this.texto             = myProces.sMensajes ;
       
                 areaDeTextoProcesado.setText(this.texto);         
                //  modificarArbolNuevos();      
                 actualizarFormulario(0) ;

            } catch (IOException ex) {

   //              Logger.getLogger(ClaseFrame.class.getName()).log(Level.SEVERE, null, ex);

            }  
    
    
}
 // .......................................................................
private void procesarArchivoCertificaciones() {
     // LLama a función procesar archivo
     
            Proceso myProces ;             // Hacemos una instancia de la clase de  de proceso de registros
            myProces = new Proceso(this.tablaConfiguracion);
                 
                int p = 2 ;
                int n = 500 ;                 // ojo!!! valores de prueba, quitar!!!!
            try {  

                 this.texto = myProces.ProcesaDatosCertificaciones(this.nombre,n,p);
                 this.tablaCertificaciones   = myProces.tablaCertificaciones ;
                 this.nCertificaciones       = myProces.nCertificaciones ; 
              
                 areaDeTextoProcesado.setText(this.texto);         
                  
                 actualizarFormulario(0) ;

            } catch (IOException ex) {

        //         Logger.getLogger(ClaseFrame.class.getName()).log(Level.SEVERE, null, ex);

            }  
    
    
}

private void comprobarRegistros() {
    /*
            Proceso myProces ;             // Hacemos una instancia de la clase de proceso de registros
            myProces = new Proceso(this.tablaConfiguracion);
         
            progressMonitor = new ProgressMonitor(null,
                                  "Punteando automáticamente registros",
                                  "", 0, 100);
                   
            
             progressMonitor.setProgress(0);
            
            
     
          //  myProces.addPropertyChangeListener(this);
            myProces.execute();
            myProces.punteaRegistros(this.nRegistros,this.tablaDatos,this.plogin,this.ppassword);
            progressMonitor.setProgress(100);
            this.Incompletos = myProces.nIncompletos ; 
            this.Completos   = myProces.nCompletos;
            
            this.tablaDatosIncpl    = myProces.tablaRes1 ;
            this.tablaDatosCmpl     = myProces.tablaRes2 ;
            this.tablaErrores       = myProces.tablaErrores;
            this.tablaErrorCod      = myProces.tablaErrorCod ;
                     
           this.texto = myProces.sMensajes ;       
           areaDeTextoProcesado.setText(this.texto);  
           
            actualizarFormulario(-1);
    */
}
 // .......................................................................
public void conectarConBD(){
    
             String Str1 = login.getText();
             String Str2 = passw.getText();
             conectarBD(Str1,Str2);    
}
 // .......................................................................
public void conectarGlobal(){
    
            conectarConBD();
            modificarArbolNuevos();
            CuentaTitularesRepetidos();  
}


private String mostrarArchivo(File abre) throws FileNotFoundException, IOException {
            
         String aux=""; 		
	 String texto="";
             /*recorremos el archivo, lo leemos para plasmarlo
	  *en el area de texto*/
	 if(abre!=null)
	 { 				
	 	FileReader archivos=new FileReader(abre);
	 	BufferedReader lee=new BufferedReader(archivos);
	 	while((aux=lee.readLine())!=null)
	 	{
                    texto+= aux+ "\n";
	 	}

                lee.close();
	 	} 			
        return texto;
}       
 
        /**
		 * Guardamos el archivo del area 
		 * de texto en el equipo
		 */
	private void guardarArchivo() 
{
        int i, nStr,estadoInsert;
        int L,G,D,s1,s2,s3,s4,s5,s9 ;
        String str;
       
        estadoInsert = 0 ;
            
            PymesDao miPymeDao = new PymesDao();
            
        //    try {
                System.out.println("Voy a comenzar la inserción ");
                PymesVo miPymes = new PymesVo();
                for (i=0 ; i < this.nRegistros; i++){
                    
                        // ..............................................
                        
                        this.tablaDatos[i][0] = "0" ;                   // Fijamos el estado como pendiente
                        // ..............................................
                        
                        
                        if  (this.tablaDatos[i][4].equals("") ) L=1 ; else L = 0 ;
           
                        if  (this.tablaDatos[i][3].equals("") ) G=1; else G = 0 ;
           
                        if  (this.tablaDatos[i][3].equals("") && this.tablaDatos[i][4].equals("") ) {L=0; G = 0; D=1;} else D = 0 ;
           
                        // ..............................................
                        
                        if (jCheckBox23.isSelected()) {this.tablaDatos[i][34] = "1" ; System.out.println("ESTABA SELECCIONADO");}
                        if (jCheckBox24.isSelected()) {this.tablaDatos[i][35] = "1" ; System.out.println("ESTABA SELECCIONADO");}
                        if (jCheckBox25.isSelected()) {this.tablaDatos[i][36] = "1" ; System.out.println("ESTABA SELECCIONADO");}
                        if (jCheckBox26.isSelected()) {this.tablaDatos[i][37] = "1" ; System.out.println("ESTABA SELECCIONADO");}
                        if (jCheckBox27.isSelected()) {this.tablaDatos[i][38] = "1" ; System.out.println("ESTABA SELECCIONADO");}
              
                        if (this.tablaDatos[i][34].equals("1") )  s1=1; else s1=0 ;         // SVGComplet
        
                        if (this.tablaDatos[i][35].equals("1") )  s2=1; else s2=0 ;          // SVGXpres
        
                        if (this.tablaDatos[i][36].equals("1") )   s3=1; else s3=0 ;         // SVG Básico
        
                        if (this.tablaDatos[i][37].equals("1") )   s4=1; else s4=0 ;         //SVELECTRIC XPRES
        
                        if (this.tablaDatos[i][38].equals("1") )   s5=1; else s5=0 ;  
                        
                        if (this.tablaDatos[i][51].equals("1") )   s9=1; else s9=0 ;        // SPP
         
                        // ...........................................................................................
                        
                        System.out.println("Insertando registro i= "+i+ " - Incidencia= "+this.tablaDatos[i][1]);
                                           
                        miPymes.setEstado(Integer.parseInt(this.tablaDatos[i][0]));
                        miPymes.setIncidencia(Integer.parseInt(this.tablaDatos[i][1]));
                        miPymes.setFecha(this.tablaDatos[i][2]);
                       
                        miPymes.setComercial(this.tablaDatos[i][17]);
                        
                        miPymes.setSwg(G);
                        miPymes.setSwe(L);
                        miPymes.setDualFuel(D);
                                                
                        miPymes.setCupsE(this.tablaDatos[i][4]);
                        miPymes.setCupsG(this.tablaDatos[i][3]);       
                                              
                        miPymes.setCodPostal(Integer.parseInt(this.tablaDatos[i][5]));
                        miPymes.setMunicipio(this.tablaDatos[i][6]);
                        miPymes.setProvincia(this.tablaDatos[i][7]);
                        miPymes.setDireccion(this.tablaDatos[i][8]);
                        miPymes.setTitular(this.tablaDatos[i][9]);
                        miPymes.setNifCif(this.tablaDatos[i][10]);
                        miPymes.setTelefonoCli(this.tablaDatos[i][16]);  
                        miPymes.setFechaFirma(this.tablaDatos[i][11]);
                        
                         try {
                            miPymes.setConsumoElect(Double.parseDouble(this.tablaDatos[i][12]));
                            miPymes.setConsumoGas(Double.parseDouble(this.tablaDatos[i][13]));
                            
                         } catch (NumberFormatException nfe){
                            miPymes.setConsumoElect(Double.parseDouble("0"));
                            miPymes.setConsumoGas(Double.parseDouble("0"));
                         }
                       
                        miPymes.setSVG_1(s1);
                        miPymes.setSVG_2(s2);
                        miPymes.setSVG_3(s3);
                        miPymes.setSVG_4(s4);
                        miPymes.setSVG_5(s5);
                        
                        miPymes.setSPP(s9);
                        
                              
                        miPymes.setObservaciones(this.tablaDatos[i][15]);   
                          
                        estadoInsert = miPymeDao.registrarContrato(miPymes,this.plogin,this.ppassword);
                        System.out.println("Registro insertado ");
                        
                }
                 if (estadoInsert == 0) {
                    
                                         JOptionPane.showMessageDialog(null,
					"Se ha registrado Exitosamente", "Información",
					JOptionPane.INFORMATION_MESSAGE);
                    
                } else { if (estadoInsert == 2) {
                                        JOptionPane.showMessageDialog(null,
					"No se Registro algún apunte, verifique la consola para ver el error",
					"Error", JOptionPane.ERROR_MESSAGE);
                    }
                }  
        
}
        private void guardarArchivoNombre(File fnombre) 
{
        String strnombre= fnombre.getAbsolutePath();
    
         JOptionPane.showMessageDialog(null,
            "\nVoy a guardar  el archivo temporal !! nombre="+strnombre,
	    "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);
	try
	{
            
	   	
	    if(strnombre !=null)
	    {
		
		 /*guardamos el archivo y le damos el formato directamente,
		 * si queremos que se guarde en formato doc lo definimos como .doc*/
		 FileWriter  save=new FileWriter(strnombre+"_tmp");
		 save.write(areaDeTextoProcesado.getText());
		 save.close();
		 JOptionPane.showMessageDialog(null,
		 "El archivo se a guardado Exitosamente",
		 "Información",JOptionPane.INFORMATION_MESSAGE);
	    }
	 }
	 catch(IOException ex)
         {
		  JOptionPane.showMessageDialog(null,
		  "Su archivo no se ha guardado",
		  "Advertencia",JOptionPane.WARNING_MESSAGE);
	 }
}
    private void conectarBD(String Str1,String Str2) {
    
        JOptionPane.showMessageDialog(null,
                "\nVoy a conectar con la Base de Datos LOGIN="+Str1,
                "AVISO",JOptionPane.WARNING_MESSAGE);
        
     //   ConexionBD miVentanaConexion=new ConexionBD(miVentanaPrincipal,true);
     //   miVentanaConexion.setVisible(true);
     //    Conectar miVentanaConexion=new Conectar(miVentanaPrincipal,true);
     //   miVentanaConexion.setVisible(true);
       if (Str1 !="" && Str2 !=""){ 
           
           this.plogin      = Str1;
           this.ppassword   = Str2;
           
           mostrarDatosConTableModel(); 
           
       Conexion conex = new Conexion(Str1,Str2);
       } else  {
            JOptionPane.showMessageDialog(null,
		  "Debes introducir login y password",
		  "Advertencia",JOptionPane.WARNING_MESSAGE);
           
       }
} 
     public final void crearArbol() {
           
               System.out.println("Voy a crear el arbol (si puedo) ");
             
            /**Construimos los nodos del arbol que seran ramas u hojas*/
            /**Definimos cual será el directorio principal o la raiz de nuestro arbol*/
            
               DefaultMutableTreeNode carpetaRaiz= new DefaultMutableTreeNode("CONTRATOS");
          
              
             /**Definimos el modelo donde se agregaran los nodos*/
            
               DefaultTreeModel modelo2;
              modelo2 = new DefaultTreeModel(carpetaRaiz);
              
             /**agregamos el modelo al arbol, donde previamente establecimos la raiz*/
              
             arbol = new JTree(modelo2);
             jScrollPane1.setViewportView(arbol);
             
}
     // ----------------------------------------------------------------------------------------------------------------
       public final void modificarArbolNuevos() {
          
           int i,j,k,cnt,nCUPS,dia=1,ndia=0,cdia=1,ind=0;
           String fecha ="",contrato="";
           String nombre = "";
           String sdia="",CUPS,str="";
           
            RenderArbol myRenderArbol ;
       //     myRenderArbol = new RenderArbol();            
       //     myRenderArbol.locuciones = this.locuciones ;
           
           
           cnt = this.nLocuciones ;
                      
           System.out.println("Voy a modificar el arbol nuevo tenemos un total de locuciones de:"+cnt+" y de Certificaciones de:"+this.nCertificaciones);
           
           DefaultMutableTreeNode carpetaRaiz = new DefaultMutableTreeNode("CONTRATOS");
           /**Definimos el modelo donde se agregaran los nodos*/
           DefaultTreeModel modelo2;
           modelo2 = new DefaultTreeModel(carpetaRaiz);
           /**agregamos el modelo al arbol, donde previamente establecimos la raiz*/
           
           arbol = new JTree(modelo2);
           jScrollPane1.setViewportView(arbol);
           
           DefaultMutableTreeNode carpeta = new DefaultMutableTreeNode("CARGADOS ("+this.nRegistros+")");     // Comenzamos con el primer dia
           modelo2.insertNodeInto(carpeta, carpetaRaiz, 0);
           
          
            for (i=0; i<this.nRegistros; i++){
               CUPS = this.tablaDatos[i][9] ;                           // Insertamos primero el nombre Titular
               CUPS = CUPS.trim();
               nCUPS = CUPS.length();
               
               str="" ;
               if (this.tablaDatos[i][0].equals("3")) str=" (KO)";
               
               
               if ( nCUPS>0){
                    DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(i+" "+this.tablaDatos[i][9]+str);
                    modelo2.insertNodeInto(archivo, carpeta, i);       
               } else {                                                 // Si no hay cups electrico, probamos a poner el de gas
                  
                   
                        DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(i+" -"+str);
                        modelo2.insertNodeInto(archivo, carpeta, i); 
                                 
              }
           }    
            // .........................................................................................................................
           k = 0;
           DefaultMutableTreeNode carpeta3 = new DefaultMutableTreeNode("CERTIFICACIONES ("+this.nCertificaciones+")");     
           modelo2.insertNodeInto(carpeta3, carpetaRaiz, 1);
           cnt = this.nCertificaciones ;
           
           if ( cnt >0 ){
               
                for (i=0; i<cnt; i++){
                   
                  
                        contrato = this.tablaCertificaciones[i][10] ;                           // Insertamos primero el nº de contrato
                        fecha = fecha.trim();
                        nombre = this.tablaCertificaciones[i][11];
                        
                        DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(this.tablaCertificaciones[i][30] +" -"+contrato+" -"+nombre);
                        modelo2.insertNodeInto(archivo, carpeta3, k);  k++;     
                    
                }
           }
           // ......................................................................................................................... 
           cnt = this.nLocuciones ;
           sdia = this.tablaLocuciones[0][20] ; 
          
           DefaultMutableTreeNode carpeta2 = new DefaultMutableTreeNode("Locución día "+sdia);     // Comenzamos con el primer dia
           modelo2.insertNodeInto(carpeta2, carpetaRaiz, 2);
           
           System.out.println("Locucion dia "+sdia);
           
           k = 0;
           for (i=0; i<cnt; i++){
                    sdia = this.tablaLocuciones[i][20] ; 
                    ndia = Integer.parseInt(sdia);
               
                    if ( ndia == cdia) {
                        fecha = this.tablaLocuciones[i][0] ;                           // Insertamos primero La fecha
                        fecha = fecha.trim();
                        nombre = this.tablaLocuciones[i][2];     
                        DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(this.tablaLocuciones[i][21]+" -"+fecha+"-"+nombre);
                        System.out.println(this.tablaLocuciones[i][21]+" -"+fecha+"-"+nombre);
                        modelo2.insertNodeInto(archivo, carpeta2, k);  k++;     
                    } else {                                                 // Si no hay cups electrico, probamos a poner el de gas
                        dia ++; 
                        cdia = Integer.parseInt(sdia);
                        sdia = this.tablaLocuciones[i][20] ; 
                        carpeta2 = new DefaultMutableTreeNode("Locución día "+sdia);     // insertamos un nuevo dia
                        System.out.println("Locucion dia"+sdia);
                        modelo2.insertNodeInto(carpeta2, carpetaRaiz, dia+1);
                        k = 0;
                        fecha = this.tablaLocuciones[i][0] ;                           // Insertamos primero La fecha
                        fecha = fecha.trim();
                        nombre = this.tablaLocuciones[i][2];
                        DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(this.tablaLocuciones[i][21]+" -"+fecha+"-"+nombre);
                        System.out.println(this.tablaLocuciones[i][21]+" -"+fecha+"-"+nombre);
                        modelo2.insertNodeInto(archivo, carpeta2, k);  k++;                           
                    }
           }
           
         
           
            // ................................................................................
           
            arbol.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
           
            public void valueChanged(TreeSelectionEvent e) {
                // se obtiene el nodo seleccionado
                DefaultMutableTreeNode nseleccionado = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();
    
                int nivel = nseleccionado.getDepth() ;
                System.out.println("El nivel de campo es ="+nivel);
                
                if ( nivel == 0) {
                    
                    String nodo         = nseleccionado.getUserObject().toString() ;
                    String [] campos    = nodo.split("\\s+");
                    int indice          = Integer.parseInt(campos[0]);
                    
                    System.out.println("El indice de campo es ="+indice);
                    actualizarFormulario(indice);
                    
                }
            }

            
               
            }); 
            
           
            
           // ................................................................................
         //  arbol.setCellRenderer(myRenderArbol);//añadimos nuevo renderer
           // ................................................................................
          
       }
       
       // ----------------------------------------------------------------------------------------------------------------
       
       public final void modificarArbol() {
           int i,cnt,nCUPS,ind,dia=1;
           String CUPS="";
          
           System.out.println("Voy a modificar el arbol (si puedo) ");
           
           DefaultMutableTreeNode carpetaRaiz = new DefaultMutableTreeNode("REGISTROS");
           /**Definimos el modelo donde se agregaran los nodos*/
           DefaultTreeModel modelo2;
           modelo2 = new DefaultTreeModel(carpetaRaiz);
           /**agregamos el modelo al arbol, donde previamente establecimos la raiz*/
           
           arbol = new JTree(modelo2);
           jScrollPane1.setViewportView(arbol);
           
           /**Definimos mas nodos del arbol y se lo agregamos al modelo*/
           /**Definimos mas nodos del arbol y se lo agregamos al modelo*/
           
           for (i =0; i<nDias; i++){
                DefaultMutableTreeNode carpeta = new DefaultMutableTreeNode("DIA "+dia); 
                modelo2.insertNodeInto(carpeta, carpetaRaiz, i);
           }
         
           cnt = this.Incompletos ;
                                
            // ................................................................................
           
            arbol.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
           
            public void valueChanged(TreeSelectionEvent e) {
                // se obtiene el nodo seleccionado
                DefaultMutableTreeNode nseleccionado = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();
    
                int nivel = nseleccionado.getDepth() ;
                System.out.println("El nivel de campo es ="+nivel);
                
                if ( nivel == 0) {
                    
                    String nodo         = nseleccionado.getUserObject().toString() ;
                    String [] campos    = nodo.split("\\s+");
                    int indice          = Integer.parseInt(campos[0]);
                    
                    System.out.println("El indice de campo es ="+indice);
                    actualizarFormulario(indice);
                    
                }
            }
            }); 
            
            // ................................................................................
           
       }
       
      private void actualizarFormulario(int indice) {
           int ind,ireg, creg, indLoc=-1;
           this.indGen = indice ;
           DefaultTableModel modelLoc;
           DefaultTableModel modelCer;
           String nif       = ""; 
           String telefono  = "";
           String direccion = "";
           String titular   = "";
           String cupsGas   = "";
           String cupsEle   = "";
                   
           MarcaColorOK() ;
           if (indice == -1) {
           
            actuaMunicipio.setVisible(false);
            
       
           
            jTextField4.setText(" "); jTextField4.setBackground(Color.white);// cups electrico
            jTextField5.setText(" "); jTextField5.setBackground(Color.white);// cups gas
         
            jTextField7.setText(" "); jTextField7.setBackground(Color.white);// agente
          
            jTextField10.setText(" "); jTextField10.setBackground(Color.white);// codigo postal
            jTextField11.setText(" "); jTextField11.setBackground(Color.white);// municipio
            jTextField12.setText(" "); jTextField12.setBackground(Color.white);// provincia
            jTextField13.setText(" "); jTextField13.setBackground(Color.white);// direccion
            jTextField14.setText(" "); jTextField14.setBackground(Color.white);// titular
            jTextField15.setText(" "); jTextField15.setBackground(Color.white);// nif/cif
            jTextField16.setText(" "); jTextField16.setBackground(Color.white);// fecha firma cliente
         
            jTextField18.setText(" "); jTextField18.setBackground(Color.white);// consumo kwha
        
            jTextField22.setText(" "); jTextField22.setBackground(Color.white);// telefono
            jTextField23.setText(" "); jTextField23.setBackground(Color.white);// persona contacto
            jTextArea1.setText(" ");   jTextArea1.setBackground(Color.white);// observaciones
            jTextField2.setText(" "); jTextField2.setBackground(Color.white);// consumo gas
            jTextField19.setText(" "); jTextField19.setBackground(Color.white);// tarifa gas
            jTextField20.setText(" "); jTextField20.setBackground(Color.white);// tarifa elec
            
            jTextField21.setText(" "); jTextField20.setBackground(Color.white);// id_m_r
            jTextField24.setText(" "); jTextField24.setBackground(Color.white);// memo
            
            jTextField74.setText(" "); jTextField74.setBackground(Color.white);// Agente comercial
            jTextField75.setText(" "); jTextField75.setBackground(Color.white);// contador
               
            jLabel41.setVisible(false);  // Sugerencia de Municipio
           
            jTextArea2.setText(" "); jTextArea2.setBackground(Color.white);   
            jTextArea3.setText(" "); jTextArea3.setBackground(Color.white); 
            
            
            jComboBox3.setSelectedIndex(0);  
            jComboBox4.setSelectedIndex(0); 
               
            jCheckBox23.setSelected(false);
            jCheckBox24.setSelected(false);
            jCheckBox25.setSelected(false);
            jCheckBox26.setSelected(false);
            jCheckBox27.setSelected(false);
            
            jCheckBox28.setSelected(false);
            jCheckBox29.setSelected(false);
            jCheckBox30.setSelected(false);
            
            jCheckBox31.setSelected(false);
            jCheckBox32.setSelected(false);
            
            jCheckBox35.setSelected(false);
            
            jCheckBox36.setSelected(false);
            
           
            
           } else {
           
           
           if (this.nRegistros >0 ) {
                
                System.out.println("Indice pasado ="+indice+" y el Indice de tablaLocuciones ="+this.tablaDatos[indice][32]);
                
                // .........................................................
                
                DefaultTableModel model;
		model = new DefaultTableModel();        // definimos el objeto tableModel
               
		miTablaLiquida = new JTable();                // creamos la instancia de la tabla
		miTablaLiquida.setModel(model);
                
                model.addColumn("idColor");                        
		model.addColumn("TUR");                
		model.addColumn("CUPS GAS");
		model.addColumn("CUPS ELECTRICIDAD");
		model.addColumn("TITULAR");
                model.addColumn("CIF/NIF");
                model.addColumn("SVG C");
                model.addColumn("SVG X");
		model.addColumn("SVG B");
                model.addColumn("SVE");
                model.addColumn("SVH");
                model.addColumn("SVGCC");
                model.addColumn("SVGSC");
                model.addColumn("SPP");
                model.addColumn("T.PLANA");
                model.addColumn("OBSERVACIONES");
                model.addColumn("Nº DE PRODUCTOS");
               
                
                
		miTablaLiquida.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		miTablaLiquida.getTableHeader().setReorderingAllowed(false);
                
                TableColumn columna1 = miTablaLiquida.getColumn("CUPS GAS");
                TableColumn columna2 = miTablaLiquida.getColumn("CUPS ELECTRICIDAD");
                TableColumn columna3 = miTablaLiquida.getColumn("TITULAR");
                TableColumn columna4 = miTablaLiquida.getColumn("OBSERVACIONES");
               
                columna1.setMinWidth(230);
                columna2.setMinWidth(230);
                columna3.setMinWidth(150);
                columna4.setMinWidth(250);
                
                
                ireg = indice ;
                this.indGenReg = ireg ;
               
      //          indice = Integer.parseInt(this.tablaDatos[indice][30]) ;               // Indice del registro de base de datos
               
                creg = Integer.parseInt(this.tablaDatos[indice][40]) ;                  // Indice de tabla de certificaciones
                indLoc = Integer.parseInt(this.tablaDatos[indice][32]);                 // Indice de tabla de locuciones
                System.out.println("Indice de tabla certificaciones CREG ="+this.tablaDatos[indice][40]+" y indLoc = "+indLoc) ;
              
                
                if (indLoc == -1){
                    
                   
                    modelLoc = new DefaultTableModel();            // definimos el objeto tableModel
                    modelCer = new DefaultTableModel();            // definimos el objeto tableModel
               
                    limpiaTablaLocuciones(modelLoc);
                    miTablaLocu = new JTable();                // creamos la instancia de la tabla
                    miTablaLocu.setModel(modelLoc);
                    miBarraLocu.setViewportView(miTablaLocu);
                   
                    jCheckBox32.setSelected(false);
                    jCheckBox33.setSelected(false);
                    jCheckBox34.setSelected(false);
                    jCheckBox35.setSelected(false);
                    
                }
                
                
                if (indice !=-1) {
               
                    
                    
                    jTextArea1.setText(" "); jTextArea1.setBackground(Color.white);  
                    jTextArea2.setText(" "); jTextArea2.setBackground(Color.white);   
                    jTextArea3.setText(" "); jTextArea3.setBackground(Color.white); 
                    
                    
                    ind = Integer.parseInt(this.tablaDatos[ireg][0]) ;      // estado      
                    jComboBox4.setSelectedIndex(ind); System.out.println("Estado para idc="+indice+" es igual a="+ind);
                                     
                    if ( ind==3) jComboBox3.setSelectedIndex(ind); else {
                            
                               ind = Integer.parseInt(this.tablaDatos[ireg][1]) ;      // incidencia      
                               jComboBox3.setSelectedIndex(ind);     
                      }
                    
                    MarcaColorOK();
                            
                    jTextField27.setText(this.tablaDatos[ireg][2]); // fecha producción           
                    jTextField4.setText(this.tablaDatos[ireg][4]); // cups electrico
                    jTextField5.setText(this.tablaDatos[ireg][3]); // cups gas         
                    jTextField7.setText(this.tablaDatos[ireg][17]); // agente          
                    jTextField10.setText(this.tablaDatos[ireg][5]); // codigo postal
                    jTextField11.setText(this.tablaDatos[ireg][6]); // municipio
                    jTextField12.setText(this.tablaDatos[ireg][7]); // provincia
                    jTextField13.setText(this.tablaDatos[ireg][8]); // direccion
                    jTextField14.setText(this.tablaDatos[ireg][9]); // titular
                    jTextField15.setText(this.tablaDatos[ireg][10]); // nif/cif
                    jTextField16.setText(this.tablaDatos[ireg][11]); // fecha firma cliente             
                    jTextField18.setText(this.tablaDatos[ireg][12]); // consumo kwha electrico
                    jTextField2.setText(this.tablaDatos[ireg][13]); // consumo kwha gas
                    
                    jTextField21.setText(this.tablaDatos[ireg][30]); // id_m_r      
                 
                  //  ind = Integer.parseInt(this.tablaDatos[ireg][14]) ; 
                  //  jComboBox2.setSelectedIndex(ind); 
                     if (this.tablaDatos[ireg][34].equals("1")) jCheckBox23.setSelected(true); else jCheckBox23.setSelected(false);
                     if (this.tablaDatos[ireg][35].equals("1")) jCheckBox24.setSelected(true); else jCheckBox24.setSelected(false);
                     if (this.tablaDatos[ireg][36].equals("1")) jCheckBox25.setSelected(true); else jCheckBox25.setSelected(false);
                     if (this.tablaDatos[ireg][37].equals("1")) jCheckBox26.setSelected(true); else jCheckBox26.setSelected(false);
                     if (this.tablaDatos[ireg][38].equals("1")) jCheckBox27.setSelected(true); else jCheckBox27.setSelected(false);
                     if (this.tablaDatos[ireg][45].equals("1")) jCheckBox33.setSelected(true); else jCheckBox33.setSelected(false); // SWG Con Calef
                     if (this.tablaDatos[ireg][46].equals("1")) jCheckBox34.setSelected(true); else jCheckBox34.setSelected(false); // SWG Sin Calef
                     
                     if (this.tablaDatos[ireg][43].equals("1")) jCheckBox31.setSelected(true); else jCheckBox31.setSelected(false); // Tur GAS
                     if (this.tablaDatos[ireg][44].equals("1")) jCheckBox32.setSelected(true); else jCheckBox32.setSelected(false); // Punteado
                     if (this.tablaDatos[ireg][48].equals("1")) jCheckBox35.setSelected(true); else jCheckBox35.setSelected(false); // Tarifa plana
                     if (this.tablaDatos[ireg][51].equals("1")) jCheckBox36.setSelected(true); else jCheckBox36.setSelected(false); // SPP
                    
                    
                    jTextArea1.setText(this.tablaDatos[ireg][15]); // observaciones
                    jTextArea2.setText(this.tablaDatos[ireg][20]); // incidencia
                    jTextArea3.setText(this.tablaDatos[ireg][21]); // explicacion
                    
                    jTextField22.setText(this.tablaDatos[ireg][16]); // telefono   
                    
                    jTextField19.setText(this.tablaDatos[ireg][28]); // tarifa gas
                    jTextField20.setText(this.tablaDatos[ireg][29]); // tarifa elec
                    
                    jTextField24.setText(this.tablaDatos[ireg][39]); // memo  
                    
                    jTextField74.setText(this.tablaDatos[ireg][41]); // Agente comercial
                    jTextField75.setText(Integer.toString(ireg));    // contador 
                    
                    jTextField123.setText(this.tablaDatos[ireg][47]) ; // Número de contratos repetidos.
                    
                    
                    System.out.println("A ver this.tablaDatos[ireg][33] = "+this.tablaDatos[ireg][33]);
                    
                    // ..........................................................                   Si es KO lo resaltamos
                    
                    System.out.println("------------------------------------------ > this.tablaDatos[ireg][1]"+this.tablaDatos[ireg][1]);
                    
                    if (this.tablaDatos[ireg][0].equals("3")) MarcaColorKO();
                      // ..........................................................                   
                    
                    if (this.tablaDatos[ireg][1].equals("1")) MarcaColorIncidencia(Integer.parseInt(this.tablaDatos[ireg][1])) ;  // Marca Amarillo
                    if (this.tablaDatos[ireg][1].equals("2")) MarcaColorIncidencia(Integer.parseInt(this.tablaDatos[ireg][1])) ;  // Marca Naranja
                    if (this.tablaDatos[ireg][1].equals("4")) MarcaColorIncidencia(Integer.parseInt(this.tablaDatos[ireg][1])) ;  // Marca Verde
                    if (this.tablaDatos[ireg][1].equals("5")) MarcaColorIncidencia(Integer.parseInt(this.tablaDatos[ireg][1])) ;  // Marca MORADO
                    if (this.tablaDatos[ireg][1].equals("6")) MarcaColorIncidencia(Integer.parseInt(this.tablaDatos[ireg][1])) ;  // Marca Azul

                    
                    // ..........................................................
                    
                    if (this.tablaDatos[ireg][33].equals("1")) jCheckBox28.setSelected(true); else jCheckBox28.setSelected(false);
                    if (this.tablaDatos[ireg][33].equals("2")) jCheckBox29.setSelected(true); else jCheckBox29.setSelected(false);
                    if (this.tablaDatos[ireg][33].equals("3")) jCheckBox30.setSelected(true); else jCheckBox30.setSelected(false);
                   
                  // .........................................................  
                    
                modelLoc = new DefaultTableModel();            // definimos el objeto tableModel
               
                limpiaTablaLocuciones(modelLoc);
                miTablaLocu = new JTable();                // creamos la instancia de la tabla
                miTablaLocu.setModel(modelLoc);
                  
                
                PymesDao miPymesDaoLoc = new PymesDao();
                 
                    
                nif         = this.tablaDatos[ireg][10].trim(); 
                telefono    = this.tablaDatos[ireg][16].trim();
                direccion   = this.tablaDatos[ireg][8].trim();
                titular     = this.tablaDatos[ireg][9].trim();
                cupsGas     = this.tablaDatos[ireg][3].trim();
                cupsEle     = this.tablaDatos[ireg][4].trim();
                        
	   
                miPymesDaoLoc.compruebaLocucion(modelLoc,this.plogin,this.ppassword,nif,telefono,direccion,titular);
               
                miTablaLocu.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		miTablaLocu.getTableHeader().setReorderingAllowed(false);
                
                TableColumn colL0  = miTablaLocu.getColumn("ID");
                TableColumn colL1  = miTablaLocu.getColumn("idt");
                TableColumn colL2  = miTablaLocu.getColumn("Fecha");
                TableColumn colL3  = miTablaLocu.getColumn("Titular");
                TableColumn colL4  = miTablaLocu.getColumn("Verifica");
                TableColumn colL5  = miTablaLocu.getColumn("Direccion");
                TableColumn colL6  = miTablaLocu.getColumn("Contrato");
                TableColumn colL7  = miTablaLocu.getColumn("Llam1");
                TableColumn colL8  = miTablaLocu.getColumn("Llam2");
                TableColumn colL9  = miTablaLocu.getColumn("Llam3");
                TableColumn colL10 = miTablaLocu.getColumn("Llam4");
                TableColumn colL11 = miTablaLocu.getColumn("Llam5");
                TableColumn colL12 = miTablaLocu.getColumn("Llam6");
                
                colL0.setMinWidth(10);
                colL1.setMinWidth(10);
                colL2.setMinWidth(40);
                colL3.setMinWidth(200);
                colL4.setMinWidth(200);
                colL5.setMinWidth(150);
                colL6.setMinWidth(120);
                colL7.setMinWidth(150);
                colL8.setMinWidth(150);
                colL9.setMinWidth(150);
                colL7.setMinWidth(150);
                colL8.setMinWidth(150);
                colL9.setMinWidth(150);
                colL10.setMinWidth(150);
                colL11.setMinWidth(150);
                colL12.setMinWidth(150);
                
                miBarraLocu.setViewportView(miTablaLocu);
               // .........................................................
                
                modelCer = new DefaultTableModel();                         // definimos el objeto tableModel
               
                limpiaTablaCertificaciones(modelCer);
                miTablaCer = new JTable();                                  // creamos la instancia de la tabla
                miTablaCer.setModel(modelCer);
              
                
                
                miPymesDaoLoc.compruebaCertificacion(modelCer,this.plogin,this.ppassword,nif,direccion,cupsGas,cupsEle);
                
                miTablaCer.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		miTablaCer.getTableHeader().setReorderingAllowed(false);
                
                TableColumn col0 = miTablaCer.getColumn("IDp");
                TableColumn col1 = miTablaCer.getColumn("Fecha");
                TableColumn col2 = miTablaCer.getColumn("Fecha_Recep.");
                TableColumn col3 = miTablaCer.getColumn("Concepto");
                TableColumn col4 = miTablaCer.getColumn("Contrato");
                TableColumn col5 = miTablaCer.getColumn("NIF");
                TableColumn col6 = miTablaCer.getColumn("Calle");
                TableColumn col7 = miTablaCer.getColumn("CUPS_Gas");
                TableColumn col8 = miTablaCer.getColumn("CUPS_Elect");
                TableColumn col9 = miTablaCer.getColumn("Producto");
       
                col0.setMinWidth(10);
                col1.setMinWidth(40);
                col2.setMinWidth(40);
                col3.setMinWidth(200);
                col4.setMinWidth(50);
                col5.setMinWidth(40);
                col6.setMinWidth(120);
                col7.setMinWidth(150);
                col8.setMinWidth(150);
                col9.setMinWidth(350);
        
                
                miBarraCer.setViewportView(miTablaCer);
                
                // .........................................................
                     
                    
                   if (indLoc != -1) {
                        System.out.println("this.tablaLocuciones["+indLoc+"][11]="+this.tablaLocuciones[indLoc][11]);

                    // ...................................
                    
                    // ...................................
                    
                    
                    if (this.locuciones[ireg]> 1  ){                                                           // Dia de la locución 2
                        
                        System.out.println("this.llamadasLocuciones[ireg][1]="+this.llamadasLocuciones[ireg][1]);
                        
                    } 
                     
                    if (this.locuciones[ireg]> 2) {                                                         // Dia de la locución 3
                       
                    }  
                    
                    
                    
                   
                    
                   }

                } else {
                   
                   
                    jTextField74.setText(" "); jTextField74.setBackground(Color.white);
                    jTextArea2.setText(" "); jTextArea2.setBackground(Color.white);   
                    jTextArea3.setText(" "); jTextArea3.setBackground(Color.white); 
                   
                    jCheckBox32.setSelected(false);
                    jCheckBox33.setSelected(false);
                    jCheckBox34.setSelected(false);
                    jCheckBox35.setSelected(false);
                    jCheckBox36.setSelected(false);

                }
                 if ( creg!= -1) {
                                                            
                 } else {
                     
                    
                 }

           }
             
           }
           
       }
       
       private void mostrarDatosConTableModel() {
		
                String fProd  ="";
                String fProd2 ="";
                System.out.println("----COMIENZO EL FORMATEO ---");
           
                DefaultTableModel model;
		model = new DefaultTableModel();        // definimos el objeto tableModel
               
		miTabla01 = new JTable();                // creamos la instancia de la tabla
		miTabla01.setModel(model);
                
                                 
		model.addColumn("ID_C");                
		model.addColumn("Estado");
		model.addColumn("Docout");
		model.addColumn("Incidencia");
                model.addColumn("Fecha");
                model.addColumn("Agente");
                model.addColumn("Orden");
		model.addColumn("Swg");
                model.addColumn("Swe");
                model.addColumn("DualFuel");
                model.addColumn("CUPS gas");
                model.addColumn("CUPS Elect");
                model.addColumn("Cod Postal");
                model.addColumn("Municipio");
                model.addColumn("Provincia");
                model.addColumn("Direccion");
                model.addColumn("Titular");
                model.addColumn("NIF-CIF");
                model.addColumn("Telefono");
                model.addColumn("Fecha Firma");
                model.addColumn("Consumo elc");
                model.addColumn("Consumo gas");
                model.addColumn("SVGCompleto");                
                model.addColumn("SVGXpres");
                model.addColumn("SVGBasico");
                model.addColumn("SVEExpres");
                model.addColumn("Servihogar");                
                model.addColumn("TBS");                
                model.addColumn("Incidencia");
                model.addColumn("Explicacion");
                model.addColumn("Solucion");
                model.addColumn("Observaciones");
                model.addColumn("Trf Gas");
                model.addColumn("Trf Elc");
                model.addColumn("Comercial");
                model.addColumn("Tur Gas");
                model.addColumn("Punteado");
                model.addColumn("SVGConCalef");
                model.addColumn("SVGSinCalef");
                model.addColumn("TarifaPlana");
                model.addColumn("SPP");
                
                
		miTabla01.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		miTabla01.getTableHeader().setReorderingAllowed(false);
                
                TableColumn columna1 = miTabla01.getColumn("Titular");
                TableColumn columna2 = miTabla01.getColumn("Direccion");
                TableColumn columna3 = miTabla01.getColumn("CUPS gas");
                TableColumn columna4 = miTabla01.getColumn("CUPS Elect");
               
                columna1.setMinWidth(230);
                columna2.setMinWidth(230);
                columna3.setMinWidth(150);
                columna4.setMinWidth(150);

		PymesDao miPymesDao1 = new PymesDao();
		/*
		 * enviamos el objeto TableModel, como mandamos el objeto podemos
		 * manipularlo desde el metodo
		 */
                
                fProd = jTextField124.getText(); fProd = fProd.trim() ;
                
                fProd2 = jTextField30.getText(); fProd2 = fProd2.trim();
                
		miPymesDao1.buscarContratos(model,this.plogin,this.ppassword,this.filtroEstadoSel,this.filtroFechaSel,this.filtroProvincia,this.filtroAgente,this.filtroMakro,this.filtroIncidencia,fProd,fProd2);
                
                 //Nueva instancia de la clase que contiene el formato
                FormatoTabla formato = new FormatoTabla();
                
                formato.fuente = this.fuente ;
                
                //Se obtiene la tabla y se establece el formato para cada tipo de dato
                
                miTabla01.setDefaultRenderer(Double.class, formato); 
                miTabla01.setDefaultRenderer(String.class, formato); 
                miTabla01.setDefaultRenderer(Integer.class, formato);
                miTabla01.setDefaultRenderer(Object.class, formato);
                
                this.nRegistros = miPymesDao1.nRegistros ;
                this.tablaDatos = miPymesDao1.tablaDatos ;
                
		miBarra01.setViewportView(miTabla01);
                
       //         miTabla01.setCellSelectionEnabled(true);
         //       miTabla01.setComponentPopupMenu(menuTablaMakro);
                
                ListSelectionModel cellSelectionModel = miTabla01.getSelectionModel();
        //        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                
                               
                cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                      String selectedData = null;
                      int id = 0, ind=0 ;
                    
                     int[] selectedRow = miTabla01.getSelectedRows();
                     int[] selectedColumns = miTabla01.getSelectedColumns();
                     
                     if (miTabla01.getSelectedRowCount()== 1) {
                        ind = miTabla01.convertRowIndexToModel(selectedRow[0]);
                        System.out.println("Selected: " + miTabla01.getValueAt(selectedRow[0], 0)+ " INDICE ="+ind);
                        id = (Integer)miTabla01.getValueAt(selectedRow[0], 0);
                        seleccionaElementoTabla(id,ind );
                     
                     }   

                    }
                });
                
                
                

	}
       public void ActualizarTablaFormulario() {
           
           int indice, ind;
           indice = this.indGenReg;
           System.out.println("Actualizo campos tabla  ="+indice);
           
            ind = jComboBox4.getSelectedIndex() ;
            this.tablaDatos[indice][0] = Integer.toString(ind);            System.out.println("Indice de estado ind ="+ind); // estado
         
            
            if ( ind == 3)  {this.tablaDatos[indice][1] = "3" ;  jComboBox3.setSelectedIndex(ind);}  else  {               // incidencia
            
            ind = jComboBox3.getSelectedIndex() ;  
            this.tablaDatos[indice][1] = Integer.toString(ind); // incidencia
            
            }
          
           this.tablaDatos[indice][4] = jTextField4.getText(); // cups electrico
           this.tablaDatos[indice][3]= jTextField5.getText(); // cups gas
         
           this.tablaDatos[indice][17] = jTextField7.getText(); // agente
         
           this.tablaDatos[indice][5] = jTextField10.getText(); // codigo postal
           this.tablaDatos[indice][6] = jTextField11.getText(); // municipio
           this.tablaDatos[indice][7] = jTextField12.getText(); // provincia
           this.tablaDatos[indice][8] = jTextField13.getText(); // direccion
           this.tablaDatos[indice][9] = jTextField14.getText(); // titular
           this.tablaDatos[indice][10] = jTextField15.getText(); // nif/cif
           this.tablaDatos[indice][11] = jTextField16.getText(); // fecha firma cliente
           
           this.tablaDatos[indice][12] = jTextField18.getText(); // consumo kwha
         
           this.tablaDatos[indice][16] =  jTextField22.getText(); // telefono
           
           this.tablaDatos[indice][15] = jTextArea1.getText(); // observaciones      
           this.tablaDatos[indice][20] = jTextArea2.getText(); // incidencia  
           this.tablaDatos[indice][21] = jTextArea3.getText(); // solucion      
           
           this.tablaDatos[indice][13] = jTextField2.getText(); // consumo gas kwha
         
           this.tablaDatos[indice][2] = jTextField27.getText(); // Fecha de producción      
           
           this.tablaDatos[indice][28] = jTextField19.getText(); // Tarifa Gas    
           this.tablaDatos[indice][29] = jTextField20.getText(); // Tarifa Elec   
           
           this.tablaDatos[indice][41] = jTextField74.getText(); // Agente comercial  
           
          this.tablaDatos[indice][39] = jTextField24.getText(); // Fecha Memo  
           
       }
      public void RefrescarTablaBD() {
          
           
           mostrarDatosConTableModel(); 
           
      
          
      }
      
      public void ActualizaConfiguracion() {
          
          
      }
      
      
      public void ActualizaFechaProduccion() {
          int i;
          for (i=0; i<this.nRegistros; i++){
              
              this.tablaDatos[i][2] = jTextField28.getText() ;
              
          }
          actualizarFormulario(-1);
      }
              
      public  void GuardarArchivoExelResultados() {
        
        int i,j;
     
        HSSFWorkbook libro = new HSSFWorkbook();        
        HSSFSheet hoja = libro.createSheet("MAKRO PYMES");
        Row fila = hoja.createRow(0);        
        Cell celda;
          
        
        String[] titulos = { "FECHA","G","L","Dual","CUPS Gas","CUPS Electricidad","Código Postal","Municipio",
                             "Provincia","Dirección","Titular","NIF/CIF","Fecha Firma Cliente","Consumo Gas kWh/año","Consumo Electricidad kWh/año",
                             "Tarifa Gas","Tarifa Electricidad",
                             "SVGComplet","SVGXpres","SVG Básico","SVELECTRIC XPRES","SVGHOGAR","SVG CON CALEFACCION","SVG C SIN CALEFACCION","TARIFA PLANA","SPP",
                             "OBSERVACIONES.","INCIDENCIA","EXPLICACION","telefono","Estado","Incidencia","COMERCIAL","AGENTE" };                                                                   // 24 CAMPOS
        
        String str,L,G,D="" ;
        

        

        // Creamos el encabezado

        for (i = 0; i < titulos.length; i++) {
              celda = fila.createCell(i);
              celda.setCellValue(titulos[i]);
        }

       
        for (j=0; j<this.nRegistros; j++) {
           
           if  (!this.tablaDatos[j][4].equals("") ) L="X"; else L = "" ;
           
           if  (!this.tablaDatos[j][3].equals("") ) G="X"; else G = "" ;
           
           if  (!this.tablaDatos[j][3].equals("") && !this.tablaDatos[j][4].equals("") ) {L=""; G = ""; D="X";} else D = "" ;
           
                 
           // Nueva fila 
           i = 0 ;
           fila = hoja.createRow(j+1);
             
           System.out.println("Inserto celdas Exel en fila  ="+j);
           
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][2]);      i++;        // FECHA PROD
           
           celda = fila.createCell(i);  celda.setCellValue(G);                          i++;        // G
           celda = fila.createCell(i);  celda.setCellValue(L);                          i++;        // L
           celda = fila.createCell(i);  celda.setCellValue(D);                          i++;        // DUAL           
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][3]);      i++;        // Cups Gas
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][4]);      i++;        // Cups Luz        
       
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][5]);      i++;        // Codigo postal
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][6]);      i++;        // municipio
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][7]);      i++;        // provincia
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][8]);      i++;        // direccion
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][9]);      i++;        // titular           
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][10]);     i++;        // Nif/CIF
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][11]);     i++;        // Fecha de firma de cliente
           
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][13]);     i++;        // consumo gas  kwha
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][12]);     i++;        // consumo electrico  kwha
           
           
           celda = fila.createCell(i);  celda.setCellValue(0);                          i++;        // tarifa gas
           celda = fila.createCell(i);  celda.setCellValue(0);                          i++;        // tarifa electricidad
           
           
           celda = fila.createCell(i);  
           if (this.tablaDatos[j][34].equals("1") )  celda.setCellValue("X"); else 
                                                         celda.setCellValue("");         i++;        // SVGComplet
        
           celda = fila.createCell(i);  
           if (this.tablaDatos[j][35].equals("1") )  celda.setCellValue("X"); else 
                                                         celda.setCellValue("");         i++;        // SVGXpres
        
           celda = fila.createCell(i);  
           if (this.tablaDatos[j][36].equals("1") )  celda.setCellValue("X"); else 
                                                         celda.setCellValue("");         i++;        // SVG Básico
        
          celda = fila.createCell(i);  
          if (this.tablaDatos[j][37].equals("1") )   celda.setCellValue("X"); else 
                                                         celda.setCellValue("");         i++;        //SVELECTRIC XPRES
        
          celda = fila.createCell(i);  
          if (this.tablaDatos[j][38].equals("1") )  celda.setCellValue("X"); else 
                                                        celda.setCellValue("");          i++;         //SVGHOGAR
                                                        
                                                        
          celda = fila.createCell(i);  
          if (this.tablaDatos[j][45].equals("1") )  celda.setCellValue("X"); else 
                                                    celda.setCellValue("");          i++;         //SVG C CON CALEFACCIÓN                                                       
                                                        
          celda = fila.createCell(i);  
          if (this.tablaDatos[j][46].equals("1") )  celda.setCellValue("X"); else 
                                                    celda.setCellValue("");          i++;         //SVG C SIN CALEFACCIÓN                                                    
                                                        
          celda = fila.createCell(i);  
          if (this.tablaDatos[j][48].equals("1") )  celda.setCellValue("X"); else 
                                                    celda.setCellValue("");          i++;         //TARIFA PLANA                                                 
                                                        
          celda = fila.createCell(i);  
          if (this.tablaDatos[j][51].equals("1") )  celda.setCellValue("X"); else 
                                                    celda.setCellValue("");          i++;         //SPP                                             
                                                    
                                
          celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][15]);      i++;        // observaciones  
          
          celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][20]);      i++;        // INCIDENCIA  
          
          celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][21]);      i++;        // EXPLICACION/SOLUCION  
          
          
          celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][16]);      i++;        // telefono
       
          celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][0]);       i++;        // estado
          celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][1]);       i++;        // incidencia         
          
          celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][41]);       i++;        // COMERCIAL         
          celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][17]);       i++;        // AGENTE         
            
             
             /*
              for (i = 0; i < datos.length; i++) {
                        celda = fila.createCell(i);
                        celda.setCellValue(datos[i]);
              }  
            */
            
        }
       
          
        try
	{
            String nombre="";
	    JFileChooser file=new JFileChooser();
	    file.showSaveDialog(this);
	    File guarda =file.getSelectedFile();
		
	    if(guarda !=null)
	    {
		 nombre=file.getSelectedFile().getName();
		 /*guardamos el archivo y le damos el formato directamente,
		 * si queremos que se guarde en formato doc lo definimos como .doc*/
		                 
                 FileOutputStream elFichero = new FileOutputStream(guarda+".xls");
                 libro.write(elFichero);
                 elFichero.close();
                 
                 
		 JOptionPane.showMessageDialog(null,
		 "El archivo se ha guardado Exitosamente",
		 "Información",JOptionPane.INFORMATION_MESSAGE);
	    }
	 }
	 catch(IOException ex)
         {
		  JOptionPane.showMessageDialog(null,
		  "Su archivo no se ha guardado",
		  "Advertencia",JOptionPane.WARNING_MESSAGE);
	 }
        
        // Se salva el libro.
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    

       
          
          
      }
 public void GuardarArchivoExelDevueltos() {
        
        int i,j,nRechazados;
        String Datos[][]        = new String[10000][50];  
        String sFecha, sFecNorm ;
        
        PymesDao miPymesDao1 = new PymesDao();
	
        sFecha = "2014-10-20";
        
        sFecha = JOptionPane.showInputDialog("Introduce la fecha de producción (aaaa-mm-dd): ");
        
        int resp=JOptionPane.showConfirmDialog(null,"¿Crear Informe de KOs y AMARILLOS para producción:"+sFecha+" ?");
        
        
        if (JOptionPane.OK_OPTION == resp){
        
            // ..............................................................................................   CONTRATOS DEVUELTOS
            miPymesDao1.buscarRechazados(this.plogin,this.ppassword,sFecha,0);

                    nRechazados = miPymesDao1.nRegistros ;
                    Datos       = miPymesDao1.tablaDatos ;

            HSSFWorkbook libro = new HSSFWorkbook();        
            HSSFSheet hoja = libro.createSheet("DEVUELTOS");
            Row fila = hoja.createRow(0);        
            Cell celda;

            String[] titulos = { "Contrato a devolver","Nombre","Localidad","Telefono","Gas","Luz","Dual",
                                 "SVGComplet","SVGXpres","SVG Básico","SVELECTRIC XPRES","SVGHOGAR","SVG CON CALEFACCION","SVG C SIN CALEFACCION","TARIFA PLANA","SPP",
                                 "Comercial","Agente","OBSERVACIONES" };                                                                   // 13 CAMPOS

            String str,L,G,D="" ;

            actualizaIncompletos(); System.out.println("Hay para devolver ="+nRechazados);

            // Creamos el encabezado

            for (i = 0; i < titulos.length; i++) {
                  celda = fila.createCell(i);
                  celda.setCellValue(titulos[i]);
            }

            for (j=0; j<nRechazados; j++) {
                
                Datos[j][3] = Datos[j][3].trim();
                Datos[j][4] = Datos[j][4].trim();

               if  (!Datos[j][4].equals("") ) L="X"; else L = "" ; System.out.println("j="+j+"Datos[3]='"+Datos[j][3]+"' Por tanto L="+L);

               if  (!Datos[j][3].equals("") ) G="X"; else G = "" ; System.out.println("j="+j+"Datos[4]='"+Datos[j][4]+"' Por tanto L="+G);

               if  (!Datos[j][3].equals("") && !Datos[j][4].equals("") ) {L=""; G = ""; D="X";} else D = "" ; System.out.println("Por tanto L="+D);

               // Nueva fila 
               i = 0 ;
               fila = hoja.createRow(j+1);

               System.out.println("Inserto celdas Exel en fila  ="+j);

               celda = fila.createCell(i);  celda.setCellValue(Datos[j][2]);      i++;        // FECHA PROD
               celda = fila.createCell(i);  celda.setCellValue(Datos[j][9]);      i++;        // titular   
               celda = fila.createCell(i);  celda.setCellValue(Datos[j][6]);      i++;        // municipio
               celda = fila.createCell(i);  celda.setCellValue(Datos[j][16]);     i++;        // telefono
               celda = fila.createCell(i);  celda.setCellValue(G);                               i++;        // G
               celda = fila.createCell(i);  celda.setCellValue(L);                               i++;        // L
               celda = fila.createCell(i);  celda.setCellValue(D);                               i++;        // D

               celda = fila.createCell(i);  
               if (Datos[j][34].equals("1") )  celda.setCellValue("X"); else 
                                                             celda.setCellValue("");              i++;        // SVGComplet

               celda = fila.createCell(i);  
               if (Datos[j][35].equals("1") )  celda.setCellValue("X"); else 
                                                             celda.setCellValue("");              i++;        // SVGXpres

               celda = fila.createCell(i);  
               if (Datos[j][36].equals("1") )  celda.setCellValue("X"); else 
                                                             celda.setCellValue("");              i++;        // SVG Básico

              celda = fila.createCell(i);  
              if (Datos[j][37].equals("1") )   celda.setCellValue("X"); else 
                                                             celda.setCellValue("");              i++;        //SVELECTRIC XPRES

              celda = fila.createCell(i);  
              if (Datos[j][38].equals("1") )  celda.setCellValue("X"); else 
                                                            celda.setCellValue("");               i++;         //SVGHOGAR
                                                            
              celda = fila.createCell(i);  
              if (Datos[j][45].equals("1") )  celda.setCellValue("X"); else 
                                                        celda.setCellValue("");          i++;         //SVG C CON CALEFACCIÓN                                                       
                                                        
              celda = fila.createCell(i);  
              if (Datos[j][46].equals("1") )  celda.setCellValue("X"); else 
                                                        celda.setCellValue("");          i++;         //SVG C SIN CALEFACCIÓN                                                    
                                                        
              celda = fila.createCell(i);  
              if (Datos[j][48].equals("1") )  celda.setCellValue("X"); else 
                                                        celda.setCellValue("");          i++;         //TARIFA PLANA          
                                                        
              celda = fila.createCell(i);  
              if (Datos[j][51].equals("1") )  celda.setCellValue("X"); else 
                                                        celda.setCellValue("");          i++;         //SPP            
                                                        

              System.out.println("Datos[j][41] = "+Datos[j][41]);
                                                            
              celda = fila.createCell(i);  celda.setCellValue(Datos[j][41]);      i++;        // comercial     // 
              celda = fila.createCell(i);  celda.setCellValue(Datos[j][17]);      i++;        // Agente        // 17 
              celda = fila.createCell(i);  celda.setCellValue(Datos[j][15]);      i++;        // observaciones  

            }

                String nombre="";
                JFileChooser file=new JFileChooser();
                file.showSaveDialog(this);
                File guarda =file.getSelectedFile();
            try
            {
               

                if(guarda !=null)
                {
                     nombre=file.getSelectedFile().getName();
                    //guardamos el archivo y le damos el formato directamente,
                    // si queremos que se guarde en formato doc lo definimos como .doc

                     FileOutputStream elFichero = new FileOutputStream(guarda+"_DEVUELTOS.xls");
                     libro.write(elFichero);
                     elFichero.close();


                     JOptionPane.showMessageDialog(null,
                     "El archivo DEVUELTOS se a guardado Exitosamente",
                     "Información",JOptionPane.INFORMATION_MESSAGE);
                }
             }
             catch(IOException ex)
             {
                      JOptionPane.showMessageDialog(null,
                      "Su archivo DEVUELTOS no se ha guardado",
                      "Advertencia",JOptionPane.WARNING_MESSAGE);
             }

            // Se salva el libro.
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
            // ..............................................................................................   CONTRATOS SUBSANABLES
             miPymesDao1.buscarRechazados(this.plogin,this.ppassword,sFecha,1);

                    nRechazados = miPymesDao1.nRegistros ;
                    Datos       = miPymesDao1.tablaDatos ;

            HSSFWorkbook libro2 = new HSSFWorkbook();        
            HSSFSheet hoja2 = libro2.createSheet("SUBSANABLES");
            Row fila2 = hoja2.createRow(0);        
            Cell celda2;

            String[] titulos2 = { "Contrato a devolver","Nombre","Localidad","Telefono","Gas","Luz","Dual",
                                 "SVGComplet","SVGXpres","SVG Básico","SVELECTRIC XPRES","SVGHOGAR","SPP",
                                 "Comercial","OBSERVACIONES" };                                                                   // 13 CAMPOS

            actualizaIncompletos(); System.out.println("Hay para devolver ="+nRechazados);

            // Creamos el encabezado

            for (i = 0; i < titulos.length; i++) {
                  celda2 = fila2.createCell(i);
                  celda2.setCellValue(titulos[i]);
            }

            for (j=0; j<nRechazados; j++) {

               Datos[j][3] = Datos[j][3].trim();
               Datos[j][4] = Datos[j][4].trim();
                
               if  (!Datos[j][4].equals("") ) L="X"; else L = "" ;

               if  (!Datos[j][3].equals("") ) G="X"; else G = "" ;

               if  (!Datos[j][3].equals("") && !Datos[j][4].equals("") ) {L=""; G = ""; D="X";} else D = "" ;

               // Nueva fila 
               i = 0 ;
               fila2 = hoja2.createRow(j+1);

               System.out.println("Inserto celdas Exel en fila  ="+j);

               celda2 = fila2.createCell(i);  celda2.setCellValue(Datos[j][2]);      i++;        // FECHA PROD
               celda2 = fila2.createCell(i);  celda2.setCellValue(Datos[j][9]);      i++;        // titular   
               celda2 = fila2.createCell(i);  celda2.setCellValue(Datos[j][6]);      i++;        // municipio
               celda2 = fila2.createCell(i);  celda2.setCellValue(Datos[j][16]);     i++;        // telefono
               celda2 = fila2.createCell(i);  celda2.setCellValue(G);                               i++;        // G
               celda2 = fila2.createCell(i);  celda2.setCellValue(L);                               i++;        // L
               celda2 = fila2.createCell(i);  celda2.setCellValue(D);                               i++;        // D

               celda2 = fila2.createCell(i);  
               if (Datos[j][34].equals("1") )  celda2.setCellValue("X"); else 
                                                             celda2.setCellValue("");              i++;        // SVGComplet

               celda2 = fila2.createCell(i);  
               if (Datos[j][35].equals("1") )  celda2.setCellValue("X"); else 
                                                             celda2.setCellValue("");              i++;        // SVGXpres

               celda2 = fila2.createCell(i);  
               if (Datos[j][36].equals("1") )  celda2.setCellValue("X"); else 
                                                             celda2.setCellValue("");              i++;        // SVG Básico

              celda2 = fila2.createCell(i);  
              if (Datos[j][37].equals("1") )   celda2.setCellValue("X"); else 
                                                             celda2.setCellValue("");              i++;        //SVELECTRIC XPRES

              celda2 = fila2.createCell(i);  
              if (Datos[j][38].equals("1") )  celda2.setCellValue("X"); else 
                                                            celda2.setCellValue("");               i++;         //SVGHOGAR
                                                            
              celda = fila.createCell(i);  
              if (Datos[j][45].equals("1") )  celda.setCellValue("X"); else 
                                                        celda.setCellValue("");          i++;         //SVG C CON CALEFACCIÓN                                                       
                                                        
              celda = fila.createCell(i);  
              if (Datos[j][46].equals("1") )  celda.setCellValue("X"); else 
                                                        celda.setCellValue("");          i++;         //SVG C SIN CALEFACCIÓN                                                    
                                                        
              celda = fila.createCell(i);  
              if (Datos[j][48].equals("1") )  celda.setCellValue("X"); else 
                                                        celda.setCellValue("");          i++;         //TARIFA PLANA 
                                                        
              celda = fila.createCell(i);  
              if (Datos[j][51].equals("1") )  celda.setCellValue("X"); else 
                                                        celda.setCellValue("");          i++;         //SPP

              System.out.println("Datos[j][41] = "+Datos[j][41]);
                                                            
              celda2 = fila2.createCell(i);  celda2.setCellValue(Datos[j][41]);      i++;        // comercial     // 17 
              celda2 = fila2.createCell(i);  celda2.setCellValue(Datos[j][15]);      i++;        // observaciones  

            }


            try
            {
                /*
                String nombre="";
                JFileChooser file=new JFileChooser();
                file.showSaveDialog(this);
                File guarda =file.getSelectedFile();
                */
                if(guarda !=null)
                {
                     nombre=file.getSelectedFile().getName();
                    //guardamos el archivo y le damos el formato directamente,
                    // si queremos que se guarde en formato doc lo definimos como .doc

                     FileOutputStream elFichero = new FileOutputStream(guarda+"_SUBSANABLES.xls");
                     libro2.write(elFichero);
                     elFichero.close();


                     JOptionPane.showMessageDialog(null,
                     "El archivo SUBSANABLE se a guardado Exitosamente",
                     "Información",JOptionPane.INFORMATION_MESSAGE);
                }
             }
             catch(IOException ex)
             {
                      JOptionPane.showMessageDialog(null,
                      "Su archivo SUBSANABLE no se ha guardado",
                      "Advertencia",JOptionPane.WARNING_MESSAGE);
             }

            // Se salva el libro.
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
            
            
            
            // .............................................................................................. 
      }
        
          
      }
   
     /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName() ) {
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message =
                String.format("Completado %d%%.\n", progress);
            progressMonitor.setNote(message);
          
            
        }

    }
    
    public void cambiaMunicipio() {
        
        if (this.indGen != -1) {
            
   
            jTextField11.setBackground(Color.white);                                                         
            jTextField11.setText(this.tablaErrorCod[indGen][0]);  
            this.tablaDatos[indGen][6] = this.tablaErrorCod[indGen][0] ;
            jLabel41.setVisible(false);
            actuaMunicipio.setVisible(false);
            
            
        }
        
        
    }
    // ------------------------------------------------------------------------------------------------------------------------------------
    //  BUSCAR CORRESPONDENCIAS EN LOCUCIONES
    // ------------------------------------------------------------------------------------------------------------------------------------
      
    public void buscarCorrespondencias() {
        
        int i,j, nstr,flag=0,encontrado;
        String telLoc, telReg, dni, snombre, tdni, ndni,ldni,fel,dniReg;
        System.out.println("COMIENZO BUSQUEDA DE CORRESPONDENCIAS");
        
        for (i=0; i< this.nRegistros; i++) {this.locuciones[i] = 0 ; this.llamadasLocuciones[i][0] = 0; }
        
        
           for (i=0; i< this.nLocuciones; i++ ){
        //..............................................................................
       // for (i=this.nLocuciones-1; i> 0; i-- ){
            
            flag = 0 ;
            
            telLoc  = this.tablaLocuciones[i][6] ; telLoc = telLoc.trim();
            snombre = this.tablaLocuciones[i][2] ; snombre = snombre.trim();
            System.out.println("Estudio Locución i="+i+" -- Busco nombre="+snombre) ;
            nstr = snombre.length();
            System.out.println("snombre ="+snombre);
            if (nstr >0){
                tdni = snombre.substring(nstr-9, nstr) ; tdni = tdni.trim(); tdni = tdni.toUpperCase();

                nstr = tdni.length();                                                   // contamos cuantos numeros y letras tenemos

                fel = tdni.substring(0,1);                                              // tomamos el primer elemento

                if ( (fel.equals("X") || fel.equals("Y") || fel.equals("Z")) && nstr==9) {           // Puede ser un NIE

                    ndni = tdni.substring(1, 8); 
                    ldni = tdni.substring(8,9);  // System.out.println("nstr="+nstr+" -tdni ="+tdni+" -ndni ="+ndni+" -ldni ="+ldni);

                } else {                                                                // Puede ser un DNI

                    if (nstr == 9) {

                    ndni = tdni.substring(0, 8); 
                    ldni = tdni.substring(8,9);  // System.out.println("nstr="+nstr+" -tdni ="+tdni+" -ndni ="+ndni+" -ldni ="+ldni);
                    } else {
                        ndni="0";
                        ldni="0";
                    }
                }

                if (isNumeric(ndni) && !isNumeric(ldni) && nstr== 9) flag=1;        // Tenemos un dni o NIE

                
                j=0 ;
               // this.tablaLocuciones[i][21] = "-1" ;
                encontrado = 0 ;
                 if (flag ==1) {                                                     
                    while (j<this.nRegistros) {


                            dniReg = this.tablaDatos[j][10] ; dniReg = dniReg.trim(); dniReg.toUpperCase();

                            if (dniReg.equals(tdni)) {
                                
                                System.out.println("NIF:"+dniReg+"-> He encontrado correspondencia i="+i+ " con registro tablaDatos j="+j+" dia="+this.tablaLocuciones[i][20]);
                               
                                // ...............................................
                               
                                this.tablaLocuciones[i][21] = Integer.toString(j) ; // System.out.println(" this.tablaLocuciones["+i+"][21] ="+ this.tablaLocuciones[i][21]);
                                this.tablaDatos[j][32]      = Integer.toString(i) ;
                                
                                // ...............................................
                                
                                this.llamadasLocuciones[j][this.locuciones[j]] = i ;  System.out.println(" this.llamadasLocuciones[j][this.locuciones[j]] ="+ this.llamadasLocuciones[j][this.locuciones[j]] );
                               
                                // ...............................................
                               
                                this.locuciones[j] = this.locuciones[j]+1 ;      System.out.println("this.locuciones[j] ="+this.locuciones[j] );
                                
                                // ...............................................
                                
                                encontrado = 1 ;
                                break;
                            }      

                        j++ ;   

                    }
                }
                } else {                        // El nombre estaba vacio.
                    j=0 ;
                    encontrado = 0 ;
                    flag = 0;
                
                }
                if (flag ==0 || encontrado==0 ) {                                                     // No hay dni, buscamos por telefono
                    while (j<this.nRegistros) {


                            telReg = this.tablaDatos[j][16] ; telReg = telReg.trim();

                            if (telReg.equals(telLoc)) {
                               System.out.println("Telefono:"+telLoc+"-> He encontrado correspondencia i="+i+ " con registro tablaDatos j="+j+" dia="+this.tablaLocuciones[i][20]);
                               
                                // ...............................................
                                
                                this.tablaLocuciones[i][21] = Integer.toString(j) ;
                                this.tablaDatos[j][32] = Integer.toString(i) ;
                                
                                // ...............................................
                                
                                if (this.locuciones[j] <5 ) {
                                
                                  this.llamadasLocuciones[j][this.locuciones[j]] = i ;  System.out.println(" this.llamadasLocuciones[j][this.locuciones[j]] ="+ this.llamadasLocuciones[j][this.locuciones[j]] );
                                }
                                // ...............................................
                                
                                this.locuciones[j] = this.locuciones[j]+1 ;
                                break;
                            }      

                        j++ ;                
                    }
                }
           
           
         
        }
        
    }
    public void ActualizaRegistro(){
        int i,L,G,s1,s2,s3,s4,s5,D,s6,s7,s9;
        int resp=JOptionPane.showConfirmDialog(null,"¿Actualizar registro en Base de Datos?");
        int estadoInsert = 0 ;
        int ind=0;
        int indTablaSel=0, indTablaForm=0 ;
        String Fecha,strId,str;
        
        // ........................................................ Comprobación de que el registro es el correcto
        i = this.indGenReg ;
        strId = jTextField21.getText() ;
        
        indTablaSel = Integer.parseInt(this.tablaDatos[i][30]);
        indTablaForm =Integer.parseInt(strId);
        // ........................................................ 
        
        // ................................................................................................................
        if (JOptionPane.OK_OPTION == resp && indTablaForm==indTablaSel){
                System.out.println("Selecciona opción Afirmativa");
                System.out.println("Voy a comenzar la inserción del registro i="+i+ " con id_m_c="+this.tablaDatos[i][30]);
                
                this.tablaDatos[i][2] = jTextField27.getText();     // fecha producción
                System.out.println("i = "+i+" ->this.tablaDatos[i][2] ="+this.tablaDatos[i][2] ) ;
                Fecha = dateToMySQLDate(this.tablaDatos[i][2]);
                
                 this.tablaDatos[i][11] = jTextField16.getText();    // fecha firma contrato.
                
                 
                PymesDao miPymeDao = new PymesDao();
                PymesVo miPymes = new PymesVo();
             
                        // ..............................................
                        
                        ind = jComboBox4.getSelectedIndex() ;
                        this.tablaDatos[i][0] = Integer.toString(ind);  // estado
                        ind = jComboBox3.getSelectedIndex() ;
                        this.tablaDatos[i][1] = Integer.toString(ind); // incidencia
                        
                        this.tablaDatos[i][15] =jTextArea1.getText();   // observaciones
                        this.tablaDatos[i][20] =jTextArea2.getText();   // incidencia
                        this.tablaDatos[i][21] =jTextArea3.getText();   // solucion
                        
                        // ..............................................
                       /*  
                        if  (this.tablaDatos[i][4].equals("") ) L=0 ; else L = 1 ;
           
                        if  (this.tablaDatos[i][3].equals("") ) G=0; else G = 1 ;
           
                        if  (this.tablaDatos[i][3].equals("") && this.tablaDatos[i][4].equals("") ) {L=0; G = 0; D=0;} else D = 1 ;
                        */
                        
                        this.tablaDatos[i][33] = "0" ;
                        if (jCheckBox28.isSelected()) { G=1 ; this.tablaDatos[i][33] = "1";} else { G=0; }     //   Gas
                        if (jCheckBox29.isSelected()) { L=1 ; this.tablaDatos[i][33] = "2";} else { L=0 ; }    //   Luz
                        if (jCheckBox30.isSelected()) { D=1 ; this.tablaDatos[i][33] = "3";} else { D=0 ; }    //   Dual
                         
                        System.out.println(" TENEMOS UN CONTRATO CON L,G,D = ("+L+","+G+","+D+")") ;
                         
                        
                        // ..............................................
                        if (jCheckBox23.isSelected()) {this.tablaDatos[i][34] = "1" ; } else {this.tablaDatos[i][34] = "0" ; }  
                        if (jCheckBox24.isSelected()) {this.tablaDatos[i][35] = "1" ; } else {this.tablaDatos[i][35] = "0" ; }  
                        if (jCheckBox25.isSelected()) {this.tablaDatos[i][36] = "1" ; } else {this.tablaDatos[i][36] = "0" ; }  
                        if (jCheckBox26.isSelected()) {this.tablaDatos[i][37] = "1" ; } else {this.tablaDatos[i][37] = "0" ; }  
                        if (jCheckBox27.isSelected()) {this.tablaDatos[i][38] = "1" ; } else {this.tablaDatos[i][38] = "0" ; }  
                        if (jCheckBox33.isSelected()) {this.tablaDatos[i][45] = "1" ; } else {this.tablaDatos[i][45] = "0" ; }  
                        if (jCheckBox34.isSelected()) {this.tablaDatos[i][46] = "1" ; } else {this.tablaDatos[i][46] = "0" ; }  
                        
                        if (jCheckBox31.isSelected()) {this.tablaDatos[i][43] = "1";} else {this.tablaDatos[i][43] = "0" ; }     // Tur Gas
                        if (jCheckBox32.isSelected()) {this.tablaDatos[i][44] = "1";} else {this.tablaDatos[i][44] = "0" ; }     // Punteado
                        if (jCheckBox35.isSelected()) {this.tablaDatos[i][48] = "1";} else {this.tablaDatos[i][48] = "0" ; }     // Tarifa Plana
                        if (jCheckBox36.isSelected()) {this.tablaDatos[i][51] = "1";} else {this.tablaDatos[i][51] = "0" ; }     // SPP
              
                        if (this.tablaDatos[i][34].equals("1") )  s1=1; else s1=0 ;         // SVGComplet
        
                        if (this.tablaDatos[i][35].equals("1") )  s2=1; else s2=0 ;          // SVGXpres
        
                        if (this.tablaDatos[i][36].equals("1") )   s3=1; else s3=0 ;         // SVG Básico
        
                        if (this.tablaDatos[i][37].equals("1") )   s4=1; else s4=0 ;         //SVELECTRIC XPRES
        
                        if (this.tablaDatos[i][38].equals("1") )   s5=1; else s5=0 ;  
                        
                        if (this.tablaDatos[i][45].equals("1") )   s6=1; else s6=0 ;  
                         
                        if (this.tablaDatos[i][46].equals("1") )   s7=1; else s7=0 ;  
                        
                        if (this.tablaDatos[i][51].equals("1") )   s9=1; else s9=0 ;        // SPP
         
                        // ...........................................................................................
                        
                        System.out.println("Insertando registro i= "+i+ " - Incidencia= "+this.tablaDatos[i][1]);
                                  
                        miPymes.setIdContrato(Integer.parseInt(this.tablaDatos[i][30]));  System.out.println("id contrato a modificar!! ="+this.tablaDatos[i][30]);
                        
                        miPymes.setEstado(Integer.parseInt(this.tablaDatos[i][0]));
                        miPymes.setIncidencia(Integer.parseInt(this.tablaDatos[i][1]));
                        
                        Fecha = dateToMySQLDate(this.tablaDatos[i][2]);                        
                        miPymes.setFecha(Fecha);
                       
                        miPymes.setComercial(this.tablaDatos[i][17]);
                        
                        miPymes.setSwg(G);
                        miPymes.setSwe(L);
                        miPymes.setDualFuel(D);
                                                
                        miPymes.setCupsE(this.tablaDatos[i][4]);
                        miPymes.setCupsG(this.tablaDatos[i][3]);       
                        try {                      
                            miPymes.setCodPostal(Integer.parseInt(this.tablaDatos[i][5]));
                        } catch (NumberFormatException nfe){
                            miPymes.setCodPostal(Integer.parseInt("0"));
                        }
                        miPymes.setMunicipio(this.tablaDatos[i][6]);
                        miPymes.setProvincia(this.tablaDatos[i][7]);
                        miPymes.setDireccion(this.tablaDatos[i][8]);
                        miPymes.setTitular(this.tablaDatos[i][9]);
                        miPymes.setNifCif(this.tablaDatos[i][10]);
                        miPymes.setTelefonoCli(this.tablaDatos[i][16]);  
                        
                       
                        Fecha = dateToMySQLDate(this.tablaDatos[i][11]);
                        miPymes.setFechaFirma(Fecha);
                        
                         try {
                            miPymes.setConsumoElect(Double.parseDouble(this.tablaDatos[i][12]));
                            miPymes.setConsumoGas(Double.parseDouble(this.tablaDatos[i][13]));
                            
                         } catch (NumberFormatException nfe){
                            miPymes.setConsumoElect(Double.parseDouble("0"));
                            miPymes.setConsumoGas(Double.parseDouble("0"));
                         }
                       
                         miPymes.setTarifaElec(this.tablaDatos[i][29]);
                         miPymes.setTarifaGas(this.tablaDatos[i][28]);
                         
                        miPymes.setSVG_1(s1);
                        miPymes.setSVG_2(s2);
                        miPymes.setSVG_3(s3);
                        miPymes.setSVG_4(s4);
                        miPymes.setSVG_5(s5);
                        miPymes.setSVG_6(s6);
                        miPymes.setSVG_7(s7);
                        
                        miPymes.setSPP(s9);
                       
                        
                              
                        miPymes.setObservaciones(this.tablaDatos[i][15]);   
                        
                        miPymes.setsIncidencia(jTextArea2.getText());
                        miPymes.setsExplicacion(jTextArea3.getText());
                       
                        miPymes.setTurGas(Integer.parseInt(this.tablaDatos[i][43]));
                        
                        miPymes.setPunteado(Integer.parseInt(this.tablaDatos[i][44]));
                        miPymes.setAgenteCom(this.tablaDatos[i][41]);
                        
                        miPymes.setTarifaPlana(Integer.parseInt(this.tablaDatos[i][48]));
                        
                        str = this.tablaDatos[i][39].trim();
                        if (! str.equals("")) {
                             Fecha = dateToMySQLDate(str);
                             miPymes.setMemo(Fecha);
                        } else {
                             miPymes.setMemo("NULL"); System.out.println("------ VOY A BORRAR LA FECHA MEMO");
                        }
                        
                        estadoInsert = miPymeDao.modificarContrato(miPymes,this.plogin,this.ppassword);
                        System.out.println("Registro insertado ");
                        
        }
        // ................................................................................................................
        else{
            System.out.println("No selecciona una opción afirmativa");
            JOptionPane.showMessageDialog(null,
                                                     "\nNo se ha realizado la modificación id="+this.tablaDatos[i][30],
                                                     "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);
            
        }
        // ................................................................................................................
        
    }
    
    
    
    
    public String dateToMySQLDate(String fecha)
    {
        String df,y,m,d;
        
        d = fecha.substring(0, 2) ; System.out.println("dia ="+d);
        m = fecha.substring(3,5) ;  System.out.println("mes ="+m);
        y = fecha.substring(6,10) ;  System.out.println("año ="+y);
        
        df = y+"-"+m+"-"+d+ " 00:00:00";
       
        return df;
        
       
    }
    
    public void actualizaIncompletos() {
        int i;
        int cnt=0;
        
        for (i=0; i<this.nRegistros; i++) {
            
            if (this.tablaDatos[i][1].equals("3")){
                
                this.tablaDatosIncpl[cnt] = this.tablaDatos[i] ;
                cnt ++;
                
            }
            
        }
        this.Incompletos = cnt ;
        
        
    }
    // ----------------------------------------------------------------------------------------------------
    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
    
 
    
    public void leerArchivoExel() throws IOException {
        JFileChooser jfc = new JFileChooser();        
        jfc.showOpenDialog(this);
        File abre=jfc.getSelectedFile();
       
        JOptionPane.showMessageDialog(null,
                "\nEl nombre del archivo es:"+abre,
                "AVISO",JOptionPane.WARNING_MESSAGE);
        
        this.nombre = abre ;
	        // An excel file name. You can create a file name with a full
	        // path information.
	        //
	 //       String filename = "test.xls";
                String filename = this.nombre.getAbsolutePath();
	        //
	        // Create an ArrayList to store the data read from excel sheet.
	        //
	        List sheetData = new ArrayList();
	        FileInputStream fis = null;
	        try {
	            //
	            // Create a FileInputStream that will be use to read the
	            // excel file.
	            //
	            fis = new FileInputStream(filename);
	            //
	            // Create an excel workbook from the file system.
	            //
	            HSSFWorkbook workbook = new HSSFWorkbook(fis);
	            //
	            // Get the first sheet on the workbook.
	            //
	            HSSFSheet sheet = workbook.getSheetAt(0);
	            //
	            // When we have a sheet object in hand we can iterator on
	            // each sheet's rows and on each row's cells. We store the
	            // data read on an ArrayList so that we can printed the
	            // content of the excel to the console.
	            //
	            Iterator rows = sheet.rowIterator();
	            while (rows.hasNext()) {
	                HSSFRow row = (HSSFRow) rows.next();
	                 
	                Iterator cells = row.cellIterator();
	                List data = new ArrayList();
	                while (cells.hasNext()) {
	                    HSSFCell cell = (HSSFCell) cells.next();
	                //  System.out.println("Añadiendo Celda: " + cell.toString());
	                    data.add(cell);
	                }
	                sheetData.add(data);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (fis != null) {
	                fis.close();
	            }
	        }
	  //    showExelData(sheetData);
              procesaCertificaciones(sheetData);
	    }
	 
    public void showExelData(List sheetData) {
	        //
	        // Iterates the data and print it out to the console.
	        //
                
	        for (int i = 0; i < sheetData.size(); i++) {
	            List list = (List) sheetData.get(i);
	            for (int j = 0; j < list.size(); j++) {
	                Cell cell = (Cell) list.get(j);
	                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	                    System.out.print(cell.getNumericCellValue());
	                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
	                    System.out.print(cell.getRichStringCellValue());
	                } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
	                    System.out.print(cell.getBooleanCellValue());
	                }
	                if (j < list.size() - 1) {
	                    System.out.print(", ");
	                }
	            }
	            System.out.println("");
	        }
  }
     public void procesaCertificaciones(List sheetData) {
	        //
	        // Iterates the data and print it out to the console.
	        //
                int cnt=0;
                SimpleDateFormat formatDateJava = new SimpleDateFormat("dd-MM-yyyy");
              
                HSSFRichTextString richTextString ;
                
	        for (int i = 1; i < sheetData.size(); i++) {
                    
	            List list = (List) sheetData.get(i);
                    System.out.println(i+ " -> Tenemos Exel con "+list.size()+" columnas");
                    if (list.size()>=26 ){
                        
                        for (int j = 0; j < list.size(); j++) {

                            Cell cell = (Cell) list.get(j);
                            this.tablaCertificaciones[cnt][j] = "";

                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

                                this.tablaCertificaciones[cnt][j] = Double.toString(cell.getNumericCellValue());                            

                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

                               richTextString = (HSSFRichTextString) cell.getRichStringCellValue();

                         //       System.out.print(cell.getRichStringCellValue());

                                this.tablaCertificaciones[cnt][j] =  richTextString.getString();                                                        

                            } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {


                                 this.tablaCertificaciones[cnt][j] = Boolean.toString(cell.getBooleanCellValue());

                            } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {

                                this.tablaCertificaciones[cnt][j] = "";

                            }
                            
                    }
                        this.tablaCertificaciones[cnt][30] ="-1";
                        System.out.println(cnt+" -> Cargo registro con NIF="+this.tablaCertificaciones[cnt][11]+ " Y FECHA ="+this.tablaCertificaciones[cnt][3]) ;
              
                        cnt ++;
	            }
            //        System.out.println("---------");
                    
	            
	        }
                this.nCertificaciones = cnt;
                System.out.println("----------- HE CARGADO "+this.nCertificaciones+" REGISTROS DE CERTIFICACIONES ------------");                
                 
                JOptionPane.showMessageDialog(null,
                "\nHE CARGADO:"+this.nCertificaciones+" CERTIFICACIONES",
                "INFORMACIÓN",JOptionPane.WARNING_MESSAGE);
  }
// --------------------------------------------------------------------------------------------------------------------------------
      private void mostrarDatosTablaCertificaciones() {
		int i,j;
                System.out.println("----COMIENZO EL FORMATEO CERTIFICACIONES---");
           
                DefaultTableModel model;
		model = new DefaultTableModel();        // definimos el objeto tableModel
               
		miTablaCertifica = new JTable();                // creamos la instancia de la tabla
		miTablaCertifica.setModel(model);
                
                                 
		model.addColumn("Año");
		model.addColumn("Mes");
		model.addColumn("Periodo");
                model.addColumn("CUPS GAS");
                model.addColumn("CUPS ELEC.");
                model.addColumn("Producto");
                model.addColumn("Importe");
		model.addColumn("Fecha");
                model.addColumn("Fecha Rec BO");                
                model.addColumn("Delegación");
                model.addColumn("Codigo SAP");
		model.addColumn("Empresa");
                model.addColumn("Tipo");
                model.addColumn("Concepto");
                model.addColumn("Contrato");
                model.addColumn("NIF");
                model.addColumn("Calle");
                model.addColumn("NF");
                model.addColumn("Escalera");
                model.addColumn("Piso");
                model.addColumn("Puerta");
                model.addColumn("C.Postal");
                model.addColumn("Municipio");
                model.addColumn("Provincia");               
                model.addColumn("Consumo Gas");                
                model.addColumn("Consumo Elec");               
                model.addColumn("Grupo Tarifa");
                model.addColumn("Grupo Consumo");                
                model.addColumn("Grupo Comision");
                              
		miTablaCertifica.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		miTablaCertifica.getTableHeader().setReorderingAllowed(false);
                
                TableColumn columna1 = miTabla01.getColumn("CUPS GAS");
                TableColumn columna2 = miTabla01.getColumn("CUPS ELEC.");
                TableColumn columna3 = miTabla01.getColumn("Producto");
                TableColumn columna4 = miTabla01.getColumn("Importe");
               
                columna1.setMinWidth(130);
                columna2.setMinWidth(130);
                columna3.setMinWidth(150);
                columna4.setMinWidth(50);
                

		Object[] fila = new Object[33];
                // para llenar cada columna con lo datos almacenados
		for ( i = 0; i < this.nCertificaciones; i++) {
                    for (j=0; j<29; j++){
			fila[j] = this.tablaCertificaciones[i][j];          // es para cargar los datos en filas a la tabla modelo
                    }    
                    model.addRow(fila);
                }  
                
                      //  fila[4]  = formatDateJava.format(rs.getDate(5)) ; 
                      //  fila[19] = formatDateJava.format(rs.getDate(20)) ; 
                                        
		  
                 //Nueva instancia de la clase que contiene el formato
              //  FormatoTabla formato = new FormatoTabla();
                System.out.println("----HE DEFINIDO FORMATO Y PASO A ASIGNAR---");
                //Se obtiene la tabla y se establece el formato para cada tipo de dato
                
              //  miTabla02.setDefaultRenderer(Double.class, formato); 
              //  miTabla02.setDefaultRenderer(String.class, formato); 
              //  miTabla02.setDefaultRenderer(Integer.class, formato);
              //  miTabla02.setDefaultRenderer(Object.class, formato);
                
		miBarra02.setViewportView(miTablaCertifica);

	}
      // --------------------------------------------------------------------------------------------------------------
      public void buscarCorrespondenciasCertificaciones() {
        
        int i,j, nstr,flag=0,encontrado,cnt;
        String telLoc, telReg, dni, snombre, tdni, ndni,ldni,fel,dniReg;
        System.out.println("COMIENZO BUSQUEDA DE CORRESPONDENCIAS");
        cnt = 0;
        for (i=0; i< this.nCertificaciones; i++ ){
            
            flag = 0 ;
            
           
            snombre = this.tablaCertificaciones[i][11] ; snombre = snombre.trim();
            
            nstr = snombre.length(); System.out.println("Estamos con nstr="+nstr);
            if (nstr >8) {
            tdni = snombre.substring(nstr-9, nstr) ; tdni = tdni.trim(); tdni = tdni.toUpperCase();
            
            System.out.println("Estamos en tdni ="+tdni);
            
            nstr = tdni.length();                                                   // contamos cuantos numeros y letras tenemos
            
            fel = tdni.substring(0,1);                                              // tomamos el primer elemento
            
            if ( (fel.equals("X") || fel.equals("Y") || fel.equals("Z")) && nstr==9) {           // Puede ser un NIE
                
                ndni = tdni.substring(1, 8); 
                ldni = tdni.substring(8,9);  // System.out.println("nstr="+nstr+" -tdni ="+tdni+" -ndni ="+ndni+" -ldni ="+ldni);
          
            } else {                                                                // Puede ser un DNI
                
                if (nstr == 9) {
                
                ndni = tdni.substring(0, 8); 
                ldni = tdni.substring(8,9);  // System.out.println("nstr="+nstr+" -tdni ="+tdni+" -ndni ="+ndni+" -ldni ="+ldni);
                } else {
                    ndni="0";
                    ldni="0";
                }
            }
                  
            if (isNumeric(ndni) && !isNumeric(ldni) && nstr== 9) flag=1;        // Tenemos un dni o NIE
            
                        
            j=0 ;
           
            System.out.println(i+" ->BUSCANDO CORRESOPONDENCIA NIF:"+tdni);
            encontrado = 0 ;
             if (flag ==1) {                                                     
                while (j<this.nRegistros) {
                                                               
                        dniReg = this.tablaDatos[j][10] ; dniReg = dniReg.trim();
                        if (dniReg.equals(tdni)) {
                            System.out.println("NIF:"+dniReg+"-> He encontrado correspondencia i="+i+ " con registro tablaDatos j="+j+" dia="+this.tablaLocuciones[i][20]);
                            this.tablaCertificaciones[i][30] = Integer.toString(j) ; // System.out.println(" this.tablaLocuciones["+i+"][21] ="+ this.tablaLocuciones[i][21]);
                            this.tablaDatos[j][40]= Integer.toString(i) ;
                             System.out.println("CAMBIO REFERENCIA  this.tablaCertificaciones["+i+"][30]="+this.tablaCertificaciones[i][30]);
                            
                           
                             
                             encontrado = 1 ;
                            cnt ++;
                            break;
                        }      

                    j++ ;   
                    
                }
            }
          }    
        }
        
         JOptionPane.showMessageDialog(null,
         "\nHE CERTIFICADO :"+cnt+" CONTRATOS DE "+this.nCertificaciones+" EVALUADAS",
         "INFORMACIÓN",JOptionPane.WARNING_MESSAGE);
    }
    // -----------------------------------------------------------------------------------------------------------------------------------
     public void seleccionaElementoTabla(int id,int ind) {
         
         this.idSelect = id ;
         this.indTabla = ind ;
         
     }
     // -------------------------------------------------------------------------------------------------------------------------------------
       public void CambiarEstadoRegistroTabla(int opc){
        int i,L,G,s1,s2,s3,s4,s5,D;
        int estadoInsert = 0 ;
        int ind=0;
        String incidencia="";
        switch (opc) {
            case 0:
                   incidencia = "BLANCO";
                   break;
            case 1:
                   incidencia = "AMARILLO";
                   break;
            case 2:
                   incidencia = "NARANJA";
                   break;
            case 3:
                   incidencia = "¡ROJO!";
                   break;
            case 4:
                   incidencia = "VERDE";
                   break;      
            case 5:
                   incidencia = "MORADO";
                   break;   
            case 6:
                   incidencia = "AZUL";
                   break;  
        }
        
        
        int resp=JOptionPane.showConfirmDialog(null,"¿Quieres cambiar registro id="+this.idSelect+" a ESTADO "+incidencia+" ?");
       
        String Fecha;
        
        i = this.idSelect ;
        
        if (JOptionPane.OK_OPTION == resp){
                System.out.println("Selecciona opción Afirmativa");
                System.out.println("Voy a comenzar la actualización del registro i="+this.indTabla+ " con id_m_c="+i);
              
                this.tablaDatos[this.indTabla][1] = String.valueOf(opc) ;
                PymesDao miPymeDao = new PymesDao();
                PymesVo miPymes = new PymesVo();
                miPymes.setIdContrato(this.idSelect);
                miPymes.setIncidencia(opc);
                                          
                estadoInsert = miPymeDao.modificarTablaIncidencia(miPymes,this.plogin,this.ppassword);
                System.out.println("Registro modificado ");
                
                if (opc==3){                                        // Si Incidencia es ROJO actualizamos ESTADO a KO
                     
                    miPymes.setIdContrato(this.idSelect);
                    miPymes.setEstado(3);                                           
                    estadoInsert = miPymeDao.modificarTablaEstado(miPymes,this.plogin,this.ppassword);
                    this.tablaDatos[this.indTabla][0] = "3" ;
                }
               
                        
        }
        else{
            System.out.println("No selecciona una opción afirmativa");
        }
        
    }
      // -------------------------------------------------------------------------------------------------------------------------------------
       public void CambiarEstadoFinalRegistroTabla(int opc){
        int i,L,G,s1,s2,s3,s4,s5,D;
        int estadoInsert = 0 ;
        int ind=0;
        String incidencia="";
        switch (opc) {
            case 0:
                   incidencia = "PENDIENTE";
                   break;
            case 1:
                   incidencia = "DOCOUT";
                   break;
            case 2:
                   incidencia = "CERTIFICADO";
                   break;
                                          
        }
        
        
        int resp=JOptionPane.showConfirmDialog(null,"¿Quieres cambiar ESTADO del registro id="+this.idSelect+" a ESTADO "+incidencia+" ?");
       
        String Fecha;
        
        i = this.idSelect ;
        
        if (JOptionPane.OK_OPTION == resp){
                System.out.println("Selecciona opción Afirmativa");
                System.out.println("Voy a comenzar la actualización del registro i="+this.indTabla+ " con id_m_c="+i);
              
               
                this.tablaDatos[this.indTabla][0] = String.valueOf(opc) ;
                PymesDao miPymeDao = new PymesDao();
                PymesVo miPymes = new PymesVo();
                miPymes.setIdContrato(this.idSelect);
                miPymes.setEstado(opc);   
                
                estadoInsert = miPymeDao.modificarTablaEstado(miPymes,this.plogin,this.ppassword);
                                          
                System.out.println("Registro modificado ");
                     
        }
        else{
            System.out.println("No selecciona una opción afirmativa");
        }
        
    }
       // -------------------------------------------------------------------------------------------------------------------------------------
       public void CambiarRegistroTabla(){
           
        int i,L,G,s1,s2,s3,s4,s5,D,s9;
        int resp=JOptionPane.showConfirmDialog(null,"¿Deseas actualizar el registro id="+this.idSelect+" en Base de Datos?");
        int estadoInsert = 0 ;
        int ind=0;
        String Fecha;
        
        i = this.indTabla;
        
        if (JOptionPane.OK_OPTION == resp){
                System.out.println("Selecciona opción Afirmativa");
                System.out.println("Voy a comenzar la inserción del registro i="+i+ " con id_m_c="+this.tablaDatos[i][30]);
                
                Fecha = dateToMySQLDate(this.tablaDatos[i][2]);
                
                PymesDao miPymeDao = new PymesDao();
                PymesVo miPymes = new PymesVo();
             
                    
                        // ..............................................
                        
                        ind = jComboBox4.getSelectedIndex() ;
                        this.tablaDatos[i][0] = Integer.toString(ind);  // estado
                        ind = jComboBox3.getSelectedIndex() ;
                        this.tablaDatos[i][1] = Integer.toString(ind); // incidencia
                        // ..............................................
                        
                        
                        if  (this.tablaDatos[i][4].equals("") ) L=1 ; else L = 0 ;
           
                        if  (this.tablaDatos[i][3].equals("") ) G=1; else G = 0 ;
           
                        if  (this.tablaDatos[i][3].equals("") && this.tablaDatos[i][4].equals("") ) {L=0; G = 0; D=1;} else D = 0 ;
           
                        // ..............................................
                        if (jCheckBox23.isSelected()) {this.tablaDatos[i][34] = "1" ; }
                        if (jCheckBox24.isSelected()) {this.tablaDatos[i][35] = "1" ; }
                        if (jCheckBox25.isSelected()) {this.tablaDatos[i][36] = "1" ; }
                        if (jCheckBox26.isSelected()) {this.tablaDatos[i][37] = "1" ; }
                        if (jCheckBox27.isSelected()) {this.tablaDatos[i][38] = "1" ; }
                        
                        if (jCheckBox31.isSelected()) {this.tablaDatos[i][43] = "1" ; }
              
                        if (this.tablaDatos[i][34].equals("1") )  s1=1; else s1=0 ;         // SVGComplet
        
                        if (this.tablaDatos[i][35].equals("1") )  s2=1; else s2=0 ;          // SVGXpres
        
                        if (this.tablaDatos[i][36].equals("1") )   s3=1; else s3=0 ;         // SVG Básico
        
                        if (this.tablaDatos[i][37].equals("1") )   s4=1; else s4=0 ;         //SVELECTRIC XPRES
        
                        if (this.tablaDatos[i][38].equals("1") )   s5=1; else s5=0 ;  
                        
                        if (this.tablaDatos[i][51].equals("1") )   s9=1; else s9=0 ;           // SPP
         
                        // ...........................................................................................
                        
                        System.out.println("Insertando registro i= "+i+ " - Incidencia= "+this.tablaDatos[i][1]+ "Turgas ="+this.tablaDatos[i][43]);
                                  
                        miPymes.setIdContrato(Integer.parseInt(this.tablaDatos[i][30]));
                        
                        miPymes.setEstado(Integer.parseInt(this.tablaDatos[i][0]));
                        miPymes.setIncidencia(Integer.parseInt(this.tablaDatos[i][1]));
                        
                        Fecha = dateToMySQLDate(this.tablaDatos[i][2]);                        
                        miPymes.setFecha(Fecha);
                       
                        miPymes.setComercial(this.tablaDatos[i][17]);
                        
                        miPymes.setSwg(G);
                        miPymes.setSwe(L);
                        miPymes.setDualFuel(D);
                                                
                        miPymes.setCupsE(this.tablaDatos[i][4]);
                        miPymes.setCupsG(this.tablaDatos[i][3]);       
                        try {                      
                            miPymes.setCodPostal(Integer.parseInt(this.tablaDatos[i][5]));
                        } catch (NumberFormatException nfe){
                            miPymes.setCodPostal(Integer.parseInt("0"));
                        }
                        miPymes.setMunicipio(this.tablaDatos[i][6]);
                        miPymes.setProvincia(this.tablaDatos[i][7]);
                        miPymes.setDireccion(this.tablaDatos[i][8]);
                        miPymes.setTitular(this.tablaDatos[i][9]);
                        miPymes.setNifCif(this.tablaDatos[i][10]);
                        miPymes.setTelefonoCli(this.tablaDatos[i][16]);  
                        
                        Fecha = dateToMySQLDate(this.tablaDatos[i][11]);
                        miPymes.setFechaFirma(Fecha);
                        
                         try {
                            miPymes.setConsumoElect(Double.parseDouble(this.tablaDatos[i][12]));
                            miPymes.setConsumoGas(Double.parseDouble(this.tablaDatos[i][13]));
                            
                         } catch (NumberFormatException nfe){
                            miPymes.setConsumoElect(Double.parseDouble("0"));
                            miPymes.setConsumoGas(Double.parseDouble("0"));
                         }
                       
                         miPymes.setTarifaElec(this.tablaDatos[i][29]);
                         miPymes.setTarifaGas(this.tablaDatos[i][28]);
                         
                        miPymes.setSVG_1(s1);
                        miPymes.setSVG_2(s2);
                        miPymes.setSVG_3(s3);
                        miPymes.setSVG_4(s4);
                        miPymes.setSVG_5(s5);
                        
                        miPymes.setSPP(s9);
                              
                        miPymes.setObservaciones(this.tablaDatos[i][15]);   
                        
                        miPymes.setsIncidencia(jTextArea2.getText());
                        miPymes.setsExplicacion(jTextArea3.getText());
                        
                        miPymes.setTurGas(Integer.parseInt(this.tablaDatos[i][43]));
                          
                        estadoInsert = miPymeDao.modificarContrato(miPymes,this.plogin,this.ppassword);
                        System.out.println("Registro insertado ");
                        
        }
        else{
            System.out.println("No selecciona una opción afirmativa");
        }
           
           
           
       }
       // ------------------------------------------------------------------------------------------------- 
       public void MarcaColorKO() {
           
           
             jTextField4.setBackground(new Color(0xFE899B));// cups electrico
             jTextField5.setBackground(new Color(0xFE899B));// cups gas
         
             jTextField7.setBackground(new Color(0xFE899B));// agente
          
             jTextField10.setBackground(new Color(0xFE899B));// codigo postal
             jTextField11.setBackground(new Color(0xFE899B));// municipio
             jTextField12.setBackground(new Color(0xFE899B));// provincia
             jTextField13.setBackground(new Color(0xFE899B));// direccion
             jTextField14.setBackground(new Color(0xFE899B));// titular
             jTextField15.setBackground(new Color(0xFE899B));// nif/cif
             jTextField16.setBackground(new Color(0xFE899B));// fecha firma cliente
         
             jTextField18.setBackground(new Color(0xFE899B));// consumo kwha
        
             jTextField22.setBackground(new Color(0xFE899B));// telefono
             jTextField23.setBackground(new Color(0xFE899B));// persona contacto
             jTextArea1.setBackground(new Color(0xFE899B));// observaciones
             jTextArea2.setBackground(new Color(0xFE899B));// incidencia
             jTextArea3.setBackground(new Color(0xFE899B));// solucion
             jTextField2.setBackground(new Color(0xFE899B));// consumo gas
             jTextField19.setBackground(new Color(0xFE899B));// tarifa gas
             jTextField20.setBackground(new Color(0xFE899B));// tarifa elec
            
             jTextField20.setBackground(new Color(0xFE899B));// id_m_r
             jTextField24.setBackground(new Color(0xFE899B));// id_m_r
            
             jTextField74.setBackground(new Color(0xFE899B));// Agente comercial
           
       }
       // ------------------------------------------------------------------------------------------------- 
        public void MarcaColorOK() {
           
           
             jTextField4.setBackground(Color.white);// cups electrico
             jTextField5.setBackground(Color.white);// cups gas
         
             jTextField7.setBackground(Color.white);// agente
          
             jTextField10.setBackground(Color.white);// codigo postal
             jTextField11.setBackground(Color.white);// municipio
             jTextField12.setBackground(Color.white);// provincia
             jTextField13.setBackground(Color.white);// direccion
             jTextField14.setBackground(Color.white);// titular
             jTextField15.setBackground(Color.white);// nif/cif
             jTextField16.setBackground(Color.white);// fecha firma cliente
         
             jTextField18.setBackground(Color.white);// consumo kwha
        
             jTextField22.setBackground(Color.white);// telefono
             jTextField23.setBackground(Color.white);// persona contacto
             jTextArea1.setBackground(Color.white);// observaciones
             jTextArea2.setBackground(Color.white);// incidencia
             jTextArea3.setBackground(Color.white);// solucion
             jTextField2.setBackground(Color.white);// consumo gas
             jTextField19.setBackground(Color.white);// tarifa gas
             jTextField20.setBackground(Color.white);// tarifa elec
            
             jTextField20.setBackground(Color.white);// id_m_r
             jTextField24.setBackground(Color.white);// id_m_r
            
             jTextField74.setBackground(Color.white);// Agente comercial
           
       }
        // ------------------------------------------------------------------------------------------------- 
        public void MarcaColorIncidencia(int incidencia) {
            Color cResaltado = new Color(0xFFFF99) ;    // amarillo
            switch (incidencia) {
                case 1:
                       cResaltado = new Color(0xFFFF99) ;    // amarillo
                break;
                case 2:
                       cResaltado = new Color(0xFFCC99) ;    // naranja
                break;
                case 4:
                       cResaltado = new Color(0x66FF66) ;    // verde
                break;
                case 5:
                       cResaltado = new Color(0xFF99FF) ;    // Morado
                break;
                case 6:
                       cResaltado = new Color(0x99CCFF) ;    // azul
                break;
               
            }
            
            System.out.println("voy a marcar con incidencia="+incidencia);
            
             jTextField4.setBackground(cResaltado);// cups electrico
             jTextField5.setBackground(cResaltado);// cups gas
         
             jTextField7.setBackground(cResaltado);// agente
          
             jTextField10.setBackground(cResaltado);// codigo postal
             jTextField11.setBackground(cResaltado);// municipio
             jTextField12.setBackground(cResaltado);// provincia
             jTextField13.setBackground(cResaltado);// direccion
             jTextField14.setBackground(cResaltado);// titular
             jTextField15.setBackground(cResaltado);// nif/cif
             jTextField16.setBackground(cResaltado);// fecha firma cliente
         
             jTextField18.setBackground(cResaltado);// consumo kwha
        
             jTextField22.setBackground(cResaltado);// telefono
             jTextField23.setBackground(cResaltado);// persona contacto
             jTextArea1.setBackground(cResaltado);// observaciones
             jTextArea2.setBackground(cResaltado);// incidencia
             jTextArea3.setBackground(cResaltado);// solucion
             jTextField2.setBackground(cResaltado);// consumo gas
             jTextField19.setBackground(cResaltado);// tarifa gas
             jTextField20.setBackground(cResaltado);// tarifa elec
            
             jTextField20.setBackground(cResaltado);// id_m_r
             jTextField24.setBackground(cResaltado);// id_m_r
            
             jTextField74.setBackground(cResaltado);// Agente comercial
           
       }
       // ------------------------------------------------------------------------------------------------- 
        public void aumentaFuenteTabla() {
            
            this.fuente = 1 ;
            mostrarDatosConTableModel();
           
            
        }
        // ------------------------------------------------------------------------------------------------- 
        public void reduceFuenteTabla() {
             this.fuente = 0 ;
           mostrarDatosConTableModel();
            
        }        
              
        // ------------------------------------------------------------------------------------------------- 
        public void buscarID() {
        int i, nStr=0, id=-1 ;
        String sBusqueda, sID ;
        sBusqueda = JOptionPane.showInputDialog("Introduce el indice de contrato que buscas: ");
        
        int resp=JOptionPane.showConfirmDialog(null,"¿Buscar el Indice de contrato ="+sBusqueda+" ?");
        
        
        if (JOptionPane.OK_OPTION == resp){
        
            sBusqueda    = sBusqueda.trim();
            nStr        = sBusqueda.length() ;
            if ( nStr > 0) {
                    for (i=0; i<this.nRegistros; i++){

                        sID = this.tablaDatos[i][30].trim();
                       if (sID.equals(sBusqueda)) {
                           
                           System.out.println("HE ENCONTRADO EL ID="+sID) ;
                           
                           actualizarFormulario(i) ;
                           break;
                           
                       }

                    }
            }
        }
        
        }
       // ------------------------------------------------------------------------------------------------- 
        public void buscarCUPSe() {
        int i, nStr=0, id=-1 ;
        String sBusqueda, sID ;
        sBusqueda = JOptionPane.showInputDialog("Introduce el CUPS electrico de contrato que buscas: ");
        
        int resp=JOptionPane.showConfirmDialog(null,"¿Buscar el CUPS de contrato ="+sBusqueda+" ?");
        
        
        if (JOptionPane.OK_OPTION == resp){
        
            sBusqueda    = sBusqueda.trim();
            nStr        = sBusqueda.length() ;
            if ( nStr > 0) {
                    for (i=0; i<this.nRegistros; i++){
                       
                        sID = this.tablaDatos[i][4].trim();
                       
                       if (sID.equals(sBusqueda)) {
                           
                           System.out.println("HE ENCONTRADO EL ID="+sID) ;
                           
                           actualizarFormulario(i) ;
                           break;
                           
                       }

                    }
            }
        }
        
        }
       // -------------------------------------------------------------------------------------------------  
        public void buscarCUPSg() {
        int i, nStr=0, id=-1 ;
        String sBusqueda, sID ;
        sBusqueda = JOptionPane.showInputDialog("Introduce el CUPS Gas de contrato que buscas: ");
        
        int resp=JOptionPane.showConfirmDialog(null,"¿Buscar el CUPSg de contrato ="+sBusqueda+" ?");
        
        
        if (JOptionPane.OK_OPTION == resp){
        
            sBusqueda    = sBusqueda.trim();
            nStr        = sBusqueda.length() ;
            if ( nStr > 0) {
                    for (i=0; i<this.nRegistros; i++){
                       
                        sID = this.tablaDatos[i][3].trim();
                        
                       if (sID.equals(sBusqueda)) {
                           
                           System.out.println("HE ENCONTRADO EL ID="+sID) ;
                           
                           actualizarFormulario(i) ;
                           break;
                           
                       }

                    }
            }
        }
        
        }
       // -------------------------------------------------------------------------------------------------  
         public void buscaNIF() {
        int i, nStr=0, id=-1 ;
        String sBusqueda, sID ;
        sBusqueda = JOptionPane.showInputDialog("Introduce el NIF/CIF de contrato que buscas: ");
        
        int resp=JOptionPane.showConfirmDialog(null,"¿Buscar el NIF/CIF de contrato ="+sBusqueda+" ?");
        
        
        if (JOptionPane.OK_OPTION == resp){
        
            sBusqueda    = sBusqueda.trim();
            nStr        = sBusqueda.length() ;
            if ( nStr > 0) {
                    for (i=0; i<this.nRegistros; i++){
                       
                        sID = this.tablaDatos[i][10].trim();
                      
                       if (sID.equals(sBusqueda)) {
                           
                           System.out.println("HE ENCONTRADO EL ID="+sID) ;
                           
                           actualizarFormulario(i) ;
                           break;
                           
                       }

                    }
            }
        }
        
        }
       // -------------------------------------------------------------------------------------------------  
        public void buscaTelefono() {
        int i, nStr=0, id=-1 ;
        String sBusqueda, sID ;
        sBusqueda = JOptionPane.showInputDialog("Introduce el Telefono de contrato que buscas: ");
        
        int resp=JOptionPane.showConfirmDialog(null,"¿Buscar el Telefono de contrato ="+sBusqueda+" ?");
        
        
        if (JOptionPane.OK_OPTION == resp){
        
            sBusqueda    = sBusqueda.trim();
            nStr        = sBusqueda.length() ;
            if ( nStr > 0) {
                    for (i=0; i<this.nRegistros; i++){
                       
                       sID = this.tablaDatos[i][16].trim();
                      
                       if (sID.equals(sBusqueda)) {
                           
                           System.out.println("HE ENCONTRADO EL ID="+sID) ;
                           
                           actualizarFormulario(i) ;
                           break;
                           
                       }

                    }
            }
        }
        
        }
       // -------------------------------------------------------------------------------------------------  
        public void buscaTitular() {
        int i, nStr=0, id=-1 ;
        String sBusqueda, sID ;
        sBusqueda = JOptionPane.showInputDialog("Introduce el Nombre del titular de contrato que buscas: ");
        
        int resp=JOptionPane.showConfirmDialog(null,"¿Buscar el Titular de contrato ="+sBusqueda+" ?");
        
        
        if (JOptionPane.OK_OPTION == resp){
        
            sBusqueda    = sBusqueda.trim();
            nStr        = sBusqueda.length() ;
            if ( nStr > 0) {
                    for (i=0; i<this.nRegistros; i++){
                       
                       sID = this.tablaDatos[i][9].trim();
                       sID.toUpperCase();
                      
                       if (sID.equals(sBusqueda)) {
                           
                           System.out.println("HE ENCONTRADO EL ID="+sID) ;
                           
                           actualizarFormulario(i) ;
                           break;
                           
                       }

                    }
            }
        }
        
        }
       // -------------------------------------------------------------------------------------------------
       public void actualizarCalculosLiquidaciones() {
           
                int p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,ptot,nReg;
                int pn1,pn2,pn3,pn4,pn5,pn6,pn7,pn8,pn9,pn10,pntot;
                double v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,vtot,val,v11,v12,v13;
                double vn1,vn2,vn3,vn4,vn5,vn6,vn7,vn8,vn9,vn10,vntot;
                 String fechaSel2 ="";
           
                String dd = jTextField40.getText() ;
                String mm = jTextField72.getText() ;
                String aa = jTextField73.getText() ;
                
                String dd2 = jTextField9.getText() ;
                String mm2 = jTextField17.getText() ;
                String aa2 = jTextField29.getText() ;
                
                String fechaSel = aa+"-"+mm+"-"+dd+" 00:00:00" ;
                
                if (!dd2.equals("00") && !mm2.equals("00")){
                     fechaSel2 = aa2+"-"+mm2+"-"+dd2+" 00:00:00" ;
                }
                
                System.out.println("----COMIENZO EL FORMATEO TABLA LIQUIDACIONES --- Fecha = "+fechaSel);
                
                
                DefaultTableModel model;
		model = new DefaultTableModel();        // definimos el objeto tableModel
               
		miTablaLiquida = new JTable();                // creamos la instancia de la tabla
		miTablaLiquida.setModel(model);
                
                model.addColumn("idColor");                        
		model.addColumn("TUR");                
		model.addColumn("CUPS GAS");
		model.addColumn("CUPS ELECTRICIDAD");
		model.addColumn("TITULAR");
                model.addColumn("CIF/NIF");
                model.addColumn("SVG C");
                model.addColumn("SVG X");
		model.addColumn("SVG B");
                model.addColumn("SVE");
                model.addColumn("SVH");
                model.addColumn("SVGCC");
                model.addColumn("SVGSC");
                model.addColumn("SPP");
                model.addColumn("T.PLANA");
                model.addColumn("OBSERVACIONES");
                model.addColumn("Nº DE PRODUCTOS");
               
                
                
		miTablaLiquida.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		miTablaLiquida.getTableHeader().setReorderingAllowed(false);
                
                TableColumn columna1 = miTablaLiquida.getColumn("CUPS GAS");
                TableColumn columna2 = miTablaLiquida.getColumn("CUPS ELECTRICIDAD");
                TableColumn columna3 = miTablaLiquida.getColumn("TITULAR");
                TableColumn columna4 = miTablaLiquida.getColumn("OBSERVACIONES");
               
                columna1.setMinWidth(230);
                columna2.setMinWidth(230);
                columna3.setMinWidth(150);
                columna4.setMinWidth(250);

		PymesDao miPymesDao2 = new PymesDao();
		/*
		 * enviamos el objeto TableModel, como mandamos el objeto podemos
		 * manipularlo desde el metodo
		 */
		nReg=miPymesDao2.tablaLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,fechaSel2);
                this.nLiquidaciones = nReg;
                // ................................................................................................................................
                
                p1 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,0,fechaSel2);
                p2 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,1,fechaSel2);
                p3 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,2,fechaSel2);
                p4 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,3,fechaSel2); 
                p5 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,4,fechaSel2);
                p6 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,5,fechaSel2);
                p7 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,6,fechaSel2); 
                p8 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,7,fechaSel2);
                
                p9 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,8,fechaSel2);
                p10 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,9,fechaSel2);
                
                 p11 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,10,fechaSel2);
                 p12 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,11,fechaSel2);
                 p13 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2);
               
                // ................................................................................................................................
               
                jTextField42.setText(String.valueOf(p5));           // Servihogar
                jTextField46.setText(String.valueOf(p4));           // Servielectric
                jTextField47.setText(String.valueOf(p1));           // Servigas complet
                jTextField48.setText(String.valueOf(p2+p3));        // Servigas basic + express
                jTextField49.setText(String.valueOf(p6));           // Servigas con calefaccion
                jTextField50.setText(String.valueOf(p7));           // Servigas sin calefaccion
                
                jTextField56.setText(String.valueOf(p8));           // TUR
                
             // jTextField108.setText(String.valueOf(p9-p8));        // SWITCHING GAS
             //   jTextField111.setText(String.valueOf(p10));        // SWITCHING ELECTRICO
                
                
                jTextField108.setText(String.valueOf(p9-p8-p11));        // SWITCHING GAS
                jTextField111.setText(String.valueOf(p10-p12));          // SWITCHING ELECTRICO
                
                jTextField129.setText(String.valueOf(p11));          // SWITCHING GAS CON TARIFA PLANA
                jTextField133.setText(String.valueOf(p12));          // SWITCHING ELECTRICO CON TARIFA PLANA
                jTextField137.setText(String.valueOf(p13));          // SPP PLAN DE PROTECCION DE PAGOS
                          
                // ................................................................................................................................
                
                val = Double.parseDouble(jTextField42.getText()) ;
                
                v1 = p5 * Double.parseDouble(jTextField44.getText());   jTextField45.setText(String.valueOf(v1)); System.out.println("----------------------- V1="+v1);
                v2 = p4 * Double.parseDouble(jTextField58.getText());   jTextField64.setText(String.valueOf(v2));
                v3 = p1 * Double.parseDouble(jTextField59.getText());   jTextField65.setText(String.valueOf(v3));
                v4 = (p2+p3) * Double.parseDouble(jTextField60.getText());  jTextField66.setText(String.valueOf(v4));
                v5 = p6 * Double.parseDouble(jTextField61.getText());   jTextField67.setText(String.valueOf(v5));
                v6 = p7 * Double.parseDouble(jTextField62.getText());   jTextField68.setText(String.valueOf(v6));
                v7 = p8 * Double.parseDouble(jTextField63.getText());   jTextField69.setText(String.valueOf(v7));
                v8 = (p9-p8-p11) * Double.parseDouble(jTextField109.getText());  jTextField110.setText(String.valueOf(v8));
                v9 = (p10-p12) * Double.parseDouble(jTextField113.getText());  jTextField114.setText(String.valueOf(v9));
                
                v11 = p11 * Double.parseDouble(jTextField127.getText());  jTextField126.setText(String.valueOf(v11));
                v12 = p12 * Double.parseDouble(jTextField131.getText());  jTextField130.setText(String.valueOf(v12));
                v13 = p13 * Double.parseDouble(jTextField135.getText());  jTextField134.setText(String.valueOf(v13)); 
                  
                // ................................................................................................................................
                
                pn1 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,0,fechaSel2);
                pn2 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,1,fechaSel2);
                pn3 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,2,fechaSel2);
                pn4 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,3,fechaSel2); 
                pn5 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,4,fechaSel2);
                pn6 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,5,fechaSel2);
                pn7 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,6,fechaSel2); 
                pn8 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,7,fechaSel2);
                
                pn9 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,8,fechaSel2);
                pn10= miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,9,fechaSel2);
               
                 // ................................................................................................................................
               
                jTextField76.setText(String.valueOf(pn5));           // Servihogar
                jTextField77.setText(String.valueOf(pn4));           // Servielectric
                jTextField78.setText(String.valueOf(pn1));           // Servigas complet
                jTextField80.setText(String.valueOf(pn2+pn3));        // Servigas basic + express
                jTextField81.setText(String.valueOf(pn6));           // Servigas con calefaccion
                jTextField82.setText(String.valueOf(pn7));           // Servigas sin calefaccion
                
                jTextField83.setText(String.valueOf(pn8));           // TUR
                
                jTextField115.setText(String.valueOf(pn9-pn8));        // SWITCHING GAS
                jTextField119.setText(String.valueOf(pn10));          // SWITCHING ELECTRICO
                
                // ................................................................................................................................
                
                vn1 = pn5 * Double.parseDouble(jTextField84.getText());  jTextField85.setText(String.valueOf(vn1));
                vn2 = pn4 * Double.parseDouble(jTextField87.getText());  jTextField88.setText(String.valueOf(vn2));
                vn3 = pn1 * Double.parseDouble(jTextField90.getText());  jTextField91.setText(String.valueOf(vn3));
                vn4 = (pn2+pn3) * Double.parseDouble(jTextField96.getText());  jTextField100.setText(String.valueOf(vn4));
                vn5 = pn6 * Double.parseDouble(jTextField97.getText());  jTextField101.setText(String.valueOf(vn5));
                vn6 = pn7 * Double.parseDouble(jTextField99.getText());  jTextField102.setText(String.valueOf(vn6));
                vn7 = pn8 * Double.parseDouble(jTextField98.getText());  jTextField103.setText(String.valueOf(vn7));
                vn8 = (pn9-pn8) * Double.parseDouble(jTextField117.getText());  jTextField118.setText(String.valueOf(vn8));
                vn9 = pn10 * Double.parseDouble(jTextField121.getText());  jTextField122.setText(String.valueOf(vn9));
                
                // ................................................................................................................................
                
                ptot = p1+p2+p3+p4+p5+p6+p7+p8+p9+p10+p13+pn1+pn2+pn3+pn4+pn5+pn6+pn7+pn8+pn9+pn10  ;  
                
                jTextField70.setText(String.valueOf(ptot));
                
                vtot = v1+v2+v3+v4+v5+v6+v7+v8+v9+v11+v12+v13-vn1-vn2-vn3-vn4-vn5-vn6-vn7-vn8-vn9 ; 
                
                jTextField71.setText(String.valueOf(vtot));
              
                
                // ................................................................................................................................
                
                 //Nueva instancia de la clase que contiene el formato
                FormatoTablaLiquida formato = new FormatoTablaLiquida();
                
                formato.fuente = this.fuente ;
                
                //Se obtiene la tabla y se establece el formato para cada tipo de dato
                
                miTablaLiquida.setDefaultRenderer(Double.class, formato); 
                miTablaLiquida.setDefaultRenderer(String.class, formato); 
                miTablaLiquida.setDefaultRenderer(Integer.class, formato);
                miTablaLiquida.setDefaultRenderer(Object.class, formato);
                
                         
		miBarra03.setViewportView(miTablaLiquida);
                
        
                
                
           
           
       } 
       // -----------------------------------------------------------------------------------------------------------
       public void GenerarExelLiquida(){
           
        String col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12,col13,col14,col15,col16,col17 ;
       
        
       
        int i,j,nR;
       
        String sFecha=jTextField40.getText()+jTextField72.getText()+jTextField73.getText();
        
        
        System.out.println("APUNTO DE GENERAR EXEL LIQUIDACIONES");
        
        int resp=JOptionPane.showConfirmDialog(null,"¿Crear Informe de LIQUIDACIÓN:"+sFecha+" ?");
        
        
        if (JOptionPane.OK_OPTION == resp){
        
        
           

                    nR  = this.nLiquidaciones ;
                   




            HSSFWorkbook libro = new HSSFWorkbook();        
            HSSFSheet hoja = libro.createSheet("DEVUELTOS");
            Row fila = hoja.createRow(0);        
            Cell celda;


            String[] titulos = { "idColor","TurGas","CUPS Gas","CUPS Electricidad","TITULAR","CIF/NIF",
                                 "SVGC","SVGX","SVG B","SVE","SVGH",
                                 "SVG con Calef","SVG Sin Calef","SPP","T.PLANA","OBSERVACIONES","Nº DE PRODUCTOS" };                                                                   // 13 CAMPOS

           
           
            // Creamos el encabezado

            for (i = 0; i < titulos.length; i++) {
                  celda = fila.createCell(i);
                  celda.setCellValue(titulos[i]);
            }


            for (j=0; j<nR; j++) {

              

               // Nueva fila 
               i = 0 ;
               fila = hoja.createRow(j+1);

               System.out.println("Inserto celdas Exel en fila  ="+j);

               
               col1 = String.valueOf(miTablaLiquida.getValueAt(j, 0)) ;     //idcolor
               col2 = String.valueOf(miTablaLiquida.getValueAt(j, 1)) ;     // tur
               col3 = (String) miTablaLiquida.getValueAt(j, 2);             // cups gas
               col4 = (String) miTablaLiquida.getValueAt(j, 3);             // cups elect
               col5 = (String) miTablaLiquida.getValueAt(j, 4);             // Titular
               col6 = (String) miTablaLiquida.getValueAt(j, 5);             // cifnif
               
               col7 = String.valueOf(miTablaLiquida.getValueAt(j, 6)) ;     //p1
               col8 = String.valueOf(miTablaLiquida.getValueAt(j, 7)) ;     //p2
               col9 = String.valueOf(miTablaLiquida.getValueAt(j, 8)) ;     //p3
               col10 = String.valueOf(miTablaLiquida.getValueAt(j, 9)) ;    //p4
               col11 = String.valueOf(miTablaLiquida.getValueAt(j, 10)) ;   //p5
               col12 = String.valueOf(miTablaLiquida.getValueAt(j, 11)) ;   //p6
               col13 = String.valueOf(miTablaLiquida.getValueAt(j, 12)) ;   //p7
               col14 = String.valueOf(miTablaLiquida.getValueAt(j, 13)) ;   //p8
               col17 = String.valueOf(miTablaLiquida.getValueAt(j, 14)) ;   //p9
               col15 = (String) miTablaLiquida.getValueAt(j, 15);             // observaciones
               
               col16 = String.valueOf(miTablaLiquida.getValueAt(j, 15)) ;     //nº total productos
               
               // ....................................................................................
               
               celda = fila.createCell(i);  celda.setCellValue(col1);      i++;        // 
               
               celda = fila.createCell(i);    
               if (col2.equals("1") )  celda.setCellValue("X"); else 
                                                             celda.setCellValue("");              i++;        // Tur Gas
               
               
               celda = fila.createCell(i);  celda.setCellValue(col3);      i++;        // 
               celda = fila.createCell(i);  celda.setCellValue(col4);      i++;        // 
               celda = fila.createCell(i);  celda.setCellValue(col5);      i++;        // 
               celda = fila.createCell(i);  celda.setCellValue(col6);      i++;        //

               celda = fila.createCell(i);  
               if (col7.equals("1") )  celda.setCellValue("X"); else 
                                                             celda.setCellValue("");              i++;        // SVGComplet

               celda = fila.createCell(i);  
               if (col8.equals("1") )  celda.setCellValue("X"); else 
                                                             celda.setCellValue("");              i++;        // SVGXpres

               celda = fila.createCell(i);  
               if (col9.equals("1") )  celda.setCellValue("X"); else 
                                                             celda.setCellValue("");              i++;        // SVG Básico

              celda = fila.createCell(i);  
              if (col10.equals("1") )   celda.setCellValue("X"); else 
                                                             celda.setCellValue("");              i++;        //SVELECTRIC XPRES

              celda = fila.createCell(i);  
              if (col11.equals("1") )  celda.setCellValue("X"); else 
                                                            celda.setCellValue("");               i++;         //SVGHOGAR
                                                            
              celda = fila.createCell(i);  
              if (col12.equals("1") )  celda.setCellValue("X"); else 
                                                            celda.setCellValue("");               i++;         //
              celda = fila.createCell(i);  
              if (col13.equals("1") )  celda.setCellValue("X"); else 
                                                            celda.setCellValue("");               i++;         //    
               celda = fila.createCell(i);  
              if (col14.equals("1") )  celda.setCellValue("X"); else 
                                                            celda.setCellValue("");               i++;         //    SPP
                                                              celda = fila.createCell(i);  
              if (col17.equals("1") )  celda.setCellValue("X"); else 
                                                            celda.setCellValue("");               i++;         //    TARIFA PLANA
                    
                                                            
              celda = fila.createCell(i);  celda.setCellValue(col15);      i++;             // observaciones
              celda = fila.createCell(i);  celda.setCellValue(col16);      i++;             // n total productos 

            }


            try
            {
                String nombre="";
                JFileChooser file=new JFileChooser();
                file.showSaveDialog(this);
                File guarda =file.getSelectedFile();

                if(guarda !=null)
                {
                     nombre=file.getSelectedFile().getName();
                    //guardamos el archivo y le damos el formato directamente,
                    // si queremos que se guarde en formato doc lo definimos como .doc

                     FileOutputStream elFichero = new FileOutputStream(guarda+".xls");
                     libro.write(elFichero);
                     elFichero.close();


                     JOptionPane.showMessageDialog(null,
                     "El archivo LIQUIDACIONES se a guardado Exitosamente",
                     "Información",JOptionPane.INFORMATION_MESSAGE);
                }
             }
             catch(IOException ex)
             {
                      JOptionPane.showMessageDialog(null,
                      "Su archivo DEVUELTOS no se ha guardado",
                      "Advertencia",JOptionPane.WARNING_MESSAGE);
             }

            // Se salva el libro.
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
      }
         
           
       }
    // -------------------------------------------------------------------------------------------------------------
    public void CuentaTitularesRepetidos() {
         int cnt=0;
         int i,j,k ;
         String sNIF,sNIFr;
    //     System.out.println("---------- INICIO BUSQUEDA DE TITULARES REPETIDOS ------------");
         for (i=0; i< this.nRegistros; i++){
             
             sNIF = this.tablaDatos[i][10] ; sNIF = sNIF.trim();
             k=0; 
             cnt = 0;
             while (k<this.nRegistros){
                 
                 sNIFr = this.tablaDatos[k][10] ; sNIFr.trim();
                 
                 if (sNIF.equals(sNIFr)) cnt++;
                 k++ ;
                 
             }
            //  if (cnt >1) System.out.println(i+" -> HE ENCONTRADO UN TITULAR REPETIDO");
             
             this.tablaDatos[i][47] = String.valueOf(cnt);
             
         }
       
    }
    
    // -------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------
    
    public void limpiaTablaLocuciones(DefaultTableModel modelLoc) {
        
                modelLoc.addColumn("ID");                
		modelLoc.addColumn("idt");
                modelLoc.addColumn("Fecha"); 
                modelLoc.addColumn("Titular");
                modelLoc.addColumn("Verifica");
                modelLoc.addColumn("Direccion");
                modelLoc.addColumn("Contrato");
                modelLoc.addColumn("Llam1");
                modelLoc.addColumn("Llam2");
                modelLoc.addColumn("Llam3");
                modelLoc.addColumn("Llam4");
                modelLoc.addColumn("Llam5");
                modelLoc.addColumn("Llam6");
                modelLoc.addColumn("Telefono");
                modelLoc.addColumn("FechaNac");
                modelLoc.addColumn("ApoderadoPyme");
                modelLoc.addColumn("Facturas");
                modelLoc.addColumn("TratoAcre");
                modelLoc.addColumn("Informado");
                modelLoc.addColumn("Cuenta");
                modelLoc.addColumn("Copia");
                modelLoc.addColumn("Observaciones");
                modelLoc.addColumn("comercial");
                modelLoc.addColumn("hora");
                modelLoc.addColumn("precios");
                
                
        
    }
    
    // -------------------------------------------------------------------------------------------------------------
    
    public void limpiaTablaCertificaciones(DefaultTableModel modelCer) {
        
               
                                 
		modelCer.addColumn("ID");   
                modelCer.addColumn("IDp");  
		modelCer.addColumn("Año");
		modelCer.addColumn("Mes");
                modelCer.addColumn("Periodo");
                modelCer.addColumn("CUPS_Gas");
                modelCer.addColumn("CUPS_Elect");
                modelCer.addColumn("Producto");
                modelCer.addColumn("Importe");		
                modelCer.addColumn("Fecha");
                modelCer.addColumn("Fecha_Recep.");
                modelCer.addColumn("Delegacion");
		modelCer.addColumn("Concepto");
                modelCer.addColumn("Contrato");
                modelCer.addColumn("NIF");
                modelCer.addColumn("Calle");
                modelCer.addColumn("NF");
                modelCer.addColumn("Escalera");
                modelCer.addColumn("Piso");
                modelCer.addColumn("Puerta");
                modelCer.addColumn("CodPost");
                modelCer.addColumn("Municipio");
                modelCer.addColumn("Provincia");
               
                  
           
                
                
        
    }
    // -------------------------------------------------------------------------------------------------------------
    
    
    public void cargarDatosCertificaciones(){
            DefaultTableModel modelCer;
          // .........................................................  
                    
                modelCer = new DefaultTableModel();            // definimos el objeto tableModel
               
                limpiaTablaCertificaciones(modelCer);
                miTablaCer = new JTable();                // creamos la instancia de la tabla
                miTablaCer.setModel(modelCer);
                  
                
                PymesDao miPymesDaoCer= new PymesDao();
                 
              //  miPymesDaoCer.compruebaLocucion(modelCer,this.plogin,this.ppassword,nif,telefono,direccion,titular);
                
                miBarraLocu.setViewportView(miTablaCer);
                // .........................................................
    }
    
    public void actualizarTablaComisiones(int idComercial) {
        int r=0;
         // .......................................................................
            
            PymesDao miTCom = new PymesDao();
            
         // .......................................................................
        
        r = miTCom.consultaTablaComisionesResidencial(this.plogin,this.ppassword,idComercial) ;
        
        jTextField43.setText(miTCom.tablaComisionesAgente[0][1]); jTextField44.setText(miTCom.tablaComisionesAgente[0][2]); 
        jTextField51.setText(miTCom.tablaComisionesAgente[1][1]); jTextField58.setText(miTCom.tablaComisionesAgente[1][2]); 
        jTextField52.setText(miTCom.tablaComisionesAgente[2][1]); jTextField59.setText(miTCom.tablaComisionesAgente[2][2]);     
        jTextField53.setText(miTCom.tablaComisionesAgente[3][1]); jTextField60.setText(miTCom.tablaComisionesAgente[3][2]);  
        jTextField54.setText(miTCom.tablaComisionesAgente[4][1]); jTextField61.setText(miTCom.tablaComisionesAgente[4][2]); 
        jTextField55.setText(miTCom.tablaComisionesAgente[5][1]); jTextField62.setText(miTCom.tablaComisionesAgente[5][2]);  
        jTextField57.setText(miTCom.tablaComisionesAgente[6][1]); jTextField63.setText(miTCom.tablaComisionesAgente[6][2]);   
        jTextField107.setText(miTCom.tablaComisionesAgente[7][1]); jTextField109.setText(miTCom.tablaComisionesAgente[7][2]);   
        jTextField112.setText(miTCom.tablaComisionesAgente[8][1]); jTextField113.setText(miTCom.tablaComisionesAgente[8][2]);   
        jTextField128.setText(miTCom.tablaComisionesAgente[9][1]); jTextField127.setText(miTCom.tablaComisionesAgente[9][2]);   
        jTextField132.setText(miTCom.tablaComisionesAgente[10][1]); jTextField131.setText(miTCom.tablaComisionesAgente[10][2]);   
        jTextField136.setText(miTCom.tablaComisionesAgente[11][1]); jTextField135.setText(miTCom.tablaComisionesAgente[11][2]);   
        
        
        System.out.println("ACTUALIZO TABLA PARA idComercial ="+idComercial+" miTCom.tablaComisionesAgente[0][2]="+miTCom.tablaComisionesAgente[0][2]);
        
    }
     public void actualizarTablaIngresos() {
        int r=0;
         // .......................................................................
            
            PymesDao miTIng = new PymesDao();
            
         // .......................................................................
        
        r = miTIng.consultaTablaIngresosResidencial(this.plogin,this.ppassword) ;
        
        jTextField139.setText(miTIng.tablaComisionesAgente[0][1]); jTextField140.setText(miTIng.tablaComisionesAgente[0][2]); 
        jTextField156.setText(miTIng.tablaComisionesAgente[1][1]); jTextField171.setText(miTIng.tablaComisionesAgente[1][2]); 
        jTextField157.setText(miTIng.tablaComisionesAgente[2][1]); jTextField172.setText(miTIng.tablaComisionesAgente[2][2]);     
        jTextField158.setText(miTIng.tablaComisionesAgente[3][1]); jTextField173.setText(miTIng.tablaComisionesAgente[3][2]);  
        jTextField159.setText(miTIng.tablaComisionesAgente[4][1]); jTextField174.setText(miTIng.tablaComisionesAgente[4][2]); 
        jTextField160.setText(miTIng.tablaComisionesAgente[5][1]); jTextField175.setText(miTIng.tablaComisionesAgente[5][2]);  
        jTextField161.setText(miTIng.tablaComisionesAgente[6][1]); jTextField176.setText(miTIng.tablaComisionesAgente[6][2]);   
        jTextField162.setText(miTIng.tablaComisionesAgente[7][1]); jTextField177.setText(miTIng.tablaComisionesAgente[7][2]);   
        jTextField163.setText(miTIng.tablaComisionesAgente[8][1]); jTextField178.setText(miTIng.tablaComisionesAgente[8][2]);   
        jTextField159.setText(miTIng.tablaComisionesAgente[9][1]); jTextField150.setText(miTIng.tablaComisionesAgente[9][2]);   
        jTextField155.setText(miTIng.tablaComisionesAgente[10][1]); jTextField154.setText(miTIng.tablaComisionesAgente[10][2]);   
        jTextField167.setText(miTIng.tablaComisionesAgente[11][1]); jTextField166.setText(miTIng.tablaComisionesAgente[11][2]);   
        
    }
     // -------------------------------------------------------------------------------------------------
       public void actualizarCalculosCertificaciones() {
           
                int p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,ptot,nReg;
                int pn1,pn2,pn3,pn4,pn5,pn6,pn7,pn8,pn9,pn10,pntot;
                double v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,vtot,val,v11,v12,v13;
                double vn1,vn2,vn3,vn4,vn5,vn6,vn7,vn8,vn9,vn10,vntot;
                
           
                String dd = jTextField104.getText() ;
                String mm = jTextField105.getText() ;
                String aa = jTextField106.getText() ;
                
                String dd2 = jTextField1.getText() ;
                String mm2 = jTextField3.getText() ;
                String aa2 = jTextField6.getText() ;
                
                String fechaSelDesde = aa+"-"+mm+"-"+dd+" 00:00:00" ;
                String fechaSelHasta = aa2+"-"+mm2+"-"+dd2+" 00:00:00" ;
                
                System.out.println("----COMIENZO EL FORMATEO TABLA CERTIFICACIONES --- Fecha = "+fechaSelDesde);
                
                
                DefaultTableModel modelCer;
		modelCer = new DefaultTableModel();        // definimos el objeto tableModel
               
		miTablaCertifica = new JTable();                // creamos la instancia de la tabla
		miTablaCertifica.setModel(modelCer);
                
                modelCer.addColumn("idColor");                        
		modelCer.addColumn("TUR");                
		modelCer.addColumn("CUPS GAS");
		modelCer.addColumn("CUPS ELECTRICIDAD");
		modelCer.addColumn("TITULAR");
                modelCer.addColumn("CIF/NIF");
                modelCer.addColumn("SVG C");
                modelCer.addColumn("SVG X");
		modelCer.addColumn("SVG B");
                modelCer.addColumn("SVE");
                modelCer.addColumn("SVH");
                modelCer.addColumn("SVGCC");
                modelCer.addColumn("SVGSC");
                modelCer.addColumn("SPP");
                modelCer.addColumn("T.PLANA");
                modelCer.addColumn("OBSERVACIONES");
                modelCer.addColumn("Nº DE PRODUCTOS");
               
                
		miTablaCertifica.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		miTablaCertifica.getTableHeader().setReorderingAllowed(false);
                
                TableColumn columna1 = miTablaCertifica.getColumn("CUPS GAS");
                TableColumn columna2 = miTablaCertifica.getColumn("CUPS ELECTRICIDAD");
                TableColumn columna3 = miTablaCertifica.getColumn("TITULAR");
                TableColumn columna4 = miTablaCertifica.getColumn("OBSERVACIONES");
               
                columna1.setMinWidth(230);
                columna2.setMinWidth(230);
                columna3.setMinWidth(150);
                columna4.setMinWidth(250);

		PymesDao miPymesDao3 = new PymesDao();
		 
                
		nReg=miPymesDao3.tablaCertificaciones(modelCer,this.plogin,this.ppassword,fechaSelDesde,fechaSelHasta);
                this.nCertificaciones = nReg;
                
                // ................................................................................................................................
                
                p1 = miPymesDao3.contarProductosCertificaciones(this.plogin,this.ppassword,fechaSelDesde,fechaSelHasta,0);
                p2 = miPymesDao3.contarProductosCertificaciones(this.plogin,this.ppassword,fechaSelDesde,fechaSelHasta, 1);
                p3 = miPymesDao3.contarProductosCertificaciones(this.plogin,this.ppassword,fechaSelDesde,fechaSelHasta, 2);
                p4 = miPymesDao3.contarProductosCertificaciones(this.plogin,this.ppassword,fechaSelDesde,fechaSelHasta, 3); 
                p5 = miPymesDao3.contarProductosCertificaciones(this.plogin,this.ppassword,fechaSelDesde,fechaSelHasta, 4);
                p6 = miPymesDao3.contarProductosCertificaciones(this.plogin,this.ppassword,fechaSelDesde,fechaSelHasta, 5);
                p7 = miPymesDao3.contarProductosCertificaciones(this.plogin,this.ppassword,fechaSelDesde,fechaSelHasta, 6); 
                p8 = miPymesDao3.contarProductosCertificaciones(this.plogin,this.ppassword,fechaSelDesde,fechaSelHasta, 7);
                
                p9 = miPymesDao3.contarProductosCertificaciones(this.plogin,this.ppassword,fechaSelDesde,fechaSelHasta, 8);
                p10 = miPymesDao3.contarProductosCertificaciones(this.plogin,this.ppassword,fechaSelDesde,fechaSelHasta, 9);
                
                p11 = miPymesDao3.contarProductosCertificaciones(this.plogin,this.ppassword,fechaSelDesde,fechaSelHasta, 10);
                p12 = miPymesDao3.contarProductosCertificaciones(this.plogin,this.ppassword,fechaSelDesde,fechaSelHasta, 11);
                p13 = miPymesDao3.contarProductosCertificaciones(this.plogin,this.ppassword,fechaSelDesde,fechaSelHasta, 12);
               
                // ................................................................................................................................
               
                jTextField138.setText(String.valueOf(p5));           // Servihogar
                jTextField141.setText(String.valueOf(p4));           // Servielectric
                jTextField142.setText(String.valueOf(p1));           // Servigas complet
                jTextField143.setText(String.valueOf(p2+p3));        // Servigas basic + express
                jTextField144.setText(String.valueOf(p6));           // Servigas con calefaccion
                jTextField145.setText(String.valueOf(p7));           // Servigas sin calefaccion
                jTextField146.setText(String.valueOf(p8));           // TUR
                jTextField147.setText(String.valueOf(p9-p8-p11));    // SWITCHING GAS
                jTextField148.setText(String.valueOf(p10-p12));      // SWITCHING ELECTRICO
                jTextField152.setText(String.valueOf(p11));          // SWITCHING GAS CON TARIFA PLANA
                jTextField164.setText(String.valueOf(p12));          // SWITCHING ELECTRICO CON TARIFA PLANA
                jTextField168.setText(String.valueOf(p13));          // SPP PLAN DE PROTECCION DE PAGOS
                     
                // ................................................................................................................................
                
                val = Double.parseDouble(jTextField138.getText()) ;
                
                v1 = p5 * Double.parseDouble(jTextField140.getText());      jTextField186.setText(String.valueOf(v1)); System.out.println("----------------------- V1="+v1);
                v2 = p4 * Double.parseDouble(jTextField171.getText());      jTextField187.setText(String.valueOf(v2));
                v3 = p1 * Double.parseDouble(jTextField172.getText());      jTextField188.setText(String.valueOf(v3));
                v4 = (p2+p3) * Double.parseDouble(jTextField173.getText()); jTextField189.setText(String.valueOf(v4));
                v5 = p6 * Double.parseDouble(jTextField174.getText());      jTextField190.setText(String.valueOf(v5));
                v6 = p7 * Double.parseDouble(jTextField175.getText());      jTextField191.setText(String.valueOf(v6));
                v7 = p8 * Double.parseDouble(jTextField176.getText());      jTextField192.setText(String.valueOf(v7));
                v8 = (p9-p8-p11) * Double.parseDouble(jTextField177.getText()); jTextField193.setText(String.valueOf(v8));
                v9 = (p10-p12) * Double.parseDouble(jTextField178.getText());     jTextField194.setText(String.valueOf(v9));
                
                v11 = p11 * Double.parseDouble(jTextField150.getText());    jTextField149.setText(String.valueOf(v11));
                v12 = p12 * Double.parseDouble(jTextField154.getText());    jTextField153.setText(String.valueOf(v12));
                v13 = p13 * Double.parseDouble(jTextField166.getText());    jTextField165.setText(String.valueOf(v13)); 
                  
                // ................................................................................................................................
                 /*    
                pn1 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,0);
                pn2 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,1);
                pn3 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,2);
                pn4 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,3); 
                pn5 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,4);
                pn6 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,5);
                pn7 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,6); 
                pn8 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,7);
                
                pn9 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,8);
                pn10= miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,9);
               
                 // ................................................................................................................................
               
                jTextField76.setText(String.valueOf(pn5));           // Servihogar
                jTextField77.setText(String.valueOf(pn4));           // Servielectric
                jTextField78.setText(String.valueOf(pn1));           // Servigas complet
                jTextField80.setText(String.valueOf(pn2+pn3));        // Servigas basic + express
                jTextField81.setText(String.valueOf(pn6));           // Servigas con calefaccion
                jTextField82.setText(String.valueOf(pn7));           // Servigas sin calefaccion
                
                jTextField83.setText(String.valueOf(pn8));           // TUR
                
                jTextField115.setText(String.valueOf(pn9-pn8));        // SWITCHING GAS
                jTextField119.setText(String.valueOf(pn10));          // SWITCHING ELECTRICO
                
                // ................................................................................................................................
                
                vn1 = pn5 * Double.parseDouble(jTextField84.getText());  jTextField85.setText(String.valueOf(vn1));
                vn2 = pn4 * Double.parseDouble(jTextField87.getText());  jTextField88.setText(String.valueOf(vn2));
                vn3 = pn1 * Double.parseDouble(jTextField90.getText());  jTextField91.setText(String.valueOf(vn3));
                vn4 = (pn2+pn3) * Double.parseDouble(jTextField96.getText());  jTextField100.setText(String.valueOf(vn4));
                vn5 = pn6 * Double.parseDouble(jTextField97.getText());  jTextField101.setText(String.valueOf(vn5));
                vn6 = pn7 * Double.parseDouble(jTextField99.getText());  jTextField102.setText(String.valueOf(vn6));
                vn7 = pn8 * Double.parseDouble(jTextField98.getText());  jTextField103.setText(String.valueOf(vn7));
                vn8 = (pn9-pn8) * Double.parseDouble(jTextField117.getText());  jTextField118.setText(String.valueOf(vn8));
                vn9 = pn10 * Double.parseDouble(jTextField121.getText());  jTextField122.setText(String.valueOf(vn9));
                */
                // ................................................................................................................................
                
                ptot = p1+p2+p3+p4+p5+p6+p7+p8+p9+p10+p13  ;  
                
                jTextField170.setText(String.valueOf(ptot));
                
                vtot = v1+v2+v3+v4+v5+v6+v7+v8+v9+v11+v12+v13 ; 
                
                jTextField169.setText(String.valueOf(vtot));
                jTextField8.setText(String.valueOf(vtot));
              
                // ................................................................................................................................
                
                 //Nueva instancia de la clase que contiene el formato
                FormatoTablaLiquida formato = new FormatoTablaLiquida();
                
                formato.fuente = this.fuente ;
                
                        
                //Se obtiene la tabla y se establece el formato para cada tipo de dato
                
                miTablaCertifica.setDefaultRenderer(Double.class, formato); 
                miTablaCertifica.setDefaultRenderer(String.class, formato); 
                miTablaCertifica.setDefaultRenderer(Integer.class, formato);
                miTablaCertifica.setDefaultRenderer(Object.class, formato);
                
                         
		miBarra02.setViewportView(miTablaCertifica);
                
        
                
                
           
           
       } 
}

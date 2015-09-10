/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.myorg.pymesAdministracion;

import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

import conexion.Conexion;
import dao.PymesDao;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import ventana.FormatoTabla;
import ventana.FormatoTablaLiquida;
import vo.PymesVo;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.myorg.pymesAdministracion//pymesAdministracion//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "pymesAdministracionTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "org.myorg.pymesAdministracion.pymesAdministracionTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_pymesAdministracionAction",
        preferredID = "pymesAdministracionTopComponent"
)
@Messages({
    "CTL_pymesAdministracionAction=Gestión de Pymes",
    "CTL_pymesAdministracionTopComponent=GESTION PYMES",
    "HINT_pymesAdministracionTopComponent=Ventana de gestión de contratos PYMES"
})
public final class pymesAdministracionTopComponent extends TopComponent {
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
    
    private static pymesAdministracionTopComponent instance;
    private static final String PREFERRED_ID = "pymesAdministracionTopComponent"; 
    
    // ..........................................................
    public pymesAdministracionTopComponent() {
        initComponents();
        setName(Bundle.CTL_pymesAdministracionTopComponent());
        setToolTipText(Bundle.HINT_pymesAdministracionTopComponent());

    }
    public static synchronized pymesAdministracionTopComponent getDefault() {
        if (instance == null) {
            instance = new pymesAdministracionTopComponent();
        }
        return instance;
    }

  
    public static synchronized pymesAdministracionTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(pymesAdministracionTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof pymesAdministracionTopComponent) {
            return (pymesAdministracionTopComponent) win;
        }
        Logger.getLogger(pymesAdministracionTopComponent.class.getName()).warning(
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
        jCheckBox32 = new javax.swing.JCheckBox();
        botonBuscarID = new javax.swing.JButton();
        jTextField75 = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
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
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
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
        jLabel35 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
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
        jPanel6 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jTextField29 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jTextField30 = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
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
        jPanel10 = new javax.swing.JPanel();
        miBarra02 = new javax.swing.JScrollPane();
        miTabla02 = new javax.swing.JTable();
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

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel7.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel8.text")); // NOI18N

        botonModificar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(botonModificar, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.botonModificar.text")); // NOI18N
        botonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel38, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel38.text")); // NOI18N

        jTextField27.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField27.text")); // NOI18N

        jLabel41.setForeground(new java.awt.Color(204, 102, 0));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel41, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel41.text")); // NOI18N

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BLANCO", "AMARILLO", "NARANJA", "ROJO", "VERDE", "MORADO", "AZUL" }));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PENDIENTE", "CERTIFICADO", "RESID", "KO", "RESID CERTIFIC", "VALIDADO", "ERROR WEBSALES" }));

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox28, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox28.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox29, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox29.text")); // NOI18N
        jCheckBox29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox29ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox30, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox30.text")); // NOI18N
        jCheckBox30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox30ActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel47, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel47.text")); // NOI18N

        jTextField21.setEditable(false);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel48, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel48.text")); // NOI18N

        jTextField24.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField24.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox32, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox32.text")); // NOI18N

        botonBuscarID.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/myorg/pymesAdministracion/IconoBusquedaPequeño_16x16.png"))); // NOI18N
        botonBuscarID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarIDActionPerformed(evt);
            }
        });

        jLabel107.setForeground(new java.awt.Color(204, 204, 204));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel107, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel107.text")); // NOI18N

        jPanel15.setBackground(new java.awt.Color(204, 204, 204));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel42, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel42.text")); // NOI18N

        jTextField1.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel44, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel44.text")); // NOI18N

        jTextField17.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField17.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel43, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel43.text")); // NOI18N

        jTextField9.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField9.text")); // NOI18N

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField17, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(jTextField9))))
                .addGap(29, 29, 29))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(125, 125, 125))
        );

        jPanel22.setBackground(new java.awt.Color(204, 204, 204));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel10.text")); // NOI18N

        jTextField4.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField4.text")); // NOI18N

        botonBuscarCUPSe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/myorg/pymesAdministracion/IconoBusquedaPequeño_16x16.png"))); // NOI18N
        botonBuscarCUPSe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarCUPSeActionPerformed(evt);
            }
        });

        jTextField5.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField5.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel11, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel11.text")); // NOI18N

        botonBuscarCUPSg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/myorg/pymesAdministracion/IconoBusquedaPequeño_16x16.png"))); // NOI18N
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonBuscarCUPSg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
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

        org.openide.awt.Mnemonics.setLocalizedText(jLabel17, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel17.text")); // NOI18N

        jTextField12.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField12.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel27, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel27.text")); // NOI18N

        jTextField22.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField22.text")); // NOI18N

        botonBuscarTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/myorg/pymesAdministracion/IconoBusquedaPequeño_16x16.png"))); // NOI18N
        botonBuscarTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarTelefonoActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel15, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel15.text")); // NOI18N

        jTextField10.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField10.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel39, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel39.text")); // NOI18N

        jTextField19.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField19.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel46, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel46.text")); // NOI18N

        jTextField20.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField20.text")); // NOI18N

        jTextField2.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel12.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel9.text")); // NOI18N

        jTextField18.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField18.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel24, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel24.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel25, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel25.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel26, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel26.text")); // NOI18N

        jTextField3.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel32, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel32.text")); // NOI18N

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel46)
                        .addComponent(jLabel39)
                        .addComponent(jLabel9)
                        .addComponent(jLabel27)
                        .addComponent(jLabel17)
                        .addComponent(jLabel26))
                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25))
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonBuscarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField12)
                    .addComponent(jTextField10)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel32)))))
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
                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel24)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addGap(4, 4, 4)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel21.setBackground(new java.awt.Color(204, 204, 255));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel28, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel28.text")); // NOI18N

        jTextField23.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField23.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel21, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel21.text")); // NOI18N

        jTextField16.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField16.text")); // NOI18N

        jLabel108.setForeground(new java.awt.Color(204, 204, 204));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel108, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel108.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel13, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel13.text")); // NOI18N

        jTextField7.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField7.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel20, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel20.text")); // NOI18N

        jTextField15.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField15.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel18, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel18.text")); // NOI18N

        jTextField13.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField13.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel16, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel16.text")); // NOI18N

        botonBuscarNIF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/myorg/pymesAdministracion/IconoBusquedaPequeño_16x16.png"))); // NOI18N
        botonBuscarNIF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarNIFActionPerformed(evt);
            }
        });

        jTextField11.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField11.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(actuaMunicipio, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.actuaMunicipio.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel138, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel138.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel19, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel19.text")); // NOI18N

        jTextField14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField14.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField14.text")); // NOI18N

        botonBuscarTitular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/myorg/pymesAdministracion/IconoBusquedaPequeño_16x16.png"))); // NOI18N
        botonBuscarTitular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarTitularActionPerformed(evt);
            }
        });

        jTextField123.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField123.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField123.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField123.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel130, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel130.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel35, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel35.text")); // NOI18N

        jTextField8.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField8.text")); // NOI18N

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
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20)
                                        .addComponent(botonBuscarNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel130))
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField123, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61))))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(botonBuscarTitular))
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel35))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(jLabel18))
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
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(miBarraLocu, javax.swing.GroupLayout.PREFERRED_SIZE, 1272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(miBarraLocu, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jPanel26.TabConstraints.tabTitle"), jPanel26); // NOI18N

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
            .addComponent(miBarraCer, javax.swing.GroupLayout.DEFAULT_SIZE, 1283, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(miBarraCer, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jPanel17.TabConstraints.tabTitle"), jPanel17); // NOI18N

        jPanel20.setBackground(new java.awt.Color(204, 204, 204));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel29, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel29.text")); // NOI18N

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane4.setViewportView(jTextArea3);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel22, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel22.text")); // NOI18N

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel23, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel23.text")); // NOI18N

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
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

        org.openide.awt.Mnemonics.setLocalizedText(jLabel33, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel33.text")); // NOI18N

        jTextField6.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField6.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel34, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel34.text")); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel34)
                .addGap(34, 34, 34))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(153, 204, 255));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel45, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel45.text")); // NOI18N

        jTextField29.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField29.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel49, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel49.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel50, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel50.text")); // NOI18N

        jTextField30.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField30.text")); // NOI18N

        jTextField31.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField31.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel51, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel51.text")); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField30)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50))
                        .addGap(110, 110, 110)))
                .addGap(34, 34, 34)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addContainerGap())
        );

        jLabel52.setForeground(new java.awt.Color(204, 204, 204));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel52, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel52.text")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(314, 314, 314)
                                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(116, 116, 116))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1288, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel52))
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
                        .addComponent(jCheckBox30)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox32)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botonBuscarID)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel48)
                        .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel52))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(botonModificar))
                    .addComponent(jCheckBox32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                            .addComponent(jCheckBox30))
                        .addGap(1, 1, 1)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel41))
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jPanel3.TabConstraints.tabTitle"), jPanel3); // NOI18N

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

        org.openide.awt.Mnemonics.setLocalizedText(botonRefrescar, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.botonRefrescar.text")); // NOI18N
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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PENDIENTE", "CERTIFICADO", "RESID", "KOs", "RESID CERTIFIC", "TODOS", "PUNTEADOS", "NO PUNTEADOS", "VALIDADOS", "ERROR WEB SALES", " " }));
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

        ListaAgentes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TODOS AGENTES", "J & C", "ETP", "NADINE", "EMILIO-RAQUEL", "SERNOVEN", "MIGUEL", "SHEILA", "MARIO SORIA" }));
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
        org.openide.awt.Mnemonics.setLocalizedText(jLabel131, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel131.text")); // NOI18N

        jTextField124.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField124.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(reduceFuente, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.reduceFuente.text")); // NOI18N
        reduceFuente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reduceFuenteActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(aumentaFuente, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.aumentaFuente.text")); // NOI18N
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

        org.openide.awt.Mnemonics.setLocalizedText(generaExelMakro, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.generaExelMakro.text")); // NOI18N
        generaExelMakro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generaExelMakroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonRefrescar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ListaOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ListaTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField124, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel131)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                    .addComponent(generaExelMakro))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(miBarra01, javax.swing.GroupLayout.DEFAULT_SIZE, 1642, Short.MAX_VALUE)
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

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jPanel5.TabConstraints.tabTitle"), jPanel5); // NOI18N

        jTextField40.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField40.text")); // NOI18N

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel60, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel60.text")); // NOI18N

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel61, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel61.text")); // NOI18N

        ListaComercialLiq.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "J & C Asesores", "ETP", "NADINE", "EMILIO-RAQUEL", "SERNOVEN", "MIGUEL", "SHEILA", "MARIO SORIA", "TODOS" }));
        ListaComercialLiq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListaComercialLiqActionPerformed(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel62, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel62.text")); // NOI18N

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel69, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel69.text")); // NOI18N

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

        org.openide.awt.Mnemonics.setLocalizedText(botonGeneraExelLiquida, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.botonGeneraExelLiquida.text")); // NOI18N
        botonGeneraExelLiquida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGeneraExelLiquidaActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(botonActualizarCalculos, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.botonActualizarCalculos.text")); // NOI18N
        botonActualizarCalculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarCalculosActionPerformed(evt);
            }
        });

        jTextField72.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField72.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel71, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel71.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel72, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel72.text")); // NOI18N

        jLabel73.setForeground(new java.awt.Color(153, 153, 153));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel73, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel73.text")); // NOI18N

        jLabel74.setForeground(new java.awt.Color(153, 153, 153));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel74, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel74.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel75, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel75.text")); // NOI18N

        jTextField73.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField73.text")); // NOI18N

        jLabel76.setForeground(new java.awt.Color(153, 153, 153));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel76, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel76.text")); // NOI18N

        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel77, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel77.text")); // NOI18N

        ListaZonaLiq.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir...", "CASTELLON", "VALENCIA", "OTRAS", "TODAS" }));
        ListaZonaLiq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListaZonaLiqActionPerformed(evt);
            }
        });

        jPanel14.setBackground(new java.awt.Color(204, 204, 204));

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel63, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel63.text")); // NOI18N

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel64, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel64.text")); // NOI18N

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel65, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel65.text")); // NOI18N

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel67, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel67.text")); // NOI18N

        jTextField42.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField42.text")); // NOI18N
        jTextField42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField42ActionPerformed(evt);
            }
        });

        jTextField43.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField43.text")); // NOI18N

        jTextField44.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField44.text")); // NOI18N
        jTextField44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField44ActionPerformed(evt);
            }
        });

        jTextField46.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField46.text")); // NOI18N

        jTextField47.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField47.text")); // NOI18N

        jTextField48.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField48.text")); // NOI18N

        jTextField49.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField49.text")); // NOI18N

        jTextField50.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField50.text")); // NOI18N
        jTextField50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField50ActionPerformed(evt);
            }
        });

        jTextField56.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField56.text")); // NOI18N

        jTextField108.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField108.text")); // NOI18N

        jTextField111.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField111.text")); // NOI18N

        jTextField76.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField76.text")); // NOI18N

        jTextField77.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField77.text")); // NOI18N

        jTextField78.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField78.text")); // NOI18N

        jTextField80.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField80.text")); // NOI18N

        jTextField81.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField81.text")); // NOI18N

        jTextField82.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField82.text")); // NOI18N

        jTextField83.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField83.text")); // NOI18N

        jTextField51.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField51.text")); // NOI18N

        jTextField52.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField52.text")); // NOI18N

        jTextField53.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField53.text")); // NOI18N

        jTextField54.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField54.text")); // NOI18N
        jTextField54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField54ActionPerformed(evt);
            }
        });

        jTextField55.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField55.text")); // NOI18N

        jTextField57.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField57.text")); // NOI18N

        jTextField107.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField107.text")); // NOI18N
        jTextField107.setPreferredSize(new java.awt.Dimension(146, 20));

        jTextField112.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField112.text")); // NOI18N
        jTextField112.setMaximumSize(new java.awt.Dimension(146, 20));
        jTextField112.setMinimumSize(new java.awt.Dimension(146, 20));
        jTextField112.setPreferredSize(new java.awt.Dimension(146, 20));
        jTextField112.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField112ActionPerformed(evt);
            }
        });

        jTextField79.setForeground(new java.awt.Color(204, 0, 0));
        jTextField79.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField79.text")); // NOI18N

        jTextField86.setForeground(new java.awt.Color(204, 0, 0));
        jTextField86.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField86.text")); // NOI18N

        jTextField89.setForeground(new java.awt.Color(204, 0, 0));
        jTextField89.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField89.text")); // NOI18N

        jTextField92.setForeground(new java.awt.Color(204, 0, 0));
        jTextField92.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField92.text")); // NOI18N

        jTextField93.setForeground(new java.awt.Color(204, 0, 0));
        jTextField93.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField93.text")); // NOI18N

        jTextField94.setForeground(new java.awt.Color(204, 0, 0));
        jTextField94.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField94.text")); // NOI18N

        jTextField95.setForeground(new java.awt.Color(204, 0, 0));
        jTextField95.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField95.text")); // NOI18N

        jTextField58.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField58.text")); // NOI18N

        jTextField59.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField59.text")); // NOI18N

        jTextField60.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField60.text")); // NOI18N

        jTextField61.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField61.text")); // NOI18N

        jTextField62.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField62.text")); // NOI18N

        jTextField63.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField63.text")); // NOI18N

        jTextField109.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField109.text")); // NOI18N

        jTextField113.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField113.text")); // NOI18N

        jTextField84.setForeground(new java.awt.Color(204, 0, 0));
        jTextField84.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField84.text")); // NOI18N

        jTextField87.setForeground(new java.awt.Color(204, 0, 0));
        jTextField87.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField87.text")); // NOI18N

        jTextField90.setForeground(new java.awt.Color(204, 0, 0));
        jTextField90.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField90.text")); // NOI18N

        jTextField96.setForeground(new java.awt.Color(204, 0, 0));
        jTextField96.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField96.text")); // NOI18N

        jTextField97.setForeground(new java.awt.Color(204, 0, 0));
        jTextField97.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField97.text")); // NOI18N

        jTextField99.setForeground(new java.awt.Color(204, 0, 0));
        jTextField99.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField99.text")); // NOI18N

        jTextField98.setForeground(new java.awt.Color(204, 0, 0));
        jTextField98.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField98.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel66, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel66.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel80, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel80.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel83, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel83.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel85, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel85.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel87, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel87.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel89, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel89.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel91, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel91.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel122, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel122.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel124, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel124.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel94, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel94.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel96, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel96.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel102, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel102.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel103, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel103.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel104, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel104.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel105, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel105.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel106, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel106.text")); // NOI18N

        jTextField45.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField45.text")); // NOI18N

        jTextField64.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField64.text")); // NOI18N
        jTextField64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField64ActionPerformed(evt);
            }
        });

        jTextField65.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField65.text")); // NOI18N

        jTextField66.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField66.text")); // NOI18N

        jTextField67.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField67.text")); // NOI18N

        jTextField68.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField68.text")); // NOI18N

        jTextField69.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField69.text")); // NOI18N

        jTextField110.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField110.text")); // NOI18N

        jTextField114.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField114.text")); // NOI18N

        jTextField85.setForeground(new java.awt.Color(204, 0, 0));
        jTextField85.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField85.text")); // NOI18N

        jTextField88.setForeground(new java.awt.Color(204, 0, 0));
        jTextField88.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField88.text")); // NOI18N

        jTextField91.setForeground(new java.awt.Color(204, 0, 0));
        jTextField91.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField91.text")); // NOI18N

        jTextField100.setForeground(new java.awt.Color(204, 0, 0));
        jTextField100.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField100.text")); // NOI18N

        jTextField101.setForeground(new java.awt.Color(204, 0, 0));
        jTextField101.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField101.text")); // NOI18N

        jTextField102.setForeground(new java.awt.Color(204, 0, 0));
        jTextField102.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField102.text")); // NOI18N

        jTextField103.setForeground(new java.awt.Color(204, 0, 0));
        jTextField103.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField103.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel68, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel68.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel81, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel81.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel82, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel82.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel84, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel84.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel86, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel86.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel88, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel88.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel90, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel90.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel123, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel123.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel125, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel125.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel93, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel93.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel95, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel95.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel97, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel97.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel98, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel98.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel99, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel99.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel100, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel100.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel101, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel101.text")); // NOI18N

        jTextField115.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField115.text")); // NOI18N

        jTextField116.setForeground(new java.awt.Color(204, 0, 0));
        jTextField116.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField116.text")); // NOI18N

        jTextField117.setForeground(new java.awt.Color(204, 0, 0));
        jTextField117.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField117.text")); // NOI18N

        jTextField118.setForeground(new java.awt.Color(204, 0, 0));
        jTextField118.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField118.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel126, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel126.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel127, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel127.text")); // NOI18N

        jTextField119.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField119.text")); // NOI18N

        jTextField120.setForeground(new java.awt.Color(204, 0, 0));
        jTextField120.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField120.text")); // NOI18N

        jTextField121.setForeground(new java.awt.Color(204, 0, 0));
        jTextField121.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField121.text")); // NOI18N

        jTextField122.setForeground(new java.awt.Color(204, 0, 0));
        jTextField122.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField122.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel128, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel128.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel129, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel129.text")); // NOI18N

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

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jButton1.text")); // NOI18N
        jButton1.setEnabled(false);

        jPanel18.setBackground(new java.awt.Color(204, 204, 255));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel132, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel132.text")); // NOI18N

        jTextField126.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField126.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel133, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel133.text")); // NOI18N

        jTextField127.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField127.text")); // NOI18N

        jTextField128.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField128.text")); // NOI18N
        jTextField128.setMinimumSize(new java.awt.Dimension(236, 20));
        jTextField128.setPreferredSize(new java.awt.Dimension(123, 20));

        jTextField129.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField129.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel134, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel134.text")); // NOI18N

        jTextField130.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField130.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel135, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel135.text")); // NOI18N

        jTextField131.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField131.text")); // NOI18N

        jTextField132.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField132.text")); // NOI18N
        jTextField132.setMinimumSize(new java.awt.Dimension(236, 20));

        jTextField133.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField133.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel136, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel136.text")); // NOI18N

        jTextField134.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField134.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel137, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel137.text")); // NOI18N

        jTextField135.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField135.text")); // NOI18N

        jTextField136.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField136.text")); // NOI18N
        jTextField136.setMinimumSize(new java.awt.Dimension(236, 20));

        jTextField137.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField137.text")); // NOI18N

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

        org.openide.awt.Mnemonics.setLocalizedText(jLabel92, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel92.text")); // NOI18N

        jTextField71.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField71.text")); // NOI18N

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel70, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel70.text")); // NOI18N

        jTextField70.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField70.text")); // NOI18N

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

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(miBarra03)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel60)
                        .addGap(122, 122, 122)
                        .addComponent(jLabel61)
                        .addGap(18, 18, 18)
                        .addComponent(ListaComercialLiq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel77)
                        .addGap(18, 18, 18)
                        .addComponent(ListaZonaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                        .addComponent(jLabel62))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel69))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel71)
                                    .addComponent(jLabel72)
                                    .addComponent(jLabel75))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField72, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField73, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel74)
                                            .addComponent(jLabel76)))
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel73)))
                                .addGap(66, 66, 66)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(botonActualizarCalculos, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botonGeneraExelLiquida, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
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
                            .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel61)
                            .addComponent(ListaComercialLiq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel77)
                            .addComponent(ListaZonaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel62)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonActualizarCalculos)
                            .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonGeneraExelLiquida)
                            .addComponent(jButton1)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel71)
                            .addComponent(jLabel73))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel72)
                            .addComponent(jLabel74))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel75)
                            .addComponent(jTextField73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel76))))
                .addGap(27, 27, 27)
                .addComponent(jLabel69)
                .addGap(18, 18, 18)
                .addComponent(miBarra03, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jPanel11.TabConstraints.tabTitle"), jPanel11); // NOI18N

        miTabla02.setModel(new javax.swing.table.DefaultTableModel(
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
        miBarra02.setViewportView(miTabla02);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(miBarra02, javax.swing.GroupLayout.DEFAULT_SIZE, 1642, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(miBarra02, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jPanel10.TabConstraints.tabTitle"), jPanel10); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel1.text")); // NOI18N

        numLineas.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.numLineas.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel2.text")); // NOI18N

        nComienzo.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.nComienzo.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel36, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel36.text")); // NOI18N

        jCheckBox1.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox1.text")); // NOI18N

        jCheckBox2.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox2, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox2.text")); // NOI18N

        jCheckBox3.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox3, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox3.text")); // NOI18N
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jCheckBox4.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox4, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox4.text")); // NOI18N

        jCheckBox5.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox5, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox5.text")); // NOI18N
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });

        jCheckBox6.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox6, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox6.text")); // NOI18N

        jCheckBox7.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox7, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox7.text")); // NOI18N

        jCheckBox8.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox8, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox8.text")); // NOI18N

        jCheckBox9.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox9, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox9.text")); // NOI18N

        jCheckBox10.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox10, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox10.text")); // NOI18N

        jCheckBox11.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox11, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox11.text")); // NOI18N

        jCheckBox12.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox12, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox12.text")); // NOI18N

        jCheckBox13.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox13, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox13.text")); // NOI18N

        jCheckBox14.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox14, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox14.text")); // NOI18N

        jCheckBox15.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox15, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox15.text")); // NOI18N

        jCheckBox16.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox16, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jCheckBox16.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel4.text")); // NOI18N

        jTextField25.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField25.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel5.text")); // NOI18N

        jTextField26.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField26.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(botonGuardaConf, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.botonGuardaConf.text")); // NOI18N

        jTextField28.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField28.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(botonCalendario, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.botonCalendario.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(botonActualizaFecha, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.botonActualizaFecha.text")); // NOI18N
        botonActualizaFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizaFechaActionPerformed(evt);
            }
        });

        login.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.login.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel30, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel30.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel31, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel31.text")); // NOI18N

        passw.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.passw.text")); // NOI18N

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel14, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel14.text")); // NOI18N

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel40, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel40.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel78, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel78.text")); // NOI18N

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
                        .addContainerGap(1361, Short.MAX_VALUE))))
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
                .addContainerGap(378, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
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

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jPanel2.TabConstraints.tabTitle"), jPanel2); // NOI18N

        areaDeTexto.setColumns(20);
        areaDeTexto.setRows(5);
        scrollPaneArea.setViewportView(areaDeTexto);

        areaDeTextoProcesado.setColumns(20);
        areaDeTextoProcesado.setRows(5);
        scrollPaneAreaProceso.setViewportView(areaDeTextoProcesado);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel6.text")); // NOI18N

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
                        .addComponent(scrollPaneAreaProceso, javax.swing.GroupLayout.DEFAULT_SIZE, 1322, Short.MAX_VALUE)
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

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

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
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void botonBuscarIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarIDActionPerformed
        buscarID();
    }//GEN-LAST:event_botonBuscarIDActionPerformed

    private void botonBuscarCUPSeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarCUPSeActionPerformed
        buscarCUPSe();
    }//GEN-LAST:event_botonBuscarCUPSeActionPerformed

    private void botonBuscarCUPSgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarCUPSgActionPerformed
        buscarCUPSg();
    }//GEN-LAST:event_botonBuscarCUPSgActionPerformed

    private void botonBuscarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarTelefonoActionPerformed
        buscaTelefono();
    }//GEN-LAST:event_botonBuscarTelefonoActionPerformed

    private void botonBuscarNIFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarNIFActionPerformed
       buscaNIF();
    }//GEN-LAST:event_botonBuscarNIFActionPerformed

    private void botonBuscarTitularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarTitularActionPerformed
       buscaTitular();
    }//GEN-LAST:event_botonBuscarTitularActionPerformed

    private void miTabla01MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miTabla01MouseClicked

    }//GEN-LAST:event_miTabla01MouseClicked

    private void miTabla01MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miTabla01MousePressed

    }//GEN-LAST:event_miTabla01MousePressed

    private void botonRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRefrescarActionPerformed
       
        RefrescarTablaBD();
        modificarArbolNuevos();
        CuentaTitularesRepetidos();
                
    }//GEN-LAST:event_botonRefrescarActionPerformed

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

        if (str.equals("PENDIENTE"))   this.filtroEstadoSel = 0 ;
        if (str.equals("RESID"))       this.filtroEstadoSel = 1 ;
        if (str.equals("CERTIFICADO")) this.filtroEstadoSel = 2 ;
        if (str.equals("TODOS"))        this.filtroEstadoSel = 3 ;
        if (str.equals("KOs"))          this.filtroEstadoSel = 4 ;
        if (str.equals("PUNTEADOS"))    this.filtroEstadoSel = 5 ;
        if (str.equals("NO PUNTEADOS")) this.filtroEstadoSel = 6 ;
        if (str.equals("VALIDADOS"))    this.filtroEstadoSel = 7 ;
        if (str.equals("ERROR WEB SALES")) this.filtroEstadoSel = 8 ;
        
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

    private void reduceFuenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reduceFuenteActionPerformed
      reduceFuenteTabla();
    }//GEN-LAST:event_reduceFuenteActionPerformed

    private void aumentaFuenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aumentaFuenteActionPerformed
       aumentaFuenteTabla();
    }//GEN-LAST:event_aumentaFuenteActionPerformed

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

    private void generaExelMakroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generaExelMakroActionPerformed
        GuardarArchivoExelResultados();
    }//GEN-LAST:event_generaExelMakroActionPerformed

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
        
    }//GEN-LAST:event_ListaComercialLiqActionPerformed

    private void botonGeneraExelLiquidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGeneraExelLiquidaActionPerformed
        GenerarExelLiquida();
    }//GEN-LAST:event_botonGeneraExelLiquidaActionPerformed

    private void botonActualizarCalculosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarCalculosActionPerformed
        actualizarCalculosLiquidaciones();
    }//GEN-LAST:event_botonActualizarCalculosActionPerformed

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
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox15;
    private javax.swing.JCheckBox jCheckBox16;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox28;
    private javax.swing.JCheckBox jCheckBox29;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox30;
    private javax.swing.JCheckBox jCheckBox32;
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
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
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
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
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
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
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
    private javax.swing.JTextField jTextField31;
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
    private javax.swing.JTable miTabla02;
    private javax.swing.JTable miTablaCer;
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
    
    
     // .......................................................................
        public void conectarConBD(){

                     String Str1 = login.getText();
                     String Str2 = passw.getText();
                     conectarBD(Str1,Str2);    
        }
    // .......................................................................
        public void conectarBDBoton() {
      
        conectarConBD();  
        modificarArbolNuevos();
        CuentaTitularesRepetidos();
     
        }
      // .......................................................................
        private void conectarBD(String Str1,String Str2) {
    
        JOptionPane.showMessageDialog(null,
                "\nVoy a conectar con la Base de Datos LOGIN="+Str1,
                "AVISO",JOptionPane.WARNING_MESSAGE);
        
    
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
    // -----------------------------------------------------------------------------------------------------
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
          
           this.tablaDatos[indice][4] = jTextField4.getText();      // cups electrico
           this.tablaDatos[indice][3]= jTextField5.getText();       // cups gas
         
           this.tablaDatos[indice][17] = jTextField7.getText();     // agente
         
           this.tablaDatos[indice][5] = jTextField10.getText();     // codigo postal
           this.tablaDatos[indice][6] = jTextField11.getText();     // municipio
           this.tablaDatos[indice][7] = jTextField12.getText();     // provincia
           this.tablaDatos[indice][8] = jTextField13.getText();     // direccion
           this.tablaDatos[indice][9] = jTextField14.getText();     // titular
           this.tablaDatos[indice][42] = jTextField8.getText();     // Empresa origen
           this.tablaDatos[indice][10] = jTextField15.getText();    // nif/cif
           this.tablaDatos[indice][11] = jTextField16.getText();    // fecha firma cliente
           
           this.tablaDatos[indice][12] = jTextField18.getText();    // consumo kwha
           this.tablaDatos[indice][13] = jTextField3.getText();     // consumo kwha Web sale
           this.tablaDatos[indice][14] = jTextField2.getText();     // consumo kwha GAS
         
           this.tablaDatos[indice][16] =  jTextField22.getText();   // telefono
           
           this.tablaDatos[indice][15] = jTextArea1.getText();      // observaciones      
           this.tablaDatos[indice][20] = jTextArea2.getText();      // incidencia  
           this.tablaDatos[indice][21] = jTextArea3.getText();      // solucion      
           
           this.tablaDatos[indice][2] = jTextField27.getText();     // Fecha de producción      
           
           this.tablaDatos[indice][28] = jTextField19.getText();    // Tarifa Elec     
           this.tablaDatos[indice][29] = jTextField20.getText();    // Tarifa Gas 
           
           this.tablaDatos[indice][41] = jTextField74.getText();    // Agente comercial  
           this.tablaDatos[indice][43] = jTextField1.getText();    // C SERVICIOS  
           
          this.tablaDatos[indice][39] = jTextField24.getText();     // Fecha doc out  
          this.tablaDatos[indice][34] = jTextField9.getText();      // Oferta 
          this.tablaDatos[indice][35] = jTextField17.getText();      // Campaña 
          this.tablaDatos[indice][36] = jTextField23.getText();      // Persona de contacto 
          this.tablaDatos[indice][37] = jTextField29.getText();      // Pagado
          this.tablaDatos[indice][38] = jTextField30.getText();      // Pagado Fenosa texto
          this.tablaDatos[indice][45] = jTextField31.getText();      // Pagado Fenosa total numero
          this.tablaDatos[indice][46] = jTextField6.getText();      //  Reactiva
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
                
                this.tablaDatos[i][2] = jTextField27.getText();                                    // fecha orden
             
                Fecha = dateToMySQLDate(this.tablaDatos[i][2]);
                
                this.tablaDatos[i][11] = jTextField16.getText();                                   // fecha firma contrato.
                
                 
                PymesDao miPymeDao  = new PymesDao();
                PymesVo miPymes     = new PymesVo();
             
                        // ..............................................
                        
                        ind = jComboBox4.getSelectedIndex() ;
                        this.tablaDatos[i][0] = Integer.toString(ind);                              // estado
                        ind = jComboBox3.getSelectedIndex() ;
                        this.tablaDatos[i][1] = Integer.toString(ind);                              // incidencia
                        
                        this.tablaDatos[i][15] =jTextArea1.getText();                               // observaciones
                        this.tablaDatos[i][20] =jTextArea3.getText();                               // incidencia
                        this.tablaDatos[i][21] =jTextArea2.getText();                               // solucion
                        
                        // ..............................................
                                              
                        this.tablaDatos[i][33] = "0" ;
                        if (jCheckBox28.isSelected()) { G=1 ; this.tablaDatos[i][43] = "1";} else { G=0; }     //   Gas
                        if (jCheckBox29.isSelected()) { L=1 ; this.tablaDatos[i][43] = "2";} else { L=0 ; }    //   Luz
                        if (jCheckBox30.isSelected()) { D=1 ; this.tablaDatos[i][43] = "3";} else { D=0 ; }    //   Dual
                         
                        System.out.println(" TENEMOS UN CONTRATO CON L,G,D = ("+L+","+G+","+D+")") ;
                         
                        // ..............................................
                        
                        if (jCheckBox32.isSelected()) {this.tablaDatos[i][44] = "1";} else {this.tablaDatos[i][44] = "0" ; }     // Punteado
                    
                        // ...........................................................................................
                        
                        System.out.println("Insertando registro i= "+i+ " - Incidencia= "+this.tablaDatos[i][1]);
                                  
                        miPymes.setIdContrato(Integer.parseInt(this.tablaDatos[i][30]));  System.out.println("id contrato a modificar!! ="+this.tablaDatos[i][30]);
                        
                        miPymes.setEstado(Integer.parseInt(this.tablaDatos[i][0]));
                        miPymes.setIncidencia(Integer.parseInt(this.tablaDatos[i][1]));
                        
                        Fecha = dateToMySQLDate(this.tablaDatos[i][2]);                        
                        miPymes.setFechaOrden(Fecha);
                       
                        miPymes.setAgente(this.tablaDatos[i][17]);
                        
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
                        miPymes.setEmpresaOrigen(this.tablaDatos[i][42]);
                        miPymes.setNifCif(this.tablaDatos[i][10]);
                        miPymes.setTelefonoCli(this.tablaDatos[i][16]);  
                        
                       
                        Fecha = dateToMySQLDate(this.tablaDatos[i][11]);
                        miPymes.setFechaFirma(Fecha);
                        
                         try {
                            miPymes.setConsumoElect(Double.parseDouble(this.tablaDatos[i][12]));
                            miPymes.setConsumoElectWS(Double.parseDouble(this.tablaDatos[i][13]));
                            
                         } catch (NumberFormatException nfe){
                            miPymes.setConsumoElect(Double.parseDouble("0"));
                            miPymes.setConsumoElectWS(Double.parseDouble("0"));
                         }
                         
                         
                         try {
                           miPymes.setConsumoGas(Double.parseDouble(this.tablaDatos[i][14]));
                            
                         } catch (NumberFormatException nfe){
                            miPymes.setConsumoGas(Double.parseDouble("0"));
                         }
                       
                         miPymes.setTarifa(this.tablaDatos[i][28]);
                         miPymes.setTarifaGas(this.tablaDatos[i][29]);
                                                       
                        miPymes.setObservaciones(jTextArea1.getText()); 
                        miPymes.setsIncidencia(jTextArea3.getText());
                        miPymes.setsExplicacion(jTextArea2.getText());
                       
                        miPymes.setCservicios(this.tablaDatos[i][43]);
                        
                        miPymes.setPunteado(Integer.parseInt(this.tablaDatos[i][44]));
                        miPymes.setCVComercial(this.tablaDatos[i][41]);
                        
                        miPymes.setOferta(this.tablaDatos[i][34]);  
                        miPymes.setCampaña(this.tablaDatos[i][35]);  
                        miPymes.setPerContacto(this.tablaDatos[i][36]);  
                        
                        miPymes.setPagado(Double.parseDouble(this.tablaDatos[i][37]));  
                        miPymes.setPFenosa(this.tablaDatos[i][38]);  
                        miPymes.setPagadoFenosa(Double.parseDouble(this.tablaDatos[i][45]));       
                        
                        miPymes.setReactiva(Double.parseDouble(this.tablaDatos[i][46]));  
                        
                        str = this.tablaDatos[i][39].trim();
                        if (!str.equals("")) {
                             Fecha = dateToMySQLDate(str);
                             miPymes.setFechaDocout(Fecha);
                        } else {
                             miPymes.setFechaDocout("NULL"); System.out.println("------ VOY A BORRAR LA FECHA MEMO");
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
   
       private void actualizarFormulario(int indice) {
           int ind,ireg, creg, indLoc=-1;
           this.indGen = indice ;
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
            jTextField8.setText(" "); jTextField14.setBackground(Color.white);// empresa origen
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
            jTextField9.setText(" "); jTextField9.setBackground(Color.white);// 
            jTextField17.setText(" "); jTextField17.setBackground(Color.white);// CAMPAÑA
            jTextField29.setText(" "); jTextField29.setBackground(Color.white); // PAGADO
            
            jTextField1.setText(" "); jTextField75.setBackground(Color.white);// Servicios extra contratados
               
            jLabel41.setVisible(false);  // Sugerencia de Municipio
            
            jCheckBox28.setSelected(false);
            jCheckBox29.setSelected(false);
            jCheckBox30.setSelected(false);
            jCheckBox32.setSelected(false);
            
           } else {
           
           
           if (this.nRegistros >0 ) {
                
                System.out.println("Indice pasado ="+indice+" y el Indice de tablaLocuciones ="+this.tablaDatos[indice][32]);
                ireg = indice ;
                this.indGenReg = ireg ;
               
      //          indice = Integer.parseInt(this.tablaDatos[indice][30]) ;               // Indice del registro de base de datos
               
                creg = Integer.parseInt(this.tablaDatos[indice][40]) ;                  // Indice de tabla de certificaciones
                indLoc = Integer.parseInt(this.tablaDatos[indice][32]);                 // Indice de tabla de locuciones
                System.out.println("Indice de tabla certificaciones CREG ="+this.tablaDatos[indice][40]+" y indLoc = "+indLoc) ;
              
                
                if (indLoc == -1){
                   
                    jCheckBox32.setSelected(false);  
                   
                                  
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
                    jTextField8.setText(this.tablaDatos[ireg][42]); // empresa origen
                    jTextField15.setText(this.tablaDatos[ireg][10]); // nif/cif
                    jTextField16.setText(this.tablaDatos[ireg][11]); // fecha firma cliente             
                    jTextField18.setText(this.tablaDatos[ireg][12]); // consumo kwha electrico
                    jTextField3.setText(this.tablaDatos[ireg][13]); // consumo kwha electrico web sale
                    jTextField2.setText(this.tablaDatos[ireg][14]); // consumo kwha gas
                    jTextField9.setText(this.tablaDatos[ireg][34]); // Oferta
                    jTextField17.setText(this.tablaDatos[ireg][35]); // Campaña
                    jTextField23.setText(this.tablaDatos[ireg][36]); // Persona de Contacto
                    jTextField29.setText(this.tablaDatos[ireg][37]); // Pagado
                    jTextField6.setText(this.tablaDatos[ireg][46]);  // Reactiva
                    jTextField30.setText(this.tablaDatos[ireg][38]);  // Pagado fenosa
                    jTextField31.setText(this.tablaDatos[ireg][45]);  // Pagado total fenosa
                    
                    jTextField21.setText(this.tablaDatos[ireg][30]); // id_m_r      
                 
                  //  ind = Integer.parseInt(this.tablaDatos[ireg][14]) ; 
                  //  jComboBox2.setSelectedIndex(ind); 
                      
                    
                     if (this.tablaDatos[ireg][44].equals("1")) jCheckBox32.setSelected(true); else jCheckBox32.setSelected(false); // Punteado
              
                 
                    jTextArea1.setText(this.tablaDatos[ireg][15]); // observaciones
                    jTextArea3.setText(this.tablaDatos[ireg][20]); // incidencia
                    jTextArea2.setText(this.tablaDatos[ireg][21]); // explicacion
                    
                    jTextField22.setText(this.tablaDatos[ireg][16]); // telefono   
                    
                    jTextField19.setText(this.tablaDatos[ireg][28]); // tarifa elec
                    jTextField20.setText(this.tablaDatos[ireg][29]); // tarifa gas
                    
                    jTextField24.setText(this.tablaDatos[ireg][39]); // memo  
                    
                    jTextField74.setText(this.tablaDatos[ireg][41]); // Agente comercial
                    jTextField75.setText(Integer.toString(ireg));    // contador 
                    
                    jTextField123.setText(this.tablaDatos[ireg][47]) ; // Número de contratos repetidos.
                    
                     jTextField1.setText(this.tablaDatos[ireg][33]); // Servicios Extra contratados
                    
                    
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
                 
           }
             
           }
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
             jTextField17.setBackground(new Color(0xFE899B));//
             jTextField24.setBackground(new Color(0xFE899B));//
            
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
            
             jTextField17.setBackground(Color.white);// id_m_r
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
            
             jTextField17.setBackground(cResaltado);// 
             jTextField24.setBackground(cResaltado);// id_m_r
            
             jTextField74.setBackground(cResaltado);// Agente comercial
           
       }
     public void RefrescarTablaBD() {
          
           
           mostrarDatosConTableModel(); 
           
      
          
      }
     private void mostrarDatosConTableModel() {
		
                String fProd ="";
                System.out.println("----COMIENZO EL FORMATEO ---");
           
                DefaultTableModel model;
		model = new DefaultTableModel();        // definimos el objeto tableModel
               
		miTabla01 = new JTable();                // creamos la instancia de la tabla
		miTabla01.setModel(model);
                
                                 
		model.addColumn("ID");                
		model.addColumn("Estado");
		model.addColumn("Docout");
                model.addColumn("Memo/R&P");
		model.addColumn("Incidencia");
                model.addColumn("Orden");
                model.addColumn("CUPS Elect");
                model.addColumn("CUPS gas");
                model.addColumn("Agente");
                model.addColumn("Cod Postal");
                model.addColumn("Municipio");
                model.addColumn("Provincia");
                model.addColumn("Direccion");
                model.addColumn("Titular");
                model.addColumn("NIF-CIF");
                model.addColumn("Fecha Firma");
                model.addColumn("CV");
                model.addColumn("Cons.CTO.kWha");
                model.addColumn("Cons.WEBS.kWha");
                model.addColumn("Pagado");
                model.addColumn("P. Fenosa");
                model.addColumn("Tarifa");
                model.addColumn("Campaña");
                model.addColumn("Telefono");
                model.addColumn("Pers.Contacto");
                model.addColumn("Problema");
                model.addColumn("Solucion");
                model.addColumn("Observaciones");
                model.addColumn("Tarifa gas");
                model.addColumn("Consumo gas");
                model.addColumn("C_servicios");
                model.addColumn("Reactiva kWha");
                model.addColumn("iServicios");
                model.addColumn("iPunteado");
                
                
		miTabla01.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		miTabla01.getTableHeader().setReorderingAllowed(false);
                
                TableColumn columna1 = miTabla01.getColumn("Titular");
                TableColumn columna2 = miTabla01.getColumn("Direccion");
                TableColumn columna3 = miTabla01.getColumn("CUPS Elect");
                TableColumn columna4 = miTabla01.getColumn("CUPS gas");
                
               
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
               
		miPymesDao1.buscarContratos(model,this.plogin,this.ppassword,this.filtroEstadoSel,this.filtroFechaSel,this.filtroProvincia,this.filtroAgente,this.filtroMakro,this.filtroIncidencia,fProd);
                
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
    // -----------------------------------------------------------------------------------------------------------------------------------
     public void seleccionaElementoTabla(int id,int ind) {
         
         this.idSelect = id ;
         this.indTabla = ind ;
         
     }
       // ----------------------------------------------------------------------------------------------------------------
       public final void modificarArbolNuevos() {
          
           int i,j,k,cnt,nCUPS,dia=1,ndia=0,cdia=1,ind=0;
           String fecha ="",contrato="";
           String nombre = "";
           String sdia="",CUPS,str="";
           
        //    RenderArbol myRenderArbol ;
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
    // -------------------------------------------------------------------------------------------------
       public void actualizarCalculosLiquidaciones() {
           
                int p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,ptot,nReg;
                int pn1,pn2,pn3,pn4,pn5,pn6,pn7,pn8,pn9,pn10,pntot;
                double v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,vtot,val,v11,v12,v13;
                double vn1,vn2,vn3,vn4,vn5,vn6,vn7,vn8,vn9,vn10,vntot;
                
           
                String dd = jTextField40.getText() ;
                String mm = jTextField72.getText() ;
                String aa = jTextField73.getText() ;
                
                String fechaSel = aa+"-"+mm+"-"+dd+" 00:00:00" ;
                
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
		nReg=miPymesDao2.tablaLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial);
                this.nLiquidaciones = nReg;
                // ................................................................................................................................
                
                p1 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,0);
                p2 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,1);
                p3 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,2);
                p4 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,3); 
                p5 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,4);
                p6 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,5);
                p7 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,6); 
                p8 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,7);
                
                p9 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,8);
                p10 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,9);
                
                 p11 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,10);
                 p12 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,11);
                 p13 = miPymesDao2.contarProductosLiquidaciones(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,12);
               
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
                v8 = (p9-p8) * Double.parseDouble(jTextField109.getText());  jTextField110.setText(String.valueOf(v8));
                v9 = p10 * Double.parseDouble(jTextField113.getText());  jTextField114.setText(String.valueOf(v9));
                
                v11 = p11 * Double.parseDouble(jTextField127.getText());  jTextField126.setText(String.valueOf(v11));
                v12 = p12 * Double.parseDouble(jTextField131.getText());  jTextField130.setText(String.valueOf(v12));
                v13 = p13 * Double.parseDouble(jTextField135.getText());  jTextField134.setText(String.valueOf(v13)); 
                  
                // ................................................................................................................................
                
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
}

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
        System.setProperty("netbeans.buildnumber", "2.0.5");
        
        System.setProperty("netbeans.projectname","CONTRACT - SERNOVEN");

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

        jTabbedPane3 = new javax.swing.JTabbedPane();
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
        jComboBox2 = new javax.swing.JComboBox();
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
        jButton1 = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
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
        jPanel14 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jTextField46 = new javax.swing.JTextField();
        jTextField48 = new javax.swing.JTextField();
        jTextField49 = new javax.swing.JTextField();
        jTextField50 = new javax.swing.JTextField();
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
        jTextField53 = new javax.swing.JTextField();
        jTextField54 = new javax.swing.JTextField();
        jTextField55 = new javax.swing.JTextField();
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
        jTextField60 = new javax.swing.JTextField();
        jTextField61 = new javax.swing.JTextField();
        jTextField62 = new javax.swing.JTextField();
        jTextField109 = new javax.swing.JTextField();
        jTextField113 = new javax.swing.JTextField();
        jTextField84 = new javax.swing.JTextField();
        jTextField87 = new javax.swing.JTextField();
        jTextField90 = new javax.swing.JTextField();
        jTextField96 = new javax.swing.JTextField();
        jTextField97 = new javax.swing.JTextField();
        jTextField99 = new javax.swing.JTextField();
        jTextField98 = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jTextField64 = new javax.swing.JTextField();
        jTextField66 = new javax.swing.JTextField();
        jTextField67 = new javax.swing.JTextField();
        jTextField68 = new javax.swing.JTextField();
        jTextField110 = new javax.swing.JTextField();
        jTextField114 = new javax.swing.JTextField();
        jTextField85 = new javax.swing.JTextField();
        jTextField88 = new javax.swing.JTextField();
        jTextField91 = new javax.swing.JTextField();
        jTextField100 = new javax.swing.JTextField();
        jTextField101 = new javax.swing.JTextField();
        jTextField102 = new javax.swing.JTextField();
        jTextField103 = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
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
        jLabel129 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jTextField104 = new javax.swing.JTextField();
        jLabel109 = new javax.swing.JLabel();
        jTextField105 = new javax.swing.JTextField();
        jTextField106 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jTextField125 = new javax.swing.JTextField();
        jTextField138 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
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
        jTextField149 = new javax.swing.JTextField();
        jTextField63 = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        jTextField65 = new javax.swing.JTextField();
        jLabel110 = new javax.swing.JLabel();
        jTextField69 = new javax.swing.JTextField();
        jLabel111 = new javax.swing.JLabel();
        jTextField119 = new javax.swing.JTextField();
        jTextField120 = new javax.swing.JTextField();
        jTextField121 = new javax.swing.JTextField();
        jTextField122 = new javax.swing.JTextField();
        jLabel128 = new javax.swing.JLabel();
        jTextField126 = new javax.swing.JTextField();
        jTextField127 = new javax.swing.JTextField();
        jTextField128 = new javax.swing.JTextField();
        jTextField129 = new javax.swing.JTextField();
        jTextField150 = new javax.swing.JTextField();
        jTextField151 = new javax.swing.JTextField();
        jTextField152 = new javax.swing.JTextField();
        jTextField153 = new javax.swing.JTextField();
        jTextField154 = new javax.swing.JTextField();
        jTextField155 = new javax.swing.JTextField();
        jTextField156 = new javax.swing.JTextField();
        jTextField157 = new javax.swing.JTextField();
        jTextField158 = new javax.swing.JTextField();
        jTextField159 = new javax.swing.JTextField();
        jTextField160 = new javax.swing.JTextField();
        jTextField161 = new javax.swing.JTextField();
        jTextField162 = new javax.swing.JTextField();
        jTextField163 = new javax.swing.JTextField();
        jTextField164 = new javax.swing.JTextField();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jTextField32 = new javax.swing.JTextField();
        jTextField33 = new javax.swing.JTextField();
        jTextField34 = new javax.swing.JTextField();
        jTextField35 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jTextField37 = new javax.swing.JTextField();
        jTextField38 = new javax.swing.JTextField();
        jTextField39 = new javax.swing.JTextField();
        jTextField42 = new javax.swing.JTextField();
        jTextField43 = new javax.swing.JTextField();
        jTextField44 = new javax.swing.JTextField();
        jTextField45 = new javax.swing.JTextField();
        jTextField258 = new javax.swing.JTextField();
        jTextField259 = new javax.swing.JTextField();
        jTextField260 = new javax.swing.JTextField();
        jTextField261 = new javax.swing.JTextField();
        jTextField262 = new javax.swing.JTextField();
        jTextField263 = new javax.swing.JTextField();
        jTextField264 = new javax.swing.JTextField();
        jTextField265 = new javax.swing.JTextField();
        jLabel180 = new javax.swing.JLabel();
        jLabel181 = new javax.swing.JLabel();
        jLabel182 = new javax.swing.JLabel();
        jLabel183 = new javax.swing.JLabel();
        jLabel184 = new javax.swing.JLabel();
        jLabel185 = new javax.swing.JLabel();
        jLabel186 = new javax.swing.JLabel();
        jLabel187 = new javax.swing.JLabel();
        jLabel188 = new javax.swing.JLabel();
        jLabel189 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jTextField47 = new javax.swing.JTextField();
        jTextField52 = new javax.swing.JTextField();
        jTextField56 = new javax.swing.JTextField();
        jTextField57 = new javax.swing.JTextField();
        jTextField165 = new javax.swing.JTextField();
        jTextField166 = new javax.swing.JTextField();
        jTextField167 = new javax.swing.JTextField();
        jTextField168 = new javax.swing.JTextField();
        jTextField169 = new javax.swing.JTextField();
        jTextField170 = new javax.swing.JTextField();
        jTextField171 = new javax.swing.JTextField();
        jTextField172 = new javax.swing.JTextField();
        jTextField173 = new javax.swing.JTextField();
        jTextField59 = new javax.swing.JTextField();
        jTextField174 = new javax.swing.JTextField();
        jTextField175 = new javax.swing.JTextField();
        jTextField176 = new javax.swing.JTextField();
        jTextField177 = new javax.swing.JTextField();
        jTextField178 = new javax.swing.JTextField();
        jTextField179 = new javax.swing.JTextField();
        jTextField180 = new javax.swing.JTextField();
        jTextField181 = new javax.swing.JTextField();
        jTextField182 = new javax.swing.JTextField();
        jTextField183 = new javax.swing.JTextField();
        jTextField184 = new javax.swing.JTextField();
        jTextField185 = new javax.swing.JTextField();
        jTextField186 = new javax.swing.JTextField();
        jTextField187 = new javax.swing.JTextField();
        jTextField188 = new javax.swing.JTextField();
        jTextField189 = new javax.swing.JTextField();
        jTextField190 = new javax.swing.JTextField();
        jTextField191 = new javax.swing.JTextField();
        jTextField192 = new javax.swing.JTextField();
        jTextField193 = new javax.swing.JTextField();
        jTextField194 = new javax.swing.JTextField();
        jTextField195 = new javax.swing.JTextField();
        jTextField196 = new javax.swing.JTextField();
        jTextField197 = new javax.swing.JTextField();
        jTextField198 = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jTextField199 = new javax.swing.JTextField();
        jTextField200 = new javax.swing.JTextField();
        jTextField201 = new javax.swing.JTextField();
        jTextField202 = new javax.swing.JTextField();
        jTextField203 = new javax.swing.JTextField();
        jTextField204 = new javax.swing.JTextField();
        jTextField205 = new javax.swing.JTextField();
        jTextField206 = new javax.swing.JTextField();
        jTextField207 = new javax.swing.JTextField();
        jTextField208 = new javax.swing.JTextField();
        jTextField209 = new javax.swing.JTextField();
        jTextField210 = new javax.swing.JTextField();
        jTextField211 = new javax.swing.JTextField();
        jLabel148 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        jLabel154 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        jLabel156 = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        jLabel158 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        jLabel160 = new javax.swing.JLabel();
        jLabel161 = new javax.swing.JLabel();
        jTextField212 = new javax.swing.JTextField();
        jTextField213 = new javax.swing.JTextField();
        jTextField214 = new javax.swing.JTextField();
        jTextField215 = new javax.swing.JTextField();
        jLabel162 = new javax.swing.JLabel();
        jLabel163 = new javax.swing.JLabel();
        jLabel164 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jTextField216 = new javax.swing.JTextField();
        jLabel165 = new javax.swing.JLabel();
        jTextField217 = new javax.swing.JTextField();
        jTextField218 = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jTextField219 = new javax.swing.JTextField();
        jTextField220 = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jTextField221 = new javax.swing.JTextField();
        jTextField222 = new javax.swing.JTextField();
        jTextField223 = new javax.swing.JTextField();
        jTextField224 = new javax.swing.JTextField();
        jTextField225 = new javax.swing.JTextField();
        jTextField226 = new javax.swing.JTextField();
        jTextField227 = new javax.swing.JTextField();
        jTextField228 = new javax.swing.JTextField();
        jTextField229 = new javax.swing.JTextField();
        jTextField230 = new javax.swing.JTextField();
        jTextField231 = new javax.swing.JTextField();
        jTextField232 = new javax.swing.JTextField();
        jLabel166 = new javax.swing.JLabel();
        jTextField233 = new javax.swing.JTextField();
        jLabel167 = new javax.swing.JLabel();
        jTextField234 = new javax.swing.JTextField();
        jLabel168 = new javax.swing.JLabel();
        jTextField235 = new javax.swing.JTextField();
        jTextField236 = new javax.swing.JTextField();
        jTextField237 = new javax.swing.JTextField();
        jTextField238 = new javax.swing.JTextField();
        jLabel169 = new javax.swing.JLabel();
        jTextField239 = new javax.swing.JTextField();
        jTextField240 = new javax.swing.JTextField();
        jTextField241 = new javax.swing.JTextField();
        jTextField242 = new javax.swing.JTextField();
        jTextField243 = new javax.swing.JTextField();
        jTextField244 = new javax.swing.JTextField();
        jTextField245 = new javax.swing.JTextField();
        jTextField246 = new javax.swing.JTextField();
        jTextField247 = new javax.swing.JTextField();
        jTextField248 = new javax.swing.JTextField();
        jTextField249 = new javax.swing.JTextField();
        jTextField250 = new javax.swing.JTextField();
        jTextField251 = new javax.swing.JTextField();
        jTextField252 = new javax.swing.JTextField();
        jTextField253 = new javax.swing.JTextField();
        jTextField254 = new javax.swing.JTextField();
        jTextField255 = new javax.swing.JTextField();
        jTextField256 = new javax.swing.JTextField();
        jTextField257 = new javax.swing.JTextField();
        jLabel170 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jLabel173 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        jLabel175 = new javax.swing.JLabel();
        jLabel176 = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        jLabel178 = new javax.swing.JLabel();
        jLabel179 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jTextField266 = new javax.swing.JTextField();
        jTextField267 = new javax.swing.JTextField();
        jTextField268 = new javax.swing.JTextField();
        jTextField269 = new javax.swing.JTextField();
        jTextField270 = new javax.swing.JTextField();
        jTextField271 = new javax.swing.JTextField();
        jTextField272 = new javax.swing.JTextField();
        jTextField273 = new javax.swing.JTextField();
        jTextField274 = new javax.swing.JTextField();
        jTextField275 = new javax.swing.JTextField();
        jTextField276 = new javax.swing.JTextField();
        jTextField277 = new javax.swing.JTextField();
        jTextField278 = new javax.swing.JTextField();
        jTextField279 = new javax.swing.JTextField();
        jTextField280 = new javax.swing.JTextField();
        jTextField281 = new javax.swing.JTextField();
        jTextField282 = new javax.swing.JTextField();
        jTextField283 = new javax.swing.JTextField();
        jTextField284 = new javax.swing.JTextField();
        jTextField285 = new javax.swing.JTextField();
        jLabel190 = new javax.swing.JLabel();
        jLabel191 = new javax.swing.JLabel();
        jLabel192 = new javax.swing.JLabel();
        jLabel193 = new javax.swing.JLabel();
        jLabel194 = new javax.swing.JLabel();
        jLabel195 = new javax.swing.JLabel();
        jLabel196 = new javax.swing.JLabel();
        jLabel197 = new javax.swing.JLabel();
        jLabel198 = new javax.swing.JLabel();
        jLabel199 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel200 = new javax.swing.JLabel();
        jTextField286 = new javax.swing.JTextField();
        jLabel201 = new javax.swing.JLabel();
        jTextField287 = new javax.swing.JTextField();
        jTextField288 = new javax.swing.JTextField();
        jTextField289 = new javax.swing.JTextField();
        jLabel202 = new javax.swing.JLabel();
        jTextField290 = new javax.swing.JTextField();
        jLabel203 = new javax.swing.JLabel();
        jTextField291 = new javax.swing.JTextField();
        jTextField292 = new javax.swing.JTextField();
        jTextField293 = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        jTextField71 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jTextField70 = new javax.swing.JTextField();
        jTextField297 = new javax.swing.JTextField();
        jTextField294 = new javax.swing.JTextField();
        jTextField295 = new javax.swing.JTextField();
        jTextField296 = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jLabel204 = new javax.swing.JLabel();
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

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PENDIENTE", "CERTIFICADO", "RESID", "KO", "RESID CERTIFIC", "DOC_OUT", "ERROR WEBSALES", "DECOMISADO" }));

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
                .addGap(18, 18, 18)
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

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elegir...", "2.0A", "2.0DHA", "2.1A", "2.1DHA", "3.0A", "3.1A", " " }));

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(54, 54, 54)
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
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField20, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField12, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel24Layout.createSequentialGroup()
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel25))
                                .addGroup(jPanel24Layout.createSequentialGroup()
                                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(botonBuscarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel24Layout.createSequentialGroup()
                                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel24)
                                        .addComponent(jLabel32)))
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel35))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel108)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel138, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel13)))
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
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(actuaMunicipio))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(botonBuscarTitular, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(botonBuscarNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel130)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField123, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(botonBuscarTitular))
                .addGap(5, 5, 5)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(botonBuscarNIF)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel130)
                        .addComponent(jTextField123, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
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
                    .addComponent(jTextField74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
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

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel56, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel56.text")); // NOI18N

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
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PENDIENTE", "CERTIFICADO", "RESID", "KOs", "RESID CERTIFIC", "TODOS", "PUNTEADOS", "NO PUNTEADOS", "DOC_OUT", "ERROR WEB SALES", "DECOMISIONADO", " " }));
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
            .addComponent(miBarra01, javax.swing.GroupLayout.DEFAULT_SIZE, 1657, Short.MAX_VALUE)
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

        ListaComercialLiq.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "J & C Asesores", "ETP", "NADINE", "EMILIO-RAQUEL", "SERNOVEN", "MIGUEL", "SHEILA", "MARIO SORIA", "ALBERTO", "AGENTE 01", "AGENTE 02", "AGENTE 03", "AGENTE 04", "TODOS" }));
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

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jButton1.text")); // NOI18N
        jButton1.setEnabled(false);

        jPanel18.setBackground(new java.awt.Color(204, 204, 255));

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
                    .addComponent(jTextField133, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(jTextField137))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField132, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(jTextField136, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jTextField135, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel137, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jTextField131)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField130, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(jTextField134))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel136, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel134, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jPanel14.setBackground(new java.awt.Color(204, 204, 204));

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel64, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel64.text")); // NOI18N

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel65, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel65.text")); // NOI18N

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel67, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel67.text")); // NOI18N

        jTextField46.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField46.text")); // NOI18N

        jTextField48.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField48.text")); // NOI18N

        jTextField49.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField49.text")); // NOI18N

        jTextField50.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField50.text")); // NOI18N
        jTextField50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField50ActionPerformed(evt);
            }
        });

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

        jTextField53.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField53.text")); // NOI18N

        jTextField54.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField54.text")); // NOI18N
        jTextField54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField54ActionPerformed(evt);
            }
        });

        jTextField55.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField55.text")); // NOI18N

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

        jTextField79.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField79.text")); // NOI18N

        jTextField86.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField86.text")); // NOI18N

        jTextField89.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField89.text")); // NOI18N

        jTextField92.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField92.text")); // NOI18N

        jTextField93.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField93.text")); // NOI18N

        jTextField94.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField94.text")); // NOI18N

        jTextField95.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField95.text")); // NOI18N

        jTextField58.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField58.text")); // NOI18N

        jTextField60.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField60.text")); // NOI18N

        jTextField61.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField61.text")); // NOI18N

        jTextField62.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField62.text")); // NOI18N

        jTextField109.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField109.text")); // NOI18N

        jTextField113.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField113.text")); // NOI18N

        jTextField84.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField84.text")); // NOI18N

        jTextField87.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField87.text")); // NOI18N
        jTextField87.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField87ActionPerformed(evt);
            }
        });

        jTextField90.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField90.text")); // NOI18N

        jTextField96.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField96.text")); // NOI18N

        jTextField97.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField97.text")); // NOI18N

        jTextField99.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField99.text")); // NOI18N

        jTextField98.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField98.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel80, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel80.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel85, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel85.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel87, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel87.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel89, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel89.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel122, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel122.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel124, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel124.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel94, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel94.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel96, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel96.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel102, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel102.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel103, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel103.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel104, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel104.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel105, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel105.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel106, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel106.text")); // NOI18N

        jTextField64.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField64.text")); // NOI18N
        jTextField64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField64ActionPerformed(evt);
            }
        });

        jTextField66.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField66.text")); // NOI18N

        jTextField67.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField67.text")); // NOI18N

        jTextField68.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField68.text")); // NOI18N

        jTextField110.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField110.text")); // NOI18N

        jTextField114.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField114.text")); // NOI18N

        jTextField85.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField85.text")); // NOI18N

        jTextField88.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField88.text")); // NOI18N

        jTextField91.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField91.text")); // NOI18N

        jTextField100.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField100.text")); // NOI18N

        jTextField101.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField101.text")); // NOI18N

        jTextField102.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField102.text")); // NOI18N

        jTextField103.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField103.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel81, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel81.text")); // NOI18N

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

        jTextField116.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField116.text")); // NOI18N

        jTextField117.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField117.text")); // NOI18N

        jTextField118.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField118.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel126, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel126.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel127, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel127.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel129, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel129.text")); // NOI18N

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel53, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel53.text")); // NOI18N

        jTextField104.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField104.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel109, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel109.text")); // NOI18N

        jTextField105.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField105.text")); // NOI18N

        jTextField106.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField106.text")); // NOI18N

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel54, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel54.text")); // NOI18N

        jTextField125.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField125.text")); // NOI18N

        jTextField138.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField138.text")); // NOI18N
        jTextField138.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField138ActionPerformed(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel55, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel55.text")); // NOI18N

        jTextField139.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField139.text")); // NOI18N

        jTextField140.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField140.text")); // NOI18N

        jTextField141.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField141.text")); // NOI18N

        jTextField142.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField142.text")); // NOI18N

        jTextField143.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField143.text")); // NOI18N

        jTextField144.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField144.text")); // NOI18N

        jTextField145.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField145.text")); // NOI18N

        jTextField146.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField146.text")); // NOI18N

        jTextField147.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField147.text")); // NOI18N

        jTextField148.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField148.text")); // NOI18N

        jTextField149.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField149.text")); // NOI18N
        jTextField149.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField149ActionPerformed(evt);
            }
        });

        jTextField63.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField63.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel91, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel91.text")); // NOI18N

        jTextField65.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField65.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel110, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel110.text")); // NOI18N

        jTextField69.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField69.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel111, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel111.text")); // NOI18N

        jTextField119.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField119.text")); // NOI18N

        jTextField120.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField120.text")); // NOI18N

        jTextField121.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField121.text")); // NOI18N

        jTextField122.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField122.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel128, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel128.text")); // NOI18N

        jTextField126.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField126.text")); // NOI18N

        jTextField127.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField127.text")); // NOI18N

        jTextField128.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField128.text")); // NOI18N

        jTextField129.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField129.text")); // NOI18N

        jTextField150.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField150.text")); // NOI18N

        jTextField151.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField151.text")); // NOI18N

        jTextField152.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField152.text")); // NOI18N

        jTextField153.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField153.text")); // NOI18N

        jTextField154.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField154.text")); // NOI18N

        jTextField155.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField155.text")); // NOI18N

        jTextField156.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField156.text")); // NOI18N

        jTextField157.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField157.text")); // NOI18N

        jTextField158.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField158.text")); // NOI18N

        jTextField159.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField159.text")); // NOI18N

        jTextField160.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField160.text")); // NOI18N

        jTextField161.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField161.text")); // NOI18N

        jTextField162.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField162.text")); // NOI18N

        jTextField163.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField163.text")); // NOI18N
        jTextField163.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField163ActionPerformed(evt);
            }
        });

        jTextField164.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField164.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel132, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel132.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel133, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel133.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel139, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel139.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel140, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel140.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel141, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel141.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel142, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel142.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel143, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel143.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel144, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel144.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel145, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel145.text")); // NOI18N

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel66, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel66.text")); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(299, 299, 299))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField106)
                                            .addComponent(jTextField125, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField138, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField50, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField49, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField48, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel14Layout.createSequentialGroup()
                                                .addComponent(jTextField105, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addComponent(jTextField77, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20)
                                        .addComponent(jTextField142))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField140, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField141, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField143, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField144, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField145, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField146, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField147, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextField139, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextField115, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField83, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField82, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField81, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField80, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField78, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField76, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField111, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField108, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField148, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addGap(72, 72, 72)
                                        .addComponent(jLabel64)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel65)
                                        .addGap(84, 84, 84))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField112, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField79, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField107, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField55, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField54, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField53, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jTextField161, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextField122, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextField156, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField158, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField157, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField159, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField160, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel133, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jTextField113, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jTextField109, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jTextField84, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addComponent(jLabel139, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                                        .addComponent(jLabel140, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(55, 55, 55))
                                                    .addComponent(jLabel141, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel142, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(jTextField62, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                                                    .addComponent(jTextField61, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextField60, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextField58, javax.swing.GroupLayout.Alignment.LEADING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                                        .addComponent(jLabel87)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jTextField65))
                                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                                        .addComponent(jLabel89)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jTextField69))
                                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel109)
                                                            .addComponent(jLabel91))
                                                        .addGap(10, 10, 10)
                                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel66)
                                                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jTextField104, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                                                                .addComponent(jTextField63)))
                                                        .addGap(0, 0, Short.MAX_VALUE))))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField89)
                                            .addComponent(jTextField86)
                                            .addComponent(jTextField92))
                                        .addGap(95, 95, 95)
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField90, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField87, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                                            .addComponent(jTextField96)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField95, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField93, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField94, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField116))
                                        .addGap(22, 22, 22)
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTextField163)
                                            .addComponent(jTextField162)
                                            .addComponent(jTextField164, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jPanel14Layout.createSequentialGroup()
                                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel144, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel145, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jTextField98, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                                                    .addComponent(jTextField117)))
                                            .addGroup(jPanel14Layout.createSequentialGroup()
                                                .addComponent(jLabel143, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextField99, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextField97, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                            .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jLabel67))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel85)
                                        .addComponent(jLabel80))
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
                                    .addComponent(jLabel110)
                                    .addComponent(jLabel111))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField118, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jTextField103, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField102, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField101, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField154, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                            .addComponent(jTextField153)
                                            .addComponent(jTextField155)
                                            .addComponent(jTextField152))
                                        .addGap(32, 32, 32)
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel101)
                                            .addComponent(jLabel100)
                                            .addComponent(jLabel99)
                                            .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTextField100, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                            .addComponent(jTextField91)
                                            .addComponent(jTextField88)
                                            .addComponent(jTextField85)
                                            .addComponent(jTextField110, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField68, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField67, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField66, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField149, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField114))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel14Layout.createSequentialGroup()
                                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jTextField64)
                                                    .addComponent(jTextField119)
                                                    .addComponent(jTextField120, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                                    .addComponent(jTextField121)
                                                    .addComponent(jTextField126)
                                                    .addComponent(jTextField127))
                                                .addGap(30, 30, 30)
                                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel81)
                                                    .addComponent(jLabel84)
                                                    .addComponent(jLabel86)
                                                    .addComponent(jLabel88)
                                                    .addComponent(jLabel90)
                                                    .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(jPanel14Layout.createSequentialGroup()
                                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jTextField128)
                                                    .addComponent(jTextField129)
                                                    .addComponent(jTextField150)
                                                    .addComponent(jTextField151))
                                                .addGap(42, 42, 42)
                                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel98, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel97, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel95, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel93, javax.swing.GroupLayout.Alignment.TRAILING)))))))))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel65)
                            .addComponent(jLabel67)
                            .addComponent(jLabel66))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel64)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80)
                    .addComponent(jLabel81)
                    .addComponent(jTextField104, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel109)
                    .addComponent(jTextField105, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField149, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel84)
                            .addComponent(jLabel85)
                            .addComponent(jTextField106, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel91)
                            .addComponent(jTextField119, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel86)
                            .addComponent(jLabel87)
                            .addComponent(jTextField54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel110)
                            .addComponent(jTextField120, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField125, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField68, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel88)
                    .addComponent(jLabel89)
                    .addComponent(jTextField138, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel111)
                    .addComponent(jTextField121, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel90)
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField107, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField108, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField109, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField110, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel122)
                    .addComponent(jLabel123)
                    .addComponent(jTextField139, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField122, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel128)
                    .addComponent(jTextField126, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField111, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField112, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField113, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField114, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel124)
                    .addComponent(jLabel125)
                    .addComponent(jTextField140, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField127, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField156, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel132))
                .addGap(8, 8, 8)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField85, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel93)
                            .addComponent(jTextField128, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField88, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel95)
                            .addComponent(jTextField129, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField91, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel97)
                            .addComponent(jTextField150, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField100, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel98)
                            .addComponent(jTextField151, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField101, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel99)
                            .addComponent(jTextField152, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField157, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel133))
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel94)
                                .addComponent(jTextField141, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField77, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField86, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField87, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel96)
                                .addComponent(jTextField142, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField158, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel139)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField78, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField89, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel102)
                            .addComponent(jTextField143, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField159, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel140))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField92, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField144, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel103)
                                .addComponent(jTextField96, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField160, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel141)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField93, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField97, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel104)
                            .addComponent(jTextField145, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField161, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel142))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField94, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField99, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel105)
                            .addComponent(jTextField102, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel100)
                            .addComponent(jTextField146, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField153, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField162, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel143))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField95, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField98, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel106)
                            .addComponent(jTextField103, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel101)
                            .addComponent(jTextField147, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField154, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField163, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel144))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField115, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField116, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField117, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField118, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel126)
                            .addComponent(jLabel127)
                            .addComponent(jTextField148, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField155, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField164, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel145))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel129)
                        .addGap(49, 49, 49))))
        );

        jPanel12.setBackground(new java.awt.Color(204, 204, 204));

        jTextField32.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField32.text")); // NOI18N

        jTextField33.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField33.text")); // NOI18N

        jTextField34.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField34.text")); // NOI18N

        jTextField35.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField35.text")); // NOI18N

        jTextField36.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField36.text")); // NOI18N

        jTextField37.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField37.text")); // NOI18N

        jTextField38.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField38.text")); // NOI18N

        jTextField39.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField39.text")); // NOI18N

        jTextField42.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField42.text")); // NOI18N

        jTextField43.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField43.text")); // NOI18N

        jTextField44.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField44.text")); // NOI18N

        jTextField45.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField45.text")); // NOI18N

        jTextField258.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField258.text")); // NOI18N

        jTextField259.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField259.text")); // NOI18N

        jTextField260.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField260.text")); // NOI18N

        jTextField261.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField261.text")); // NOI18N

        jTextField262.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField262.text")); // NOI18N

        jTextField263.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField263.text")); // NOI18N

        jTextField264.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField264.text")); // NOI18N

        jTextField265.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField265.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel180, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel180.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel181, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel181.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel182, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel182.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel183, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel183.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel184, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel184.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel185, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel185.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel186, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel186.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel187, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel187.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel188, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel188.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel189, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel189.text")); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField32, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(jTextField33)
                    .addComponent(jTextField34)
                    .addComponent(jTextField35)
                    .addComponent(jTextField36))
                .addGap(71, 71, 71)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField37)
                    .addComponent(jTextField38)
                    .addComponent(jTextField39, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(jTextField42)
                    .addComponent(jTextField43))
                .addGap(26, 26, 26)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField44, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(jTextField45)
                    .addComponent(jTextField258)
                    .addComponent(jTextField259)
                    .addComponent(jTextField260))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel180, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField261, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel181, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField262, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel184, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField265, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel182, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField263, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel183, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField264, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel12Layout.createSequentialGroup()
                                    .addGap(53, 53, 53)
                                    .addComponent(jLabel185, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel186, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel187, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel188, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel189, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField261, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel180)
                    .addComponent(jLabel185))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField262, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel181)
                    .addComponent(jLabel186))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField258, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField263, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel182)
                    .addComponent(jLabel187))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField259, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField264, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel183)
                    .addComponent(jLabel188))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField260, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField265, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel184)
                    .addComponent(jLabel189)))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane4.addTab(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jPanel8.TabConstraints.tabTitle"), jPanel8); // NOI18N

        jPanel23.setBackground(new java.awt.Color(204, 204, 204));

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel68, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel68.text")); // NOI18N

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel79, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel79.text")); // NOI18N

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel82, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel82.text")); // NOI18N

        jTextField47.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField47.text")); // NOI18N

        jTextField52.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField52.text")); // NOI18N

        jTextField56.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField56.text")); // NOI18N

        jTextField57.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField57.text")); // NOI18N
        jTextField57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField57ActionPerformed(evt);
            }
        });

        jTextField165.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField165.text")); // NOI18N

        jTextField166.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField166.text")); // NOI18N

        jTextField167.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField167.text")); // NOI18N

        jTextField168.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField168.text")); // NOI18N

        jTextField169.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField169.text")); // NOI18N

        jTextField170.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField170.text")); // NOI18N

        jTextField171.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField171.text")); // NOI18N

        jTextField172.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField172.text")); // NOI18N

        jTextField173.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField173.text")); // NOI18N

        jTextField59.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField59.text")); // NOI18N

        jTextField174.setForeground(new java.awt.Color(255, 0, 0));
        jTextField174.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField174.text")); // NOI18N

        jTextField175.setForeground(new java.awt.Color(255, 0, 0));
        jTextField175.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField175.text")); // NOI18N
        jTextField175.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField175ActionPerformed(evt);
            }
        });

        jTextField176.setForeground(new java.awt.Color(204, 0, 0));
        jTextField176.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField176.text")); // NOI18N

        jTextField177.setForeground(new java.awt.Color(255, 0, 0));
        jTextField177.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField177.text")); // NOI18N
        jTextField177.setPreferredSize(new java.awt.Dimension(146, 20));

        jTextField178.setForeground(new java.awt.Color(255, 0, 0));
        jTextField178.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField178.text")); // NOI18N
        jTextField178.setMaximumSize(new java.awt.Dimension(146, 20));
        jTextField178.setMinimumSize(new java.awt.Dimension(146, 20));
        jTextField178.setPreferredSize(new java.awt.Dimension(146, 20));
        jTextField178.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField178ActionPerformed(evt);
            }
        });

        jTextField179.setForeground(new java.awt.Color(255, 0, 0));
        jTextField179.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField179.text")); // NOI18N

        jTextField180.setForeground(new java.awt.Color(255, 0, 0));
        jTextField180.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField180.text")); // NOI18N

        jTextField181.setForeground(new java.awt.Color(255, 0, 0));
        jTextField181.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField181.text")); // NOI18N

        jTextField182.setForeground(new java.awt.Color(255, 0, 0));
        jTextField182.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField182.text")); // NOI18N

        jTextField183.setForeground(new java.awt.Color(255, 0, 0));
        jTextField183.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField183.text")); // NOI18N

        jTextField184.setForeground(new java.awt.Color(255, 0, 0));
        jTextField184.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField184.text")); // NOI18N

        jTextField185.setForeground(new java.awt.Color(255, 0, 0));
        jTextField185.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField185.text")); // NOI18N

        jTextField186.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField186.text")); // NOI18N

        jTextField187.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField187.text")); // NOI18N

        jTextField188.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField188.text")); // NOI18N

        jTextField189.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField189.text")); // NOI18N

        jTextField190.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField190.text")); // NOI18N

        jTextField191.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField191.text")); // NOI18N

        jTextField192.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField192.text")); // NOI18N

        jTextField193.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField193.text")); // NOI18N
        jTextField193.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField193ActionPerformed(evt);
            }
        });

        jTextField194.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField194.text")); // NOI18N

        jTextField195.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField195.text")); // NOI18N

        jTextField196.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField196.text")); // NOI18N

        jTextField197.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField197.text")); // NOI18N

        jTextField198.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField198.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel83, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel83.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel112, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel112.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel113, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel113.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel114, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel114.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel146, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel146.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel147, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel147.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel115, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel115.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel116, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel116.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel117, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel117.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel118, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel118.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel119, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel119.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel120, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel120.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel121, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel121.text")); // NOI18N

        jTextField199.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField199.text")); // NOI18N
        jTextField199.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField199ActionPerformed(evt);
            }
        });

        jTextField200.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField200.text")); // NOI18N

        jTextField201.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField201.text")); // NOI18N

        jTextField202.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField202.text")); // NOI18N

        jTextField203.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField203.text")); // NOI18N

        jTextField204.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField204.text")); // NOI18N

        jTextField205.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField205.text")); // NOI18N

        jTextField206.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField206.text")); // NOI18N

        jTextField207.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField207.text")); // NOI18N

        jTextField208.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField208.text")); // NOI18N

        jTextField209.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField209.text")); // NOI18N

        jTextField210.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField210.text")); // NOI18N

        jTextField211.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField211.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel148, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel148.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel149, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel149.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel150, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel150.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel151, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel151.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel152, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel152.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel153, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel153.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel154, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel154.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel155, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel155.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel156, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel156.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel157, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel157.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel158, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel158.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel159, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel159.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel160, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel160.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel161, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel161.text")); // NOI18N

        jTextField212.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField212.text")); // NOI18N

        jTextField213.setForeground(new java.awt.Color(255, 0, 0));
        jTextField213.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField213.text")); // NOI18N

        jTextField214.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField214.text")); // NOI18N

        jTextField215.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField215.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel162, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel162.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel163, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel163.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel164, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel164.text")); // NOI18N

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel57, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel57.text")); // NOI18N

        jTextField216.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField216.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel165, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel165.text")); // NOI18N

        jTextField217.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField217.text")); // NOI18N

        jTextField218.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField218.text")); // NOI18N

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel58, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel58.text")); // NOI18N

        jTextField219.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField219.text")); // NOI18N

        jTextField220.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField220.text")); // NOI18N
        jTextField220.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField220ActionPerformed(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel59, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel59.text")); // NOI18N

        jTextField221.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField221.text")); // NOI18N

        jTextField222.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField222.text")); // NOI18N

        jTextField223.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField223.text")); // NOI18N

        jTextField224.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField224.text")); // NOI18N

        jTextField225.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField225.text")); // NOI18N

        jTextField226.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField226.text")); // NOI18N

        jTextField227.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField227.text")); // NOI18N

        jTextField228.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField228.text")); // NOI18N

        jTextField229.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField229.text")); // NOI18N

        jTextField230.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField230.text")); // NOI18N

        jTextField231.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField231.text")); // NOI18N
        jTextField231.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField231ActionPerformed(evt);
            }
        });

        jTextField232.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField232.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel166, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel166.text")); // NOI18N

        jTextField233.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField233.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel167, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel167.text")); // NOI18N

        jTextField234.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField234.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel168, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel168.text")); // NOI18N

        jTextField235.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField235.text")); // NOI18N

        jTextField236.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField236.text")); // NOI18N

        jTextField237.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField237.text")); // NOI18N

        jTextField238.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField238.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel169, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel169.text")); // NOI18N

        jTextField239.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField239.text")); // NOI18N

        jTextField240.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField240.text")); // NOI18N

        jTextField241.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField241.text")); // NOI18N

        jTextField242.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField242.text")); // NOI18N

        jTextField243.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField243.text")); // NOI18N

        jTextField244.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField244.text")); // NOI18N

        jTextField245.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField245.text")); // NOI18N

        jTextField246.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField246.text")); // NOI18N

        jTextField247.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField247.text")); // NOI18N

        jTextField248.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField248.text")); // NOI18N

        jTextField249.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField249.text")); // NOI18N

        jTextField250.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField250.text")); // NOI18N

        jTextField251.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField251.text")); // NOI18N

        jTextField252.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField252.text")); // NOI18N

        jTextField253.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField253.text")); // NOI18N

        jTextField254.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField254.text")); // NOI18N

        jTextField255.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField255.text")); // NOI18N

        jTextField256.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField256.text")); // NOI18N
        jTextField256.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField256ActionPerformed(evt);
            }
        });

        jTextField257.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField257.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel170, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel170.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel171, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel171.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel172, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel172.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel173, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel173.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel174, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel174.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel175, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel175.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel176, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel176.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel177, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel177.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel178, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel178.text")); // NOI18N

        jLabel179.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel179, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel179.text")); // NOI18N

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(299, 299, 299))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField218)
                                            .addComponent(jTextField219, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField220, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField57, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField56, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField52, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel23Layout.createSequentialGroup()
                                                .addComponent(jTextField217, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addComponent(jTextField168, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20)
                                        .addComponent(jTextField224))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField222, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField223, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField225, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField226, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField227, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField228, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField229, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextField221, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextField212, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField173, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField172, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField171, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField170, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField169, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField167, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField166, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField165, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField230, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addGap(72, 72, 72)
                                        .addComponent(jLabel68)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel79)
                                        .addGap(84, 84, 84))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField178, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField179, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField177, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField176, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField175, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField174, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jTextField59, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jTextField254, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextField238, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextField249, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField251, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField250, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField252, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField253, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel169, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel170, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel171, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jTextField191, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jTextField190, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jTextField192, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addComponent(jLabel172, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                                        .addComponent(jLabel173, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(55, 55, 55))
                                                    .addComponent(jLabel174, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel175, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(jTextField189, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                                                    .addComponent(jTextField188, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextField187, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextField186, javax.swing.GroupLayout.Alignment.LEADING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                                        .addComponent(jLabel113)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jTextField233))
                                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                                        .addComponent(jLabel114)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jTextField234))
                                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel165)
                                                            .addComponent(jLabel166))
                                                        .addGap(10, 10, 10)
                                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel179)
                                                            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jTextField216, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                                                                .addComponent(jTextField232)))
                                                        .addGap(0, 0, Short.MAX_VALUE))))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField181)
                                            .addComponent(jTextField180)
                                            .addComponent(jTextField182))
                                        .addGap(95, 95, 95)
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField194, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField193, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                                            .addComponent(jTextField195)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField185, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField183, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField184, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField213))
                                        .addGap(22, 22, 22)
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTextField256)
                                            .addComponent(jTextField255)
                                            .addComponent(jTextField257, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jPanel23Layout.createSequentialGroup()
                                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel177, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel178, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jTextField198, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                                                    .addComponent(jTextField214)))
                                            .addGroup(jPanel23Layout.createSequentialGroup()
                                                .addComponent(jLabel176, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextField197, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextField196, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                            .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jLabel82))
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel112)
                                        .addComponent(jLabel83))
                                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel115)
                                        .addComponent(jLabel116)
                                        .addComponent(jLabel117)
                                        .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel146, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel147, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel121, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel120, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel119, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addComponent(jLabel162, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel167)
                                    .addComponent(jLabel168))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField215, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jTextField211, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField210, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField209, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField247, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                            .addComponent(jTextField246)
                                            .addComponent(jTextField248)
                                            .addComponent(jTextField245))
                                        .addGap(32, 32, 32)
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel161)
                                            .addComponent(jLabel160)
                                            .addComponent(jLabel159)
                                            .addComponent(jLabel163, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel164, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTextField208, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                            .addComponent(jTextField207)
                                            .addComponent(jTextField206)
                                            .addComponent(jTextField205)
                                            .addComponent(jTextField203, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField202, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField201, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField200, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField231, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField204))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel23Layout.createSequentialGroup()
                                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jTextField199)
                                                    .addComponent(jTextField235)
                                                    .addComponent(jTextField236, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                                    .addComponent(jTextField237)
                                                    .addComponent(jTextField239)
                                                    .addComponent(jTextField240))
                                                .addGap(30, 30, 30)
                                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel148)
                                                    .addComponent(jLabel149)
                                                    .addComponent(jLabel150)
                                                    .addComponent(jLabel151)
                                                    .addComponent(jLabel152)
                                                    .addComponent(jLabel153, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel154, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(jPanel23Layout.createSequentialGroup()
                                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jTextField241)
                                                    .addComponent(jTextField242)
                                                    .addComponent(jTextField243)
                                                    .addComponent(jTextField244))
                                                .addGap(42, 42, 42)
                                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel158, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel157, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel156, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel155, javax.swing.GroupLayout.Alignment.TRAILING)))))))))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel79)
                            .addComponent(jLabel82)
                            .addComponent(jLabel179))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField186, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField199, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel83)
                    .addComponent(jLabel148)
                    .addComponent(jTextField216, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel165)
                    .addComponent(jTextField217, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField231, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel58)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField52, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField174, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField187, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField200, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel149)
                            .addComponent(jLabel112)
                            .addComponent(jTextField218, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField232, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel166)
                            .addComponent(jTextField235, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField188, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField201, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel150)
                            .addComponent(jLabel113)
                            .addComponent(jTextField175, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField233, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel167)
                            .addComponent(jTextField236, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField219, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField176, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField189, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField202, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel151)
                    .addComponent(jLabel114)
                    .addComponent(jTextField220, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField234, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel168)
                    .addComponent(jTextField237, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel152)
                    .addComponent(jLabel59))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField177, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField165, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField190, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField203, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel146)
                    .addComponent(jLabel153)
                    .addComponent(jTextField221, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField238, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel169)
                    .addComponent(jTextField239, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField166, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField178, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField191, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField204, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel147)
                    .addComponent(jLabel154)
                    .addComponent(jTextField222, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField240, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField249, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel170))
                .addGap(8, 8, 8)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField205, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel155)
                            .addComponent(jTextField241, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField206, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel156)
                            .addComponent(jTextField242, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField207, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel157)
                            .addComponent(jTextField243, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField208, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel158)
                            .addComponent(jTextField244, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField209, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel159)
                            .addComponent(jTextField245, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField192, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField179, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField250, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel171))
                            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField167, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel115)
                                .addComponent(jTextField223, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField168, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField180, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField193, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel116)
                                .addComponent(jTextField224, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField251, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel172)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField169, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField181, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField194, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel117)
                            .addComponent(jTextField225, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField252, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel173))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField182, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField170, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField226, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel118)
                                .addComponent(jTextField195, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField253, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel174)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField171, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField183, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField196, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel119)
                            .addComponent(jTextField227, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField254, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel175))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField172, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField184, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField197, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel120)
                            .addComponent(jTextField210, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel160)
                            .addComponent(jTextField228, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField246, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField255, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel176))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField173, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField185, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField198, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel121)
                            .addComponent(jTextField211, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel161)
                            .addComponent(jTextField229, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField247, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField256, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel177))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField212, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField213, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField214, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField215, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel162)
                            .addComponent(jLabel163)
                            .addComponent(jTextField230, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField248, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField257, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel178))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel164)
                        .addGap(49, 49, 49))))
        );

        jPanel13.setBackground(new java.awt.Color(204, 204, 204));

        jTextField266.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField266.text")); // NOI18N

        jTextField267.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField267.text")); // NOI18N

        jTextField268.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField268.text")); // NOI18N

        jTextField269.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField269.text")); // NOI18N

        jTextField270.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField270.text")); // NOI18N

        jTextField271.setForeground(new java.awt.Color(204, 0, 0));
        jTextField271.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField271.text")); // NOI18N

        jTextField272.setForeground(new java.awt.Color(204, 0, 0));
        jTextField272.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField272.text")); // NOI18N

        jTextField273.setForeground(new java.awt.Color(255, 0, 0));
        jTextField273.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField273.text")); // NOI18N

        jTextField274.setForeground(new java.awt.Color(204, 0, 0));
        jTextField274.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField274.text")); // NOI18N

        jTextField275.setForeground(new java.awt.Color(204, 0, 0));
        jTextField275.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField275.text")); // NOI18N

        jTextField276.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField276.text")); // NOI18N

        jTextField277.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField277.text")); // NOI18N

        jTextField278.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField278.text")); // NOI18N

        jTextField279.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField279.text")); // NOI18N

        jTextField280.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField280.text")); // NOI18N

        jTextField281.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField281.text")); // NOI18N

        jTextField282.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField282.text")); // NOI18N

        jTextField283.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField283.text")); // NOI18N

        jTextField284.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField284.text")); // NOI18N

        jTextField285.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField285.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel190, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel190.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel191, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel191.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel192, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel192.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel193, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel193.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel194, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel194.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel195, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel195.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel196, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel196.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel197, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel197.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel198, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel198.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel199, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel199.text")); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField266, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(jTextField267)
                    .addComponent(jTextField268)
                    .addComponent(jTextField269)
                    .addComponent(jTextField270))
                .addGap(71, 71, 71)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField271)
                    .addComponent(jTextField272)
                    .addComponent(jTextField273, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(jTextField274)
                    .addComponent(jTextField275))
                .addGap(26, 26, 26)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField276, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(jTextField277)
                    .addComponent(jTextField278)
                    .addComponent(jTextField279)
                    .addComponent(jTextField280))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel190, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField281, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel191, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField282, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel194, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField285, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel192, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField283, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel193, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField284, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel13Layout.createSequentialGroup()
                                    .addGap(53, 53, 53)
                                    .addComponent(jLabel195, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel196, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel197, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel198, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel199, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField266, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField271, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField276, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField281, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel190)
                    .addComponent(jLabel195))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField267, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField272, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField277, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField282, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel191)
                    .addComponent(jLabel196))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField268, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField273, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField278, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField283, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel192)
                    .addComponent(jLabel197))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField269, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField274, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField279, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField284, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel193)
                    .addComponent(jLabel198))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField270, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField275, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField280, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField285, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel194)
                    .addComponent(jLabel199)))
        );

        jPanel25.setBackground(new java.awt.Color(204, 204, 255));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel200, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel200.text")); // NOI18N

        jTextField286.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField286.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel201, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel201.text")); // NOI18N

        jTextField287.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField287.text")); // NOI18N

        jTextField288.setForeground(new java.awt.Color(204, 0, 0));
        jTextField288.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField288.text")); // NOI18N
        jTextField288.setMinimumSize(new java.awt.Dimension(236, 20));

        jTextField289.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField289.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel202, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel202.text")); // NOI18N

        jTextField290.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField290.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel203, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel203.text")); // NOI18N

        jTextField291.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField291.text")); // NOI18N

        jTextField292.setForeground(new java.awt.Color(204, 0, 0));
        jTextField292.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField292.text")); // NOI18N
        jTextField292.setMinimumSize(new java.awt.Dimension(236, 20));

        jTextField293.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField293.text")); // NOI18N

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField289, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(jTextField293))
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField288, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(jTextField292, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(48, 48, 48)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField287, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(jTextField291))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel201, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel203, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField286, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(jTextField290))
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel202, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel200, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel200)
                    .addComponent(jTextField286, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel201)
                    .addComponent(jTextField287, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField288, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField289, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel202)
                    .addComponent(jTextField290, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel203)
                    .addComponent(jTextField291, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField292, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField293, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane4.addTab(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jPanel9.TabConstraints.tabTitle"), jPanel9); // NOI18N

        jPanel19.setBackground(new java.awt.Color(204, 204, 204));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel92, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel92.text")); // NOI18N

        jTextField71.setForeground(new java.awt.Color(255, 0, 0));
        jTextField71.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField71.text")); // NOI18N

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel70, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel70.text")); // NOI18N

        jTextField70.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField70.text")); // NOI18N

        jTextField297.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField297.text")); // NOI18N

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jTextField70, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField297, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField71, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel19Layout.createSequentialGroup()
                    .addContainerGap(374, Short.MAX_VALUE)
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
                    .addComponent(jTextField70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField297, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel19Layout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(jLabel70)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTextField294.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField294.text")); // NOI18N

        jTextField295.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField295.text")); // NOI18N

        jTextField296.setText(org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jTextField296.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel63, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel63.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel204, org.openide.util.NbBundle.getMessage(pymesAdministracionTopComponent.class, "pymesAdministracionTopComponent.jLabel204.text")); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel63)
                                .addGap(207, 207, 207)
                                .addComponent(jLabel61)
                                .addGap(18, 18, 18)
                                .addComponent(ListaComercialLiq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel77)
                                .addGap(18, 18, 18)
                                .addComponent(ListaZonaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
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
                                            .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel11Layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel11Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel74))
                                            .addGroup(jPanel11Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel76)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextField296, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(74, 74, 74)
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
                                        .addGap(21, 21, 21)
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jTextField294, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel69))
                                            .addGroup(jPanel11Layout.createSequentialGroup()
                                                .addGap(127, 127, 127)
                                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel204)
                                                    .addComponent(jTextField295, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(miBarra03)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(80, 80, 80))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel61)
                                        .addComponent(ListaComercialLiq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel77)
                                        .addComponent(ListaZonaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel63)
                                        .addComponent(jLabel204))
                                    .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(3, 3, 3)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel71)
                                    .addComponent(jLabel73)
                                    .addComponent(jTextField294, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel72)
                                    .addComponent(jLabel74)
                                    .addComponent(jTextField295, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel75)
                                    .addComponent(jTextField73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel76)
                                    .addComponent(jTextField296, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botonActualizarCalculos)
                                    .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botonGeneraExelLiquida)
                                    .addComponent(jButton1))))
                        .addGap(27, 27, 27)
                        .addComponent(jLabel69)
                        .addGap(28, 28, 28)
                        .addComponent(miBarra03, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jTabbedPane4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
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
            .addComponent(miBarra02, javax.swing.GroupLayout.DEFAULT_SIZE, 1657, Short.MAX_VALUE)
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
                        .addContainerGap(1375, Short.MAX_VALUE))))
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
                .addContainerGap(393, Short.MAX_VALUE))
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
                        .addComponent(scrollPaneAreaProceso, javax.swing.GroupLayout.DEFAULT_SIZE, 1337, Short.MAX_VALUE)
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
        if (str.equals("DOC_OUT"))      this.filtroEstadoSel = 7 ;
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
        if (str.equals("ALBERTO"))              this.filtroComercial = 9 ;
        if (str.equals("AGENTE 01"))            this.filtroComercial = 10 ;
        if (str.equals("AGENTE 02"))            this.filtroComercial = 11 ;
        if (str.equals("AGENTE 03"))            this.filtroComercial = 12 ;
        if (str.equals("AGENTE 04"))            this.filtroComercial = 13 ;
        
        
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

    private void jTextField138ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField138ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField138ActionPerformed

    private void jTextField149ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField149ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField149ActionPerformed

    private void jTextField163ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField163ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField163ActionPerformed

    private void jTextField87ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField87ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField87ActionPerformed

    private void jTextField57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField57ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField57ActionPerformed

    private void jTextField175ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField175ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField175ActionPerformed

    private void jTextField178ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField178ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField178ActionPerformed

    private void jTextField193ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField193ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField193ActionPerformed

    private void jTextField199ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField199ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField199ActionPerformed

    private void jTextField220ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField220ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField220ActionPerformed

    private void jTextField231ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField231ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField231ActionPerformed

    private void jTextField256ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField256ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField256ActionPerformed

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
    private javax.swing.JComboBox jComboBox2;
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
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel197;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
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
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
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
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
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
    private javax.swing.JTextField jTextField179;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField180;
    private javax.swing.JTextField jTextField181;
    private javax.swing.JTextField jTextField182;
    private javax.swing.JTextField jTextField183;
    private javax.swing.JTextField jTextField184;
    private javax.swing.JTextField jTextField185;
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
    private javax.swing.JTextField jTextField195;
    private javax.swing.JTextField jTextField196;
    private javax.swing.JTextField jTextField197;
    private javax.swing.JTextField jTextField198;
    private javax.swing.JTextField jTextField199;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField200;
    private javax.swing.JTextField jTextField201;
    private javax.swing.JTextField jTextField202;
    private javax.swing.JTextField jTextField203;
    private javax.swing.JTextField jTextField204;
    private javax.swing.JTextField jTextField205;
    private javax.swing.JTextField jTextField206;
    private javax.swing.JTextField jTextField207;
    private javax.swing.JTextField jTextField208;
    private javax.swing.JTextField jTextField209;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField210;
    private javax.swing.JTextField jTextField211;
    private javax.swing.JTextField jTextField212;
    private javax.swing.JTextField jTextField213;
    private javax.swing.JTextField jTextField214;
    private javax.swing.JTextField jTextField215;
    private javax.swing.JTextField jTextField216;
    private javax.swing.JTextField jTextField217;
    private javax.swing.JTextField jTextField218;
    private javax.swing.JTextField jTextField219;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField220;
    private javax.swing.JTextField jTextField221;
    private javax.swing.JTextField jTextField222;
    private javax.swing.JTextField jTextField223;
    private javax.swing.JTextField jTextField224;
    private javax.swing.JTextField jTextField225;
    private javax.swing.JTextField jTextField226;
    private javax.swing.JTextField jTextField227;
    private javax.swing.JTextField jTextField228;
    private javax.swing.JTextField jTextField229;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField230;
    private javax.swing.JTextField jTextField231;
    private javax.swing.JTextField jTextField232;
    private javax.swing.JTextField jTextField233;
    private javax.swing.JTextField jTextField234;
    private javax.swing.JTextField jTextField235;
    private javax.swing.JTextField jTextField236;
    private javax.swing.JTextField jTextField237;
    private javax.swing.JTextField jTextField238;
    private javax.swing.JTextField jTextField239;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField240;
    private javax.swing.JTextField jTextField241;
    private javax.swing.JTextField jTextField242;
    private javax.swing.JTextField jTextField243;
    private javax.swing.JTextField jTextField244;
    private javax.swing.JTextField jTextField245;
    private javax.swing.JTextField jTextField246;
    private javax.swing.JTextField jTextField247;
    private javax.swing.JTextField jTextField248;
    private javax.swing.JTextField jTextField249;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField250;
    private javax.swing.JTextField jTextField251;
    private javax.swing.JTextField jTextField252;
    private javax.swing.JTextField jTextField253;
    private javax.swing.JTextField jTextField254;
    private javax.swing.JTextField jTextField255;
    private javax.swing.JTextField jTextField256;
    private javax.swing.JTextField jTextField257;
    private javax.swing.JTextField jTextField258;
    private javax.swing.JTextField jTextField259;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField260;
    private javax.swing.JTextField jTextField261;
    private javax.swing.JTextField jTextField262;
    private javax.swing.JTextField jTextField263;
    private javax.swing.JTextField jTextField264;
    private javax.swing.JTextField jTextField265;
    private javax.swing.JTextField jTextField266;
    private javax.swing.JTextField jTextField267;
    private javax.swing.JTextField jTextField268;
    private javax.swing.JTextField jTextField269;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField270;
    private javax.swing.JTextField jTextField271;
    private javax.swing.JTextField jTextField272;
    private javax.swing.JTextField jTextField273;
    private javax.swing.JTextField jTextField274;
    private javax.swing.JTextField jTextField275;
    private javax.swing.JTextField jTextField276;
    private javax.swing.JTextField jTextField277;
    private javax.swing.JTextField jTextField278;
    private javax.swing.JTextField jTextField279;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField280;
    private javax.swing.JTextField jTextField281;
    private javax.swing.JTextField jTextField282;
    private javax.swing.JTextField jTextField283;
    private javax.swing.JTextField jTextField284;
    private javax.swing.JTextField jTextField285;
    private javax.swing.JTextField jTextField286;
    private javax.swing.JTextField jTextField287;
    private javax.swing.JTextField jTextField288;
    private javax.swing.JTextField jTextField289;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField290;
    private javax.swing.JTextField jTextField291;
    private javax.swing.JTextField jTextField292;
    private javax.swing.JTextField jTextField293;
    private javax.swing.JTextField jTextField294;
    private javax.swing.JTextField jTextField295;
    private javax.swing.JTextField jTextField296;
    private javax.swing.JTextField jTextField297;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
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
           this.tablaDatos[indice][33] = jTextField1.getText();    // C SERVICIOS  
           
          this.tablaDatos[indice][39] = jTextField24.getText();     // Fecha doc out  
          this.tablaDatos[indice][34] = jTextField9.getText();      // Oferta 
          this.tablaDatos[indice][35] = jTextField17.getText();      // Campaña 
          this.tablaDatos[indice][36] = jTextField23.getText();      // Persona de contacto 
          this.tablaDatos[indice][37] = jTextField29.getText();      // Pagado
          this.tablaDatos[indice][38] = jTextField30.getText();      // Pagado Fenosa texto
          this.tablaDatos[indice][45] = jTextField31.getText();      // Pagado Fenosa total numero
          this.tablaDatos[indice][46] = jTextField6.getText();      //  Reactiva
          
          if (this.tablaDatos[indice][43] == "1" ) jCheckBox28.setSelected(true);    //   Gas
          if (this.tablaDatos[indice][43] == "2" ) jCheckBox29.setSelected(true);    //   Luz
          if (this.tablaDatos[indice][43] == "3" ) jCheckBox30.setSelected(true);    //   Dual
                       
          
          
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
                                              
                        this.tablaDatos[i][43] = "0" ;
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
                    
                    modelLoc = new DefaultTableModel();            // definimos el objeto tableModel
                    modelCer = new DefaultTableModel();            // definimos el objeto tableModel
               
                    limpiaTablaLocuciones(modelLoc);
                    miTablaLocu = new JTable();                // creamos la instancia de la tabla
                    miTablaLocu.setModel(modelLoc);
                    miBarraLocu.setViewportView(miTablaLocu);
                   
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
                    
                    if (this.tablaDatos[ireg][43].equals("1")) jCheckBox28.setSelected(true); else jCheckBox28.setSelected(false);
                    if (this.tablaDatos[ireg][43].equals("2")) jCheckBox29.setSelected(true); else jCheckBox29.setSelected(false);
                    if (this.tablaDatos[ireg][43].equals("3")) jCheckBox30.setSelected(true); else jCheckBox30.setSelected(false);
                 
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
                TableColumn col10 = miTablaCer.getColumn("Grupo_comision");
                TableColumn col11 = miTablaCer.getColumn("Grupo_consumo");
                TableColumn col12 = miTablaCer.getColumn("Año");
                TableColumn col13 = miTablaCer.getColumn("Mes");
                TableColumn col14 = miTablaCer.getColumn("Periodo");
                TableColumn col15 = miTablaCer.getColumn("ID");
                
                col15.setMinWidth(35);
                col0.setMinWidth(25);
                col1.setMinWidth(40);
                col2.setMinWidth(40);
                col3.setMinWidth(200);
                col4.setMinWidth(50);
                col5.setMinWidth(40);
                col6.setMinWidth(120);
                col7.setMinWidth(140);
                col8.setMinWidth(140);
                col9.setMinWidth(350);
                col10.setMinWidth(170);
                col11.setMinWidth(170);
                col12.setMaxWidth(30);
                col13.setMaxWidth(35);
                col14.setMaxWidth(35);
              
                miBarraCer.setViewportView(miTablaCer);
                
                // .........................................................  
                    
                    
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


            String[] titulos = { "idColor","CUPSELEC","TITULAR","CIF/NIF",
                                 "CONSUMO kwh","SVG","SVE","TARIFA","OFERTA",
                                 "OBSERVACIONES","PLANES","DIRECTRIZ" };                                                                  
           
           
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
               col2 = (String) miTablaLiquida.getValueAt(j, 1);             // cups gas
               col3 = (String) miTablaLiquida.getValueAt(j, 2);             // Titular
               col4 = (String) miTablaLiquida.getValueAt(j, 3);             // cifnif
               col5 = String.valueOf(miTablaLiquida.getValueAt(j, 4)) ;     // consumo
               col6 = String.valueOf(miTablaLiquida.getValueAt(j, 5)) ;     // svg
               col7 = String.valueOf(miTablaLiquida.getValueAt(j, 6)) ;     // sve
               col8 = (String) miTablaLiquida.getValueAt(j, 7);             // tarifa
               col9 = (String) miTablaLiquida.getValueAt(j, 8);             // oferta
               col10 = (String) miTablaLiquida.getValueAt(j, 9);             // observaciones
               col11 = String.valueOf(miTablaLiquida.getValueAt(j, 10));             // Planes
               col12 = String.valueOf(miTablaLiquida.getValueAt(j, 11));             // Directriz
                             
               // ....................................................................................
               
               celda = fila.createCell(i);  celda.setCellValue(col1);      i++;        // 
               
               celda = fila.createCell(i);  celda.setCellValue(col2);      i++;   
               celda = fila.createCell(i);  celda.setCellValue(col3);      i++;        // 
               celda = fila.createCell(i);  celda.setCellValue(col4);      i++;        // 
               celda = fila.createCell(i);  celda.setCellValue(col5);      i++;        // 
               celda = fila.createCell(i);  celda.setCellValue(col6);      i++;        //
               celda = fila.createCell(i);  celda.setCellValue(col7);      i++;        //      
               celda = fila.createCell(i);  celda.setCellValue(col8);      i++;        //
               celda = fila.createCell(i);  celda.setCellValue(col9);      i++;        // 
               celda = fila.createCell(i);  celda.setCellValue(col10);      i++;        //
               celda = fila.createCell(i);  celda.setCellValue(col11);      i++;        //
               celda = fila.createCell(i);  celda.setCellValue(col12);      i++;        //
                  
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
           
                int p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21,ptot,nReg;
                int pn1,pn2,pn3,pn4,pn5,pn6,pn7,pn8,pn9,pn10,pn11,pn12,pn13,pn14,pn15,pn16,pn17,pn18,pn19,pn20,pn21,pntot;
                int pdn1,pdn2,pdn3,pdn4,pdn5,pdn6,pdn7,pdn8,pdn9,pdn10,pdn11,pdn12,pdn13,pdn14;
                int pd1,pd2,pd3,pd4,pd5,pd6,pd7,pd8,pd9,pd10,pd11,pd12,pd13,pd14,pd15,pd16,pdtot;
                double v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,vtot,val,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21;
                double vd1,vd2,vd3,vd4,vd5,vd6,vd7,vd8,vd9,vd10,vdtot,vald,vd11,vd12,vd13,vd14;
                double vn1,vn2,vn3,vn4,vn5,vn6,vn7,vn8,vn9,vn10,vn11,vn12,vn13,vn14,vn15,vn16,vn17,vn18,vn19,vn20,vn21,vntot;
                double vdn1,vdn2,vdn3,vdn4,vdn5,vdn6,vdn7,vdn8,vdn9,vdn10,vdn11,vdn12,vdn13,vdn14 ;
                String fechaSel2 ="";
                
                String dd = jTextField40.getText() ;
                String mm = jTextField72.getText() ;
                String aa = jTextField73.getText() ;
                
                String dd2 = jTextField294.getText() ;
                String mm2 = jTextField295.getText() ;
                String aa2 = jTextField296.getText() ;
                
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
		model.addColumn("CUPS ELECTRICIDAD");
		model.addColumn("TITULAR");
                model.addColumn("CIF/NIF");
                model.addColumn("CNSUMO (kWh)");
                model.addColumn("SVG");               
                model.addColumn("SVE");
                model.addColumn("TARIFA");
                model.addColumn("OFERTA");
                model.addColumn("OBSERVACIONES");
                model.addColumn("PLANES");
                model.addColumn("DIRECTRIZ");
               
		miTablaLiquida.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		miTablaLiquida.getTableHeader().setReorderingAllowed(false);
                             
                TableColumn columna2 = miTablaLiquida.getColumn("CUPS ELECTRICIDAD");
                TableColumn columna3 = miTablaLiquida.getColumn("TITULAR");
                TableColumn columna4 = miTablaLiquida.getColumn("OBSERVACIONES");
               
            
                columna2.setMinWidth(230);
                columna3.setMinWidth(150);
                columna4.setMinWidth(250);

		PymesDao miPymesDao2 = new PymesDao();
		/*
		 * enviamos el objeto TableModel, como mandamos el objeto podemos
		 * manipularlo desde el metodo
		 */
		nReg=miPymesDao2.tablaLiquidacionesPymes(model,this.plogin,this.ppassword,fechaSel, this.filtroZonaLiq,this.filtroComercial,fechaSel2);
                this.nLiquidaciones = nReg;
                // ................................................................................................................................
                
                p1 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,1,0,100000,fechaSel, this.filtroZonaLiq,this.filtroComercial,0,fechaSel2,0);
                p2 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,2,0,5000,fechaSel, this.filtroZonaLiq,this.filtroComercial,1,fechaSel2,0);
                p3 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,3,5000,10000,fechaSel, this.filtroZonaLiq,this.filtroComercial,2,fechaSel2,0);
                p4 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,4,10000,100000,fechaSel, this.filtroZonaLiq,this.filtroComercial,3,fechaSel2,0); 
                p5 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,5,0,5000,fechaSel, this.filtroZonaLiq,this.filtroComercial,4,fechaSel2,0);
                p6 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,6,5000,10000,fechaSel, this.filtroZonaLiq,this.filtroComercial,5,fechaSel2,0);
                p7 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,7,10000,15000,fechaSel, this.filtroZonaLiq,this.filtroComercial,6,fechaSel2,0); 
                p8 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,8,15000,20000,fechaSel, this.filtroZonaLiq,this.filtroComercial,7,fechaSel2,0);
                p9 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,9,20000,50000,fechaSel, this.filtroZonaLiq,this.filtroComercial,8,fechaSel2,0);
                p10 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,10,50000,100000,fechaSel, this.filtroZonaLiq,this.filtroComercial,9,fechaSel2,0);
                p11 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,11,100000,250000,fechaSel, this.filtroZonaLiq,this.filtroComercial,10,fechaSel2,0);
                p12 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,12,250000,350000,fechaSel, this.filtroZonaLiq,this.filtroComercial,11,fechaSel2,0);
                p13 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,13,350000,450000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,0);
                p14 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,14,450000,0,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,0);
               
                p15 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,15,0,0,fechaSel, this.filtroZonaLiq,this.filtroComercial,0,fechaSel2,3);
                p16 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,16,0,0,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,3);
                
                p17 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,17,0,50000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,3);
                p18 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,18,50000,75000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,3);
                p19 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,19,0,100000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,3);
                p20 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,20,100000,250000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,3);
                p21 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,21,250000,500000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,3);
                
// ................................................................................................................................
                ptot = p1+p2+p3+p4+p5+p6+p7+p8+p9+p10+p11+p12+p13+p14+p17+p18+p19+p20+p21 ; 
                // ................................................................................................................................
               
                jTextField106.setText(String.valueOf(p2));              //  Tarifa 2.1 -2.1 DHA --- 0 - 5,000 kWh/año
                jTextField125.setText(String.valueOf(p3));              //  Tarifa 2.1 -2.1 DHA --- 5,000 - 10,000 kWh/año
                jTextField138.setText(String.valueOf(p4));              //  Tarifa 2.1 -2.1 DHA --- 10,000 - 100,000 kWh/año
                
                jTextField139.setText(String.valueOf(p5));              //  Tarifa 3.0A - 3.1A --- 0 - 5,000 kWh/año
                jTextField140.setText(String.valueOf(p6));              //  Tarifa 3.0A - 3.1A --- 5,000 - 10,000 kWh/año 
                jTextField141.setText(String.valueOf(p7));              //  Tarifa 3.0A - 3.1A --- 10,000 - 15,000 kWh/año
                jTextField77.setText(String.valueOf(p8));               //  Tarifa 3.0A - 3.1A --- 15,000 - 20,000 kWh/año
                jTextField143.setText(String.valueOf(p9));              //  Tarifa 3.0A - 3.1A --- 20,000 - 50,000 kWh/año
                jTextField144.setText(String.valueOf(p10));             //  Tarifa 3.0A - 3.1A --- 50,000 - 100,000 kWh/año
                jTextField145.setText(String.valueOf(p11));             //  Tarifa 3.0A - 3.1A --- 100,000 - 250,000 kWh/año
                jTextField146.setText(String.valueOf(p12));             //  Tarifa 3.0A - 3.1A --- 250,000 - 350,000 kWh/año
                jTextField147.setText(String.valueOf(p13));             //  Tarifa 3.0A - 3.1A --- 350,000 - 450,000 kWh/año
                jTextField115.setText(String.valueOf(p14));             //  Tarifa 3.0A - 3.1A --- 450,000 kWh/año - 
               
                jTextField137.setText(String.valueOf(p15));             //    sve
                jTextField133.setText(String.valueOf(p16));             //    svg
                
                jTextField32.setText(String.valueOf(p17));             //    Gas  (3.3) 50.000 kwha  
                jTextField33.setText(String.valueOf(p18));             //    Gas  (3.3) 75.000 kwha 
                jTextField34.setText(String.valueOf(p19));             //    Gas  (3.4) 100.000 kwha 
                jTextField35.setText(String.valueOf(p20));             //    Gas  (3.4) 250.000 kwha 
                jTextField36.setText(String.valueOf(p21));             //    Gas  (3.4) 500.000 kwha 
                
                jTextField70.setText(String.valueOf(ptot));             // Total
                            
                // ................................................................................................................................
                //                                              PLANES
                // ................................................................................................................................
                
                v1  = p1 * Double.parseDouble(jTextField58.getText());          jTextField149.setText(String.valueOf(v1)); //  Tarifa 2.0 -2.0 DHA 
            
                v2  = p2 * Double.parseDouble(jTextField60.getText());          jTextField66.setText(String.valueOf(v2)); //  Tarifa 2.1 -2.1 DHA --- 0 - 5,000 kWh/año
                v3  = p3 * Double.parseDouble(jTextField61.getText());          jTextField67.setText(String.valueOf(v3)); //  Tarifa 2.1 -2.1 DHA --- 5,000 - 10,000 kWh/año
                v4  = p4 * Double.parseDouble(jTextField62.getText());          jTextField68.setText(String.valueOf(v4)); //  Tarifa 2.1 -2.1 DHA --- 10,000 - 100,000 kWh/año 
                
                v5  = p5 * Double.parseDouble(jTextField122.getText());         jTextField110.setText(String.valueOf(v5)); //  Tarifa 3.0A - 3.1A --- 0 - 5,000 kWh/año
                v6  = p6 * Double.parseDouble(jTextField156.getText());         jTextField114.setText(String.valueOf(v6)); //  Tarifa 3.0A - 3.1A --- 5,000 - 10,000 kWh/año 
                v7  = p7 * Double.parseDouble(jTextField157.getText());         jTextField85.setText(String.valueOf(v7));//  Tarifa 3.0A - 3.1A --- 10,000 - 15,000 kWh/año
                v8  = p8 * Double.parseDouble(jTextField158.getText());         jTextField88.setText(String.valueOf(v8)); //  Tarifa 3.0A - 3.1A --- 15,000 - 20,000 kWh/año
                v9  = p9 * Double.parseDouble(jTextField159.getText());         jTextField91.setText(String.valueOf(v9)); //  Tarifa 3.0A - 3.1A --- 20,000 - 50,000 kWh/año
                v10 = p10 * Double.parseDouble(jTextField160.getText());        jTextField100.setText(String.valueOf(v10)); //  Tarifa 3.0A - 3.1A --- 50,000 - 100,000 kWh/año
                v11 = p11 * Double.parseDouble(jTextField161.getText());        jTextField101.setText(String.valueOf(v11)); //  Tarifa 3.0A - 3.1A --- 100,000 - 250,000 kWh/año
                v12 = p12 * Double.parseDouble(jTextField162.getText());        jTextField102.setText(String.valueOf(v12)); //  Tarifa 3.0A - 3.1A --- 250,000 - 350,000 kWh/año
                v13 = p13 * Double.parseDouble(jTextField163.getText());        jTextField103.setText(String.valueOf(v13)); //  Tarifa 3.0A - 3.1A --- 350,000 - 450,000 kWh/año  
                v14 = p14 * Double.parseDouble(jTextField164.getText());        jTextField118.setText(String.valueOf(v14)); //  Tarifa 3.0A - 3.1A --- 450,000 kWh/año -
                
                v15 = p15 * Double.parseDouble(jTextField135.getText());        jTextField134.setText(String.valueOf(v15)); //  sve
                v16 = p16 * Double.parseDouble(jTextField131.getText());        jTextField130.setText(String.valueOf(v16)); //  svg
                
                v17 = p17 * Double.parseDouble(jTextField44.getText());         jTextField261.setText(String.valueOf(v17)); //  Gas  (3.3) 50.000 kwha  
                v18 = p18 * Double.parseDouble(jTextField45.getText());         jTextField262.setText(String.valueOf(v18)); //  Gas  (3.3) 75.000 kwha  
                v19 = p19 * Double.parseDouble(jTextField258.getText());        jTextField263.setText(String.valueOf(v19)); //  Gas  (3.4) 100.000 kwha   
                v20 = p20 * Double.parseDouble(jTextField259.getText());        jTextField264.setText(String.valueOf(v20)); //  Gas  (3.4) 250.000 kwha   
                v21 = p21 * Double.parseDouble(jTextField260.getText());        jTextField265.setText(String.valueOf(v21)); //  Gas  (3.4) 500.000 kwha    
                
                
                // ................................................................................................................................
                //                                              DIRECTRIZ
                // ................................................................................................................................
                
                pd1 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,1,0,100000,fechaSel, this.filtroZonaLiq,this.filtroComercial,0,fechaSel2,1);
                pd2 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,2,0,5000,fechaSel, this.filtroZonaLiq,this.filtroComercial,1,fechaSel2,1);
                pd3 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,3,5000,10000,fechaSel, this.filtroZonaLiq,this.filtroComercial,2,fechaSel2,1);
                pd4 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,4,10000,100000,fechaSel, this.filtroZonaLiq,this.filtroComercial,3,fechaSel2,1); 
                pd5 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,5,0,5000,fechaSel, this.filtroZonaLiq,this.filtroComercial,4,fechaSel2,1);
                pd6 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,6,5000,10000,fechaSel, this.filtroZonaLiq,this.filtroComercial,5,fechaSel2,1);
                pd7 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,7,10000,15000,fechaSel, this.filtroZonaLiq,this.filtroComercial,6,fechaSel2,1); 
                pd8 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,8,15000,20000,fechaSel, this.filtroZonaLiq,this.filtroComercial,7,fechaSel2,1);
                pd9 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,9,20000,50000,fechaSel, this.filtroZonaLiq,this.filtroComercial,8,fechaSel2,1);
                pd10 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,10,50000,100000,fechaSel, this.filtroZonaLiq,this.filtroComercial,9,fechaSel2,1);
                pd11 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,11,100000,250000,fechaSel, this.filtroZonaLiq,this.filtroComercial,10,fechaSel2,1);
                pd12 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,12,250000,350000,fechaSel, this.filtroZonaLiq,this.filtroComercial,11,fechaSel2,1);
                pd13 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,13,350000,450000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,1);
                pd14 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,14,450000,0,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,1);
               
              //  p15 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,15,350000,450000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2);
              //  p16 = miPymesDao2.contarProductosLiquidacionesPymes(model,this.plogin,this.ppassword,16,450000,0,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2);
                
                // ................................................................................................................................
                pdtot = pd1+pd2+pd3+pd4+pd5+pd6+pd7+pd8+pd9+pd10+pd11+pd12+pd13+pd14 ; 
                 
                // ................................................................................................................................
              
                jTextField46.setText(String.valueOf(pd1));               //  Tarifa 2.0 -2.0 DHA
               
                jTextField48.setText(String.valueOf(pd2));              //  Tarifa 2.1 -2.1 DHA --- 0 - 5,000 kWh/año
                jTextField49.setText(String.valueOf(pd3));              //  Tarifa 2.1 -2.1 DHA --- 5,000 - 10,000 kWh/año
                jTextField50.setText(String.valueOf(pd4));              //  Tarifa 2.1 -2.1 DHA --- 10,000 - 100,000 kWh/año
                
                jTextField108.setText(String.valueOf(pd5));              //  Tarifa 3.0A - 3.1A --- 0 - 5,000 kWh/año
                jTextField111.setText(String.valueOf(pd6));              //  Tarifa 3.0A - 3.1A --- 5,000 - 10,000 kWh/año 
                jTextField76.setText(String.valueOf(pd7));              //  Tarifa 3.0A - 3.1A --- 10,000 - 15,000 kWh/año
                jTextField142.setText(String.valueOf(pd8));               //  Tarifa 3.0A - 3.1A --- 15,000 - 20,000 kWh/año
                jTextField78.setText(String.valueOf(pd9));              //  Tarifa 3.0A - 3.1A --- 20,000 - 50,000 kWh/año
                jTextField80.setText(String.valueOf(pd10));             //  Tarifa 3.0A - 3.1A --- 50,000 - 100,000 kWh/año
                jTextField81.setText(String.valueOf(pd11));             //  Tarifa 3.0A - 3.1A --- 100,000 - 250,000 kWh/año
                jTextField82.setText(String.valueOf(pd12));             //  Tarifa 3.0A - 3.1A --- 250,000 - 350,000 kWh/año
                jTextField83.setText(String.valueOf(pd13));             //  Tarifa 3.0A - 3.1A --- 350,000 - 450,000 kWh/año
                jTextField148.setText(String.valueOf(pd14));             //  Tarifa 3.0A - 3.1A --- 450,000 kWh/año - 
               
                jTextField297.setText(String.valueOf(pdtot));             // Total
                
                // ................................................................................................................................
             
                vd1 = pd1 * Double.parseDouble(jTextField104.getText()) ;         jTextField64.setText(String.valueOf(vd1));   //  Tarifa 2.0 -2.0 DHA               
              
                vd2  = pd2 * Double.parseDouble(jTextField63.getText());          jTextField119.setText(String.valueOf(vd2)); //  Tarifa 2.1 -2.1 DHA --- 0 - 5,000 kWh/año
                vd3  = pd3 * Double.parseDouble(jTextField65.getText());          jTextField120.setText(String.valueOf(vd3)); //  Tarifa 2.1 -2.1 DHA --- 5,000 - 10,000 kWh/año
                vd4  = pd4 * Double.parseDouble(jTextField69.getText());          jTextField121.setText(String.valueOf(vd4)); //  Tarifa 2.1 -2.1 DHA --- 10,000 - 100,000 kWh/año 
                
                vd5  = pd5 * Double.parseDouble(jTextField109.getText());         jTextField126.setText(String.valueOf(vd5)); //  Tarifa 3.0A - 3.1A --- 0 - 5,000 kWh/año
                vd6  = pd6 * Double.parseDouble(jTextField113.getText());         jTextField127.setText(String.valueOf(vd6)); //  Tarifa 3.0A - 3.1A --- 5,000 - 10,000 kWh/año 
                vd7  = pd7 * Double.parseDouble(jTextField84.getText());          jTextField128.setText(String.valueOf(vd7));//  Tarifa 3.0A - 3.1A --- 10,000 - 15,000 kWh/año
                vd8  = pd8 * Double.parseDouble(jTextField87.getText());          jTextField129.setText(String.valueOf(vd8)); //  Tarifa 3.0A - 3.1A --- 15,000 - 20,000 kWh/año
                vd9  = pd9 * Double.parseDouble(jTextField90.getText());          jTextField150.setText(String.valueOf(vd9)); //  Tarifa 3.0A - 3.1A --- 20,000 - 50,000 kWh/año
                vd10 = pd10 * Double.parseDouble(jTextField96.getText());         jTextField151.setText(String.valueOf(vd10)); //  Tarifa 3.0A - 3.1A --- 50,000 - 100,000 kWh/año
                vd11 = pd11 * Double.parseDouble(jTextField97.getText());         jTextField152.setText(String.valueOf(vd11)); //  Tarifa 3.0A - 3.1A --- 100,000 - 250,000 kWh/año
                vd12 = pd12 * Double.parseDouble(jTextField99.getText());         jTextField153.setText(String.valueOf(vd12)); //  Tarifa 3.0A - 3.1A --- 250,000 - 350,000 kWh/año
                vd13 = pd13 * Double.parseDouble(jTextField98.getText());         jTextField154.setText(String.valueOf(vd13)); //  Tarifa 3.0A - 3.1A --- 350,000 - 450,000 kWh/año  
                vd14 = pd14 * Double.parseDouble(jTextField117.getText());        jTextField155.setText(String.valueOf(vd14)); //  Tarifa 3.0A - 3.1A --- 450,000 kWh/año -
                
                // ................................................................................................................................
                // PLANES DECOMISIONADOS
                
                pn1 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,1,0,100000,fechaSel, this.filtroZonaLiq,this.filtroComercial,0,fechaSel2,0);
                pn2 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,2,0,5000,fechaSel, this.filtroZonaLiq,this.filtroComercial,1,fechaSel2,0);
                pn3 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,3,5000,10000,fechaSel, this.filtroZonaLiq,this.filtroComercial,2,fechaSel2,0);
                pn4 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,4,10000,100000,fechaSel, this.filtroZonaLiq,this.filtroComercial,3,fechaSel2,0); 
                pn5 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,5,0,5000,fechaSel, this.filtroZonaLiq,this.filtroComercial,4,fechaSel2,0);
                pn6 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,6,5000,10000,fechaSel, this.filtroZonaLiq,this.filtroComercial,5,fechaSel2,0);
                pn7 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,7,10000,15000,fechaSel, this.filtroZonaLiq,this.filtroComercial,6,fechaSel2,0); 
                pn8 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,8,15000,20000,fechaSel, this.filtroZonaLiq,this.filtroComercial,7,fechaSel2,0);
                pn9 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,9,20000,50000,fechaSel, this.filtroZonaLiq,this.filtroComercial,8,fechaSel2,0);
                pn10 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,10,50000,100000,fechaSel, this.filtroZonaLiq,this.filtroComercial,9,fechaSel2,0);
                pn11 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,11,100000,250000,fechaSel, this.filtroZonaLiq,this.filtroComercial,10,fechaSel2,0);
                pn12 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,12,250000,350000,fechaSel, this.filtroZonaLiq,this.filtroComercial,11,fechaSel2,0);
                pn13 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,13,350000,450000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,0);
                pn14 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,14,450000,0,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,0);
               
                pn15 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,15,0,0,fechaSel, this.filtroZonaLiq,this.filtroComercial,0,fechaSel2,3);
                pn16 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,16,0,0,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,3);
                
                pn17 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,17,0,50000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,3);
                pn18 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,18,50000,75000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,3);
                pn19 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,19,0,100000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,3);
                pn20 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,20,100000,250000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,3);
                pn21 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,21,250000,500000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,3);
                 
                // ................................................................................................................................
               
                jTextField47.setText(String.valueOf(pn1));          
                jTextField218.setText(String.valueOf(pn2));           
                jTextField219.setText(String.valueOf(pn3));           
                jTextField220.setText(String.valueOf(pn4));        
                jTextField221.setText(String.valueOf(pn5));           
                jTextField222.setText(String.valueOf(pn6)); 
                jTextField223.setText(String.valueOf(pn7));  
                jTextField168.setText(String.valueOf(pn8));        
                jTextField225.setText(String.valueOf(pn9)); 
                jTextField226.setText(String.valueOf(pn10)); 
                jTextField227.setText(String.valueOf(pn11)); 
                jTextField228.setText(String.valueOf(pn12)); 
                jTextField229.setText(String.valueOf(pn13)); 
                jTextField212.setText(String.valueOf(pn14)); 
                jTextField266.setText(String.valueOf(pn15)); 
                jTextField267.setText(String.valueOf(pn16)); 
                jTextField268.setText(String.valueOf(pn17)); 
                jTextField269.setText(String.valueOf(pn18)); 
                jTextField270.setText(String.valueOf(pn19)); 
                jTextField289.setText(String.valueOf(pn20)); 
                jTextField293.setText(String.valueOf(pn21)); 
                
                // ................................................................................................................................
                
                vn1  = pn1 * Double.parseDouble(jTextField186.getText());       jTextField231.setText(String.valueOf(vn1));
                vn2  = pn2 * Double.parseDouble(jTextField187.getText());       jTextField200.setText(String.valueOf(vn2));   
                vn3  = pn3 * Double.parseDouble(jTextField188.getText());       jTextField201.setText(String.valueOf(vn3));  
                vn4  = pn4 * Double.parseDouble(jTextField189.getText());       jTextField202.setText(String.valueOf(vn4));  
                vn5  = pn5 * Double.parseDouble(jTextField238.getText());       jTextField203.setText(String.valueOf(vn5));  
                vn6  = pn6 * Double.parseDouble(jTextField249.getText());       jTextField204.setText(String.valueOf(vn6));  
                vn7  = pn7 * Double.parseDouble(jTextField250.getText());       jTextField205.setText(String.valueOf(vn7)); 
                vn8  = pn8 * Double.parseDouble(jTextField251.getText());       jTextField206.setText(String.valueOf(vn8));  
                vn9  = pn9 * Double.parseDouble(jTextField252.getText());       jTextField207.setText(String.valueOf(vn9));  
                vn10 = pn10 * Double.parseDouble(jTextField253.getText());      jTextField208.setText(String.valueOf(vn10));  
                vn11 = pn11 * Double.parseDouble(jTextField254.getText());      jTextField209.setText(String.valueOf(vn11));  
                vn12 = pn12 * Double.parseDouble(jTextField255.getText());      jTextField210.setText(String.valueOf(vn12));  
                vn13 = pn13 * Double.parseDouble(jTextField256.getText());      jTextField211.setText(String.valueOf(vn13));  
                vn14 = pn14 * Double.parseDouble(jTextField257.getText());      jTextField215.setText(String.valueOf(vn14));  
                vn15 = pn15 * Double.parseDouble(jTextField276.getText());      jTextField281.setText(String.valueOf(vn15));  
                vn16 = pn16 * Double.parseDouble(jTextField277.getText());      jTextField282.setText(String.valueOf(vn16));  
                vn17 = pn17 * Double.parseDouble(jTextField278.getText());      jTextField283.setText(String.valueOf(vn17));  
                vn18 = pn18 * Double.parseDouble(jTextField279.getText());      jTextField284.setText(String.valueOf(vn18));  
                vn19 = pn19 * Double.parseDouble(jTextField280.getText());      jTextField285.setText(String.valueOf(vn19));  
                vn20 = pn20 * Double.parseDouble(jTextField287.getText());      jTextField286.setText(String.valueOf(vn20));  
                vn21 = pn21 * Double.parseDouble(jTextField291.getText());      jTextField290.setText(String.valueOf(vn21));  
                
                // ................................................................................................................................
                
               
                // DIRECTRICES DECOMISIONADOS
                
                pdn1 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,1,0,100000,fechaSel, this.filtroZonaLiq,this.filtroComercial,0,fechaSel2,1);
                pdn2 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,2,0,5000,fechaSel, this.filtroZonaLiq,this.filtroComercial,1,fechaSel2,1);
                pdn3 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,3,5000,10000,fechaSel, this.filtroZonaLiq,this.filtroComercial,2,fechaSel2,1);
                pdn4 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,4,10000,100000,fechaSel, this.filtroZonaLiq,this.filtroComercial,3,fechaSel2,1); 
                pdn5 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,5,0,5000,fechaSel, this.filtroZonaLiq,this.filtroComercial,4,fechaSel2,1);
                pdn6 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,6,5000,10000,fechaSel, this.filtroZonaLiq,this.filtroComercial,5,fechaSel2,1);
                pdn7 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,7,10000,15000,fechaSel, this.filtroZonaLiq,this.filtroComercial,6,fechaSel2,1); 
                pdn8 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,8,15000,20000,fechaSel, this.filtroZonaLiq,this.filtroComercial,7,fechaSel2,1);
                pdn9 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,9,20000,50000,fechaSel, this.filtroZonaLiq,this.filtroComercial,8,fechaSel2,1);
                pdn10 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,10,50000,100000,fechaSel, this.filtroZonaLiq,this.filtroComercial,9,fechaSel2,1);
                pdn11 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,11,100000,250000,fechaSel, this.filtroZonaLiq,this.filtroComercial,10,fechaSel2,1);
                pdn12 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,12,250000,350000,fechaSel, this.filtroZonaLiq,this.filtroComercial,11,fechaSel2,1);
                pdn13 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,13,350000,450000,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,1);
                pdn14 = miPymesDao2.contarProductosLiquidacionesNegativas(model,this.plogin,this.ppassword,14,450000,0,fechaSel, this.filtroZonaLiq,this.filtroComercial,12,fechaSel2,1);
               
                // ................................................................................................................................
               
                jTextField217.setText(String.valueOf(pdn1));          
                jTextField52.setText(String.valueOf(pdn2));           
                jTextField56.setText(String.valueOf(pdn3));           
                jTextField57.setText(String.valueOf(pdn4));        
                jTextField165.setText(String.valueOf(pdn5));           
                jTextField166.setText(String.valueOf(pdn6)); 
                jTextField167.setText(String.valueOf(pdn7));  
                jTextField224.setText(String.valueOf(pdn8));        
                jTextField169.setText(String.valueOf(pdn9)); 
                jTextField170.setText(String.valueOf(pdn10)); 
                jTextField171.setText(String.valueOf(pdn11)); 
                jTextField172.setText(String.valueOf(pdn12)); 
                jTextField173.setText(String.valueOf(pdn13)); 
                jTextField230.setText(String.valueOf(pdn14)); 
                
                // ................................................................................................................................
                
                vdn1  = pdn1 * Double.parseDouble(jTextField216.getText());       jTextField199.setText(String.valueOf(vdn1));
                vdn2  = pdn2 * Double.parseDouble(jTextField232.getText());       jTextField235.setText(String.valueOf(vdn2));   
                vdn3  = pdn3 * Double.parseDouble(jTextField233.getText());       jTextField236.setText(String.valueOf(vdn3));  
                vdn4  = pdn4 * Double.parseDouble(jTextField234.getText());       jTextField237.setText(String.valueOf(vdn4));  
                vdn5  = pdn5 * Double.parseDouble(jTextField190.getText());       jTextField239.setText(String.valueOf(vdn5));  
                vdn6  = pdn6 * Double.parseDouble(jTextField191.getText());       jTextField240.setText(String.valueOf(vdn6));  
                vdn7  = pdn7 * Double.parseDouble(jTextField192.getText());       jTextField241.setText(String.valueOf(vdn7)); 
                vdn8  = pdn8 * Double.parseDouble(jTextField193.getText());       jTextField242.setText(String.valueOf(vdn8));  
                vdn9  = pdn9 * Double.parseDouble(jTextField194.getText());       jTextField243.setText(String.valueOf(vdn9));  
                vdn10 = pdn10 * Double.parseDouble(jTextField195.getText());      jTextField244.setText(String.valueOf(vdn10));  
                vdn11 = pdn11 * Double.parseDouble(jTextField196.getText());      jTextField245.setText(String.valueOf(vdn11));  
                vdn12 = pdn12 * Double.parseDouble(jTextField197.getText());      jTextField246.setText(String.valueOf(vdn12));  
                vdn13 = pdn13 * Double.parseDouble(jTextField198.getText());      jTextField247.setText(String.valueOf(vdn13));  
                vdn14 = pdn14 * Double.parseDouble(jTextField214.getText());      jTextField247.setText(String.valueOf(vdn14));  
                
                // ................................................................................................................................
                
                
                pntot = pn1+pn2+pn3+pn4+pn5+pn6+pn7+pn8+pn9+pn10+pn11+pn12+pn13+pn14+pn15+pn16+pn17+pn18+pn19+pdn1+pdn2+pdn3+pdn4+pdn5+pdn6+pdn7+pdn8+pdn9+pdn10+pdn11+pdn12+pdn13+pdn14  ;  
                
                jTextField70.setText(String.valueOf(ptot-pntot));
                
                vntot = vn1+vn2+vn3+vn4+vn5+vn6+vn7+vn8+vn9+vn10+vn11+vn12+vn13+vn14+vn15+vn16+vn17+vn18+vn19+vn20+vn21+vdn1+vdn2+vdn3+vdn4+vdn5+vdn6+vdn7+vdn8+vdn9+vdn10+vdn11+vdn12+vdn13+vdn14 ;
                vtot = v1+v2+v3+v4+v5+v6+v7+v8+v9+v10+v11+v12+v13+v14+v15+v16+v17+v18+v19+v20+v21+vd1+vd2+vd3+vd4+vd5+vd6+vd7+vd8+vd9+vd10+vd11+vd12+vd13+vd14 ;
               
                System.out.println(" Resultados (ptot,pntot,vtot,vntot)=("+ptot+","+pntot+","+vtot+","+vntot+ ")") ;
                
                System.out.println("vtot ="+v1+"+"+v2+"+"+v3+"+"+v4+"+"+v5+"+"+v6+"+"+v7+"+"+v8+"+"+v9+"+"+v10+"+"+v11+"+"+v12+"+"+v13+"+"+v14+"+"+vd1+"+ vd2="+vd2+"+"+vd3+"+"+vd4+"+"+vd5+"+"+vd6+"+"+vd7+"+"+vd8+"+"+vd9+"+"+vd10+"+"+vd11+"+"+vd12+"+"+vd13+"+"+vd14);
                
                jTextField71.setText(String.valueOf(vtot-vntot));
              
                
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
                modelCer.addColumn("Grupo_comision");
                modelCer.addColumn("Grupo_consumo");
                modelCer.addColumn("Importe");
                modelCer.addColumn("Producto");
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
                modelCer.addColumn("Grupo_tarifa");
               
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
}

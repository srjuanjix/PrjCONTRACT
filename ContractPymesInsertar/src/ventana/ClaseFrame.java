
package ventana;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import proceso.Proceso;
import conexion.Conexion;
import dao.PymesDao;
import datechooser.beans.DateChooserDialog;
import java.awt.Color;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import vo.PymesVo;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ProgressMonitor;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import principal.Principal;



/** implements ActionListener
 * TreeSelectionListener
 * @author jab7b7
 */
 public class ClaseFrame extends javax.swing.JFrame  implements PropertyChangeListener   {
    
    // .......................................................... 
    JFileChooser fileChooser;                                                       /*Declaramos el objeto fileChooser*/
    String texto;
    File nombre = null ;
    String archivo = null;  
    public ProgressMonitor progressMonitor;
    // .......................................................... 
    public String tablaDatos[][]        = new String[2500][50];  
    public String tablaDatosIncpl[][]   = new String[2500][50];
    public String tablaDatosCmpl[][]    = new String[2500][50];
    public Integer tablaErrores[][]     = new Integer[2500][50];
    public String tablaErrorCod[][]    = new String[2500][50];
    
    public Integer tablaConfiguracion[] = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1} ;
    
    // .......................................................... 
    public int nRegistros=0;
    public int Incompletos=0 ;
    public int Completos=0 ;
    public int idt=0 ;
    public int indGen=-1;
    
      // .......................................................... 
    public String plogin    = "";
    public String ppassword = "";
    
    public int filtroEstadoSel = 0 ;
    public int filtroFechaSel = 0 ;
    
    
      
    
    // .......................................................... 
    public ClaseFrame miVentanaPrincipal;
    
      
    
    
    /**
     * Creates new form ClaseFrame
     */
    public ClaseFrame() {
                      
        initComponents();
        actualizarFormulario(-1);
         
    
        // ........................................................ Carga configuración y establece valores por defecto
        
        jCheckBox1.setSelected(false);
        
        // ........................................................
        
        /*Creamos los objetos*/
        
	fileChooser=new JFileChooser();
        crearArbol();
        
        // ........................................................
        
        /*Propiedades del boton, lo instanciamos, posicionamos y activamos los eventos*/
        
        botonAbrir.addActionListener(new ActionListener() {
           

            @Override
            public void actionPerformed(ActionEvent e) {
                   System.out.println("Acabo de capturar el evento boton abrir!!!");
                   abrirArchivo2();
                
               }
        });
        // ........................................................
          botonProcesar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                   System.out.println("Acabo de capturar el evento boton procesar!!!");
                   procesarArchivo();    
            }
        });
        // ........................................................
          botonConectar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                  
             conectarConBD();     
                   
            }
        });  
        // ........................................................
            botonCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                  
             JOptionPane.showMessageDialog(null,
            "\nVoy a CERRAR EL PROGRAMA!!",
	    "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);
		System.exit(0); 
                   
            }
        });  
        // ........................................................
              botonGuardar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                  
             guardarArchivo();    
                   
            }
        });  
        // ........................................................
               botonPuntear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                   System.out.println("Acabo de capturar el evento boton puntear!!!");       
                                                        
                   comprobarRegistros();
                   modificarArbol();
            }
        });
        // ........................................................
              botonModificar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                   System.out.println("Acabo de capturar el evento boton Modificar!!!");       
                   ActualizarTablaFormulario();
                   
            }
        });
        // ........................................................
             botonRefrescar.addActionListener(new ActionListener() {
           
            @Override
            public void actionPerformed(ActionEvent e) {
                   System.out.println("Acabo de capturar el evento boton Refrescar Tabla BD!!!");
                   // conectarConBD();   
                   RefrescarTablaBD();
                
               }
        });
        // ........................................................
             botonGuardaExel.addActionListener(new ActionListener() {
           
            @Override
            public void actionPerformed(ActionEvent e) {
                   System.out.println("Acabo de capturar el evento boton Guarda Exel!!!");
                   GuardarArchivoExelResultados();   
                   GuardarArchivoExelDevueltos();
                
               }
        });
        // ........................................................
        botonActualizaFecha.addActionListener(new ActionListener() {
           
         @Override
            public void actionPerformed(ActionEvent e) {
                   System.out.println("Acabo de capturar el evento boton Actualizar Fecha!!!");
                   ActualizaFechaProduccion();   
                
               }
        });
         // ........................................................
        botonGuardaConf.addActionListener(new ActionListener() {
           
         @Override
            public void actionPerformed(ActionEvent e) {
                   System.out.println("Acabo de capturar el evento boton Guardar configuración");
                   ActualizaConfiguracion();   
                
               }
        });
         // ........................................................
        botonCalendario.addActionListener(new ActionListener() {
           
         @Override
            public void actionPerformed(ActionEvent e) {
                   System.out.println("Acabo de capturar el evento boton Calendario!!!");
                  
                    DateChooserDialog dcd=null;
                    dcd=new DateChooserDialog();
                    dcd.setModal(true);
                    dcd.setSelectedDate(null);
                    dcd.showDialog(null);


                    Calendar cal=dcd.getSelectedDate();
                    if (cal!=null) {
                    int day=cal.get(Calendar.DAY_OF_MONTH);
                    int mes=cal.get(Calendar.MONTH);
                    int year=cal.get(Calendar.YEAR);
                    String fecha=day+"/"+(mes+1)+"/"+year;
                    System.out.println(fecha);

                    jTextField28.setText(fecha);


                    } else {

                    jTextField28.setText(null);

                    }
                
               }
        });
                
        
        // ........................................................
        // ----------------------------------------------------------------------------------------------------------------------------
               botonNuevo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                   System.out.println("Acabo de capturar el evento boton AÑADIR NUEVO!!!");       
                                                        
                //   comprobarRegistros();
                   insertarNuevo();
                   modificarArbolNuevos();   
            }
        });
        // ----------------------------------------------------------------------------------------------------------------------------
               
        // ........................................................
               actuaMunicipio.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                   System.out.println("Acabo de capturar el evento boton actualizar municipio!!!");       
                                                        
                   cambiaMunicipio();
                  
            }
        });
        // ........................................................
        // ........................................................
        // Accion a realizar cuando el JComboBox cambia de item seleccionado.
	/*
        combo.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
			tf.setText(combo.getSelectedItem().toString());
	}
	});
        */
    }

   
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel = new javax.swing.JLabel();
        botonAbrir = new javax.swing.JButton();
        botonGuardar = new javax.swing.JButton();
        botonProcesar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        botonPuntear = new javax.swing.JButton();
        botonConectar = new javax.swing.JButton();
        login = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        arbol = new javax.swing.JTree();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel29 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        actuaMunicipio = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jTextField27 = new javax.swing.JTextField();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        botonNuevo = new javax.swing.JButton();
        botonModificar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jCheckBox24 = new javax.swing.JCheckBox();
        jCheckBox25 = new javax.swing.JCheckBox();
        jCheckBox26 = new javax.swing.JCheckBox();
        jCheckBox27 = new javax.swing.JCheckBox();
        jCheckBox28 = new javax.swing.JCheckBox();
        jComboBox2 = new javax.swing.JComboBox();
        jPanel8 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jCheckBox17 = new javax.swing.JCheckBox();
        jCheckBox18 = new javax.swing.JCheckBox();
        jCheckBox19 = new javax.swing.JCheckBox();
        jCheckBox20 = new javax.swing.JCheckBox();
        jCheckBox21 = new javax.swing.JCheckBox();
        jCheckBox22 = new javax.swing.JCheckBox();
        jCheckBox23 = new javax.swing.JCheckBox();
        jCheckBox29 = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        scrollPaneArea = new javax.swing.JScrollPane();
        areaDeTexto = new javax.swing.JTextArea();
        scrollPaneAreaProceso = new javax.swing.JScrollPane();
        areaDeTextoProcesado = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        miBarra01 = new javax.swing.JScrollPane();
        miTabla01 = new javax.swing.JTable();
        ListaTiempo = new javax.swing.JComboBox();
        botonRefrescar = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
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
        jLabel31 = new javax.swing.JLabel();
        passw = new javax.swing.JPasswordField();
        botonGuardaExel = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jTextField28 = new javax.swing.JTextField();
        botonActualizaFecha = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        botonCalendario = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel.setText("PROCESADO DE PLANTILLAS COMERCIALES");

        botonAbrir.setText("Abrir");

        botonGuardar.setText("Insertar");

        botonProcesar.setText("Procesar");

        botonCancelar.setText("Salir");

        botonPuntear.setText("Puntear");

        botonConectar.setText("CONECTAR BD");

        login.setText("admin02");

        jLabel30.setText("Login:");

        arbol.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        arbol.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                arbolValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(arbol);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel29.setText("OBSERVACIONES");

        jLabel9.setText("CONSUMO ELECTRICO:");

        jTextField18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField18ActionPerformed(evt);
            }
        });

        jLabel12.setText("CONSUMO GAS:");

        jLabel28.setText("PERS. CONTACTO");

        jLabel21.setText("FECHA FIRMA CLIENTE");

        jLabel27.setText("TELÉFONO");

        jLabel14.setText("TARIFA GAS");

        jLabel22.setText("TARIFA ELÉCTRICA");

        jLabel20.setText("NIF / CIF");

        jLabel19.setText("TITULAR                    ");

        jLabel18.setText("DIRECCIÓN                ");

        jLabel17.setText("PROVINCIA               ");

        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });

        jLabel16.setText("MUNICIPIO               ");

        actuaMunicipio.setText("<");

        jLabel41.setForeground(new java.awt.Color(204, 102, 0));
        jLabel41.setText("Municipio");

        jLabel15.setText("CÓDIGO POSTAL      ");

        jLabel13.setText("AGENTE");

        jLabel11.setText("CUPS GAS                 ");

        jLabel10.setText("CUPS ELECTRICIDAD");

        jLabel38.setText("FECHA DE PRODUCCIÓN:");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PENDIENTE", "AUTORIZADO", "CERTIFICADO", " " }));

        jLabel7.setText("ESTADO");

        jLabel8.setText("INCIDENCIA:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BLANCO", "AMARILLO", "NARANJA", "ROJO", "VERDE" }));

        jLabel24.setForeground(new java.awt.Color(102, 102, 102));
        jLabel24.setText("dd-mm-aa");

        jLabel25.setText("COMERCIAL");

        jLabel32.setForeground(new java.awt.Color(153, 153, 153));
        jLabel32.setText("dd-mm-aa");

        jLabel33.setText("EMPRESA ORIGEN");

        jTextField8.setText("jTextField8");

        jLabel34.setText("AUTONOMO");

        jTextField9.setText("jTextField9");

        jLabel35.setText("CAMPAÑA");

        jTextField17.setText("jTextField17");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(274, 274, 274))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel28))
                                .addGap(14, 14, 14)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(73, 73, 73)
                                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel22)
                                                .addComponent(jLabel14))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jTextField1)
                                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel32))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(actuaMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel41))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel25)
                                            .addGap(42, 42, 42)
                                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(24, 24, 24)))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(88, 88, 88)
                                                .addComponent(jLabel12))
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel34)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap(85, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel24))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel33)
                                            .addComponent(jLabel35))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField8)
                                            .addComponent(jTextField17, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel33)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25)))
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(actuaMunicipio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel27)
                                .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel14)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)
                            .addComponent(jLabel32))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        botonNuevo.setText("AÑADIR NUEVO");

        botonModificar.setText("Modificar");

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));

        jCheckBox24.setText("SWG");

        jCheckBox25.setText("SWE");

        jCheckBox26.setText("Dual Fuel");

        jCheckBox27.setText("GAS TUR");

        jCheckBox28.setText("TARIFA PLANA");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NINGUNO...", "SVG Completo", "SVG Xpress", "SVG Básico", "SVElectric Xpress", "SERVI HOGAR", "SVG CON Calefacción", "SVG SIN Calefacción" }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(jCheckBox24)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox25)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox26)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox27)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox24)
                    .addComponent(jCheckBox25)
                    .addComponent(jCheckBox26)
                    .addComponent(jCheckBox27)
                    .addComponent(jCheckBox28)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("SERVICIOS CONTRATADOS");

        jCheckBox17.setText("SWG");

        jCheckBox18.setText("SVG Xpres");

        jCheckBox19.setText("SVG Básico");

        jCheckBox20.setText("SV Electric Xpres");

        jCheckBox21.setText("Servi Hogar");

        jCheckBox22.setText("SVG Completo CON Calefacción");

        jCheckBox23.setText("SVG Completo SIN Calefacción");

        jCheckBox29.setText("SPP Servicio Protección de Pagos");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox17)
                    .addComponent(jCheckBox18)
                    .addComponent(jCheckBox19)
                    .addComponent(jCheckBox20)
                    .addComponent(jCheckBox21)
                    .addComponent(jCheckBox22)
                    .addComponent(jCheckBox23)
                    .addComponent(jLabel23)
                    .addComponent(jCheckBox29))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox17)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox18)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox19)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox20)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox21)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox22)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox23)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox29)
                .addContainerGap(164, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(168, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonModificar)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("ADMINISTRACION", jPanel3);

        areaDeTexto.setColumns(20);
        areaDeTexto.setRows(5);
        scrollPaneArea.setViewportView(areaDeTexto);

        areaDeTextoProcesado.setColumns(20);
        areaDeTextoProcesado.setRows(5);
        scrollPaneAreaProceso.setViewportView(areaDeTextoProcesado);

        jLabel6.setText("Log de acciones");

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
                        .addComponent(scrollPaneAreaProceso, javax.swing.GroupLayout.DEFAULT_SIZE, 1061, Short.MAX_VALUE)
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
                .addContainerGap(88, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ARCHIVOS", jPanel1);

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
        miBarra01.setViewportView(miTabla01);

        ListaTiempo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Última semana", "Último mes", "Último año", "Todos" }));
        ListaTiempo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListaTiempoActionPerformed(evt);
            }
        });

        botonRefrescar.setText("Refrescar consulta");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PENDIENTES", "AUTORIZADOS", "CERTIFICADOS", "TODOS" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(miBarra01, javax.swing.GroupLayout.DEFAULT_SIZE, 1361, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 1008, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ListaTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botonRefrescar)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ListaTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonRefrescar)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(miBarra01, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        jTabbedPane1.addTab("BASE DE DATOS", jPanel5);

        jLabel1.setText("Número de líneas a procesar:");

        numLineas.setText("500");

        jLabel2.setText("Número de línea de comienzo:");

        nComienzo.setText("1");

        jLabel36.setText("ANÁLISIS AUTOMÁTICO:");

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("CUPS Electrico");

        jCheckBox2.setSelected(true);
        jCheckBox2.setText("CUPS Gas");

        jCheckBox3.setSelected(true);
        jCheckBox3.setText("Agente");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jCheckBox4.setSelected(true);
        jCheckBox4.setText("Código Postal");

        jCheckBox5.setSelected(true);
        jCheckBox5.setText("Municipio");
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });

        jCheckBox6.setSelected(true);
        jCheckBox6.setText("Provincia");

        jCheckBox7.setSelected(true);
        jCheckBox7.setText("Dirección");

        jCheckBox8.setSelected(true);
        jCheckBox8.setText("Titular");

        jCheckBox9.setSelected(true);
        jCheckBox9.setText("Nif o Cif");

        jCheckBox10.setSelected(true);
        jCheckBox10.setText("Fecha de firma");

        jCheckBox11.setSelected(true);
        jCheckBox11.setText("Autonomo");

        jCheckBox12.setSelected(true);
        jCheckBox12.setText("Oferta");

        jCheckBox13.setSelected(true);
        jCheckBox13.setText("Tarifa");

        jCheckBox14.setSelected(true);
        jCheckBox14.setText("Campaña");

        jCheckBox15.setSelected(true);
        jCheckBox15.setText("Teléfono contacto");

        jCheckBox16.setSelected(true);
        jCheckBox16.setText("Persona de contacto");

        jLabel4.setText("Cadena de conexión BD:");

        jTextField25.setText("jdbc:mysql://localhost/");

        jLabel5.setText("Base de datos                :");

        jTextField26.setText("sernovenDB");

        botonGuardaConf.setText("Guardar Configuracion");

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
                        .addContainerGap(1046, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                            .addComponent(jCheckBox14)
                            .addComponent(jCheckBox13)
                            .addComponent(jCheckBox12)
                            .addComponent(jCheckBox16)
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
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jTextField26))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                .addContainerGap(554, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel36)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox4)
                    .addComponent(jCheckBox13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox5)
                    .addComponent(jCheckBox14))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numLineas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nComienzo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77))
        );

        jTabbedPane1.addTab("CONFIGURACION", jPanel2);

        jLabel31.setText("Password:");

        passw.setText("admin02");

        botonGuardaExel.setText("Guardar");

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ventana/logo.JPG"))); // NOI18N

        jLabel39.setText("FECHA DE PRODUCCIÓN:");

        jTextField28.setText(" ");

        botonActualizaFecha.setText("ACTUALIZAR");
        botonActualizaFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizaFechaActionPerformed(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel40.setText("PYMES");

        botonCalendario.setText("Cal");

        jLabel26.setText("Version 1.25");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(botonAbrir, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(botonProcesar))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel30)
                                        .addGap(3, 3, 3)
                                        .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel31)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(botonPuntear, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                    .addComponent(passw)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField28)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(botonConectar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(botonCalendario)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(botonActualizaFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(botonGuardar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(botonGuardaExel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(botonCancelar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel40)))
                                .addGap(58, 58, 58)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel)
                            .addComponent(jLabel37)))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonConectar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(passw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonActualizaFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonAbrir)
                            .addComponent(botonProcesar)
                            .addComponent(botonPuntear)
                            .addComponent(botonGuardar)
                            .addComponent(botonCancelar)
                            .addComponent(botonGuardaExel)
                            .addComponent(jLabel40)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel37)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addGap(379, 379, 379))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void arbolValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_arbolValueChanged
        // TODO add your handling code here:
         System.out.println("Acabo de capturar el evento!!!");
                
    }//GEN-LAST:event_arbolValueChanged

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void botonActualizaFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizaFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonActualizaFechaActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String str;
        str = jComboBox1.getSelectedItem().toString()  ;
        System.out.println("Acabo de capturar el comboBox 1!!! Selecciono="+str);
        
        if (str.equals("PENDIENTES"))   this.filtroEstadoSel = 0 ;
        if (str.equals("AUTORIZADOS"))  this.filtroEstadoSel = 1 ;
        if (str.equals("CERTIFICADOS")) this.filtroEstadoSel = 2 ;
        if (str.equals("TODOS"))        this.filtroEstadoSel = 3 ;
        
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void ListaTiempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListaTiempoActionPerformed
        // TODO add your handling code here:
        String str;
        str = ListaTiempo.getSelectedItem().toString()  ;
        System.out.println("Acabo de capturar el comboBox 2!!! Selecciono="+str);
        
        if (str.equals("Última semana"))   this.filtroFechaSel = 0 ;
        if (str.equals("Último mes"))      this.filtroFechaSel = 1 ;
        if (str.equals("Último año"))      this.filtroFechaSel = 2 ;
        if (str.equals("Todos"))           this.filtroFechaSel = 3 ;
     
    }//GEN-LAST:event_ListaTiempoActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField18ActionPerformed

    /**
     * @param args the command line arguments
     */   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox ListaTiempo;
    private javax.swing.JButton actuaMunicipio;
    private javax.swing.JTree arbol;
    private javax.swing.JTextArea areaDeTexto;
    private javax.swing.JTextArea areaDeTextoProcesado;
    private javax.swing.JButton botonAbrir;
    private javax.swing.JButton botonActualizaFecha;
    private javax.swing.JButton botonCalendario;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonConectar;
    private javax.swing.JButton botonGuardaConf;
    private javax.swing.JButton botonGuardaExel;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JButton botonModificar;
    private javax.swing.JButton botonNuevo;
    private javax.swing.JButton botonProcesar;
    private javax.swing.JButton botonPuntear;
    private javax.swing.JButton botonRefrescar;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox15;
    private javax.swing.JCheckBox jCheckBox16;
    private javax.swing.JCheckBox jCheckBox17;
    private javax.swing.JCheckBox jCheckBox18;
    private javax.swing.JCheckBox jCheckBox19;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox20;
    private javax.swing.JCheckBox jCheckBox21;
    private javax.swing.JCheckBox jCheckBox22;
    private javax.swing.JCheckBox jCheckBox23;
    private javax.swing.JCheckBox jCheckBox24;
    private javax.swing.JCheckBox jCheckBox25;
    private javax.swing.JCheckBox jCheckBox26;
    private javax.swing.JCheckBox jCheckBox27;
    private javax.swing.JCheckBox jCheckBox28;
    private javax.swing.JCheckBox jCheckBox29;
    private javax.swing.JCheckBox jCheckBox3;
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
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField login;
    private javax.swing.JScrollPane miBarra01;
    private javax.swing.JTable miTabla01;
    private javax.swing.JTextField nComienzo;
    private javax.swing.JTextField numLineas;
    private javax.swing.JPasswordField passw;
    private javax.swing.JScrollPane scrollPaneArea;
    private javax.swing.JScrollPane scrollPaneAreaProceso;
    // End of variables declaration//GEN-END:variables
      
     /**
        * @param miVentana
         * Enviamos una instancia de la ventana principal
         */
    public void setVentanaPrincipal(ClaseFrame miVentana)  {
              miVentanaPrincipal=miVentana;
    }
        
        public void actionPerformed(ActionEvent evento) {
        // .......................................................................
        if (evento.getSource()==botonAbrir)
	{
	    this.nombre=abrirArchivo();
                        
            try {           
                this.archivo = mostrarArchivo(this.nombre);
            } catch (IOException ex) {
                Logger.getLogger(ClaseFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            areaDeTexto.setText(this.archivo);                
                       
	}
	// .......................................................................		
	if (evento.getSource()==botonGuardar)
	{
		guardarArchivo();
	}
        // .......................................................................
        if (evento.getSource()==botonProcesar)
        {
            // LLama a función procesar archivo
            JOptionPane.showMessageDialog(null,
            "\nVoy a procesar el archivo!! nombre="+this.nombre,
	    "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);
          
            Proceso myProces ;             // Hacemos una instancia de la clase de lectura de datos desde CSV
            myProces = new Proceso();
            
      //      int p = Integer.parseInt(nComienzo.getText());
      //      int n = Integer.parseInt(numLineas.getText());

                int p = 2 ;
                int n = 100 ;
            try {  

                 this.texto = myProces.ProcesaDatosReales(this.nombre,n,p);
                 this.tablaDatos = myProces.tablaDatos ;
                 this.nRegistros = myProces.nRegistros ;
                 areaDeTextoProcesado.setText(this.texto);         
                 modificarArbolNuevos();      
                 actualizarFormulario(0) ;

            } catch (IOException ex) {

                 Logger.getLogger(ClaseFrame.class.getName()).log(Level.SEVERE, null, ex);

            }  
            
        }
        // .......................................................................
        if (evento.getSource()==botonCancelar)
	{
            JOptionPane.showMessageDialog(null,
            "\nVoy a CERRAR EL PROGRAMA!!",
	    "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);
		System.exit(0); 
	}
        // .......................................................................		
	if (evento.getSource()==botonPuntear)
	{
         
              JOptionPane.showMessageDialog(null,
            "\nVoy a Puntear los registros!!",
	    "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);
              
           
            comprobarRegistros();
            modificarArbol();
            
	}
        // .......................................................................		
	if (evento.getSource()==botonConectar)
	{
                String Str1 = login.getText();
                String Str2 = passw.getText();
		conectarBD(Str1,Str2);
	}
        // .......................................................................
        		
	if (evento.getSource()==botonGuardaExel)
	{
               
		GuardarArchivoExelResultados();
	}
        // .......................................................................
        
        
        
    }
    
    /**
		 * Permite mostrar la ventana que carga 
		 * archivos en el area de texto
		 * @return
		 */
private File abrirArchivo() {
    fileChooser.showOpenDialog(this);
        File abre=fileChooser.getSelectedFile();
        JOptionPane.showMessageDialog(null,
                "\nEl nombre del archivo es:"+abre,
                "AVISO",JOptionPane.WARNING_MESSAGE);
	return abre;
}
 // .......................................................................
private void abrirArchivo2() {
    fileChooser.showOpenDialog(this);
        File abre=fileChooser.getSelectedFile();
        JOptionPane.showMessageDialog(null,
                "\nEl nombre del archivo es:"+abre,
                "AVISO",JOptionPane.WARNING_MESSAGE);
        
        this.nombre = abre ;
        
          try {           
                this.archivo = mostrarArchivo(this.nombre);
            } catch (IOException ex) {
                Logger.getLogger(ClaseFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            areaDeTexto.setText(this.archivo);            
        
        
	return;
}
 // .......................................................................
private void procesarArchivo() {
     // LLama a función procesar archivo
            JOptionPane.showMessageDialog(null,
            "\nVoy a procesar el archivo!! nombre="+this.nombre,
	    "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);
          
            Proceso myProces ;             // Hacemos una instancia de la clase de  de proceso de registros
            myProces = new Proceso(this.tablaConfiguracion);
                 
                int p = 2 ;
                int n = 2500 ;                 // ojo!!! valores de prueba, quitar!!!!
            try {  

                 this.texto = myProces.ProcesaDatosReales(this.nombre,n,p);
                 this.tablaDatos = myProces.tablaDatos ;
                 this.nRegistros = myProces.nRegistros ;  
                 this.texto = myProces.sMensajes ;
       
                 areaDeTextoProcesado.setText(this.texto);         
                 modificarArbolNuevos();      
                 actualizarFormulario(0) ;

            } catch (IOException ex) {

                 Logger.getLogger(ClaseFrame.class.getName()).log(Level.SEVERE, null, ex);

            }  
    
    
}

private void comprobarRegistros() {
    
            Proceso myProces ;             // Hacemos una instancia de la clase de proceso de registros
            myProces = new Proceso(this.tablaConfiguracion);
         
            progressMonitor = new ProgressMonitor(null,
                                  "Punteando automáticamente registros",
                                  "", 0, 100);
                   
            
             progressMonitor.setProgress(0);
            
            
     
            myProces.addPropertyChangeListener(this);
            myProces.execute();
            myProces.punteaRegistros(this.nRegistros,this.tablaDatos,this.plogin,this.ppassword);
                   
            this.Incompletos = myProces.nIncompletos ; 
            this.Completos   = myProces.nCompletos;
            
            this.tablaDatosIncpl    = myProces.tablaRes1 ;
            this.tablaDatosCmpl     = myProces.tablaRes2 ;
            this.tablaErrores       = myProces.tablaErrores;
            this.tablaErrorCod      = myProces.tablaErrorCod ;
                     
           this.texto = myProces.sMensajes ;       
           areaDeTextoProcesado.setText(this.texto);  
           
            actualizarFormulario(-1);
    
}
 // .......................................................................
private void conectarConBD(){
    
             String Str1 = login.getText();
             String Str2 = passw.getText();
             conectarBD(Str1,Str2);    
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
        int i, nStr,estadoInsert,nFecha=0;
        int L,G,D,s1,s2,s3,s4,s5,s6,s7,s9,tg,tp,cnt=0 ;
        String str,sFecha="";
       
        estadoInsert = 0 ;
            
            PymesDao miPymeDao = new PymesDao();
            
        //    try {
                      System.out.println("Voy a comenzar la inserción ");
                PymesVo miPymes = new PymesVo();
                 Conexion conex = new Conexion(this.plogin,this.ppassword);
                for (i=0 ; i < this.nRegistros; i++){
                    
                        // ..............................................
                        
                        this.tablaDatos[i][0] = "0" ;                   // Fijamos el estado como pendiente
                        // ..............................................
                      
                        
                        if  (!this.tablaDatos[i][4].equals("") ) L=1 ; else L = 0 ;
           
                        if  (!this.tablaDatos[i][3].equals("") ) G=1; else G = 0 ;
           
                        if  (!this.tablaDatos[i][3].equals("") && !this.tablaDatos[i][4].equals("") ) {L=0; G = 0; D=1;} else D = 0 ;
           
                        
                        
                        
                        // ..............................................
                        
                        if (this.tablaDatos[i][30].equals("1") )  s1=1; else s1=0 ;         // SVGComplet
        
                        if (this.tablaDatos[i][31].equals("1") )  s2=1; else s2=0 ;          // SVGXpres
        
                        if (this.tablaDatos[i][32].equals("1") )   s3=1; else s3=0 ;         // SVG Básico
        
                        if (this.tablaDatos[i][33].equals("1") )   s4=1; else s4=0 ;         //SVELECTRIC XPRES
        
                        if (this.tablaDatos[i][34].equals("1") )   s5=1; else s5=0 ;  
                       
                        if (this.tablaDatos[i][35].equals("1") )   s6=1; else s6=0 ;         //SVG CON CALEFACCION
        
                        if (this.tablaDatos[i][36].equals("1") )   s7=1; else s7=0 ;         // SVG SIN CALEFACCION
                        
                     
                        if (this.tablaDatos[i][43].equals("1") )   tg=1; else tg=0 ;         // tur gas
                        if (this.tablaDatos[i][44].equals("1") )   tp=1; else tp=0 ;         // tarifa plana
                        
                        if (this.tablaDatos[i][45].equals("1") )   s9=1; else s9=0 ;         // SPP
                        
                        
                        // ...........................................................................................
                        
                        System.out.println("Insertando registro i= "+i+ " - Incidencia= "+this.tablaDatos[i][1]+"  Comercial ="+this.tablaDatos[i][36]);
                                           
                        miPymes.setEstado(Integer.parseInt(this.tablaDatos[i][37]));
                        miPymes.setIncidencia(Integer.parseInt(this.tablaDatos[i][38]));
                        
                        sFecha = dateToMySQLDate(this.tablaDatos[i][2]);
                        miPymes.setFecha(sFecha.trim());
                       
                        str = this.tablaDatos[i][17]; str = str.replace("'"," ") ; 
                        
                        miPymes.setComercial(str);
                        
                        miPymes.setSwg(G);
                        miPymes.setSwe(L);
                        miPymes.setDualFuel(D);
                                                
                        miPymes.setCupsE(this.tablaDatos[i][4]);
                        miPymes.setCupsG(this.tablaDatos[i][3]);     
                        
                        str = this.tablaDatos[i][5]; str = str.trim();
                        try {                      
                            miPymes.setCodPostal(Integer.parseInt(str));
                          } catch (NumberFormatException nfe){
                            miPymes.setCodPostal(0);
                          }
                        str = this.tablaDatos[i][6]; str = str.replace("'"," ") ; 
                        
                        System.out.println("Inserto municipio ="+str);
                        
                        miPymes.setMunicipio(str);
                        
                        str = this.tablaDatos[i][7]; str = str.replace("'"," ") ; 
                        miPymes.setProvincia(str);
                        
                        str = this.tablaDatos[i][8]; str = str.replace("'"," ") ; 
                        miPymes.setDireccion(str);
                        
                        str = this.tablaDatos[i][9]; str = str.replace("'"," ") ; 
                        miPymes.setTitular(str);
                        
                        miPymes.setNifCif(this.tablaDatos[i][10]);
                        miPymes.setTelefonoCli(this.tablaDatos[i][16]);  
                        
                        sFecha =this.tablaDatos[i][11].trim();
                        nFecha = sFecha.length();
                        if (nFecha>0 && nFecha==8){
                            sFecha = dateToMySQLDate(this.tablaDatos[i][11]);                       
                             miPymes.setFechaFirma(sFecha);
                        } else {
                            sFecha = "2014-01-01 00:00:00";
                            miPymes.setFechaFirma(sFecha.trim());
                        }
                        
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
                        miPymes.setSVG_6(s6);            
                        miPymes.setSVG_7(s7);   
                        miPymes.setSVG_9(s9);  
                        
                        miPymes.setTurGas(tg);      
                        miPymes.setTarifaPlana(tp);  
                        miPymes.setPunteado(0);      
                              
                        miPymes.setObservaciones(this.tablaDatos[i][15]); 
                        
                        miPymes.setTarifaGas(this.tablaDatos[i][19]);
                        miPymes.setTarifaElec(this.tablaDatos[i][18]);
                           
                        miPymes.setAgenteComercial(this.tablaDatos[i][39]); 
                        
                        
               //         estadoInsert = miPymeDao.registrarContrato(miPymes,this.plogin,this.ppassword);
                        estadoInsert = miPymeDao.registrarContrato(miPymes,conex);
                        System.out.println("Registro insertado ");
                        
                        if (estadoInsert == 0 ) cnt++ ;
                        
                }
                	conex.desconectar();
                 if (estadoInsert == 0) {
                    
                                         JOptionPane.showMessageDialog(null,
					"Se ha registrado Exitosamente ("+cnt+") contratos", "Información",
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
              DefaultMutableTreeNode carpetaRaiz = new DefaultMutableTreeNode("REGISTROS");
             /**Definimos el modelo donde se agregaran los nodos*/
              DefaultTreeModel modelo2;
              modelo2 = new DefaultTreeModel(carpetaRaiz);
             /**agregamos el modelo al arbol, donde previamente establecimos la raiz*/
             arbol = new JTree(modelo2);
             jScrollPane1.setViewportView(arbol);
             
             /**definimos los eventos*/
            // arbol.getSelectionModel().addTreeSelectionListener((TreeSelectionListener) this); 
           
        
             /**Definimos mas nodos del arbol y se lo agregamos al modelo*/
         
             DefaultMutableTreeNode carpeta2 = new DefaultMutableTreeNode("NUEVOS (0)");             
             DefaultMutableTreeNode carpeta3 = new DefaultMutableTreeNode("INCOMPLETOS (0)");             
             DefaultMutableTreeNode carpeta4 = new DefaultMutableTreeNode("PUNTEADOS (0)");
       
             /**Definimos donde se agrega el nodo, dentro de que rama y que posicion*/          
             
             modelo2.insertNodeInto(carpeta2, carpetaRaiz, 0);
             modelo2.insertNodeInto(carpeta3, carpetaRaiz, 1);
             modelo2.insertNodeInto(carpeta4, carpetaRaiz, 2);          
                         
}
       public final void modificarArbolNuevos() {
           int i,cnt,nCUPS;
           String CUPS ="";
           
           cnt = this.nRegistros ; 
                      
           System.out.println("Voy a modificar el arbol nuevo ");
           System.out.println("El numero de registro es="+cnt);
           
           DefaultMutableTreeNode carpetaRaiz = new DefaultMutableTreeNode("REGISTROS");
           /**Definimos el modelo donde se agregaran los nodos*/
           DefaultTreeModel modelo2;
           modelo2 = new DefaultTreeModel(carpetaRaiz);
           /**agregamos el modelo al arbol, donde previamente establecimos la raiz*/
           
           arbol = new JTree(modelo2);
           jScrollPane1.setViewportView(arbol);
           
           /**Definimos mas nodos del arbol y se lo agregamos al modelo*/
           DefaultMutableTreeNode carpeta2 = new DefaultMutableTreeNode("NUEVOS ("+cnt+")");       
           DefaultMutableTreeNode carpeta3 = new DefaultMutableTreeNode("INCOMPLETOS (0)");             
           DefaultMutableTreeNode carpeta4 = new DefaultMutableTreeNode("PUNTEADOS (0)");
          
           modelo2.insertNodeInto(carpeta2, carpetaRaiz, 0);
           modelo2.insertNodeInto(carpeta3, carpetaRaiz, 1);
           modelo2.insertNodeInto(carpeta4, carpetaRaiz, 2);
           
           for (i=0; i<cnt; i++){
               CUPS = this.tablaDatos[i][4] ;                           // Insertamos primero el CUPS de electricidad
               CUPS = CUPS.trim();
               nCUPS = CUPS.length();
               if ( nCUPS>0){
                    DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(i+" "+this.tablaDatos[i][4]);
                    modelo2.insertNodeInto(archivo, carpeta2, i);       
               } else {                                                 // Si no hay cups electrico, probamos a poner el de gas
                   CUPS = this.tablaDatos[i][3] ;
                   CUPS = CUPS.trim();
                   nCUPS = CUPS.length();
                   if ( nCUPS>0){
                    DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(i+" "+this.tablaDatos[i][3]);
                    modelo2.insertNodeInto(archivo, carpeta2, i);      
                   } else {
                        DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(i+" -");
                        modelo2.insertNodeInto(archivo, carpeta2, i); 
                   }                
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
           
       }
       public final void modificarArbol() {
           int i,cnt,nCUPS,ind;
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
           DefaultMutableTreeNode carpeta2 = new DefaultMutableTreeNode("NUEVOS ("+this.nRegistros+")");       
           DefaultMutableTreeNode carpeta3 = new DefaultMutableTreeNode("INCOMPLETOS ("+this.Incompletos+")");             
           DefaultMutableTreeNode carpeta4 = new DefaultMutableTreeNode("PUNTEADOS ("+this.Completos+")");
          
           modelo2.insertNodeInto(carpeta2, carpetaRaiz, 0);
           modelo2.insertNodeInto(carpeta3, carpetaRaiz, 1);
           modelo2.insertNodeInto(carpeta4, carpetaRaiz, 2);
           
           cnt = this.nRegistros ;
           
            for (i=0; i<cnt; i++){
               CUPS = this.tablaDatos[i][4] ;                           // Insertamos primero el CUPS de electricidad
               CUPS = CUPS.trim();
               nCUPS = CUPS.length();
               if ( nCUPS>0){
                    DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(i+" "+this.tablaDatos[i][4]);
                    modelo2.insertNodeInto(archivo, carpeta2, i);       
               } else {                                                 // Si no hay cups electrico, probamos a poner el de gas
                   CUPS = this.tablaDatos[i][3] ;
                   CUPS = CUPS.trim();
                   nCUPS = CUPS.length();
                   if ( nCUPS>0){
                    DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(i+" "+this.tablaDatos[i][3]);
                    modelo2.insertNodeInto(archivo, carpeta2, i);      
                   } else {
                        DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(i);
                        modelo2.insertNodeInto(archivo, carpeta2, i); 
                   }                
              }
           }   
             
           
           cnt = this.Incompletos ;
           
           
            for (i=0; i<cnt; i++){
                
               ind = Integer.parseInt(this.tablaDatosIncpl[i][29]);
                
               CUPS = this.tablaDatosIncpl[i][4] ;                           // Insertamos primero el CUPS de electricidad
               CUPS = CUPS.trim();
               nCUPS = CUPS.length();
               if ( nCUPS>0){
                    DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(ind+" "+this.tablaDatosIncpl[i][4]);
                    modelo2.insertNodeInto(archivo, carpeta3, i);       
               } else {                                                 // Si no hay cups electrico, probamos a poner el de gas
                   CUPS = this.tablaDatosIncpl[i][3] ;
                   CUPS = CUPS.trim();
                   nCUPS = CUPS.length();
                   if ( nCUPS>0){
                    DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(ind+" "+this.tablaDatosIncpl[i][3]);
                    modelo2.insertNodeInto(archivo, carpeta3, i);      
                   } else {
                        DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(i);
                        modelo2.insertNodeInto(archivo, carpeta3, i);  // ind
                   }                
              }
           }   
                    
           cnt = this.Completos ;
           
           for (i=0; i<cnt; i++){
               
                ind = Integer.parseInt(this.tablaDatosCmpl[i][29]);
               
               CUPS = this.tablaDatosCmpl[i][4] ;                           // Insertamos primero el CUPS de electricidad
               CUPS = CUPS.trim();
               nCUPS = CUPS.length();
               if ( nCUPS>0){
                    DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(ind+" "+this.tablaDatosCmpl[i][4]);
                    modelo2.insertNodeInto(archivo, carpeta4, i);       
               } else {                                                 // Si no hay cups electrico, probamos a poner el de gas
                   CUPS = this.tablaDatosCmpl[i][3] ;
                   CUPS = CUPS.trim();
                   nCUPS = CUPS.length();
                   if ( nCUPS>0){
                    DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(ind+" "+this.tablaDatosCmpl[i][3]);
                    modelo2.insertNodeInto(archivo, carpeta4, i);      
                   } else {
                        DefaultMutableTreeNode archivo = new DefaultMutableTreeNode(ind);
                        modelo2.insertNodeInto(archivo, carpeta4, i); 
                   }                
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
           
       }
       
       private void actualizarFormulario(int indice) {
           
           this.indGen = indice ;
           
           if (indice == -1) {
            
            actuaMunicipio.setVisible(false);
            
            jTextField4.setText(""); jTextField4.setBackground(Color.white);// cups electrico
            jTextField5.setText(""); jTextField5.setBackground(Color.white);// cups gas
         
            jTextField7.setText(""); jTextField7.setBackground(Color.white);// agente
            jTextField6.setText(""); jTextField6.setBackground(Color.white);// comercial
          
            jTextField10.setText(""); jTextField10.setBackground(Color.white);// codigo postal
            jTextField11.setText(""); jTextField11.setBackground(Color.white);// municipio
            jTextField12.setText(""); jTextField12.setBackground(Color.white);// provincia
            jTextField13.setText(""); jTextField13.setBackground(Color.white);// direccion
            jTextField14.setText(""); jTextField14.setBackground(Color.white);// titular
            jTextField15.setText(""); jTextField15.setBackground(Color.white);// nif/cif
            jTextField16.setText(""); jTextField16.setBackground(Color.white);// fecha firma cliente
         
            jTextField18.setText(""); jTextField18.setBackground(Color.white);// consumo kwha
        
            jTextField22.setText(""); jTextField22.setBackground(Color.white);// telefono
            jTextField23.setText(""); jTextField23.setBackground(Color.white);// persona contacto
            jTextArea1.setText("");   jTextArea1.setBackground(Color.white);// observaciones
            jTextField2.setText(""); jTextField2.setBackground(Color.white);// consumo gas
            
            jTextField1.setText(""); jTextField1.setBackground(Color.white);// consumo gas
            jTextField3.setText(""); jTextField3.setBackground(Color.white);// consumo gas
           
            // .............................................................................
            
            jCheckBox17.setSelected(false);
            jCheckBox19.setSelected(false);
            jCheckBox20.setSelected(false);
            jCheckBox21.setSelected(false);
            jCheckBox22.setSelected(false);
            jCheckBox23.setSelected(false);
            jCheckBox29.setSelected(false);
            
             // .............................................................................
            
             jCheckBox24.setSelected(false);
             jCheckBox25.setSelected(false);
             jCheckBox26.setSelected(false);
             jCheckBox27.setSelected(false);
             jCheckBox28.setSelected(false);
               
            jLabel41.setVisible(false);  // Sugerencia de Municipio
               
           } else {
           int ind;
           
            ind = Integer.parseInt(this.tablaDatos[indice][0]) ;      // estado      
            jComboBox4.setSelectedIndex(ind); 
             
            ind = Integer.parseInt(this.tablaDatos[indice][1]) ;      // incidencia      
            jComboBox3.setSelectedIndex(ind);               
          
            jTextField27.setText(this.tablaDatos[indice][2]); // fecha producción
     
            jTextField4.setText(this.tablaDatos[indice][4]); // cups electrico
            jTextField5.setText(this.tablaDatos[indice][3]); // cups gas
       
            jTextField7.setText(this.tablaDatos[indice][17]); // agente
            jTextField6.setText(this.tablaDatos[indice][39]); // COMERCIAL
       
            jTextField10.setText(this.tablaDatos[indice][5]); // codigo postal
            jTextField11.setText(this.tablaDatos[indice][6]); // municipio
            jTextField12.setText(this.tablaDatos[indice][7]); // provincia
            jTextField13.setText(this.tablaDatos[indice][8]); // direccion
            jTextField14.setText(this.tablaDatos[indice][9]); // titular
            jTextField15.setText(this.tablaDatos[indice][10]); // nif/cif
            jTextField16.setText(this.tablaDatos[indice][11]); // fecha firma cliente
        
            jTextField18.setText(this.tablaDatos[indice][12]); // consumo kwha electrico
            jTextField2.setText(this.tablaDatos[indice][13]); // consumo kwha gas
            
            jTextField1.setText(this.tablaDatos[indice][18]); // tarifa electrica
            jTextField3.setText(this.tablaDatos[indice][19]); // tarifa electrica
            
            ind = Integer.parseInt(this.tablaDatos[indice][14]) ;            
            jComboBox2.setSelectedIndex(ind); 
                       
            jTextArea1.setText(this.tablaDatos[indice][15]); // observaciones
            jTextField22.setText(this.tablaDatos[indice][16]); // telefono
            
            // .............................................................................
            
             if (this.tablaDatos[indice][30].equals("1")) jCheckBox17.setSelected(true); else jCheckBox17.setSelected(false);
             if (this.tablaDatos[indice][31].equals("1")) jCheckBox18.setSelected(true); else jCheckBox18.setSelected(false);
             if (this.tablaDatos[indice][32].equals("1")) jCheckBox19.setSelected(true); else jCheckBox19.setSelected(false);
             if (this.tablaDatos[indice][33].equals("1")) jCheckBox20.setSelected(true); else jCheckBox20.setSelected(false);
             if (this.tablaDatos[indice][34].equals("1")) jCheckBox21.setSelected(true); else jCheckBox21.setSelected(false);
             if (this.tablaDatos[indice][35].equals("1")) jCheckBox22.setSelected(true); else jCheckBox22.setSelected(false);
             if (this.tablaDatos[indice][36].equals("1")) jCheckBox23.setSelected(true); else jCheckBox23.setSelected(false);
             if (this.tablaDatos[indice][45].equals("1")) jCheckBox29.setSelected(true); else jCheckBox29.setSelected(false);
            
            // .............................................................................
            
             if (this.tablaDatos[indice][40].equals("1")) jCheckBox24.setSelected(true); else jCheckBox24.setSelected(false);
             if (this.tablaDatos[indice][41].equals("1")) jCheckBox25.setSelected(true); else jCheckBox25.setSelected(false);
             if (this.tablaDatos[indice][42].equals("1")) jCheckBox26.setSelected(true); else jCheckBox26.setSelected(false);
             if (this.tablaDatos[indice][43].equals("1")) jCheckBox27.setSelected(true); else jCheckBox27.setSelected(false);
             if (this.tablaDatos[indice][44].equals("1")) jCheckBox28.setSelected(true); else jCheckBox28.setSelected(false);
                    
            // .............................................................................
                       
            this.idt = indice ;
            
            System.out.println("tablaErrores["+indice+"][8] =  "+this.tablaErrores[indice][8] );
            System.out.println("tablaErrores["+indice+"][4] =  "+this.tablaErrores[indice][4] );
            System.out.println("tablaErrores["+indice+"][0] =  "+this.tablaErrores[indice][0] );
            
           
            if (this.tablaErrores[indice][4] == 1) {  jTextField4.setBackground(Color.red);} else {
                                                        jTextField4.setBackground(Color.white);
            }
         
          
            if (this.tablaErrores[indice][5] == 1) {    jTextField10.setBackground(Color.red);} else {
                                                        jTextField10.setBackground(Color.white);
            }
             if (this.tablaErrores[indice][6] == 1) {    jTextField11.setBackground(Color.red);                                                         
                                                        jLabel41.setText(this.tablaErrorCod[indice][0]);
                                                        System.out.println("Error en municipio dato indice("+indice+") ="+this.tablaErrorCod[indice][28]);
                                                        jLabel41.setVisible(true);
                                                        actuaMunicipio.setVisible(true);} 
            else {
                                                        jTextField11.setBackground(Color.white); jLabel41.setVisible(false);
            }
            if (this.tablaErrores[indice][9] == 1) {    jTextField12.setBackground(Color.red);} else {
                                                        jTextField12.setBackground(Color.white);
            }
            if (this.tablaErrores[indice][10] == 1) {    jTextField15.setBackground(Color.red);} else {
                                                        jTextField15.setBackground(Color.white);
            }
            if (this.tablaErrores[indice][11] == 1) {    jTextField14.setBackground(Color.red);} else {
                                                        jTextField14.setBackground(Color.white);
            }
            
             if (this.tablaErrores[indice][11] == 1) {    jTextField16.setBackground(Color.red);} else {
                                                        jTextField16.setBackground(Color.white);
            }
           
            if (this.tablaErrores[indice][15] == 1) {    jTextField18.setBackground(Color.red);} else {
                                                        jTextField18.setBackground(Color.white);
            }
           
           
           
            if (this.tablaErrores[indice][16] == 1) {    jTextField22.setBackground(Color.red);} else {
                                                        jTextField22.setBackground(Color.white);
            }
            if (this.tablaErrores[indice][20] == 1) {    jTextField23.setBackground(Color.red);} else {
                                                        jTextField23.setBackground(Color.white);
            }
            if (this.tablaErrores[indice][21] == 1) {    jTextArea1.setBackground(Color.red);} else {
                                                        jTextArea1.setBackground(Color.white);
            }
            if (this.tablaErrores[indice][3] == 1) {    jTextField5.setBackground(Color.red);} else {
                                                        jTextField5.setBackground(Color.white);
            }
            if (this.tablaErrores[indice][24] == 1) {    jTextField2.setBackground(Color.red);} else {
                                                        jTextField2.setBackground(Color.white);
            }
           
             
           }
           
       }
       // -------------------------------------------------------------------------------------------------------------------------
       private void mostrarDatosConTableModel() {
		
                System.out.println("----COMIENZO EL FORMATEO ---");
           
                DefaultTableModel model;
		model = new DefaultTableModel();        // definimos el objeto tableModel
               
		miTabla01 = new JTable();                // creamos la instancia de la tabla
		miTabla01.setModel(model);
                
                                 
		model.addColumn("ID_C");
		model.addColumn("Estado");		
                model.addColumn("Fecha");
		model.addColumn("Incidencia");
                model.addColumn("Orden");                
                model.addColumn("CUPS Elect");
                model.addColumn("CUPS gas");
                model.addColumn("Reactiva");  
                model.addColumn("Agente");  
                model.addColumn("Empresa Origen");  
                model.addColumn("Cod Postal");
                model.addColumn("Municipio");
                model.addColumn("Provincia");
                model.addColumn("Direccion");
                model.addColumn("Titular");
                model.addColumn("NIF-CIF");
                model.addColumn("Consumo elc");
                model.addColumn("Consumo gas");
                model.addColumn("Autonomo");                 
                model.addColumn("Fecha Firma");                
                model.addColumn("Oferta");                
                model.addColumn("Tarifa");
                model.addColumn("Campaña");
                model.addColumn("Telefono");
                model.addColumn("Pers. contacto");                
                model.addColumn("Incidencia");
                model.addColumn("Explicacion");
                model.addColumn("Solucion");
                model.addColumn("Observaciones");
                model.addColumn("Memo");
                
               
                
		miTabla01.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		miTabla01.getTableHeader().setReorderingAllowed(false);

		PymesDao miPymesDao1 = new PymesDao();
		/*
		 * enviamos el objeto TableModel, como mandamos el objeto podemos
		 * manipularlo desde el metodo
		 */
		miPymesDao1.buscarContratos(model,this.plogin,this.ppassword,this.filtroEstadoSel,this.filtroFechaSel);
                
                 //Nueva instancia de la clase que contiene el formato
                FormatoTabla formato = new FormatoTabla();
                System.out.println("----HE DEFINIDO FORMATO Y PASO A ASIGNAR---");
                //Se obtiene la tabla y se establece el formato para cada tipo de dato
                
                miTabla01.setDefaultRenderer(Double.class, formato); 
                miTabla01.setDefaultRenderer(String.class, formato); 
                miTabla01.setDefaultRenderer(Integer.class, formato);
                miTabla01.setDefaultRenderer(Object.class, formato);
             
		miBarra01.setViewportView(miTabla01);

	}
       // -------------------------------------------------------------------------------------------------------------------------
       public void ActualizarTablaFormulario() {
           
           int indice, ind;
           indice = this.idt ;
           System.out.println("Actualizo campos tabla  ="+indice);
           
            ind = jComboBox4.getSelectedIndex() ;
            this.tablaDatos[indice][0] = Integer.toString(ind);  // estado
            ind = jComboBox3.getSelectedIndex() ;
            this.tablaDatos[indice][1] = Integer.toString(ind); // incidencia
            
         
          
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
           this.tablaDatos[indice][13] = jTextField2.getText(); // consumo gas kwha
         
           this.tablaDatos[indice][2] = jTextField27.getText(); // Fecha de producción      
           this.tablaDatos[indice][39] = jTextField6.getText(); // Comercial   
           
       }
      // -------------------------------------------------------------------------------------------------------------------------
      public void RefrescarTablaBD() {
          
           
           mostrarDatosConTableModel(); 
           
      
          
      }
      // -------------------------------------------------------------------------------------------------------------------------
      public void ActualizaConfiguracion() {
          
          
      }
      
      // ------------------------------------------------------------------------------------------------------------------------- 
      public void ActualizaFechaProduccion() {
          int i;
          for (i=0; i<this.nRegistros; i++){
              
              this.tablaDatos[i][2] = jTextField28.getText() ;
              
          }
          actualizarFormulario(-1);
      }
       // -------------------------------------------------------------------------------------------------------------------------          
      public  void GuardarArchivoExelResultados() {
        
        int i,j;
     
        HSSFWorkbook libro = new HSSFWorkbook();        
        HSSFSheet hoja = libro.createSheet("MAKRO PYMES");
        Row fila = hoja.createRow(0);        
        Cell celda;
          
        
        String[] titulos = { "FECHA","G","L","Dual","CUPS Gas","CUPS Electricidad","Código Postal","Municipio",
                             "Provincia","Dirección","Titular","NIF/CIF","Fecha Firma Cliente","Consumo Gas kWh/año","Consumo Electricidad kWh/año",
                             "Tarifa Gas","Tarifa Electricidad","SVGComplet","SVGXpres","SVG Básico","SVELECTRIC XPRES","SVGHOGAR",
                             "OBSERVACIONES.","telefono","Estado","Incidencia" };                                                                   // 24 CAMPOS
        
        String str,L,G,D="" ;
        

        

        // Creamos el encabezado

        for (i = 0; i < titulos.length; i++) {
              celda = fila.createCell(i);
              celda.setCellValue(titulos[i]);
        }

       
        for (j=0; j<this.nRegistros; j++) {
           
           if  (this.tablaDatos[j][4] !="" ) L="X"; else L = "" ;
           
           if  (this.tablaDatos[j][3] !="" ) G="X"; else G = "" ;
           
           if  (this.tablaDatos[j][3] !="" && this.tablaDatos[j][4] !="" ) {L=""; G = ""; D="X";} else D = "" ;
           
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
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][19]);     i++;        // Tarifa gas
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][18]);     i++;        // Tarifa electrica
           
           
           celda = fila.createCell(i);  
           if (this.tablaDatos[j][14].equals("1") )  celda.setCellValue("X"); else 
                                                         celda.setCellValue("");         i++;        // SVGComplet
        
           celda = fila.createCell(i);  
           if (this.tablaDatos[j][14].equals("2") )  celda.setCellValue("X"); else 
                                                         celda.setCellValue("");         i++;        // SVGXpres
        
           celda = fila.createCell(i);  
           if (this.tablaDatos[j][14].equals("3") )  celda.setCellValue("X"); else 
                                                         celda.setCellValue("");         i++;        // SVG Básico
        
          celda = fila.createCell(i);  
          if (this.tablaDatos[j][14].equals("4") )   celda.setCellValue("X"); else 
                                                         celda.setCellValue("");         i++;        //SVELECTRIC XPRES
        
          celda = fila.createCell(i);  
          if (this.tablaDatos[j][14].equals("5") )  celda.setCellValue("X"); else 
                                                        celda.setCellValue("");          i++;         //SVGHOGAR
                                
          celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][15]);      i++;        // observaciones  
          celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][16]);      i++;        // telefono
       
          celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][0]);       i++;        // estado
          celda = fila.createCell(i);  celda.setCellValue(this.tablaDatos[j][1]);       i++;        // incidencia                   
            
             
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
        
        // Se salva el libro.
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    

       
          
          
      }
         // -------------------------------------------------------------------------------------------------------------------------
 public void GuardarArchivoExelDevueltos() {
        
        int i,j;
     
        HSSFWorkbook libro = new HSSFWorkbook();        
        HSSFSheet hoja = libro.createSheet("DEVUELTOS");
        Row fila = hoja.createRow(0);        
        Cell celda;
          
        
        String[] titulos = { "Contrato a devolver","Nombre","Localidad","Telefono","Gas","Luz",
                             "SVGComplet","SVGXpres","SVG Básico","SVELECTRIC XPRES","SVGHOGAR",
                             "Comercial","OBSERVACIONES" };                                                                   // 13 CAMPOS
        
        String str,L,G,D="" ;
        

        

        // Creamos el encabezado

        for (i = 0; i < titulos.length; i++) {
              celda = fila.createCell(i);
              celda.setCellValue(titulos[i]);
        }

       
        for (j=0; j<this.Incompletos; j++) {
           
           if  (this.tablaDatosIncpl[j][4] !="" ) L="X"; else L = "" ;
           
           if  (this.tablaDatosIncpl[j][3] !="" ) G="X"; else G = "" ;
           
           if  (this.tablaDatosIncpl[j][3] !="" && this.tablaDatos[j][4] !="" ) {L=""; G = ""; D="X";} else D = "" ;
           
           // Nueva fila 
           i = 0 ;
           fila = hoja.createRow(j+1);
             
           System.out.println("Inserto celdas Exel en fila  ="+j);
           
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatosIncpl[j][2]);      i++;        // FECHA PROD
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatosIncpl[j][9]);      i++;        // titular   
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatosIncpl[j][6]);      i++;        // municipio
           celda = fila.createCell(i);  celda.setCellValue(this.tablaDatosIncpl[j][16]);     i++;        // telefono
           celda = fila.createCell(i);  celda.setCellValue(G);                               i++;        // G
           celda = fila.createCell(i);  celda.setCellValue(L);                               i++;        // L
          
           celda = fila.createCell(i);  
           if (this.tablaDatosIncpl[j][14].equals("1") )  celda.setCellValue("X"); else 
                                                         celda.setCellValue("");              i++;        // SVGComplet
        
           celda = fila.createCell(i);  
           if (this.tablaDatosIncpl[j][14].equals("2") )  celda.setCellValue("X"); else 
                                                         celda.setCellValue("");              i++;        // SVGXpres
        
           celda = fila.createCell(i);  
           if (this.tablaDatosIncpl[j][14].equals("3") )  celda.setCellValue("X"); else 
                                                         celda.setCellValue("");              i++;        // SVG Básico
        
          celda = fila.createCell(i);  
          if (this.tablaDatosIncpl[j][14].equals("3") )   celda.setCellValue("X"); else 
                                                         celda.setCellValue("");              i++;        //SVELECTRIC XPRES
        
          celda = fila.createCell(i);  
          if (this.tablaDatosIncpl[j][14].equals("4") )  celda.setCellValue("X"); else 
                                                        celda.setCellValue("");               i++;         //SVGHOGAR
                                
          celda = fila.createCell(i);  celda.setCellValue(this.tablaDatosIncpl[j][17]);      i++;        // comercial     
          celda = fila.createCell(i);  celda.setCellValue(this.tablaDatosIncpl[j][15]);      i++;        // observaciones  
          
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
    


          
          
      }
      // -------------------------------------------------------------------------------------------------------------------------
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
       // -------------------------------------------------------------------------------------------------------------------------
     public String dateToMySQLDate(String fecha)
    {
        String df,y,m,d;
        
        fecha = fecha.trim();
        
        System.out.println("Fecha = :"+fecha+":");
        
        d = fecha.substring(0, 2) ; System.out.println("dia ="+d);
        m = fecha.substring(3,5) ;  System.out.println("mes ="+m);
        y = fecha.substring(6,8) ;  System.out.println("año ="+y);
        
        df = y+"-"+m+"-"+d+ " 00:00:00";
       
        return df;
        
       
    }
        // -------------------------------------------------------------------------------------------------------------------------
      public void cambiaMunicipio() {
        
        if (this.indGen != -1) {
            
   
            jTextField11.setBackground(Color.white);                                                         
            jTextField11.setText(this.tablaErrorCod[indGen][0]);  
            this.tablaDatos[indGen][6] = this.tablaErrorCod[indGen][0] ;
            jLabel41.setVisible(false);
            actuaMunicipio.setVisible(false);
            
            
        }
        
        
    }
      // -------------------------------------------------------------------------------------------------------------------------
    public void insertarNuevo() {
           int indice,j;
           
          int resp=JOptionPane.showConfirmDialog(null,"¿Insertar nuevo registro en la tabla Temporal?");
        
         
         if (JOptionPane.OK_OPTION == resp){
            
            // .......................................................................
            indice = this.nRegistros  ;
            // ....................................................................... 
            this.tablaDatos[indice][0]="0" ;                      // estado      
            this.tablaDatos[indice][1] ="0" ;                     // incidencia  
            this.tablaDatos[indice][2]  =  jTextField27.getText();// fecha producción
            this.tablaDatos[indice][4]  =  jTextField4.getText(); // cups electrico
            this.tablaDatos[indice][3]  =  jTextField5.getText(); // cups gas
            this.tablaDatos[indice][17] =  jTextField7.getText(); // agente
            this.tablaDatos[indice][39] =  jTextField6.getText(); // agente
            this.tablaDatos[indice][5]  =  jTextField10.getText(); // codigo postal 
            this.tablaDatos[indice][6]  =  jTextField11.getText(); // municipio
            this.tablaDatos[indice][7]  =  jTextField12.getText(); // provincia
            this.tablaDatos[indice][8]  =  jTextField13.getText(); // direccion
            this.tablaDatos[indice][9]  =  jTextField14.getText(); // titular
            this.tablaDatos[indice][10] =  jTextField15.getText(); // nif/cif
            this.tablaDatos[indice][11] =  jTextField16.getText(); // fecha firma cliente
            this.tablaDatos[indice][12] =  jTextField18.getText(); // consumo kwha electrico
            this.tablaDatos[indice][13] =  jTextField2.getText();  // consumo kwha gas
       
            this.tablaDatos[indice][18] =  jTextField1.getText(); // tarifa electrica
            this.tablaDatos[indice][19] =  jTextField3.getText(); // tarifa de gas
            
            this.tablaDatos[indice][15] = jTextArea1.getText();   // observaciones
            this.tablaDatos[indice][16] = jTextField22.getText(); // telefono
           
            // ....................................................................... 
            
            if (jCheckBox24.isSelected()) {  this.tablaDatos[indice][40] = "1";} else { this.tablaDatos[indice][40] = "0"; }    //   Gas
            if (jCheckBox25.isSelected()) {  this.tablaDatos[indice][41] = "1";} else { this.tablaDatos[indice][41] = "0"; }    //   Luz
            if (jCheckBox26.isSelected()) {  this.tablaDatos[indice][42] = "1";} else { this.tablaDatos[indice][42] = "0"; }    //   Dual
            if (jCheckBox27.isSelected()) {  this.tablaDatos[indice][43] = "1";} else { this.tablaDatos[indice][43] = "0"; }    //   Gas TUR
            if (jCheckBox28.isSelected()) {  this.tablaDatos[indice][44] = "1";} else { this.tablaDatos[indice][44] = "0"; }    //   Tarifa Plana
            if (jCheckBox29.isSelected()) {  this.tablaDatos[indice][45] = "1";} else { this.tablaDatos[indice][45] = "0"; }    //   SPP
       
            // ..............................................
            this.tablaDatos[indice][14]="0";
            if (jCheckBox17.isSelected()) {this.tablaDatos[indice][30] = "1" ; this.tablaDatos[indice][14]="1"; } else {this.tablaDatos[indice][30] = "0" ;  }  
            if (jCheckBox18.isSelected()) {this.tablaDatos[indice][31] = "1" ; this.tablaDatos[indice][14]="2";} else {this.tablaDatos[indice][31] = "0" ; }  
            if (jCheckBox19.isSelected()) {this.tablaDatos[indice][32] = "1" ; this.tablaDatos[indice][14]="3";} else {this.tablaDatos[indice][32] = "0" ; }  
            if (jCheckBox20.isSelected()) {this.tablaDatos[indice][33] = "1" ; this.tablaDatos[indice][14]="4";} else {this.tablaDatos[indice][33] = "0" ; }  
            if (jCheckBox21.isSelected()) {this.tablaDatos[indice][34] = "1" ; this.tablaDatos[indice][14]="5";} else {this.tablaDatos[indice][34] = "0" ; }  
            if (jCheckBox22.isSelected()) {this.tablaDatos[indice][35] = "1" ; this.tablaDatos[indice][14]="6";} else {this.tablaDatos[indice][35] = "0" ; }  // SWG Con Calef
            if (jCheckBox23.isSelected()) {this.tablaDatos[indice][36] = "1" ; this.tablaDatos[indice][14]="7";} else {this.tablaDatos[indice][36] = "0" ; }  // SWG Sin Calef
             
            // ..............................................
            
             for ( j=0; j<50; j++) 
                 this.tablaErrores[indice][j] = 0 ;             // inicializamos
             
            this.tablaDatos[indice][29] = String.valueOf(indice) ;
            this.tablaDatos[indice][37] = "0" ;                  // idEstado
            this.tablaDatos[indice][38] = "0" ;                  // idIncidencia
       
            this.nRegistros ++;
         }
          
    }
}

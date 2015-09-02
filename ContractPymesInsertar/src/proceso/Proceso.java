 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proceso;

import java.io.FileNotFoundException;
import java.io.IOException;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import conexion.Conexion;
import dao.PymesDao;
import java.io.File;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

/**
 *
 * @author jab7b7
 */
public class Proceso extends SwingWorker<Void, Void> {

 // ...........................................  DEFINICIONES PÚBLICAS DE LA CLASE
 public String tablaDatos[][] = new String[2500][50] ;    // Tabla  con los datos a procesar.
 public String tablaRes1[][] = new String[2500][50];
 public String tablaRes2[][] = new String[2500][50];
 public String tablaTmp[][] = new String[2500][50];
 public String tablaOrd[][] = new String[2500][50];
 
 public String tablaErrorCod[][] = new String[2500][50];
 
 public Integer tablaErrores[][] = new Integer[2500][50];
 public Integer tablaConfigura[]= new Integer[50];
 
 public int nRegistros      = 0 ;
 public int nIncompletos    = 0 ;
 public int nCompletos      = 0 ;
 public String sMensajes    = "";
 // ...........................................
 
   
 
 public Proceso () {
     
     return;
     
 }
  public Proceso (Integer [] configuraAnalisis)  {
     
     this.tablaConfigura = configuraAnalisis ;
     System.out.println("He definido Configuracion analisis el primero es="+ this.tablaConfigura[0]);
     return;
     
 }

 // ........................................... 

public String ProcesaDatosReales(File nombre,int n,int p) throws IOException  {
    

// int n       = 288 ;         // Numero de elementos a leer de la tabla
// int p       = 2 ;           // Posicion de inicio de lectura de la tabla
    
int d       = 1 ;
int cnt     = 0 ;           // contador principal
int intcnt  = 0 ;           // contador interno.   
int dias    = 0 ;           // Cuenta dias de la semana
int nC      = 50 ;          // numero de campos de las tablas
int j ;
String str="";
String resultado = "";
// String tablaDatos[][] = new String[20][500] ;    // Tabla temporal con los datos a procesar.
String strFile="";

System.out.println("El nombre del archivo es:"+nombre+" (type File)");
strFile= nombre.getAbsolutePath();
System.out.println("El nombre del archivo es:"+strFile+" (type String)");
//Le pasamos la URL del archivo CSV a leer.
// CsvReader reader = new CsvReader(strFile+"_tmp");
CsvReader reader = new CsvReader(strFile);
//Le pasamos la URL del archivo CSV a escribir.
CsvWriter writer = new CsvWriter ("/home/jab7b7/Datos/resultadoTmp.csv");

try {
    reader.readRecord() ;                           // Nos saltamos la cabecera
while (reader.readRecord())
{
    
        
     if ( intcnt < n ) {
       //         String estado       = reader.get(0);
                String estado       = "0";
                String incidencia   = "0";
                String fecha_prod   = reader.get(0);
                String cups_gas     = reader.get(4);
                String cups_elec    = reader.get(5);
                String cod_postal   = reader.get(6);
                String municipio    = reader.get(7);
                String provincia    = reader.get(8);
                String direccion    = reader.get(9);
                String titular      = reader.get(10);
                String nif_cif      = reader.get(11);
                String fecha_firma  = reader.get(12);
                String consumo_gas  = reader.get(13);
                String consumo      = reader.get(14);
                String tarifa_gas   = reader.get(15);
                String tarifa       = reader.get(16);
                String prd01        = reader.get(17);
                String prd02        = reader.get(18);
                String prd03        = reader.get(19);
                String prd04        = reader.get(20);
                String prd05        = reader.get(21);
                String prd06        = reader.get(22);
                String prd07        = reader.get(23);
                String prd08        = reader.get(24);
                String prd09        = reader.get(25);
                String observaciones= reader.get(26);
                String telefono     = reader.get(27);
                String agente       = reader.get(29);
               // String iEstado      = reader.get(27);
               // String iIncidencia  = reader.get(28);
                 String iEstado     = "0";
                String iIncidencia  = "0" ;
                String Comercial    = reader.get(28);
              
                
                tablaDatos[intcnt][0] = estado ;
                tablaDatos[intcnt][1] = incidencia ;
                tablaDatos[intcnt][2]= fecha_prod ;  
                tablaDatos[intcnt][3]= cups_gas.toUpperCase() ;  
                tablaDatos[intcnt][4] = cups_elec.toUpperCase() ;
                
                tablaDatos[intcnt][5] = cod_postal ;
                tablaDatos[intcnt][6] = municipio.toUpperCase() ;
                tablaDatos[intcnt][7] = provincia.toUpperCase();
                tablaDatos[intcnt][8]= direccion.toUpperCase();
                tablaDatos[intcnt][9]= titular.toUpperCase();
                tablaDatos[intcnt][10]= nif_cif.toUpperCase();
                tablaDatos[intcnt][11]= fecha_firma ;  
                 
                str = consumo.trim();
                if (str.length()>0) { tablaDatos[intcnt][12]= consumo ; } else {tablaDatos[intcnt][12] ="0";}
                
                str = consumo_gas.trim();
                if (str.length()>0) { tablaDatos[intcnt][13]= consumo_gas ; } else {tablaDatos[intcnt][13] ="0";}
                
                
                tablaDatos[intcnt][14] ="0";
                tablaDatos[intcnt][44] = "0" ;
                
                str = prd01.trim(); if (str.length()>0) {tablaDatos[intcnt][14] = "1" ; tablaDatos[intcnt][30]= "1" ; } else tablaDatos[intcnt][30]= "0" ;
                str = prd02.trim(); if (str.length()>0) {tablaDatos[intcnt][14] = "2" ; tablaDatos[intcnt][31]= "1" ;}  else tablaDatos[intcnt][31]= "0" ;
                str = prd03.trim(); if (str.length()>0) {tablaDatos[intcnt][14] = "3" ; tablaDatos[intcnt][32]= "1" ;}  else tablaDatos[intcnt][32]= "0" ;
                str = prd04.trim(); if (str.length()>0) {tablaDatos[intcnt][14] = "4" ; tablaDatos[intcnt][33]= "1" ;}  else tablaDatos[intcnt][33]= "0" ;
                str = prd05.trim(); if (str.length()>0) {tablaDatos[intcnt][14] = "5" ; tablaDatos[intcnt][34]= "1" ;}  else tablaDatos[intcnt][34]= "0" ;  
                
                str = prd06.trim(); if (str.length()>0) {tablaDatos[intcnt][14] = "6" ; tablaDatos[intcnt][35]= "1" ;}  else tablaDatos[intcnt][35]= "0" ;
                str = prd07.trim(); if (str.length()>0) {tablaDatos[intcnt][14] = "7" ; tablaDatos[intcnt][36]= "1" ;}  else tablaDatos[intcnt][36]= "0" ; 
                str = prd08.trim(); if (str.length()>0) { tablaDatos[intcnt][44]= "1" ;}  else tablaDatos[intcnt][44]= "0" ; 
                str = prd09.trim(); if (str.length()>0) { tablaDatos[intcnt][45]= "1" ;}  else tablaDatos[intcnt][45]= "0" ; 
                
                tablaDatos[intcnt][15]= observaciones.toUpperCase();   
                tablaDatos[intcnt][16]= telefono;                   
                tablaDatos[intcnt][17] = agente.toUpperCase() ;
                                
                tablaDatos[intcnt][18]= tarifa;  
                tablaDatos[intcnt][19]= tarifa_gas;  
                
                tablaDatos[intcnt][37]= iEstado;                   
                tablaDatos[intcnt][38]= iIncidencia ;
                tablaDatos[intcnt][39] = Comercial.toUpperCase() ;
                 
                 tablaDatos[intcnt][29] = String.valueOf(intcnt) ;
                
                tablaDatos[intcnt][40] = "0" ;
                tablaDatos[intcnt][41] = "0" ;
                tablaDatos[intcnt][42] = "0" ;
                tablaDatos[intcnt][43] = "0" ;
          //      tablaDatos[intcnt][44] = "0" ;
                
                
                                     

                System.out.println(intcnt+" -- "+ fecha_prod + " - cups: " + cups_elec  + " -" + " - tarifa = "+ tablaDatos[intcnt][14] + "TLF="+telefono+" Comercial ="+tablaDatos[intcnt][39]+" Agente ="+tablaDatos[intcnt][17] );
                
                for ( j=0; j<nC; j++) 
                    this.tablaErrores[intcnt][j] = 0 ;          // inicializamos
                
                 intcnt ++ ;
       } else {
            
                break;
       }
       
   
   
         
}

this.nRegistros = intcnt ;              // Apuntamos cuantos registros hemos cargado en memoria

intcnt = 0; 

while (intcnt < n){
    for (d=0 ; d < 7 ; d++ ) {
    //writer.write(String) - Escribe los campos separandolos con coma automaticamente.
 //       writer.write ( tablaDatos[d][intcnt] );
        resultado = resultado+tablaDatos[intcnt][d]+",";
    }
    resultado = resultado+"\n"  ;
                
 //   writer.endRecord();
    intcnt ++ ;
}


} catch (IOException e) {
e.printStackTrace();
}

reader.close();
writer.close();

return resultado;  

}

 public void punteaRegistros(int nReg,String[][] tabla,String ccStr1,String ccStr2)  {
     
     // ........................................
     int i,j,k,res;
     int nC = 50;                                                       // numero de campos
     // ........................................
     String dni,sdni,ldni, cups,cupsi,cupsf, codigopost,telef, fecha, municipio, provincia,municipio_error,cups2;
     char letra,letrac,fletra;
     int nStr, error=0 ;    
     int existe[]        = {0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0} ; 
        
     int progress = 0;
     int coefprog = 100 / nReg ;
     setProgress(0);    
     Conexion conex = new Conexion(ccStr1,ccStr2);
     // ........................................
     for (i=0; i<nReg; i++ ){
         
         tabla[i][nC-1] = i+"" ;       
         

        // Apunto el indice de la tabla en la propia tabla al final
         // ..................... Comprueba que el campo exista 
         
         for ( j=0; j<nC; j++) {
             this.tablaErrores[i][j] = 0 ;          // inicializamos
             if (existe[j] == 1) {      // Si este campo debe existir
                 
                 if (tabla[i][j] == "") {
                        
                     this.tablaErrores[i][j] = 1 ;  // no hay nada y deberia apuntamos error de campo = 1
                     
                 } else {
                     
                     this.tablaErrores[i][j] = 0 ;
                     
                 }                     
                 
             } else {
                 
                 this.tablaErrores[i][j] = 0 ;
             }
         
         }           
         
         if (this.tablaConfigura[15] == 1 ) {
            // ..................... Comprueba DNI o nif

            dni    = tabla[i][10];

            dni    = dni.trim();
            nStr   = dni.length() ;
            
            if (nStr > 0){
            
                letra  = dni.charAt(nStr-1);
                sdni   = dni.substring(0, nStr-1);

                ldni = dni.substring(0, 1);

                if (isNumeric(ldni) == true ) {

                letrac = ComprobarDNI(sdni);

                   if (letrac == letra ){
                           System.out.println(i+" -El DNI tiene "+nStr+" caracteres y la letra ="+letra+ " Y ES CORRECTO");
                            this.tablaErrores[i][10] = 0 ;  // no hay nada y deberia apuntamos error de campo = 1

                   } else {
                          System.out.println(i+" -El DNI  tiene "+nStr+" caracteres y la letra ="+letra+ " y debería tener la letra = "+letrac);
                          this.sMensajes = this.sMensajes + i+"- El DNI  tiene "+nStr+" caracteres y la letra ="+letra+ " y debería tener la letra = "+letrac+"\n" ;
                          this.tablaErrores[i][10] = 1 ;  // no hay nada y deberia apuntamos error de campo = 1

                   }

                } else {

                    if ( ldni.equals("B")  || ldni.equals("A")  || ldni.equals("C") || ldni.equals("D") || ldni.equals("E") 
                         || ldni.equals("F") || ldni.equals("G") || ldni.equals("H")|| ldni.equals("S") || ldni.equals("U") 
                         || ldni.equals("J")){

                         System.out.println("El DNI podria ser un NIF");
                         this.tablaErrores[i][10] = 0 ; 

                    } else {

                       if ( ldni.equals("X") || ldni.equals("Y") || ldni.equals("Z"))  {
                          
                           System.out.println("El DNI podria ser un NIE EXTRANGERO...");
                          
                            letra  = dni.charAt(nStr-1);
                            sdni   = dni.substring(1, nStr-1);

                            ldni = dni.substring(1, 2);

                            if (isNumeric(ldni) == true ) {

                            letrac = ComprobarDNI(sdni);

                               if (letrac == letra ){
                                       System.out.println("El NIE "+i+" tiene "+nStr+" caracteres y la letra ="+letra+ " Y ES CORRECTO");
                                        this.tablaErrores[i][10] = 0 ;  // no hay nada y deberia apuntamos error de campo = 1

                               } else {
                                      System.out.println("El NIE "+i+" tiene "+nStr+" caracteres y la letra ="+letra+ " y debería tener la letra = "+letrac);
                                      this.sMensajes = this.sMensajes + i+"- El NIE tiene "+nStr+" caracteres y la letra ="+letra+ " y debería tener la letra = "+letrac+"\n" ;
                                      this.tablaErrores[i][10] = 1 ;  // no hay nada y deberia apuntamos error de campo = 1

                               }
                           
                            }
                               
                       
                       } else { 
                            System.out.println("El DNI "+i+" tiene "+nStr+" caracteres y empieza por letra ="+ldni+ " ES UN ERROR");
                            this.sMensajes = this.sMensajes + i+" -El DNI  tiene "+nStr+" caracteres y empieza por letra ="+ldni+ " ES UN ERROR"+"\n" ;
                            this.tablaErrores[i][10] = 1 ;  // no hay nada y deberia apuntamos error de campo = 1
                       }
                    }

                }
            }
         }
          // ..................... Comprueba CUPS ELECTRICO
         if (this.tablaConfigura[0] == 1 ) {
            
            // .......................................... Primero comprobamos que esté correcto
            cups = tabla[i][4];
            cups = cups.trim();
            nStr = cups.length();

             if (nStr> 0) {         
               cupsi = cups.substring(0, 2);
               cupsf = cups.substring(nStr-2,nStr);

               if ( nStr != 20 ){
                     System.out.println("El CUPSe "+i+" tiene "+nStr+" ES UN ERROR");
                      this.sMensajes = this.sMensajes + i+" - El CUPSe  tiene "+nStr+" ES UN ERROR"+"\n" ;


                     this.tablaErrores[i][4] = 1 ;  // no hay nada y deberia apuntamos error              
               } else {

                   if ( cupsi.equals("ES")  && isNumeric(cupsf)== false) {

                        System.out.println("El CUPSe "+i+" tiene "+nStr+" y FORMALMENTE ES CORRECTO");
                        this.tablaErrores[i][4] = 0 ;  // no hay nada y deberia apuntamos error           
                   } else {

                       System.out.println("El CUPSe "+i+" tiene "+nStr+" empieza por "+cupsi+" y termina por "+cupsf+ " y FORMALMENTE ES ERRONEO cupsi="+cupsi+ " y cupsf = "+cupsf);
                       this.sMensajes = this.sMensajes + i+" - El CUPSe  tiene "+nStr+" empieza por "+cupsi+" y termina por "+cupsf+ " y FORMALMENTE ES ERRONEO cupsi="+cupsi+ " y cupsf = "+cupsf+"\n" ;
                       this.tablaErrores[i][4] = 1 ;  // no hay nada y deberia apuntamos error           
                   }

               }
             
             
             // .......................................... Ahora comprobamos que no se repita
             
                for (k=i+1; k<nReg; k++) {


                    cups2 = tabla[k][4];
                    cups2 = cups2.trim();
                    if (cups2.equals(cups)== true) {
                          System.out.println(k+" -El CUPSe  "+cups+" SE REPITE con "+i+" -cupse2 = "+cups2);
                          this.sMensajes = this.sMensajes+i+" -El CUPSe  "+cups+" se repite con "+k+" -cupse = "+cups2 +"\n" ;
                          this.tablaErrores[i][4] = 1 ;  // no hay nada y deberia apuntamos error           

                    }


                }
            } 
             
             
             
             
         }
         // ..................... Comprueba CUPS GAS
         if (this.tablaConfigura[1] == 1 ) {
            cups = tabla[i][3];

           cups = cups.trim();


           nStr = cups.length();

           if (nStr != 0) {

              cupsi = cups.substring(0, 2);
              cupsf = cups.substring(nStr-2,nStr);

              if ( nStr != 20 ){
                    System.out.println("El CUPSg "+i+" tiene "+nStr+" ES UN ERROR");
                    this.sMensajes = this.sMensajes + i+" - El CUPSg  tiene "+nStr+" ES UN ERROR"+"\n" ;

                    this.tablaErrores[i][3] = 1 ;  //  apuntamos error              
              } else {

                  if ( cupsi.equals("ES")  && isNumeric(cupsf)== false) {

                       System.out.println("El CUPSg "+i+" tiene "+nStr+" y FORMALMENTE ES CORRECTO");
                       this.tablaErrores[i][3] = 0 ;       
                  } else {

                      System.out.println("El CUPSg "+i+" tiene "+nStr+" empieza por "+cupsi+" y termina por "+cupsf+ " y FORMALMENTE ES ERRONEO cupsi="+cupsi+ " y cupsf = "+cupsf);
                       this.sMensajes = this.sMensajes + i+" - El CUPSg  tiene "+nStr+" empieza por "+cupsi+" y termina por "+cupsf+ " y FORMALMENTE ES ERRONEO cupsi="+cupsi+ " y cupsf = "+cupsf+"\n" ;

                      this.tablaErrores[i][3] = 1 ;  //  apuntamos error           
                  }
              }
              
              // .......................................... Ahora comprobamos que no se repita
             
                for (k=i+1; k<nReg; k++) {


                    cups2 = tabla[k][3];
                    cups2 = cups2.trim();
                    if (cups2.equals(cups)== true) {
                          System.out.println(k+" -El CUPSg  "+cups+" SE REPITE con "+i+" -cupsg2 = "+cups2);
                          this.sMensajes = this.sMensajes+i+" -El CUPSg  "+cups+" se repite con "+k+" -cupsg = "+cups2 +"\n" ;
                          this.tablaErrores[i][3] = 1 ;  // no hay nada y deberia apuntamos error           

                    }


                }
              
              
              
          }   else {

                       this.tablaErrores[i][3] = 0 ;   
          }
         }
        // .................... Comprueba el código postal
         
        codigopost = tabla[i][5] ;
        codigopost = codigopost.trim();
        nStr = codigopost.length();
        
        if (nStr != 5) {
            
                    System.out.println("El Codigo Postal "+i+" tiene "+nStr+" ES UN ERROR");
                    this.sMensajes = this.sMensajes + i+" - El Codigo Postal  tiene "+nStr+" ES UN ERROR"+"\n" ;
                
                    this.tablaErrores[i][5] = 1 ;  //  apuntamos error             
               System.out.println("tablaErrores["+i+"][7] =  "+this.tablaErrores[i][5] );
        } else {
                // ....................................................................................................
                if ( isNumeric(codigopost) == true) {
                    
                    municipio = tabla[i][6]; municipio = municipio.trim();
                    provincia = tabla[i][7]; provincia = provincia.trim();
                                        
                    res = CompruebaCodPost(codigopost,municipio,provincia,conex) ;
                                       
                    if (res == 0) {
                         System.out.println("El Codigo Postal "+i+"  ES  CORRECTO");
                         this.tablaErrores[i][5] = 0;  //  apuntamos error         
                         
                    } else {
                        if (res == 1) {
                            System.out.println("El Codigo Postal "+i+"  NO EXISTE");
                            this.tablaErrores[i][5] = 1;  //  apuntamos error         
                            this.sMensajes = this.sMensajes + i+" - El Codigo Postal COD ("+codigopost+") NO EXITE"+"\n" ;
                        }
                        if (res == 2) {
                            System.out.println("El Codigo Postal "+i+"  NO SE CORRESPONDE CON municipio="+municipio);
                            this.tablaErrores[i][5] = 1;  //  apuntamos error   
                            this.tablaErrores[i][6] = 1;  //  apuntamos error                             
                            municipio_error = BuscaCodPost(codigopost,conex);
                            tablaErrorCod[i][0] = municipio_error ; 
                            this.sMensajes = this.sMensajes + i+" - El Codigo Postal COD ("+codigopost+") NO SE CORRESPONDE CON municipio="+municipio+" debería ser ="+municipio_error+"\n" ;
                        }
                        
                    }
                    
                    
                    
                    
                // ....................................................................................................
                    
                } else {
                     System.out.println("El Codigo Postal "+i+" tiene caracteres, ES UN ERROR");
                     this.tablaErrores[i][5] = 1 ;  //  apuntamos error             
                }
               
        }
                        
        // ,,,,,,,,,,,,,,,,,,,, Comprueba el teléfono
         
        telef = tabla[i][16] ;
        telef = telef.trim();
        nStr = telef.length();
        
        if (nStr != 9) {
            
                    System.out.println("El Telefono "+i+" tiene "+nStr+" ES UN ERROR");
                     this.sMensajes = this.sMensajes + i+"- El Telefono tiene "+nStr+" ES UN ERROR"+"\n" ;
                
                    this.tablaErrores[i][16] = 1 ;  //  apuntamos error             
            
        } else {
            
                if ( isNumeric(telef) == true) {
                     System.out.println("El Telefono "+i+"  ES  CORRECTO");
                    this.tablaErrores[i][16] = 0;  //  apuntamos error         
                    
                } else {
                     System.out.println("El Telefono  "+i+" tiene caracteres, ES UN ERROR");
                     this.sMensajes = this.sMensajes + i+" - El Telefono   tiene caracteres, ES UN ERROR"+"\n" ;
                
                     this.tablaErrores[i][16] = 1 ;  //  apuntamos error             
                }
            
        }              
         
        // ....................  Comprueba fecha
         
        fecha = tabla[i][11] ;
        
        fecha = fecha.trim();
        
        if (isFechaValida(fecha) == true ) {
            
                    System.out.println("La fecha "+i+" ES  CORRECTA");
                    this.tablaErrores[i][11] = 0 ;  //  apuntamos error        
            
        } else {
                    System.out.println("La fecha "+i+" ES  ERRORNEA");
                     this.sMensajes = this.sMensajes + i+" - La fecha  ES  ERRORNEA"+"\n" ;
                
                    this.tablaErrores[i][11] = 1 ;  //  apuntamos error             
            
        }
 //     setProgress(Math.min(coefprog*i, 100));  
        
     }
      conex.desconectar();
       // .................... 
     
     for (i=0; i<nReg; i++) {
         error = 0 ;
         for (j=0; j<nC; j++){
             
             if (this.tablaErrores[i][j] == 1 && error ==0 ) {
                 error = 1;
                 tabla[i][1] = "1" ;
                 this.tablaRes1[this.nIncompletos] = tabla[i] ; System.out.println("this.tablaRes1["+this.nIncompletos+"][29] = tabla["+i+"][29] = ("+ this.tablaRes1[this.nIncompletos][29] +")("+tabla[i][29]+")");
                 this.nIncompletos = this.nIncompletos + 1 ;
             }             
         }
         if (error == 0){
               this.tablaRes2[this.nCompletos] = tabla[i] ;
               this.nCompletos = this.nCompletos + 1 ;             
         }       
     }
     
     
    
    
     
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
  // ----------------------------------------------------------------------------------------------------
    public static boolean isFechaValida(String fecha) {
        String separador="";
        int nStr;
        
        
        nStr = fecha.length();
              
        
        if (nStr>0) {
        separador = fecha.substring(2, 3);
        nStr = fecha.length();
        System.out.println("El separador de fecha es: "+separador+"El numero de caracteres es ="+nStr);            
        
        try {
             SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            if (separador.equals("/")) {
                formatoFecha = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
            }
            if (separador.equals("-") && nStr==8) {
                formatoFecha = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
                 System.out.println("Redefino el formato de fecha");
            }
            if (separador.equals("-") && nStr==10) {
                formatoFecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            }
            
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        } else {
            
            return false;
            
        }
        return true;
    }
    // ........................................... METODO DE COMPROBACIÓN DE LETRA DE DNI
 
public char ComprobarDNI(String sDNI) {
    
    String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
    char letra ;
    
    int iDNI = Integer.parseInt(sDNI);
    int index = iDNI - (Math.abs(iDNI/23)*23);
    letra = letras.charAt(index);
    System.out.println("Su letra de DNI es: "+ letra);
    return letra;
}
   // ........................................... METODO DE COMPROBACIÓN DE LETRA DE DNI o NIE ALTERNATIVO
public static boolean isNifNie(String nif){
 
   System.out.println("NIF "+nif);
   //si es NIE, eliminar la x,y,z inicial para tratarlo como nif
   if (nif.toUpperCase().startsWith("X")||nif.toUpperCase().startsWith("Y")||nif.toUpperCase().startsWith("Z"))
        nif = nif.substring(1);
 
    Pattern nifPattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
    Matcher m = nifPattern.matcher(nif);
    if(m.matches()){
        String letra = m.group(2);

        //Extraer letra del NIF

        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int dni = Integer.parseInt(m.group(1));
        dni = dni % 23;
        String reference = letras.substring(dni,dni+1);

        if (reference.equalsIgnoreCase(letra)){
            System.out.println("son iguales. Es NIF. "+letra+" "+reference);
            return true;
        }else{
            System.out.println("NO son iguales. NO es NIF. "+letra+" "+reference);
            return false;
        }
    }
    else
        return false;
    }
    public int CompruebaCodPost(String cod,String municipio,String localidad,Conexion conex){
        int val=0;
        
        PymesDao miPymesDao1 = new PymesDao();
		
	val = miPymesDao1.compruebaCodPost(conex,cod,municipio) ;
       
        
        return val;
    }
    
     public String BuscaCodPost(String cod,Conexion conex){
        String res = "";
        
        PymesDao miPymesDao1 = new PymesDao();
		
	res = miPymesDao1.buscarCodPost(conex,cod) ;
       
        
        return res;
    }

    @Override
    protected Void doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    // --------------------------------------------------------------------------------------------------------------------------------
    public void reordenarTabla(int nReg) {
        
        int i=0, j=0, k=0, cnt=0,flag=0,salto=0;
        String titular,titrep;
        // ....................                         COPIAMOS TABLA A TABLA RESULTADO
        for (j =0 ; j<nReg; j++) {
            
            this.tablaOrd[j] = this.tablaDatos[j] ;
                       
        }
               
        // ....................                        INICIAMOS PROCESO DE REOORDENACIÓN
        while (cnt<nReg){       
            // ....................
       
            titular = this.tablaOrd[cnt][9] ;
              
            cnt ++;
            
            // .................................        BORRAMOS TABLA TEMPORAL
            for (j=0;j<nReg; j++){
                for (k=0;k<40; k++){
                    this.tablaTmp[i][k]="";
                }                             
            }
        
         
         // ...................                         COPIAMOS A TABLA TMP TABLA ORDENADA TEMPORAL
         
         for (j=0; j<cnt; j++){
             
             this.tablaTmp[j] = this.tablaOrd[j] ;
             
         }
                 
         // ....................                        BUSCAMOS EN LISTA semi ordenada
         flag = 0 ;
         j = cnt+1 ;
         while(j<nReg ){
            
             titrep = this.tablaOrd[j][9] ;
             
             if (titular.equals(titrep)){               // Si encuentra uno igual
                 
                 System.out.println("HE ENCONTADO COINCIDENCIA = "+titrep);
                 salto = j;                 
                 flag = 1 ;
                 break;                     // salimos de la busqueda
             }         
             j++;             
         }
         if (flag == 1) {
             this.tablaTmp[cnt] = this.tablaOrd[j] ;
             
         }else {
             this.tablaTmp[cnt] = this.tablaOrd[cnt] ;
             
         }
         
        }
            
    }
    
}

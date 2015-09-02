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
 public String tablaDatos[][]           = new String[5000][50] ;    // Tabla  con los datos a procesar.
 public String tablaRes1[][]            = new String[5000][50];
 public String tablaRes2[][]            = new String[5000][50];
 public String tablaErrorCod[][]        = new String[5000][50];
 public String tablaLocuciones[][]      = new String[5000][50];
 public String tablaCertificaciones[][] = new String[5000][50];
 
 public Integer tablaErrores[][]        = new Integer[5000][50];
 public Integer tablaConfigura[]        = new Integer[30];
 
 public int nRegistros      = 0 ;
 public int nIncompletos    = 0 ;
 public int nCompletos      = 0 ;
 public int nLocuciones     = 0;
 public int nCertificaciones= 0; 
 public int nDias           = 0;
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
int dia     = 0 ;           // Cuenta dias de la locucion

String str="", sdia,idia, sidia="";
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
    reader.readRecord() ;                           // Nos saltamos el titulo de la hoja
    reader.readRecord() ;                           // Nos saltamos la cabecera
while (reader.readRecord())
{
    
        
     if ( intcnt < n ) {
      
         
                String fecha        = reader.get(0);
                String codigo       = reader.get(1);
                String nombre_tit   = reader.get(2);
                String persona_fir  = reader.get(3);
                String edad_pyme    = reader.get(5);
                String apoderado_p  = reader.get(3);
                String telefono     = reader.get(6);
                String contrato     = reader.get(7);
                String direccion    = reader.get(4);
                String dni_fac      = reader.get(13);
                String trato        = reader.get(9);
                String acreditacion = reader.get(10);
                String firma        = reader.get(11);
                String copia        = reader.get(12);
                String observaciones= reader.get(14);
                String hora         = reader.get(15);
                String llam1        = reader.get(16);
                String llam2        = reader.get(17);
                String llam3        = reader.get(18);
                String llam4        = reader.get(19);
                String llam5        = reader.get(20);
               
                sdia = fecha ;  idia = fecha ;
                
                System.out.println("sdia ="+sdia);
                
                sdia = sdia.trim(); sdia= sdia.substring(0,3); sdia = sdia.toUpperCase(); // System.out.println("llam1 ="+llam1);
                
                
                if (sdia.equals("DIA") || sdia.equals("DÍA") ){ 
                    dia ++ ; this.nDias = dia; 
                    idia = idia.substring(4); idia = idia.trim();  sidia=idia; //  System.out.println("sidia ="+sidia);
               
                
                }                     // ha cambiado el dia, no hay datos que introducir en esta linea
               
                else {
         //      tablaLocuciones[intcnt][20] = String.valueOf(dia);
                tablaLocuciones[intcnt][20] = sidia;
                tablaLocuciones[intcnt][21] = "-1";
                tablaLocuciones[intcnt][0]  = fecha ;
                tablaLocuciones[intcnt][1]  = codigo;
                tablaLocuciones[intcnt][2]  = nombre_tit.toUpperCase();
                tablaLocuciones[intcnt][3]  = persona_fir.toUpperCase();
                tablaLocuciones[intcnt][4]  = edad_pyme;
                tablaLocuciones[intcnt][5]  = apoderado_p.toUpperCase();
                tablaLocuciones[intcnt][6]  = telefono;
                tablaLocuciones[intcnt][7]  = contrato.toUpperCase();
                tablaLocuciones[intcnt][8]  = direccion.toUpperCase();
                tablaLocuciones[intcnt][9]  = dni_fac.toUpperCase();
                tablaLocuciones[intcnt][10] = trato.toUpperCase();
                tablaLocuciones[intcnt][11] = acreditacion.toUpperCase();
                tablaLocuciones[intcnt][12] = firma.toUpperCase();
                tablaLocuciones[intcnt][13] = copia.toUpperCase();
                tablaLocuciones[intcnt][14] = observaciones;
                tablaLocuciones[intcnt][15] = hora;
                tablaLocuciones[intcnt][16] = llam1;
                tablaLocuciones[intcnt][17] = llam2;
                tablaLocuciones[intcnt][18] = llam3;
                tablaLocuciones[intcnt][19] = llam4;
                tablaLocuciones[intcnt][22] = llam5;
                
             //   System.out.println( intcnt+"DIA="+dia+" -Fecha="+ fecha + " - Nombre: " + nombre_tit  + " -" + " - contrato = "+ tablaDatos[intcnt][14] );
                                
                intcnt ++ ;
                        
                }
               
            
       } else {
            
                break;
       }
}

this.nLocuciones = intcnt ;              // Apuntamos cuantos registros hemos cargado en memoria

intcnt = 0; 

while (intcnt < n){
    for (d=0 ; d < 7 ; d++ ) {
        resultado = resultado+tablaLocuciones[intcnt][d]+",";
    }
    resultado = resultado+"\n"  ;
    intcnt ++ ;
}

} catch (IOException e) {
e.printStackTrace();
}

reader.close();
writer.close();

return resultado;  

}
// ------------------------------------------------------------------------------------------------------------------

public String ProcesaDatosCertificaciones(File nombre,int n,int p) throws IOException  {
    

// int n       = 288 ;         // Numero de elementos a leer de la tabla
// int p       = 2 ;           // Posicion de inicio de lectura de la tabla
    
int d       = 1 ;
int cnt     = 0 ;           // contador principal
int intcnt  = 0 ;           // contador interno.   
int dia     = 0 ;           // Cuenta dias de la locucion

String str="", sdia,idia, sidia="";
String resultado = "";
// String tablaDatos[][] = new String[20][500] ;    // Tabla temporal con los datos a procesar.
String strFile="";


strFile= nombre.getAbsolutePath();

//Le pasamos la URL del archivo CSV a leer.

CsvReader reader = new CsvReader(strFile);


try {
    reader.readRecord() ;                           // Nos saltamos el titulo de la hoja
    reader.readRecord() ;                           // Nos saltamos la cabecera
while (reader.readRecord())
{
    
        
     if ( intcnt < n ) {
      
         
                String fecha        = reader.get(0);
                String codigo       = reader.get(1);
                String nombre_tit   = reader.get(2);
                String persona_fir  = reader.get(3);
                String edad_pyme    = reader.get(5);
                String apoderado_p  = reader.get(6);
                String telefono     = reader.get(6);
                String contrato     = reader.get(7);
                String direccion    = reader.get(8);
                String dni_fac      = reader.get(9);
                String trato        = reader.get(10);
                String acreditacion = reader.get(11);
                String firma        = reader.get(12);
                String copia        = reader.get(13);
                String observaciones= reader.get(14);
                String hora         = reader.get(16);
                String llam1        = reader.get(16);
                String llam2        = reader.get(17);
                String llam3        = reader.get(18);
                String llam4        = reader.get(19);
               
                sdia = fecha ;  idia = fecha ;
                sdia = sdia.trim(); sdia= sdia.substring(0,3); sdia = sdia.toUpperCase(); // System.out.println("llam1 ="+llam1);
                
                
                if (sdia.equals("DIA") || sdia.equals("DÍA") ){ 
                    dia ++ ; this.nDias = dia; 
                    idia = idia.substring(4); idia = idia.trim();  sidia=idia; //  System.out.println("sidia ="+sidia);
               
                
                }                     // ha cambiado el dia, no hay datos que introducir en esta linea
               
                else {
         //      tablaLocuciones[intcnt][20] = String.valueOf(dia);
                tablaCertificaciones[intcnt][20] = sidia;
                tablaCertificaciones[intcnt][21] = "-1";
                tablaCertificaciones[intcnt][0]  = fecha ;
                tablaCertificaciones[intcnt][1]  = codigo;
                tablaCertificaciones[intcnt][2]  = nombre_tit.toUpperCase();
                tablaCertificaciones[intcnt][3]  = persona_fir.toUpperCase();
                tablaCertificaciones[intcnt][4]  = edad_pyme;
                tablaCertificaciones[intcnt][5]  = apoderado_p.toUpperCase();
                tablaCertificaciones[intcnt][6]  = telefono;
                tablaCertificaciones[intcnt][7]  = contrato.toUpperCase();
                tablaCertificaciones[intcnt][8]  = direccion.toUpperCase();
                tablaCertificaciones[intcnt][9]  = dni_fac.toUpperCase();
                tablaCertificaciones[intcnt][10] = trato.toUpperCase();
                tablaCertificaciones[intcnt][11] = acreditacion.toUpperCase();
                tablaCertificaciones[intcnt][12] = firma.toUpperCase();
                tablaCertificaciones[intcnt][13] = copia.toUpperCase();
                tablaCertificaciones[intcnt][14] = observaciones;
                tablaCertificaciones[intcnt][15] = hora;
                tablaCertificaciones[intcnt][16] = llam1;
                tablaCertificaciones[intcnt][17] = llam2;
                tablaCertificaciones[intcnt][18] = llam3;
                tablaCertificaciones[intcnt][19] = llam4;
                
             //   System.out.println( intcnt+"DIA="+dia+" -Fecha="+ fecha + " - Nombre: " + nombre_tit  + " -" + " - contrato = "+ tablaDatos[intcnt][14] );
                                
                intcnt ++ ;
                        
                }
               
            
       } else {
            
                break;
       }
}

this.nCertificaciones = intcnt ;              // Apuntamos cuantos registros hemos cargado en memoria

intcnt = 0; 

while (intcnt < n){
    for (d=0 ; d < 7 ; d++ ) {
        resultado = resultado+tablaLocuciones[intcnt][d]+",";
    }
    resultado = resultado+"\n"  ;
    intcnt ++ ;
}

} catch (IOException e) {
e.printStackTrace();
}

reader.close();

return resultado;  

}
// ------------------------------------------------------------------------------------------------------------------

 public void punteaRegistros(int nReg,String[][] tabla,String ccStr1,String ccStr2)  {
     
     // ........................................
     int i,j,k,res;
     int nC = 30;                                                       // numero de campos
     // ........................................
     String dni,sdni,ldni, cups,cupsi,cupsf, codigopost,telef, fecha, municipio, provincia,municipio_error,cups2;
     char letra,letrac,fletra;
     int nStr, error=0 ;    
     int existe[]        = {0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0} ; 
        
     int progress = 0;
     int coefprog = 100 / nReg ;
     setProgress(0);    
     
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
                                        
                    res = CompruebaCodPost(codigopost,municipio,provincia,ccStr1,ccStr2) ;
                                       
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
                            municipio_error = BuscaCodPost(codigopost,ccStr1,ccStr2);
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
      setProgress(Math.min(coefprog*i, 100));  
        
     }
     
      // .................... 
     
     for (i=0; i<nReg; i++) {
         error = 0 ;
         for (j=0; j<nC; j++){
             
             if (this.tablaErrores[i][j] == 1 && error ==0 ) {
                 error = 1;
                 tabla[i][1] = "1" ;
                 this.tablaRes1[this.nIncompletos] = tabla[i] ; System.out.println("this.tablaRes1["+this.nIncompletos+"][28] = tabla["+i+"][28] = ("+ this.tablaRes1[this.nIncompletos][28] +")("+tabla[i][28]+")");
                 this.nIncompletos = this.nIncompletos + 1 ;
             }             
         }
         if (error == 0){
               this.tablaRes2[this.nCompletos] = tabla[i] ;
               this.nCompletos = this.nCompletos + 1 ;             
         }       
     }
    
     setProgress(Math.min(100, 100)); 
    
    
     
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
// -------------------------------------------------------------------------------------------------------------
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
// -------------------------------------------------------------------------------------------------------------
    public int CompruebaCodPost(String cod,String municipio,String localidad,String str1,String str2){
        int val=0;
        
        PymesDao miPymesDao1 = new PymesDao();
		
	val = miPymesDao1.compruebaCodPost(str1,str2,cod,municipio) ;
       
        
        return val;
    }
    // -------------------------------------------------------------------------------------------------------------
     public String BuscaCodPost(String cod,String str1,String str2){
        String res = "";
        
        PymesDao miPymesDao1 = new PymesDao();
		
	res = miPymesDao1.buscarCodPost(str1,str2,cod) ;
       
        
        return res;
    }
// -------------------------------------------------------------------------------------------------------------
    @Override
    protected Void doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
}

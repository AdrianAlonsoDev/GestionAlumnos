/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionalumnos;

import gestionalumnos.modelos.Notas;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author adria
 */
public class GestionNotas {
    
    public GestionNotas() {
    }

    //Arrays para almacenar en memoria las asignaturas y cursos
    //y mostrarlas en la interfaz, luego estas se almacenan en un fichero.
    public static ArrayList<Notas> notas = new ArrayList<>();
    private final GestionAsignaturas asignaturaAux = new GestionAsignaturas();

    /* @generarAsignaturas
    * Plantilla de asignaturas y cursos.
    * Se ejecuta cuando se abre por primera vez el
    * programa o cuando el archivo Asignaturas.dat es eliminado.
     */
    public void generarNotas() {
        //Comprobamos antes de generar alumnos quelas asignaturas exsisten.   
        if (Files.exists(Path.of("Asignaturas.dat"))) {

            // Comprobamos ahora el fichero Notas.dat, si el fichero no existe lo creamos   
            if (!Files.exists(Path.of("Notas.dat"))) {
                File fichero = new File("Notas.dat");

                //Limpiamos el ArrayList de notas por si acaso
                limpiarArrays();
                
                try {
                    fichero.createNewFile();
                } catch (IOException e) {
                    System.out.println("IOException " + e);
                }
                
                
                Random rd = new Random();
                
                int[] notasLista = new int[8]; 
                
                for (int i = 0; i < notasLista.length; i++) {
                   notasLista[i] = rd.nextInt(11); 
                }
                
                /*
                 * Populamos los ArrayLists
                 */
                System.out.println("GRABO LOS DATOS DE LAS NOTAS.");
                
                for (int i = 0; i < asignaturaAux.asignaturas.size(); i++) {                                        
                    int convocatoriaRandom = ThreadLocalRandom.current().nextInt(2017, 2022);
                    Notas nota = new Notas(asignaturaAux.asignaturas.get(i), notasLista, convocatoriaRandom);
                    escribirNota(nota);
                    notas.add(nota);
                }

                //Si el fichero existe en un primer lugar, pasamos a leerlo.    
            } else {
                System.out.println("El fichero ya Notas existe, cargándolo");
                try {
                    leerNotas();
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Error en leerNotas() " + e);
                }
            }
        } else {
            System.out.println("No se pueden generar las notas porque faltas las asignaturas, generandolas ahora mismo...");
            asignaturaAux.generarAsignaturas();
        }
    }

    /* @leerAsignaturas
    * Se Ejecuta cuando el fichero Asignaturas.dat
    * existe y limpia las arrays para rellenarlas con
    * los datos del archivo Asignaturas.
     */
    public void leerNotas() throws IOException, ClassNotFoundException {
        //Limpiamos los ArrayLists por si acaso
        limpiarArrays();

        //Si el fichero existe
        if (Files.exists(Path.of("Notas.dat"))) {
            //Declaramos el fichero
            File fichero = new File("Notas.dat");

            //Cargamos en memoria los datosdel fichero
            ObjectInputStream dataIS;
            dataIS = new ObjectInputStream(new FileInputStream(fichero));

            System.out.println("LEYENDO ARCHIVO DE NOTAS");

            //Lectura del fichero
            try {
                while (true) {
                    // Creamos un objeto asignatura leyendo del fichero 
                    Notas nota = (Notas) dataIS.readObject();
                    System.out.printf("Nota de " + nota.getAsignatura() + ": " + Arrays.toString(nota.getNotas()));
                    notas.add(nota);
                }
            } catch (EOFException eo) {
                System.out.println("FIN DE LECTURA.");
                System.out.println(notas.toString());
            } catch (StreamCorruptedException x) {
                System.out.println("Error en StreamCorruptedException" + x);
            }
            dataIS.close(); // cerrar stream de entrada
        } else {
            System.out.println("El fichero Notas.dat no existe, creandolo desde la plantilla...");
            generarNotas();
        }
    }

    public void añadirNotas(Notas nota) {
        //La añadimos en memoria (ArrayList)
        notas.add(nota);
        
        //Recreamos el fichero con la información en memoria
        if (Files.exists(Path.of("Notas.dat"))) {
            File fichero = new File("Notas.dat");
            fichero.delete();
            try {
                fichero.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GestionAsignaturas.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Una vezcreado el fichero lo populamos con cada dato del array.
            for (int i = 0; i < notas.size(); i++) {
                escribirNota(notas.get(i));
            }
        }
    }

    public void escribirNota(Notas nota) {
        if (Files.exists(Path.of("Notas.dat"))) {
            try {
                //Declara el fichero
                File fichero = new File("Notas.dat");
                FileOutputStream fileout = new FileOutputStream(fichero, true);
                //Crea el flujo de salida
                //Conecta el flujo de bytes al flujo de datos
                ObjectOutputStream dataOS;
                try {
                    dataOS = new ObjectOutputStream(fileout);
                    //Escribo la persona en el fichero
                    dataOS.writeObject(nota); 
                    dataOS.close();
                } catch (IOException e) {
                    System.out.println("IOException " + e);
                }
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFoundException " + e);
            }
        } else {
            System.out.println("No se puede escribir la nota porque no existe el fichero Notas.dat");
            generarNotas();
        }
    }

    /* @limpiarArrays
    * Vaciar los ArrayList de Asignaturas y Cursos
     */
    public void limpiarArrays() {
        notas.clear();
    }
}

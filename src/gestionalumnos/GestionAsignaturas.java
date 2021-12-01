/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionalumnos;

import gestionalumnos.modelos.Asignatura;
import gestionalumnos.modelos.Curso;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adria
 */
public class GestionAsignaturas {

    public GestionAsignaturas() {
    }

    //Arrays para almacenar en memoria las asignaturas y cursos
    //y mostrarlas en la interfaz, luego estas se almacenan en un fichero.
    public static ArrayList<Asignatura> asignaturas = new ArrayList<>();
    public static ArrayList<Curso> cursos = new ArrayList<>();

    /* @generarAsignaturas
    * Plantilla de asignaturas y cursos.
    * Se ejecuta cuando se abre por primera vez el
    * programa o cuando el archivo Asignaturas.dat es eliminado.
    */
    public void generarAsignaturas() {
        // Si el fichero no existe lo creamos   
        if (!Files.exists(Path.of("Asignaturas.dat"))) {
            System.out.println("(generarAsignaturas) Asignaturas.dat no existe");
            File fichero = new File("Asignaturas.dat");
        
            try {
                fichero.createNewFile();
                System.out.println("(generarAsignaturas) Asignaturas.dat creado");
                
            } catch (IOException e) {
                System.out.println("(generarAsignaturas) Asignaturas.dat falló al crearse" + " \nIOException " + e);
            }
            
            //Arrays normales que almacenan la plantilla de datos.
            String nombres[] = {"Lengua", "Matemáticas", "Conocimiento", "Física y química",
                "Educación Física", "Religion", "Inglés"};
            
            String cursosNombre[] = {"Primero", "Segundo", "Tercero", "Cuarto", "Primero Bachiller", "Segundo Bachiller" , "Apoyo"};

            //Limpiamos los ArrayLists por si acaso
            limpiarArrays();
           
            /*
            * Populamos los ArrayLists
            */
            System.out.println("(generarAsignaturas) GRABO LOS DATOS DE LA ASIGNATURA Y CURSOS.");
            
            //Rellenamos el ArrayList con cursos por defecto
            for (String cursosNombre1 : cursosNombre) {
                Curso curso = new Curso(cursosNombre1);
                cursos.add(curso);
            }
                              
            //Rellenamos el ArrayList con asignaturas por defecto           
            for (int i = 0; i < nombres.length; i++) { //recorro los arrays
                Asignatura asignatura = new Asignatura(nombres[i], cursos.get(i)); //creo la persona
                //Escribo la asignatura en el fichero.
                try {
                 escribirAsignatura(asignatura);
                } catch(IOException e) {
                    System.out.println("(generarAsignaturas) Falló el escribirAsignatura()\n IOException " + e);
                }
                //Guardamos las asignatura en memoria
                asignaturas.add(asignatura);
            }            
        //Si el fichero existe en un primer lugar, pasamos a leerlo.    
        } else {
            System.out.println("(generarAsignaturas) El fichero Asignaturas ya existe, cargándolo");
            try {
                leerAsignaturas();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("\nError en leerAsignaturas() " + e);
            }
        }
    }

    
    /* @leerAsignaturas
    * Se Ejecuta cuando el fichero Asignaturas.dat
    * existe y limpia las arrays para rellenarlas con
    * los datos del archivo Asignaturas.
    */
    public void leerAsignaturas() throws IOException, ClassNotFoundException {
        //Limpiamos los ArrayLists por si acaso
        limpiarArrays();
        
        //Si el fichero existe
        if (Files.exists(Path.of("Asignaturas.dat"))) {
            System.out.println("(leerAsignaturas) Fichero Asignaturas.dat existe");
            
            //Cargamos en memoria los datosdel fichero

            System.out.println("(leerAsignaturas) LEYENDO ARCHIVO DE ASIGNATURAS");           
            

            Asignatura asignatura; // defino la variable persona
            ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream("Asignaturas.dat"));
                try {
                    while (true) {
                        // lectura del fichero
                        // leer una Persona
                        asignatura = (Asignatura) dataIS.readObject();
                        asignaturas.add(asignatura);
                        System.out.printf("Nombre: %s", asignatura.getNombre());
                        
                    }
                }catch(EOFException e) {
                    System.out.println("(leerAsignaturas) FIN LECTURA ARCHIVO");
                } catch(StreamCorruptedException e) {
                    
                    System.out.println("(leerAsignaturas) StreamCorruptedException " + e);
                    
                }
                
                dataIS.close();
                // cerrar stream de entrada
            
             // cerrar stream de entrada
            System.out.println("(leerAsignaturas) Fichero Asignaturas.dat cerrado ");
        } else {
            System.out.println("(leerAsignaturas) El fichero Asignaturas.dat no existe, creandolo desde la plantilla...");
            generarAsignaturas();
        }
    }

    public void añadirAsignatura(String nombre, Curso curso) {
        Asignatura asignatura = new Asignatura(nombre, curso);
        asignaturas.add(asignatura);
        if (Files.exists(Path.of("Asignaturas.dat"))) {
            File fichero = new File("Asignaturas.dat");
            fichero.delete();
            try {
                fichero.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GestionAsignaturas.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int i = 0; i < asignaturas.size(); i++) {
                try {
                    escribirAsignatura(asignaturas.get(i));
                } catch(IOException e) {
                    System.out.println("(añadirAsignatura) IOException " + e );
                }
            }
        }
    }

    public void escribirAsignatura(Asignatura asignatura) throws IOException {
        if (Files.exists(Path.of("Asignaturas.dat"))) {
                //Declara el fichero
                File fichero = new File("Asignaturas.dat"); 
                FileOutputStream fileOut = new FileOutputStream(fichero, true);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(asignatura);
                System.out.println("(escribirAsignatura) Escrito la asignatura: " + asignatura.getNombre());
                objectOut.close();
                fileOut.close();
                
        } else {
            System.out.println("escribirAsignatura) No se puede escribir la asignatura porque no existe el fichero Asignaturas.dat");
        }
    }
    
    /* @limpiarArrays
     * Vaciar los ArrayList de Asignaturas y Cursos
     */
    public void limpiarArrays() {
        asignaturas.clear();
        cursos.clear();       
    }
}

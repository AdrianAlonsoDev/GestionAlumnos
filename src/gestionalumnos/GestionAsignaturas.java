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

            String cursosNombre[] = {"Primero", "Segundo", "Tercero", "Cuarto", "Primero Bachiller", "Segundo Bachiller", "Apoyo"};

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
                //Guardamos las asignatura en memoria
                asignaturas.add(asignatura);
            }

            escribirAsignatura(asignaturas);
            //Si el fichero existe en un primer lugar, pasamos a leerlo.    
        } else {
            System.out.println("(generarAsignaturas) El fichero Asignaturas ya existe, cargándolo");
            leerAsignaturas();
        }
    }

    /* @leerAsignaturas
    * Se Ejecuta cuando el fichero Asignaturas.dat
    * existe y limpia las arrays para rellenarlas con
    * los datos del archivo Asignaturas.
     */
    public void leerAsignaturas() {
        //Limpiamos los ArrayLists por si acaso
        limpiarArrays();

        //Si el fichero existe
        if (Files.exists(Path.of("Asignaturas.dat"))) {

            //Cargamos en memoria los datosdel fichero
            System.out.println("(leerAsignaturas) LEYENDO ARCHIVO DE ASIGNATURAS");

            try {
                ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream("Asignaturas.dat"));

                // Se lee el primer objeto
                asignaturas = (ArrayList) ois.readObject();

                // Mientras haya objetos
                ois.close();
                // cerrar stream de entrada

                System.out.println(asignaturas.toString());
                System.out.println("(leerAsignaturas) Fichero Asignaturas.dat cerrado ");
            } catch (EOFException e) {
                System.out.println("(leerAsignaturas) FIN LECTURA ARCHIVO");
            } catch (Exception e2) {
                System.out.println("error:" + e2);
                e2.printStackTrace();
            }

            // cerrar stream de entrada
        } else {
            System.out.println("(leerAsignaturas) El fichero Asignaturas.dat no existe, creandolo desde la plantilla...");
            generarAsignaturas();
        }
    }

    public void añadirAsignatura(String nombre, Curso curso) {
        Asignatura asignatura = new Asignatura(nombre, curso);
        asignaturas.add(asignatura);
        escribirAsignatura(asignaturas);
        
    }

    public void escribirAsignatura(ArrayList lista) {
        if (Files.exists(Path.of("Asignaturas.dat"))) {
            //Declara el fichero
            try {
                ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("Asignaturas.dat"));
                objectOut.writeObject(lista);
                System.out.println("(escribirAsignatura) Escrito la lista de asignaturas: ");
                objectOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionalumnos;

import gestionalumnos.interfaz.GUI;
import gestionalumnos.modelos.Asignatura;
import gestionalumnos.modelos.Curso;
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
    public static ArrayList<Notas> notasCargadas = new ArrayList<>();

    /* @generarAsignaturas
    * Plantilla de asignaturas y cursos.
    * Se ejecuta cuando se abre por primera vez el
    * programa o cuando el archivo Asignaturas.dat es eliminado.
     */
    public void generarNotas() {
        
        // Si el fichero no existe lo creamos   
        if (Files.exists(Path.of("Asignaturas.dat"))) {
            if (!Files.exists(Path.of("Notas.dat"))) {
                System.out.println("\nGRABO LOS DATOS DE LAS NOTAS.");

                File fichero = new File("Notas.dat");

                System.out.println("(generarNotas) Notas.dat no existe");

                try {
                    fichero.createNewFile();
                    System.out.println("(generarNotas) Notas.dat creado");

                } catch (IOException e) {
                    System.out.println("(generarNotas) Notas.dat falló al crearse" + " \nIOException " + e);
                }

                Random rd = new Random();

                int[] notasLista = new int[8];

                for (int i = 0; i < notasLista.length; i++) {
                    notasLista[i] = rd.nextInt(11);
                }

                /*
                 * Populamos los ArrayLists
                 */
                for (int i = 0; i < GUI.asignaturaAux.asignaturas.size(); i++) {
                    int convocatoriaRandom = ThreadLocalRandom.current().nextInt(2017, 2022);
                    Notas nota = new Notas(GUI.asignaturaAux.asignaturas.get(i), notasLista, convocatoriaRandom);
                    notasCargadas.add(nota);
                    System.out.println("Añadido la nota de: " + notasCargadas.get(i).getAsignatura(true));

                }

                    escribirNota(notasCargadas);
                //Si el fichero existe en un primer lugar, pasamos a leerlo.    
            } else {
                System.out.println("(generarNotas) El fichero Notas ya existe, cargándolo");
                leerNotas();
            } 
        } else {
            System.out.println("El fichero Asignaturas no existe, generandolo");
            GUI.asignaturaAux.generarAsignaturas();
        }
    }
    
   /* @leerNotas
    * Se Ejecuta cuando el fichero Notas.dat
    * existe y limpia las arrays para rellenarlas con
    * los datos del archivo.
    */
    public void leerNotas() {
        
        System.out.println("\n LEYENDO ARCHIVO DE NOTAS");

        //Limpiamos los ArrayLists por si acaso
        limpiarArrays();

        //Si el fichero existe
        if (Files.exists(Path.of("Notas.dat"))) {
                        
            //Cargamos en memoria los datosdel fichero            
            try {
                ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream("Notas.dat"));

                // Se lee el primer objeto
                notasCargadas = (ArrayList) ois.readObject();

                // Mientras haya objetos
                ois.close();
                // cerrar stream de entrada
                for (int i = 0; i < notasCargadas.size(); i++) {
                    System.out.println("Leyendo notas de: " + notasCargadas.get(i).getAsignatura(true));
                }
                System.out.println("(leerNotas) Fichero Notas.dat cerrado ");
            } catch (EOFException e) {
                System.out.println("(leerNotas) FIN LECTURA ARCHIVO");
            } catch (Exception e2) {
                System.out.println("error:" + e2);
                e2.printStackTrace();
            }

            // cerrar stream de entrada
        } else {
            System.out.println("(leerNotas) El fichero Notas.dat no existe, creandolo desde la plantilla...");
            generarNotas();
        }
    }

    public void añadirNotas(Notas nota) {
        //La añadimos en memoria (ArrayList)
        notasCargadas.add(nota);
        
        //Recreamos el fichero con la información en memoria
        if (Files.exists(Path.of("Notas.dat"))) {
                escribirNota(notasCargadas);

        } else {
            System.out.println("(añadirNotas) No existe Notas.dat");
        }
    }

    public void escribirNota(ArrayList lista) {
        if (Files.exists(Path.of("Notas.dat"))) {
            //Declara el fichero
            try {
                ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("Notas.dat"));
                objectOut.writeObject(lista);
                System.out.println("(escribirNota) Escrito la lista de notas en el fichero.");
                objectOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("escribirNota) No se puede escribir la asignatura porque no existe el fichero Asignaturas.dat");
        }
    }

    /* @limpiarArrays
     * Vaciar los ArrayList de Asignaturas y Cursos
     */
    public void limpiarArrays() {
        notasCargadas.clear();
    }
}

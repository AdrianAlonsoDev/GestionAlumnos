/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionalumnos;

import gestionalumnos.interfaz.GUI;
import gestionalumnos.interfaz.Utils;
import gestionalumnos.modelos.Alumno;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adria
 */
public class GestionAlumnos {

    public GestionAlumnos() {
    }

    //Arrays para almacenar en memoria las asignaturas y cursos
    //y mostrarlas en la interfaz, luego estas se almacenan en un fichero.
    public static ArrayList<Alumno> alumnos = new ArrayList<>();


    /* @generarAlumnos
    * Plantilla de alumnos.
    * Se ejecuta cuando se abre por primera vez el
    * programa o cuando el archivo Alumnos.dat es eliminado.
     */
    public void generarAlumnos() {
        System.out.println("\n GRABO LOS DATOS DE LOS ALUMNOS.");
        //Comprobamos antes de generar alumnos quelas asignaturas exsisten.   
        if (Files.exists(Path.of("Asignaturas.dat"))) {

            // Comprobamos ahora el fichero Alumnos.dat, si el fichero no existe lo creamos   
            if (!Files.exists(Path.of("Alumnos.dat"))) {
                File fichero = new File("Alumnos.dat");

                try {
                    fichero.createNewFile();
                } catch (IOException e) {
                    System.out.println("IOException " + e);
                }

                //Arrays normales que almacenan la plantilla de datos.
                String nombres[] = {"Ana", "Luis Miguel", "Alicia", "Pedro", "Manuel", "Andrés", "Julio", "Antonio", "María Jesús"};

                String dnis[] = {"16318913E", "32188392F", "85992696C", "01663862L", "01663862L", "01663862L", "53485653G", "94157264W", "57485635X"};

                String apellidos[] = {"Lopez Garcia", "Buenafuente Plaza", "Rodriguez Ligero", "Gomez Agua",
                    "Cortés Perez", "Alonso Pueblo", "Perez Gomez", "Martinez Garcia", "Guerrero López"};

                LocalDate fechas[] = {LocalDate.parse("2016-08-16"), LocalDate.parse("2016-08-16"), LocalDate.parse("2016-08-16"),
                    LocalDate.parse("2016-08-16"), LocalDate.parse("2016-08-16"), LocalDate.parse("2016-08-16"),
                    LocalDate.parse("2016-08-16"), LocalDate.parse("2016-08-16"), LocalDate.parse("2016-08-16")};

                //Limpiamos los ArrayLists por si acaso
                limpiarArrays();

                /*
                 * Populamos los ArrayLists
                 */
                
                //Rellenamos el ArrayList con asignaturas por defecto           
                for (int i = 0; i < nombres.length; i++) { //recorro los arrays
                    Alumno alumno = new Alumno(nombres[i], apellidos[i], dnis[i], fechas[i], GUI.asignaturaAux.asignaturas, GUI.notaAux.notasCargadas); //creo la persona
                    System.out.println("Añadido el alumno: " + nombres[i] + " Con dni: " + dnis[i]);
                    //Guardamos las asignatura en memoria
                    alumnos.add(alumno);
                }

                escribirAlumno(alumnos);

                //Si el fichero existe en un primer lugar, pasamos a leerlo.    
            } else {
                System.out.println("El fichero  Alumnos ya existe, cargándolo");

                leerAlumnos();
            }
        } else {
            System.out.println("No se pueden generar los alumnos porque faltas las asignaturas, generandolas ahora mismo...");
            GUI.asignaturaAux.generarAsignaturas();
        }
    }

    /* @leerAlumnos
    * Se Ejecuta cuando el fichero Alumnos.dat
    * existe y limpia las arrays para rellenarlas con
    * los datos del archivo.
     */
    public void leerAlumnos() {
        System.out.println("\n LEYENDO ARCHIVO DE ALUMNOS");

        //Limpiamos los ArrayLists por si acaso
        limpiarArrays();

        //Si el fichero existe
        if (Files.exists(Path.of("Alumnos.dat"))) {
            try {
                //Declaramos el fichero
                File fichero = new File("Alumnos.dat");

                //Cargamos en memoria los datosdel fichero
                ObjectInputStream dataIS;
                dataIS = new ObjectInputStream(new FileInputStream(fichero));

                //Lectura del fichero

                alumnos = (ArrayList) dataIS.readObject();
                for (int i = 0; i < alumnos.size(); i++) {
                    System.out.println("Leido el alumno: " + alumnos.get(i).toString());
                }

                dataIS.close(); // cerrar stream de entrada
            } catch (EOFException e) {
                System.out.println("(leerAsignaturas) FIN LECTURA ARCHIVO");
            } catch (Exception e2) {
                System.out.println("error:" + e2);
                e2.printStackTrace();
            }
        } else {
            System.out.println("El fichero Alumnos.dat no existe, creandolo desde la plantilla...");
            generarAlumnos();
        }
    }

    public boolean añadirAlumno(String nombre, String apellidos, String dni, LocalDate fechaNac, ArrayList<Asignatura> listaAsignaturas, ArrayList<Notas> notasAlumno) {
        boolean finished = false;

        Utils util = new Utils();

        if (util.comprobarDNI(dni) == true) {

            Alumno alumno = new Alumno(nombre, apellidos, dni, fechaNac, listaAsignaturas, notasAlumno);
            alumnos.add(alumno);
            if (Files.exists(Path.of("Alumnos.dat"))) {
                escribirAlumno(alumnos);
                finished = true;
                System.out.println("El alumno ha sido añadido a la memoria y al fichero.");
            } else {
                System.out.println("El fichero alumnos no exsiste, no se ha podido añadir el alumno");
                return finished;
            }
        } else {
            System.out.println("El DNI no exsiste, no se ha podido añadir el usuario");
            return finished;
        }
        return finished;

    }

    public void escribirAlumno(ArrayList lista) {
        if (Files.exists(Path.of("Alumnos.dat"))) {
            try {
                File fichero = new File("Alumnos.dat"); //declara el fichero
                FileOutputStream fileout = new FileOutputStream(fichero);
                //crea el flujo de salida
                //conecta el flujo de bytes al flujo de datos
                ObjectOutputStream dataOS;
                try {
                    dataOS = new ObjectOutputStream(fileout);

                    dataOS.writeObject(lista); //escribo la persona en el fichero
                    System.out.println("Escrito la lista de Alumnos en el fichero");
                    dataOS.close();
                } catch (IOException e) {
                    System.out.println("IOException " + e);
                }
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFoundException " + e);
            }
        } else {
            System.out.println("No se puede escribir el alumno porque no existe el fichero Alumno.dat");
            generarAlumnos();
        }
    }

    /* @limpiarArrays
     * Vaciar los ArrayList de Asignaturas y Cursos
     */
    public void limpiarArrays() {
        alumnos.clear();
    }

}

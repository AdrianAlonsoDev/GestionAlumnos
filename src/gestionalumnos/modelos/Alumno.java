/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionalumnos.modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author adria
 */
public class Alumno implements Serializable {

    //Attributes
    private String nombre;
    private String apellidos;
    private String dni;
    private LocalDate fechaNac;
    private ArrayList<Asignatura> listaAsignaturas;
    private ArrayList<Notas> notasAlumno;
    

    //ConstructorsS
    public Alumno() {
    }

    public Alumno(String nombre, String apellidos, String dni, LocalDate fechaNac, ArrayList<Asignatura> listaAsignaturas, ArrayList<Notas> notasAlumno) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fechaNac = fechaNac;
        this.listaAsignaturas = listaAsignaturas;
        this.notasAlumno = notasAlumno;
    }

    //Methods
    @Override
    public String toString() {
        return this.nombre + " " + this.apellidos;
    }

    //Gets and Sets
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNac.toString();
    }

    public void setFechaNacimiento(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }
    
    public int getEdad() {
        int edad = 0;       
        return edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public ArrayList<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(ArrayList listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }    

    public ArrayList<Notas> getNotasAlumno() {
        return notasAlumno;
    }

    public void setNotasAlumno(ArrayList<Notas> notasAlumno) {
        this.notasAlumno = notasAlumno;
    }

    
}

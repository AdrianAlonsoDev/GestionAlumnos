/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionalumnos.modelos;

import java.io.Serializable;

/**
 *
 * @author adria
 */
public class Asignatura implements Serializable{
    
    String nombre;
    Curso curso;
    
    //Constructor vacio
    public Asignatura(){}

    public Asignatura(String nombre, Curso curso) {
        this.nombre = nombre;
        this.curso = curso;
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    public String getCurso(Curso curso) {
        return curso.getNombre();
    }

    @Override
    public String toString() {
        return "\nAsignatura:" + nombre + " Curso:" + curso.getNombre();
    }
    
    
}

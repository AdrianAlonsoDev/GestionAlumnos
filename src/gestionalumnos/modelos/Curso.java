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
public class Curso implements Serializable{
    String nombre;

    public Curso() {}
    
    public Curso(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}

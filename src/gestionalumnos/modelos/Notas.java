/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionalumnos.modelos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author adria
 */
public class Notas implements Serializable{
    
    Asignatura asignatura;
    ArrayList<Integer> notasGrupo;
    
    //Constructor vacio
    public Notas(){}

    public Notas(Asignatura asignatura, ArrayList<Integer> notasGrupo) {
        this.asignatura = asignatura;
        this.notasGrupo = notasGrupo;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }
    
    public String getAsignatura(Boolean pideNombre) {
        return asignatura.getNombre();
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public ArrayList<Integer> getNotas() {
        return notasGrupo;
    }
    
    public String getNotas(boolean string) {
        return notasGrupo.toString();
    }
   

    public void setNotas(ArrayList<Integer> notas) {
        this.notasGrupo = notas;
    }
    
}

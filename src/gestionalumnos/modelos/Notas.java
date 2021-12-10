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
public class Notas implements Serializable{
    
    Asignatura asignatura;
    String notasGrupo;
    int convocatoria;
    
    //Constructor vacio
    public Notas(){}

    public Notas(Asignatura asignatura, String notas, int convocatoria) {
        this.asignatura = asignatura;
        this.notasGrupo = notas;
        this.convocatoria = convocatoria;
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

    public String getNotas() {
        return notasGrupo;
    }
   

    public void setNotas(String notas) {
        this.notasGrupo = notas;
    }

    public int getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(int convocatoria) {
        this.convocatoria = convocatoria;
    }
    
    
}

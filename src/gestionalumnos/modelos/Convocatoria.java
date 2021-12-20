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
public class Convocatoria implements Serializable {
    
    int numeroTrimestre;
    String titulo;
    ArrayList<Notas> notasDeConvocatoria;

    public Convocatoria(int numeroTrimestre, ArrayList<Notas> notasDeConvocatoria) {
        this.numeroTrimestre = numeroTrimestre;
        this.titulo = "Trimestre " + numeroTrimestre;
        this.notasDeConvocatoria = notasDeConvocatoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumeroTrimestre() {
        return numeroTrimestre;
    }

    public void setNumeroTrimestre(int numeroTrimestre) {
        this.numeroTrimestre = numeroTrimestre;
    }

    public ArrayList<Notas> getNotasDeConvocatoria() {
        return notasDeConvocatoria;
    }

    public void setNotasDeConvocatoria(ArrayList<Notas> notasDeConvocatoria) {
        this.notasDeConvocatoria = notasDeConvocatoria;
    }
    
    
    
    
    
}

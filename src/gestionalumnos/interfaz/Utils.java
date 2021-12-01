/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionalumnos.interfaz;

/**
 *
 * @author adria
 */
public class Utils {
    
    public Utils() {}
    
    public boolean comprobarDNI(String dni) {
        boolean resultado = false;
        char letraDni;
        int numeroDni;
        String auxLetra = "TRWAGMYFPDXBNJZSQVHLCKE";
        int modulo;
        char letraDniCalculada;

        if (dni.length() == 9) {
            letraDni = dni.charAt(8);
            numeroDni = Integer.parseInt(dni.substring(0, 8));
            modulo = numeroDni % 23;
            letraDni = Character.toUpperCase(letraDni);
            letraDniCalculada = auxLetra.charAt(modulo);
            if (letraDniCalculada == letraDni) {
                resultado = true;
            }
        } else {
            resultado = false;
        }
        return resultado;
    }
    
}

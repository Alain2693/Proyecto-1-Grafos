/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo;
import java.util.ArrayList;
/**
 *
 * @author Alain
 */
public class Nodo {
    public int color;
    public int valor;
    double grado;
    double pos_x;
    double pos_y;
    String nombre;
    boolean descubierto;
    double distancia;
    public ArrayList<Integer> ConjuntoConectado;
    
    public Nodo(String nombre){
    this.nombre=nombre;
    this.descubierto=false;
    }
public void Nodo(){
    this.grado=0;
}    
    
public String toString()
{
   return this.nombre;
}

public double getGrado(){
return this.grado;
}
    
}

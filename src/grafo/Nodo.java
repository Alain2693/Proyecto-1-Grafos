/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo;

/**
 *
 * @author Alain
 */
public class Nodo {
    int color;
    double grado;
    double pos_x;
    double pos_y;
    String nombre;
    
    public Nodo(String nombre){
    this.nombre=nombre;    
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
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
public class Arista {
    public int nombre;
    public int color; 
    public String nodo1;
    public String nodo2;
    public int nodoint1;
    public int nodoint2;
    public String nodosjuntos;
    
    public void ConstruirArista(String nodo1, String nodo2,int nodoint1, int nodoint2){
    this.nodo1 = nodo1;
    this.nodo2 = nodo2;
    this.nodoint1=nodoint1;
    this.nodoint2=nodoint2;
    this.nodosjuntos= nodo1 +" -- "+ nodo2+";";
    }
    
    public String toString()
{
   return this.nodosjuntos;
}
}

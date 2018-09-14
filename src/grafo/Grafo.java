/*
 This Code creates a graph by 4 methods.
 */
package grafo;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * @author Alain Gomez Cabrera MCIC
 */
public class Grafo {

    /**
     * @param args the command line arguments
     */
    public static Arista arista;
    public static Nodo nodo;
    public static HashMap<Integer, Nodo> TablaNodos;
    public static HashMap<Integer, Arista> TablaAristas;

    public Grafo() {
    }

    static public HashMap<Integer, Nodo> crearNodos(int n_nodos) {
        HashMap<Integer, Nodo> TablaNodos = new HashMap<Integer, Nodo>();
        for (int i = 1; i <= n_nodos; i++) {
            String name = ("Nodo");
            name = name.concat(String.valueOf(i));
            Nodo nodo = new Nodo(name);
            nodo.pos_x = Math.random();
            nodo.pos_y = Math.random();
            TablaNodos.put(i, nodo);
        }
        Grafo.TablaNodos = TablaNodos;
        return TablaNodos;
    }

    /**
     *
     * @param n numero de nodos
     * @param m numero de aristas
     * @param p true:no permite autoaristas, false: permite autoaristas
     */
    static public void modeloErdos(int n, int m, boolean p) {
        HashMap<Integer, Arista> TablaAristas = new HashMap<Integer, Arista>();
        crearNodos(n);
        int nodo1, nodo2;
        for (int i = 1; i <= m; i++) {
            if (p) {
                //Si no se permite autoarista
                do {
                    Random r = new Random();
                    nodo1 = r.nextInt(n) + 1;
                    nodo2 = r.nextInt(n) + 1;
                } while (nodo1 == nodo2);
            } else {
                //Si se permite autoarista
                Random r = new Random();
                nodo1 = r.nextInt(n) + 1;
                nodo2 = r.nextInt(n) + 1;
            }
            //Selecionar 2 nodos al azar
            Nodo nodo_aux1 = Grafo.TablaNodos.get(nodo1);
            Nodo nodo_aux2 = Grafo.TablaNodos.get(nodo2);
            Arista arista = new Arista();
            arista.nodo1 = nodo_aux1.nombre;
            arista.nodo2 = nodo_aux2.nombre;
            //Crear arista
            arista.ConstruirArista(nodo_aux1.nombre, nodo_aux2.nombre);
            TablaAristas.put(i, arista);
            //Guardar arista
            Grafo.TablaAristas = TablaAristas;
        }
    }

    /**
     *
     * @param n numero de nodos
     * @param prob probabilidad para creacion de autoarista
     * @param p true:no permite autoaristas, false: permite autoaristas
     */
    static public void modeloGilbert(int n, double prob, boolean p) {
        HashMap<Integer, Arista> TablaAristas = new HashMap<Integer, Arista>();
        crearNodos(n);
        int k = 1;
        Nodo nodo_aux1;
        Nodo nodo_aux2;
        //Recorrer la aristas
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (p) {
                    //Si no se permite autoarista
                    if ((Math.random() <= prob) && i != j) {
                        Arista arista = new Arista();
                        nodo_aux1 = Grafo.TablaNodos.get(i);
                        nodo_aux2 = Grafo.TablaNodos.get(j);
                        arista.ConstruirArista(nodo_aux1.nombre, nodo_aux2.nombre);
                        TablaAristas.put(k, arista);
                        k = k + 1;
                        //Si no se desea doble arista entre mismos nodos: n1--n2 n2--n1
                        /*Arista arista_aux = new Arista();
                        for (Iterator<Map.Entry<Integer, Arista>> it = TablaAristas.entrySet().iterator(); it.hasNext();) {
                            Map.Entry<Integer, Arista> entry = it.next();
                            arista_aux = TablaAristas.get(entry.getKey());
                            if ((arista.nodo1 == arista_aux.nodo2) && (arista.nodo2 == arista_aux.nodo1)) {
                                it.remove();
                            }
                        }*/
                    }
                } else {
                    //Si se permite autoarista
                    if (Math.random() <= prob) {
                        Arista arista = new Arista();
                        nodo_aux1 = Grafo.TablaNodos.get(i);
                        nodo_aux2 = Grafo.TablaNodos.get(j);
                        arista.ConstruirArista(nodo_aux1.nombre, nodo_aux2.nombre);
                        TablaAristas.put(k, arista);
                        k = k + 1;
                        //Si no se desea doble arista entre mismos nodos: n1--n2 n2--n1
                        /*Arista arista_aux = new Arista();
                        for (Iterator<Map.Entry<Integer, Arista>> it = TablaAristas.entrySet().iterator(); it.hasNext();) {
                            Map.Entry<Integer, Arista> entry = it.next();
                            arista_aux = TablaAristas.get(entry.getKey());
                            if ((arista.nodo1 == arista_aux.nodo2) && (arista.nodo2 == arista_aux.nodo1) && (arista.nodo1 != arista_aux.nodo1) && (arista.nodo2 != arista_aux.nodo2)) {
                                it.remove();
                            }
                        }*/
                    }
                }
            }
        }
        //Guardar aristas
        Grafo.TablaAristas = TablaAristas;
    }

    /**
     *
     * @param n numero de nodos
     * @param r distancia minima para unir nodos
     * @param p true:no permite autoaristas, false: permite autoaristas
     */
    static public void modeloGeografico(int n, double r, boolean p) {
        HashMap<Integer, Arista> TablaAristas = new HashMap<Integer, Arista>();
        int k = 1;
        double distance;
        Nodo nodo_aux1;
        Nodo nodo_aux2;
        crearNodos(n);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                nodo_aux1 = Grafo.TablaNodos.get(i);
                nodo_aux2 = Grafo.TablaNodos.get(j);
                //Obtener la distancia entre nodos
                distance = Math.sqrt(Math.pow(nodo_aux1.pos_x - nodo_aux2.pos_x, 2) + Math.pow(nodo_aux1.pos_y - nodo_aux2.pos_y, 2));
                if (p) {
                    //Si no se permite autoarista
                    if (distance <= r && (i != j)) {
                        Arista arista = new Arista();
                        arista.ConstruirArista(nodo_aux1.nombre, nodo_aux2.nombre);
                        TablaAristas.put(k, arista);
                        k = k + 1;
                        //Si no se desea doble arista entre mismos nodos: n1--n2 n2--n1
                        /*Arista arista_aux = new Arista();
                        for (Iterator<Map.Entry<Integer, Arista>> it = TablaAristas.entrySet().iterator(); it.hasNext();) {
                            Map.Entry<Integer, Arista> entry = it.next();
                            arista_aux = TablaAristas.get(entry.getKey());
                            if ((arista.nodo1 == arista_aux.nodo2) && (arista.nodo2 == arista_aux.nodo1)) {
                                it.remove();
                            }
                        }*/
                    }
                } else {
                    //Si se permite autoarista
                    if (distance <= r) {
                        Arista arista = new Arista();
                        arista.ConstruirArista(nodo_aux1.nombre, nodo_aux2.nombre);
                        TablaAristas.put(k, arista);
                        k = k + 1;
                        //Si no se desea doble arista entre mismos nodos: n1--n2 n2--n1
                        /*Arista arista_aux = new Arista();
                        for (Iterator<Map.Entry<Integer, Arista>> it = TablaAristas.entrySet().iterator(); it.hasNext();) {
                            Map.Entry<Integer, Arista> entry = it.next();
                            arista_aux = TablaAristas.get(entry.getKey());
                            if ((arista.nodo1 == arista_aux.nodo2) && (arista.nodo2 == arista_aux.nodo1) && (arista.nodo1 != arista_aux.nodo1) && (arista.nodo2 != arista_aux.nodo2)) {
                                it.remove();
                            }
                        }*/
                    }
                }
            }
        }
        Grafo.TablaAristas = TablaAristas;
    }

    /**
     *
     * @param n numero de nodos
     * @param d grado del nodo
     * @param p true:no permite autoaristas, false: permite autoaristas
     */
    static public void modeloBarabasi(int n, double d, boolean p) {
        HashMap<Integer, Nodo> TablaNodos = new HashMap<Integer, Nodo>();
        HashMap<Integer, Arista> TablaAristas = new HashMap<Integer, Arista>();
        int k = 1, c;
        double prob;
        Nodo nodo_aux1;
        Nodo nodo_aux2;
        String name = ("Nodo");
        name = name.concat(String.valueOf(1));
        Nodo nodo = new Nodo(name);
        TablaNodos.put(1, nodo);
        name = ("Nodo");
        prob = 1;
        for (int i = 2; i <= n; i++) {
            name = name.concat(String.valueOf(i));
            Nodo nodo1 = new Nodo(name);
            TablaNodos.put(i, nodo1);
            name = ("Nodo");
            Set set = TablaNodos.entrySet();
            Iterator iterator = set.iterator();
            nodo_aux2 = TablaNodos.get(i);
            while (iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                nodo_aux1 = TablaNodos.get(mentry.getKey());
                prob = 1 - nodo_aux1.getGrado() / d;
                double pr = Math.random();
                if (pr <= prob && nodo_aux2.grado < d) {
                    Arista arista = new Arista();
                    if (!p) {
                        arista.ConstruirArista(nodo_aux1.nombre, nodo_aux2.nombre);
                        TablaAristas.put(k, arista);
                        k = k + 1;
                        c = (int) mentry.getKey();
                        nodo_aux1.grado = nodo_aux1.grado + 1;
                        nodo_aux2.grado = nodo_aux2.grado + 1;
                        TablaNodos.put(c, nodo_aux1);
                        TablaNodos.put(i, nodo_aux2);
                    } else {
                        if (nodo_aux1.nombre != nodo_aux2.nombre) {
                            arista.ConstruirArista(nodo_aux1.nombre, nodo_aux2.nombre);
                            TablaAristas.put(k, arista);
                            k = k + 1;
                            c = (int) mentry.getKey();
                            nodo_aux1.grado = nodo_aux1.grado + 1;
                            nodo_aux2.grado = nodo_aux2.grado + 1;
                            TablaNodos.put(c, nodo_aux1);
                            TablaNodos.put(i, nodo_aux2);
                        }
                    }
                }
            }
        }
        Grafo.TablaAristas = TablaAristas;
        Grafo.TablaNodos = TablaNodos;
    }

    /**
     * Creacion del archivo .gv
     *
     * @throws IOException
     */
    static public void crearArchivo() throws IOException {
        String idFichero = "C:\\Users\\Alain\\Documents\\Grafos\\Grafo.gv";
        Set set = Grafo.TablaNodos.entrySet();
        String newline = System.getProperty("line.separator");
        PrintWriter ficheroSalida = new PrintWriter(idFichero);
        ficheroSalida.print("graph grafo1 {" + newline);
        Iterator iterator2 = set.iterator();
        try {
            while (iterator2.hasNext()) {
                Map.Entry mentry2 = (Map.Entry) iterator2.next();
                ficheroSalida.print(mentry2.getValue() + ";" + newline);
            }
            set = Grafo.TablaAristas.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                ficheroSalida.print(mentry.getValue() + newline);
            }
            ficheroSalida.print("}");
            ficheroSalida.close();
        } catch (NullPointerException e) {
        }
    }

    public static void main(String[] args) throws IOException {
        int n = 30, m = 200;
        boolean p = true; //true no permite autoarista
        double prob = 0.3;
        Grafo.modeloErdos(n, m, p);
        //Grafo.modeloGilbert(n, prob, p);
        //Grafo.modeloBarabasi(n, 20, p);
        //Grafo.modeloGeografico(n, 0.5, p);
        Grafo.crearArchivo();
    }
}

/*
 This Code creates a graph by 4 methods.
 */
package grafo;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

/**
 * @author Alain Gomez Cabrera MCIC
 */
public class Grafo {

    public static Arista arista;
    public static Nodo nodo;
    public static HashMap<Integer, Nodo> TablaNodos;
    public static HashMap<Integer, Arista> TablaAristas;
    public static HashMap<Integer, Nodo> TablaNodosTree;
    public static HashMap<Integer, Arista> TablaAristasTree;
    public static HashMap<Integer, Arista> AristasConPesos;
    public ArrayList<Nodo> ListaNodos;
    public ArrayList<Arista> ListaAristas;

    public Grafo() {
        HashMap<Integer, Nodo> TablaNodosTree = new HashMap<>();
        HashMap<Integer, Arista> TablaAristasTree = new HashMap<>();
        ArrayList<Nodo> ListaNodos = new ArrayList<>();
        ArrayList<Arista> ListaAristas = new ArrayList<>();
        this.TablaNodosTree = TablaNodosTree;
        this.TablaAristasTree = TablaAristasTree;
        this.ListaNodos = ListaNodos;
        this.ListaAristas = ListaAristas;
    }

    static public HashMap<Integer, Nodo> crearNodos(int n_nodos) {
        HashMap<Integer, Nodo> TablaNodos = new HashMap<>();
        for (int i = 1; i <= n_nodos; i++) {
            String name = ("Nodo");
            name = name.concat(String.valueOf(i));
            Nodo nodo = new Nodo(name);
            nodo.valor = i;
            nodo.pos_x = Math.random();
            nodo.pos_y = Math.random();
            TablaNodos.put(i, nodo);
        }
        Grafo.TablaNodos = TablaNodos;
        return TablaNodos;
    }

    static public void EdgeValues(float min, float max) {
        HashMap<Integer, Arista> Aristas = new HashMap<>();
        float peso;
        Set set = Grafo.TablaAristas.entrySet();
        Aristas = Grafo.TablaAristas;
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            peso = (float) Math.random();
            peso = peso * (max - min);
            peso = peso + min;
            Map.Entry mentry = (Map.Entry) iterator.next();
            arista = Aristas.get((int) mentry.getKey());
            arista.peso = peso;
            Aristas.put((int) mentry.getKey(), arista);
        }
        Grafo.TablaAristas = Aristas;
    }

    /**
     *
     * @param n numero de nodos
     * @param m numero de aristas
     * @param p true:no permite autoaristas, false: permite autoaristas
     */
    static public void modeloErdos(int n, int m, boolean p) throws IOException {
        HashMap<Integer, Arista> TablaAristas = new HashMap<>();
        crearNodos(n);
        int nodo1, nodo2;
        for (int i = 0; i < m; i++) {
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
            arista.ConstruirArista(nodo_aux1.nombre, nodo_aux2.nombre, nodo1, nodo2);
            TablaAristas.put(i, arista);
            //Guardar arista
            Grafo.TablaAristas = TablaAristas;
        }
        Grafo.crearArchivo("C:\\Users\\Alain\\Desktop\\Dijkstra\\erdosV" + n + "E" + m + ".gv");

    }
    /**
     *
     * @param n numero de nodos
     * @param prob probabilidad para creacion de autoarista
     * @param p true:no permite autoaristas, false: permite autoaristas
     */
    static public void modeloGilbert(int n, double prob, boolean p) throws IOException {
        HashMap<Integer, Arista> TablaAristas = new HashMap<>();
        crearNodos(n);
        int k = 0;
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
                        arista.ConstruirArista(nodo_aux1.nombre, nodo_aux2.nombre, i, j);
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
                        arista.ConstruirArista(nodo_aux1.nombre, nodo_aux2.nombre, i, j);
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
        Grafo.crearArchivo("C:\\Users\\Alain\\Desktop\\Dijkstra\\Gilbert" + n + "p" + prob + ".gv");

    }

    /**
     *
     * @param n numero de nodos
     * @param r distancia minima para unir nodos
     * @param p true:no permite autoaristas, false: permite autoaristas
     */
    static public void modeloGeografico(int n, double r, boolean p) throws IOException {
        HashMap<Integer, Arista> TablaAristas = new HashMap<>();
        int k = 0;
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
                        arista.ConstruirArista(nodo_aux1.nombre, nodo_aux2.nombre, i, j);
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
                        arista.ConstruirArista(nodo_aux1.nombre, nodo_aux2.nombre, i, j);
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
        int pr = (int) (r * 100);
        Grafo.crearArchivo("C:\\Users\\Alain\\Desktop\\Dijkstra\\Geografico" + n + "r" + pr + ".gv");
    }

    /**
     *
     * @param n numero de nodos
     * @param d grado del nodo
     * @param p true:no permite autoaristas, false: permite autoaristas
     */
    static public void modeloBarabasi(int n, double d, boolean p) throws IOException {
        HashMap<Integer, Nodo> TablaNodos = new HashMap<>();
        HashMap<Integer, Arista> TablaAristas = new HashMap<>();
        int k = 0, c, x;
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
                x = (int) mentry.getKey();
                if (pr <= prob && nodo_aux2.grado < d) {
                    Arista arista = new Arista();
                    if (!p) {
                        arista.ConstruirArista(nodo_aux1.nombre, nodo_aux2.nombre, x, i);
                        TablaAristas.put(k, arista);
                        k = k + 1;
                        c = (int) mentry.getKey();
                        nodo_aux1.grado = nodo_aux1.grado + 1;
                        nodo_aux2.grado = nodo_aux2.grado + 1;
                        TablaNodos.put(c, nodo_aux1);
                        TablaNodos.put(i, nodo_aux2);
                    } else {
                        if (nodo_aux1.nombre != nodo_aux2.nombre) {
                            arista.ConstruirArista(nodo_aux1.nombre, nodo_aux2.nombre, x, i);
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
        Grafo.crearArchivo("C:\\Users\\Alain\\Desktop\\Dijkstra\\Barabasi" + n + "d" + d + ".gv");
    }

    /**
     * Creacion del archivo .gv
     *
     * @throws IOException
     */
    static public void crearArchivo(String idFichero) throws IOException {
        HashMap<Integer, Arista> TablaAristas = new HashMap<>();
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
                Arista arista = new Arista();
                Map.Entry mentry = (Map.Entry) iterator.next();
                arista = Grafo.TablaAristas.get((int) mentry.getKey());
                ficheroSalida.print(mentry.getValue() + " [weight=" + arista.peso + "]" + newline);
            }
            ficheroSalida.print("}");
            ficheroSalida.close();
        } catch (NullPointerException e) {
        }
    }

    public void BFS(Grafo grafo1, int nodoRaiz) throws IOException {
        int z, NumNodosGraf;
        HashMap<Integer, Nodo> Nodos = new HashMap<>();
        HashMap<Integer, Arista> Aristas = new HashMap<>();
        HashMap<Integer, Arista> AristasBFS = new HashMap<>();
        HashMap<Integer, Nodo> NodosBFS = new HashMap<>();
        Nodos = grafo1.TablaNodos;
        Aristas = grafo1.TablaAristas;
        NumNodosGraf = Nodos.size();
        boolean[] discovered;
        discovered = new boolean[NumNodosGraf];
        ArrayList<Integer> cola;
        ArrayList<Integer> cola_aux;
        int nodo_2;
        int nodo_1;
        ArrayList<Integer> Lista_aux = new ArrayList<>();
        Lista_aux.add(nodoRaiz);
        Nodo nodobfs = new Nodo("nodo" + nodoRaiz);
        NodosBFS.put(0, nodobfs);
        Arista arista;
        ArrayList<ArrayList<Integer>> Capas = new ArrayList<>();
        z = 0;
        Capas.add(z, Lista_aux);
        discovered[0] = true;
        Set set = Aristas.entrySet();
        Iterator iterator;
        cola = new ArrayList<>();
        cola_aux = new ArrayList<>();
        int k = 0;
        int n = 1;
        int keybfs = 0;
        while (!Capas.get(k).isEmpty()) {
            cola_aux = new ArrayList<>();
            cola = new ArrayList<>();
            Capas.add(k + 1, cola_aux);
            for (int c = 0; c <= Capas.get(k).size() - 1; c++) {
                cola_aux = new ArrayList<>();
                cola = new ArrayList<>();
                n = Capas.get(k).get(c);
                set = Aristas.entrySet();
                iterator = set.iterator();
                while (iterator.hasNext()) {
                    arista = new Arista();
                    Map.Entry mentry = (Map.Entry) iterator.next();
                    arista = Aristas.get((int) mentry.getKey());
                    nodo_1 = arista.nodoint1;
                    if ((nodo_1 == n)) {
                        nodo_2 = arista.nodoint2;
                        cola.add(nodo_2);
                    }
                    if ((arista.nodoint2 == n)) {
                        nodo_2 = arista.nodoint1;
                        cola.add(nodo_2);
                    }
                }
                for (int j = 0; j <= (cola.size() - 1); j++) {
                    if (discovered[cola.get(j) - 1] == false) {
                        discovered[cola.get(j) - 1] = true;
                        int num = cola.get(j);
                        Capas.get(k + 1).add(num);
                        Arista aristabfs = new Arista();
                        aristabfs.ConstruirArista("nodo" + n, "nodo" + num, n, num);
                        AristasBFS.put(keybfs, aristabfs);
                        keybfs = keybfs + 1;
                        nodobfs = new Nodo("nodo" + num);
                        NodosBFS.put(keybfs, nodobfs);
                    }
                }
            }
            n = n + 1;
            k = k + 1;
            grafo1.TablaNodosTree = NodosBFS;
            grafo1.TablaAristasTree = AristasBFS;
        }
    }

    public void crearBFS(Grafo grafo1, int nodoRaiz) throws IOException {
        BFS(grafo1, nodoRaiz);
        String fichero = "C:\\Users\\Alain\\Documents\\Grafos\\TreeBFS.gv";
        Set set = grafo1.TablaNodosTree.entrySet();
        String newline = System.getProperty("line.separator");
        PrintWriter ficheroSalida = new PrintWriter(fichero);
        ficheroSalida.print("graph grafoTree {" + newline);
        Iterator iterator2 = set.iterator();
        try {
            while (iterator2.hasNext()) {
                Map.Entry mentry2 = (Map.Entry) iterator2.next();
                ficheroSalida.print(mentry2.getValue() + ";" + newline);
            }
            set = grafo1.TablaAristasTree.entrySet();
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

    public void DFS_R(Grafo grafo1, int nodoRaiz) throws IOException {
        int nodo_2, nodo_1;
        Nodo nodo_aux, nodo_aux1, nodo_aux2;
        nodo_aux = new Nodo("nodo" + nodoRaiz);
        Set set = grafo1.TablaAristas.entrySet();
        Iterator iterator;
        nodo_aux = grafo1.TablaNodos.get(nodoRaiz);
        nodo_aux.descubierto = true;
        grafo1.TablaNodos.put(nodoRaiz, nodo_aux);
        grafo1.ListaNodos.add(nodo_aux);
        set = grafo1.TablaAristas.entrySet();
        iterator = set.iterator();
        while (iterator.hasNext()) {
            arista = new Arista();
            Map.Entry mentry = (Map.Entry) iterator.next();
            arista = grafo1.TablaAristas.get((int) mentry.getKey());
            nodo_1 = arista.nodoint1;
            nodo_2 = arista.nodoint2;
            nodo_aux2 = new Nodo("Nodo" + arista.nodoint2);
            nodo_aux2 = grafo1.TablaNodos.get(arista.nodoint2);
            nodo_aux1 = new Nodo("Nodo" + arista.nodoint1);
            nodo_aux1 = grafo1.TablaNodos.get(arista.nodoint1);

            if (nodoRaiz == nodo_1 && (nodo_aux2.descubierto) == false) {
                Arista aristadfs = new Arista();
                aristadfs.ConstruirArista("Nodo" + nodo_1, "Nodo" + nodo_2, nodo_1, nodo_2);
                grafo1.ListaAristas.add(0, aristadfs);
                DFS_R(grafo1, nodo_2);
            }
            if (nodoRaiz == nodo_2 && (nodo_aux1.descubierto) == false) {
                Arista aristadfs = new Arista();
                aristadfs.ConstruirArista("Nodo" + nodo_2, "Nodo" + nodo_1, nodo_2, nodo_1);
                grafo1.ListaAristas.add(0, aristadfs);
                DFS_R(grafo1, nodo_1);
            }
        }
    }

    public void crearDFS_R(Grafo grafo1, int nodoRaiz) throws IOException {
        grafo1.ListaNodos.clear();
        grafo1.ListaAristas.clear();
        DFS_R(grafo1, nodoRaiz);
        String fichero = "C:\\Users\\Alain\\Documents\\Grafos\\TreeDFS_R.gv";
        String newline = System.getProperty("line.separator");
        PrintWriter ficheroSalida = new PrintWriter(fichero);
        ficheroSalida.print("graph grafoTreeDFS_R {" + newline);
        for (int r = 0; r < grafo1.ListaNodos.size(); r++) {
            ficheroSalida.print(grafo1.ListaNodos.get(r) + ";" + newline);
        }
        for (int r = 0; r < grafo1.ListaAristas.size(); r++) {
            ficheroSalida.print(grafo1.ListaAristas.get(r) + newline);
        }
        ficheroSalida.print("}");
        ficheroSalida.close();
    }

    public void DFS_I(Grafo grafo1, int nodoRaiz) throws IOException {
        int nodo_2, nodo_1;
        Nodo nodo_aux, nodo_aux1, nodo_aux2;
        Stack<Nodo> pila = new Stack<>();
        nodo_aux = grafo1.TablaNodos.get(nodoRaiz);
        nodo_aux.descubierto = true;
        pila.push(nodo_aux);
        grafo1.TablaNodos.put(nodoRaiz, nodo_aux);
        grafo1.ListaNodos.add(nodo_aux);
        Set set = grafo1.TablaAristas.entrySet();
        Iterator iterator;
        iterator = set.iterator();
        boolean encontrar = false;
        while (!pila.isEmpty()) {
            encontrar = false;
            iterator = set.iterator();
            while (iterator.hasNext() && encontrar == false) {

                arista = new Arista();
                Map.Entry mentry = (Map.Entry) iterator.next();
                arista = grafo1.TablaAristas.get((int) mentry.getKey());
                nodo_1 = arista.nodoint1;
                nodo_2 = arista.nodoint2;
                nodo_aux2 = new Nodo("Nodo");
                nodo_aux2 = grafo1.TablaNodos.get(arista.nodoint2);
                nodo_aux1 = new Nodo("Nodo");
                nodo_aux1 = grafo1.TablaNodos.get(arista.nodoint1);

                if (nodoRaiz == nodo_1 && (nodo_aux2.descubierto) == false) {
                    pila.push(nodo_aux2);
                    nodo_aux = new Nodo("Nodo" + nodo_2);
                    nodo_aux.descubierto = true;
                    grafo1.TablaNodos.put(nodo_2, nodo_aux);
                    nodoRaiz = nodo_2;
                    encontrar = true;
                    grafo1.ListaNodos.add(0, nodo_aux);
                    Arista aristadfs = new Arista();
                    aristadfs.ConstruirArista("Nodo" + nodo_1, "Nodo" + nodo_2, nodo_1, nodo_2);
                    grafo1.ListaAristas.add(0, aristadfs);
                }
                if (nodoRaiz == nodo_2 && (nodo_aux1.descubierto) == false) {
                    pila.push(nodo_aux1);
                    nodo_aux = new Nodo("Nodo" + nodo_1);
                    nodo_aux.descubierto = true;
                    grafo1.TablaNodos.put(nodo_1, nodo_aux);
                    nodoRaiz = nodo_1;
                    encontrar = true;
                    grafo1.ListaNodos.add(0, nodo_aux);
                    Arista aristadfs = new Arista();
                    aristadfs.ConstruirArista("Nodo" + nodo_2, "Nodo" + nodo_1, nodo_2, nodo_1);
                    grafo1.ListaAristas.add(0, aristadfs);
                }
            }
            if (encontrar == false) {
                pila.pop();
                if (!pila.isEmpty()) {
                    nodoRaiz = Integer.parseInt(pila.peek().nombre.substring(4));
                }
            }
        }
    }

    public void crearDFS_I(Grafo grafo1, int nodoRaiz) throws IOException {
        DFS_I(grafo1, nodoRaiz);
        String fichero = "C:\\Users\\Alain\\Documents\\Grafos\\TreeDFS_I.gv";
        String newline = System.getProperty("line.separator");
        PrintWriter ficheroSalida = new PrintWriter(fichero);
        ficheroSalida.print("graph grafoTreeDFS_I {" + newline);
        for (int r = 0; r < grafo1.ListaNodos.size(); r++) {
            ficheroSalida.print(grafo1.ListaNodos.get(r) + ";" + newline);
        }
        for (int r = 0; r < grafo1.ListaAristas.size(); r++) {
            ficheroSalida.print(grafo1.ListaAristas.get(r) + newline);
        }
        ficheroSalida.print("}");
        ficheroSalida.close();
    }

    public void Dijkstra(int s) throws FileNotFoundException {
        double[] distancia = new double[Grafo.TablaNodos.size() + 1];
        ArrayList<Integer> NodosVisitados = new ArrayList<>();
        ArrayList<Integer> NodosPorVisitar = new ArrayList<>();
        ArrayList<Arista> ListaAristasDijkstra = new ArrayList<>();
        HashMap<Integer, Arista> AristasDisponibles = new HashMap<>();
        Nodo nodo;
        Arista arista, aristamin;
        int u, a = 0, nodo_a_agregar = 0, llave_arista_a_quitar = 0, aux = 0;
        double dmin = Double.POSITIVE_INFINITY;
        double pInfiniteDouble = Double.POSITIVE_INFINITY;
        double d, dc = 0, dc2 = 0;
        AristasDisponibles = Grafo.TablaAristas;
        for (int i = 1; i < Grafo.TablaNodos.size() + 1; i++) {
            distancia[i] = pInfiniteDouble;
            NodosPorVisitar.add(Grafo.TablaNodos.get(i).valor);
        }
        for (int i = 0; i < Grafo.TablaAristas.size(); i++) {
            ListaAristas.add(i, Grafo.TablaAristas.get(i));
        }
        u = s;
        distancia[u] = 0;
        NodosVisitados.add(u);
        NodosPorVisitar.remove(u - 1);
        arista = AristasDisponibles.get(0);
        aristamin = AristasDisponibles.get(3);
        d = pInfiniteDouble;
        for (int k = 0; k < AristasDisponibles.size(); k++) {
            arista = AristasDisponibles.get(k);
        }
        while (!NodosPorVisitar.isEmpty()) {
            d = pInfiniteDouble;
            dmin = pInfiniteDouble;
            for (int j = 0; j < NodosVisitados.size(); j++) {
                u = NodosVisitados.get(j);
                for (int k = 0; k < AristasDisponibles.size(); k++) {
                    arista = AristasDisponibles.get(k);
                    if ((arista.nodoint1 == u) && (distancia[arista.nodoint2] == pInfiniteDouble)) {
                        d = arista.peso + distancia[arista.nodoint1];
                    } else if ((arista.nodoint1 == u) && (distancia[arista.nodoint2] > (arista.peso + distancia[arista.nodoint1]))) {
                        d = arista.peso + distancia[arista.nodoint1];
                    }
                    if ((arista.nodoint2 == u) && (distancia[arista.nodoint1] == pInfiniteDouble)) {
                        d = arista.peso + distancia[arista.nodoint2];
                    } else if ((arista.nodoint2 == u) && (distancia[arista.nodoint1] > (arista.peso + distancia[arista.nodoint2]))) {
                        d = arista.peso + distancia[arista.nodoint2];
                    }
                    if (arista.nodoint2 == s && distancia[arista.nodoint1] != pInfiniteDouble) {
                        d = arista.peso;
                    }
                    if (arista.nodoint1 == u) {
                        if (d < dmin && !NodosVisitados.contains(arista.nodoint2)) {
                            dmin = d;
                            nodo_a_agregar = arista.nodoint2;
                            llave_arista_a_quitar = k;
                            aristamin = AristasDisponibles.get(k);
                        }
                    }
                    if (arista.nodoint2 == u && !NodosVisitados.contains(arista.nodoint1)) {
                        if (d < dmin) {
                            dmin = d;
                            nodo_a_agregar = arista.nodoint1;
                            llave_arista_a_quitar = k;
                            aristamin = AristasDisponibles.get(k);
                        }
                    }
                }
            }
            NodosVisitados.add(nodo_a_agregar);
            for (int k = 0; k < NodosPorVisitar.size(); k++) {
                if (NodosPorVisitar.get(k) == nodo_a_agregar) {
                    aux = k;
                }
            }
            NodosPorVisitar.remove(aux);
            ListaAristasDijkstra.add(aristamin);

            for (int k = 0; k < NodosVisitados.size(); k++) {
            }

            for (int j = 0; j < ListaAristasDijkstra.size(); j++) {
            }

            if ((dmin) < distancia[nodo_a_agregar]) {
                distancia[nodo_a_agregar] = dmin;
            }
        }
        String idFichero = "C:\\Users\\Alain\\Desktop\\Dijkstra\\arboldijkstra.gv";
        String newline = System.getProperty("line.separator");
        try (PrintWriter ficheroSalida = new PrintWriter(idFichero)) {
            ficheroSalida.print("graph grafo1 {" + newline);
            for (int k = 0; k < Grafo.TablaNodos.size(); k++) {
                a = k + 1;
                dc = distancia[k + 1] * 100;
                dc2 = dc % 100;
                dc = dc - dc2;
                dc = dc / 100;
                ficheroSalida.print("Nodo" + a + "[label=Nodo" + a + "(" + (int) dc + "." + (int) dc2 + ")]" + newline);
            }
            for (int k = 0; k < ListaAristasDijkstra.size(); k++) {
                arista = ListaAristasDijkstra.get(k);
                ficheroSalida.print(arista.nodosjuntos + "[weight=" + arista.peso + "]" + newline);
            }
            ficheroSalida.print("}");
        }
    }

    public static void main(String[] args) throws IOException {
        int n = 200, m = 1000;
        boolean p = true; //true no permite autoarista
        double prob = 0.3;
        Grafo grafo1 = new Grafo();

        //Metodos de construccion
        //grafo1.modeloErdos(n, m, p);
        //grafo1.modeloGilbert(n, prob, p);
        //grafo1.modeloBarabasi(n, 13, p);
        grafo1.modeloGeografico(n, 0.3, p);
        //Construir arboles
        //grafo1.crearBFS(grafo1, 1);
        //grafo1.crearDFS_R(grafo1, 1);
        //grafo1.crearDFS_I(grafo1, 1);
        grafo1.EdgeValues(20.0f, 50.0f);
        grafo1.Dijkstra(100);

    }
}

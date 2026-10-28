package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Gráfica no dirigida con lista de adyacencia. Guarda los nodos y permite
 * agregar aristas sin duplicarlas.
 */

public class Grafica {

    private List<Nodo> nodos;

    public Grafica() {
        this.nodos = new ArrayList<>();
    }

    public void agregarNodo(Nodo nodo) {
        if (buscarNodo(nodo.getId()) != null) {
            throw new IllegalArgumentException("Ya existe un nodo con el id " + nodo.getId());
        }
        nodos.add(nodo);
    }

    public Nodo buscarNodo(int id) {
        for (Nodo n : nodos) {
            if (n.getId() == id) return n;
        }
        return null;
    }

    /**
     * Agrega una arista entre los nodos con esos ids.
     * @return true si la arista se agrego, false si ya existia 
     */
    public boolean agregarArista(int idA, int idB) {
        Nodo a = buscarNodo(idA);
        Nodo b = buscarNodo(idB);
        if (a == null || b == null) {
            throw new IllegalArgumentException(
                "No se puede agregar la arista (" + idA + "," + idB + "): nodo inexistente.");
        }
        if (a.getVecinos().contains(b)) {
            return false; // la arista ya estaba
        }
        a.agregarVecino(b);
        b.agregarVecino(a);
        return true;
    }

    public List<Nodo> getNodos() { return nodos; }

    public List<int[]> getAristas() {
        List<int[]> aristas = new ArrayList<>();
        for (Nodo u : nodos) {
            for (Nodo v : u.getVecinos()) {
                if (u.getId() < v.getId()) {
                    aristas.add(new int[]{u.getId(), v.getId()});
                }
            }
        }
        return aristas;
    }

    public void imprimirVecinos() {
        for (Nodo n : nodos) {
            System.out.print("Nodo " + n.getEtiqueta() + "(" + n.getId() + ") -> vecinos: ");
            for (Nodo v : n.getVecinos()) {
                System.out.print(v.getEtiqueta() + "(" + v.getId() + ") ");
            }
            System.out.println();
        }
    }
}
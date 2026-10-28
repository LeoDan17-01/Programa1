package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un vertice de la grafica. Sirve tanto para la grafica original G
 * como para la reducida G'. esOriginal dice de indica su origen
 */

public class Nodo {
    
    private int id;
    private List<Nodo> vecinos;
    private boolean esOriginal;   // true si viene de G, false si fue agregado por la reduccion
    private String etiqueta;      // util para imprimir "vw" en los nodos nuevos

    public Nodo(int id, boolean esOriginal, String etiqueta) {
        this.id = id;
        this.vecinos = new ArrayList<>();
        this.esOriginal = esOriginal;
        this.etiqueta = etiqueta;
    }

    public int getId() { return id; }

    public List<Nodo> getVecinos() { return vecinos; }

    public boolean isEsOriginal() { return esOriginal; }

    public String getEtiqueta() { return etiqueta; }

    public void agregarVecino(Nodo vecino) {
        // Evitamos duplicar vecinos por si se agrega dos veces la misma arista
        if (!this.vecinos.contains(vecino)) {
            this.vecinos.add(vecino);
        }
    }

    @Override
    public String toString() {
        return etiqueta + "(" + id + ")";
    }
}
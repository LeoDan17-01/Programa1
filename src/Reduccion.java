package src;

import java.util.List;

public class Reduccion {

    /**
     * Aplica la reducción Vertex Cover -> Dominating Set.
     *
     * Para cada arista (v,w) de G:
     *   - Se agrega un nuevo vértice vw
     *   - Se conecta vw con v y con w
     *   - La arista original (v,w) se conserva (ya está en G)
     *
     * El valor de k se conserva.
     */
    public static Grafica reducir(Grafica g) {
        Grafica gp = new Grafica();

        // 1. Copiamos los vértices originales con el mismo id
        for (Nodo n : g.getNodos()) {
            gp.agregarNodo(new Nodo(n.getId(), true, n.getEtiqueta()));
        }

        // 2. Copiamos las aristas originales
        for (int[] a : g.getAristas()) {
            gp.agregarArista(a[0], a[1]);
        }

        // 3. Para cada arista (v,w) agregamos el nuevo vértice vw y sus dos aristas
        int siguienteId = maxId(g) + 1;

        for (int[] a : g.getAristas()) {
            int v = a[0];
            int w = a[1];

            String etiquetaNueva = "v" + v + "v" + w; // por ejemplo "v1v2"
            Nodo nuevo = new Nodo(siguienteId, false, etiquetaNueva);
            gp.agregarNodo(nuevo);

            // aristas (v, vw) y (w, vw)
            gp.agregarArista(v, siguienteId);
            gp.agregarArista(w, siguienteId);

            siguienteId++;
        }

        return gp;
    }

    private static int maxId(Grafica g) {
        int max = 0;
        for (Nodo n : g.getNodos()) {
            if (n.getId() > max) max = n.getId();
        }
        return max;
    }
}
package src;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Uso: java src.Main <archivo_ejemplar>");
            System.exit(1);
        }

        String ruta = args[0];

        try {
            // 1. Leer ejemplar de Vertex Cover
            LectorEjemplar.Ejemplar ej = LectorEjemplar.leer(ruta);

            // 2. Construir la gráfica original G
            Grafica g = new Grafica();
            for (int i = 1; i <= ej.n; i++) {
                g.agregarNodo(new Nodo(i, true, "v" + i));
            }
            for (int[] a : ej.aristas) {
                g.agregarArista(a[0], a[1]);
            }

            // 3. Aplicar la reducción
            Grafica gp = Reduccion.reducir(g);

            // 4. Reporte
            imprimirReporte(g, gp, ej.k);

        } catch (IOException ex) {
            System.err.println("Error al leer el archivo: " + ex.getMessage());
            System.exit(1);
        }
    }

    private static void imprimirReporte(Grafica g, Grafica gp, int k) {
        System.out.println("=================================================");
        System.out.println("  REDUCCIÓN VERTEX COVER -> DOMINATING SET");
        System.out.println("=================================================\n");

        // --- Grafica original ---
        System.out.println("--- Gráfica original G ---");
        System.out.println("Vértices originales:");
        for (Nodo n : g.getNodos()) {
            System.out.println("  " + n.getEtiqueta() + " (id=" + n.getId() + ")");
        }

        System.out.println("\nAristas originales:");
        List<int[]> aristasG = g.getAristas();
        for (int[] a : aristasG) {
            System.out.println("  (" + a[0] + ", " + a[1] + ")");
        }

        // --- Grafica reducida ---
        System.out.println("\n--- Gráfica reducida G' (Dominating Set) ---");

        System.out.println("Vértices originales conservados:");
        for (Nodo n : gp.getNodos()) {
            if (n.isEsOriginal()) {
                System.out.println("  " + n.getEtiqueta() + " (id=" + n.getId() + ")");
            }
        }

        System.out.println("\nNuevos vértices agregados (uno por arista):");
        for (Nodo n : gp.getNodos()) {
            if (!n.isEsOriginal()) {
                System.out.println("  " + n.getEtiqueta() + " (id=" + n.getId() + ")");
            }
        }

        System.out.println("\nAristas originales conservadas:");
        for (int[] a : aristasG) {
            System.out.println("  (" + a[0] + ", " + a[1] + ")");
        }

        System.out.println("\nNuevas aristas agregadas (triángulos):");
        // Las nuevas aristas son las que tocan a un nodo nuevo
        for (int[] a : gp.getAristas()) {
            Nodo u = gp.buscarNodo(a[0]);
            Nodo v = gp.buscarNodo(a[1]);
            boolean esNueva = !u.isEsOriginal() || !v.isEsOriginal();
            // Excluye aristas originales ya listadas
            boolean esOriginal = u.isEsOriginal() && v.isEsOriginal();
            if (esNueva && !esOriginal) {
                System.out.println("  (" + a[0] + ", " + a[1] + ")");
            }
        }

        System.out.println("\nValor de k para Dominating Set: " + k);
        System.out.println("=================================================");
    }
}
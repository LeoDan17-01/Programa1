package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lee un archivo de texto con un ejemplar de Vertex Cover.
 * El formato es: lineas "n <num>", "k <num>", "aristas" y luego pares "u v".
 * Las lineas vacias y las que empiezan con # se ignoran.
 */

public class LectorEjemplar {

    public static class Ejemplar {
        public int n;
        public int k;
        public List<int[]> aristas = new ArrayList<>();
    }

    public static Ejemplar leer(String ruta) throws IOException {
        Ejemplar e = new Ejemplar();
        boolean enAristas = false;

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;

                String[] partes = linea.split("\\s+");

                if (!enAristas) {
                    switch (partes[0].toLowerCase()) {
                        case "n":
                            e.n = Integer.parseInt(partes[1]);
                            break;
                        case "k":
                            e.k = Integer.parseInt(partes[1]);
                            break;
                        case "aristas":
                            enAristas = true;
                            break;
                        default:
                            throw new IOException("Linea no reconocida: " + linea);
                    }
                } else {
                    if (partes.length != 2) {
                        throw new IOException("Arista invalida: " + linea);
                    }
                    int u = Integer.parseInt(partes[0]);
                    int v = Integer.parseInt(partes[1]);
                    e.aristas.add(new int[]{u, v});
                }
            }
        }

        if (e.n <= 0) throw new IOException("Falta la linea 'n <num>' o es invalida.");
        if (e.k < 0) throw new IOException("Falta la linea 'k <num>' o es invalida.");
        return e;
    }
}
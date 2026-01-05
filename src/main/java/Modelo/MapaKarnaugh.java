package Modelo;

public class MapaKarnaugh {
    private final int numeroVariables;

    public MapaKarnaugh(int numeroVariables) {
        this.numeroVariables = numeroVariables;
    }

    public int[][] construirMapa(boolean[] salidas) {

        int filas = 1 << (numeroVariables / 2);
        int columnas = 1 << (numeroVariables - numeroVariables / 2);

        int[][] mapa = new int[filas][columnas];

        int[] grayFilas = generarGray(filas);
        int[] grayColumnas = generarGray(columnas);

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {

                int indiceTabla =
                        (grayFilas[i] << (numeroVariables - numeroVariables / 2))
                                | grayColumnas[j];

                mapa[i][j] = salidas[indiceTabla] ? 1 : 0;
            }
        }

        return mapa;
    }

    private int[] generarGray(int n) {
        int[] gray = new int[n];
        for (int i = 0; i < n; i++) {
            gray[i] = i ^ (i >> 1);
        }
        return gray;
    }
}


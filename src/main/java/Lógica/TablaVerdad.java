package Lógica;

public class TablaVerdad {

    private final int numeroVariables;

    public TablaVerdad(int numeroVariables) {
        validarNumeroVariables(numeroVariables);
        this.numeroVariables = numeroVariables;
    }
    
    public boolean[][] generarCombinaciones() {
        int totalFilas = 1 << numeroVariables;
        boolean[][] combinaciones = new boolean[totalFilas][numeroVariables];

        for (int fila = 0; fila < totalFilas; fila++) {
            for (int columna = 0; columna < numeroVariables; columna++) {
                combinaciones[fila][numeroVariables - columna - 1] =
                        ((fila >> columna) & 1) == 1;
            }
        }
        return combinaciones;
    }

    public boolean[][] generarTablaConSalida() {
        int totalFilas = 1 << numeroVariables;
        boolean[][] tabla = new boolean[totalFilas][numeroVariables + 1];

        for (int fila = 0; fila < totalFilas; fila++) {
            for (int columna = 0; columna < numeroVariables; columna++) {
                tabla[fila][numeroVariables - columna - 1] =
                        ((fila >> columna) & 1) == 1;
            }
            tabla[fila][numeroVariables] = tabla[fila][0];
        }
        return tabla;
    }
    
    private void validarNumeroVariables(int numeroVariables) {
        if (numeroVariables <= 0) {
            throw new IllegalArgumentException(
                "El número de variables debe ser mayor que cero"
            );
        }
    }
}

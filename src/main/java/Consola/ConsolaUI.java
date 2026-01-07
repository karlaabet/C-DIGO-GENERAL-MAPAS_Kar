package Consola;

import java.util.Scanner;

import Lógica.TablaVerdad;
import Modelo.MapaKarnaugh;

public class ConsolaUI {

    private Scanner scanner;

    public ConsolaUI() {
        scanner = new Scanner(System.in);
    }

    public void iniciar() {

        System.out.println("=================================");
        System.out.println("     MAPAS DE KARNAUGH     ");
        System.out.println("=================================");

        int numeroVariables = pedirNumeroVariables();

        // TABLA DE VERDAD
        TablaVerdad tabla = new TablaVerdad(numeroVariables);
        boolean[][] combinaciones = tabla.generarCombinaciones();

        boolean[] salidas = pedirSalidas(combinaciones);

        mostrarTabla(combinaciones, salidas, numeroVariables);

        MapaKarnaugh mapaKarnaugh = new MapaKarnaugh(numeroVariables);
        int[][] mapa = mapaKarnaugh.construirMapa(salidas);

        mostrarMapa(mapa, numeroVariables);

        System.out.println("\n Programa finalizado correctamente.");
    }

    private int pedirNumeroVariables() {
        while (true) {
            System.out.print("Ingrese el número de variables: ");
            try {
                int n = Integer.parseInt(scanner.nextLine());
                if (n > 0) return n;
                System.out.println("Debe ser mayor que 0.");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private boolean[] pedirSalidas(boolean[][] combinaciones) {
        boolean[] salidas = new boolean[combinaciones.length];

        System.out.println("\nIngrese los valores de salida F (0 o 1):");

        for (int i = 0; i < combinaciones.length; i++) {
            while (true) {
                System.out.print("Fila " + i + " " + mostrarCombinacion(combinaciones[i]) + " => F = ");
                String input = scanner.nextLine();

                if (input.equals("0")) {
                    salidas[i] = false;
                    break;
                }
                if (input.equals("1")) {
                    salidas[i] = true;
                    break;
                }
                System.out.println("Solo se permite 0 o 1.");
            }
        }
        return salidas;
    }

    private void mostrarTabla(boolean[][] combinaciones, boolean[] salidas, int n) {
        System.out.println("\nTABLA DE VERDAD");

        for (int i = 0; i < n; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println("| F");

        for (int i = 0; i < combinaciones.length; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print((combinaciones[i][j] ? 1 : 0) + " ");
            }
            System.out.println("| " + (salidas[i] ? 1 : 0));
        }
    }

    private void mostrarMapa(int[][] mapa, int n) {
        System.out.println("\nMAPA DE KARNAUGH");

        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void mostrarResultado(String expresion) {
        System.out.println("\nRESULTADO SIMPLIFICADO");
        System.out.println(expresion);
    }

    private String mostrarCombinacion(boolean[] fila) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < fila.length; i++) {
            sb.append(fila[i] ? 1 : 0);
            if (i < fila.length - 1) sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
}

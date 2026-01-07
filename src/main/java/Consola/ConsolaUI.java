package Consola;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Lógica.TablaVerdad;
import Lógica.QuineMcCluskey;
import Lógica.Term;
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

        TablaVerdad tabla = new TablaVerdad(numeroVariables);
        boolean[][] combinaciones = tabla.generarCombinaciones();

        boolean[] salidas = pedirSalidas(combinaciones);
        mostrarTabla(combinaciones, salidas, numeroVariables);

        MapaKarnaugh mapaKarnaugh = new MapaKarnaugh(numeroVariables);
        int[][] mapa = mapaKarnaugh.construirMapa(salidas);
        mostrarMapa(mapa, numeroVariables);

        List<Integer> minterms = obtenerMinterms(salidas);
        List<Term> primos = QuineMcCluskey.generarPrimos(minterms, numeroVariables);
        String expresion = construirExpresion(primos);

        mostrarResultado(expresion);

        System.out.println("\nPrograma finalizado correctamente.");
    }

    private List<Integer> obtenerMinterms(boolean[] salidas) {
        List<Integer> minterms = new ArrayList<>();
        for (int i = 0; i < salidas.length; i++) {
            if (salidas[i]) minterms.add(i);
        }
        return minterms;
    }

    private String construirExpresion(List<Term> primos) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < primos.size(); i++) {
            Term t = primos.get(i);
            sb.append(convertirTermino(t.bits));
            if (i < primos.size() - 1) sb.append(" + ");
        }

        return sb.length() == 0 ? "0" : sb.toString();
    }

    private String convertirTermino(String bits) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bits.length(); i++) {
            char c = bits.charAt(i);
            char var = (char) ('A' + i);

            if (c == '1') sb.append(var);
            if (c == '0') sb.append(var).append("'");
        }
        return sb.toString();
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
        System.out.println("F = " + expresion);
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

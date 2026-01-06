package Lógica;

import java.util.*;

public class Simplificador {

    private final int numeroVariables;

    public Simplificador(int numeroVariables) {
        this.numeroVariables = numeroVariables;
    }

    public String simplificar(int[][] mapa) {
        List<Integer> minterms = extraerMinterms(mapa);
        if (minterms.isEmpty()) return "0";

        List<Term> primos = QuineMcCluskey.generarPrimos(minterms, numeroVariables);
        return construirExpresion(primos);
    }

    private List<Integer> extraerMinterms(int[][] mapa) {
        List<Integer> minterms = new ArrayList<>();
        int columnas = mapa[0].length;

        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < columnas; j++) {
                if (mapa[i][j] == 1)
                    minterms.add(i * columnas + j);
            }
        }
        return minterms;
    }

    private String construirExpresion(List<Term> implicantes) {
        char[] vars = new char[numeroVariables];
        for (int i = 0; i < numeroVariables; i++)
            vars[i] = (char) ('A' + i);

        List<String> partes = new ArrayList<>();

        for (Term t : implicantes) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < t.bits.length(); i++) {
                if (t.bits.charAt(i) == '1')
                    sb.append(vars[i]);
                else if (t.bits.charAt(i) == '0')
                    sb.append(vars[i]).append("'");
            }
            partes.add(sb.length() == 0 ? "1" : sb.toString());
        }
        return String.join(" + ", partes);
    }
}
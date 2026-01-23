package Lógica;

import java.util.*;

public class Simplificador {

    private final int numeroVariables;

    public Simplificador(int numeroVariables) {
        this.numeroVariables = numeroVariables;
    }

    public String construirExpresion(List<Term> implicantes, String tipo) {

        // Caso especial: sin implicantes
        if (implicantes.isEmpty()) {
            return tipo.equals("POS") ? "1" : "0"; 
        }

        // Generar variables dinámicamente maximo 4 
        char[] vars = new char[numeroVariables];
        for (int i = 0; i < numeroVariables; i++) {
            vars[i] = (char) ('A' + i);
        }

        List<String> partes = new ArrayList<>();

        for (Term t : implicantes) {
            StringBuilder sb = new StringBuilder();

            if (tipo.equals("SOP")) {
                // SOP: AB'C
                for (int i = 0; i < t.bits.length(); i++) {
                    char b = t.bits.charAt(i);
                    if (b == '1') sb.append(vars[i]);
                    else if (b == '0') sb.append(vars[i]).append("'");
                }
                partes.add(sb.length() == 0 ? "1" : sb.toString());
            }

            else { // POS
                // POS: (A + B' + C)
                List<String> sumas = new ArrayList<>();
                for (int i = 0; i < t.bits.length(); i++) {
                    char b = t.bits.charAt(i);
                    if (b == '0') sumas.add(String.valueOf(vars[i]));
                    else if (b == '1') sumas.add(vars[i] + "'");
                }
                partes.add("(" + String.join(" + ", sumas) + ")");
            }
        }

        return tipo.equals("SOP")
                ? String.join(" + ", partes)
                : String.join("", partes);
    }
}

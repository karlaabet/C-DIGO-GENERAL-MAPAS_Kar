package Lógica;

import java.util.*;

public class Simplificador {

    private final int numeroVariables;

    public Simplificador(int numeroVariables) {
        this.numeroVariables = numeroVariables;
    }

    // MODIFICADO: Ahora recibe el tipo ("SOP" o "POS")
    public String construirExpresion(List<Term> implicantes, String tipo) {
        if (implicantes.isEmpty()) return (tipo.equals("POS")) ? "0" : "0"; 
        
        char[] vars = {'A', 'B', 'C', 'D'}; // Ajustable según numVariables
        List<String> partes = new ArrayList<>();

        for (Term t : implicantes) {
            StringBuilder sb = new StringBuilder();
            
            // LÓGICA PARA SOP (Suma de Productos) -> Ejemplo: AB' + C
            if (tipo.equals("SOP")) {
                for (int i = 0; i < t.bits.length(); i++) {
                    if (t.bits.charAt(i) == '1') sb.append(vars[i]);       // 1 es la letra
                    else if (t.bits.charAt(i) == '0') sb.append(vars[i]).append("'"); // 0 es negada
                }
            } 
            // LÓGICA PARA POS (Producto de Sumas) -> Ejemplo: (A'+B)(C')
            else {
                sb.append("(");
                List<String> sumas = new ArrayList<>();
                for (int i = 0; i < t.bits.length(); i++) {
                    // En POS es AL REVÉS: 
                    // 0 es la letra normal, 1 es la letra negada
                    if (t.bits.charAt(i) == '0') sumas.add(String.valueOf(vars[i])); 
                    else if (t.bits.charAt(i) == '1') sumas.add(vars[i] + "'");
                }
                sb.append(String.join("+", sumas));
                sb.append(")");
            }
            
            partes.add(sb.length() == 0 ? "1" : sb.toString());
        }

        // Unir las partes
        if (tipo.equals("SOP")) return String.join(" + ", partes);
        else return String.join("", partes); // En POS se multiplican (juntas)
    }
}
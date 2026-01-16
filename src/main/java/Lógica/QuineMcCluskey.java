
package Lógica;

import java.util.ArrayList;
import java.util.List;

public class QuineMcCluskey {
     public static List<Term> generarPrimos(List<Integer> minterms, int vars) {

        List<Term> actuales = new ArrayList<>();
        for (int m : minterms) {
            String bin = String.format("%" + vars + "s",
                    Integer.toBinaryString(m)).replace(' ', '0');
            actuales.add(new Term(bin));
        }

        List<Term> primos = new ArrayList<>();

        while (!actuales.isEmpty()) {
            List<Term> nuevos = new ArrayList<>();
            boolean[] usados = new boolean[actuales.size()];

            for (int i = 0; i < actuales.size(); i++) {
                for (int j = i + 1; j < actuales.size(); j++) {
                    if (actuales.get(i).diferencias(actuales.get(j)) == 1) {
                        Term t = actuales.get(i).combinar(actuales.get(j));
                        if (!contiene(nuevos, t)) nuevos.add(t);
                        usados[i] = usados[j] = true;
                    }
                }
            }

            for (int i = 0; i < actuales.size(); i++)
                if (!usados[i]) primos.add(actuales.get(i));

            actuales = nuevos;
        }

        return primos;
    }

    private static boolean contiene(List<Term> lista, Term t) {
        for (Term x : lista)
            if (x.bits.equals(t.bits)) return true;
        return false;
    }
}

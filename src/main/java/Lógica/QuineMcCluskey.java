package Lógica;

import java.util.*;

public class QuineMcCluskey {

    /**
     * Punto de entrada principal para obtener la simplificación mínima real.
     */
    public static List<Term> simplificar(List<Integer> minterms, int vars) {
        if (minterms.isEmpty()) return new ArrayList<>();
        
        // 1. Generar todos los implicantes primos posibles
        List<Term> todosLosPrimos = generarPrimos(minterms, vars);
        
        // 2. Seleccionar el conjunto mínimo absoluto que cubra todos los términos
        return seleccionarMinimos(todosLosPrimos, minterms, vars);
    }

    public static List<Term> generarPrimos(List<Integer> minterms, int vars) {
        List<Term> actuales = new ArrayList<>();
        for (int m : minterms) {
            String bin = String.format("%" + vars + "s", Integer.toBinaryString(m)).replace(' ', '0');
            actuales.add(new Term(bin));
        }

        List<Term> primos = new ArrayList<>();
        while (!actuales.isEmpty()) {
            List<Term> nuevos = new ArrayList<>();
            boolean[] usados = new boolean[actuales.size()];

            for (int i = 0; i < actuales.size(); i++) {
                for (int j = i + 1; j < actuales.size(); j++) {
                    // Usar puedeCombinar para respetar la posición de los guiones '-'
                    if (actuales.get(i).puedeCombinar(actuales.get(j))) {
                        Term t = actuales.get(i).combinar(actuales.get(j));
                        if (!contiene(nuevos, t)) nuevos.add(t);
                        usados[i] = usados[j] = true;
                    }
                }
            }

            for (int i = 0; i < actuales.size(); i++) {
                if (!usados[i] && !contiene(primos, actuales.get(i))) {
                    primos.add(actuales.get(i));
                }
            }
            actuales = nuevos;
        }
        return primos;
    }

    private static List<Term> seleccionarMinimos(List<Term> primos, List<Integer> minterms, int vars) {
        List<Term> esenciales = new ArrayList<>();
        Set<Integer> cubiertosEPI = new HashSet<>();

        // 1. Identificar Primos Esenciales (EPI): Términos que solo un primo puede cubrir
        for (int m : minterms) {
            Term candidato = null;
            int count = 0;
            for (Term p : primos) {
                if (p.cubreMintermino(m, vars)) {
                    candidato = p;
                    count++;
                }
            }
            if (count == 1 && !contiene(esenciales, candidato)) {
                esenciales.add(candidato);
                for (int mt : minterms) {
                    if (candidato.cubreMintermino(mt, vars)) cubiertosEPI.add(mt);
                }
            }
        }

        // 2. Identificar términos restantes que faltan cubrir
        List<Integer> faltantes = new ArrayList<>();
        for (int m : minterms) {
            if (!cubiertosEPI.contains(m)) faltantes.add(m);
        }

        if (faltantes.isEmpty()) return esenciales;

        // 3. Búsqueda Recursiva para encontrar la mejor combinación de los restantes
        List<Term> candidatosRestantes = new ArrayList<>();
        for (Term p : primos) {
            if (!contiene(esenciales, p)) candidatosRestantes.add(p);
        }

        List<Term> mejorResto = encontrarMinimoRecursivo(faltantes, candidatosRestantes, vars);
        esenciales.addAll(mejorResto);
        
        return esenciales;
    }

    private static List<Term> encontrarMinimoRecursivo(List<Integer> faltantes, List<Term> disponibles, int vars) {
        List<List<Term>> solucionesValidas = new ArrayList<>();
        explorar(faltantes, disponibles, 0, new ArrayList<>(), solucionesValidas, vars);

        List<Term> mejor = null;
        for (List<Term> sol : solucionesValidas) {
            // Criterio: Menor cantidad de términos (paréntesis)
            if (mejor == null || sol.size() < mejor.size()) {
                mejor = sol;
            } 
            // En empate, elegir el que tenga menos letras (literales)
            else if (sol.size() == mejor.size() && contarLetras(sol) < contarLetras(mejor)) {
                mejor = sol;
            }
        }
        return mejor != null ? mejor : new ArrayList<>();
    }

    private static void explorar(List<Integer> faltantes, List<Term> disponibles, int index, 
                                 List<Term> actual, List<List<Term>> soluciones, int vars) {
        if (faltantes.isEmpty()) {
            soluciones.add(new ArrayList<>(actual));
            return;
        }
        if (index >= disponibles.size()) return;

        // Probar incluyendo el término si aporta cobertura
        Term t = disponibles.get(index);
        List<Integer> nuevosFaltantes = new ArrayList<>();
        boolean aporta = false;
        for (int f : faltantes) {
            if (t.cubreMintermino(f, vars)) aporta = true;
            else nuevosFaltantes.add(f);
        }

        if (aporta) {
            actual.add(t);
            explorar(nuevosFaltantes, disponibles, index + 1, actual, soluciones, vars);
            actual.remove(actual.size() - 1);
        }

        explorar(faltantes, disponibles, index + 1, actual, soluciones, vars);
    }

    private static int contarLetras(List<Term> lista) {
        int count = 0;
        for (Term t : lista) {
            for (char c : t.bits.toCharArray()) if (c != '-') count++;
        }
        return count;
    }

    private static boolean contiene(List<Term> lista, Term t) {
        if (t == null) return false;
        for (Term x : lista) {
            if (x.bits.equals(t.bits)) return true;
        }
        return false;
    }
}

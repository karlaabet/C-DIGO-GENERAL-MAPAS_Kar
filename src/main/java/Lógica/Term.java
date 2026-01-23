package Lógica;

import java.util.Objects;

public class Term {
    public String bits;

    public Term(String bits) {
        this.bits = bits;
    }

    // VITAL: Permite que List.contains() y HashSet funcionen correctamente
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term);
        return Objects.equals(bits, term.bits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bits);
    }

    // Asegura que los guiones estén en la misma posición antes de combinar
    public boolean puedeCombinar(Term otro) {
        int dif = 0;
        for (int i = 0; i < bits.length(); i++) {
            if ((this.bits.charAt(i) == '-') != (otro.bits.charAt(i) == '-')) return false;
            if (this.bits.charAt(i) != otro.bits.charAt(i)) dif++;
        }
        return dif == 1;
    }

    public Term combinar(Term otro) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bits.length(); i++) {
            sb.append(bits.charAt(i) == otro.bits.charAt(i) ? bits.charAt(i) : '-');
        }
        return new Term(sb.toString());
    }

    public boolean cubreMintermino(int m, int vars) {
        String bin = String.format("%" + vars + "s", Integer.toBinaryString(m)).replace(' ', '0');
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) != '-' && bits.charAt(i) != bin.charAt(i)) return false;
        }
        return true;
    }
    
}

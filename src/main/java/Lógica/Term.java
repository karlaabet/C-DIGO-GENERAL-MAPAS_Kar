
package Lógica;

public class Term {
    String bits;
    boolean usado = false;

    public Term(String bits) {
        this.bits = bits;
    }

    public int diferencias(Term otro) {
        int dif = 0;
        for (int i = 0; i < bits.length(); i++)
            if (bits.charAt(i) != otro.bits.charAt(i)) dif++;
        return dif;
    }

    public Term combinar(Term otro) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bits.length(); i++) {
            sb.append(bits.charAt(i) == otro.bits.charAt(i)
                    ? bits.charAt(i)
                    : '-');
        }
        return new Term(sb.toString());
    }
}

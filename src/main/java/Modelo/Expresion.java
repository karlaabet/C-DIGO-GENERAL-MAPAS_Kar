package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Expresion {

    private final List<Termino> terminos;

    public Expresion() {
        this.terminos = new ArrayList<>();
    }

    public void agregarTermino(Termino termino) {
        if (termino != null) {
            terminos.add(termino);
        }
    }

    public List<Termino> getTerminos() {
        return new ArrayList<>(terminos);
    }

    public boolean estaVacia() {
        return terminos.isEmpty();
    }

    @Override
    public String toString() {
        if (terminos.isEmpty()) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        for (Termino t : terminos) {
            sb.append("(").append(t).append(")").append(" + ");
        }
        sb.delete(sb.length() - 3, sb.length());
        return sb.toString();
    }
}

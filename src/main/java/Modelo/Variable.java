package Modelo;

public class Variable {

    private final char nombre;
    private final boolean negada;

    public Variable(char nombre, boolean negada) {
        this.nombre = nombre;
        this.negada = negada;
    }

    public char getNombre() {
        return nombre;
    }

    public boolean isNegada() {
        return negada;
    }

    public Variable negar() {
        return new Variable(nombre, !negada);
    }

    @Override
    public String toString() {
        return negada ? "¬" + nombre : String.valueOf(nombre);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Variable)) return false;

        Variable otra = (Variable) obj;
        return nombre == otra.nombre && negada == otra.negada;
    }

    @Override
    public int hashCode() {
        return (nombre * 31) + (negada ? 1 : 0);
    }
}

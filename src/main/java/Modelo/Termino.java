package Modelo;

import java.util.LinkedHashSet;
import java.util.Set;

public class Termino {

    private final Set<Variable> variables;

    public Termino() {
        this.variables = new LinkedHashSet<>();
    }

    public void agregarVariable(Variable variable) {
        variables.add(variable);
    }

    public Set<Variable> getVariables() {
        return new LinkedHashSet<>(variables);
    }

    public boolean estaVacio() {
        return variables.isEmpty();
    }

    @Override
    public String toString() {
        if (variables.isEmpty()) {
            return "1";
        }

        StringBuilder sb = new StringBuilder();
        for (Variable v : variables) {
            sb.append(v).append("·");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}

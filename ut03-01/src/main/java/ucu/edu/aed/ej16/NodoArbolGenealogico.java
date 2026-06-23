package ucu.edu.aed.ej16;

import java.util.ArrayList;
import java.util.List;

public class NodoArbolGenealogico {

    private Persona persona;
    private List<NodoArbolGenealogico> hijos;

    public NodoArbolGenealogico(Persona persona) {
        this.persona = persona;
        this.hijos = new ArrayList<>();
    }

    public Persona getPersona() {
        return persona;
    }

    public List<NodoArbolGenealogico> getHijos() {
        return hijos;
    }

    public void agregarHijo(NodoArbolGenealogico hijo) {
        hijos.add(hijo);
    }

    public boolean tieneHijos() {
        return !hijos.isEmpty();
    }
}

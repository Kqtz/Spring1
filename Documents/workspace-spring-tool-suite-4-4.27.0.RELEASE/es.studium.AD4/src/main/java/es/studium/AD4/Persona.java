package es.studium.AD4;

/**
 * Representa a una persona con un nombre.
 */
public class Persona {
    private String nombre;

    public Persona() { }

    public Persona(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Persona: " + nombre;
    }
}

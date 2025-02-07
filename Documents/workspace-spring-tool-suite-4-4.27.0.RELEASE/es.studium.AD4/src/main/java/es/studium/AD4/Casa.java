package es.studium.AD4;

import java.util.List;

/**
 * Clase que representa una casa con un nombre, una lista de habitaciones y un propietario.
 */
public class Casa {
    private String nombreCasa;       // Nombre de la casa
    private List<Habitacion> habitaciones; // Lista de habitaciones que pertenecen a la casa
    private Persona propietario;     // Propietario de la casa

    /**
     * Constructor vacío necesario para frameworks como Spring que pueden necesitar instanciar
     * la clase sin parámetros antes de asignar valores.
     */
    public Casa() { }

    /**
     * Constructor que permite inicializar una casa con un nombre, una lista de habitaciones y un propietario.
     *
     * nombreCasa Nombre de la casa
     * habitaciones Lista de habitaciones que contiene la casa
     * propietario Propietario de la casa
     */
    public Casa(String nombreCasa, List<Habitacion> habitaciones, Persona propietario) {
        this.nombreCasa = nombreCasa;
        this.habitaciones = habitaciones;
        this.propietario = propietario;
    }

    /**
     * Obtiene el nombre de la casa.
     * Nombre de la casa.
     */
    public String getNombreCasa() {
        return nombreCasa;
    }

    /**
     * Establece el nombre de la casa.
     * nombreCasa Nuevo nombre para la casa.
     */
    public void setNombreCasa(String nombreCasa) {
        this.nombreCasa = nombreCasa;
    }

    /**
     * Obtiene la lista de habitaciones de la casa.
     * Lista de habitaciones.
     */
    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    /**
     * Establece una nueva lista de habitaciones para la casa.
     * habitaciones Lista de habitaciones.
     */
    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    /**
     * Obtiene el propietario de la casa.
     * Propietario de la casa.
     */
    public Persona getPropietario() {
        return propietario;
    }

    /**
     * Establece un nuevo propietario para la casa.
     * propietario Nuevo propietario.
     */
    public void setPropietario(Persona propietario) {
        this.propietario = propietario;
    }

    /**
     * Representación en formato de texto de la casa, incluyendo el nombre, propietario y habitaciones.
     * 
     * Cadena con la información de la casa.
     */
    @Override
    public String toString() {
        return "Casa: " + nombreCasa + "\nPropietario: " + propietario + "\nHabitaciones: " + habitaciones;
    }
}

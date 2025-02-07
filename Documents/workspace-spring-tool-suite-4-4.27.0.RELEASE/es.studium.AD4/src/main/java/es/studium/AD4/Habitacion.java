package es.studium.AD4;

/**
 * Clase que representa una habitación dentro de una casa.
 * Contiene información sobre su nombre, piso, si tiene ventana y quién la ocupa.
 */
public class Habitacion {
    private String nombreHabitacion;  // Nombre de la habitación (ej: "Sala", "Dormitorio")
    private Integer pisoHabitacion;   // Número de piso en el que se encuentra la habitación
    private Boolean tieneVentana;     // Indica si la habitación tiene ventana (true o false)
    private Persona persona;          // Persona que ocupa la habitación

    /**
     * Constructor vacío necesario para frameworks como Spring que pueden necesitar instanciar
     * la clase sin parámetros antes de asignar valores.
     */
    public Habitacion() { }

    /**
     * Constructor que inicializa una habitación con todos sus atributos.
     *
     * nombreHabitacion Nombre de la habitación.
     * pisoHabitacion Número del piso donde está la habitación.
     * tieneVentana Indica si la habitación tiene ventana o no.
     * persona Persona asignada a la habitación.
     */
    public Habitacion(String nombreHabitacion, Integer pisoHabitacion, Boolean tieneVentana, Persona persona) {
        this.nombreHabitacion = nombreHabitacion;
        this.pisoHabitacion = pisoHabitacion;
        this.tieneVentana = tieneVentana;
        this.persona = persona;
    }

    /**
     * Obtiene el nombre de la habitación.
     * Nombre de la habitación.
     */
    public String getNombreHabitacion() {
        return nombreHabitacion;
    }

    /**
     * Establece el nombre de la habitación.
     * nombreHabitacion Nuevo nombre de la habitación.
     */
    public void setNombreHabitacion(String nombreHabitacion) {
        this.nombreHabitacion = nombreHabitacion;
    }

    /**
     * Obtiene el número de piso en el que se encuentra la habitación.
     * Número de piso.
     */
    public Integer getPisoHabitacion() {
        return pisoHabitacion;
    }

    /**
     * Establece el número de piso de la habitación.
     * pisoHabitacion Número de piso.
     */
    public void setPisoHabitacion(Integer pisoHabitacion) {
        this.pisoHabitacion = pisoHabitacion;
    }

    /**
     * Indica si la habitación tiene ventana.
     * `true` si tiene ventana, `false` si no.
     */
    public Boolean getTieneVentana() {
        return tieneVentana;
    }

    /**
     * Establece si la habitación tiene ventana.
     * tieneVentana `true` si tiene ventana, `false` si no.
     */
    public void setTieneVentana(Boolean tieneVentana) {
        this.tieneVentana = tieneVentana;
    }

    /**
     * Obtiene la persona asignada a la habitación.
     * Persona que ocupa la habitación.
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Asigna una persona a la habitación.
     * persona Nueva persona que ocupará la habitación.
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * Devuelve una representación en formato de texto de la habitación, incluyendo su nombre,
     * piso, si tiene ventana y el nombre de la persona que la ocupa.
     *
     * Cadena con la información de la habitación.
     */
    @Override
    public String toString() {
        return "Habitación: " + nombreHabitacion +
               ", Piso: " + pisoHabitacion +
               ", Ventana: " + tieneVentana +
               ", Ocupante: " + persona;
    }
}

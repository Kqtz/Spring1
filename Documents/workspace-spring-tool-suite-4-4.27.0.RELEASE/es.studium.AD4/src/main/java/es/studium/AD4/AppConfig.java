package es.studium.AD4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

/**
 * Esta clase define la configuración de la aplicación mediante anotaciones de Spring.
 * Se encarga de crear y gestionar los beans necesarios para representar una casa con habitaciones y personas.
 */
@Configuration // Indica que esta clase es de configuración y define beans para el contenedor de Spring.
public class AppConfig {

    /**
     * Crea un bean que representa al propietario de la casa.
     * Se instancia un objeto de la clase Persona con el nombre "Juan Pérez".
     */
    @Bean
    public Persona propietario() {
        return new Persona("Juan Pérez");
    }

    /**
     * Crea un bean que representa a la persona asignada a la primera habitación.
     */
    @Bean
    public Persona habitacionPersona1() {
        return new Persona("Ana Gómez");
    }

    /**
     * Crea un bean que representa a la persona asignada a la segunda habitación.
     */
    @Bean
    public Persona habitacionPersona2() {
        return new Persona("Carlos Sánchez");
    }

    /**
     * Crea un bean para la primera habitación.
     * Se le asigna el nombre "Sala", número 1, está disponible (true) y tiene una persona asociada.
     */
    @Bean
    public Habitacion habitacion1() {
        return new Habitacion("Sala", 1, true, habitacionPersona1());
    }

    /**
     * Crea un bean para la segunda habitación.
     * Se le asigna el nombre "Dormitorio", número 2, no está disponible (false) y tiene una persona asociada.
     */
    @Bean
    public Habitacion habitacion2() {
        return new Habitacion("Dormitorio", 2, false, habitacionPersona2());
    }

    /**
     * Crea un bean para la casa.
     * Se le asigna un nombre dinámico basado en el propietario.
     * Contiene una lista de habitaciones y el propietario correspondiente.
     */
    @Bean
    public Casa casa() {
        return new Casa("Casa de " + propietario().getNombre(),
                        Arrays.asList(habitacion1(), habitacion2()), // Se agregan las habitaciones a la casa
                        propietario()); // Se asigna el propietario de la casa
    }
}

package es.studium.AD4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Clase principal que carga la configuración de Spring y obtiene el bean de la casa.
 */
public class Principal {
    public static void main(String[] args) {
        // Carga el contexto de Spring desde el archivo XML
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        // Obtiene el bean de la casa y lo imprime
        Casa casa = context.getBean("casa", Casa.class);
        System.out.println(casa);

        // Cierra el contexto de Spring
        ((ClassPathXmlApplicationContext) context).close();
    }
}

package es.studium.juego;
import javax.swing.SwingUtilities;

public class PiedraPapelTijera {
    public static void main(String[] args) {
        System.out.println("Iniciando la aplicación...");
        SwingUtilities.invokeLater(() -> {
            System.out.println("Abriendo el menú principal...");
            new MainMenu();
        });
    }
}

package es.studium.juego;
import javax.swing.*;
import java.awt.*;

public class HelpWindow extends JFrame {
    public HelpWindow() {
        setTitle("Ayuda - Manual de Usuario");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        

        JEditorPane helpText = new JEditorPane();
        helpText.setContentType("text/html");
        helpText.setEditable(false);
        helpText.setText(
            "<html>" +
            "<head>" +
            "<style>" +
            "body { font-family: Arial, sans-serif; font-size: 14px; margin: 10px; color: black; }" +
            "h1 { color: red; }" +
            "h2 { color: red; margin-bottom: 5px; }" +
            "strong { color: red; }" +
            "ul { margin: 0 0 10px 20px; }" +
            "li { margin-bottom: 5px; }" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<h1>Manual de Usuario</h1>" +
            "<h2>1. Menú Principal:</h2>" +
            "<ul>" +
            "<li><strong>Nueva Partida:</strong> Introduzca su nombre y proceda a iniciar una partida contra el adversario computarizado. Durante la partida, se reproducirá una banda sonora que realzará su experiencia lúdica.</li>" +
            "<li><strong>Ranking:</strong> Consulte el listado de las diez mejores puntuaciones registradas en la base de datos.</li>" +
            "<li><strong>Ayuda:</strong> Acceda al presente manual, en el que se exponen detalladamente todas las funcionalidades del juego.</li>" +
            "</ul>" +
            "<h2>2. Durante la Partida:</h2>" +
            "<ul>" +
            "<li><strong>Interfaz:</strong> La pantalla exhibe la puntuación actual, el número de vidas (representadas mediante corazones) y la imagen correspondiente a la jugada de la máquina.</li>" +
            "<li><strong>Selección de Jugada:</strong> Elija entre <strong>Piedra</strong>, <strong>Papel</strong> o <strong>Tijeras</strong> pulsando el botón adecuado o utilizando las teclas 1 (Piedra), 2 (Papel) o 3 (Tijeras) de su teclado.</li>" +
            "<li><strong>Animación de la IA:</strong> Al comenzar cada ronda, el sistema presenta una animación en la que se alternan las imágenes de piedra, papel y tijeras. Durante dicha animación se exhibe el mensaje <strong>\"...\"</strong>.</li>" +
            "<li><strong>Resultado:</strong> Una vez finalizada la animación, se revela la jugada definitiva de la máquina y se actualiza el resultado, que puede ser victoria, derrota o empate. Una victoria incrementará su puntuación, mientras que una derrota reducirá sus vidas.</li>" +
            "<li><strong>Controles Adicionales:</strong> Durante la partida, podrá ajustar el volumen de la música utilizando las teclas '+' (para aumentarlo) y '-' (para disminuirlo).</li>" +
            "</ul>" +
            "<h2>3. Finalización del Juego:</h2>" +
            "<ul>" +
            "<li>La partida continuará hasta que se agoten sus vidas. Al producirse tal situación, se mostrará un mensaje informándole de que la partida ha concluido y su puntuación se registrará en la base de datos.</li>" +
            "<li>En determinadas versiones, dispondrá de un botón <strong>\"Finalizar\"</strong> que le permitirá concluir la partida manualmente, registrándose en ese instante su puntuación actual.</li>" +
            "</ul>" +
            "<h2>4. Objetivo:</h2>" +
            "<ul>" +
            "<li>El objetivo consiste en acumular la mayor puntuación posible obteniendo victorias en las rondas de Piedra, Papel o Tijeras.</li>" +
            "</ul>" +
            "<h2>5. Consideraciones Técnicas:</h2>" +
            "<ul>" +
            "<li>La interfaz gráfica ha sido desarrollada con <strong>Java Swing</strong>, proporcionando una experiencia visual enriquecida con animaciones y efectos sonoros.</li>" +
            "<li>La banda sonora de fondo, así como los efectos sonoros asociados a las situaciones de victoria o derrota, se reproducen de forma automática para mejorar la inmersión.</li>" +
            "<li>Los resultados de las partidas se almacenan en una base de datos, lo que permite consultar en el Ranking las puntuaciones más destacadas.</li>" +
            "</ul>" +
            "<p>¡<strong>Disfrute del juego y le deseamos la mejor de las suertes!</strong></p>" +
            "</body>" +
            "</html>"
        );
        

        helpText.setCaretPosition(0);
        
        JScrollPane scrollPane = new JScrollPane(helpText);
        add(scrollPane, BorderLayout.CENTER);
        
        setVisible(true);
    }
}

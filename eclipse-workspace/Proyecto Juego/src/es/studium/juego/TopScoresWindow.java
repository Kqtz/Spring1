package es.studium.juego;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TopScoresWindow extends JFrame {
    public TopScoresWindow(DatabaseManager dbManager) {
        setTitle("Top 10 Mejores Partidas");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        List<ScoreRecord> topScores = dbManager.getTopScores();
        String[] columnNames = {"Jugador", "Puntuación", "Fecha"};
        Object[][] data = new Object[topScores.size()][3];
        for (int i = 0; i < topScores.size(); i++) {
            ScoreRecord record = topScores.get(i);
            data[i][0] = record.getPlayerName();
            data[i][1] = record.getScore();
            data[i][2] = record.getDate();
        }
        
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        setVisible(true);
    }
}

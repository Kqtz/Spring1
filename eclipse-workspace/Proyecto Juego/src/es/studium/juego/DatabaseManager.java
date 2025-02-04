package es.studium.juego;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection conn;
    
    public DatabaseManager() {
        try {        
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Papeltijerajuegorecu.db");
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS scores ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "playerName TEXT NOT NULL, "
                   + "score INTEGER NOT NULL, "
                   + "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                   + ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insertScore(String playerName, int score) {
        String sql = "INSERT INTO scores(playerName, score) VALUES(?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<ScoreRecord> getTopScores() {
        List<ScoreRecord> scores = new ArrayList<>();
        String sql = "SELECT playerName, score, date FROM scores ORDER BY score DESC, date ASC LIMIT 10";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String playerName = rs.getString("playerName");
                int score = rs.getInt("score");
                String date = rs.getString("date");
                scores.add(new ScoreRecord(playerName, score, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }
}

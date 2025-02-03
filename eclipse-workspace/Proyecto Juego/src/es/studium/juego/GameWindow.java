package es.studium.juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameWindow extends JFrame {
    private String playerName;
    private MusicPlayer musicPlayer;
    private DatabaseManager dbManager;
    private int score = 0;
    private int lives = 6; // 6 medios corazones (3 completos)

    private JLabel scoreLabel;
    private JLabel aiChoiceLabel;
    private JLabel resultLabel;
    private JPanel livesPanel;

    private Random random = new Random();
    // Variable para controlar que el mensaje inicial se muestre solo la primera vez
    private boolean firstRound = true;
    // Variable para evitar que el jugador inicie otra ronda mientras se procesa la actual
    private boolean roundInProgress = false;

    public GameWindow(String playerName, DatabaseManager dbManager) {
        this.playerName = playerName;
        this.dbManager = dbManager;

        setTitle("Piedra, Papel o Tijera - Jugador: " + playerName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // 🎯 Panel superior con puntuación y corazones
        JPanel topPanel = new JPanel(new BorderLayout());
        scoreLabel = new JLabel("Puntuación: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(scoreLabel, BorderLayout.NORTH);

        // 💖 Panel de vidas (corazones)
        livesPanel = new JPanel(new FlowLayout());
        updateLivesDisplay();
        topPanel.add(livesPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // 🎮 Panel de botones de elección
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton piedraButton = createImageButton("piedra.png");
        JButton papelButton = createImageButton("papel.png");
        JButton tijeraButton = createImageButton("tijeras.png");

        buttonsPanel.add(piedraButton);
        buttonsPanel.add(papelButton);
        buttonsPanel.add(tijeraButton);
        add(buttonsPanel, BorderLayout.CENTER);

        // 📜 Panel de resultado y elección de la IA
        JPanel resultPanel = new JPanel(new BorderLayout());
        aiChoiceLabel = new JLabel("", SwingConstants.CENTER);
        // El mensaje por defecto se muestra solo la primera vez
        resultLabel = new JLabel("¡Elige qué vas a usar!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.BOLD, 40));

        resultPanel.add(aiChoiceLabel, BorderLayout.NORTH);
        resultPanel.add(resultLabel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.SOUTH);

        // 🎮 Acciones de los botones
        piedraButton.addActionListener(e -> playRound("Piedra"));
        papelButton.addActionListener(e -> playRound("Papel"));
        tijeraButton.addActionListener(e -> playRound("Tijeras"));

        // ⌨ Manejo por teclado
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_1: playRound("Piedra"); break;
                    case KeyEvent.VK_2: playRound("Papel"); break;
                    case KeyEvent.VK_3: playRound("Tijeras"); break;
                    case KeyEvent.VK_PLUS: 
                    case KeyEvent.VK_ADD:
                        musicPlayer.setVolume(musicPlayer.getVolume() + 0.1f);
                        break;
                    case KeyEvent.VK_MINUS: 
                    case KeyEvent.VK_SUBTRACT:
                        musicPlayer.setVolume(musicPlayer.getVolume() - 0.1f);
                        break;
                }
            }
        });

        setFocusable(true);
        setVisible(true);
    }

    // 🎨 Método para crear botones con imágenes
    private JButton createImageButton(String imageName) {
        JButton button = new JButton();
        try {
            Image img = new ImageIcon(getClass().getClassLoader().getResource("resources/" + imageName)).getImage();
            Image resizedImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(resizedImg));
        } catch (Exception e) {
            e.printStackTrace();
        }
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    // 🔄 Método para jugar una ronda
    private void playRound(String playerMove) {
        // Si ya hay una ronda en curso, se ignora la petición
        if (roundInProgress) {
            return;
        }
        roundInProgress = true;
        
        // Si no es la primera vez, durante la animación se muestra "..."
        if (!firstRound) {
            resultLabel.setText("...");
        }
        // Tras la primera jugada, ya no se muestra el mensaje inicial
        firstRound = false;

        String computerMove = getComputerMove();
        String[] images = {"piedra.png", "papel.png", "tijeras.png"};

        Timer aiAnimationTimer = new Timer(100, null);
        aiAnimationTimer.addActionListener(new ActionListener() {
            int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                aiChoiceLabel.setIcon(loadScaledImage(images[count % 3], 120, 120));
                count++;

                if (count >= 6) {
                    aiAnimationTimer.stop();
                    aiChoiceLabel.setIcon(loadScaledImage(computerMove.toLowerCase() + ".png", 120, 120));

                    // 🏆 Actualizar puntaje y vidas después de la animación
                    SwingUtilities.invokeLater(() -> updateGameStatus(playerMove, computerMove));
                    // Permitir iniciar una nueva ronda
                    roundInProgress = false;
                }
            }
        });

        aiAnimationTimer.start();
    }

    private void updateGameStatus(String playerMove, String computerMove) {
        String result;
        if (playerMove.equalsIgnoreCase(computerMove)) {
            result = "Empate!";
        } else if ((playerMove.equals("Piedra") && computerMove.equals("Tijeras")) ||
                   (playerMove.equals("Papel") && computerMove.equals("Piedra")) ||
                   (playerMove.equals("Tijeras") && computerMove.equals("Papel"))) {
            result = "Ganaste!";
            score++;
            SoundEffects.playSound("win.wav"); // 🔊 Sonido de victoria
        } else {
            result = "Perdiste...";
            lives--;
            SoundEffects.playSound("lose.wav"); // 🔊 Sonido de derrota
        }

        // 📊 Actualizar la interfaz tras la animación
        scoreLabel.setText("Puntuación: " + score);
        resultLabel.setText(result);
        updateLivesDisplay();

        // ❌ Si se quedan sin corazones, termina el juego
        if (lives <= 0) {
            JOptionPane.showMessageDialog(this, "¡Has perdido todas tus vidas! Juego terminado.");
            endGame();
        }
    }

    // 🎲 Método para que la IA elija su jugada
    private String getComputerMove() {
        int move = random.nextInt(3);
        switch (move) {
            case 0: return "Piedra";
            case 1: return "Papel";
            case 2: return "Tijeras";
            default: return "Piedra";
        }
    }

    // ❤️ Método para actualizar la visualización de las vidas
    private void updateLivesDisplay() {
        livesPanel.removeAll();

        for (int i = 0; i < lives / 2; i++) {
            livesPanel.add(new JLabel(loadScaledImage("cora.png", 40, 40)));
        }
        if (lives % 2 == 1) {
            livesPanel.add(new JLabel(loadScaledImage("mediocora.png", 40, 40)));
        }

        livesPanel.revalidate();
        livesPanel.repaint();
    }

    // 📷 Método para cargar imágenes escaladas
    private ImageIcon loadScaledImage(String imageName, int width, int height) {
        try {
            String path = "resources/" + imageName;
            java.net.URL imageUrl = getClass().getClassLoader().getResource(path);

            if (imageUrl == null) {
                System.err.println("⚠ ERROR: No se encontró la imagen: " + path);
                return null;
            }

            Image img = new ImageIcon(imageUrl).getImage();
            Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImg);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void endGame() {
        dbManager.insertScore(playerName, score);
        new MainMenu().setVisible(true);
        this.dispose();
    }
}

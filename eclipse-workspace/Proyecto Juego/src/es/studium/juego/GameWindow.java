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
    private int lives = 6; 

    private JLabel scoreLabel;
    private JLabel aiChoiceLabel;
    private JLabel resultLabel;
    private JPanel livesPanel;

    private Random random = new Random();
    private boolean firstRound = true;
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
        
        JPanel topPanel = new JPanel(new BorderLayout());
        scoreLabel = new JLabel("Puntuación: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(scoreLabel, BorderLayout.NORTH);
        
        livesPanel = new JPanel(new FlowLayout());
        updateLivesDisplay();
        topPanel.add(livesPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);


        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton piedraButton = createImageButton("piedra.png");
        JButton papelButton = createImageButton("papel.png");
        JButton tijeraButton = createImageButton("tijeras.png");

        buttonsPanel.add(piedraButton);
        buttonsPanel.add(papelButton);
        buttonsPanel.add(tijeraButton);
        add(buttonsPanel, BorderLayout.CENTER);


        JPanel resultPanel = new JPanel(new BorderLayout());
        aiChoiceLabel = new JLabel("", SwingConstants.CENTER);

        resultLabel = new JLabel("¡Elige qué vas a usar!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.BOLD, 40));

        resultPanel.add(aiChoiceLabel, BorderLayout.NORTH);
        resultPanel.add(resultLabel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.SOUTH);


        piedraButton.addActionListener(e -> playRound("Piedra"));
        papelButton.addActionListener(e -> playRound("Papel"));
        tijeraButton.addActionListener(e -> playRound("Tijeras"));


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


    private void playRound(String playerMove) {

        if (roundInProgress) {
            return;
        }
        roundInProgress = true;
        

        if (!firstRound) {
            resultLabel.setText("...");
        }

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


                    SwingUtilities.invokeLater(() -> updateGameStatus(playerMove, computerMove));

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
            SoundEffects.playSound("win.wav"); 
        } else {
            result = "Perdiste...";
            lives--;
            SoundEffects.playSound("lose.wav"); 
        }


        scoreLabel.setText("Puntuación: " + score);
        resultLabel.setText(result);
        updateLivesDisplay();


        if (lives <= 0) {
            JOptionPane.showMessageDialog(this, "¡Has perdido todas tus vidas! Juego terminado.");
            endGame();
        }
    }


    private String getComputerMove() {
        int move = random.nextInt(3);
        switch (move) {
            case 0: return "Piedra";
            case 1: return "Papel";
            case 2: return "Tijeras";
            default: return "Piedra";
        }
    }


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

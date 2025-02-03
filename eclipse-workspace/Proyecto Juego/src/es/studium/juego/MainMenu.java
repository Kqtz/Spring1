package es.studium.juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class MainMenu extends JFrame {
    private static Clip clip; // Música del menú
    private static FloatControl volumeControl;
    private static final float VOLUME_LEVEL = 0.4f; // Volumen inicial al 40%

    private DatabaseManager dbManager;
    private int windowWidth;
    private int windowHeight;
    private JButton nuevaPartidaButton;
    private JButton rankingButton;
    private JButton ayudaButton;

    public MainMenu() {
        dbManager = new DatabaseManager();

        // Cargar la imagen de fondo y reducir su tamaño a 1/3
        ImageIcon bgIcon = new ImageIcon(getClass().getClassLoader().getResource("resources/MenuPrincipal.png"));

        if (bgIcon.getIconWidth() <= 0) {
            System.err.println("Error: No se pudo cargar MenuPrincipal.png");
            windowWidth = 800;
            windowHeight = 600;
        } else {
            int originalWidth = bgIcon.getIconWidth();
            int originalHeight = bgIcon.getIconHeight();

            windowWidth = originalWidth / 3;
            windowHeight = originalHeight / 3;
        }

        setTitle("Piedra, Papel o Tijera - Alvaro Moreno Caballero");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(windowWidth, windowHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // Panel de fondo con imagen escalada
        BackgroundPanel backgroundPanel = new BackgroundPanel("resources/MenuPrincipal.png", windowWidth, windowHeight);
        backgroundPanel.setBounds(0, 0, windowWidth, windowHeight);
        // Usamos GridBagLayout para posicionar el panel de botones
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel);

        // Tamaño de los botones
        int buttonWidth = 200;
        int buttonHeight = 70;

        // Crear botones
        nuevaPartidaButton = createButton("nuevapartida.png", buttonWidth, buttonHeight);
        rankingButton = createButton("ranking.png", buttonWidth, buttonHeight);
        ayudaButton = createButton("ayuda.png", buttonWidth, buttonHeight);

        // Panel para organizar los botones en vertical
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Para que se vea el fondo
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        // Alinear los botones en el centro del panel vertical
        nuevaPartidaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rankingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ayudaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Añadir botones y espaciadores verticales
        buttonPanel.add(nuevaPartidaButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(rankingButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(ayudaButton);

        // Configurar GridBagConstraints para alinear el panel de botones en la esquina superior derecha
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHEAST; // Ancla en la esquina superior derecha
        gbc.insets = new Insets(40, 40, 40, 40); // Márgenes desde los bordes
        backgroundPanel.add(buttonPanel, gbc);

        // Acciones de los botones
        nuevaPartidaButton.addActionListener(e -> startNewGame());
        rankingButton.addActionListener(e -> openRanking());
        ayudaButton.addActionListener(e -> openHelp());

        // Iniciar la música del menú solo si no está sonando
        if (clip == null || !clip.isRunning()) {
            playMusic("resources/music.wav");
        }

        // Habilitar control por teclado
        setFocusable(true);
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_1:
                        startNewGame();
                        break;
                    case KeyEvent.VK_2:
                        openRanking();
                        break;
                    case KeyEvent.VK_3:
                        openHelp();
                        break;
                }
            }
        });

        setVisible(true);
    }

    // Método para crear botones con imágenes escaladas correctamente
    private JButton createButton(String imageName, int width, int height) {
        JButton button = new JButton();
        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/" + imageName));
            if (icon.getIconWidth() > 0) {
                Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(img));
            } else {
                System.err.println("Error: No se pudo cargar " + imageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    // Método para iniciar nueva partida
    private void startNewGame() {
        String playerName = JOptionPane.showInputDialog(this, "Introduce tu nombre:");
        if (playerName != null && !playerName.trim().isEmpty()) {
            new GameWindow(playerName, dbManager);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, introduce un nombre válido.");
        }
    }

    // Método para abrir Ranking
    private void openRanking() {
        new TopScoresWindow(dbManager);
    }

    // Método para abrir Ayuda
    private void openHelp() {
        new HelpWindow();
    }

    // Método para reproducir música del menú con volumen al 40%
    private static void playMusic(String musicPath) {
        try {
            URL url = MainMenu.class.getClassLoader().getResource(musicPath);
            if (url == null) {
                System.err.println("Error: No se pudo encontrar el archivo de música.");
                return;
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            // Configurar control de volumen
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(VOLUME_LEVEL);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Método para establecer el volumen inicial al 40%
    private static void setVolume(float volume) {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            volumeControl.setValue(min + (max - min) * volume);
        }
    }

    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu());
    }
}

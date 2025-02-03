package es.studium.juego;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;
    private int width, height;

    public BackgroundPanel(String imagePath, int width, int height) {
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
            backgroundImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            this.width = width;
            this.height = height;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, width, height, this);
        }
    }
}

package es.studium.juego;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundEffects {
    public static void playSound(String fileName) {
        try {
            URL soundURL = SoundEffects.class.getClassLoader().getResource("resources/" + fileName);
            if (soundURL == null) {
                System.err.println("⚠ ERROR: No se encontró el sonido " + fileName);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}

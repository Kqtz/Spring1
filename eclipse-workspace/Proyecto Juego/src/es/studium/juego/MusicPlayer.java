package es.studium.juego;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class MusicPlayer {
    private Clip clip;
    private FloatControl volumeControl;
    private float volume = 0.5f; // Volumen inicial al 50%

    public MusicPlayer(String filePath) {
        try {
            URL soundURL = getClass().getClassLoader().getResource("resources/" + filePath);
            if (soundURL == null) {
                System.err.println("⚠ ERROR: No se encontró el archivo de audio " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(volume);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

    public void setVolume(float newVolume) {
        if (volumeControl != null) {
            volume = Math.min(1.0f, Math.max(0.0f, newVolume));
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            volumeControl.setValue(min + (max - min) * volume);
        }
    }

    public float getVolume() {
        return volume;
    }
}

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Sound {

    private String url;
    private Clip clip;

    public Sound(String url) {
        this.url = url;
        insertSound(this.url);
    }

    private void insertSound(String url) {
        Map<String, String> sounds = new HashMap<>();
        sounds.put("dead", url);
        try {

            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(sounds.get("dead")));
            clip = AudioSystem.getClip();
            clip.open(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSound() {
        clip.start();
    }
}

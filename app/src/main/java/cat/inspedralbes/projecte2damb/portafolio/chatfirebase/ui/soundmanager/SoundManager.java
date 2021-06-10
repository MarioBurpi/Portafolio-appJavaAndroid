package cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ui.soundmanager;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;

import cat.inspedralbes.projecte2damb.portafolio.R;

import static android.media.AudioManager.STREAM_MUSIC;

public class SoundManager {

    SoundPool soundPool;
    int idSound;
    boolean soundOK;

    public SoundManager(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            SoundPool.Builder spBuilder = new SoundPool.Builder();
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool = spBuilder.setMaxStreams(10).setAudioAttributes(attributes).build();
        }else {
            // OLD-Fashioned way
            soundPool = new SoundPool(10, STREAM_MUSIC, 0);
        }

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0){
                    // Check which sound is loaded
                    if (sampleId == idSound) {
                        soundOK = true;
                    }
                }
            }
        });
        // Finally, load the sound
        idSound = soundPool.load(context, R.raw.notification_sound, 1);
    }

    /**
     * Reproduces the sound
     */
    public void playSound (){
        if (soundOK){
            soundPool.play(idSound,1.0F, 1.0F,0,0,1.0F);
        }

    }
}

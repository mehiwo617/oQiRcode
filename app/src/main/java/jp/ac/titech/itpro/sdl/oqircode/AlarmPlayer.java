package jp.ac.titech.itpro.sdl.oqircode;

import static android.media.AudioManager.STREAM_ALARM;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import java.io.IOException;

public class AlarmPlayer {

    /**
     * The context.
     */
    private final Context mContext;
    private long mCrescendoDuration = 0;

    public AlarmPlayer(Context context) {
        mContext = context;
    }

    public boolean play(long crescendoDuration) throws IOException {
        mCrescendoDuration = crescendoDuration;
        MediaPlayer mMediaPlayer = new MediaPlayer();
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        mMediaPlayer.setDataSource(mContext, uri);

        mMediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build());


        mMediaPlayer.setAudioStreamType(STREAM_ALARM);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.prepare();
        mMediaPlayer.start();

        return true;
    }

}

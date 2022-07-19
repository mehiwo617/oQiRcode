package jp.ac.titech.itpro.sdl.oqircode;

import static android.media.AudioManager.STREAM_ALARM;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

public class AlarmPlayer {

    private final Context mContext;
    private long mCrescendoDuration = 0;
    private long mCrescendoStopTime = 0;
    private final static String TAG = AlarmPlayer.class.getSimpleName();

    MediaPlayer mMediaPlayer;

    public AlarmPlayer(Context context) {
        mContext = context;
    }

    public boolean play(long crescendoDuration) throws IOException {
        Log.d(TAG, "Alarm Play");
        mCrescendoDuration = crescendoDuration;
        mMediaPlayer = new MediaPlayer();
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        mMediaPlayer.setDataSource(mContext, uri);

        mMediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build());

        mMediaPlayer.setAudioStreamType(STREAM_ALARM);
        mMediaPlayer.setVolume(1, 1);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.prepare();
        mMediaPlayer.start();

        return true;
    }

    public boolean stop() {

        mCrescendoDuration = 0;
        mCrescendoStopTime = 0;

        // Stop audio playing
        if (mMediaPlayer != null) {
            Log.d(TAG, "Alarm Stop");
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        return true;
    }

}

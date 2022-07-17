package jp.ac.titech.itpro.sdl.oqircode;

import static android.media.AudioManager.STREAM_ALARM;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class AlarmController extends BroadcastReceiver {

    private final static String TAG = AlarmController.class.getSimpleName();

    //    private Context mContext;
    private long mCrescendoDuration = 0;
    private long mCrescendoStopTime = 0;
    MediaPlayer mMediaPlayer;
    long duration = 5;

    /**
     * 設定時間になったら動く
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast toast = Toast.makeText(context, "アラームおk！", Toast.LENGTH_SHORT);
        try {
            playRingtone(duration, context);
            Log.d(TAG, "ok アラーム");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "アラーム失敗");
        }
        toast.show();
    }

    public boolean playRingtone(long crescendoDuration, Context context) throws IOException {
        Log.d(TAG, "Alarm Play");
        mCrescendoDuration = crescendoDuration;
        mMediaPlayer = new MediaPlayer();
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        mMediaPlayer.setDataSource(context, uri);

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

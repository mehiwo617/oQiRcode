package jp.ac.titech.itpro.sdl.oqircode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class AlarmReceiver extends BroadcastReceiver {

    private final static String TAG = AlarmReceiver.class.getSimpleName();

    //    private Context mContext;
    private long mCrescendoDuration = 0;
    private long mCrescendoStopTime = 0;
    MediaPlayer mMediaPlayer;
    AlarmPlayer mAlarmPlayer;
    long duration = 5;

    public AlarmReceiver(AlarmPlayer alarmPlayer) {
        mAlarmPlayer = alarmPlayer;
    }

    /**
     * 設定時間になったら動く
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast toast = Toast.makeText(context, "アラームおk！", Toast.LENGTH_SHORT);
        if (mAlarmPlayer == null) {
            Log.d(TAG, "Alarm Player null");
        } else {
            try {
                mAlarmPlayer.play(duration);
                Log.d(TAG, "ok アラーム");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "アラーム失敗");
            }
            toast.show();
        }
    }
}

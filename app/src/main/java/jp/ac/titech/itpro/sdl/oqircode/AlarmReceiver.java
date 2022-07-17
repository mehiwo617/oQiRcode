package jp.ac.titech.itpro.sdl.oqircode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class AlarmReceiver extends BroadcastReceiver {

    private final static String TAG = AlarmReceiver.class.getSimpleName();
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
        AlarmPlayer mAlarmPlayer = new AlarmPlayer(context);
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

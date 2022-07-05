package jp.ac.titech.itpro.sdl.oqircode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast toast = Toast.makeText(context, "アラームおk！", Toast.LENGTH_SHORT);
        toast.show();
    }
}

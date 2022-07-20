package jp.ac.titech.itpro.sdl.oqircode;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Calendar;

import jp.ac.titech.itpro.sdl.oqircode.databinding.ActivityMainBinding;
import jp.ac.titech.itpro.sdl.oqircode.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.DirectionListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    private static Context mContext;
    private final String ACTION_ALARM = "jp.ac.titech.itpro.sdl.oqircode.alarm";

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    //    private Button mStopButton; //一時的に設置
    private Calendar mAlarmCalendar = Calendar.getInstance();
    private TextView mAlarmTimeText;

    private AlarmPlayer mAlarmPlayer;
    private AlarmReceiver mReceiver;
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;

    private Boolean switchBool = false;

    private QRReader mQRReader;
    // Register the launcher and result handler
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() != null) {
                    mQRReader = new QRReader(result);
                    if (mQRReader.isTrueQR()) {
                        Log.d(TAG, "Stop QRAlarm");
                        mAlarmPlayer.stop();
                        Switch mAlarmSwitch = findViewById(R.id.alarm_switch);
                        mAlarmSwitch.setChecked(false);
                        Toast.makeText(mContext, "アラームを止めました！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "それは違うQRコードです", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "スキャンをキャンセルしました", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mContext = getApplicationContext();
        mAlarmPlayer = new AlarmPlayer(mContext);
        mReceiver = new AlarmReceiver(mAlarmPlayer);

//        mStopButton = findViewById(R.id.button_stop);
        mAlarmTimeText = findViewById(R.id.alarm_time);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_ALARM);
        registerReceiver(mReceiver, intentFilter);

        setSupportActionBar(binding.appBarMain.toolbar);
        /* QRコードの読み取りを始める */
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barcodeLauncher.launch(new ScanOptions());
            }

        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onClicked(boolean isChecked) {
        if (isChecked) {
            Calendar nowCalendar = Calendar.getInstance();
            int nowDay = nowCalendar.get(Calendar.DAY_OF_MONTH);
            int nowHour = nowCalendar.get(Calendar.HOUR_OF_DAY);
            int nowMinute = nowCalendar.get(Calendar.MINUTE);
            int hour = mAlarmCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = mAlarmCalendar.get(Calendar.MINUTE);
            // 今の時刻より前なら明日起動
            Log.d(TAG, "now hour " + String.valueOf(nowHour) + " in hour " + String.valueOf(hour));
            Log.d(TAG, "now minute " + String.valueOf(nowMinute) + " in minute " + String.valueOf(minute));
            if (nowHour > hour && nowMinute > hour || nowHour > hour) {
                Log.d(TAG, "明日通知");
                mAlarmCalendar.set(Calendar.DAY_OF_MONTH, nowDay + 1);
            }
            registerAlarm();
        } else {
            unregisterAlarm();
        }
    }

    /**
     * アラームをストップさせる
     *
     * @param view ボタンの種類
     */
    public void onClickStop(View view) {

        Log.d(TAG, "Stop click");
        mAlarmPlayer.stop();
        return;
    }

    /**
     * 時間をクリックしてアラームの時間を設定する
     *
     * @param view ボタンの種類
     */
    public void onClickAlarmTimeSet(View view) {
        Log.d(TAG, "Alarm Time Set");

        TimePick mTimePickerDialog = new TimePick(mAlarmCalendar, mAlarmTimeText);
        mTimePickerDialog.show(getSupportFragmentManager(), "timePicker");

    }

    /**
     * アラームを登録する
     */
    public void registerAlarm() {
        Intent intent = new Intent();
        intent.setAction(ACTION_ALARM);
        mPendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        int hour = mAlarmCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mAlarmCalendar.get(Calendar.MINUTE);

        long alarm_time = mAlarmCalendar.getTimeInMillis();
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(alarm_time, null), mPendingIntent);
        switchBool = true;
        Toast.makeText(mContext, hour + ":" + minute + "にアラームを設定しました", Toast.LENGTH_LONG).show();
        Log.d(TAG, "switch true");
    }

    /**
     * アラームを解除する
     */
    public void unregisterAlarm() {
        mAlarmManager.cancel(mPendingIntent);
        mPendingIntent.cancel();
        switchBool = false;
        Log.d(TAG, "switch false");
    }


}
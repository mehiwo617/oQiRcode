package jp.ac.titech.itpro.sdl.oqircode;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import jp.ac.titech.itpro.sdl.oqircode.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    private static Context mContext;
    private final String ACTION_ALARM = "jp.ac.titech.itpro.sdl.oqircode.alarm";

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private Button mAlarmButton;
    private Button mStopButton; //一時的に設置

    private AlarmPlayer mAlarmPlayer;
    private AlarmReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mContext = getApplicationContext();
        mAlarmPlayer = new AlarmPlayer(mContext);
        mReceiver = new AlarmReceiver(mAlarmPlayer);

        mAlarmButton = findViewById(R.id.button_alarm);
        mStopButton = findViewById(R.id.button_stop);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_ALARM);
        registerReceiver(mReceiver, intentFilter);

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onClickStop(View view) {

        Log.d(TAG, "Stop click");
        mAlarmPlayer.stop();
        return;
    }

    public void onClickAlarm(View view) {
        Log.d(TAG, "Click Alarm");

        Intent intent = new Intent();
        intent.setAction(ACTION_ALARM);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();     //現在時間が取得される
        calendar.setTimeInMillis(System.currentTimeMillis() + 5000);   //カレンダーを5秒進める
        long alarm_time = calendar.getTimeInMillis();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(alarm_time, null), pendingIntent);

    }

}
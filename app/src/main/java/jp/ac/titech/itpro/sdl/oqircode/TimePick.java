package jp.ac.titech.itpro.sdl.oqircode;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

public class TimePick extends DialogFragment implements
        TimePickerDialog.OnTimeSetListener {

    Calendar mAlarmCalendar;
    TextView mAlarmTimeText;

    TimePick(Calendar calendar, TextView text) {
        mAlarmCalendar = calendar;
        mAlarmTimeText = text;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar mCalendar = Calendar.getInstance();
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),
                this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        mAlarmCalendar.set(Calendar.HOUR_OF_DAY, hour);
        mAlarmCalendar.set(Calendar.MINUTE, minute);
        mAlarmCalendar.set(Calendar.SECOND, 0);
        
        String time = String.format(Locale.JAPAN, "%02d:%02d", mAlarmCalendar.get(Calendar.HOUR_OF_DAY), mAlarmCalendar.get(Calendar.MINUTE));
        mAlarmTimeText.setText(time);
    }

}

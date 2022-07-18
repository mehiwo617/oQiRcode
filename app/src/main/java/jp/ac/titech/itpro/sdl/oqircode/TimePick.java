package jp.ac.titech.itpro.sdl.oqircode;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePick extends DialogFragment implements
        TimePickerDialog.OnTimeSetListener {

    Calendar mAlarmCalendar;

    TimePick(Calendar calendar) {
        mAlarmCalendar = calendar;
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
    }

}

package jp.ac.titech.itpro.sdl.oqircode.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import jp.ac.titech.itpro.sdl.oqircode.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private final static String TAG = HomeFragment.class.getSimpleName();
    private FragmentHomeBinding binding;
    private Switch mAlarmSwitch;
    private DirectionListener mListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mAlarmSwitch = binding.alarmSwitch;
        mAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                mListener.onClicked(isChecked);
            }
        });

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public interface DirectionListener {
        public void onClicked(boolean isChecked);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DirectionListener) {
            mListener = (DirectionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
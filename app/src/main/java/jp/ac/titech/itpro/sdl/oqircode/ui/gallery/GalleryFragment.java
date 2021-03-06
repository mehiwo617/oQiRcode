package jp.ac.titech.itpro.sdl.oqircode.ui.gallery;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.print.PrintHelper;

import com.google.zxing.WriterException;

import jp.ac.titech.itpro.sdl.oqircode.QRCreator;
import jp.ac.titech.itpro.sdl.oqircode.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private final static String TAG = GalleryFragment.class.getSimpleName();
    private FragmentGalleryBinding binding;
    private QRCreator mQRCreator = new QRCreator();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            ImageView imageViewQRcode = binding.imageQR;
            imageViewQRcode.setImageBitmap(mQRCreator.createQR());
        } catch (WriterException e) {
            e.printStackTrace();
            Log.e(TAG, "QRコード作成失敗");
        }

        Button mPrintButton = binding.buttonPrint;
        mPrintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888);
                try {
                    bitmap = mQRCreator.createQR();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                PrintHelper printHelper = new PrintHelper(requireContext());
                printHelper.setColorMode(PrintHelper.COLOR_MODE_COLOR);
                printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                printHelper.printBitmap("job_name", bitmap);
            }
        });

//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
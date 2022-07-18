package jp.ac.titech.itpro.sdl.oqircode;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCreator {

    private final static String TAG = QRCreator.class.getSimpleName();
    QRCode mCode = new QRCode();
    String correctQR = mCode.code;
    int size = 500;

    public Bitmap createQR() throws WriterException {

        Log.d(TAG, "create start");
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.encodeBitmap(correctQR, BarcodeFormat.QR_CODE, size, size);

        return bitmap;
    }

}

package jp.ac.titech.itpro.sdl.oqircode;

import android.util.Log;

import com.journeyapps.barcodescanner.ScanIntentResult;

public class QRReader {

    private final static String TAG = QRReader.class.getSimpleName();
    ScanIntentResult mResult;
    QRCode mCode = new QRCode();
    String correctQR = mCode.code;

    public QRReader(ScanIntentResult result) {
        mResult = result;
    }

    public Boolean isTrueQR() {
        if (mResult != null) {
            Log.d(TAG, mResult.getContents() + " を読み取った");
            if (mResult.getContents().equals(correctQR)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}

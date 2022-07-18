package jp.ac.titech.itpro.sdl.oqircode;

import android.util.Log;

import com.journeyapps.barcodescanner.ScanIntentResult;

public class QRReader {

    private final static String TAG = QRReader.class.getSimpleName();
    ScanIntentResult mResult;
    String correctQR = "https://scrapbox.io/astarothmeffime-90971468/";

    public QRReader(ScanIntentResult result) {
        mResult = result;
    }

    public Boolean isTrueQR() {
        Log.d(TAG, mResult.getContents() + " を読み取った");
        if (mResult.getContents().equals(correctQR)) {
            return true;
        } else
            return false;
    }
}

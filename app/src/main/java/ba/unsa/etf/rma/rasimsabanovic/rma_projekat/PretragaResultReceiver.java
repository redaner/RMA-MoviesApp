package ba.unsa.etf.rma.rasimsabanovic.rma_projekat;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by Rasim on 16.05.2017..
 */

public class PretragaResultReceiver extends ResultReceiver {

    private Receiver mReceiver;

    public PretragaResultReceiver(Handler h) {
        super(h);
    }

    public void setReceiver(Receiver r) {
        mReceiver = r;
    }


    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}

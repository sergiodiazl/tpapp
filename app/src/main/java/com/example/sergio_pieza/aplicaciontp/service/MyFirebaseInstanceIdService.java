package com.example.sergio_pieza.aplicaciontp.service;

/**
 * Created by sergio-pieza on 22/11/2017.
 */


        import android.util.Log;
        import com.google.firebase.iid.FirebaseInstanceId;
        import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String LOGTAG = "android-fcm";

    @Override
    public void onTokenRefresh() {
        //Se obtiene el token actualizado
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(LOGTAG, "Token actualizado: " + refreshedToken);
    }
}
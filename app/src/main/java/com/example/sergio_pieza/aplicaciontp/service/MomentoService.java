
package com.example.sergio_pieza.aplicaciontp.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.Volley.VolleyMultipartRequest;
import com.example.sergio_pieza.aplicaciontp.activity.SubirMomentoActivity;
import com.example.sergio_pieza.aplicaciontp.helper.Api;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sergio-pieza on 14/12/2017.
 */

public class MomentoService extends Service implements LocationListener{
    Bitmap imagen;
    String uId,zId,descripcion,latitud,longitud;
    LocationManager locationManager;
    private Location ubicacion;
    NotificationManagerCompat notificationManager;

    int ntInicio=1;
    int ntFin=2;
    int ntCoord=3;
    int ntRequest=4;
    int ntError=5;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @SuppressLint("MissingPermission")
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        notificationManager =NotificationManagerCompat.from(this);
        notifInicio();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        Bundle bundle=intent.getExtras();
         uId =bundle.getString("uId");
         zId =bundle.getString("zId");
        descripcion =bundle.getString("descripcion");
         imagen= SubirMomentoActivity.getBitmap();


        return START_STICKY;
    }

    private void notifInicio() {
        Resources res =this.getResources();
        Bitmap icono =BitmapFactory.decodeResource(res,R.drawable.icono);
        String titulo=res.getString(R.string.noti1titulo);
        String texto=res.getString(R.string.noti1texto);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this).
                setSmallIcon(R.drawable.notificacion).setLargeIcon(icono)
                .setContentTitle(titulo).setContentText(texto);
        notificationManager.notify(ntInicio,builder.build());

    }
    private void notifFin() {
        Resources res =this.getResources();
        Bitmap icono =BitmapFactory.decodeResource(res,R.drawable.icono);
        String titulo=res.getString(R.string.noti2titulo);
        String texto=res.getString(R.string.noti2texto);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this).
                setSmallIcon(R.drawable.notificacion).setLargeIcon(icono)
                .setContentTitle(titulo).setContentText(texto).setAutoCancel(true);
        notificationManager.notify(ntFin,builder.build());

    }
    private void notiCoord(String mensaje) {
        Resources res =this.getResources();
        Bitmap icono =BitmapFactory.decodeResource(res,R.drawable.icono);
        String titulo=res.getString(R.string.noti2titulo);
        String texto=mensaje;
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this).
                setSmallIcon(R.drawable.notificacion).setLargeIcon(icono)
                .setContentTitle(titulo).setContentText(texto).setAutoCancel(true);
        notificationManager.notify(ntCoord,builder.build());
        notificationManager.cancel(ntInicio);
    }
    private void notifRequest() {
        Resources res =this.getResources();
        Bitmap icono =BitmapFactory.decodeResource(res,R.drawable.icono);
        String titulo="entro a request";
        String texto="intentando subir";
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this).
                setSmallIcon(R.drawable.notificacion).setLargeIcon(icono)
                .setContentTitle(titulo).setContentText(texto).setAutoCancel(true);
        notificationManager.notify(ntRequest,builder.build());


    }
    private void notiError(String mensaje) {
        Resources res =this.getResources();
        Bitmap icono =BitmapFactory.decodeResource(res,R.drawable.icono);
        String titulo="Error al subir";
        String texto=mensaje;
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this).
                setSmallIcon(R.drawable.notificacion).setLargeIcon(icono)
                .setContentTitle(titulo).setContentText(texto).setAutoCancel(true);
        notificationManager.notify(ntError,builder.build());
         notificationManager.cancel(ntRequest);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }



    public void uploadBitmap(final Bitmap bitmap,String descripcion,String uId,String zId,String latitud ,String longitud) {
        notifRequest();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Api.URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            notifFin();
                            stopSelf();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "excepcion en response",
                                    Toast.LENGTH_LONG).show();
                            stopSelf();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                            notiError( getResources().getString(R.string.error_timeout_red));
                            stopSelf();
                        } else if (error instanceof AuthFailureError) {

                            notiError( getResources().getString(R.string.error_auth));
                            stopSelf();
                        } else if (error instanceof ServerError) {

                            notiError( getResources().getString(R.string.error_server));
                            stopSelf();
                        } else if (error instanceof NetworkError) {

                            notiError( getResources().getString(R.string.error_red));
                            stopSelf();
                        } else if (error instanceof ParseError) {

                            notiError( getResources().getString(R.string.error_parse));

                            stopSelf();
                        }
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("funcion", "subirMomento");
                params.put("descripcion", descripcion);
                params.put("usuario_id", uId);
                params.put("zona_id", zId);//llammar a un metodo para conseguir el id de zona usuario
                params.put("latitud", latitud);//llamar metodo para obtener la lat/olong
                params.put("longitud", longitud);
                Log.d("paramteros string:", String.valueOf(params));
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                Log.d("paramteros imagen:", String.valueOf(imagename));
                return params;
            }
            public byte[] getFileDataFromDrawable(Bitmap bitmap) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            ubicacion=location;
            latitud=String.valueOf(ubicacion.getLatitude());
            longitud=String.valueOf(ubicacion.getLongitude());
            locationManager.removeUpdates(this);
            String hay=String.valueOf(imagen!=null);
            String mensaje="u:"+latitud+" Id:"+longitud;
            notiCoord(mensaje);
            uploadBitmap(imagen,descripcion,uId,zId,latitud,longitud);


        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onProviderEnabled(String provider) {
            ubicacion=locationManager.getLastKnownLocation(provider);
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

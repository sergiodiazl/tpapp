package com.example.sergio_pieza.aplicaciontp.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.sergio_pieza.aplicaciontp.helper.Api;
import com.example.sergio_pieza.aplicaciontp.helper.SharedPrefHelper;

import com.example.sergio_pieza.aplicaciontp.service.MomentoService;
import com.example.sergio_pieza.aplicaciontp.sql.Usuario;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SubirMomentoActivity extends AppCompatActivity implements View.OnClickListener ,LocationListener{

    double latitud, longitud;
    ImageView imagenV;
    EditText eDescrip;
    Button botonSubir, botonCamara, botonCarpeta;
    FloatingActionButton fabAHome;
    static Bitmap imagen;
    //cliente de googlpe play p la ubciacion
    private GoogleApiClient mGoogleApiClient;
    //codigos para las peticiones de permisos
    static final int COARSE_LOCATION = 1;
    Usuario uActual = SharedPrefHelper.getInstance(this).getUser();
    //ubicacion
    private LocationManager locationManager;
    private Location ubicacion;
    private String provider;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //vistas
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setContentView(R.layout.activity_subir_momento);
        imagenV = (ImageView) findViewById(R.id.iSubirImagen);
        eDescrip = (EditText) findViewById(R.id.dSubirImagen);
        botonSubir = (Button) findViewById(R.id.botonSubirImagen);
        botonCarpeta = (Button) findViewById(R.id.botonImagenCarpeta);
        botonCamara = (Button) findViewById(R.id.botonImagenCamara);
        fabAHome = (FloatingActionButton) findViewById(R.id.fabAHome);
        ActivityCompat.requestPermissions(this, new String[]
                {android.Manifest.permission.ACCESS_FINE_LOCATION}, COARSE_LOCATION);

        //verifica los permisos de lctura
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }

        botonSubir.setOnClickListener(this);
        botonCarpeta.setOnClickListener(this);
        botonCamara.setOnClickListener(this);
        fabAHome.setOnClickListener(this);

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(SubirMomentoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (SubirMomentoActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SubirMomentoActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, COARSE_LOCATION);

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,200,0,this);



            if (ubicacion != null) {
                latitud= ubicacion.getLatitude();
                longitud = ubicacion.getLongitude();
                String lat = String.valueOf(latitud);
                String lon = String.valueOf(latitud);
                locationManager.removeUpdates(this);
                Log.d("gps","Your current location is"+ "\n" + "Lattitude = " + lat
                        + "\n" + "Longitude = " + lon);
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 1:
                    Uri imageUri = data.getData();
                    try {
                        //getting bitmap object from uri
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        imagen=bitmap;
                        //displaying selected image to imageview
                        imagenV.setImageBitmap(bitmap);

                        //calling the method uploadBitmap to upload image
                        // uploadBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    Bundle extras = data.getExtras();
                    Bitmap imagenBitmap = (Bitmap) extras.get("data");
                    imagenV.setImageBitmap(imagenBitmap);
                    imagen=imagenBitmap;

            }

        }
        //getting the image Uri


    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void uploadBitmap(final Bitmap bitmap) {
        final String descripcion = eDescrip.getText().toString().trim();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Api.URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            Toast.makeText(getApplicationContext(),
                                    "subido desde activity",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.error_timeout_red),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.error_auth),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.error_server),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.error_red),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.error_parse),
                                    Toast.LENGTH_LONG).show();
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
                String uId = String.valueOf(uActual.getId());
                String zId = String.valueOf(uActual.getZona_id());
                Map<String, String> params = new HashMap<>();
                params.put("funcion", "subirMomento");
                params.put("descripcion", descripcion);
                params.put("usuario_id", uId);
                params.put("zona_id", zId);//llammar a un metodo para conseguir el id de zona usuario
                params.put("latitud", String.valueOf(latitud));//llamar metodo para obtener la lat/olong
                params.put("longitud", String.valueOf(longitud));
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
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }


    private void aHome() {
        finish();
        startActivity(new Intent(this, HomeActivity.class));
        return;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.botonImagenCamara:
                getLocation();
                Intent iCamara = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
                if (iCamara.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(iCamara, 2);
                }
                break;

            case R.id.botonImagenCarpeta:
                getLocation();
                Intent iCarpeta = new Intent(Intent.ACTION_PICK);
                iCarpeta.setType("image/*");
                startActivityForResult(iCarpeta, 1);

                break;
            case R.id.botonSubirImagen:

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    Intent iService=new Intent(getBaseContext(), MomentoService.class);
                    iService.putExtra("descripcion",eDescrip.getText().toString().trim());
                    String uId=String.valueOf(SharedPrefHelper.getInstance(this).getUser().getId());
                    String zId=String.valueOf(SharedPrefHelper.getInstance(this).getUser().getZona_id());
                    iService.putExtra("uId",uId);
                    iService.putExtra("zId",zId);
                    startService(iService);

                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    if (imagenV.getDrawable() == null) {

                    } else {

                        getLocation();
                        imagenV.buildDrawingCache();
                        Bitmap imagen = imagenV.getDrawingCache();
                        uploadBitmap(imagen);



                    }
                }
                break;
            case R.id.fabAHome:
                aHome();
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location!=null){
            ubicacion=location;
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public class noUbicacionException extends Exception {
        public noUbicacionException(){
            super("no hay gps");
        }
    }
    public static  Bitmap getBitmap(){
        return imagen;
    }
}



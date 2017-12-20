package com.example.sergio_pieza.aplicaciontp.helper;

/**
 * Created by sergio-pieza on 13/12/2017.
 */
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.Locale;
public class LocaleHelper {
public void cargarLocale (Context context){
    Locale locale =new Locale(getIdioma());
    Locale.setDefault(locale);
    Configuration config = new Configuration();
    config.locale = locale;
    Resources res=context.getResources();
    DisplayMetrics dm =res.getDisplayMetrics();
    res.updateConfiguration(config, dm);
}
private String getIdioma(){
    String idioma ="idioma";
    return idioma;


}
}

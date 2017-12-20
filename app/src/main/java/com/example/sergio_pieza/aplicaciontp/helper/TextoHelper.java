package com.example.sergio_pieza.aplicaciontp.helper;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Created by sergio-pieza on 12/12/2017.
 */

public class TextoHelper {
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    public static boolean checkEmail(TextView textView) {
        String email =textView.getText().toString().trim();
        if(!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
            textView.setError("email no valido");
            textView.requestFocus();
            return false;
        }else{
            return true;
        }
    }
    public static boolean textVacio(TextView textView,String error){
        String text =textView.getText().toString().trim();
        if(TextUtils.isEmpty(text)){
            textView.setError(error);
            textView.requestFocus();
            return false;
        }else{
            return true;
        }
    }
    public static boolean editVacio(EditText textView, String error){
        String text =textView.getText().toString().trim();
        if(TextUtils.isEmpty(text)){
            textView.setError(error);
            textView.requestFocus();
            return false;
        }else{
            return true;
        }
    }
    public static boolean textIguales(TextView tv1,TextView tv2 ,String error){
        String t1 =tv1.getText().toString().trim();
        String t2 =tv2.getText().toString().trim();
        if (!t1.equals(t2)){
            tv2.setError(error);
            tv2.requestFocus();
            return false;
        }else{
            return true;
        }
    }
}
